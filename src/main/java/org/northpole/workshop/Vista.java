package org.northpole.workshop;

public class Vista {
    public static void main(String[] args) throws Exception {
        Practica p = new Practica();
        p.cargar();

        System.out.println("----- ARREGLO -----");
        long inicioArreglo = System.nanoTime();
        p.mostrarArregloHastaRepetido();
        long finArreglo = System.nanoTime();
        Practica.mostrarTiempoEjecucion(inicioArreglo, finArreglo);

        System.out.println("----- LISTA -----");
        long inicioLista = System.nanoTime();
        p.mostrarListaHastaRepetido();
        long finLista = System.nanoTime();
        Practica.mostrarTiempoEjecucion(inicioLista, finLista);
    }
}