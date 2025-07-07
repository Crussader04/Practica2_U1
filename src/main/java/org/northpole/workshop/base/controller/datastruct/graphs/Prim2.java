package org.northpole.workshop.base.controller.datastruct.graphs;

import org.northpole.workshop.base.controller.datastruct.list.LinkedList;

public class Prim2 {

    public String generar(int r, int c) throws Exception {
        // build maze and initialize with only walls
        StringBuilder s = new StringBuilder(c);
        for (int x = 0; x < c; x++) {
            s.append('0');
        }
        char[][] maz = new char[r][c];
        for (int x = 0; x < r; x++) {
            maz[x] = s.toString().toCharArray();
        }

        // select random point and open as start node
        Point st = new Point((int) (Math.random() * r), (int) (Math.random() * c), null);
        maz[st.r][st.c] = 'S';

        // Cambia ArrayList<Point> por tu LinkedList<Point>
        LinkedList<Point> frontier = new LinkedList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if ((x == 0 && y == 0) || (x != 0 && y != 0)) continue;
                try {
                    if (maz[st.r + x][st.c + y] == '1') continue;
                } catch (Exception e) { continue; }
                frontier.add(new Point(st.r + x, st.c + y, st));
            }
        }

        Point last = null;
        while (!frontier.isEmpty()) {
            // Elige un nodo aleatorio de la frontera
            int idx = (int) (Math.random() * frontier.getSize());
            Point cu = frontier.get(idx);
            // Elimina ese nodo de la frontera
            frontier.delete(idx, cu);

            Point op = cu.opposite();
            try {
                if (maz[cu.r][cu.c] == '0') {
                    if (maz[op.r][op.c] == '0') {
                        maz[cu.r][cu.c] = '1';
                        maz[op.r][op.c] = '1';
                        last = op;
                        for (int x = -1; x <= 1; x++) {
                            for (int y = -1; y <= 1; y++) {
                                if ((x == 0 && y == 0) || (x != 0 && y != 0)) continue;
                                try {
                                    if (maz[op.r + x][op.c + y] == '1') continue;
                                } catch (Exception e) { continue; }
                                frontier.add(new Point(op.r + x, op.c + y, op));
                            }
                        }
                    }
                }
            } catch (Exception e) { }

            if (frontier.isEmpty() && last != null) {
                maz[last.r][last.c] = 'E';
            }
        }

        s = new StringBuilder();
        for (int i = 0; i < r; i++) {
            String aux = "";
            for (int j = 0; j < c; j++) {
                aux += maz[i][j] + ",";
            }
            aux = aux.substring(0, aux.length() - 1);
            s.append(aux).append("\n");
        }
        return s.toString();
    }

    static class Point {
        Integer r;
        Integer c;
        Point parent;

        public Point(int x, int y, Point p) {
            r = x;
            c = y;
            parent = p;
        }

        public Point opposite() {
            if (parent == null) return null;
            if (!this.r.equals(parent.r)) {
                return new Point(this.r + (this.r.compareTo(parent.r)), this.c, this);
            }
            if (!this.c.equals(parent.c)) {
                return new Point(this.r, this.c + (this.c.compareTo(parent.c)), this);
            }
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        Prim2 p = new Prim2();
        String aux = p.generar(20, 20);
        System.out.println(aux);
    }
}