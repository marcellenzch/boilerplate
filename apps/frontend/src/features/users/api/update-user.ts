import { useMutation, useQueryClient } from '@tanstack/react-query';

import { api } from '@/lib/api-client';
import { MutationConfig } from '@/lib/react-query';

import { $OpenApiTs } from '@/generated/api/types';

type Params = $OpenApiTs['/api/users/{id}']['put']['req'];
type RequestBody = $OpenApiTs['/api/users/{id}']['put']['req']['requestBody'];
type ResponseBody = $OpenApiTs['/api/users/{id}']['put']['res']['200'];

export const updateUser = ({
  params,
  data,
}: {
  params: Pick<Params, 'id'>;
  data: RequestBody;
}): Promise<ResponseBody> => {
  return api.put(`/users/${params.id}`, data);
};

type UseUpdateUserOptions = {
  mutationConfig?: MutationConfig<typeof updateUser>;
};

export const useUpdateUser = ({ mutationConfig }: UseUpdateUserOptions) => {
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
    mutationFn: updateUser,
  });
};
