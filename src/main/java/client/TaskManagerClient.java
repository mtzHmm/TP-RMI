package client;

import interfaces.TaskData;
import interfaces.TaskHandle;
import interfaces.TaskManager;

import java.rmi.Naming;

public class TaskManagerClient {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 1099;

        TaskManager manager = (TaskManager) Naming.lookup("rmi://" + host + ":" + port + "/TaskManager");
        manager.subscribe(new TaskCallbackImpl());

        TaskHandle h1 = manager.createTask(new TaskData("Write report", "Prepare weekly report", 2));
        TaskHandle h2 = manager.createTask(new TaskData("Fix bug", "Resolve login issue", 3));

        h1.updateProgress(25);
        h2.updateProgress(40);

        System.out.println("Pending task IDs: " + manager.listPendingTasks());

        h1.complete("Report sent");
        h2.complete("Bug fixed and deployed");

        System.out.println(h1.getStatus());
        System.out.println(h2.getStatus());
        System.out.println("Pending task IDs after completion: " + manager.listPendingTasks());
    }
}
