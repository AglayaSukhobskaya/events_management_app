package com.light_digital.sukhobskaya.TestTask.controller.admin;

import com.light_digital.sukhobskaya.TestTask.dto.AdminDTO;
import com.light_digital.sukhobskaya.TestTask.exception.Handler;
import com.light_digital.sukhobskaya.TestTask.model.Admin;
import com.light_digital.sukhobskaya.TestTask.security.AccountDetails;
import com.light_digital.sukhobskaya.TestTask.security.JWTUtil;
import com.light_digital.sukhobskaya.TestTask.service.AdminService;
import com.light_digital.sukhobskaya.TestTask.util.AdminValidator;
import com.light_digital.sukhobskaya.TestTask.util.ValidationUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController implements Handler {
    AdminService adminService;
    AdminValidator adminValidator;
    ModelMapper modelMapper;
    JWTUtil jwtUtil;
    AuthenticationManager authenticationManager;

    @GetMapping("/api/admin")
    public AdminDTO get(@AuthenticationPrincipal AccountDetails accountDetails) {
        return modelMapper.map(adminService.get(accountDetails.getAccount().getId()), AdminDTO.class);
    }

    @PostMapping("/auth/admin/registration")
    public Map<String, String> register(@RequestBody @Valid AdminDTO adminDTO,
                                        BindingResult bindingResult) {

        Admin admin = modelMapper.map(adminDTO, Admin.class);

        adminValidator.validate(admin, bindingResult);
        ValidationUtil.checkDataValidity(bindingResult);

        adminService.register(admin);

        String token = jwtUtil.generateToken(admin.getLogin());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/auth/admin/login")
    public Map<String, String> performLogin(@RequestBody AdminDTO adminDTO) {

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(adminDTO.getLogin(),
                        adminDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(adminDTO.getLogin());
        return Map.of("jwt-token", token);
    }

    @DeleteMapping("/api/admin")
    public ResponseEntity<HttpStatus> delete(@AuthenticationPrincipal AccountDetails accountDetails) {
        adminService.delete(accountDetails.getAccount().getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/api/admin/application")
    public ResponseEntity<HttpStatus> applyForContract(@AuthenticationPrincipal AccountDetails accountDetails) {
        adminValidator.contractCheck(accountDetails.getAccount().getId());
        adminService.applyForContract(accountDetails.getAccount().getId());
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
