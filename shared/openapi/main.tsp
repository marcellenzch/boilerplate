import "@typespec/http";
import "@typespec/rest";
import "@typespec/openapi3";

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
  @get list(...PaginationQuery): Page<User> | Error;
  @get read(@format("uuid") @path id: string): User | Error;
  @post create(...User): {
    @statusCode _: 201;
    @body body: User;
  } | Error;
  @put update(...User): User | Error;
  @delete delete(@format("uuid") @path id: string): void | Error;
}

@route("/clients")
@tag("Clients")
interface Clients {
  @get list(...PaginationQuery): Page<Client> | Error;
}
