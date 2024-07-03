import { QueryClient } from '@tanstack/react-query';
import { createBrowserRouter } from 'react-router-dom';

import { AppRoot } from './app/root';
import ProtectedApp from '@/components/protected-app.tsx';

export const createRouter = (queryClient: QueryClient) =>
  createBrowserRouter([
    {
      path: '/',
      element: (
        <ProtectedApp>
          <AppRoot />
        </ProtectedApp>
      ),
      children: [
        {
          path: '/',
          lazy: async () => {
            const { LandingRoute } = await import('./landing');
            return { Component: LandingRoute };
          },
        },
        {
          path: 'users',
          lazy: async () => {
            const { UsersRoute } = await import('./app/users');
            return { Component: UsersRoute };
          },
        },
      ],
    },
    {
      path: '*',
      lazy: async () => {
        const { NotFoundRoute } = await import('./not-found');
        return { Component: NotFoundRoute };
      },
    },
  ]);
