package org.northpole.workshop.base.controller.service;

import java.util.Arrays;
import java.util.List;

import org.northpole.workshop.base.controller.dao.dao_models.DaoGenero;
import org.northpole.workshop.base.models.Genero;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class GeneroService {
    private DaoGenero dg;
    
    public GeneroService() {
        dg = new DaoGenero();
    }

    public void createGenero(@NotEmpty String nombre) throws Exception {
        if (nombre.trim().length() > 0) {
            dg.getObj().setNombre(nombre);
            if (!dg.save()) 
                throw new Exception("tu genero no existe :D");
        }
    }

    public void updateGenero(Integer id, @NotEmpty String nombre) throws Exception {
        if (id != null && id > 0 && nombre.trim().length() > 0) {
            dg.setObj(dg.listAll().get(id - 1));
            dg.getObj().setNombre(nombre);
            if (!dg.update(id - 1)) 
                throw new Exception("tu genero no podra modificarse :D");
        }
    }

    public List<Genero> listAllGenero() {
        return Arrays.asList(dg.listAll().toArray());
    }
    
}
