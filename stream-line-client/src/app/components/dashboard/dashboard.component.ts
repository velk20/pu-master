import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {ChannelService} from "../../services/channel.service";
import {AuthService} from "../../services/auth.service";
import {Channel, Message} from "../../models/channel";
import {UserService} from "../../services/user.service";
import {Friend} from "../../models/user";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  standalone: true,
  imports: [
    FormsModule,
    NgForOf
  ],
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  channels: Channel[] = [];

  friends: Friend[] = [];

  messages:Message[]  = [];

  selectedChatName = 'Channel Name';
  newMessage = '';

  constructor(private channelService: ChannelService,
              private authService: AuthService,
              private userService: UserService,) {
  }

  ngOnInit(): void {
    let user = this.authService.getUserFromJwt();
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
    this.selectedChatName = channel.name;
    this.messages = channel.messages
  }

  selectFriend(friend: Friend) {
    this.selectedChatName = friend.username;
    //TODO
    // Logic to fetch private messages can be added here
  }

  sendMessage() {
    if (this.newMessage.trim()) {
      //TODO create new message
      // this.messages.push({ user: 'You', text: this.newMessage });
      this.newMessage = '';
    }
  }
}
