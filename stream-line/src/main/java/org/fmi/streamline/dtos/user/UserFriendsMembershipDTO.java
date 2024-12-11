package org.fmi.streamline.dtos.user;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendsMembershipDTO extends UserMembershipDTO {
    private List<UserMembershipDTO> friends = new ArrayList<>();
}
