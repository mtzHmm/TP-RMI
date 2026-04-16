package server;

import interfaces.Shape;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PentagonImpl extends UnicastRemoteObject implements Shape {
    private final double side;

    public PentagonImpl(double side) throws RemoteException {
        if (side <= 0) {
            throw new RemoteException("Pentagon side must be > 0");
        }
        this.side = side;
    }

    @Override
    public double area() {
        return 0.25 * Math.sqrt(5 * (5 + 2 * Math.sqrt(5))) * side * side;
    }

    @Override
    public double perimeter() {
        return 5.0 * side;
    }

    @Override
    public String describe() {
        return "Pentagon(side=" + side + ")";
    }
}
