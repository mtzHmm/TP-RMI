package client;

import interfaces.GameServer;

import java.rmi.Naming;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class GameSimulationClient {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 1100;

        GameServer serveur = (GameServer) Naming.lookup("rmi://" + host + ":" + port + "/GameServer");

        int nbJoueurs = 4;
        CountDownLatch fin = new CountDownLatch(nbJoueurs);

        for (int i = 1; i <= nbJoueurs; i++) {
            String nomJoueur = "Joueur-" + i;
            new Thread(() -> {
                try {
                    GameCallbackImpl cb = new GameCallbackImpl(nomJoueur);
                    String idJoueur = serveur.joinGame(nomJoueur, cb);
                    Random rnd = new Random();
                    for (int n = 1; n <= 3; n++) {
                        serveur.sendAction(idJoueur, "action-" + n + "-" + rnd.nextInt(100));
                        Thread.sleep(200L);
                    }
                    Thread.sleep(1500L);
                    serveur.leaveGame(idJoueur);
                } catch (Exception e) {
                    System.err.println("Erreur simulation pour " + nomJoueur + " : " + e.getMessage());
                } finally {
                    fin.countDown();
                }
            }, nomJoueur + "-thread").start();
        }

        fin.await();
        System.out.println("Simulation terminée. Joueurs restants : " + serveur.getPlayerCount());
    }
}
