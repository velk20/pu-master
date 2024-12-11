export interface LoginUser{
  username: string;
  password: string;
}

export interface RegisterUser extends LoginUser{
  firstName: string;
  lastName: string;
  email: string;
  phone: string;
  age: number;
  role?: string;
}

export interface JwtTokenResponse{
  token: string;
}
