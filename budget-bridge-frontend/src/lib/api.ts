import { getSession } from "next-auth/react";

const API_URL = process.env.NEXT_PUBLIC_API_URL;

async function fetchWithAuth<T>(
  endpoint: string,
  options: RequestInit = {}
): Promise<ApiResponse<T>> {
  const { signal, ...restOptions } = options;

  const session: any = await getSession();

  const headers: Record<string, string> = {
    "Content-Type": "application/json",
  };

  if (session?.accessToken) {
    headers.Authorization = `Bearer ${session.accessToken}`;
  }

  const response = await fetch(`${API_URL}${endpoint}`, {
    ...restOptions,
    headers,
    signal: signal ?? undefined,
    credentials: "include",
    body: restOptions.body ? JSON.stringify(restOptions.body) : undefined,
  });

  if (!response.ok) {
    const errorContent = (await response.json()) as ApiResponse<string>;

    throw new Error(errorContent.data);
  }

  return (await response.json()) as ApiResponse<T>;
}

export const api = {
  createAccount: async (
    data: Partial<Account>
  ): Promise<ApiResponse<Account>> => {
    return fetchWithAuth<Account>("/accounts", {
      method: "POST",
      body: JSON.stringify(data),
    });
  },

  updateAccount: async (
    id: string,
    data: Partial<Account>
  ): Promise<ApiResponse<Account>> => {
    return fetchWithAuth<Account>(`/accounts/${id}`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  },

  deleteAccount: async (id: string): Promise<ApiResponse<string>> => {
    return fetchWithAuth<string>(`/accounts/${id}`, {
      method: "DELETE",
    });
  },

  getAccounts: async ({
    name = null,
    type = null,
    currency = null,
    color = null,
    page,
    size,
  }: {
    name?: string | null;
    type?: string | null;
    currency?: string | null;
    color?: string | null;
    page?: number;
    size?: number;
  }): Promise<ApiResponse<PaginatedResponse<Account>>> => {
    return fetchWithAuth<PaginatedResponse<Account>>(
      `/accounts?name=${name}&type=${type}&currency=${currency}&color=${color}&page=${page}&size=${size}`
    );
  },

  getAccount: async (id: string): Promise<ApiResponse<Account>> => {
    return fetchWithAuth<Account>(`/accounts/${id}`);
  },

  createTransaction: async (
    data: Partial<Transaction>
  ): Promise<ApiResponse<Transaction>> => {
    return fetchWithAuth<Transaction>("/transactions", {
      method: "POST",
      body: JSON.stringify(data),
    });
  },

  updateTransaction: async (
    id: string,
    data: Partial<Transaction>
  ): Promise<ApiResponse<Transaction>> => {
    return fetchWithAuth<Transaction>(`/transactions/${id}`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  },

  deleteTransaction: async (id: string): Promise<ApiResponse<string>> => {
    return fetchWithAuth<string>(`/transactions/${id}`, {
      method: "DELETE",
    });
  },

  getTransactions: async ({
    accountId = null,
    categoryId = null,
    startDate = null,
    endDate = null,
    type = null,
    page,
    size,
  }: {
    accountId?: string | null;
    categoryId?: string | null;
    goal?: string | null;
    type?: string | null;
    startDate?: Date | null;
    endDate?: Date | null;
    page?: number;
    size?: number;
  }): Promise<ApiResponse<PaginatedResponse<Transaction>>> => {
    return fetchWithAuth<PaginatedResponse<Transaction>>(
      `/transactions?account=${accountId}&categoryId=${categoryId}&type=${type}&startDate=${startDate}&endDate=${endDate}&page=${page}&size=${size}`
    );
  },

  getTransaction: async (id: string): Promise<ApiResponse<Transaction>> => {
    return fetchWithAuth<Transaction>(`/transactions/${id}`);
  },

  createGoal: async (data: Partial<Goal>): Promise<ApiResponse<Goal>> => {
    return fetchWithAuth<Goal>("/goals", {
      method: "POST",
      body: JSON.stringify(data),
    });
  },

  updateGoal: async (
    id: string,
    data: Partial<Goal>
  ): Promise<ApiResponse<Goal>> => {
    return fetchWithAuth<Goal>(`/goals/${id}`, {
      method: "PUT",
      body: JSON.stringify(data),
    });
  },

  deleteGoal: async (id: string): Promise<ApiResponse<string>> => {
    return fetchWithAuth<string>(`/goals/${id}`, {
      method: "DELETE",
    });
  },

  getGoals: async ({
    accountId = null,
    type = null,
    status = null,
    page,
    size,
  }: {
    accountId?: string | null;
    type?: string | null;
    status?: string | null;
    page?: number;
    size?: number;
  }): Promise<ApiResponse<PaginatedResponse<Goal>>> => {
    return fetchWithAuth<PaginatedResponse<Goal>>(
      `/goals?account=${accountId}&type=${type}&status=${status}&page=${page}&size=${size}`
    );
  },

  deposit: async (id: string, amount: number): Promise<ApiResponse<string>> => {
    return fetchWithAuth<string>(`/goals/${id}/deposit`, {
      method: "POST",
      body: JSON.stringify({ amount }),
    });
  },

  createCategory: async (
    data: Partial<Category>
  ): Promise<ApiResponse<Category>> => {
    return fetchWithAuth<Category>("/categories", {
      method: "POST",
      body: JSON.stringify(data),
    });
  },

  getCategories: async ({
    page,
    size,
  }: {
    page?: number;
    size?: number;
  }): Promise<ApiResponse<PaginatedResponse<Category>>> => {
    return fetchWithAuth<PaginatedResponse<Category>>(
      `/categories?page=${page}&size=${size}`
    );
  },

  deleteCategory: async (id: string): Promise<ApiResponse<string>> => {
    return fetchWithAuth<string>(`/categories/${id}`, {
      method: "DELETE",
    });
  },

  createBudget: async (data: Partial<Budget>): Promise<ApiResponse<Budget>> => {
    return fetchWithAuth<Budget>("/budgets", {
      method: "POST",
      body: JSON.stringify(data),
    });
  },

  getBudgets: async ({
    page,
    size,
  }: {
    page?: number;
    size?: number;
  }): Promise<ApiResponse<PaginatedResponse<Budget>>> => {
    return fetchWithAuth<PaginatedResponse<Budget>>(
      `/budgets/current?page=${page}&size=${size}`
    );
  },

  deleteBudget: async (id: string): Promise<ApiResponse<string>> => {
    return fetchWithAuth<string>(`/budgets/${id}`, {
      method: "DELETE",
    });
  },
};
