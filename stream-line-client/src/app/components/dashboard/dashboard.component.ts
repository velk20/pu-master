import {Component, OnInit} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";
import {ChannelService} from "../../services/channel.service";
import {AuthService} from "../../services/auth.service";
import {Channel} from "../../models/channel";

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

  friends = [
    { name: 'Friend 1' },
    { name: 'Friend 2' }
  ];

  messages = [
    { user: 'User1', text: 'Hello, everyone!' },
    { user: 'User2', text: 'Hi! How are you?' }
  ];

  selectedChatName = 'Channel Name';
  newMessage = '';

  constructor(private channelService: ChannelService,
              private authService: AuthService,) {
  }

  ngOnInit(): void {
    let user = this.authService.getUserFromJwt();
    console.log(user)
    this.channelService.getChannelsForUser(user.id).subscribe(res => {
      console.log(res)
      this.channels = res.data as Channel[];
    })
    }

  selectChannel(channel: { name: string }) {
    this.selectedChatName = channel.name;
    // Logic to fetch channel messages can be added here
  }

  selectFriend(friend: { name: string }) {
    this.selectedChatName = friend.name;
    // Logic to fetch private messages can be added here
  }

  sendMessage() {
    if (this.newMessage.trim()) {
      this.messages.push({ user: 'You', text: this.newMessage });
      this.newMessage = '';
    }
  }
}
