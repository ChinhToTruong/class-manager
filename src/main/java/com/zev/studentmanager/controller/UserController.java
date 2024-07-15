package com.zev.studentmanager.controller;

import com.zev.studentmanager.dto.request.UpdateUserInfoRequest;
import com.zev.studentmanager.dto.response.ApiResponse;
import com.zev.studentmanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Log4j
@Tag(name = "User Controller")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
          summary = "get list user",
          description = "Api get list user"
    )
    @GetMapping
    public ResponseEntity<?> getUsers(Pageable pageable) {
        log.info("----------- get users -----------");

        return ApiResponse.build()
                .withMessage("get users successfully")
                .withData(userService.getUsers(pageable))
                .toEntity();
    }

    @Operation(
            summary = "update user",
            description = "Api update user information",
            parameters = {@Parameter(name = "id", description = "the id of the user",example = "1")}
    )
    @PutMapping("/update/{id}")
    public void updateUserInformation(
            @Valid @RequestBody UpdateUserInfoRequest request,
            @PathVariable(name = "id") @Min(1) Long id
    ){

        log.info("---------- update user -------------");

        try{
            userService.updateUserInformation(request, id);
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Operation(
            summary = "get active user",
            description = "Api get active user information"
    )
    @GetMapping("/active")
    public ResponseEntity<?> getActiveUser(Pageable pageable) {
        log.info("----------- get users -----------");

        return ApiResponse.build()
                .withMessage("get users successfully")
                .withData(userService.getActiveUsers(pageable))
                .toEntity();
    }

    @Operation(
            summary ="soft-delete user",
            description = "Api soft-delete user information"
    )
    @DeleteMapping("/soft-delete/{id}")
    public ResponseEntity<?> deleteSoftUserById(@PathVariable(name = "id") @Min(1) Long id) {
        log.info("---------- delete soft user by id -------------");

        try{
            userService.deleteSoftUserById(id);
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return ApiResponse.build()
                .withMessage("soft-delete users successfully")
                .toEntity();
    }


    @Operation(
            summary = "hard-delete user",
            description = "Api hard-delete user information"
    )
    @DeleteMapping("/hard-delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") @Min(1) Long id) {
        log.info("---------- delete user by id -------------");

        try{
            userService.deleteUser(id);
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return ApiResponse.build()
               .withMessage("hard-delete users successfully")
               .toEntity();
    }


    @Operation(
            summary = "get user by id",
            description = "Api get user information"
    )
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name = "id") @Min(1) Long id) {
        log.info("---------- get user by id -------------");

        return ApiResponse.build()
                .withMessage("get user successfully")
                .withData(userService.getUserById(id))
                .toEntity();

    }

}
