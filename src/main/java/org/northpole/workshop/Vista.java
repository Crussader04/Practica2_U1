package org.northpole.workshop;

public class Vista {
    public static void main(String[] args) throws Exception {
        Practica p = new Practica();
        p.cargar();

        System.out.println("----- ORDENAMIENTO QUICK SORT -----");
        long inicioQuick = System.nanoTime();
        p.ordenarMatriz();
        long finQuick = System.nanoTime();
        Practica.mostrarTiempoEjecucion(inicioQuick, finQuick);
        System.out.println("Comparaciones realizadas (QuickSort): " + p.getCont());

    
        System.out.println("----- ORDENAMIENTO SHELL SORT -----");
        long inicioShell = System.nanoTime();
        p.ordenarMatrizShellSort();
        long finShell = System.nanoTime();
        Practica.mostrarTiempoEjecucion(inicioShell, finShell);
        System.out.println("Comparaciones realizadas (ShellSort): " + p.getCont());

        /* System.out.println("----- ARREGLO -----");
        long inicioArreglo = System.nanoTime();
        p.mostrarArregloHastaRepetido();
        long finArreglo = System.nanoTime();
        Practica.mostrarTiempoEjecucion(inicioArreglo, finArreglo);

        System.out.println("----- LISTA -----");
        long inicioLista = System.nanoTime();
        p.mostrarListaHastaRepetido();
        long finLista = System.nanoTime();
        Practica.mostrarTiempoEjecucion(inicioLista, finLista);*/
    }
}