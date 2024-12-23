import {UserMembership} from "./user";

export interface Channel {
  id: string;
  name: string;
  ownerUsername: string;
  createdAt: string;
  memberships: UserMembership[];
  messages: Message[];
}

export interface NewChannelName{
  id:string;
  name: string;
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

export interface UserToChannel {
  channelId: string;
  username: string;
  remove?: boolean;
}

export interface NewMessage extends UserToChannel{
  message: string;
}
