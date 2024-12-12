import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {DatePipe, NgForOf} from "@angular/common";
import {ChannelService} from "../../services/channel.service";
import {AuthService} from "../../services/auth.service";
import {Channel, Message, NewChannel, NewMessage} from "../../models/channel";
import {UserService} from "../../services/user.service";
import {Friend} from "../../models/user";
import {ToastrService} from "ngx-toastr";
import { Modal } from 'bootstrap';

declare var bootstrap: any; // Add this line to declare bootstrap globally

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    DatePipe
  ],
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  channels: Channel[] = [];

  friends: Friend[] = [];

  messages:Message[]  = [];

  selectedChanelId: string = '';
  selectedChatName: string = 'Channel Name';
  newMessage: string = '';
  newChannelName: string = '';
  currentLoggedUsername: string = '';

  constructor(private channelService: ChannelService,
              private authService: AuthService,
              private userService: UserService,
              private toastr: ToastrService,) {
  }

  ngOnInit(): void {
    let user = this.authService.getUserFromJwt();
    this.currentLoggedUsername = user.username;

    this.channelService.getChannelsForUser(user.id).subscribe(res => {
      this.channels = res.data as Channel[];
      this.selectChannel(this.channels[0])
      console.log(this.channels)

      this.userService.getUserById(user.id).subscribe(res => {
//TODO add friends
      })
    });



    }

  selectChannel(channel: Channel) {
    this.selectedChanelId = channel.id;
    this.selectedChatName = channel.name;
    this.messages = channel.messages.sort((a, b) => {
      return new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime();
    });
  }

  selectFriend(friend: Friend) {
    this.selectedChatName = friend.username;
    //TODO
    // Logic to fetch private messages can be added here
  }

  sendMessage() {
    if (this.newMessage.trim()) {
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

          this.newMessage = '';
        },
        error => {
          this.toastr.error(error.message);
        }
      );
    }
  }

  openCreateChannelModal() {
    const modalElement = document.getElementById('createChannelModal');
    if (modalElement) {
      const modal = new Modal(modalElement)
      modal.show();
    }
  }

  createChannel() {
    if (this.newChannelName) {
      const newChannel:NewChannel={
        name: this.newChannelName,
        ownerUsername: this.currentLoggedUsername,
      }
      this.channelService.createChannel(newChannel).subscribe(res => {
        const newChannel = res.data as Channel; // The updated channel returned by the API
        this.channels.push(newChannel);
      })

      this.newChannelName = '';
      const modalElement = document.getElementById('createChannelModal');
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
}
