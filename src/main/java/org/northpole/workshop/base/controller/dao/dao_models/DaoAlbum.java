package org.northpole.workshop.base.controller.dao.dao_models;


import org.northpole.workshop.base.controller.dao.AdapterDao;
import org.northpole.workshop.base.models.Album;

public class DaoAlbum extends AdapterDao<Album>{
    private Album obj;
    
    public DaoAlbum() {
        super (Album.class);
    }
    
    public Album getObj() {
        if (obj == null) 
            this.obj = new Album();
        return obj;
    }

    public void setObj(Album obj) {
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
