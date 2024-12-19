export interface Friend {
  id: string;
  username: string;
}

export interface AddFriend{
  requesterUsername: string;
  friendUsername: string;
}

export interface Profile {
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  phone: string;
  age: number;
}

export interface User extends Profile{
  createdAt: string;
  lastModified: string;
  role?: string;
  friends: Friend[];
}

export interface UserMembership{
  id: string;
  username: string;
  role: string;
}

export interface UserRoleToChannel{
  channelId: string;
  username: string;
  role: string;
}
