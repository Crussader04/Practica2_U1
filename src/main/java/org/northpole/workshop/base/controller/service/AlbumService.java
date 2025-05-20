package org.northpole.workshop.base.controller.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.northpole.workshop.base.controller.dao.dao_models.DaoAlbum;
import org.northpole.workshop.base.controller.dao.dao_models.DaoBanda;
import org.northpole.workshop.base.models.Album;
import org.northpole.workshop.base.models.Banda;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class AlbumService {
    private DaoAlbum da;
    
    public AlbumService() {
        da = new DaoAlbum();
    }
    
    public void createAlbum(@NotEmpty String nombre, @NonNull Date fecha, Integer id_banda) throws Exception {
        if (nombre.trim().length() > 0 && fecha.toString().length() > 0) {
            da.getObj().setNombre(nombre);
            da.getObj().setFecha(fecha);
            da.getObj().setId_banda(id_banda);
            if (!da.save()) 
                throw new Exception("tu Album no existe :D");
        }
    }

    public void updateAlbum( Integer id, @NotEmpty String nombre, @NonNull Date fecha, Integer id_banda) throws Exception {
        if (id != null && id > 0 && nombre.trim().length() > 0 && fecha.toString().length() > 0) {
            da.setObj(da.listAll().get(id - 1));
            da.getObj().setNombre(nombre);
            da.getObj().setFecha(fecha);
            da.getObj().setId_banda(id_banda);
            if (!da.update(id - 1)) 
                throw new Exception("tu Album no podra modificarse :D");
        }
    }

    public List<HashMap> listBandaCombo(){
        List<HashMap> lista = new ArrayList<>();
        DaoBanda db = new DaoBanda();
        if (!db.listAll().isEmpty()){
            Banda[] arreglo = db.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("value", arreglo[i].getId().toString(i));
                aux.put("label", arreglo[i].getNombre());
                lista.add(aux);
            }
        }
        return lista;
    }

    public List<HashMap> listAlbum(){
        List<HashMap> list = new ArrayList<>();
        if(!da.listAll().isEmpty()){
        Album[] arreglo = da.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                HashMap<String, String> aux = new HashMap<>();
                aux.put("id", arreglo[i].getId().toString(i));
                aux.put("nombre", arreglo[i].getNombre().toString());
                aux.put("fecha", arreglo[i].getFecha().toString());
                aux.put("Banda", new DaoBanda().listAll().get(arreglo[i].getId_banda() - 1).getNombre());
                aux.put("id_banda", new DaoBanda().listAll().get(arreglo[i].getId_banda() - 1).getId().toString());
                list.add(aux);
            }
        }
        return list;
    }
}
