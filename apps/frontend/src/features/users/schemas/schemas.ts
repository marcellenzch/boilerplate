import { z } from 'zod';

export const editUserInputSchema = z.object({
  email: z.string().max(80).email('Invalid email address'),
  firstName: z.string().max(255).optional(),
  lastName: z.string().max(255).optional(),
});

export type EditUserInput = z.infer<typeof editUserInputSchema>;
