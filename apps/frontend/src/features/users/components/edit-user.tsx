import { useCreateUser } from '@/features/users/api/create-user.ts';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { EditUserInput, editUserInputSchema } from '@/features/users/schemas';
import { Form, FormControl, FormDescription, FormField, FormItem, FormLabel, FormMessage } from '@/components/ui/form';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import React from 'react';
import { UserDTO, UserDTOCreateOrUpdate } from '@/generated/api/types';
import { useUser } from '@/features/users/api/get-user.ts';
import { useUpdateUser } from '@/features/users/api/update-user.ts';
import { addApiErrorResponseToFormErrors } from '@/lib/react-hook-form.ts';

type CreateUserProps = {
  onSuccess?: (data: UserDTO, variables: { data: UserDTOCreateOrUpdate }, context: unknown) => void;
};

type UpdateUserProps = CreateUserProps & {
  id: string;
};

interface UserFormProps {
  id?: string;
  onSuccess?: (data: UserDTO, variables: { data: UserDTOCreateOrUpdate }, context: unknown) => void;
  defaultValues?: Partial<UserDTO>;
}

const UserForm = ({ id, onSuccess, defaultValues }: UserFormProps) => {
  const createUserMutation = useCreateUser({
    mutationConfig: {
      onSuccess: onSuccess,
    },
  });

  const updateUserMutation = useUpdateUser({
    mutationConfig: {
      onSuccess: onSuccess,
    },
  });

  const form = useForm<EditUserInput>({
    defaultValues,
    resolver: zodResolver(editUserInputSchema),
  });

  function onSubmit(values: EditUserInput) {
    if (id === undefined) {
      createUserMutation.mutate(
        { data: values },
        {
          onSuccess: () => form.reset(),
          onError: (error) => addApiErrorResponseToFormErrors(error, form),
        }
      );
    } else {
      updateUserMutation.mutate(
        { params: { id }, data: values },
        {
          onSuccess: () => form.reset(),
          onError: (error) => addApiErrorResponseToFormErrors(error, form),
        }
      );
    }
  }

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
        <FormField
          control={form.control}
          name="email"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Email</FormLabel>
              <FormControl>
                <Input type="email" {...field} />
              </FormControl>
              <FormDescription>This is your email address that you use to login.</FormDescription>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="firstName"
          render={({ field }) => (
            <FormItem>
              <FormLabel>First Name</FormLabel>
              <FormControl>
                <Input {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="lastName"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Last Name</FormLabel>
              <FormControl>
                <Input {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <Button type="submit">Submit</Button>
      </form>
    </Form>
  );
};

export const CreateUser = ({ onSuccess }: CreateUserProps) => {
  return <UserForm onSuccess={onSuccess} />;
};

export const UpdateUser = ({ onSuccess, id }: UpdateUserProps) => {
  const getUserQuery = useUser({ params: { id: id } });
  if (getUserQuery.isFetching) {
    return 'Sample data';
  }
  return <UserForm id={id} onSuccess={onSuccess} defaultValues={getUserQuery.data} />;
};
