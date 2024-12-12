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

export interface JwtPayload {
  role: string;
  id: string;
  email: string;
  username: string;
  sub: string;
  iat: number;
  exp: number;
}
