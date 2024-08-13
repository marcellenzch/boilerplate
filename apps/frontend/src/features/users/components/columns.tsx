import { ColumnDef } from '@tanstack/react-table';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu.tsx';
import { Button } from '@/components/ui/button.tsx';
import { MoreHorizontal } from 'lucide-react';
import { DataTableColumnHeader } from './data-table-column-header';
import React from 'react';
import { User } from '@/generated/api/types';

interface ColumnDefinitionHandlerProps {
  contextMenuActions: {
    deleteUser: (id: string) => void;
    setOpenCreateUserDialog: React.Dispatch<React.SetStateAction<boolean>>;
    setEditUserId: React.Dispatch<React.SetStateAction<string | undefined>>;
  };
}

export function UserColumnHandler({
  contextMenuActions: { setOpenCreateUserDialog, setEditUserId, deleteUser },
}: ColumnDefinitionHandlerProps): ColumnDef<User>[] {
  const openEditDialog = (id: string | undefined) => {
    setEditUserId(id);
    setOpenCreateUserDialog(true);
  };

  const columns: ColumnDef<User>[] = [
    {
      header: 'ID',
      accessorKey: 'id',
      enableHiding: false,
      cell: ({ row }) => {
        return <div className="w-[300px]">{row.getValue('id')}</div>;
      },
    },
    {
      header: ({ column }) => <DataTableColumnHeader column={column} title="Email" />,
      accessorKey: 'email',
      enableHiding: false,
      cell: ({ row }) => {
        return (
          <div className="flex space-x-2">
            <span className="truncate font-medium">{row.getValue('email')}</span>
          </div>
        );
      },
    },
    {
      header: ({ column }) => <DataTableColumnHeader column={column} title="First Name" />,
      accessorKey: 'firstName',
    },
    {
      header: ({ column }) => <DataTableColumnHeader column={column} title="Last Name" />,
      accessorKey: 'lastName',
    },
    {
      id: 'actions',
      cell: ({ row }) => {
        const user = row.original;

        return (
          <div className="w-[50px]">
            <DropdownMenu modal={false}>
              <DropdownMenuTrigger asChild>
                <Button variant="ghost" className="h-8 w-8 p-0">
                  <span className="sr-only">Open menu</span>
                  <MoreHorizontal className="h-4 w-4" />
                </Button>
              </DropdownMenuTrigger>
              <DropdownMenuContent align="end">
                <DropdownMenuLabel>Actions</DropdownMenuLabel>
                <DropdownMenuItem onClick={() => navigator.clipboard.writeText(user.email)}>
                  Copy Email Address
                </DropdownMenuItem>
                <DropdownMenuSeparator />
                <DropdownMenuItem onClick={() => openEditDialog(user.id)}>Edit</DropdownMenuItem>
                <DropdownMenuItem onClick={() => deleteUser(user.id)}>Delete</DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          </div>
        );
      },
    },
  ];

  return columns;
}
