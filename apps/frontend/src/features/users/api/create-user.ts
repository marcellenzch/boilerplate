import { useMutation, useQueryClient } from '@tanstack/react-query';

import { api } from '@/lib/api-client';
import { MutationConfig } from '@/lib/react-query';

import { $OpenApiTs } from '@/generated/api/types';
import { userKeys } from '@/features/users/api/factory/query-key-factory.ts';

type RequestBody = $OpenApiTs['/api/users']['post']['req']['requestBody'];
type ResponseBody = $OpenApiTs['/api/users']['post']['res']['201'];

export const createUser = ({ data }: { data: RequestBody }): Promise<ResponseBody> => {
  return api.post('/users', data);
};

type UseCreateUserOptions = {
  mutationConfig?: MutationConfig<typeof createUser>;
};

export const useCreateUser = ({ mutationConfig }: UseCreateUserOptions) => {
  const queryClient = useQueryClient();

  const { onSuccess, ...restConfig } = mutationConfig || {};

  return useMutation({
    onSuccess: (...args) => {
      // We invalidate the existing queries as the pagination might be affected by the new entry
      queryClient.invalidateQueries({
        queryKey: userKeys.lists(),
      });

      queryClient.setQueryData<ResponseBody>(userKeys.detail(args[0].id), () => args[0]);

      onSuccess?.(...args);
    },
    ...restConfig,
    mutationFn: createUser,
  });
};
