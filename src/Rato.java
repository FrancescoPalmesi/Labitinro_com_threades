import java.util.*;

public class Rato implements Runnable {
    private int id;
    private int x, y;
    private Labirinto labirinto;
    private Set<String> caminhoPercorrido = new HashSet<>();
    private Random random = new Random();
    private static volatile boolean queijoEncontrado = false;
    private static int ratosTerminados = 0;
    private static final Object lock = new Object();

    public Rato(int id, int x, int y, Labirinto labirinto) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.labirinto = labirinto;
        this.labirinto.marcarPosicaoInicial(x, y, id);
    }

    @Override
    public void run() {
        while (!queijoEncontrado) {
            if (labirinto.isQueijo(x, y)) {
                queijoEncontrado = true;
                System.out.println("\n🧀 QUEIJO ENCONTRADO! 🧀");
                System.out.println("Rato " + id + " encontrou o queijo na posição (" + x + "," + y + ")!");
                System.out.println("Todos os outros ratos param de procurar.");
                break;
            }
            if (!mover()) {
                // Rato ficou preso
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        synchronized (lock) {
            ratosTerminados++;
        }
    }

    private boolean mover() {
        int[] direcoes = {0, 1, 2, 3}; // 0: cima, 1: direita, 2: baixo, 3: esquerda
        Collections.shuffle(Arrays.asList(direcoes));

        for (int direcao : direcoes) {
            int novoX = x;
            int novoY = y;

            switch (direcao) {
                case 0: novoY--; break; // Cima
                case 1: novoX++; break; // Direita
                case 2: novoY++; break; // Baixo
                case 3: novoX--; break; // Esquerda
            }

            if (novoX >= 0 && novoX < labirinto.tamanho && novoY >= 0 && novoY < labirinto.tamanho
                    && !labirinto.isParede(novoX, novoY) && !caminhoPercorrido.contains(novoX + "," + novoY)) {
                x = novoX;
                y = novoY;
                caminhoPercorrido.add(x + "," + y);
                labirinto.marcarVisitado(x, y);
                System.out.println("Rato " + id + " moveu para (" + x + "," + y + ")");
                return true; // O rato conseguiu se mover
            }
        }

        System.out.println("Rato " + id + " está preso na posição (" + x + "," + y + ")");
        return false; // O rato não conseguiu se mover
    }

    public static boolean isQueijoEncontrado() {
        return queijoEncontrado;
    }

    public static int getRatosTerminados() {
        return ratosTerminados;
    }
}
