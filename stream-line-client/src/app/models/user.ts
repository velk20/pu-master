export interface Friend {
  id: string;
  username: string;
}

export interface AddFriend{
  requesterUsername: string;
  friendUsername: string;
}

export interface User{
  username: string;
  email: string;
  firstName: string;
  lastName: string;
  phone: string;
  age: number;
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
