package com.food_app_api.Viviepi.jwt;

import com.food_app_api.Viviepi.exceptions.JwtFilterException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    @Autowired
    private JwtUtil jwtUtil;
    private Gson gson = new Gson();

//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//
//            String token = extractTokenFromRequest(request); // Lấy token từ request
//            if(token != null)
//            {
//                if (jwtUtil.validate(token)) {
//                    String subjectJson = jwtUtil.getSubject(token);
//                    Map<String,Object> subjectData = gson.fromJson(subjectJson,Map.class);
//
//                    if (StringUtils.hasText(subjectData.get("type").toString())
//                    && !subjectData.get("type").equals("refresh")){
//                        try {
//
//                            String email = (String) subjectData.get("data");
//                            //
//                            ResponseAuthentication responseToken = gson.fromJson(email, ResponseAuthentication.class);
//                            String newData = gson.toJson(responseToken.getRoles());
//                            Type listType = new TypeToken<ArrayList<SimpleGrantedAuthority>>(){}.getType();
//                            List<GrantedAuthority> roles = gson.fromJson(newData, listType);
//
//                            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
//                                    responseToken.getUsername(), "", roles
//                            );
//                            // Lấy thông tin từ token
//                            String username = (String) userToken.getPrincipal();
//                            String password = (String) userToken.getCredentials();
//                            List<GrantedAuthority> authorities = (List<GrantedAuthority>) userToken.getAuthorities();
//
//                            // In ra thông tin
//                            System.out.println("Username: " + username);
//                            System.out.println("Password: " + password);
//                            System.out.println("Authorities: " + authorities);
//
//                            SecurityContext contextHolder = SecurityContextHolder.getContext();
//                            contextHolder.setAuthentication(userToken);
//                        }catch (JsonSyntaxException | IllegalStateException e){
//                            System.out.println("Not accept type !" + e);
//                            throw new JsonSyntaxException("Not accept type !", e);
//                        }
//                        //
////
////                        UsernamePasswordAuthenticationToken authenticationToken =
////                                new UsernamePasswordAuthenticationToken(email,null, new ArrayList<>());
////                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//                    }
//                }
//
//
//            }
//
//        filterChain.doFilter(request, response);
//    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String headerValue = request.getHeader("Authorization");
        if (headerValue != null && headerValue.startsWith("Bearer ")){
            final String token = headerValue.substring(7);
            if (jwtUtil.validationToke(token)){
                final String data = jwtUtil.parserToken(token);
                String reqPath = request.getRequestURI();
                if (data != null && !data.isEmpty()){
                    if (!reqPath.equals("/account/api/refresh_token")){
                        try {
                            ResponseToken responseToken = gson.fromJson(data, ResponseToken.class);
                            String newData = gson.toJson(responseToken.getRoles());
                            Type listType = new TypeToken<ArrayList<SimpleGrantedAuthority>>(){}.getType();
                            List<GrantedAuthority> roles = gson.fromJson(newData, listType);

                            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(
                                    responseToken.getUsername(), "", roles
                            );
                            SecurityContext contextHolder = SecurityContextHolder.getContext();
                            contextHolder.setAuthentication(userToken);
                        }catch (JsonSyntaxException | IllegalStateException e){
                            System.out.println("Not accept type !" + e);
                            throw new JsonSyntaxException("Not accept type !", e);
                        }
                    }
                }else {
                    System.out.println("Data is not exist !");
                    throw new JwtFilterException(403, "Data is not exist !", null);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

}
    
