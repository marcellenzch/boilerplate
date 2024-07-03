import { QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import * as React from 'react';
import { queryClient } from '@/lib/react-query';
import { onSigninCallback, userManager } from '@/config.ts';
import ProtectedApp from '@/components/protected-app.tsx';
import { AuthProvider } from 'react-oidc-context';
import { ThemeProvider } from '@/components/theme-provider';

type AppProviderProps = {
  children: React.ReactNode;
};

export const AppProvider = ({ children }: AppProviderProps) => {
  return (
    <React.Suspense>
      <AuthProvider userManager={userManager} onSigninCallback={onSigninCallback}>
        <QueryClientProvider client={queryClient}>
          <ThemeProvider defaultTheme="light" storageKey="app-theme">
            {import.meta.env.DEV && <ReactQueryDevtools />}
            {children}
          </ThemeProvider>
        </QueryClientProvider>
      </AuthProvider>
    </React.Suspense>
  );
};
