export { default } from "next-auth/middleware";

export const config = {
  matcher: ["/dashboard", "/transactions", "/goals", "/accounts", "/budgets"],
};
