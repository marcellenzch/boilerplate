import { useMutation, useQueryClient } from '@tanstack/react-query';

import { api } from '@/lib/api-client';
import { MutationConfig } from '@/lib/react-query';

import { $OpenApiTs } from '@/generated/api/types';
import { userKeys } from '@/features/users/api/factory/query-key-factory.ts';

type Params = Omit<$OpenApiTs['/users/{id}']['delete']['req'], `xTenantId`>;

export const deleteUser = (params: Params): Promise<void> => {
  return api.delete(`/users/${params.id}`);
};

type UseDeleteUserOptions = {
  mutationConfig?: MutationConfig<typeof deleteUser>;
};

export const useDeleteUser = ({ mutationConfig }: UseDeleteUserOptions) => {
  const queryClient = useQueryClient();

  const { onSuccess, ...restConfig } = mutationConfig || {};

  return useMutation({
    onSuccess: (...args) => {
      // We invalidate the existing queries as the pagination might be affected by the new entry
      queryClient.invalidateQueries({
        queryKey: userKeys.lists(),
      });

      queryClient.invalidateQueries({
        queryKey: userKeys.detail(args[1].id),
      });

      onSuccess?.(...args);
    },
    ...restConfig,
    mutationFn: deleteUser,
  });
};
