package com.food_app_api.Viviepi.services;

import com.food_app_api.Viviepi.dtos.CustomerDTO;
import com.food_app_api.Viviepi.entities.Role;
import com.food_app_api.Viviepi.entities.User;
import com.food_app_api.Viviepi.jwt.JwtTokenProvider;
import com.food_app_api.Viviepi.repositories.RoleRepository;
import com.food_app_api.Viviepi.repositories.UserRepository;
import com.food_app_api.Viviepi.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public User registerUser(Long roleId, CustomerDTO customerDTO) {

        Optional<User> isUserExist = userRepository.findByEmail(customerDTO.getEmail());
        User newUser = new User();
        User savedUser = new User();
        if (isUserExist.isPresent()) {
            System.out.println("Email: " + customerDTO.getEmail() + " đã tồn tại");
        } else {

            Optional<Role> userRole = roleRepository.findById(roleId);
            newUser.setEmail(customerDTO.getEmail());
            String encodedPassword = passwordEncoder.encode(customerDTO.getPassword());
            newUser.setPassword(encodedPassword);
            newUser.setDisplayName(customerDTO.getDisplayName());
            newUser.setCreatedBy(customerDTO.getDisplayName());
            newUser.setPhoneNumber(customerDTO.getPhoneNumber());
            newUser.setRole(userRole.get());

            OtpUtils otpUtils = new OtpUtils();
            String otp = otpUtils.generateOtp();
            LocalDateTime currentDateTime = LocalDateTime.now();
            newUser.setOtp(otp);
            newUser.setCreateDateOtp(currentDateTime);
            newUser.setActive(false);
//            Customer customer = new Customer();
//
//            customer.setUser(newUser);
//            customer.setFullname(customerDTO.getFirstName()+ " "+  customerDTO.getLastName());
//            customer.setCode(customerDTO.getCode());
//            customer.setLastName(customerDTO.getLastName());
//            customer.setFirstName(customerDTO.getFirstName());
//            customer.setGender(customerDTO.getGender());
//            customer.setDateOfBirth(customerDTO.getDateOfBirth());
//            customer.setAddress(customerDTO.getAddress());
//            customer.setEmail(newUser.getEmail());
//            customer.setPhoneNumber(customerDTO.getPhoneNumber());
//            customer.setCreatedBy(customerDTO.getFullname());
//            customer.setActive(true);
//
//            newUser.setCustomer(customer);
//            newUser.setCustomer_id(customer);
            savedUser = userRepository.save(newUser);
            //customerService.createCustomer(customerDTO, savedUser);
            // sending otp to your email
        }
        System.out.println("Email: " + newUser.getEmail() + "OTP: " + newUser.getOtp());
        return savedUser;

    }
}
