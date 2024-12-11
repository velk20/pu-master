package org.fmi.streamline.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.fmi.streamline.dtos.channel.AddMessageToChannelDTO;
import org.fmi.streamline.dtos.channel.AddUserToChannelDTO;
import org.fmi.streamline.dtos.channel.ChannelDTO;
import org.fmi.streamline.dtos.channel.UpdateUserRoleToChannelDTO;
import org.fmi.streamline.exception.EntityNotFoundException;
import org.fmi.streamline.services.ChannelService;
import org.fmi.streamline.util.AppResponseUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Channels Controller", description = "Operations related to channels management")
@RestController
@RequestMapping("/api/v1/channels")
@SecurityRequirement(name = "bearerAuth")
public class ChannelController {
    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get channels")
    public ResponseEntity<?> getChannelsByUserMember(@Parameter(description = "ID of the user")@PathVariable String userId) {
        List<ChannelDTO> allChannelsByMember = channelService.getAllChannelsByMember(userId);

        return AppResponseUtil.success()
                .withData(allChannelsByMember)
                .build();
    }

    @GetMapping
    @Operation(summary = "Get all channels")
    public ResponseEntity<?> getAllChannels() {
        List<ChannelDTO> all = channelService.getAll();

        return AppResponseUtil.success()
                .withData(all)
                .build();
    }

    @PutMapping("/addUser")
    @Operation(summary = "Add new user to the channel")
    public ResponseEntity<?> addUserToChannel(@Valid @RequestBody AddUserToChannelDTO dto,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessages)
                    .build();
        }
        ChannelDTO channelDTO = channelService.addNewUser(dto);

        return AppResponseUtil.success()
                .withData(channelDTO)
                .withMessage("Successfully added new user to the channel")
                .build();
    }

    @PostMapping("/addMessage")
    @Operation(summary = "Add new message to channel")
    public ResponseEntity<?> addMessageToChannel(@Valid @RequestBody AddMessageToChannelDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessages)
                    .build();
        }

        ChannelDTO channelDTO = this.channelService.addMessageToChannel(dto);

        return AppResponseUtil.success()
                .withMessage("Successfully created message in channel: " + channelDTO.getName())
                .withData(channelDTO)
                .build();
    }

    @PutMapping("/userRole")
    @Operation(summary = "Update user role in channel")
    public ResponseEntity<?> updateUserRoleToChannel(@Valid @RequestBody UpdateUserRoleToChannelDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessages)
                    .build();
        }

        ChannelDTO channelDTO = this.channelService.updateUserRoleInChannel(dto);

        return AppResponseUtil.success()
                .withMessage("Successfully updated user role in channel")
                .withData(channelDTO)
                .build();
    }

    @PostMapping
    @Operation(summary = "Create channel")
    public ResponseEntity<?> createChannel(@Valid @RequestBody ChannelDTO channelDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());

            return AppResponseUtil.error(HttpStatus.BAD_REQUEST)
                    .withErrors(errorMessages)
                    .build();
        }

        ChannelDTO newChannel = channelService.createChannel(channelDTO);
        return AppResponseUtil.created()
                .withData(newChannel)
                .withMessage("Channel created successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete channel by id")
    public ResponseEntity<?> deleteChannelById(@Parameter(description = "ID of the channel")@PathVariable String id) {
        channelService.deleteChannel(id);

        return AppResponseUtil.success()
                .withMessage("Channel was deleted.")
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