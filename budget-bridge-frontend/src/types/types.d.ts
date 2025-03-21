export {};

declare global {
  declare namespace NodeJS {
    export interface ProcessEnv {
      KEYCLOAK_CLIENT_ID: string;
      KEYCLOAK_CLIENT_SECRET: string;
      KEYCLOAK_ISSUER: string;
    }
  }

  export interface ApiResponse<T> {
    status: number;
    data: T;
    timestamp: Date;
  }

  export interface PaginatedResponse<T> {
    content: T[];
    totalPages: number;
    totalElements: number;
    size: number;
    number: number;
  }

  export interface Account {
    id: string;
    userId: string;
    name: string;
    type: string;
    currency: string;
    balance: number;
    color: string;
    isActive: boolean;
    createdAt: Date;
    updatedAt: Date;
  }

  export interface Transaction {
    id: string;
    userId: string;
    account: Account;
    goal: Goal;
    type: string;
    category: Category;
    amount: number;
    description: string;
    transactionDate: Date;
    createdAt: Date;
    updatedAt: Date;
  }

  export interface Goal {
    id: string;
    userId: string;
    account: Account;
    title: string;
    description: string;
    targetAmount: number;
    currentAmount: number;
    type: string;
    deadline: Date;
    status: string;
    createdAt: Date;
    updatedAt: Date;
  }

  export interface Budget {
    id: string;
    userId: string;
    category: Category;
    targetAmount: number;
    spentAmount: number;
    startDate: Date;
    endDate: Date;
  }

  export interface Category {
    id: string;
    userId: string;
    name: string;
    color: string;
  }
}
