"use client";

import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
} from "@/components/ui/card";
import { signIn } from "next-auth/react";
import Image from "next/image";

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-4 bg-gradient-to-b from-blue-50 to-white">
      <Card className="w-full max-w-md shadow-lg">
        <CardHeader className="items-center space-y-4">
          <div className="flex items-center justify-center">
            <div className="relative w-48 h-48">
              <Image
                src="/logo.png"
                alt="Budget Bridge logo"
                fill
                priority
                className="object-contain"
              />
            </div>
          </div>
        </CardHeader>

        <CardContent className="space-y-6 text-center">
          <h1 className="text-3xl font-bold text-gray-800">
            Controle Financeiro Pessoal
          </h1>
          <p className="text-gray-600">
            Gerencie suas receitas, despesas e metas de forma simples e
            eficiente
          </p>
        </CardContent>

        <CardFooter className="flex justify-center">
          <Button
            asChild
            variant="default"
            size="lg"
            className="w-full max-w-xs hover:text-yellow-400 active:bg-gray-200 cursor-pointer"
          >
            <span
              className="w-full text-gray-800"
              onClick={() => signIn("keycloak", { callbackUrl: "/dashboard" })}
            >
              Começar agora
            </span>
          </Button>
        </CardFooter>
      </Card>
    </main>
  );
}
