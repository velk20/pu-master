package org.fmi.streamline.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.fmi.streamline.dtos.channel.ChannelDTO;
import org.fmi.streamline.services.ChannelService;
import org.fmi.streamline.util.AppResponseUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
}
