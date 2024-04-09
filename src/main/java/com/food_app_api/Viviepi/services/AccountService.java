package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.ResetPasswordDTO;
import com.food_app_api.Viviepi.dto.RolesUsersDTO;
import com.food_app_api.Viviepi.dto.UserDTO;
import com.food_app_api.Viviepi.dto.UserSignUpDTO;
import com.food_app_api.Viviepi.dto.token.RefreshTokenDTO;
import com.food_app_api.Viviepi.entities.RegistrationTokenDevice;
import com.food_app_api.Viviepi.entities.Role;
import com.food_app_api.Viviepi.entities.RolesUsers;
import com.food_app_api.Viviepi.entities.User;
import com.food_app_api.Viviepi.entities.token.Tokens;
import com.food_app_api.Viviepi.entities.token.VerificationToken;
import com.food_app_api.Viviepi.exceptions.PermissionDenyException;
import com.food_app_api.Viviepi.exceptions.UserNotFoundException;
import com.food_app_api.Viviepi.exceptions.VerificationTokenException;
import com.food_app_api.Viviepi.jwt.JwtUtil;
import com.food_app_api.Viviepi.jwt.ResponseAuthentication;
import com.food_app_api.Viviepi.jwt.ResponseToken;
import com.food_app_api.Viviepi.mapper.RoleUsersMapper;
import com.food_app_api.Viviepi.mapper.UserMapper;
import com.food_app_api.Viviepi.payload.request.SignInRequest;
import com.food_app_api.Viviepi.payload.request.SignUpRequest;
import com.food_app_api.Viviepi.payload.response.ResponseObject;
import com.food_app_api.Viviepi.repositories.*;
import com.food_app_api.Viviepi.util.EmailUtil;
import com.food_app_api.Viviepi.util.UUIDUtil;
import com.google.gson.Gson;
import jakarta.mail.MessagingException;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService implements IAccountService{

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ITokenRepository tokenRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    IRoleUserRepository roleUserRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleUsersMapper roleUsersMapper;

    @Autowired
    IVerificationTokenRepository verificationTokenRepository;

    @Autowired
    private  EmailUtil emailUtil;

    @Autowired
    private UUIDUtil uuidUtil;

    @Autowired
    Gson gson = new Gson();


    @Autowired
    IRegistrationTokenDeviceRepository registrationTokenDeviceRepository;
    @Override
    public UserDTO getUserInfo(String token) {
        UserDTO userDTO = new UserDTO();
        String name= "";
        if (jwtUtil.validationToke(token)) {
            final String data = jwtUtil.parserToken(token);
            ResponseToken responseToken = gson.fromJson(data, ResponseToken.class);
            System.out.println("Username info : " +  responseToken.getUsername());
            Optional<User> optionalUser = userRepository.findByEmail(responseToken.getUsername());
            User user = optionalUser.get();
            userDTO = userMapper.toUserDTO(user);
//            name = responseToken.getUsername();
        }else {
            System.out.println("Token is not valid!");
        }
        return userDTO;
    }

    @Override
    public boolean checkEmailExists(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public RefreshTokenDTO refreshToken(HttpServletRequest request, HttpServletResponse response) throws CertificateException {
        return null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject signInAdmin(SignInRequest signInRequest) {
        RolesUsers rolesUsers = roleUserRepository.findOneByEmail(
                signInRequest.getEmail()
        );
        if ((rolesUsers.getIdRole().getName().equals("ROLE_ADMIN"))){
            return new ResponseObject(
                    200,
                    "Sign-In by admin !",
                    token(signInRequest.getEmail(), signInRequest.getPassword())
            );
        }else {
            throw new PermissionDenyException(403, "Email or Password Denied !", null);
        }
    }


    @Override
    public UserSignUpDTO signUp(SignUpRequest request)  {
        if(!checkEmailExists(request.getEmail()))
        {
            User user = new User();
            user.setFullname(request.getFullName());
            user.setEmail(request.getEmail());
            if(request.isGoogle())
            {
                user.setPassword(null);
            }
            else {
                user.setPassword(passwordEncoder.encode(request.getPassword()));

            }
            user.setIsActive(true);
            user.setUserId(uuidUtil.generateUUID());
            Role role = roleRepository.findOneByName(request.getRoleName());
            System.out.println("Name role : " + role.getName());

            User newUser = userRepository.save(user);
            UserSignUpDTO userSignUpDTO = userMapper.userSignUpToUserSignUpDTO(newUser,role);
            RolesUsersDTO rolesUsersDTO = roleUsersMapper.toRoleUserDTO(newUser, role);
            String verifyCode = String.format("%040d", new BigInteger(
                    UUID.randomUUID().toString().replace("-", ""), 16)
            );

            VerificationToken verificationToken = new VerificationToken(verifyCode.substring(0, 6), user);
            userSignUpDTO.setToken(verificationToken.getOtp());
            this.verificationTokenRepository.save(verificationToken);
            // save registrationTokenDevice
            if (!registrationTokenDeviceRepository.existsByTokenDevice(request.getTokenDevice())) {
                RegistrationTokenDevice registrationTokenDevice = new RegistrationTokenDevice();
                registrationTokenDevice.setTokenDevice(request.getTokenDevice());
                registrationTokenDeviceRepository.save(registrationTokenDevice);
            }
            //save cái dto converter đã xử lý xuống database
            this.roleUserRepository.save(
                    roleUsersMapper.toRoleUserEntity(rolesUsersDTO)
            );

            return  userSignUpDTO;
        }else{
            throw new PermissionDenyException(403, "Email is exist !", null);

        }

    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject signInUser(SignInRequest signInRequest) {
        RolesUsers rolesUsers = roleUserRepository.findOneByEmail(
                signInRequest.getEmail()
        );
        if (rolesUsers.getIdRole().getName().equals("ROLE_USER")){


            return new ResponseObject(
                    200,
                    "sign-In by user !",
                    token(signInRequest.getEmail(), signInRequest.getPassword())
            );
        }else {
            throw new PermissionDenyException(403, "Email or Password Denied !", null);
        }
    }

    public ResponseAuthentication token(String email, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password
        );
        this.authenticationManager.authenticate(authenticationToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        ResponseToken data = new ResponseToken();
        data.setUsername(email);
        data.setRoles(roles);
        String token = jwtUtil.generateToken(gson.toJson(data));
        String refreshToken = jwtUtil.generateRefreshToken(email);
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.get();
        revokeAllUserTokens(user);
        saveUserToken(user, refreshToken);

//        Optional<User> currentUser = userRepository.findByEmail(email);
        //UserSession userSession = new UserSession(currentUser.get().getUserId(),token,email);
        // Lưu session vào Redis
        //userSessionService.saveUserSession(userSession);

        System.out.println("Generate token is successfully !");
        return ResponseAuthentication.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .build();
    }

    private void revokeAllUserTokens(User user){
        List<Tokens> validUserTokens = tokenRepository.findAllTokenValidByUser(user.getId());
        if (!validUserTokens.isEmpty()){
            validUserTokens.forEach(tokens -> {
                tokens.setExpired(true);
                tokens.setRevoke(true);
            });
        }
        this.tokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(User user, String jwtToken){
        Tokens tokens = Tokens.builder()
                .user(user)
                .token(jwtToken)
                .tokenType("Bearer")
                .expired(false)
                .revoke(false)
                .build();
        this.tokenRepository.save(tokens);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String verifyEmail(UserSignUpDTO signUpDTO) {
        VerificationToken verificationToken = verificationTokenRepository.findByOtp(signUpDTO.getToken());
        if (verificationToken.getUser().getIsActive()){
            System.out.println("This account has already been verified, please login!");
            return "This account has already been verified, please, login.";
        }
        String verificationResult = validateOTP(signUpDTO.getToken());
        if (verificationResult.equalsIgnoreCase("Valid")){
            System.out.println("Email verified successfully. Now you can login to your account");
            return "Email verified successfully. Now you can login to your account";
        }
        System.out.println("Invalid verification token !");
        return "Invalid verification token !";
    }

    public String validateOTP(String verifyToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByOtp(verifyToken);
        if(verificationToken == null){
            System.out.println("Invalid verification token !");
            throw new VerificationTokenException(500, "Invalid verification token !");
        }
        if (verificationToken.getUser().getIsActive()){
            System.out.println("This account has already been verified, please login!");
            return "This account has already been verified, please, login.";
        }
        User userEntity = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(verificationToken);
            System.out.println("Token already expired !");
            throw new VerificationTokenException(500, "Token already expired !");
        }
        userEntity.setIsActive(true);
        userRepository.save(userEntity);
        System.out.println("Token valid");
        return "Valid";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String forgotPassword(String email) throws UnsupportedEncodingException, MessagingException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            System.out.println("User not exist !");
            throw new UserNotFoundException(404, "User not exist !");
        }
        String verifyCode = String.format("%040d", new BigInteger(
                UUID.randomUUID().toString().replace("-", ""), 16)
        );
        VerificationToken verificationToken = new VerificationToken(verifyCode.substring(0, 6), optionalUser.get());
        this.verificationTokenRepository.save(verificationToken);
        this.emailUtil.sendResetPassword(email, verifyCode.substring(0, 6));
        System.out.println("Check your email to reset password if account was registered !");
        return "Check your email to reset password if account was register !";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String  setPassword(ResetPasswordDTO resetPasswordDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(resetPasswordDTO.getEmail());
        User user = optionalUser.get();
        if (optionalUser.isEmpty()){
            System.out.println("User not exist !");
            throw new UserNotFoundException(404, "User not exist !");
        }
//        String verificationResult = validateTokenReset(resetPasswordDTO.getOtp());
//        if (verificationResult.equalsIgnoreCase("valid")) {
//            user.setPassword(this.passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
//            this.userRepository.save(user);
//        }else {
//            return "Verify code is not correct !";
//        }
        user.setPassword(this.passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
        this.userRepository.save(user);

        System.out.println("Set new password successfully !");
        return "Set new password successfully !";
    }

    public String validateTokenReset(String verifyOTP, String email) {
        Optional<User> user = userRepository.findByEmail(email);
        VerificationToken tokenReset = verificationTokenRepository.findByOtp(verifyOTP);
        if(tokenReset == null  || user.isEmpty()){
            System.out.println("Invalid verification token !");
            throw new VerificationTokenException(500, "Invalid verification token !");
        }
        Calendar calendar = Calendar.getInstance();
        if ((calendar.getTime().getTime() - tokenReset.getExpirationTime().getTime()) <= 0){
            verificationTokenRepository.delete(tokenReset);
            System.out.println("Token already expired !");
            throw new VerificationTokenException(500, "Token already expired !");
        }
        System.out.println("Token valid");
        return "Valid";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String resendActiveAccount(String email) throws  UnsupportedEncodingException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.get();
        if (optionalUser.isEmpty()){
            System.out.println("User not found or exist !");
            throw new ObjectNotFoundException(404, "User not found or exist !");
        }
        String verifyCode = String.format("%040d", new BigInteger(
                UUID.randomUUID().toString().replace("-", ""), 16)
        );
        VerificationToken verificationToken = new VerificationToken(verifyCode.substring(0, 6), user);
        this.verificationTokenRepository.save(verificationToken);

        return "Verification code is resend to your email ! please check email to activation account again !";
    }
}
