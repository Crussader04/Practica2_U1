import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Banda_1 from "./org/northpole/workshop/base/models/Banda.js";
async function createBanda_1(nombre: string | undefined, fecha: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("BandaService", "createBanda", { nombre, fecha }, init); }
async function listAllBanda_1(init?: EndpointRequestInit_1): Promise<Array<Banda_1 | undefined> | undefined> { return client_1.call("BandaService", "listAllBanda", {}, init); }
async function listBanda_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("BandaService", "listBanda", {}, init); }
async function updateBanda_1(id: number | undefined, nombre: string | undefined, fecha: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("BandaService", "updateBanda", { id, nombre, fecha }, init); }
export { createBanda_1 as createBanda, listAllBanda_1 as listAllBanda, listBanda_1 as listBanda, updateBanda_1 as updateBanda };
