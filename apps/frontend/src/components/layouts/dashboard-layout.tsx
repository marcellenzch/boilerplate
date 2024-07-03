import { Home, Users, Package2, Bell, ShoppingCart, Package, LineChart, Menu, Search, CircleUser } from 'lucide-react';
import { NavLink, useNavigate } from 'react-router-dom';

import { Button } from '@/components/ui/button';

import { Badge } from '@/components/ui/badge.tsx';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card.tsx';
import { Sheet, SheetContent, SheetTrigger } from '@/components/ui/sheet.tsx';
import { Input } from '@/components/ui/input.tsx';
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '@/components/ui/dropdown-menu.tsx';
import { cn } from '@/lib/utils.ts';
import { ModeToggle } from '@/components/ui/mode-toggle.tsx';

type SideNavigationItem = {
  name: string;
  to: string;
  icon: (props: React.SVGProps<SVGSVGElement>) => JSX.Element;
};

export function DashboardLayout({ children }: { children: React.ReactNode }) {
  const navigation = [
    { name: 'Dashboard', to: '/', icon: Home },
    {
      name: 'Users',
      to: '/users',
      icon: Users,
    },
  ].filter(Boolean) as SideNavigationItem[];

  return (
    <div className="grid min-h-screen w-full md:grid-cols-[220px_1fr] lg:grid-cols-[280px_1fr]">
      <div className="hidden border-r dark:border-r-neutral-400 bg-gray-100/40 dark:bg-neutral-900 md:block">
        <div className="flex h-full max-h-screen flex-col gap-2">
          <div className="flex h-14 items-center border-b dark:border-b-neutral-400 dark:text-neutral-50 px-4 lg:h-[60px] lg:px-6">
            <Package2 className="h-6 w-6" />
            <span className="">Acme Inc</span>
            <Button variant="outline" size="icon" className="ml-auto h-8 w-8">
              <Bell className="h-4 w-4" />
              <span className="sr-only">Toggle notifications</span>
            </Button>
          </div>
          <div className="flex-1">
            <nav className="grid items-start px-2 text-sm font-medium lg:px-4">
              {navigation.map((item) => (
                <NavLink
                  key={item.name}
                  to={item.to}
                  end
                  className={({ isActive }) =>
                    cn(
                      'flex items-center gap-3 rounded-lg px-3 py-2 text-muted-foreground transition-all hover:text-primary',
                      isActive && 'bg-gray-900 text-white'
                    )
                  }
                >
                  <item.icon
                    className={cn('text-gray-400 group-hover:text-gray-300', 'mr-4 size-6 shrink-0')}
                    aria-hidden="true"
                  />
                  {item.name}
                </NavLink>
              ))}
            </nav>
          </div>
          <div className="mt-auto p-4">
            <Card x-chunk="dashboard-02-chunk-0">
              <CardHeader className="p-2 pt-0 md:p-4">
                <CardTitle>Upgrade to Pro</CardTitle>
                <CardDescription>Unlock all features and get unlimited access to our support team.</CardDescription>
              </CardHeader>
              <CardContent className="p-2 pt-0 md:p-4 md:pt-0">
                <Button size="sm" className="w-full">
                  Upgrade
                </Button>
              </CardContent>
            </Card>
          </div>
        </div>
      </div>
      <div className="flex flex-col">
        <header className="flex h-14 items-center gap-4 border-b dark:border-b-neutral-400 bg-gray-100/40 dark:bg-neutral-900 px-4 lg:h-[60px] lg:px-6">
          <Sheet>
            <SheetTrigger asChild>
              <Button variant="outline" size="icon" className="shrink-0 md:hidden">
                <Menu className="h-5 w-5" />
                <span className="sr-only">Toggle navigation menu</span>
              </Button>
            </SheetTrigger>
            <SheetContent side="left" className="flex flex-col">
              <nav className="grid gap-2 text-lg font-medium">
                <NavLink target="#" to="#" className="flex items-center gap-2 text-lg font-semibold">
                  <Package2 className="h-6 w-6" />
                  <span className="sr-only">Acme Inc</span>
                </NavLink>
                {navigation.map((item) => (
                  <NavLink
                    key={item.name}
                    to={item.to}
                    end
                    className={({ isActive }) =>
                      cn(
                        'mx-[-0.65rem] flex items-center gap-4 rounded-xl px-3 py-2 text-muted-foreground hover:text-foreground',
                        isActive && 'bg-gray-900 text-white'
                      )
                    }
                  >
                    <item.icon
                      className={cn('text-gray-400 group-hover:text-gray-300', 'mr-4 size-6 shrink-0')}
                      aria-hidden="true"
                    />
                    {item.name}
                  </NavLink>
                ))}
              </nav>
              <div className="mt-auto">
                <Card>
                  <CardHeader>
                    <CardTitle>Upgrade to Pro</CardTitle>
                    <CardDescription>Unlock all features and get unlimited access to our support team.</CardDescription>
                  </CardHeader>
                  <CardContent>
                    <Button size="sm" className="w-full">
                      Upgrade
                    </Button>
                  </CardContent>
                </Card>
              </div>
            </SheetContent>
          </Sheet>
          <div className="w-full flex-1"></div>
          <DropdownMenu modal={false}>
            <DropdownMenuTrigger asChild>
              <Button variant="secondary" size="icon" className="rounded-full">
                <CircleUser className="h-5 w-5" />
                <span className="sr-only">Toggle user menu</span>
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              <DropdownMenuLabel>My Account</DropdownMenuLabel>
              <DropdownMenuSeparator />
              <DropdownMenuItem>Settings</DropdownMenuItem>
              <DropdownMenuItem>Support</DropdownMenuItem>
              <DropdownMenuSeparator />
              <DropdownMenuItem>Logout</DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
          <ModeToggle />
        </header>
        <main className="flex flex-1 flex-col gap-4 p-4 lg:gap-6 lg:p-6 dark:bg-neutral-700">{children}</main>
      </div>
    </div>
  );
}
