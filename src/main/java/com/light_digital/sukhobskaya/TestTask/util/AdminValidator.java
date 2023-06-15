package com.light_digital.sukhobskaya.TestTask.util;

import com.light_digital.sukhobskaya.TestTask.model.Admin;
import com.light_digital.sukhobskaya.TestTask.repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
public class AdminValidator implements Validator {
    private final AdminRepository adminRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Admin.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Assert.notNull("target", "Admin should not be null!");
        Admin admin = (Admin) target;

        if (adminRepository.findByLogin(admin.getLogin()).isPresent()) {
            errors.rejectValue("login", "", "Admin with this login already exists!");
        }
    }

}
