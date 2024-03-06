package com.food_app_api.Viviepi.jwt;


import com.food_app_api.Viviepi.entities.RolesUsers;
import com.food_app_api.Viviepi.exceptions.UserNotFoundException;
import com.food_app_api.Viviepi.exceptions.VerificationTokenException;
import com.food_app_api.Viviepi.repositories.IRoleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    IRoleUserRepository roleUserRepository;

//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String email  = authentication.getName();
//        String password = String.valueOf(authentication.getCredentials());
//        RolesUsers users = roleUserRepository.findOneByEmailAndProvider(email);
//
//        System.out.println("Arrived at custom provider. " + email + " " + password);
//        if(userService.checkEmailExists(email)){
//            UserDTO user =  userService.findUserByEmail(email);
//            System.out.println("Fetched pwd: " + user.getPassword());
//            if(passwordEncoder.matches(password, user.getPassword()))
//            {
//                // Lấy danh sách quyền của user
//                List<GrantedAuthority> roles = new ArrayList<>();
//                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
//                        users.getIdRole().getName()
//                );
//                roles.add(grantedAuthority);
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//                        user.getEmail(),user.getPassword(), roles
//                );
//
//
//                //UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword(),new ArrayList<>());
//                System.out.println("Password matches");
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                return authentication;
//            }
//            else {
//                System.out.println("Password not matches");
//            }
//
//        }else {
//            System.out.println("Email not exists");
//            throw new BadCredentialsException("Email does not exist. Please create an account.");
//        }
//        return null;
//    }
@Override
public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();
    RolesUsers users = roleUserRepository.findOneByEmail(username);
    if (users != null){
        if (users.getIdUser().getIsActive()){
            if (passwordEncoder.matches(password, users.getIdUser().getPassword())){
                List<GrantedAuthority> roles = new ArrayList<>();
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(
                        users.getIdRole().getName()
                );
                roles.add(grantedAuthority);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                        username, users.getIdUser().getPassword(), roles
                );
                SecurityContextHolder.getContext().setAuthentication(token);
                System.out.println("TOKEN after CustomAuthentication: " + token);
                return token;
            }else {
                System.out.println("Username or password not exist !");
                throw new UserNotFoundException(404, "Username or password not exist !");
            }
        }else {
            System.out.println("User is disabled !");
            throw new VerificationTokenException(401, "User is disabled !");
        }
    }else {
        System.out.println("User not found or not exist !");
        throw new UserNotFoundException(404, "User not found or not exist !");
    }
}
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }


}
