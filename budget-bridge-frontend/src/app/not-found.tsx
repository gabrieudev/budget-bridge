import Link from "next/link";
import { Metadata } from "next";

export const metadata: Metadata = {
  title: "Auth - 404",
  description: "Página 404 da aplicação de autenticação",
};

export default function NotFound() {
  return (
    <div className="flex h-screen w-screen flex-col items-center justify-center bg-gradient-to-r from-[#F7CAC9] to-[#92A8D1]">
      <h1 className="text-9xl font-extrabold text-white">404</h1>
      <p className="mt-4 text-3xl font-semibold text-white">
        A página que você procura não existe!
      </p>
      <p className="mt-2 text-xl text-white">
        Mas não se preocupe, você pode voltar para a página inicial clicando{" "}
        <Link className="underline" href="/dashboard">
          aqui
        </Link>
        .
      </p>
    </div>
  );
}
