export interface User {
  username: string;
  roles: string[];
}

export interface Product {
  id: string;
  name: string;
  active: boolean;
  price: number;
  currency: string;
}
