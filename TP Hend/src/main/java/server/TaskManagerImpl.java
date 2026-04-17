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
    private final Map<String, TaskHandleImpl> tasksById = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<TaskCallback> subscribers = new CopyOnWriteArrayList<>();

    public TaskManagerImpl() throws RemoteException {
        super();
    }

    @Override
    public TaskHandle createTask(TaskData data) throws RemoteException {
        if (data == null) {
            throw new RemoteException("TaskData cannot be null");
        }

        String taskId = UUID.randomUUID().toString();
        TaskHandleImpl handle = new TaskHandleImpl(taskId, data, this);
        tasksById.put(taskId, handle);

        notifyTaskCreated(data);
        return handle;
    }

    @Override
    public void subscribe(TaskCallback cb) throws RemoteException {
        if (cb == null) {
            throw new RemoteException("Callback cannot be null");
        }
        subscribers.addIfAbsent(cb);
    }

    @Override
    public List<String> listPendingTasks() {
        List<String> pending = new ArrayList<>();
        for (TaskHandleImpl handle : tasksById.values()) {
            if (!"DONE".equals(handle.getState())) {
                pending.add(handle.getTaskId());
            }
        }
        return pending;
    }

    void notifyTaskCompleted(String taskId, String result) {
        Iterator<TaskCallback> it = subscribers.iterator();
        while (it.hasNext()) {
            TaskCallback cb = it.next();
            try {
                cb.onTaskCompleted(taskId, result);
            } catch (RemoteException e) {
                subscribers.remove(cb);
            }
        }
    }

    private void notifyTaskCreated(TaskData data) {
        Iterator<TaskCallback> it = subscribers.iterator();
        while (it.hasNext()) {
            TaskCallback cb = it.next();
            try {
                cb.onTaskCreated(data);
            } catch (RemoteException e) {
                subscribers.remove(cb);
            }
        }
    }
}
