export type User = {
  id: string;
  email: string;
  firstName: string;
  lastName: string;
};

export type Page<T> = {
  content: T[];
  page: {
    size: number;
    number: number;
    totalElements: number;
    totalPages: number;
  };
};
