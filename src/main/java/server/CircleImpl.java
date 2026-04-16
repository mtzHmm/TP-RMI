package server;

import interfaces.Shape;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CircleImpl extends UnicastRemoteObject implements Shape {
    private final double radius;

    public CircleImpl(double radius) throws RemoteException {
        if (radius <= 0) {
            throw new RemoteException("Circle radius must be > 0");
        }
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }

    @Override
    public double perimeter() {
        return 2.0 * Math.PI * radius;
    }

    @Override
    public String describe() {
        return "Circle(radius=" + radius + ")";
    }
}
