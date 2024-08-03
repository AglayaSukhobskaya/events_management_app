package com.light_digital.sukhobskaya.TestTask.service;

import com.light_digital.sukhobskaya.TestTask.model.Admin;
import com.light_digital.sukhobskaya.TestTask.model.User;
import com.light_digital.sukhobskaya.TestTask.repository.AdminRepository;
import com.light_digital.sukhobskaya.TestTask.repository.UserRepository;
import com.light_digital.sukhobskaya.TestTask.security.AccountDetails;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDetailsService implements UserDetailsService {
    AdminRepository adminRepository;
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Admin> foundAdmin = adminRepository.findByLogin(username);
        Optional<User> foundUser = userRepository.findByLogin(username);

        if (foundAdmin.isEmpty() && foundUser.isEmpty()) {
            throw new UsernameNotFoundException("Account not found!");
        }

        return foundAdmin.map(AccountDetails::new).orElseGet(() -> new AccountDetails(foundUser.get()));
    }
}
