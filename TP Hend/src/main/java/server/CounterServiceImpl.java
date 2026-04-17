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
    public SharedCounter createCounter(String nom) throws RemoteException {
        if (nom == null || nom.isBlank()) {
            throw new RemoteException("Le nom du compteur est obligatoire");
        }
        return new SharedCounterImpl(nom);
    }

    @Override
    public void atomicIncrement(SharedCounter c, int n) throws RemoteException {
        if (c == null) {
            throw new RemoteException("Le compteur ne peut pas être null");
        }
        if (n < 0) {
            throw new RemoteException("n doit être >= 0");
        }
        for (int i = 0; i < n; i++) {
            c.increment();
        }
    }

    @Override
    public int sum(SharedCounter c1, SharedCounter c2) throws RemoteException {
        if (c1 == null || c2 == null) {
            throw new RemoteException("Les compteurs ne peuvent pas être null");
        }
        return c1.getValue() + c2.getValue();
    }
}
