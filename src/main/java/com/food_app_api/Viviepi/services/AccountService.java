package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dto.ResetPasswordDTO;
import com.food_app_api.Viviepi.dto.RolesUsersDTO;
import com.food_app_api.Viviepi.dto.UserSignUpDTO;
import com.food_app_api.Viviepi.dto.token.RefreshTokenDTO;
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
import com.google.gson.Gson;
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
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

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
    Gson gson = new Gson();

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
    public ResponseObject signInAdmin(SignInRequest signInRequest) {
        return null;
    }



    @Override
    public UserSignUpDTO signUp(SignUpRequest request) {
        User user = new User();
        user.setFullname(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsActive(true);

        Role role = roleRepository.findOneByName(request.getRoleName());
        System.out.println("Name role : " + role.getName());

        User newUser = userRepository.save(user);
        UserSignUpDTO userSignUpDTO = userMapper.userSignUpToUserSignUpDTO(newUser);
        RolesUsersDTO rolesUsersDTO = roleUsersMapper.toRoleUserDTO(newUser, role);

        //save cái dto converter đã xử lý xuống database
        this.roleUserRepository.save(
                roleUsersMapper.toRoleUserEntity(rolesUsersDTO)
        );

        return  userSignUpDTO;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseObject signInUser(SignInRequest signInRequest) {
        RolesUsers rolesUsers = roleUserRepository.findOneByEmail(
                signInRequest.getEmail()
        );
        if (rolesUsers.getIdRole().getName().equals("USER")){
            return new ResponseObject(
                    200,
                    "sign-In by user !",
                    token(signInRequest.getEmail(), signInRequest.getPassword())
            );
        }else {
            throw new PermissionDenyException(403, "Permission Denied !", null);
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
    public String verifyEmail(UserSignUpDTO signUpDTO) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String forgotPassword(String email) throws UnsupportedEncodingException {
        return null;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String setPassword(ResetPasswordDTO resetPasswordDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(resetPasswordDTO.getEmail());
        User user = optionalUser.get();
        if (optionalUser.isEmpty()){
            System.out.println("User not exist !");
            throw new UserNotFoundException(404, "User not exist !");
        }
        String verificationResult = validateTokenReset(resetPasswordDTO.getToken());
        if (verificationResult.equalsIgnoreCase("valid")) {
            user.setPassword(this.passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
            this.userRepository.save(user);
        }else {
            return "Verify code is not correct !";
        }
        System.out.println("Set new password successfully !");
        return "Set new password successfully !";
    }

    public String validateTokenReset(String verifyToken) {
        VerificationToken tokenReset = verificationTokenRepository.findByToken(verifyToken);
        if(tokenReset == null){
            System.out.println("Invalid verification token !");
            throw new VerificationTokenException(500, "Invalid verification token !");
        }
        Calendar calendar = Calendar.getInstance();
        if ((tokenReset.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            verificationTokenRepository.delete(tokenReset);
            System.out.println("Token already expired !");
            throw new VerificationTokenException(500, "Token already expired !");
        }
        System.out.println("Token valid");
        return "Valid";
    }

    @Override
    public String resendActiveAccount(String email) throws UnsupportedEncodingException {
        return null;
    }
}
