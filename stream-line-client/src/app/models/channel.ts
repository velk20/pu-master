import {UserMembership} from "./user";

export interface Channel {
  id: string;
  name: string;
  ownerUsername: string;
  createdAt: string;
  memberships: UserMembership[];
  messages: Message[];
}

export interface NewChannel {
  name: string;
  ownerUsername: string;
}

export interface Message {
  id: string;
  content: string;
  authorUsername: string;
  channelId: string;
  receiverId: string;
  timestamp: string;
}

export interface UserFriendMessage{
  userId: string;
  friendId: string;
}

export interface FriendMessage{
  senderId: string;
  friendId: string;
  message: string;
}

export interface NewMessage {
  channelId: string;
  username: string;
  message: string;
}
