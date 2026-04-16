package server;

import interfaces.TaskData;
import interfaces.TaskHandle;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TaskHandleImpl extends UnicastRemoteObject implements TaskHandle {
    private final String taskId;
    private final TaskData data;
    private final TaskManagerImpl manager;

    private volatile int progress = 0;
    private volatile String state = "PENDING";
    private volatile String result = "";

    public TaskHandleImpl(String taskId, TaskData data, TaskManagerImpl manager) throws RemoteException {
        super();
        this.taskId = taskId;
        this.data = data;
        this.manager = manager;
    }

    @Override
    public synchronized void updateProgress(int percent) throws RemoteException {
        if (percent < 0 || percent > 100) {
            throw new RemoteException("Progress must be between 0 and 100");
        }
        this.progress = percent;
        this.state = percent == 0 ? "PENDING" : (percent < 100 ? "IN_PROGRESS" : "DONE");
    }

    @Override
    public synchronized void complete(String result) throws RemoteException {
        this.progress = 100;
        this.state = "DONE";
        this.result = result == null ? "" : result;
        manager.notifyTaskCompleted(taskId, this.result);
    }

    @Override
    public synchronized String getStatus() {
        return "Task " + taskId + " (" + data.getTitle() + ") state=" + state + ", progress=" + progress + "%";
    }

    public String getTaskId() {
        return taskId;
    }

    public String getState() {
        return state;
    }
}
