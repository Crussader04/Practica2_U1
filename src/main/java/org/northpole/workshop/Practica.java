package org.northpole.workshop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.northpole.workshop.base.controller.datastruct.list.LinkedList;

public class Practica {
    private Integer [] matriz;
    private LinkedList <Integer> lista;


    public void cargar() throws Exception {
    try (BufferedReader br = new BufferedReader(new FileReader("data.txt"))) {
        StringBuilder sb = new StringBuilder();
        String linea;
        while ((linea = br.readLine()) != null) {
            sb.append(linea).append(" ");
        }

        StringTokenizer st = new StringTokenizer(sb.toString(), " ,");
        matriz = new Integer[st.countTokens()];
        lista = new LinkedList<>();

        int index = 0;
        while (st.hasMoreTokens()) {
            int num = Integer.parseInt(st.nextToken());
            matriz[index++] = num;
            lista.add(num);
        }

    } catch (IOException e) {
        System.out.println("Error al leer el archivo: " + e.getMessage());
    }
}

    private Boolean verificar_numero_arreglo(Integer a) {
        int cont = 0;
        Boolean band = false;
        //StringBuilder resp = new StringBuilder();
        for(int i = 0; i < matriz.length; i++) {
            if(a.intValue() == matriz[i].intValue()) 
                cont++;
            if(cont>=2){
                band = true;
                break;
            }
        }
        return band;//resp.toString().split("-");
    }

    public String[] verificar_arreglo() {
        StringBuilder resp = new StringBuilder();
        for(int i = 0; i < matriz.length; i++) {
            if(verificar_numero_arreglo(matriz[i]))
                resp.append(matriz[i].toString()).append("-");
                break;
        }
        return resp.toString().split("-");
    }

    private Boolean verificar_numero_lista(Integer a) {
        int cont = 0;
        Boolean band = false;
        //StringBuilder resp = new StringBuilder();
        for(int i = 0; i < lista.getSize(); i++) {
            if(a.intValue() == lista.get(i).intValue()) 
                cont++;
            if(cont>=2){
                band = true;
                break;
            }
        }
        return band;//resp.toString().split("-");
    }
    public LinkedList<Integer> verificar_lista() throws Exception {
        LinkedList<Integer> resp = new LinkedList<>();
        for(int i = 0; i < lista.getSize(); i++) {
            if(verificar_numero_lista(lista.get(i).intValue()))
                resp.add(lista.get(i));
                break;
        }
        return resp;
    }

    public void mostrarArregloHastaRepetido() {
    HashSet<Integer> vistos = new HashSet<>();
    for (int i = 0; i < matriz.length; i++) {
        int actual = matriz[i];
        if (vistos.contains(actual)) {
            System.out.println("Número repetido encontrado en arreglo: " + actual);
            break;
        }
        System.out.println(actual);
        vistos.add(actual);
    }
}

public void mostrarListaHastaRepetido() throws Exception {
    HashSet<Integer> vistos = new HashSet<>();
    for (int i = 0; i < lista.getSize(); i++) {
        int actual = lista.get(i);
        if (vistos.contains(actual)) {
            System.out.println("Número repetido encontrado en lista: " + actual);
            break;
        }
        System.out.println(actual);
        vistos.add(actual);
    }
}

    public static void mostrarTiempoEjecucion(long startTime, long endTime) {
        long duration = endTime - startTime;
        System.out.println("Tiempo de ejecución: " + duration + " nanosegundos");
    }

}
