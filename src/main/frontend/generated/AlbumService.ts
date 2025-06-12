import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Album_1 from "./org/northpole/workshop/base/models/Album.js";
async function createAlbum_1(nombre: string | undefined, fecha: string | undefined, id_banda: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("AlbumService", "createAlbum", { nombre, fecha, id_banda }, init); }
async function listAlbum_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("AlbumService", "listAlbum", {}, init); }
async function listAllAlbum_1(init?: EndpointRequestInit_1): Promise<Array<Album_1 | undefined> | undefined> { return client_1.call("AlbumService", "listAllAlbum", {}, init); }
async function listBandaCombo_1(init?: EndpointRequestInit_1): Promise<Array<Record<string, unknown> | undefined> | undefined> { return client_1.call("AlbumService", "listBandaCombo", {}, init); }
async function updateAlbum_1(id: number | undefined, nombre: string | undefined, fecha: string | undefined, id_banda: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("AlbumService", "updateAlbum", { id, nombre, fecha, id_banda }, init); }
export { createAlbum_1 as createAlbum, listAlbum_1 as listAlbum, listAllAlbum_1 as listAllAlbum, listBandaCombo_1 as listBandaCombo, updateAlbum_1 as updateAlbum };
