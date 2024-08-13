import { useMutation, useQueryClient } from '@tanstack/react-query';

import { api } from '@/lib/api-client';
import { MutationConfig } from '@/lib/react-query';

import { $OpenApiTs } from '@/generated/api/types';
import { userKeys } from '@/features/users/api/factory/query-key-factory.ts';

type Params = $OpenApiTs['/users/{id}']['put']['req'];
type RequestBody = $OpenApiTs['/users/{id}']['put']['req']['requestBody'];
type ResponseBody = $OpenApiTs['/users/{id}']['put']['res']['200'];

type ListResponse = $OpenApiTs['/users']['get']['res']['200'];

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
      const updatedUser = args[0];

      // update lists that include the updated user and replace the existing object with the new one
      queryClient.setQueriesData({ queryKey: userKeys.lists() }, (previous: ListResponse | undefined) => {
        if (previous === undefined) {
          return undefined;
        }

        return {
          ...previous,
          content: previous.content.map((user) => {
            return updatedUser.id === user.id ? updatedUser : user;
          }),
        };
      });

      queryClient.setQueryData<ResponseBody>(userKeys.detail(args[0].id), () => args[0]);

      onSuccess?.(...args);
    },
    ...restConfig,
    mutationFn: updateUser,
  });
};
