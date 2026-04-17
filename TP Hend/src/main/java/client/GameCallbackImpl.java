package client;

import interfaces.GameCallback;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GameCallbackImpl extends UnicastRemoteObject implements GameCallback {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
    private final String nomJoueur;

    public GameCallbackImpl(String nomJoueur) throws RemoteException {
        super();
        this.nomJoueur = nomJoueur;
    }

    @Override
    public void onPlayerJoined(String playerName) {
        journaliser("CONNEXION", playerName);
    }

    @Override
    public void onGameEvent(String eventMessage) {
        journaliser("EVENEMENT", eventMessage);
    }

    @Override
    public void onGameOver(String winner) {
        journaliser("FIN_PARTIE", "Gagnant : " + winner);
    }

    private void journaliser(String type, String message) {
        System.out.printf("[%s] %-12s (%s) >> %s%n", LocalTime.now().format(FMT), type, nomJoueur, message);
    }
}
