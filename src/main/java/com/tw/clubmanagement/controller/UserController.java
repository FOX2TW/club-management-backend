package com.tw.clubmanagement.controller;

import com.tw.clubmanagement.controller.representation.UserInformationGetResponseDTO;
import com.tw.clubmanagement.exception.ValidationException;
import com.tw.clubmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;


@RestController
@RequestMapping("/user")
public class UserController {
    private static final Pattern POSITIVE_PATTERN = Pattern.compile("^[1-9]?[0-9]*$");
    private static final String USERID_INVALID_MESSAGE = "用户ID必须是一个正整数";

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public UserInformationGetResponseDTO getUserInformation(@PathVariable String userId) {
        if (!POSITIVE_PATTERN.matcher(userId).matches()) {
            throw new ValidationException(USERID_INVALID_MESSAGE);
        }

        return UserInformationGetResponseDTO.fromUserInformation(
                userService.getUserInformation(Integer.valueOf(userId)));
    }
}
