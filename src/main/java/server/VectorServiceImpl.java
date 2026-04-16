package server;

import interfaces.Vector2D;
import interfaces.VectorService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class VectorServiceImpl extends UnicastRemoteObject implements VectorService {
    public VectorServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public double magnitude(Vector2D v) throws RemoteException {
        validate(v);
        return Math.sqrt(v.x * v.x + v.y * v.y);
    }

    @Override
    public Vector2D normalize(Vector2D v) throws RemoteException {
        validate(v);
        double mag = magnitude(v);
        if (mag == 0.0) {
            throw new RemoteException("Cannot normalize zero vector");
        }
        return new Vector2D(v.x / mag, v.y / mag);
    }

    @Override
    public Vector2D add(Vector2D a, Vector2D b) throws RemoteException {
        validate(a);
        validate(b);
        return new Vector2D(a.x + b.x, a.y + b.y);
    }

    private void validate(Vector2D v) throws RemoteException {
        if (v == null) {
            throw new RemoteException("Vector cannot be null");
        }
    }
}
