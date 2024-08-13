import { api } from '@/lib/api-client';
import { QueryConfig } from '@/lib/react-query';
import { queryOptions, useQuery } from '@tanstack/react-query';
import { $OpenApiTs } from '@/generated/api/types';
import { userKeys } from '@/features/users/api/factory/query-key-factory.ts';

type Params = $OpenApiTs['/users/{id}']['get']['req'];
type Response = $OpenApiTs['/users/{id}']['get']['res']['200'];

export const getUser = (params: Params): Promise<Response> => {
  return api.get(`/users/${params.id}`);
};

export const getUserQueryOptions = (params: Params) => {
  return queryOptions({
    queryKey: userKeys.detail(params.id),
    queryFn: () => getUser(params),
    placeholderData: () => undefined,
    staleTime: 0,
  });
};

type UseUserOptions = {
  params: Params;
  queryConfig?: QueryConfig<typeof getUserQueryOptions>;
};

export const useUser = ({ params, queryConfig }: UseUserOptions) => {
  return useQuery({
    ...getUserQueryOptions(params),
    ...queryConfig,
  });
};
