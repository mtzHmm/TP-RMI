package server;

import interfaces.Shape;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RectangleImpl extends UnicastRemoteObject implements Shape {
    private final double width;
    private final double height;

    public RectangleImpl(double width, double height) throws RemoteException {
        if (width <= 0 || height <= 0) {
            throw new RemoteException("Rectangle width and height must be > 0");
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public double perimeter() {
        return 2.0 * (width + height);
    }

    @Override
    public String describe() {
        return "Rectangle(width=" + width + ", height=" + height + ")";
    }
}
