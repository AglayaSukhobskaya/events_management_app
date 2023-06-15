package com.light_digital.sukhobskaya.TestTask.util;

import com.light_digital.sukhobskaya.TestTask.exception.NotValidDataException;
import lombok.experimental.UtilityClass;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;

@UtilityClass
public class ValidationUtil {

    public static void checkDataValidity(Errors errors) {
        if (errors.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errorList = errors.getFieldErrors();
            for (FieldError error : errorList) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new NotValidDataException(errorMsg.toString());
        }
    }
}
