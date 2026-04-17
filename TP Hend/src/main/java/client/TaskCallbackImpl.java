package client;

import interfaces.TaskCallback;
import interfaces.TaskData;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TaskCallbackImpl extends UnicastRemoteObject implements TaskCallback {
    public TaskCallbackImpl() throws RemoteException {
        super();
    }

    @Override
    public void onTaskCreated(TaskData donnees) {
        System.out.println("[CALLBACK] Nouvelle tâche créée : " + donnees);
    }

    @Override
    public void onTaskCompleted(String idTache, String resultat) {
        System.out.println("[CALLBACK] Tâche terminée [id=" + idTache + "] résultat=" + resultat);
    }
}
