import "@typespec/http";

using TypeSpec.Http;

model Page<T> {
  content: T[];
  page: Pagination;
}

model Pagination {
  /** The maximum number of items in a page */
  @example(10)
  @maxValue(50)
  size: int64;

  /** The number of the current page */
  @example(2)
  number: int64;

  /** The total number of elements */
  @example(37)
  totalElements: int64;

  /** The total number of Pages */
  @example(4)
  totalPages: int64;
}

model PaginationQuery {
  /** The maximum number of items in a page */
  @query
  @maxValue(50)
  size?: int32;

  /** The number of the page you want to query */
  @example(10)
  @query
  page?: int32;
}
