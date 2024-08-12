import * as z from 'zod';

export const EnvSchema = z.object({
  API_URL: z.string(),
  OIDC_AUTHORITY: z.string(),
  OIDC_CLIENT_ID: z.string(),
});
