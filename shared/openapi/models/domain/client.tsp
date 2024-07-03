import "@typespec/http";

using TypeSpec.Http;
model Client {
  @visibility("read", "update")
  @example("1185ae16-e8e8-4d90-a5f1-c254ab20c382")
  @path
  id: string;

  @maxLength(120)
  @example("Company Ltd.")
  name: string;
}
