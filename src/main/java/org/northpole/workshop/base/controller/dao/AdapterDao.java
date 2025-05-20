package org.northpole.workshop.base.controller.dao;

import org.northpole.workshop.base.controller.datastruct.list.LinkedList;

import java.io.File;
import java.io.FileReader;
import java.util.Scanner;
import java.io.FileWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class AdapterDao<T> implements InterfaceDao<T> {
    private Class<T> clazz;
    private Gson g;
    protected static String base_path = "data"+File.separatorChar;
    
    public AdapterDao(Class<T> clazz) {
        this.clazz = clazz;
        this.g = new GsonBuilder().setPrettyPrinting().create();
    }


    private  String readFile()throws Exception{
        File file = new File(base_path + clazz.getSimpleName() + ".json");
        if (!file.exists()) {
           saveFile("[]");
        }
        StringBuilder sb = new StringBuilder();
        try (Scanner in = new Scanner(new FileReader(file))) {
            while (in.hasNextLine()) {
                sb.append(in.nextLine()).append("\n");
            }
        } catch (Exception e) {
            throw new Exception("Error al leer el archivo: " + file.getAbsolutePath(), e);
        }
        return sb.toString();
    }

    private void saveFile(String data) throws Exception {
        File file = new File(base_path + clazz.getSimpleName() + ".json");
        if(!file.exists()) {
            file.createNewFile();
        }
        //if (!file.exists()) {
            FileWriter fw = new FileWriter(file);
            fw.write(data);
            fw.close();
            
        //}
        try (java.io.FileWriter writer = new java.io.FileWriter(file)) {
            writer.write(data);
        } catch (Exception e) {
            throw new Exception("Error al guardar el archivo: " + file.getAbsolutePath(), e);
        }
    }

    @Override
    public LinkedList<T> listAll() {
        LinkedList<T> lista = new LinkedList<>();
        try{
            String data = readFile();
            T[] m = (T[]) g.fromJson(data, java.lang.reflect.Array.newInstance(clazz, 0).getClass());
            lista.toList(m);
        }catch(Exception e){
            //TODO
        }
        
        return lista;
    }

    @Override
    public void persist(T obj) throws Exception {
        LinkedList<T> lista = listAll();
        lista.add(obj);
        saveFile(g.toJson(lista.toArray()));
    }

    @Override
    public void update(T obj, Integer pos) throws Exception {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'update'");
        LinkedList<T> lista = listAll();
        lista.update(obj, pos);
        saveFile(g.toJson(lista.toArray()));
    }

    @Override
    public void update_id(T obj, Integer id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update_id'");
    }

    @Override
    public T get(Integer id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
}
}