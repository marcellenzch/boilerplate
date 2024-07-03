import { Button } from '@/components/ui/button';
import { DashboardLayout } from '@/components/layouts/dashboard-layout.tsx';

export const LandingRoute = () => {
  return (
    <div className="flex items-center bg-white">
      <div className="mx-auto max-w-7xl px-4 py-12 text-center sm:px-6 lg:px-8 lg:py-16">
        <h2 className="text-3xl font-extrabold tracking-tight text-gray-900 sm:text-4xl">
          <span className="block">Bulletproof React</span>
        </h2>
        <p>Showcasing Best Practices For Building React Applications</p>
        <div className="mt-8 flex justify-center">
          <div className="inline-flex rounded-md shadow">
            <Button>Get started</Button>
          </div>
          <div className="ml-3 inline-flex">
            <a href="https://github.com/alan2207/bulletproof-react" target="_blank" rel="noreferrer">
              <Button variant="outline">Github Repo</Button>
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};
