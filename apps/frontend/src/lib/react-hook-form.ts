/* eslint:disable */

import { AxiosError } from 'axios';
import { Error } from '@/generated/api/types';
import { FieldValues, UseFormReturn } from 'react-hook-form';
import { FieldPath } from 'react-hook-form/dist/types/path';

export function addApiErrorResponseToFormErrors<T extends FieldValues = FieldValues>(
  error: AxiosError<Error>,
  form: UseFormReturn<T>
) {
  if (error.response === undefined) {
    return;
  }

  const fieldErrors = error?.response.data?.fieldErrors;

  if (!fieldErrors) {
    return;
  }

  fieldErrors.forEach((fieldError) => {
    if (fieldError.property === undefined) {
      return;
    }

    const key = fieldError.property as FieldPath<T>;
    if (Object.keys(form.getValues()).includes(key)) {
      form.setError(key, { type: 'server', message: fieldError.message });
    }
  });
}
