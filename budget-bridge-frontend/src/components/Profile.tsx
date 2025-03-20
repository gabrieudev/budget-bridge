"use client";

import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuGroup,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuShortcut,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import federatedLogout from "@/utils/federatedLogout";
import { LogOut, Settings, User } from "lucide-react";
import { useState } from "react";
import { SettingsDialog } from "./SettingsDialog";

export default function Profile({
  username,
  email,
}: {
  username: string;
  email: string;
}) {
  const [isSettingsOpen, setIsSettingsOpen] = useState(false);

  const handleSettingsOpen = (e: Event) => {
    e.preventDefault();
    setIsSettingsOpen(true);
  };

  return (
    <DropdownMenu>
      <DropdownMenuTrigger asChild>
        <Button variant="outline">
          <User className="h-4 w-4" />
          Perfil
        </Button>
      </DropdownMenuTrigger>
      <DropdownMenuContent className="w-56">
        <DropdownMenuLabel>{username}</DropdownMenuLabel>
        <DropdownMenuSeparator />
        <DropdownMenuGroup>
          <DropdownMenuItem onSelect={handleSettingsOpen}>
            <div className="flex items-center justify-between w-full">
              Configurações
              <DropdownMenuShortcut>
                <Settings className="h-4 w-4" />
              </DropdownMenuShortcut>
            </div>
          </DropdownMenuItem>
          <DropdownMenuItem onClick={() => federatedLogout()}>
            Sair
            <DropdownMenuShortcut>
              <LogOut className="h-4 w-4" />
            </DropdownMenuShortcut>
          </DropdownMenuItem>
        </DropdownMenuGroup>
      </DropdownMenuContent>
      <SettingsDialog
        open={isSettingsOpen}
        onOpenChange={setIsSettingsOpen}
        username={username}
        email={email}
      />
    </DropdownMenu>
  );
}
