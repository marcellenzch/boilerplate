import { useUsers } from '@/features/users/api/get-users.ts';
import React, { useEffect } from 'react';
import { PaginationState } from '@tanstack/react-table';
import { DataTable, TablePagination } from '@/features/users/components/data-table.tsx';
import { usePersistentStorageValue } from '@/hooks/use-persistent-storage.tsx';
import { UserColumnHandler } from '@/features/users/components/columns.tsx';
import { FormDialog } from '@/features/users/components/form-dialog.tsx';
import { UpdateUser } from '@/features/users/components/edit-user.tsx';
import { useUser } from '@/features/users/api/get-user.ts';
import { useSearchParams } from 'react-router-dom';
import { useDeleteUser } from '@/features/users/api/delete-user.ts';

const defaultPagination: PaginationState = {
  pageIndex: 0,
  pageSize: 10,
};

export const UsersList = () => {
  const [queryParams, setQueryParams] = useSearchParams();
  const [openCreateUserDialog, setOpenCreateUserDialog] = React.useState(false);
  const [editUserId, setEditUserId] = React.useState<string | undefined>(undefined);

  const [pageSize, setPageSize] = usePersistentStorageValue<number>(
    'users-list-pagination-size',
    defaultPagination.pageSize
  );

  const [pagination, setPagination] = React.useState<PaginationState>({
    pageIndex: Number(queryParams.get('page') || defaultPagination.pageIndex),
    pageSize,
  });

  const { data, isPending, isFetching } = useUsers({
    params: { page: pagination.pageIndex, size: pagination.pageSize },
  });

  const deleteUserMutation = useDeleteUser({ mutationConfig: { onSuccess: () => {} } });

  const userQuery = useUser({ params: { id: '1bbf9a16-0850-4fcc-8c51-86714e675122' } });

  useEffect(() => {
    setPageSize(pagination.pageSize);
  }, [pagination.pageSize]);

  useEffect(() => {
    setQueryParams({ ...queryParams, page: pagination.pageIndex.toString() });
  }, [pagination.pageIndex]);

  useEffect(() => {
    if (Number(queryParams.get('page')) !== pagination.pageIndex) {
      setPagination({
        ...pagination,
        pageIndex: Number(queryParams.get('page')),
      });
    }
  }, [queryParams]);

  if (isPending || data === undefined) {
    return null;
  }

  const tablePagination: TablePagination = {
    state: pagination,
    setPagination: setPagination,
    metadata: data.page,
  };

  const deleteUser = (id: string) => {
    if (confirm('Do you really want to delete this user?')) {
      deleteUserMutation.mutate({ id });
    }
  };

  const email = userQuery.data?.email ?? '';

  const tableColumns = UserColumnHandler({
    contextMenuActions: { setOpenCreateUserDialog, setEditUserId, deleteUser },
  });

  return (
    <>
      <DataTable loading={isFetching} columns={tableColumns} data={data?.content} pagination={tablePagination} />
      <FormDialog title={`Edit User`} open={openCreateUserDialog} setOpen={setOpenCreateUserDialog}>
        {editUserId !== undefined && (
          <UpdateUser
            id={editUserId}
            onSuccess={() => {
              setEditUserId(undefined);
              setOpenCreateUserDialog(false);
            }}
          />
        )}
      </FormDialog>
      {email}
    </>
  );
};
