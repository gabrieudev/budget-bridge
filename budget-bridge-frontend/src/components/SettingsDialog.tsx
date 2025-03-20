"use client";

import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { ThemeToggle } from "./ThemeToggle";

export function SettingsDialog({
  open,
  onOpenChange,
  username,
  email,
}: {
  open?: boolean;
  onOpenChange?: (open: boolean) => void;
  username: string;
  email: string;
}) {
  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogTrigger asChild></DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle className="text-center">Configurações</DialogTitle>
        </DialogHeader>

        <Tabs defaultValue="account" aria-describedby="dialog-description">
          <TabsList className="grid w-full grid-cols-2">
            <TabsTrigger value="account">Conta</TabsTrigger>
            <TabsTrigger value="general">Geral</TabsTrigger>
          </TabsList>

          <TabsContent value="account">
            <div className="flex items-center justify-between mb-4 mt-4">
              <h2 className="text-lg font-semibold">Nome</h2>
              <p>{username}</p>
            </div>

            <div className="flex items-center justify-between mb-4 mt-4">
              <h2 className="text-lg font-semibold">E-mail</h2>
              <p>{email}</p>
            </div>
          </TabsContent>

          <TabsContent value="general">
            <div className="flex items-center justify-between mb-4 mt-4">
              <h2 className="text-lg font-semibold">Tema</h2>
              <ThemeToggle />
            </div>
          </TabsContent>
        </Tabs>
      </DialogContent>
    </Dialog>
  );
}
