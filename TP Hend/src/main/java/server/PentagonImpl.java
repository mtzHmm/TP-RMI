package server;

import interfaces.Shape;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PentagonImpl extends UnicastRemoteObject implements Shape {
    private final double cote;

    public PentagonImpl(double cote) throws RemoteException {
        if (cote <= 0) {
            throw new RemoteException("Le côté du pentagone doit être > 0");
        }
        this.cote = cote;
    }

    @Override
    public double area() {
        return 0.25 * Math.sqrt(5 * (5 + 2 * Math.sqrt(5))) * cote * cote;
    }

    @Override
    public double perimeter() {
        return 5.0 * cote;
    }

    @Override
    public String describe() {
        return "Pentagone(cote=" + cote + ")";
    }
}
