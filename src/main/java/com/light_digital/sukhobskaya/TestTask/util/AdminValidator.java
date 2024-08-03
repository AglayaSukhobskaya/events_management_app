package com.light_digital.sukhobskaya.TestTask.util;

import com.light_digital.sukhobskaya.TestTask.exception.NotFoundException;
import com.light_digital.sukhobskaya.TestTask.exception.NotValidDataException;
import com.light_digital.sukhobskaya.TestTask.model.Admin;
import com.light_digital.sukhobskaya.TestTask.repository.AdminRepository;
import com.light_digital.sukhobskaya.TestTask.security.AccountDetails;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminValidator implements Validator {
    AdminRepository adminRepository;

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

    public void haveSignedContract(AccountDetails accountDetails) {
        var foundAdmin = adminRepository.findByLogin(accountDetails.getUsername())
                .orElseThrow(() -> new NotFoundException("Admin does not exist!"));
        if (!foundAdmin.getContract().getSigned()) {
            throw new NotValidDataException("Admin does not have signed contract!");
        }
    }

    public void contractCheck(Integer id) {
        var foundAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Admin does not exist!"));
        if (foundAdmin.getContract().getSigned()) {
            throw new NotValidDataException("Your contract is already signed!");
        }
    }
}
