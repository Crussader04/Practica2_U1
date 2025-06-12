package org.northpole.workshop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

import org.northpole.workshop.base.controller.datastruct.list.LinkedList;

public class Practica {
    private Integer[] matriz;
    private LinkedList<Integer> lista;
    private int cont = 0;

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
        for (int i = 0; i < matriz.length; i++) {
            if (a.intValue() == matriz[i].intValue())
                cont++;
            if (cont >= 2) {
                band = true;
                break;
            }
        }
        return band;
    }

    public String[] verificar_arreglo() {
        StringBuilder resp = new StringBuilder();
        for (int i = 0; i < matriz.length; i++) {
            if (verificar_numero_arreglo(matriz[i]))
                resp.append(matriz[i].toString()).append("-");
            break;
        }
        return resp.toString().split("-");
    }

    private Boolean verificar_numero_lista(Integer a) {
        int cont = 0;
        Boolean band = false;
        for (int i = 0; i < lista.getSize(); i++) {
            if (a.intValue() == lista.get(i).intValue())
                cont++;
            if (cont >= 2) {
                band = true;
                break;
            }
        }
        return band;
    }

    public LinkedList<Integer> verificar_lista() throws Exception {
        LinkedList<Integer> resp = new LinkedList<>();
        for (int i = 0; i < lista.getSize(); i++) {
            if (verificar_numero_lista(lista.get(i).intValue()))
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

    public int getCont() {
        return cont;
    }

    public void ordenarMatriz() {
        if (matriz != null && matriz.length > 0) {
            cont = 0; // Reiniciar el contador antes de ordenar
            int[] arr = new int[matriz.length];
            for (int i = 0; i < matriz.length; i++) {
                arr[i] = matriz[i];
            }
            quickSort(arr, 0, arr.length - 1);
            for (int i = 0; i < matriz.length; i++) {
                matriz[i] = arr[i];
            }
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j <= high - 1; j++) {
            cont++; // Cuenta cada comparación
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void quickSort(int[] arr, int low, int high) {
        if (!lista.isEmpty()) {
            if (low < high) {

                int pi = partition(arr, low, high);
                quickSort(arr, low, pi - 1);
                quickSort(arr, pi + 1, high);
            }
        }
    }

    public void ordenarMatrizShellSort() {
        if (matriz != null && matriz.length > 0) {
            cont = 0; 
            int[] arr = new int[matriz.length];
            for (int i = 0; i < matriz.length; i++) {
                arr[i] = matriz[i];
            }
            shellSort(arr);
            for (int i = 0; i < matriz.length; i++) {
                matriz[i] = arr[i];
            }
        }
    }

    public void shellSort(int arr[]) {
        int n = arr.length;
        if (!lista.isEmpty()) {
            for (int gap = n / 2; gap > 0; gap /= 2) {
                for (int i = gap; i < n; i++) {
                    int temp = arr[i];
                    int j;
                    for (j = i; j >= gap && arr[j - gap] > temp; j -= gap) {
                        cont++; // Cuenta la comparación
                        arr[j] = arr[j - gap];
                    }
                    // Si el ciclo no entró, igual cuenta la comparación
                    if (j >= gap)
                        cont++;
                    arr[j] = temp;
                }
            }
        }
    }

}
