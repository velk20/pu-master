package org.fmi.stream_line.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDTO {
    private String id;
    private String content;
    private String authorId;
    private String channelId;
    private String receiverId;
    private LocalDateTime timestamp;
    private boolean deleted;
}
