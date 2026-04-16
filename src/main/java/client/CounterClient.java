package client;

import interfaces.CounterService;
import interfaces.SharedCounter;

import java.rmi.Naming;

public class CounterClient {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 1099;

        CounterService service = (CounterService) Naming.lookup("rmi://" + host + ":" + port + "/CounterService");

        SharedCounter c1 = service.createCounter("counter-1");
        SharedCounter c2 = service.createCounter("counter-2");

        service.atomicIncrement(c1, 5);
        service.atomicIncrement(c2, 7);

        System.out.println("Counter-1 value: " + c1.getValue());
        System.out.println("Counter-2 value: " + c2.getValue());
        System.out.println("Sum via service: " + service.sum(c1, c2));
    }
}
