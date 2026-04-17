package server;

import interfaces.Shape;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CircleImpl extends UnicastRemoteObject implements Shape {
    private final double rayon;

    public CircleImpl(double rayon) throws RemoteException {
        if (rayon <= 0) {
            throw new RemoteException("Le rayon du cercle doit être > 0");
        }
        this.rayon = rayon;
    }

    @Override
    public double area() {
        return Math.PI * rayon * rayon;
    }

    @Override
    public double perimeter() {
        return 2.0 * Math.PI * rayon;
    }

    @Override
    public String describe() {
        return "Cercle(rayon=" + rayon + ")";
    }
}
