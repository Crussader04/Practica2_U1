package org.northpole.workshop;

import java.util.Scanner;

public class Vista {
    public static void main(String[] args) throws Exception {
/*         Practica p = new Practica();
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
        System.out.println("Comparaciones realizadas (ShellSort): " + p.getCont()); */

        /* System.out.println("----- ARREGLO -----");
        long inicioArreglo = System.nanoTime();
        p.mostrarArregloHastaRepetido();
        long finArreglo = System.nanoTime();
        Practica.mostrarTiempoEjecucion(inicioArreglo, finArreglo);

        System.out.println("----- LISTA -----");
        long inicioLista = System.nanoTime();
        p.mostrarListaHastaRepetido();
        long finLista = System.nanoTime();
        Practica.mostrarTiempoEjecucion(inicioLista, finLista); */

        /* UndirectLabelGraph gd = new UndirectLabelGraph<>(5, String.class);
        gd.label_vertex(1, "Nole");
        gd.label_vertex(2, "Rafa");
        gd.label_vertex(3, "Murray");
        gd.label_vertex(4, "Delpo");
        gd.label_vertex(5, "Guga");
        gd.insert_label("Nole", "Rafa", 1.0f);
        gd.insert_label("Nole", "Murray", 2.0f);

        System.out.println(gd.toString()); */

        /* int V = 5;
        int[][] edges = {
            {0, 1, 1},
            {0, 2, 2},
            {1, 2, 1},
            {1, 3, 4},
            {2, 3, 3},
            {3, 4, 1},
            {0, 4, 10}
            // Agrega más aristas si quieres
        };

        LinkedList<LinkedList<Adjacency>> adj = UndirectLabelGraph.constructAdj(edges, V);
        int[] dist = UndirectLabelGraph.dijkstra(adj, 0, V);

        for (int i = 0; i < V; i++) {
            System.out.println("Distancia de 0 a " + i + ": " + dist[i]);
        } */

        Scanner scanner = new Scanner(System.in);
        int dim = 0;

        System.out.print("Ingrese la dimensión del laberinto (min. 30, max: 100): ");
        while (dim < 30  || dim > 100) {
            dim = scanner.nextInt();
            if (dim < 30 || dim > 100) {
                System.out.print("Dimensión inválida. Ingrese un valor entre 30 y 100: ");
            }
        }    

        System.out.println("Generando laberinto de dimensión " + dim + "x" + dim );
        Laberinto.mostrarLaberinto(dim);
        
        System.out.println("desea resolver el laberinto? (s/n)");
        String respuesta = scanner.next();
        if (respuesta.equalsIgnoreCase("s")) {
            System.out.println("Resolviendo laberinto...");
            Laberinto.resolverLaberinto(dim);
        } else if (respuesta.equalsIgnoreCase("n")) {
            System.out.println("Saliendo sin resolver el laberinto.");
        }
    }
}