import "@typespec/http";
import "@typespec/rest";
import "@typespec/openapi3";

import "./models/util/common.tsp";
import "./models/util/error.tsp";
import "./models/util/page.tsp";

import "./models/domain/client.tsp";
import "./models/domain/user.tsp";

using TypeSpec.Http;

@service({
  title: "API",
})
@useAuth(BearerAuth)
@server("http://localhost:8080/api", "Localhost server")
namespace API;

@route("/users")
@tag("Users")
interface Users {
  @get list(...TenantIdHeader, ...PaginationQuery): Page<User> | Error;
  @get read(...TenantIdHeader, @format("uuid") @path id: string): User | Error;
  @post create(...TenantIdHeader, ...User): {
    @statusCode _: 201;
    @body body: User;
  } | Error;
  @put update(...TenantIdHeader, ...User): User | Error;
  @delete delete(...TenantIdHeader, @format("uuid") @path id: string): void | Error;
}

@route("/clients")
@tag("Clients")
interface Clients {
  @get list(...TenantIdHeader, ...PaginationQuery): Page<Client> | Error;
}
