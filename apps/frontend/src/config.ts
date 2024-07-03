import { UserManager, WebStorageStateStore } from 'oidc-client-ts';
import { env } from '@/config/env';

export const userManager = new UserManager({
  authority: env.OIDC_AUTHORITY,
  client_id: env.OIDC_CLIENT_ID,
  redirect_uri: `${window.location.origin}${window.location.pathname}`,
  post_logout_redirect_uri: window.location.origin,
  userStore: new WebStorageStateStore({ store: window.sessionStorage }),
  monitorSession: true, // this allows cross tab login/logout detection
});

export const onSigninCallback = () => {
  window.history.replaceState({}, document.title, window.location.pathname);
};
