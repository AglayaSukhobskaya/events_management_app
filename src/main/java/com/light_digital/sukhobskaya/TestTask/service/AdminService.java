package com.light_digital.sukhobskaya.TestTask.service;

import com.light_digital.sukhobskaya.TestTask.exception.NotFoundException;
import com.light_digital.sukhobskaya.TestTask.model.Admin;
import com.light_digital.sukhobskaya.TestTask.model.Application;
import com.light_digital.sukhobskaya.TestTask.model.Contract;
import com.light_digital.sukhobskaya.TestTask.repository.AdminRepository;
import com.light_digital.sukhobskaya.TestTask.repository.ApplicationRepository;
import com.light_digital.sukhobskaya.TestTask.repository.ContractRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminService {
    AdminRepository adminRepository;
    ApplicationRepository applicationRepository;
    ContractRepository contractRepository;
    PasswordEncoder passwordEncoder;

    @Transactional
    public void register(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole("ROLE_ADMIN");
        admin.setEvents(new ArrayList<>());
        adminRepository.save(admin);

        Contract contract = new Contract(admin);
        contractRepository.save(contract);
        admin.setContract(contract);
    }

    public Admin get(Integer id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Admin with id=" + id + " not found!"));
    }

    @Transactional
    public void delete(Integer id) {
        adminRepository.deleteById(id);
    }

    @Transactional
    public void applyForContract(int id) {
        Optional<Admin> foundAdmin = adminRepository.findById(id);
        if (foundAdmin.isEmpty()) {
            throw new NotFoundException("Admin with id=" + id + " not found!");
        }
        Application application = new Application(foundAdmin.get());
        applicationRepository.save(application);
    }

}
