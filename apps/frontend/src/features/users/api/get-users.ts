import { api } from '@/lib/api-client';
import { QueryConfig } from '@/lib/react-query';
import { keepPreviousData, queryOptions, useQuery } from '@tanstack/react-query';
import { $OpenApiTs } from '@/generated/api/types';

type Params = $OpenApiTs['/api/users']['get']['req'];
type Response = $OpenApiTs['/api/users']['get']['res']['200'];

export const getUsers = (params: Params = { page: 0 }): Promise<Response> => {
  return api.get(`/users`, { params });
};

export const getUsersQueryOptions = (params: Params = { page: 0, size: 10 }) => {
  return queryOptions({
    queryKey: ['users', params.page, params.size],
    queryFn: () => getUsers(params),
    placeholderData: keepPreviousData,
    staleTime: 0,
  });
};

type UseUsersOptions = {
  params?: Params;
  queryConfig?: QueryConfig<typeof getUsersQueryOptions>;
};

export const useUsers = ({ params, queryConfig }: UseUsersOptions = {}) => {
  return useQuery({
    ...getUsersQueryOptions(params),
    ...queryConfig,
  });
};
