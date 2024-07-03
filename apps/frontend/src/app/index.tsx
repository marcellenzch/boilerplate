import { AppProvider } from './main-provider';
import { UsersList } from '@/features/users/components/users-list.tsx';
import { Button } from '@/components/ui/button.tsx';
import { useQueryClient } from '@tanstack/react-query';
import { useMemo } from 'react';
import { createRouter } from '@/app/routes';
import { RouterProvider } from 'react-router-dom';

const AppRouter = () => {
  const queryClient = useQueryClient();

  const router = useMemo(() => createRouter(queryClient), [queryClient]);

  return <RouterProvider router={router} />;
};

function App() {
  return (
    <AppProvider>
      <AppRouter />
    </AppProvider>
  );
}

export default App;
