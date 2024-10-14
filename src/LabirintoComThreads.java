import java.util.concurrent.*;

public class LabirintoComThreads {
    public static void main(String[] args) {
        int tamanho = 10; // Tamanho do labirinto
        int numRatos = 3; // Número de ratos

        Labirinto labirinto = new Labirinto(tamanho);
        labirinto.gerarLabirinto();
        labirinto.colocarQueijo(0, 0);

        System.out.println("Labirinto inicial:");
        labirinto.imprimirLabirinto();

        ExecutorService executor = Executors.newFixedThreadPool(numRatos);

        for (int i = 0; i < numRatos; i++) {
            int[] posicao = labirinto.getPosicaoAleatoria();
            Rato rato = new Rato(i + 1, posicao[0], posicao[1], labirinto);
            executor.execute(rato);
        }

        executor.shutdown();

        try {
            while (!executor.awaitTermination(100, TimeUnit.MILLISECONDS)) {
                if (Rato.isQueijoEncontrado()) {
                    executor.shutdownNow(); // Interrompe todas as threads
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\nEstado final do labirinto:");
        labirinto.imprimirLabirinto();
    }
}