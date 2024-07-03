import { useMutation, useQueryClient } from '@tanstack/react-query';

import { api } from '@/lib/api-client';
import { MutationConfig } from '@/lib/react-query';

import { $OpenApiTs } from '@/generated/api/types';

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
      queryClient.invalidateQueries({
        queryKey: ['users'],
      });

      queryClient.setQueryData<ResponseBody>(['user', args[0].id], () => args[0]);

      onSuccess?.(...args);
    },
    ...restConfig,
    mutationFn: createUser,
  });
};
