package client;

import interfaces.GameServer;

import java.rmi.Naming;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class GameSimulationClient {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 1099;

        GameServer server = (GameServer) Naming.lookup("rmi://" + host + ":" + port + "/GameServer");

        int players = 3;
        CountDownLatch done = new CountDownLatch(players);

        for (int i = 1; i <= players; i++) {
            String playerName = "Player-" + i;
            new Thread(() -> {
                try {
                    GameCallbackImpl cb = new GameCallbackImpl(playerName);
                    String playerId = server.joinGame(playerName, cb);
                    Random rnd = new Random();
                    for (int n = 1; n <= 2; n++) {
                        server.sendAction(playerId, "action-" + n + "-" + rnd.nextInt(100));
                        Thread.sleep(300L);
                    }
                    Thread.sleep(2000L);
                    server.leaveGame(playerId);
                } catch (Exception e) {
                    System.err.println("Simulation error for " + playerName + ": " + e.getMessage());
                } finally {
                    done.countDown();
                }
            }, playerName + "-thread").start();
        }

        done.await();
        System.out.println("Simulation finished. Remaining players: " + server.getPlayerCount());
    }
}
