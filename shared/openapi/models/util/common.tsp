import "@typespec/http";

using TypeSpec.Http;

model TenantIdHeader {
  /** The tenant identifier */
  @header
  @format("uuid")
  `x-tenant-id`: string;
}
