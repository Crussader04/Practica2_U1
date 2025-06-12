package org.northpole.workshop.base.controller.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.northpole.workshop.base.controller.dao.dao_models.DaoAlbum;
import org.northpole.workshop.base.controller.dao.dao_models.DaoCancion;
import org.northpole.workshop.base.controller.dao.dao_models.DaoGenero;
import org.northpole.workshop.base.controller.datastruct.list.LinkedList;
import org.northpole.workshop.base.models.Album;
import org.northpole.workshop.base.models.Cancion;
import org.northpole.workshop.base.models.Genero;
import org.northpole.workshop.base.models.TipoArchivoEnum;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class CancionService {
    private DaoCancion dc;

    public CancionService() {
        dc = new DaoCancion();
    }

    public void createCancion(@NotEmpty String nombre, Double duracion, @NotEmpty String url, @NonNull String tipo,
            Integer id_genero, Integer id_album) throws Exception {
        if (nombre.trim().length() > 0 && duracion > 0 && url.trim().length() > 0 && tipo.trim().length() > 0
                && id_album > 0 && id_genero > 0) {
            dc.getObj().setNombre(nombre);
            dc.getObj().setDuracion(duracion);
            dc.getObj().setUrl(url);
            dc.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
            dc.getObj().setId_Genero(id_genero);
            dc.getObj().setId_album(id_album);
            if (!dc.save())
                throw new Exception("tu cancion no existe :D");
        }
    }

    public void updateCancion(Integer id, @NotEmpty String nombre, @NonNull Double duracion, @NotEmpty String url,
            @NonNull String tipo, Integer id_genero, Integer id_album) throws Exception {
        if (nombre.trim().length() > 0 && duracion > 0 && url.trim().length() > 0 && tipo.trim().length() > 0
                && id_album > 0 && id_genero > 0) {
            dc.setObj(dc.listAll().get(id - 1));
            dc.getObj().setNombre(nombre);
            dc.getObj().setDuracion(duracion);
            dc.getObj().setUrl(url);
            dc.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
            dc.getObj().setId_Genero(id_genero);
            dc.getObj().setId_album(id_album);
            if (!dc.update(id - 1))
                throw new Exception("tu cancion quedo en estado estatic :D");
        }
    }

    public List<HashMap> listAlbumCombo() {
        List<HashMap> lista = new ArrayList<>();
        DaoAlbum da = new DaoAlbum();
        if (!da.listAll().isEmpty()) {
            Album[] arreglo = da.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<HashMap> listGeneroCombo() {
        List<HashMap> lista = new ArrayList<>();
        DaoGenero dg = new DaoGenero();
        if (!dg.listAll().isEmpty()) {
            Genero[] arreglo = dg.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<String> listTipo() {
        List<String> lista = new ArrayList<>();
        for (TipoArchivoEnum tipo : TipoArchivoEnum.values()) {
            lista.add(tipo.toString());
        }
        return lista;
    }

    public List<HashMap> listCancion() throws Exception {
        return Arrays.asList(dc.all().toArray());
    }

    public List<HashMap> order(String attribute, Integer type) throws Exception {
        System.out.println(attribute + " " + type);
        if (attribute == null || attribute.isEmpty()) {
            // Sin atributo: devuelve la lista original
            return Arrays.asList(dc.all().toArray());
        } else {
            // Ordena por el atributo solicitado
            return Arrays.asList(dc.orderByProperty(type, attribute).toArray());
        }
    }

    public List<HashMap> search(String attribute, String value, Integer type) throws Exception {
        if (attribute == null || attribute.isEmpty() || value == null || value.isEmpty()) {
            // Sin atributo o valor: devuelve la lista original
            return Arrays.asList(dc.all().toArray());
        } else {
            // Filtra por el atributo y valor solicitados
            return Arrays.asList(dc.search(attribute, value, type).toArray());
        }
    }

    public List<HashMap> searchAlbum (String nombreAlbum) throws Exception{
        return Arrays.asList(dc.binarySearchByAlbum(nombreAlbum).toArray());
    }

}
