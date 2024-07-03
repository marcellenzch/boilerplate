import { redirect } from 'react-router-dom';
import { User } from 'oidc-client-ts';
import { userManager } from '@/config.ts';

/**
 * This is a special method to check if the user authenticated before
 execute the loader code
 *
 * @param {Function} fn - The loader function
 * @returns your loader function data or redirect you into login page
 */
export function checkLoaderAuth<T>(fn: () => Promise<T>): () => Promise<T> {
  return async (): Promise<T> => {
    if (await hasToken()) {
      return await fn();
    } else {
      return redirect('/') as any;
    }
  };
}

export async function getToken(): Promise<string | null> {
  const user: User | null = await userManager.getUser();
  return user?.access_token || null;
}

export async function hasToken(): Promise<boolean> {
  const user: User | null = await userManager.getUser();

  if (user === null) {
    return false;
  }

  if (user.expired) {
    return false;
  }

  return !!user.access_token;
}
