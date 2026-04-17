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
    private final Map<String, String> nomsParId       = new ConcurrentHashMap<>();
    private final Map<String, GameCallback> callbacksParId = new ConcurrentHashMap<>();

    public GameServerImpl() throws RemoteException {
        super();
    }

    @Override
    public String joinGame(String nomJoueur, GameCallback cb) throws RemoteException {
        if (nomJoueur == null || nomJoueur.isBlank()) {
            throw new RemoteException("Le nom du joueur est obligatoire");
        }
        if (cb == null) {
            throw new RemoteException("Le callback est obligatoire");
        }

        String idJoueur = UUID.randomUUID().toString();
        nomsParId.put(idJoueur, nomJoueur);
        callbacksParId.put(idJoueur, cb);

        diffuserSauf(idJoueur, nomJoueur);
        return idJoueur;
    }

    @Override
    public void sendAction(String idJoueur, String action) throws RemoteException {
        String nom = nomsParId.get(idJoueur);
        if (nom == null) {
            throw new RemoteException("Identifiant joueur inconnu : " + idJoueur);
        }
        diffuserTous("ACTION de " + nom + " : " + action);
    }

    @Override
    public void leaveGame(String idJoueur) {
        String nomParti = nomsParId.remove(idJoueur);
        callbacksParId.remove(idJoueur);
        if (nomParti != null) {
            diffuserTous("Joueur déconnecté : " + nomParti);
        }
    }

    @Override
    public int getPlayerCount() {
        return callbacksParId.size();
    }

    private void diffuserSauf(String idExclu, String nomJoueurConnecte) {
        Iterator<Map.Entry<String, GameCallback>> it = callbacksParId.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, GameCallback> entree = it.next();
            String idJoueur = entree.getKey();
            GameCallback cb = entree.getValue();
            if (idJoueur.equals(idExclu)) {
                continue;
            }
            try {
                cb.onPlayerJoined(nomJoueurConnecte);
                cb.onGameEvent("Nouveau joueur : " + nomJoueurConnecte);
            } catch (RemoteException e) {
                nomsParId.remove(idJoueur);
                callbacksParId.remove(idJoueur, cb);
            }
        }
    }

    private void diffuserTous(String message) {
        Iterator<Map.Entry<String, GameCallback>> it = callbacksParId.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, GameCallback> entree = it.next();
            String idJoueur = entree.getKey();
            GameCallback cb = entree.getValue();
            try {
                cb.onGameEvent(message);
            } catch (RemoteException e) {
                nomsParId.remove(idJoueur);
                callbacksParId.remove(idJoueur, cb);
            }
        }
    }
}
