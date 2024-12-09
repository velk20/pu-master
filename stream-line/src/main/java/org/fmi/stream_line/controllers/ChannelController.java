package org.fmi.stream_line.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.fmi.stream_line.dtos.ChannelDTO;
import org.fmi.stream_line.entities.ChannelEntity;
import org.fmi.stream_line.services.ChannelService;
import org.fmi.stream_line.util.AppResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
