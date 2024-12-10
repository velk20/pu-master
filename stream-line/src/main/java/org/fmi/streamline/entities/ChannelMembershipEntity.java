package org.fmi.streamline.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "channel_memberships")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChannelMembershipEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "channel_id", nullable = false)
    private ChannelEntity channel;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public enum Role {
        OWNER, ADMIN, GUEST
    }
}
