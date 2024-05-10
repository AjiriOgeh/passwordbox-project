package com.passwordbox.controllers;

import com.passwordbox.dataTransferObjects.requests.*;
import com.passwordbox.dataTransferObjects.responses.*;
import com.passwordbox.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/Signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.signUp(signUpRequest)), HttpStatus.CREATED);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/Logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest logoutRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.logout(logoutRequest)), HttpStatus.OK);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/Login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.login(loginRequest)), HttpStatus.OK);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/SaveLoginInfo")
    public ResponseEntity<?> saveNewLoginInfo(@RequestBody SaveLoginInfoRequest saveLoginInfoRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.saveLoginInfo(saveLoginInfoRequest)), HttpStatus.CREATED);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/EditLoginInfo")
    public ResponseEntity<?> editLoginInfo(@RequestBody EditLoginInfoRequest editLoginInfoRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.editLoginInfo(editLoginInfoRequest)), HttpStatus.OK);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ViewLoginInfo")
    public ResponseEntity<?> viewLoginInfo(@RequestBody ViewLoginInfoRequest viewLoginInfoRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.viewLoginInfo(viewLoginInfoRequest)), HttpStatus.OK);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/DeleteLoginInfo")
    public ResponseEntity<?> deleteLoginInfo(@RequestBody DeleteLoginInfoRequest deleteLoginInfoRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.deleteLoginInfo(deleteLoginInfoRequest)), HttpStatus.OK);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/GeneratePassword")
    public ResponseEntity<?> generatePassword(@RequestBody GeneratePasswordRequest generatePasswordRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.generatePassword(generatePasswordRequest)), HttpStatus.CREATED);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/SaveCreditCard")
    public ResponseEntity<?> saveCreditCard(@RequestBody SaveCreditCardRequest saveCreditCardRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.saveCreditCard(saveCreditCardRequest)), HttpStatus.CREATED);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/EditCreditCard")
    public ResponseEntity<?> editCreditCard(@RequestBody EditCreditCardRequest editCreditCardRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.editCreditCard(editCreditCardRequest)), HttpStatus.OK);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ViewCreditCard")
    public ResponseEntity<?> viewCreditCard(@RequestBody ViewCreditCardRequest viewCreditCardRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.viewCreditCard(viewCreditCardRequest)), HttpStatus.OK);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/DeleteCreditCard")
    public ResponseEntity<?> deleteCreditCard(@RequestBody DeleteCreditCardRequest deleteCreditCardRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.deleteCreditCard(deleteCreditCardRequest)), HttpStatus.OK);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/SavePassport")
    public ResponseEntity<?> savePassport(@RequestBody SavePassportRequest savePassportRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.savePassport(savePassportRequest)), HttpStatus.CREATED);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/EditPassport")
    public ResponseEntity<?> editPassport(@RequestBody EditPassportRequest editPassportRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.editPassport(editPassportRequest)), HttpStatus.OK);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ViewPassport")
    public ResponseEntity<?> viewPassport(@RequestBody ViewPassportRequest viewPassportRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.viewPassport(viewPassportRequest)), HttpStatus.OK);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/DeletePassport")
    public ResponseEntity<?> deletePassport(@RequestBody DeletePassportRequest deletePassportRequest) {
        try {
            return new ResponseEntity<>(new ApiResponse(true, userService.deletePassport(deletePassportRequest)), HttpStatus.OK);
        }
        catch (Exception error) {
            return new ResponseEntity<>(new ApiResponse(false, error.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

}
