import Axios, { InternalAxiosRequestConfig } from 'axios';

import { env } from '@/config/env';
import { getToken } from '@/lib/auth-utils.ts';

async function authRequestInterceptor(config: InternalAxiosRequestConfig) {
  if (config.headers) {
    config.headers.Accept = 'application/json';
  }

  const token: string | null = await getToken();
  if (token) {
    config.headers.set('Authorization', `Bearer ${token}`);
    config.headers.set('X-TENANT-ID', '252b6807-9981-4f79-a87f-b827b1134958');
  }

  config.withCredentials = true;
  return config;
}

export const api = Axios.create({
  baseURL: env.API_URL,
});

api.interceptors.request.use(authRequestInterceptor);
api.interceptors.response.use(
  (response) => {
    return response.data;
  },
  (error) => {
    /*const message = error.response?.data?.message || error.message;
    useNotifications.getState().addNotification({
      type: 'error',
      title: 'Error',
      message,
    });*/

    return Promise.reject(error);
  }
);
