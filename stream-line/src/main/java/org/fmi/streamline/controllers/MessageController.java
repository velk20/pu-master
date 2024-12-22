package org.fmi.streamline.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.fmi.streamline.dtos.message.EditMessageDTO;
import org.fmi.streamline.dtos.message.MessageDTO;
import org.fmi.streamline.services.MessageService;
import org.fmi.streamline.util.AppResponseUtil;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Message Controller", description = "Operations related to messages management")
@RestController
@RequestMapping("/api/v1/messages")
@SecurityRequirement(name = "bearerAuth")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PutMapping("/{messageId}")
    @Operation(summary = "Edit message")
    public ResponseEntity<?> editMessage(@PathVariable("messageId") String messageId,
                                         @RequestBody @Valid EditMessageDTO dto,
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

        MessageDTO editedMessage = this.messageService.editMessage(messageId, dto);
        return AppResponseUtil.success()
                .withData(editedMessage)
                .withMessage("Message edited successfully")
                .build();
    }

    @DeleteMapping
    @Operation(summary = "Delete message")
    public ResponseEntity<?> deleteMessage(@RequestParam String messageId, @RequestParam String userId) {

        this.messageService.deleteMessage(messageId, userId);

        return AppResponseUtil.success()
                .withMessage("Message deleted successfully")
                .build();
    }
}
