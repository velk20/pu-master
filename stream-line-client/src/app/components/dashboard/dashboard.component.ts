import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
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
  UserFriendMessage,
  UserToChannel
} from "../../models/channel";
import {UserService} from "../../services/user.service";
import {AddFriend, Friend, User, UserMembership, UserRoleToChannel} from "../../models/user";
import {ToastrService} from "ngx-toastr";
import {Dropdown, Modal} from 'bootstrap';
import Swal from 'sweetalert2';
import {JwtPayload} from "../../models/auth";

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
  isCurrentLoggedUserOwner: boolean = false;
  isCurrentLoggedUserAdmin: boolean = false;

  availableFriends: Friend[] = [];
  selectedFriend: Friend | null = null;

  channelsMembersList: UserMembership[] = [];

  private modalInstance: Modal | null = null; // Modal instance

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
    const user: JwtPayload = this.authService.getUserFromJwt();
    this.currentLoggedUsername = user.username;
    this.currentLoggedUserId = user.id;

    this.getUserChannels(this.currentLoggedUserId);
    this.getUserFriendsList(this.currentLoggedUserId);
    }

  private getUserChannels(userId:string) {
    this.channelService.getChannelsForUser(userId).subscribe(res => {
      this.channels = res.data as Channel[];
      if (this.channels.length > 0) {
        this.onSelectChannel(this.channels[0])
      }
    }, error => {
      Swal.fire('Error', error.error.message, 'error');
    });
  }

  private getAvailableFriendsForUser(userId: string) {
    this.userService.getAllAvailableUserFriends(userId).subscribe(res => {
      this.availableFriends = res.data as Friend[]
    }, error => {
      Swal.fire('Error', error.error.message, 'error');
    });
  }

  private getUserFriendsList(userId: string) {
    this.userService.getUserById(userId).subscribe(res => {
      const user = res.data as User;
      this.friends = user.friends
    }, error => {
      Swal.fire('Error', error.error.message, 'error');
    });
  }

  onSelectChannel(channel: Channel) {
    this.selectedFriendId = '';
    this.selectedChanelId = channel.id;
    this.selectedChatName = channel.name;

    this.messages = channel.messages.sort((a, b) => {
      return new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime();
    });
  }

  resetChat(){
    this.selectedFriendId = '';
    this.selectedChanelId = '';
    this.selectedChatName = 'Channel Name';
    this.messages = [];
  }

  onSelectFriend(friend: Friend) {
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
    },error => {
      this.toastr.error(error.error.message);
    })
  }

  onSendMessage() {
    if (this.newMessage.trim()) {
      if (this.selectedChanelId){
        this.sendMessageToChannel()
      } else {
        this.sendMessageToFriend()
      }
      this.newMessage = '';
    }
  }

  private sendMessageToFriend(){
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

  private sendMessageToChannel(){
    const newMessage: NewMessage={
      message: this.newMessage,
      username: this.currentLoggedUsername,
      channelId: this.selectedChanelId
    }

    this.channelService.addMessage(newMessage).subscribe(res => {
        const updatedChannel = res.data as Channel; // The updated channel returned by the API
        const index = this.channels.findIndex(channel => channel.id === updatedChannel.id);

        if (index !== -1) {
          this.channels[index] = updatedChannel;
        }

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

  onAddFriend() {
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
            this.getAvailableFriendsForUser(this.currentLoggedUserId);
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
        this.addFriend(result.value);
      }
    }).finally(()=>{
      this.availableFriends = [];
    });
  }

  private addFriend(friendUsername: string){
    // Perform the action to add the friend with the provided username
    const newFriend:AddFriend={
      requesterUsername: this.currentLoggedUsername,
      friendUsername: friendUsername
    }
    this.userService.addFriend(newFriend).subscribe(res => {
      const currentUser = res.data as User;
      this.friends = currentUser.friends;
      Swal.fire('Success', `You have added "${friendUsername}" as a friend!`, 'success');
    }, error => {
      Swal.fire('Error', error.error.message, 'error');
    })
    this.selectedFriend = null;

  }

  onCreateChannel() {
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
        this.createChannel(result.value)
      }
    });
  }

  private createChannel(channelName: string){
    const newChannel:NewChannel={
      name: channelName,
      ownerUsername: this.currentLoggedUsername,
    }
    this.channelService.createChannel(newChannel).subscribe(res => {
      const newChannel = res.data as Channel; // The updated channel returned by the API
      this.channels.push(newChannel);
      Swal.fire('Success', `Channel "${channelName}" has been created!`, 'success');
    }, error => {
      Swal.fire('Error', error.error.message, 'error');
    })
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
        this.deleteChannelById(id);
      }
    });
  }

  private deleteChannelById(id: string) {
    this.channelService.deleteChannel(id).subscribe(res => {
      this.toastr.info(res.message);
      this.channels = this.channels.filter(channel => channel.id !== id);
        this.resetChat();

        Swal.fire(
          'Deleted!',
          'The channel has been deleted.',
          'success'
        );
    },
      (error) => {
        Swal.fire('Error', error.error.message, 'error');
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
        this.renameChannel(id, result.value);
      }
    });
  }

  private renameChannel(channelId: string, newChannelName:string){
    const updateChannel:NewChannelName ={
      id: channelId,
      name: newChannelName,
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
      Swal.fire('Error', error.error.message, 'error');
    })
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
        this.removeFriend(this.currentLoggedUsername,friendUsername);
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
      Swal.fire(
        'Removed!',
        `Friend with username: ${friendUsername} was removed!`,
        'success'
      );
    }, error => {
      Swal.fire('Error', error.error.message, 'error');
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
       this.addUserToChannel(result.value, id);
      }
    });
  }

  private addUserToChannel(username: string, id: string){
    const newUserToChannel:  UserToChannel={
      channelId: id,
      username: username,
    }
    this.channelService.addOrRemoveUserFromChannel(newUserToChannel).subscribe(res => {
      let updatedChannel = res.data as Channel;
      const channelIndex = this.channels.findIndex(channel => channel.id === updatedChannel.id);

      if (channelIndex !== -1) {
        this.channels[channelIndex] = updatedChannel;
      }

      Swal.fire('Success', `You have added "${username}" to the channel: ${updatedChannel.name}!`, 'success');
    },error =>  {
      Swal.fire('Error', error.error.message, 'error');
    })
  }

  onLeaveChannel(channelId: string) {
    Swal.fire({
      title: 'Are you sure?',
      text: 'Do you really want to leave from this channel? Action cannot be undone.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Leave',
      cancelButtonText: 'Cancel',
    }).then((result) => {
      if (result.isConfirmed) {
        this.leaveChannel(this.currentLoggedUsername,channelId);
      }
    });

  }

  private leaveChannel(currentLoggedUsername: string, channelId: string) {
    this.channelService
      .addOrRemoveUserFromChannel({channelId: channelId, username: currentLoggedUsername, remove: true})
      .subscribe(res => {
        const updatedChannel = res.data as Channel;
        const channelIndex = this.channels.findIndex(channel => channel.id === updatedChannel.id);
        if (channelIndex !== -1) {
          this.channels.splice(channelIndex, 1);
          if (this.channels.length > 0){
            this.onSelectChannel(this.channels[0]);
          } else {
            this.resetChat();
          }

          Swal.fire(
            'Left!',
            `You left from channel with name: ${this.selectedChatName}!`,
            'success'
          );
        }
      }, error => {
        Swal.fire('Error', `${error.error.message}`, 'error');
      })
  }

  private removeUserFromChannel(username: string, channelId: string) {
    this.channelService
      .addOrRemoveUserFromChannel({channelId: channelId, username: username, remove: true})
      .subscribe(res => {
        const updatedChannel = res.data as Channel;
        const channelIndex = this.channels.findIndex(channel => channel.id === updatedChannel.id);
        if (channelIndex !== -1) {
          this.channels[channelIndex] = updatedChannel;
            this.onSelectChannel(this.channels[channelIndex]);

          Swal.fire(
            'Removed!',
            `User with username: ${username} was removed from channel with name: ${updatedChannel.name}!`,
            'success'
          );
        }
      }, error => {
        Swal.fire('Error', `${error.error.message}`, 'error');
      })
  }

  onAddFriendToChannel(username: string) {
    Swal.fire({
      title: 'Add friend to channel',
      html: `
    <input id="channel-name" class="swal2-input" placeholder="Enter channel's name">
    <ul id="autocomplete-list" style="list-style: none; padding: 0; margin: 0; max-height: 150px; overflow-y: auto;"></ul>
  `,
      showCancelButton: true,
      confirmButtonText: 'Add',
      cancelButtonText: 'Cancel',
      didOpen: () => {
        const input = document.getElementById('channel-name') as HTMLInputElement;
        const list = document.getElementById('autocomplete-list') as HTMLUListElement;

        input.addEventListener('input', () => {
          const query = input.value.toLowerCase();
          list.innerHTML = ''; // Clear previous results

          if (query) {
            const availableChannels:Channel[] = this.channels
              .filter(channel=> channel.ownerUsername == this.currentLoggedUsername && channel.memberships.filter(member => member.username != username));

              const matches = availableChannels.filter((channel) => channel.name.toLowerCase().includes(query));
              matches.forEach((channel) => {
                const listItem = document.createElement('li');
                listItem.textContent = channel.name;
                listItem.style.cursor = 'pointer';
                listItem.style.padding = '5px 10px';
                listItem.style.border = '1px solid #ddd';
                listItem.style.marginBottom = '2px';

                listItem.addEventListener('click', () => {
                  input.value = channel.name;
                  list.innerHTML = ''; // Clear the list after selection
                });

                list.appendChild(listItem);
              });
          }
        });
      },
      preConfirm: () => {
        const input = document.getElementById('channel-name') as HTMLInputElement;
        if (!input.value) {
          Swal.showValidationMessage('Please enter a channel name.');
        } else {
          return input.value;
        }
        return ;
      },
    }).then((result) => {
      if (result.isConfirmed) {
        this.addFriendToChannel(username, result.value);
      }
    });
  }

  private addFriendToChannel(friendUsername: string, channelName: string){
    const selectedChannels = this.channels.filter(c => c.name === channelName);
    if (selectedChannels.length === 0){
      Swal.fire('Error', `Channel with name: ${channelName} does not exist!`, 'error');
      return;
    }

    const newUserToChannel:  UserToChannel={
      channelId: selectedChannels[0].id,
      username: friendUsername,
    }
    this.channelService.addOrRemoveUserFromChannel(newUserToChannel).subscribe(res => {
      let updatedChannel = res.data as Channel;
      const channelIndex = this.channels.findIndex(channel => channel.id === updatedChannel.id);

      if (channelIndex !== -1) {
        this.channels[channelIndex] = updatedChannel;
      }

      Swal.fire('Success', `You have added "${friendUsername}" to the channel: ${updatedChannel.name}!`, 'success');
    },error =>  {
      Swal.fire('Error', error.error.message, 'error');
    })
  }

  isOwnerOfChannel(channel: Channel) {
    return this.currentLoggedUsername === channel.ownerUsername;
  }
  isAdminOfChannel(channel: Channel) {
    return channel.memberships.some(member =>
      member.username === this.currentLoggedUsername
      && member.role === 'ADMIN');
  }

  onViewMembers(channelId: string) {
    let channel = this.channels.filter(c => c.id === channelId)[0];
    this.channelsMembersList = channel.memberships
    this.isCurrentLoggedUserOwner = channel.ownerUsername === this.currentLoggedUsername;
    this.isCurrentLoggedUserAdmin = channel.memberships
      .filter(m => m.username === this.currentLoggedUsername && m.role == "ADMIN").length > 0;
    this.showUserModal();
  }

  showUserModal() {
    // Open the modal
    const modalElement = document.getElementById('viewMembersModal');
    if (modalElement) {
      this.modalInstance = new Modal(modalElement);
      this.modalInstance.show();
    }
  }

  closeModal() {
    if (this.modalInstance) {
      this.modalInstance.hide();
      this.modalInstance = null; // Reset the instance
    }
  }

  toggleAdmin(member: UserMembership) {
    const newRoleUser : UserRoleToChannel= {
      role: member.role == 'ADMIN' ? 'GUEST' : 'ADMIN',
      channelId: this.selectedChanelId,
      username: member.username
    }
    this.channelService.updateUserRoleForChannel(newRoleUser).subscribe(res => {
      const updatedChannel = res.data as Channel;
      const channelIndex = this.channels.findIndex(channel => channel.id === updatedChannel.id);

      if (channelIndex !== -1) {
        this.channels[channelIndex] = updatedChannel;
      }
      this.toastr.info('Role updated successfully');
      this.closeModal()
    },error =>{
        this.toastr.error(error.error.message);
    })
  }

  removeUser(username: string) {
    Swal.fire({
      title: 'Are you sure?',
      text: `Do you really want to remove this member from '${this.selectedChatName}'? This action cannot be undone.`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#d33',
      cancelButtonColor: '#3085d6',
      confirmButtonText: 'Remove',
      cancelButtonText: 'Cancel',
    }).then((result) => {
      if (result.isConfirmed) {
        this.removeUserFromChannel(username, this.selectedChanelId);
      }
    });
    this.closeModal();
  }

  isUserAdmin(member: UserMembership) {
    return member.role === 'ADMIN';
  }

  isUserGuest(member: UserMembership) {
    return member.role === 'GUEST';
  }

  isUserOwner(member: UserMembership) {
    return member.role === 'OWNER';
  }
}
