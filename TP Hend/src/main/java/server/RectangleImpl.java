package server;

import interfaces.Shape;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RectangleImpl extends UnicastRemoteObject implements Shape {
    private final double largeur;
    private final double hauteur;

    public RectangleImpl(double largeur, double hauteur) throws RemoteException {
        if (largeur <= 0 || hauteur <= 0) {
            throw new RemoteException("La largeur et la hauteur du rectangle doivent être > 0");
        }
        this.largeur = largeur;
        this.hauteur = hauteur;
    }

    @Override
    public double area() {
        return largeur * hauteur;
    }

    @Override
    public double perimeter() {
        return 2.0 * (largeur + hauteur);
    }

    @Override
    public String describe() {
        return "Rectangle(largeur=" + largeur + ", hauteur=" + hauteur + ")";
    }
}
