package org.fmi.streamline.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer age;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime lastModified;
    private String role;
    private List<FriendDTO> friends;

    public UserDetailDTO setFriends(List<FriendDTO> friends) {
        this.friends = friends;
        return this;
    }
}
