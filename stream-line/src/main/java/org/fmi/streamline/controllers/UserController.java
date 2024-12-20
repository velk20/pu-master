package org.fmi.streamline.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.fmi.streamline.dtos.message.FriendMessageDTO;
import org.fmi.streamline.dtos.message.MessageDTO;
import org.fmi.streamline.dtos.message.SendMessageToFriendDTO;
import org.fmi.streamline.dtos.user.*;
import org.fmi.streamline.entities.UserEntity;
import org.fmi.streamline.exception.EntityNotFoundException;
import org.fmi.streamline.services.UserService;
import org.fmi.streamline.util.AppResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Users Controller", description = "Operations related to users management")
@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping("/{userId}/available-friends")
    @Operation(summary = "Get all users")
    public ResponseEntity<?> getAllUsersAvailableForFriend(@PathVariable("userId") String userId) {
        List<UserDetailDTO> all = this.userService.getAllAvailableFriends(userId);
        return AppResponseUtil.success()
                .withData(all)
                .build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        UserEntity userEntity = this.userService.getById(id);
        List<FriendDTO> list = userEntity.getFriends().stream().map(e->this.modelMapper.map(e, FriendDTO.class)).toList();
        return AppResponseUtil.success()
                .withData(this.modelMapper.map(userEntity, UserDetailDTO.class)
                        .setFriends(list))
                .build();
    }

    @GetMapping("/{userId}/friendMessages/{friendId}")
    @Operation(summary = "Get all messages for friend")
    public ResponseEntity<?> getFriendMessages(@Parameter(description = "ID of the user")@PathVariable String userId,
                                               @Parameter(description = "Id of the friend")@PathVariable String friendId) {
        FriendMessageDTO dto = new FriendMessageDTO(userId, friendId);
        List<MessageDTO> messages = userService.getFriendMessages(dto);
        return AppResponseUtil.success()
                .withData(messages)
                .build();
    }

    @PostMapping("/sendMessage")
    @Operation(summary = "Send message to friend")
    public ResponseEntity<?> sendMessageToFriend(@Valid @RequestBody SendMessageToFriendDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessages)
                    .build();
        }

        MessageDTO messageDTO = this.userService.sendMessageToFriend(dto);

        return AppResponseUtil.success()
                .withData(messageDTO)
                .build();
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update profile")
    public ResponseEntity<?> updateProfile(@PathVariable("userId") String userId,
                                           @Valid @RequestBody UpdateProfileDTO dto,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessages)
                    .build();
        }

        UserDetailDTO updateProfile = this.userService.updateProfile(userId, dto);
        return AppResponseUtil.success()
                .withMessage("Profile updated successfully")
                .withData(updateProfile)
                .build();
    }

    @PutMapping("/change-password")
    @Operation(summary = "Update user password")
    public ResponseEntity<?> updateUserPassword(@Valid @RequestBody ChangeUserPasswordDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessages)
                    .build();
        }

        this.userService.changePassword(dto);
        return AppResponseUtil.success()
                .withMessage("Password updated successfully")
                .build();
    }

    @PutMapping("/addFriend")
    @Operation(summary = "Add User friend")
    public ResponseEntity<?> addUserFriend(@Valid @RequestBody AddOrRemoveFriendDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessages)
                    .build();
        }
        UserDetailDTO userDTO = this.userService.addFriend(dto);
        return AppResponseUtil.success()
                .withData(userDTO)
                .withMessage("Friend successfully added")
                .build();
    }

    @PutMapping("/removeFriend")
    @Operation(summary = "Remove friend from user's friend list")
    public ResponseEntity<?> removeFriend(@Valid @RequestBody AddOrRemoveFriendDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessages)
                    .build();
        }
        UserDetailDTO userDTO = this.userService.removeFriend(dto);
        return AppResponseUtil.success()
                .withData(userDTO)
                .withMessage("Friend successfully removed")
                .build();
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user by ID")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId) {
        this.userService.deleteUser(userId);
        return AppResponseUtil
                .success()
                .withMessage("User was deleted!")
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                .logStackTrace(Arrays.toString(ex.getStackTrace()))
                .withMessage(ex.getMessage())
                .build();
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex) {
        return AppResponseUtil.error(HttpStatus.NOT_FOUND)
                .logStackTrace(Arrays.toString(ex.getStackTrace()))
                .withMessage(ex.getMessage())
                .build();
    }
}
