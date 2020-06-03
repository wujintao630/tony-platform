package com.tonytaotao.datastructure.recursion;

public class MiGongDemo {
    public static void main(String[] args) {

        int[][] map = new int[8][7];
        // 使用1表示墙
        for (int i = 0; i<7;i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        for (int i = 0; i<8 ; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }
        map[4][1] = 1;
        map[4][2] = 1;
        map[3][2] = 1;


        // 输出地图
        System.out.println("原始地图");
        for (int i = 0; i< 8 ;i++) {
            for (int j = 0; j< 7 ;j++) {
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }

        setWay(map, 1, 1);

        System.out.println("小球走过标识过的地图");
        for (int i = 0; i< 8 ;i++) {
            for (int j = 0; j< 7 ;j++) {
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }

    }

    /**
     * map表示地图
     * i,j表示从地图的哪个位置开始出发(1,1)
     * 如果小球能到[6][5]的位置，则说明通路找到
     * map[i][j] 0-没有走过，1-表示墙，2-通路可以走，3-该点已经走过，但是走不通
     * 走法策略：下->右->上->左，走不通就回溯
     * @param map 迷宫地图
     * @param i 哪一行开始
     * @param j 那一列开始
     * @return 找到通路就返回true，否则返回false
     */
    public static boolean setWay(int[][]map, int i, int j) {
        if (map[6][5] == 2) { // 通路已经找到，ok
            return true;
        } else {
            if (map[i][j] == 0) {
                map[i][j] = 2; //假定该点可以走通
                if (setWay(map, i+1, j)) { // 向下走
                    return true;
                } else if(setWay(map, i, j+1)) { // 向右走
                    return true;
                } else if(setWay(map, i-1,j)) { // 向上走
                    return true;
                } else if(setWay(map, i, j-1)) { // 向左走
                    return true;
                } else {
                    // 说明该点走不通，是死路
                    map[i][j] = 3;
                    return false;
                }
            } else { // 可能是1,2,3
                return false;
            }
        }
    }
}
