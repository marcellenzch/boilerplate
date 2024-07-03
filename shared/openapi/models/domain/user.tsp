import "@typespec/http";

using TypeSpec.Http;
model User {
  @visibility("read", "update")
  @example("1185ae16-e8e8-4d90-a5f1-c254ab20c381")
  @format("uuid")
  @path
  id: string;

  @maxLength(120)
  @example("peter.smith@example.com")
  email: string;

  @maxLength(255)
  @example("Peter")
  firstName?: string;

  @maxLength(255)
  @example("Smith")
  lastName?: string;
}
