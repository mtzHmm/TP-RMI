package client;

import interfaces.CounterService;
import interfaces.SharedCounter;

import java.rmi.Naming;

public class CounterClient {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 1100;

        CounterService service = (CounterService) Naming.lookup("rmi://" + host + ":" + port + "/CounterService");

        SharedCounter compteurA = service.createCounter("compteur-A");
        SharedCounter compteurB = service.createCounter("compteur-B");

        service.atomicIncrement(compteurA, 10);
        service.atomicIncrement(compteurB, 3);

        System.out.println("Valeur compteur-A : " + compteurA.getValue());
        System.out.println("Valeur compteur-B : " + compteurB.getValue());
        System.out.println("Somme via service : " + service.sum(compteurA, compteurB));

        compteurA.decrement();
        System.out.println("Valeur compteur-A après décrement : " + compteurA.getValue());
    }
}
