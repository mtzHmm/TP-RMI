package server;

import interfaces.CounterService;
import interfaces.SharedCounter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CounterServiceImpl extends UnicastRemoteObject implements CounterService {
    public CounterServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public SharedCounter createCounter(String name) throws RemoteException {
        if (name == null || name.isBlank()) {
            throw new RemoteException("Counter name is required");
        }
        return new SharedCounterImpl(name);
    }

    @Override
    public void atomicIncrement(SharedCounter c, int n) throws RemoteException {
        if (c == null) {
            throw new RemoteException("Counter cannot be null");
        }
        if (n < 0) {
            throw new RemoteException("n must be >= 0");
        }
        for (int i = 0; i < n; i++) {
            c.increment();
        }
    }

    @Override
    public int sum(SharedCounter c1, SharedCounter c2) throws RemoteException {
        if (c1 == null || c2 == null) {
            throw new RemoteException("Counters cannot be null");
        }
        return c1.getValue() + c2.getValue();
    }
}
