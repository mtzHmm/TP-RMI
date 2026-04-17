package server;

import interfaces.Shape;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HexagonImpl extends UnicastRemoteObject implements Shape {
    private final double cote;

    public HexagonImpl(double cote) throws RemoteException {
        if (cote <= 0) {
            throw new RemoteException("Le côté de l'hexagone doit être > 0");
        }
        this.cote = cote;
    }

    @Override
    public double area() {
        return (3.0 * Math.sqrt(3) / 2.0) * cote * cote;
    }

    @Override
    public double perimeter() {
        return 6.0 * cote;
    }

    @Override
    public String describe() {
        return "Hexagone(cote=" + cote + ")";
    }
}
