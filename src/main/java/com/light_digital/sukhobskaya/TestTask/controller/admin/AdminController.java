package com.light_digital.sukhobskaya.TestTask.controller.admin;

import com.light_digital.sukhobskaya.TestTask.dto.AdminDTO;
import com.light_digital.sukhobskaya.TestTask.exception.Handler;
import com.light_digital.sukhobskaya.TestTask.model.Admin;
import com.light_digital.sukhobskaya.TestTask.security.AccountDetails;
import com.light_digital.sukhobskaya.TestTask.security.JWTUtil;
import com.light_digital.sukhobskaya.TestTask.service.AdminService;
import com.light_digital.sukhobskaya.TestTask.util.AdminValidator;
import com.light_digital.sukhobskaya.TestTask.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth/admin")
@AllArgsConstructor
public class AdminController implements Handler {

    private final AdminService adminService;
    private final AdminValidator adminValidator;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @GetMapping()
    public AdminDTO get(@AuthenticationPrincipal AccountDetails accountDetails) {
        return modelMapper.map(adminService.get(accountDetails.getAccount().getId()), AdminDTO.class);
    }

    @PostMapping("/registration")
    public Map<String, String> register(@RequestBody @Valid AdminDTO adminDTO,
                                        BindingResult bindingResult) {

        Admin admin = modelMapper.map(adminDTO, Admin.class);

        adminValidator.validate(admin, bindingResult);
        ValidationUtil.checkDataValidity(bindingResult);

        adminService.register(admin);

        String token = jwtUtil.generateToken(admin.getLogin());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
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

}
