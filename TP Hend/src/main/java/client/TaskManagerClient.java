package client;

import interfaces.TaskData;
import interfaces.TaskHandle;
import interfaces.TaskManager;

import java.rmi.Naming;

public class TaskManagerClient {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 1100;

        TaskManager gestionnaire = (TaskManager) Naming.lookup("rmi://" + host + ":" + port + "/TaskManager");
        gestionnaire.subscribe(new TaskCallbackImpl());

        TaskHandle t1 = gestionnaire.createTask(new TaskData("Rédiger le rapport", "Rapport de fin de projet", 1));
        TaskHandle t2 = gestionnaire.createTask(new TaskData("Corriger le bug #42", "Erreur dans le module authentification", 3));
        TaskHandle t3 = gestionnaire.createTask(new TaskData("Déployer en production", "Mise en production version 2.0", 2));

        t1.updateProgress(50);
        t2.updateProgress(80);
        t3.updateProgress(10);

        System.out.println("Tâches en attente : " + gestionnaire.listPendingTasks());

        t1.complete("Rapport envoyé");
        t2.complete("Bug corrigé et testé");

        System.out.println(t1.getStatus());
        System.out.println(t2.getStatus());
        System.out.println(t3.getStatus());
        System.out.println("Tâches en attente après complétions : " + gestionnaire.listPendingTasks());
    }
}
