import { Component } from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf} from "@angular/common";

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
export class DashboardComponent {
  channels = [
    { name: 'Channel 1' },
    { name: 'Channel 2' },
    { name: 'Channel 3' }
  ];

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
