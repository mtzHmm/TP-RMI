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
        valider(v);
        return Math.sqrt(v.x * v.x + v.y * v.y);
    }

    @Override
    public Vector2D normalize(Vector2D v) throws RemoteException {
        valider(v);
        double norme = magnitude(v);
        if (norme == 0.0) {
            throw new RemoteException("Impossible de normaliser un vecteur nul");
        }
        return new Vector2D(v.x / norme, v.y / norme);
    }

    @Override
    public Vector2D add(Vector2D a, Vector2D b) throws RemoteException {
        valider(a);
        valider(b);
        return new Vector2D(a.x + b.x, a.y + b.y);
    }

    private void valider(Vector2D v) throws RemoteException {
        if (v == null) {
            throw new RemoteException("Le vecteur ne peut pas être null");
        }
    }
}
