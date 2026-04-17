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
    public void onTaskCreated(TaskData data) {
        System.out.println("Task created: " + data);
    }

    @Override
    public void onTaskCompleted(String taskId, String res) {
        System.out.println("Task completed: " + taskId + " result=" + res);
    }
}
