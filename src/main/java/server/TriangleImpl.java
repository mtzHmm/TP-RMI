package server;

import interfaces.Shape;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class TriangleImpl extends UnicastRemoteObject implements Shape {
    private final double a;
    private final double b;
    private final double c;

    public TriangleImpl(double a, double b, double c) throws RemoteException {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new RemoteException("Triangle sides must be > 0");
        }
        if (a + b <= c || a + c <= b || b + c <= a) {
            throw new RemoteException("Triangle sides do not satisfy triangle inequality");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double area() {
        double s = perimeter() / 2.0;
        return Math.sqrt(s * (s - a) * (s - b) * (s - c));
    }

    @Override
    public double perimeter() {
        return a + b + c;
    }

    @Override
    public String describe() {
        return "Triangle(a=" + a + ", b=" + b + ", c=" + c + ")";
    }
}
