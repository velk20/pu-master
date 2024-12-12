import {UserMembership} from "./user";

export interface Channel {
  id: string;
  name: string;
  ownerId: string;
  createdAt: string;
  memberships: UserMembership[];
  messages: Message[];
}

export interface Message {
  id: string;
  content: string;
  authorId: string;
  channelId: string;
  receiverId: string;
  timestamp: string;
}
