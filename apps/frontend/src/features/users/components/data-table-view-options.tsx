import { Table } from '@tanstack/react-table';
import {
  DropdownMenu,
  DropdownMenuCheckboxItem,
  DropdownMenuContent,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu.tsx';
import { Button } from '@/components/ui/button.tsx';
import { MixerHorizontalIcon } from '@radix-ui/react-icons';
import { LoaderCircle } from 'lucide-react';
import { CreateUser } from '@/features/users/components/edit-user.tsx';
import { FormDialog } from '@/features/users/components/form-dialog.tsx';
import React from 'react';

interface DataTableViewOptionsProps<TData> {
  table: Table<TData>;
  loading?: boolean;
}

export function DataTableViewOptions<TData>({ table, loading }: DataTableViewOptionsProps<TData>) {
  const [openCreateUserDialog, setOpenCreateUserDialog] = React.useState(false);

  return (
    <div className="ml-auto flex items-center gap-3">
      {loading ? <LoaderCircle className="animate-spin" /> : null}
      <DropdownMenu modal={false}>
        <DropdownMenuTrigger asChild>
          <Button variant="outline" size="sm" className="ml-auto hidden h-8 lg:flex">
            <MixerHorizontalIcon className="mr-2 h-4 w-4" />
            View
          </Button>
        </DropdownMenuTrigger>
        <DropdownMenuContent align="end" className="w-[150px]">
          <DropdownMenuLabel>Toggle columns</DropdownMenuLabel>
          <DropdownMenuSeparator />
          {table
            .getAllColumns()
            .filter((column) => typeof column.accessorFn !== 'undefined' && column.getCanHide())
            .map((column) => {
              return (
                <DropdownMenuCheckboxItem
                  key={column.id}
                  className="capitalize"
                  checked={column.getIsVisible()}
                  onCheckedChange={(value) => column.toggleVisibility(!!value)}
                >
                  {column.id}
                </DropdownMenuCheckboxItem>
              );
            })}
        </DropdownMenuContent>
      </DropdownMenu>
      <FormDialog
        trigger={<Button size="sm">Add User</Button>}
        title={'Create User'}
        description={'Invite a new user to the platform'}
        open={openCreateUserDialog}
        setOpen={setOpenCreateUserDialog}
      >
        <CreateUser
          onSuccess={() => {
            setOpenCreateUserDialog(false);
          }}
        />
      </FormDialog>
    </div>
  );
}
