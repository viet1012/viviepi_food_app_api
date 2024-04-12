package com.food_app_api.Viviepi.services;


import com.food_app_api.Viviepi.dto.UserDTO;
import com.food_app_api.Viviepi.entities.User;
import com.food_app_api.Viviepi.jwt.JwtUtil;
import com.food_app_api.Viviepi.mapper.RoleUsersMapper;
import com.food_app_api.Viviepi.mapper.UserMapper;
import com.food_app_api.Viviepi.payload.response.ResponseOutput;
import com.food_app_api.Viviepi.repositories.IRoleRepository;
import com.food_app_api.Viviepi.repositories.IRoleUserRepository;
import com.food_app_api.Viviepi.repositories.IUserRepository;
import com.food_app_api.Viviepi.repositories.ITokenRepository;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@AllArgsConstructor
public class UserService implements IUserService {

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
    Gson gson = new Gson();
    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAll() {
        if (userRepository.findAll().isEmpty()) {
            throw new ObjectNotFoundException(404, "List user is empty !");
        }
        System.out.println("Get all user completed !");
        return userMapper.toUserDTOList(userRepository.findAll());
    }

    @Override
    public ResponseOutput getAll(int page, int limit, String sortBy, String sortField) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.fromString(sortBy), sortField));
        List<UserDTO> userDTOList = userMapper.toUserDTOList(
                userRepository.findAll(pageable).getContent()
        );
        if (userDTOList.isEmpty()){
            System.out.println("List user is empty !");
            throw new ObjectNotFoundException(404, "List user is empty !");
        }
        int totalItem = (int) userRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        System.out.println("Get all user completed !");
        return new ResponseOutput(
                page,
                totalItem,
                totalPage,
                userDTOList
        );
    }

    @Override
    public boolean checkEmailExists(String email) {
        System.out.println("Email: " + email);
        Optional<User> user = userRepository.findByEmail(email);
        System.out.println("Email Exists " + user.isPresent());

        return user.isPresent();
    }


    @Override
    public UserDTO findUserByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            UserDTO userDTO = new UserDTO();
            userDTO = userMapper.userToUserDTO(user.get());
            return userDTO;
        }
        else return null;
    }

    @Override
    public UserDTO addNewUser(long id) {
        return null;
    }


    @Override
    public UserDTO showUserInfo(String email){
        Optional<User>  optionalUser = userRepository.findByEmail(email);
        UserDTO userDTO = userMapper.userToUserDTO(optionalUser.get());
        return userDTO;
    }

    @Override
    public long getIdByEmail(String emai) {
        return 0;
    }

    @Override
    public void deleteUserByUserId(String userId) {
        Optional<User> user = userRepository.findByUserId(userId);
        if(user.isPresent())
        {
            userRepository.delete(user.get());
        }
        else {
            return;
        }
    }


}
