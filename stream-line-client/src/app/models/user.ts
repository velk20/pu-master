
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
  friendsIds: string[];
}

export interface UserMembership{
  id: string;
  username: string;
  role: string;
}
