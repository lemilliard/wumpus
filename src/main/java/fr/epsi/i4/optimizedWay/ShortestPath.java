package fr.epsi.i4.optimizedWay;

import java.util.ArrayList;

public class ShortestPath {

    public int max = 100;
    public int col;
    public int row;
    public int colStart;
    public int rowStart;
    public int colEnd;
    public int rowEnd;
    public int[][] djikstra;
    public ArrayList<int[]> nodeList;
    public boolean[][][][] proxi;
    public boolean end;

    public ArrayList<Duo> lastNode;
    public ArrayList<int[]> path;

    public ShortestPath(int col, int row, int colStart, int rowStart, int colEnd, int rowEnd, boolean[][][][] proxi) {
        col = col + 2;
        row = row + 2;
        this.col = col;
        this.row = row;
        this.colEnd = colEnd;
        this.rowEnd = rowEnd;
        this.rowStart = rowStart;
        this.colStart = colStart;
        nodeList = new ArrayList<int[]>();
        lastNode = new ArrayList<Duo>();
        path = new ArrayList<>();
        end = false;

        djikstra = new int[col][row];
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                djikstra[i][j] = 10000;
                int[] node = new int[2];
                node[0] = i;
                node[1] = j;
                if (i != 0 && j != 0 && i != col - 1 && j != row - 1) {
                    nodeList.add(node);
                    lastNode.add(new Duo(node, node));
                }
            }
        }
        djikstra[colStart][rowStart] = 0;
        this.proxi = new boolean[max][max][max][max];
        setProxi(proxi);
    }

    public void djikstra() {
        System.out.println("DJIKSTRA");
        int[] mem = new int[2];
        mem[0] = colStart;
        mem[1] = rowStart;
        nodeList.remove(mem);
        System.out.println(proxi[mem[0]][mem[1]][mem[0] - 1][mem[1]]);
        while (djikstra[colEnd][rowEnd] == 10000 && !end) {
            mem = findMin();
            nodeList.remove(mem);
            if (!end && proxi[mem[0]][mem[1]][mem[0] - 1][mem[1]]) {
                int[] mem2 = new int[2];
                mem2[0] = mem[0] - 1;
                mem2[1] = mem[1];
                updateDist(mem, mem2);
            }
            if (!end && proxi[mem[0]][mem[1]][mem[0] + 1][mem[1]]) {
                int[] mem2 = new int[2];
                mem2[0] = mem[0] + 1;
                mem2[1] = mem[1];
                updateDist(mem, mem2);
            }
            if (!end && proxi[mem[0]][mem[1]][mem[0]][mem[1] - 1]) {
                int[] mem2 = new int[2];
                mem2[0] = mem[0];
                mem2[1] = mem[1] - 1;
                updateDist(mem, mem2);
            }
            if (!end && proxi[mem[0]][mem[1]][mem[0]][mem[1] + 1]) {
                int[] mem2 = new int[2];
                mem2[0] = mem[0];
                mem2[1] = mem[1] + 1;
                updateDist(mem, mem2);
            }
            if (!end) {
                proxi[mem[0]][mem[1]][mem[0] - 1][mem[1]] = false;
                proxi[mem[0]][mem[1]][mem[0] + 1][mem[1]] = false;
                proxi[mem[0]][mem[1]][mem[0]][mem[1] - 1] = false;
                proxi[mem[0]][mem[1]][mem[0]][mem[1] + 1] = false;

                proxi[mem[0] - 1][mem[1]][mem[0]][mem[1]] = false;
                proxi[mem[0] + 1][mem[1]][mem[0]][mem[1]] = false;
                proxi[mem[0]][mem[1] - 1][mem[0]][mem[1]] = false;
                proxi[mem[0]][mem[1] + 1][mem[0]][mem[1]] = false;
            }
        }
    }

    public void showDjikstra() {
        System.out.println("Point de depart: " + colStart + " " + rowStart);
        System.out.println("Point d'arrivée: " + colEnd + " " + rowEnd);
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (djikstra[i][j] == 10000) {
                    System.out.print("x ");
                } else {
                    System.out.print(djikstra[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println("PATH");
        System.out.println(path.size());
        if (!end) {
            path();
            System.out.println("PATH OK");
            System.out.println(path.size());
            for (int i = 0; i < path.size(); i++) {
                System.out.print("[" + path.get(i)[0] + "," + path.get(i)[1] + "]");
            }
        } else {
            System.out.println("PAS DE CHEMIN TROUVE");
        }
        System.out.println("DJIKSTRA OK");
    }

    public int[] findMin() {
        int mini = 10000;
        int[] mem = new int[2];
        mem[0] = -1;
        mem[1] = -1;

        for (int i = 0; i < nodeList.size(); i++) {
            int[] node = nodeList.get(i);
            int nCol = node[0];
            int nRow = node[1];

            if (proxi[nCol][nRow][nCol - 1][nRow] && djikstra[nCol - 1][nRow] < mini) {
                mini = djikstra[nCol - 1][nRow];
                mem[0] = nCol - 1;
                mem[1] = nRow;
            }
            if (proxi[nCol][nRow][nCol + 1][nRow] && djikstra[nCol + 1][nRow] < mini) {
                mini = djikstra[nCol + 1][nRow];
                mem[0] = nCol + 1;
                mem[1] = nRow;
            }
            if (proxi[nCol][nRow][nCol][nRow + 1] && djikstra[nCol][nRow + 1] < mini) {
                mini = djikstra[nCol][nRow + 1];
                mem[0] = nCol;
                mem[1] = nRow + 1;
            }
            if (proxi[nCol][nRow][nCol][nRow - +1] && djikstra[nCol][nRow - 1] < mini) {
                mini = djikstra[nCol][nRow - 1];
                mem[0] = nCol;
                mem[1] = nRow - 1;
            }
        }
        if (mem[0] == -1 && mem[1] == -1) {
            end = true;
        }
        return mem;

    }

    public void getProxi(int[] current) {

    }

    public void updateDist(int[] node1, int[] node2) {
        if (djikstra[node2[0]][node2[1]] > djikstra[node1[0]][node1[1]] + 1) {
            djikstra[node2[0]][node2[1]] = djikstra[node1[0]][node1[1]] + 1;
            for (int i = 0; i < lastNode.size(); i++) {
                if (lastNode.get(i).getFirst()[0] == node2[0] && lastNode.get(i).getFirst()[1] == node2[1]) {
                    lastNode.get(i).setSecond(node1);
                }
            }
        }
    }

    public void path() {
        if (path.size() == 0) {
            int itX = colEnd;
            int itY = rowEnd;

            while (itX != colStart || itY != rowStart) {
                int tempItX = -1;
                int tempItY = -1;
                int[] node = new int[2];
                node[0] = itX;
                node[1] = itY;
                path.add(node);
                for (int i = 0; i < lastNode.size(); i++) {
                    if (lastNode.get(i).getFirst()[0] == itX && lastNode.get(i).getFirst()[1] == itY) {
                        tempItX = lastNode.get(i).getSecond()[0];
                        tempItY = lastNode.get(i).getSecond()[1];
                    }
                }
                itX = tempItX;
                itY = tempItY;
            }
        }
    }

    public void setProxi(boolean[][][][] proxi) {
        for (int i = 0; i < max-2; i++) {
            for (int j = 0; j < max-2; j++) {
                for (int k = 0; k < max-2; k++) {
                    for (int l = 0; l < max-2; l++) {
                        this.proxi[i][j][k][l] = proxi[i][j][k][l];
                        //System.out.println(i +" "+ j +" "+ k +" "+ l);
                    }
                }
            }
        }
        System.out.println("ok setProxi");
    }
}
