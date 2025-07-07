package org.northpole.workshop;

import org.northpole.workshop.base.controller.datastruct.graphs.Adjacency;
import org.northpole.workshop.base.controller.datastruct.graphs.Prim2;
import org.northpole.workshop.base.controller.datastruct.graphs.UndirectLabelGraph;
import org.northpole.workshop.base.controller.datastruct.list.LinkedList;

public class Laberinto {
    private static char[][] laberintoActual = null;

    public static void mostrarLaberinto(int dim) throws Exception {
    laberintoActual = generarLaberinto(dim);
    imprimirLaberinto(laberintoActual, null); // null porque no hay camino a marcar
    }

    public static void resolverLaberinto(int dim) throws Exception {
        char[][] lab = laberintoActual;
        int[] nodos = mapearNodos(lab);
        int start = nodos[0], end = nodos[1], nodeCount = nodos[2];
        int[][] posToIdx = posToIdx(lab, nodeCount);
        int[][] idxToPos = idxToPos(lab, nodeCount);

        int[][] edgesArr = construirAristas(lab, posToIdx);
        LinkedList<LinkedList<Adjacency>> adj = UndirectLabelGraph.constructAdj(edgesArr, nodeCount);
        int[] dist = UndirectLabelGraph.dijkstra(adj, start, nodeCount);

        boolean[][] enCamino = reconstruirCamino(adj, dist, idxToPos, start, end, lab.length);
        imprimirLaberinto(lab, enCamino);
        System.out.println("\nLongitud del camino más corto: " + dist[end]);
    }

    private static char[][] generarLaberinto(int dim) throws Exception {
        Prim2 prim = new Prim2();
        String labStr = prim.generar(dim, dim);
        char[][] lab = new char[dim][dim];
        String[] filas = labStr.split("\n");
        for (int i = 0; i < dim; i++) {
            String[] celdas = filas[i].split(",");
            for (int j = 0; j < dim; j++) {
                lab[i][j] = celdas[j].charAt(0);
            }
        }
        return lab;
    }

    private static int[] mapearNodos(char[][] lab) {
        int dim = lab.length, start = -1, end = -1, nodeCount = 0;
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (lab[i][j] != '0') {
                    if (lab[i][j] == 'S') start = nodeCount;
                    if (lab[i][j] == 'E') end = nodeCount;
                    nodeCount++;
                }
        return new int[]{start, end, nodeCount};
    }

    private static int[][] posToIdx(char[][] lab, int nodeCount) {
        int dim = lab.length, idx = 0;
        int[][] posToIdx = new int[dim][dim];
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (lab[i][j] != '0') posToIdx[i][j] = idx++;
        return posToIdx;
    }

    private static int[][] idxToPos(char[][] lab, int nodeCount) {
        int dim = lab.length, idx = 0;
        int[][] idxToPos = new int[nodeCount][2];
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (lab[i][j] != '0') {
                    idxToPos[idx][0] = i;
                    idxToPos[idx][1] = j;
                    idx++;
                }
        return idxToPos;
    }

    private static int[][] construirAristas(char[][] lab, int[][] posToIdx) {
        int dim = lab.length;
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        LinkedList<int[]> edges = new LinkedList<>();
        for (int i = 0; i < dim; i++)
            for (int j = 0; j < dim; j++)
                if (lab[i][j] != '0')
                    for (int[] d : dirs) {
                        int ni = i + d[0], nj = j + d[1];
                        if (ni >= 0 && ni < dim && nj >= 0 && nj < dim && lab[ni][nj] != '0')
                            edges.add(new int[]{posToIdx[i][j], posToIdx[ni][nj], 1});
                    }
        int[][] edgesArr = new int[edges.getSize()][3];
        for (int i = 0; i < edges.getSize(); i++) {
            int[] e = edges.get(i);
            edgesArr[i][0] = e[0];
            edgesArr[i][1] = e[1];
            edgesArr[i][2] = e[2];
        }
        return edgesArr;
    }

    private static boolean[][] reconstruirCamino(LinkedList<LinkedList<Adjacency>> adj, int[] dist, int[][] idxToPos, int start, int end, int dim) throws Exception {
        boolean[][] enCamino = new boolean[dim][dim];
        int actual = end;
        while (actual != start) {
            int i = idxToPos[actual][0], j = idxToPos[actual][1];
            enCamino[i][j] = true;
            LinkedList<Adjacency> vecinos = adj.get(actual);
            int siguiente = -1;
            for (int k = 0; k < vecinos.getSize(); k++) {
                Adjacency v = vecinos.get(k);
                int idx = v.getdestiny();
                if (dist[idx] == dist[actual] - 1) {
                    siguiente = idx;
                    break;
                }
            }
            if (siguiente == -1) break;
            actual = siguiente;
        }
        int si = idxToPos[start][0], sj = idxToPos[start][1];
        enCamino[si][sj] = true;
        return enCamino;
    }

    private static void imprimirLaberinto(char[][] lab, boolean[][] enCamino) {
        int dim = lab.length;
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (lab[i][j] == '0') System.out.print("█");
                else if (lab[i][j] == 'S') System.out.print("S");
                else if (lab[i][j] == 'E') System.out.print("E");
                else if (enCamino != null && enCamino[i][j]) System.out.print("*");
                else System.out.print(" ");
            }
            System.out.println();
        }
    }
}
