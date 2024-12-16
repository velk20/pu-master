import {AfterViewInit, Component, OnInit} from '@angular/core';
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
  NewMessage, UserToChannel,
  UserFriendMessage
} from "../../models/channel";
import {UserService} from "../../services/user.service";
import {AddFriend, Friend, User} from "../../models/user";
import {ToastrService} from "ngx-toastr";
import {Dropdown, Modal} from 'bootstrap';
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
export class DashboardComponent implements OnInit, AfterViewInit {
  channels: Channel[] = [];
  friends: Friend[] = [];
  messages:Message[]  = [];

  selectedChanelId: string = '';
  selectedFriendId: string = '';
  selectedChatName: string = 'Channel Name';
  newMessage: string = '';
  currentLoggedUsername: string = '';
  currentLoggedUserId: string = '';

  availableFriends: Friend[] = [];
  selectedFriend: Friend | null = null;

  constructor(private channelService: ChannelService,
              private authService: AuthService,
              private userService: UserService,
              private toastr: ToastrService,) {
  }

  ngAfterViewInit() {
    const dropdownElements = document.querySelectorAll('.dropdown-toggle');
    dropdownElements.forEach((dropdown) => {
      new Dropdown(dropdown);
    });
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

  addFriend() {
    Swal.fire({
      title: 'Add Friend',
      html: `
    <input id="friend-username" class="swal2-input" placeholder="Enter friend's username">
    <ul id="autocomplete-list" style="list-style: none; padding: 0; margin: 0; max-height: 150px; overflow-y: auto;"></ul>
  `,
      showCancelButton: true,
      confirmButtonText: 'Add',
      cancelButtonText: 'Cancel',
      didOpen: () => {
        const input = document.getElementById('friend-username') as HTMLInputElement;
        const list = document.getElementById('autocomplete-list') as HTMLUListElement;

        input.addEventListener('input', () => {
          const query = input.value.toLowerCase();
          list.innerHTML = ''; // Clear previous results

          if (query) {
            const matches = this.availableFriends.filter((user) => user.username.toLowerCase().includes(query));
            matches.forEach((user) => {
              const listItem = document.createElement('li');
              listItem.textContent = user.username;
              listItem.style.cursor = 'pointer';
              listItem.style.padding = '5px 10px';
              listItem.style.border = '1px solid #ddd';
              listItem.style.marginBottom = '2px';

              listItem.addEventListener('click', () => {
                input.value = user.username;
                list.innerHTML = ''; // Clear the list after selection
              });

              list.appendChild(listItem);
            });
          }
        });
      },
      preConfirm: () => {
        const input = document.getElementById('friend-username') as HTMLInputElement;
        if (!input.value) {
          Swal.showValidationMessage('Please enter a username.');
        } else {
          return input.value;
        }

        return ;
      },
    }).then((result) => {
      if (result.isConfirmed) {
        const username = result.value;
        // Perform the action to add the friend with the provided username
        const newFriend:AddFriend={
          requesterUsername: this.currentLoggedUsername,
          friendUsername: username
        }
        this.userService.addFriend(newFriend).subscribe(res => {
          const currentUser = res.data as User;
          this.friends = currentUser.friends;
          Swal.fire('Success', `You have added "${username}" as a friend!`, 'success');
        })
        this.selectedFriend = null;

      }
    });
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

          this.selectedChatName = updateChannel.name;
          Swal.fire('Success', `Channel was renamed successfully`, 'success');
        }, error => {
          this.toastr.error(error.error.message);
        })
      }
    });
  }

  onRemoveFriend(friendUsername: string) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'Do you really want to remove your friend? This action cannot be undone.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Remove',
      cancelButtonText: 'Cancel',
    }).then((result) => {
      if (result.isConfirmed) {
        // Perform the delete action (e.g., call an API to delete the channel)
        this.removeFriend(this.currentLoggedUsername,friendUsername); // Example function to handle deletion

        Swal.fire(
          'Removed!',
          `Friend with username: ${friendUsername} was removed!`,
          'success'
        );
      }
    });

  }

  private removeFriend(currentLoggedUsername: string, friendUsername: string) {
    const removeFriend: AddFriend = {
      friendUsername: friendUsername,
      requesterUsername: currentLoggedUsername
    }

    this.userService.removeFriend(removeFriend).subscribe(res => {
        const user = res.data as User;
      const updatedFriends: Friend[] = user.friends;
      this.friends = updatedFriends;

    }, error => {
      this.toastr.error(error.error.message);
    })
  }

  onAddUserToChannel(id: string) {
    Swal.fire({
      title: 'Add user to channel',
      html: `
    <input id="friend-username" class="swal2-input" placeholder="Enter user's username">
    <ul id="autocomplete-list" style="list-style: none; padding: 0; margin: 0; max-height: 150px; overflow-y: auto;"></ul>
  `,
      showCancelButton: true,
      confirmButtonText: 'Add',
      cancelButtonText: 'Cancel',
      didOpen: () => {
        const input = document.getElementById('friend-username') as HTMLInputElement;
        const list = document.getElementById('autocomplete-list') as HTMLUListElement;

        input.addEventListener('input', () => {
          const query = input.value.toLowerCase();
          list.innerHTML = ''; // Clear previous results

          if (query) {
            this.channelService.getAvailableUsersForChannel(id).subscribe(res => {
              const usersAvailableForChannel = res.data as User[];
              const matches = usersAvailableForChannel.filter((user) => user.username.toLowerCase().includes(query));
              matches.forEach((user) => {
                const listItem = document.createElement('li');
                listItem.textContent = user.username;
                listItem.style.cursor = 'pointer';
                listItem.style.padding = '5px 10px';
                listItem.style.border = '1px solid #ddd';
                listItem.style.marginBottom = '2px';

                listItem.addEventListener('click', () => {
                  input.value = user.username;
                  list.innerHTML = ''; // Clear the list after selection
                });

                list.appendChild(listItem);
              });
            })
          }
        });
      },
      preConfirm: () => {
        const input = document.getElementById('friend-username') as HTMLInputElement;
        if (!input.value) {
          Swal.showValidationMessage('Please enter a username.');
        } else {
          return input.value;
        }

        return ;
      },
    }).then((result) => {
      if (result.isConfirmed) {
        const username = result.value;
        // Perform the action to add the friend with the provided username
        const newUserToChannel:  UserToChannel={
          channelId: id,
          username: username,
        }
        this.channelService.addUserTOChannel(newUserToChannel).subscribe(res => {
          let updatedChannel = res.data as Channel;
          const channelIndex = this.channels.findIndex(channel => channel.id === updatedChannel.id);

          if (channelIndex !== -1) {
            this.channels[channelIndex] = updatedChannel;
          }

          Swal.fire('Success', `You have added "${username}" to the channel: ${updatedChannel.name}!`, 'success');
        },error =>  {
          this.toastr.error(error.error.message);
        })
      }
    });
  }

  onLeaveChannel(channelId: string) {
    //TODO
  }
}
