package org.northpole.workshop.base.controller.dao.dao_models;

import org.northpole.workshop.base.controller.dao.AdapterDao;
import org.northpole.workshop.base.models.Cancion;

public class DaoCancion extends AdapterDao<Cancion>{
    private Cancion obj;
    
    public DaoCancion() {
        super(Cancion.class);
    }
    
    public Cancion getObj() {
        if (obj == null) 
            this.obj = new Cancion();
        return obj;
    }

    public void setObj(Cancion obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            obj.setId(listAll().getSize() + 1);
            this.persist(obj);
            return true;
        } catch (Exception e) {
            //Log de errores
            e.printStackTrace();
            System.out.println(e);
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj ,obj.getId());
            return true;
        } catch (Exception e) {
            //Log de errores
            return false;
            // TODO: handle exception
        }
    }
    
}
