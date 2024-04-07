package com.food_app_api.Viviepi.controllers;


import com.food_app_api.Viviepi.entities.RegistrationTokenDevice;
import com.food_app_api.Viviepi.services.RegistrationTokenDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/token_mobile/api")
public class RegistrationTokenDeviceController {

    @Autowired
    private RegistrationTokenDeviceService registrationTokenDeviceService;

    @GetMapping("/get/all")
    public ResponseEntity<List<RegistrationTokenDevice>> getAllTokens() {
        List<RegistrationTokenDevice> tokens = registrationTokenDeviceService.getAllTokens();
        return new ResponseEntity<>(tokens, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RegistrationTokenDevice> saveToken(@RequestBody RegistrationTokenDevice tokenDevice) {
        RegistrationTokenDevice savedToken = registrationTokenDeviceService.saveToken(tokenDevice);
        return new ResponseEntity<>(savedToken, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteToken(@PathVariable Long id) {
        registrationTokenDeviceService.deleteToken(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/all")
    public ResponseEntity<Void> deleteAllTokens() {
        registrationTokenDeviceService.deleteAllToken();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
