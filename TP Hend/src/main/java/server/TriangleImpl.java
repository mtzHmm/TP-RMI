package server;

import interfaces.Shape;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TriangleImpl extends UnicastRemoteObject implements Shape {
    private final double cote1;
    private final double cote2;
    private final double cote3;

    public TriangleImpl(double cote1, double cote2, double cote3) throws RemoteException {
        if (cote1 <= 0 || cote2 <= 0 || cote3 <= 0) {
            throw new RemoteException("Les côtés du triangle doivent être > 0");
        }
        if (cote1 + cote2 <= cote3 || cote1 + cote3 <= cote2 || cote2 + cote3 <= cote1) {
            throw new RemoteException("Les côtés ne vérifient pas l'inégalité triangulaire");
        }
        this.cote1 = cote1;
        this.cote2 = cote2;
        this.cote3 = cote3;
    }

    @Override
    public double area() {
        double s = perimeter() / 2.0;
        return Math.sqrt(s * (s - cote1) * (s - cote2) * (s - cote3));
    }

    @Override
    public double perimeter() {
        return cote1 + cote2 + cote3;
    }

    @Override
    public String describe() {
        return "Triangle(cote1=" + cote1 + ", cote2=" + cote2 + ", cote3=" + cote3 + ")";
    }
}
