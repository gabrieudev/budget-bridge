"use client";

import { Button } from "@/components/ui/button";
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from "@/components/ui/sheet";
import { cn } from "@/lib/utils";
import {
  ArrowBigRightDash,
  CreditCard,
  GoalIcon,
  Home,
  Menu,
  PiggyBankIcon,
} from "lucide-react";
import Image from "next/image";
import Link from "next/link";
import { usePathname } from "next/navigation";
import Profile from "./Profile";

const routes = [
  {
    href: "/dashboard",
    label: "Dashboard",
    icon: Home,
  },
  {
    href: "/goals",
    label: "Metas",
    icon: GoalIcon,
  },
  {
    href: "/transactions",
    label: "Transações",
    icon: ArrowBigRightDash,
  },
  {
    href: "/accounts",
    label: "Contas",
    icon: CreditCard,
  },
  {
    href: "/budgets",
    label: "Orçamentos",
    icon: PiggyBankIcon,
  },
];

export function Header() {
  const pathname = usePathname();

  return (
    <header className="sticky top-0 z-50 w-full border-b bg-background/95 backdrop-blur supports-[backdrop-filter]:bg-background/60">
      <div className="flex h-16 justify-between px-4">
        <div className="flex flex-1 gap-6">
          <Sheet>
            <SheetTrigger asChild className="lg:hidden mt-3.5">
              <Button variant="ghost" size="icon" className="-ml-2">
                <Menu className="h-5 w-5" />
                <span className="sr-only">Abrir menu</span>
              </Button>
            </SheetTrigger>
            <SheetContent side="left" className="w-64">
              <SheetHeader className="border-b pb-4 mb-4">
                <SheetTitle>Menu</SheetTitle>
              </SheetHeader>
              <nav className="flex flex-col space-y-3">
                {routes.map((route) => {
                  const Icon = route.icon;
                  return (
                    <Link
                      key={route.href}
                      href={route.href}
                      className={cn(
                        "flex items-center gap-2 rounded-lg px-3 py-2 text-sm font-medium transition-colors hover:bg-accent",
                        pathname === route.href ? "bg-accent" : "transparent"
                      )}
                    >
                      <Icon className="h-4 w-4" />
                      {route.label}
                    </Link>
                  );
                })}
              </nav>
            </SheetContent>
          </Sheet>

          <Link href="/" className="hidden lg:flex items-center gap-2">
            <Image
              src="/logo.png"
              alt="Budget Bridge logo"
              width={32}
              height={32}
            />
          </Link>

          <nav className="hidden lg:flex flex-1 items-center gap-6">
            {routes.map((route) => (
              <Link
                key={route.href}
                href={route.href}
                className={cn(
                  "flex items-center gap-2 text-sm font-medium transition-colors hover:text-foreground/80",
                  pathname === route.href
                    ? "text-foreground"
                    : "text-foreground/60"
                )}
              >
                {route.label}
              </Link>
            ))}
          </nav>
        </div>

        <div className="flex items-center gap-2">
          <Profile />
        </div>
      </div>
    </header>
  );
}
