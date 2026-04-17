package server;

import interfaces.TaskData;
import interfaces.TaskHandle;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TaskHandleImpl extends UnicastRemoteObject implements TaskHandle {
    private final String idTache;
    private final TaskData donnees;
    private final TaskManagerImpl gestionnaire;

    private volatile int progression = 0;
    private volatile String etat = "WAITING";
    private volatile String resultat = "";

    public TaskHandleImpl(String idTache, TaskData donnees, TaskManagerImpl gestionnaire) throws RemoteException {
        super();
        this.idTache     = idTache;
        this.donnees     = donnees;
        this.gestionnaire = gestionnaire;
    }

    @Override
    public synchronized void updateProgress(int percent) throws RemoteException {
        if (percent < 0 || percent > 100) {
            throw new RemoteException("La progression doit être entre 0 et 100");
        }
        this.progression = percent;
        this.etat = percent == 0 ? "WAITING" : (percent < 100 ? "RUNNING" : "COMPLETED");
    }

    @Override
    public synchronized void complete(String result) throws RemoteException {
        this.progression = 100;
        this.etat        = "COMPLETED";
        this.resultat    = result == null ? "" : result;
        gestionnaire.notifyTaskCompleted(idTache, this.resultat);
    }

    @Override
    public synchronized String getStatus() {
        return "Tache " + idTache + " (" + donnees.getTitle() + ") etat=" + etat + ", progression=" + progression + "%";
    }

    public String getTaskId() {
        return idTache;
    }

    public String getState() {
        return etat;
    }
}
