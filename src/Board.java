public class Board {
    int size;
    Cell[] CellBoard;

    private final int N;   // 格子总数 n^2
    private final int E;   // 边总数 2 * size + (size-1)*(size-1)*2
    private final int Ex;  // 单方向边数 n*(n-1)
    private final int[] comp;          // 每格组分 domainNum
    private final byte[] edgeConnected; // 每条边连通状态（1=连通

    public Board(int size) {
        this.size = size;
        N = size * size;
        Ex = size * (size - 1);
        E = 2 * size*(size-1) + (size - 1) * (size - 1) * 2;
        comp = new int [N];
        edgeConnected = new byte[E];
    }

    public int id(int x, int y) {
        return (x * size) + y;
    }

    /*map:
    0       size        2size       .   .   .   size*(size-1)
    1       size+1
    2
    .
    .
    .
    size-1                                      size*size
     */

    public int edgeIndexX(int i, int j) {
        return i * size + j;
    }

    public int edgeIndexY(int i, int j) {
        return Ex + (i * size + j);
    }

    public int edgeIndexXY(int i, int j) {//up_left to down_right
        return 2 * Ex + (i * (size - 1) + j);
    }

    public int edgeIndexYX(int i, int j) {//up_right to down_left x_start_from_(1,0) y_end_from_(1,size-1)
        return 2 * Ex + (size - 1) * (size - 1) + (i * (size - 1) + j);
    }


    private void endpoints(int e, int[] out) {
        int n = size;
        if (e < Ex) {                    // x: (i,j) - (i+1,j)
            int i = e / n, j = e % n;
            out[0] = id(i, j);
            out[1] = id(i + 1, j);
        } else if (e < 2 * Ex) {         // y: (i,j) - (i,j+1)
            int t = (e - Ex);
            int i = t / n, j = t % n;
            out[0] = id(i, j);
            out[1] = id(i, j + 1);
        } else if (e < (2 * Ex + (n - 1) * (n - 1))) { // xy: (i,j) - (i+1,j+1)
            int t = (e - 2 * Ex);
            int i = t / (n - 1), j = t % (n - 1);
            out[0] = id(i, j);
            out[1] = id(i + 1, j + 1);
        } else {
            int t = (e - (2 * Ex + (n - 1) * (n - 1)));
            int i = t / (n - 1), j = t % (n - 1);
            out[0] = id(i, j+1);
            out[1] = id(i+1, j);
        }
    }



}

