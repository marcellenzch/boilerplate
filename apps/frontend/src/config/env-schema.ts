import * as z from 'zod';

export const EnvSchema = z.object({
  API_URL: z.string().default('http://localhost:8080/api'),
  OIDC_AUTHORITY: z.string().default('http://localhost:8081/realms/sample'),
  OIDC_CLIENT_ID: z.string().default('app'),
  FRONTEND_URL: z.string().optional().default('http://localhost:5173'),
});
