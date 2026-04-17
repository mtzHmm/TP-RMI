package server;

import interfaces.TaskCallback;
import interfaces.TaskData;
import interfaces.TaskHandle;
import interfaces.TaskManager;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class TaskManagerImpl extends UnicastRemoteObject implements TaskManager {
    private final Map<String, TaskHandleImpl> tachesParId = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<TaskCallback> abonnes = new CopyOnWriteArrayList<>();

    public TaskManagerImpl() throws RemoteException {
        super();
    }

    @Override
    public TaskHandle createTask(TaskData donnees) throws RemoteException {
        if (donnees == null) {
            throw new RemoteException("Les données de tâche ne peuvent pas être null");
        }

        String idTache = UUID.randomUUID().toString();
        TaskHandleImpl handle = new TaskHandleImpl(idTache, donnees, this);
        tachesParId.put(idTache, handle);

        notifierTacheCreee(donnees);
        return handle;
    }

    @Override
    public void subscribe(TaskCallback cb) throws RemoteException {
        if (cb == null) {
            throw new RemoteException("Le callback ne peut pas être null");
        }
        abonnes.addIfAbsent(cb);
    }

    @Override
    public List<String> listPendingTasks() {
        List<String> enAttente = new ArrayList<>();
        for (TaskHandleImpl handle : tachesParId.values()) {
            if (!"COMPLETED".equals(handle.getState())) {
                enAttente.add(handle.getTaskId());
            }
        }
        return enAttente;
    }

    void notifyTaskCompleted(String idTache, String resultat) {
        Iterator<TaskCallback> it = abonnes.iterator();
        while (it.hasNext()) {
            TaskCallback cb = it.next();
            try {
                cb.onTaskCompleted(idTache, resultat);
            } catch (RemoteException e) {
                abonnes.remove(cb);
            }
        }
    }

    private void notifierTacheCreee(TaskData donnees) {
        Iterator<TaskCallback> it = abonnes.iterator();
        while (it.hasNext()) {
            TaskCallback cb = it.next();
            try {
                cb.onTaskCreated(donnees);
            } catch (RemoteException e) {
                abonnes.remove(cb);
            }
        }
    }
}
