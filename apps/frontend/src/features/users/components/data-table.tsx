'use client';

import {
  ColumnDef,
  flexRender,
  getCoreRowModel,
  OnChangeFn,
  PaginationState,
  useReactTable,
} from '@tanstack/react-table';

import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from '@/components/ui/table';
import React from 'react';
import { Pagination } from '@/generated/api/types';
import { DataTablePagination } from '@/features/users/components/data-table-pagination.tsx';
import { DataTableViewOptions } from './data-table-view-options';
import { useDebounce } from '@uidotdev/usehooks';

export type TablePagination = {
  state: PaginationState;
  setPagination: OnChangeFn<PaginationState> | undefined;
  metadata: Pagination | undefined;
};

interface DataTableProps<TData, TValue> {
  columns: ColumnDef<TData, TValue>[];
  data: TData[];
  pagination: TablePagination;
  loading?: boolean;
}

export function DataTable<TData, TValue>({ columns, data, pagination, loading }: DataTableProps<TData, TValue>) {
  const table = useReactTable({
    data: data,
    columns,
    pageCount: pagination.metadata?.totalPages ?? -1,
    state: {
      pagination: pagination.state,
    },
    onPaginationChange: pagination.setPagination,
    getCoreRowModel: getCoreRowModel(),
    manualPagination: true, //we're doing manual "server-side" pagination
    debugTable: false,
  });

  // debounce loading state to prevent flickering
  const debouncedLoading = useDebounce(loading, 50);

  return (
    <div className="space-y-4">
      <div className="flex items-center justify-between">
        <DataTableViewOptions table={table} loading={debouncedLoading} />
      </div>
      <div className="rounded-md border">
        <Table>
          <TableHeader>
            {table.getHeaderGroups().map((headerGroup) => (
              <TableRow className="bg-neutral-100 hover:bg-neutral-100" key={headerGroup.id}>
                {headerGroup.headers.map((header) => {
                  return (
                    <TableHead key={header.id}>
                      {header.isPlaceholder ? null : flexRender(header.column.columnDef.header, header.getContext())}
                    </TableHead>
                  );
                })}
              </TableRow>
            ))}
          </TableHeader>
          <TableBody>
            {table.getRowModel().rows?.length ? (
              table.getRowModel().rows.map((row) => (
                <TableRow key={row.id} data-state={row.getIsSelected() && 'selected'}>
                  {row.getVisibleCells().map((cell) => (
                    <TableCell key={cell.id}>{flexRender(cell.column.columnDef.cell, cell.getContext())}</TableCell>
                  ))}
                </TableRow>
              ))
            ) : (
              <TableRow>
                <TableCell colSpan={columns.length} className="h-24 text-center">
                  {debouncedLoading ? 'Loading Data' : 'No results.'}
                </TableCell>
              </TableRow>
            )}
          </TableBody>
        </Table>
      </div>
      <DataTablePagination table={table} />
    </div>
  );
}
