package com.AlexTsoy.Task18.util.validators;

import com.AlexTsoy.Task18.entity.User;
import com.AlexTsoy.Task18.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserValidator implements Validator {

    private final UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "This email is used");
        }
        if (userRepository.findUserByPhoneNumber(user.getPhoneNumber()).isPresent()) {
            errors.rejectValue("phoneNumber", "", "This phone is used");
        }
    }
}
