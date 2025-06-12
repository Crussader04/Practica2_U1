package org.northpole.workshop.base.controller.dao.dao_models;

import java.util.HashMap;

import org.northpole.workshop.base.controller.Utiles;
import org.northpole.workshop.base.controller.dao.AdapterDao;
import org.northpole.workshop.base.controller.datastruct.list.LinkedList;
import org.northpole.workshop.base.models.Cancion;

import io.swagger.v3.oas.models.links.Link;

public class DaoCancion extends AdapterDao<Cancion> {
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
            // Log de errores
            e.printStackTrace();
            System.out.println(e);
            return false;
            // TODO: handle exception
        }
    }

    public Boolean update(Integer pos) {
        try {
            this.update(obj, obj.getId());
            return true;
        } catch (Exception e) {
            // Log de errores
            return false;
            // TODO: handle exception
        }
    }

    private int partition(HashMap<String, String>[] arr, int low, int high, Integer type, String attribute) {
        String pivot = arr[high].get(attribute).toString();
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (type == Utiles.ASCENDENTE) {
                if (arr[j].get(attribute).toString().toLowerCase().compareTo(pivot.toLowerCase()) < 0) {
                    i++;
                    swap(arr, i, j);
                }
            } else {
                if (arr[j].get(attribute).toString().toLowerCase().compareTo(pivot.toLowerCase()) > 0) {
                    i++;
                    swap(arr, i, j);
                }
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(HashMap<String, String>[] arr, int i, int j) {
        HashMap<String, String> temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void quickSort(HashMap<String, String>[] arr, int low, int high, int type, String attribute) {
        if (low < high) {
            int pi;
            pi = partition(arr, low, high, type, attribute);
            quickSort(arr, low, pi - 1, type, attribute);
            quickSort(arr, pi + 1, high, type, attribute);
        }
    }

    public LinkedList<HashMap<String, String>> orderByProperty(Integer type, String attribute) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        if (!lista.isEmpty()) {
            HashMap<String, String>[] arreglo = lista.toArray();
            quickSort(arreglo, 0, arreglo.length - 1, type, attribute);
            // Limpiar la lista original y llenarla con el nuevo orden
            lista.clear();
            for (HashMap map : arreglo) {
                lista.add(map);
            }
        }
        return lista;
    }

    private HashMap<String, String> toDict(Cancion arreglo) {
        HashMap<String, String> aux = new HashMap<>();
        DaoAlbum da = new DaoAlbum();
        DaoGenero dg = new DaoGenero();
        // da.get(1);
        aux.put("id", arreglo.getId().toString());
        aux.put("nombre", arreglo.getNombre().toString());
        aux.put("album", da.listAll().get(arreglo.getId_album() - 1).getNombre());
        aux.put("genero", dg.listAll().get(arreglo.getId_Genero() - 1).getNombre());
        aux.put("url", arreglo.getUrl().toString());
        aux.put("duracion", arreglo.getDuracion().toString());
        aux.put("tipo", arreglo.getTipo().toString());
        return aux;
    }

    public LinkedList<HashMap<String, String>> all() throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!this.listAll().isEmpty()) {
            Cancion[] arreglo = this.listAll().toArray();
            for (int i = 0; i < arreglo.length; i++) {
                lista.add(toDict(arreglo[i]));
            }
        }
        return lista;
    }

    public LinkedList<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        LinkedList<HashMap<String, String>> resp = new LinkedList<>();
        if (!this.listAll().isEmpty()) {
            lista = orderByProperty(Utiles.ASCENDENTE, attribute);
            HashMap<String, String>[] arr = lista.toArray();
            Integer n = bynaryLineal(arr, attribute, text);
            System.out.println("n: " + n);
            switch (type) {
                case 1:
                    if (n > 0) {
                        for (int i = n; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().trim().toLowerCase().startsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else if (n < 0) {
                        n *= -1;
                        for (int i = 0; i < n; i++) {
                            if (arr[i].get(attribute).toString().trim().toLowerCase().startsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else {
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().trim().toLowerCase().startsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    }
                    break;
                case 2:
                    if (n > 0) {
                        for (int i = n; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().trim().toLowerCase().endsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else if (n < 0) {
                        n *= -1;
                        for (int i = 0; i < n; i++) {
                            if (arr[i].get(attribute).toString().trim().toLowerCase().endsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    } else {
                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i].get(attribute).toString().trim().toLowerCase().endsWith(text.toLowerCase())) {
                                resp.add(arr[i]);
                            }
                        }
                    }
                    break;
                default:
                    System.out.println(attribute + " " + text + "TRES" + n);
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().trim().toLowerCase().contains(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                    break;
            }
        }
        return resp;
    }

    private Integer bynaryLineal(HashMap<String, String>[] arr, String attribute, String text) {
        Integer half = 0;
        if (!(arr.length == 0) && !text.isEmpty()) {
            half = arr.length / 2;
            int aux = 0;
            if (text.trim().toLowerCase().charAt(0) > arr[half].get(attribute).toString().trim().toLowerCase()
                    .charAt(0))
                aux = 1;
            else if (text.trim().toLowerCase().charAt(0) < arr[half].get(attribute).toString().trim().toLowerCase()
                    .charAt(0))
                aux = -1;
            ;
            half = half * aux;
        }
        return half;
    }

    public LinkedList<HashMap<String, String>> binarySearchByAlbum(String album) throws Exception {
        // Obtener la lista ordenada por el atributo "album"
        LinkedList<HashMap<String, String>> listaOrdenada = orderByProperty(Utiles.ASCENDENTE, "album");
        LinkedList<HashMap<String, String>> resultados = new LinkedList<>();

        if (listaOrdenada == null || listaOrdenada.isEmpty())
            return resultados;
        // Convertir la lista a un array para permitir acceso por índice
        HashMap<String, String>[] arr = listaOrdenada.toArray();

        int low = 0, high = arr.length - 1;
        int found = -1; // Variable para almacenar el índice del álbum encontrado

        while (low <= high) {
            int mid = low + (high - low) / 2;

            String midAlbum = arr[mid].get("album");

            // Evitar problemas si no existe la clave o es null
            if (midAlbum == null) {
                low = mid + 1;
                continue;
            }

            int cmp = midAlbum.compareToIgnoreCase(album);

            if (cmp == 0) {
                found = mid;
                break; // Álbum encontrado
            } else if (cmp < 0) {
                low = mid + 1; // Buscar en la mitad derecha
            } else {
                high = mid - 1; // Buscar en la mitad izquierda
            }
        }
        if (found != -1) {
            // Buscar hacia la izquierda
            java.util.LinkedList<HashMap<String, String>> temp = new java.util.LinkedList<>();
            int i = found;
            while (i >= 0 && arr[i].get("album") != null && arr[i].get("album").equalsIgnoreCase(album)) {
                temp.addFirst(arr[i]);
                i--;
            }
            for (HashMap<String, String> elem : temp) {
                resultados.add(elem);
            }
            // Buscar hacia la derecha
            int j = found + 1;
            while (j < arr.length && arr[j].get("album") != null && arr[j].get("album").equalsIgnoreCase(album)) {
                resultados.add(arr[j]);
                j++;
            }
        }
        return resultados;
    }

    public static void main(String[] args) throws Exception {
        DaoCancion dc = new DaoCancion();
        System.out.println(dc.listAll().print());
        System.out.println(dc.orderByProperty(1, "nombre").print());
        System.out.println(dc.get(4));
        System.out.println(dc.binarySearchByAlbum("Escencia"));

        /*
         * usar content the first and Filtering the second
         * 
         * private Cuenta login(String correo, String contrasena) throws Exception {
         * DaoCuenta daoCuenta = new DaoCuenta();
         * Cuenta cuenta = daoCuenta.getByCorreo(correo);
         * if (cuenta != null && cuenta.getContrasena().equals(contrasena)) {
         * return cuenta;
         * }
         * return null;
         * }
         * 
         * public T BinarySearchRecursive(T arr[], int a, int b, Integer id) throws
         * Exception{
         * //Base Case to Exit the Recursive Function
         * if (b < 1) {
         * return null;
         * }
         * int n = a + (b=1)/2;
         * 
         * //If number is found at mean index of start and end
         * if((Integer)getMethod("Id", arr[n])==id)
         * return arr[n];
         * 
         * //If number to search for is greater than the arr value at index 'n'
         * else if((Integer)getMethod("Id", arr[n]) >id)
         * return BinarySearchRecursive(arr,a,n-1,id);
         * 
         * //If number to search for is greater than the arr value at index 'n'
         * else
         * return BinarySearchRecursive(arr,n+1,b,id);
         * }
         */
        /*
         * System.out.println(dc.orderByName(1).print());
         * System.out.println(dc.orderByLapse(2).print());
         */
    }

    /*
     * Para el uso del login
     * public HashMap<String, object> toDicgt(Persona arreglo) {
     * HashMap<String, object> map = new HashMap<>();
     * Dao.Persona dp = new Dao.Persona();
     * map.put("correo",c.getCorreo());
     * map.put("id",c.getId());
     * map.put("estado",c.getEstado());
     * //funcionara el toString
     * 
     * return map;
     * }
     */
}
