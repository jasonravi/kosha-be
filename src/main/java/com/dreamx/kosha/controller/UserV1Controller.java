package com.dreamx.kosha.controller;


import com.dreamx.kosha.entity.User;
import com.dreamx.kosha.model.ConsentResponseDTO;
import com.dreamx.kosha.model.UserResponseDTO;
import com.dreamx.kosha.service.UserService;
import com.dreamx.kosha.service.UserV1Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("v1/kosha/user")
public class UserV1Controller {

    private UserV1Service userV1Service;
    private UserService userService;

    // build get user by id REST API
    // http://localhost:8080/v1/cosha/user/1
    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long userId){
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ConsentResponseDTO> createUser(@RequestBody User user) throws JsonProcessingException {
        ConsentResponseDTO consentResponseDTO = userV1Service.saveUser(user);
        return new ResponseEntity<>(consentResponseDTO, HttpStatus.CREATED);
    }

    // This method will ignore the class-level @RequestMapping
    @RequestMapping(value = "/Consent/Notification", method = RequestMethod.GET)
    public String getStatus() {
        return "Status OK";
    }

    @GetMapping("onboardingDetail")
    public ResponseEntity<UserResponseDTO> getOnboardingDetails(@RequestParam(name = "mobileNumber") String mobileNumber) throws JsonProcessingException {
        UserResponseDTO userResponseDTO = userV1Service.getOnboardingDetail(mobileNumber);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }



}
