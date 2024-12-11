package org.fmi.streamline.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "channels")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChannelEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private UserEntity owner;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ChannelMembershipEntity> memberships = new HashSet<>();

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MessageEntity> messages = new HashSet<>();

    @Column(nullable = false)
    private boolean deleted = false;

    public ChannelEntity addMembership(ChannelMembershipEntity membership) {
        memberships.add(membership);
        return this;
    }
}
