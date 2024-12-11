package org.fmi.streamline.dtos.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserMembershipDTO {
    private String id;
    private String username;
    private String role;
}
