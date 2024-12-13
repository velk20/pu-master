import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {ChannelService} from "../../services/channel.service";
import {AuthService} from "../../services/auth.service";
import {
  Channel,
  FriendMessage,
  Message,
  NewChannel,
  NewChannelName,
  NewMessage,
  UserFriendMessage
} from "../../models/channel";
import {UserService} from "../../services/user.service";
import {AddFriend, Friend, User} from "../../models/user";
import {ToastrService} from "ngx-toastr";
import { Modal } from 'bootstrap';
import Swal from 'sweetalert2';

declare var bootstrap: any; // Add this line to declare bootstrap globally

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    DatePipe,
    NgIf
  ],
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  channels: Channel[] = [];

  friends: Friend[] = [];

  messages:Message[]  = [];

  selectedChanelId: string = '';
  selectedFriendId: string = '';
  selectedChatName: string = 'Channel Name';
  newMessage: string = '';
  newChannelName: string = '';
  currentLoggedUsername: string = '';
  currentLoggedUserId: string = '';

  availableFriends: Friend[] = [];
  searchFriendQuery = '';
  searchResults:Friend[] = [];
  selectedFriend: Friend | null = null;

  constructor(private channelService: ChannelService,
              private authService: AuthService,
              private userService: UserService,
              private toastr: ToastrService,) {
  }

  ngOnInit(): void {
    let user = this.authService.getUserFromJwt();
    this.currentLoggedUsername = user.username;
    this.currentLoggedUserId = user.id;

    this.channelService.getChannelsForUser(user.id).subscribe(res => {
      this.channels = res.data as Channel[];
      if (this.channels.length > 0) {
        this.selectChannel(this.channels[0])
      }
    });
    this.userService.getUserById(user.id).subscribe(res => {
      const user = res.data as User;
      this.friends = user.friends
    });
    this.userService.getAllAvailableUserFriends(user.id).subscribe(res => {
      console.log(res)
      const friends = res.data as Friend[];
      this.availableFriends = friends
    })
    }

  selectChannel(channel: Channel) {
    this.selectedFriendId = '';
    this.selectedChanelId = channel.id;
    this.selectedChatName = channel.name;

    this.messages = channel.messages.sort((a, b) => {
      return new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime();
    });
  }

  selectFriend(friend: Friend) {
    this.selectedChanelId = '';
    this.selectedFriendId = friend.id;
    this.selectedChatName = friend.username;

    let getMessages: UserFriendMessage = {
      friendId: this.selectedFriendId,
      userId: this.currentLoggedUserId,
    };

    this.channelService.getFriendMessages(getMessages).subscribe(res => {
      this.messages = res.data as Message[];
      this.messages = this.messages.sort((a, b) =>
        new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime()
      );
    })
  }

  sendMessage() {
    if (this.newMessage.trim()) {
      if (this.selectedChanelId){
        this.sendMessageToChannel()
      } else {
        this.sendMessageToFriend()
      }
      this.newMessage = '';
    }
  }

  sendMessageToFriend(){
      const friendMessage: FriendMessage = {
        friendId: this.selectedFriendId,
        senderId: this.currentLoggedUserId,
        message: this.newMessage
      }

    this.channelService.sendMessage(friendMessage).subscribe(res => {
        this.messages.push(res.data as Message);
        this.messages = this.messages.sort((a, b) =>
          new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime()
        );
      },
      error => {
        this.toastr.error(error.message);
      });
  }

  sendMessageToChannel(){
    const newMessage: NewMessage={
      message: this.newMessage,
      username: this.currentLoggedUsername,
      channelId: this.selectedChanelId
    }

    this.channelService.addMessage(newMessage).subscribe(res => {
        const updatedChannel = res.data as Channel; // The updated channel returned by the API
        const index = this.channels.findIndex(channel => channel.id === updatedChannel.id);

        if (index !== -1) {
          // Update the channel in the channels array
          this.channels[index] = updatedChannel;
        }

        // Optionally reassign the messages if this is the currently selected channel
        if (this.selectedChatName === updatedChannel.name) {
          this.messages = updatedChannel.messages.sort((a, b) =>
            new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime()
          );
        }

      },
      error => {
        this.toastr.error(error.message);
      }
    );
  }


  openAddFriendModal() {
    const modalElement = document.getElementById('addFriendModal');
    if (modalElement) {
      const modal = new Modal(modalElement)
      modal.show();
    }
  }

  searchFriends() {
    this.searchResults = this.availableFriends.filter((friend) =>
      friend.username.toLowerCase().includes(this.searchFriendQuery.toLowerCase())
    );
  }

  selectFriendFromSearch(friend: Friend) {
    this.selectedFriend = friend;
    this.searchFriendQuery = friend.username;
    this.searchResults = [];
  }

  addFriend() {
    if (this.selectedFriend) {
      const newFriend:AddFriend={
        requesterUsername: this.currentLoggedUsername,
        friendUsername: this.selectedFriend.username
      }
      this.userService.addFriend(newFriend).subscribe(res => {
        const currentUser = res.data as User;
        this.friends = currentUser.friends;
      })
      this.selectedFriend = null;
      this.searchFriendQuery = '';

      const modalElement = document.getElementById('addFriendModal');
      if (modalElement) {
        const modal = Modal.getInstance(modalElement);
        if (modal) {
          modal.hide();
        } else {
          console.error('Modal instance not found. Ensure it is initialized.');
        }
      }
    }
  }

  createChannel() {
    Swal.fire({
      title: 'Create New Channel',
      input: 'text',
      inputLabel: 'Channel Name',
      inputPlaceholder: 'Enter the channel name',
      showCancelButton: true,
      confirmButtonText: 'Create',
      cancelButtonText: 'Cancel',
      inputValidator: (value) => {
        if (!value) {
          return 'You need to write a channel name!';
        }
        return null;
      },
    }).then((result) => {
      if (result.isConfirmed) {
        const channelName = result.value;
        const newChannel:NewChannel={
          name: channelName,
          ownerUsername: this.currentLoggedUsername,
        }
        this.channelService.createChannel(newChannel).subscribe(res => {
          const newChannel = res.data as Channel; // The updated channel returned by the API
          this.channels.push(newChannel);
        })
        Swal.fire('Success', `Channel "${channelName}" has been created!`, 'success');
      }
    });
  }

  onDeleteChannel(id: string) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'Do you really want to delete this channel? This action cannot be undone.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Yes, delete it!',
      cancelButtonText: 'Cancel',
    }).then((result) => {
      if (result.isConfirmed) {
        // Perform the delete action (e.g., call an API to delete the channel)
        this.deleteChannelById(id); // Example function to handle deletion

        Swal.fire(
          'Deleted!',
          'The channel has been deleted.',
          'success'
        );
      }
    });
  }

  // Example function to delete the channel (replace this with your actual logic)
  deleteChannelById(id: string) {
    this.channelService.deleteChannel(id).subscribe(res => {
      this.toastr.info(res.message);
      this.channels = this.channels.filter(channel => channel.id !== id);
    },
      (error) => {
        this.toastr.error('Failed to delete the channel. Please try again.', 'Error');
      })
  }

  onRenameChannel(id: string) {
    Swal.fire({
      title: 'Enter new channel name',
      input: 'text',
      inputPlaceholder: 'New channel name',
      showCancelButton: true,
      confirmButtonText: 'Rename',
      cancelButtonText: 'Cancel',
      inputValidator: (value) => {
        if (!value) {
          return 'You need to enter a name!';
        }

        return;
      }
    }).then((result) => {
      if (result.isConfirmed && result.value) {
        const newName = result.value;
        const updateChannel:NewChannelName ={
          id: id,
          name: newName,
        }
        this.channelService.renameChannel(updateChannel).subscribe(res => {
          const newChannel = res.data as Channel;
          const channelIndex = this.channels.findIndex(channel => channel.id === newChannel.id);

          if (channelIndex !== -1) {
            this.channels[channelIndex] = newChannel;
          }
        }, error => {
          this.toastr.error(error.error.message);
        })
      }
    });
  }

}
