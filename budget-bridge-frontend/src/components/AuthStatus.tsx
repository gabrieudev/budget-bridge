"use client";

import { Button } from "@/components/ui/button";
import {
  Tooltip,
  TooltipContent,
  TooltipProvider,
  TooltipTrigger,
} from "@/components/ui/tooltip";
import { LogOut } from "lucide-react";
import federatedLogout from "@/lib/federatedLogout";

export function AuthStatus() {
  return (
    <TooltipProvider>
      <Tooltip>
        <TooltipTrigger asChild>
          <Button variant="ghost" size="icon" onClick={() => federatedLogout()}>
            <LogOut className="h-5 w-5" />
            <span className="sr-only">Sair</span>
          </Button>
        </TooltipTrigger>
        <TooltipContent>
          <p>Sair</p>
        </TooltipContent>
      </Tooltip>
    </TooltipProvider>
  );
}
