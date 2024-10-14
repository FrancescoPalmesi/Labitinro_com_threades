import java.util.Random;

public class Labirinto {
    char[][] grid;
    int tamanho;
    private Random random = new Random();

    public Labirinto(int tamanho) {
        this.tamanho = tamanho;
        this.grid = new char[tamanho][tamanho];
    }

    public void gerarLabirinto() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                grid[i][j] = random.nextDouble() < 0.3 ? '#' : ' ';
            }
        }
        // Garantir um caminho do canto superior esquerdo ao inferior direito
        for (int i = 0; i < tamanho; i++) {
            grid[i][0] = ' ';
            grid[0][i] = ' ';
        }
    }

    public void colocarQueijo(int x, int y) {
        grid[y][x] = 'Q';
    }

    public boolean isParede(int x, int y) {
        return grid[y][x] == '#';
    }

    public boolean isQueijo(int x, int y) {
        return grid[y][x] == 'Q';
    }

    public synchronized void marcarVisitado(int x, int y) {
        if (grid[y][x] == ' ') {
            grid[y][x] = '.';
        }
    }

    public int[] getPosicaoAleatoria() {
        int x, y;
        do {
            x = random.nextInt(tamanho);
            y = random.nextInt(tamanho);
        } while (isParede(x, y) || isQueijo(x, y));
        return new int[]{x, y};
    }

    public void marcarPosicaoInicial(int x, int y, int ratoId) {
        grid[y][x] = (char) ('0' + ratoId);
    }

    public void imprimirLabirinto() {
        for (int i = 0; i < tamanho; i++) {
            for (int j = 0; j < tamanho; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
