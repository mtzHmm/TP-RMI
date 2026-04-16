package server;

import interfaces.CounterService;
import interfaces.GameServer;
import interfaces.ShapeFactory;
import interfaces.TaskManager;
import interfaces.VectorService;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainServer {
    public static void main(String[] args) throws Exception {
        int port = 1099;

        try {
            Registry registry = LocateRegistry.createRegistry(port);
            System.out.println("RMI registry started on port " + port + " (" + registry + ")");
        } catch (Exception ignored) {
            System.out.println("RMI registry already running on port " + port);
        }

        ShapeFactory shapeFactory = new ShapeFactoryImpl();
        GameServer gameServer = new GameServerImpl();
        VectorService vectorService = new VectorServiceImpl();
        CounterService counterService = new CounterServiceImpl();
        TaskManager taskManager = new TaskManagerImpl();

        Naming.rebind("rmi://localhost:" + port + "/ShapeFactory", shapeFactory);
        Naming.rebind("rmi://localhost:" + port + "/GameServer", gameServer);
        Naming.rebind("rmi://localhost:" + port + "/VectorService", vectorService);
        Naming.rebind("rmi://localhost:" + port + "/CounterService", counterService);
        Naming.rebind("rmi://localhost:" + port + "/TaskManager", taskManager);

        System.out.println("Services bound: ShapeFactory, GameServer, VectorService, CounterService, TaskManager");
    }
}
