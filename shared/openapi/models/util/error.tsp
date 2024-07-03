import "@typespec/http";

using TypeSpec.Http;

@error
model Error {
  @example(404)
  status?: int16;

  code?: string;
  message?: string;
  fieldErrors?: FieldError[];

  @maxLength(120)
  @example("peter.smith@example.com")
  email: string;

  @maxLength(255)
  @example("Peter")
  firstName: string;

  @maxLength(255)
  @example("Smith")
  lastName: string;
}

model FieldError {
  code?: string;
  message?: string;
  property?: string;
  path?: string;
  rejectedValue?: unknown;
}
