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
  size: int32;

  /** The number of the current page */
  @example(2)
  number: int32;

  /** The total number of elements */
  @example(37)
  totalElements: int64;

  /** The total number of Pages */
  @example(4)
  totalPages: int32;
}

model PaginationQuery {
  /** The maximum number of items in a page */
  @query
  @maxValue(50)
  size?: int32;

  /** The number of the page you want to query */
  @query
  @example(10)
  page?: int32;
}
