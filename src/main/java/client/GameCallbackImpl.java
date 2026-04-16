package client;

import interfaces.GameCallback;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GameCallbackImpl extends UnicastRemoteObject implements GameCallback {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final String playerName;

    public GameCallbackImpl(String playerName) throws RemoteException {
        super();
        this.playerName = playerName;
    }

    @Override
    public void onPlayerJoined(String playerName) {
        log("PLAYER_JOINED", playerName);
    }

    @Override
    public void onGameEvent(String eventMessage) {
        log("NOTIF", eventMessage);
    }

    @Override
    public void onGameOver(String winner) {
        log("GAME_OVER", "Winner: " + winner);
    }

    private void log(String type, String message) {
        System.out.printf("[%s] %s (%s): %s%n", LocalTime.now().format(FMT), type, playerName, message);
    }
}
