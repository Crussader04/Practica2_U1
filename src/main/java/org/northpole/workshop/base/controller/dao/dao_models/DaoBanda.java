package org.northpole.workshop.base.controller.dao.dao_models;

import org.northpole.workshop.base.controller.dao.AdapterDao;
import org.northpole.workshop.base.models.Banda;

public class DaoBanda extends AdapterDao<Banda>{
    private Banda obj;
    
    public DaoBanda() {
        super(Banda.class);
    }
    
    public Banda getObj() {
        if (obj == null) 
            this.obj = new Banda();
        return obj;
    }

    public void setObj(Banda obj) {
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

    public Boolean update(Integer pos){
        try {
            this.update(obj,pos);
            return true;
        } catch (Exception e) {
            System.out.println("Objerto no guardado" + e.getMessage());
            return false;
        }
    }

    /* public Boolean update_By_Id(Integer id){
        try {
            this.update_id(obj, id);
            return true;
        } catch (Exception e) {
            System.out.println("Objerto no guardado" + e.getMessage());
            return false;
        }
    } */
}
