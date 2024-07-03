import { useUsers } from '@/features/users/api/get-users.ts';
import React, { useEffect } from 'react';
import { PaginationState } from '@tanstack/react-table';
import { DataTable, TablePagination } from '@/features/users/components/data-table.tsx';
import { usePersistentStorageValue } from '@/hooks/use-persistent-storage.tsx';
import { UserColumnHandler } from '@/features/users/components/columns.tsx';
import { FormDialog } from '@/features/users/components/form-dialog.tsx';
import { UpdateUser } from '@/features/users/components/edit-user.tsx';

const defaultPagination: PaginationState = {
  pageIndex: 0,
  pageSize: 10,
};

export const UsersList = () => {
  const [openCreateUserDialog, setOpenCreateUserDialog] = React.useState(false);
  const [editUserId, setEditUserId] = React.useState<string | undefined>(undefined);

  const [pageSize, setPageSize] = usePersistentStorageValue<number>(
    'users-list-pagination-size',
    defaultPagination.pageSize
  );

  const [pagination, setPagination] = React.useState<PaginationState>({
    pageIndex: defaultPagination.pageIndex,
    pageSize,
  });

  const usersQuery = useUsers({
    params: { page: pagination.pageIndex, size: pagination.pageSize },
  });

  useEffect(() => {
    setPageSize(pagination.pageSize);
  }, [pagination.pageSize]);

  const defaultData = React.useMemo(() => [], []);

  const tablePagination: TablePagination = {
    state: pagination,
    setPagination: setPagination,
    metadata: usersQuery.data?.page,
  };

  const data = usersQuery.data?.content ?? defaultData;

  const tableColumns = UserColumnHandler({ contextMenuActions: { setOpenCreateUserDialog, setEditUserId } });

  return (
    <>
      <DataTable loading={usersQuery.isFetching} columns={tableColumns} data={data} pagination={tablePagination} />
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
    </>
  );
};
