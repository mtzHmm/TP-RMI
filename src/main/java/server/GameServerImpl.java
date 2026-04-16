package server;

import interfaces.GameCallback;
import interfaces.GameServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GameServerImpl extends UnicastRemoteObject implements GameServer {
    private final Map<String, String> namesById = new ConcurrentHashMap<>();
    private final Map<String, GameCallback> callbacksById = new ConcurrentHashMap<>();

    public GameServerImpl() throws RemoteException {
        super();
    }

    @Override
    public String joinGame(String playerName, GameCallback cb) throws RemoteException {
        if (playerName == null || playerName.isBlank()) {
            throw new RemoteException("playerName is required");
        }
        if (cb == null) {
            throw new RemoteException("callback is required");
        }

        String playerId = UUID.randomUUID().toString();
        namesById.put(playerId, playerName);
        callbacksById.put(playerId, cb);

        broadcastExcept(playerId, playerName);
        return playerId;
    }

    @Override
    public void sendAction(String playerId, String action) throws RemoteException {
        String name = namesById.get(playerId);
        if (name == null) {
            throw new RemoteException("Unknown playerId: " + playerId);
        }
        broadcastAll("ACTION from " + name + ": " + action);
    }

    @Override
    public void leaveGame(String playerId) {
        String leftName = namesById.remove(playerId);
        callbacksById.remove(playerId);
        if (leftName != null) {
            broadcastAll("Player left: " + leftName);
        }
    }

    @Override
    public int getPlayerCount() {
        return callbacksById.size();
    }

    private void broadcastExcept(String excludedPlayerId, String joinedPlayerName) {
        Iterator<Map.Entry<String, GameCallback>> it = callbacksById.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, GameCallback> entry = it.next();
            String playerId = entry.getKey();
            GameCallback cb = entry.getValue();
            if (playerId.equals(excludedPlayerId)) {
                continue;
            }
            try {
                cb.onPlayerJoined(joinedPlayerName);
                cb.onGameEvent("Player joined: " + joinedPlayerName);
            } catch (RemoteException e) {
                namesById.remove(playerId);
                callbacksById.remove(playerId, cb);
            }
        }
    }

    private void broadcastAll(String message) {
        Iterator<Map.Entry<String, GameCallback>> it = callbacksById.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, GameCallback> entry = it.next();
            String playerId = entry.getKey();
            GameCallback cb = entry.getValue();
            try {
                cb.onGameEvent(message);
            } catch (RemoteException e) {
                namesById.remove(playerId);
                callbacksById.remove(playerId, cb);
            }
        }
    }
}
