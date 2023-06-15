package com.light_digital.sukhobskaya.TestTask.controller.user;

import com.light_digital.sukhobskaya.TestTask.dto.UserDTO;
import com.light_digital.sukhobskaya.TestTask.exception.Handler;
import com.light_digital.sukhobskaya.TestTask.model.User;
import com.light_digital.sukhobskaya.TestTask.security.JWTUtil;
import com.light_digital.sukhobskaya.TestTask.service.UserService;
import com.light_digital.sukhobskaya.TestTask.util.UserValidator;
import com.light_digital.sukhobskaya.TestTask.util.ValidationUtil;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth/user")
@AllArgsConstructor
public class UserController implements Handler {

    private final UserService userService;
    private final UserValidator userValidator;
    private final ModelMapper modelMapper;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/registration")
    public Map<String, String> register(@RequestBody @Valid UserDTO userDTO,
                                        BindingResult bindingResult) {

        User user = modelMapper.map(userDTO, User.class);

        userValidator.validate(user, bindingResult);
        ValidationUtil.checkDataValidity(bindingResult);

        userService.register(user);

        String token = jwtUtil.generateToken(user.getLogin());
        return Map.of("jwt-token", token);
    }

    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody UserDTO userDTO) {

        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(userDTO.getLogin(),
                        userDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }

        String token = jwtUtil.generateToken(userDTO.getLogin());
        return Map.of("jwt-token", token);
    }

}
