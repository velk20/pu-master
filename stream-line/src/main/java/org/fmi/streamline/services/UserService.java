package org.fmi.streamline.services;

import org.fmi.streamline.dtos.user.AddFriendDTO;
import org.fmi.streamline.dtos.user.UserFriendsMembershipDTO;
import org.fmi.streamline.entities.UserEntity;
import org.fmi.streamline.exception.EntityNotFoundException;
import org.fmi.streamline.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserService(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public UserEntity getByUsername(String username) {
        return this.userRepository.findByUsernameAndIsActiveTrue(username)
                .orElseThrow(()-> new EntityNotFoundException("User with username " + username + " not found"));
    }

    public UserEntity getById(String id) {
        return this.userRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(()-> new EntityNotFoundException("User with id " + id + " not found"));
    }

    public UserFriendsMembershipDTO addFriend(AddFriendDTO dto) {
        if (dto.getFriendUsername().equals(dto.getRequesterUsername())) {
            throw new IllegalArgumentException("User can't add themselves as friend");
        }

        UserEntity requesterFriend = this.getByUsername(dto.getRequesterUsername());
        UserEntity newFriend = this.getByUsername(dto.getFriendUsername());

        if (requesterFriend.getFriends().contains(newFriend)
                || newFriend.getFriends().contains(requesterFriend)) {
            throw new IllegalArgumentException("User already is friend");
        }

        requesterFriend.addFriend(newFriend);
        newFriend.addFriend(requesterFriend);

        this.userRepository.save(requesterFriend);
        this.userRepository.save(newFriend);

        return this.modelMapper.map(requesterFriend, UserFriendsMembershipDTO.class);
    }
}
