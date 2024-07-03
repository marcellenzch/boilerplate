import { NavLink } from 'react-router-dom';

export const NotFoundRoute = () => {
  return (
    <div className="mt-52 flex flex-col items-center font-semibold">
      <h1>404 - Not Found</h1>
      <p>Sorry, the page you are looking for does not exist.</p>
      <NavLink to="/" replace>
        Go to Home
      </NavLink>
    </div>
  );
};
