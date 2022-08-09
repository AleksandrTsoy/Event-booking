package com.AlexTsoy.Task18.controllers;

import com.AlexTsoy.Task18.entity.User;
import com.AlexTsoy.Task18.exceptions.EntityErrorResponse;
import com.AlexTsoy.Task18.exceptions.EntityNotSaveExceprion;
import com.AlexTsoy.Task18.service.UserService;
import com.AlexTsoy.Task18.util.validators.UserValidator;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UsersController {
    private final UserService userService;
    private final UserValidator userValidator;

    @ApiOperation("Get all users")
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();

    }

    @ApiOperation("Get one user")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.findById(id);
    }

    @ApiOperation("Create new user")
    @PostMapping()
    public User create(@RequestBody @Valid User user, BindingResult bindingResult) {
        validator(user, bindingResult);
        return userService.save(user);
    }

    @ApiOperation("Remove user")
    @DeleteMapping("/{id}")
    public void removeParticipant(@PathVariable("id") int id) {
        userService.delete(id);
    }

    @ApiOperation("Update user")
    @PutMapping("/{id}")
    public void update(@PathVariable("id") int id, @RequestBody @Valid User user, BindingResult bindingResult) {
        validator(user, bindingResult);
        user.setId(id);
        userService.save(user);
    }

    private void validator(User user, BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new EntityNotSaveExceprion(errorMessage.toString());
        }
    }

    @ExceptionHandler
    private ResponseEntity<EntityErrorResponse> saveException(EntityNotSaveExceprion e) {
        EntityErrorResponse response = new EntityErrorResponse(e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
