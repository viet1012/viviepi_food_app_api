package com.food_app_api.Viviepi.services;
import com.food_app_api.Viviepi.entities.RegistrationTokenDevice;
import com.food_app_api.Viviepi.repositories.IRegistrationTokenDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RegistrationTokenDeviceService {
    @Autowired
    private IRegistrationTokenDeviceRepository registrationTokenDeviceRepository;

    public List<RegistrationTokenDevice> getAllTokens() {
        return registrationTokenDeviceRepository.findAll();
    }

    public RegistrationTokenDevice saveToken(RegistrationTokenDevice tokenDevice) {
        return registrationTokenDeviceRepository.save(tokenDevice);
    }

    public void deleteToken(Long id) {
        registrationTokenDeviceRepository.deleteById(id);
    }

    public void deleteAllToken() {
        registrationTokenDeviceRepository.deleteAll();
    }

}
