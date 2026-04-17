package server;

import interfaces.SharedCounter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedCounterImpl extends UnicastRemoteObject implements SharedCounter {
    private final String nom;
    private final AtomicInteger compteur = new AtomicInteger(0);

    public SharedCounterImpl(String nom) throws RemoteException {
        super();
        this.nom = nom;
    }

    @Override
    public void increment() {
        compteur.incrementAndGet();
    }

    @Override
    public void decrement() {
        compteur.decrementAndGet();
    }

    @Override
    public int getValue() {
        return compteur.get();
    }

    @Override
    public void reset() {
        compteur.set(0);
    }

    @Override
    public String toString() {
        return "Compteur[nom='" + nom + "', valeur=" + compteur.get() + "]";
    }
}
