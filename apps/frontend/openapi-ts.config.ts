import { defineConfig } from '@hey-api/openapi-ts';

export default defineConfig({
  //input: './../../shared/openapi/generated/service.yaml',
  input: './../../shared/openapi/tsp-output/@typespec/openapi3/openapi.yaml',
  output: {
    format: 'prettier',
    path: 'src/generated/api/types',
  },
  services: false,
  schemas: false,
  exportCore: false,
});
