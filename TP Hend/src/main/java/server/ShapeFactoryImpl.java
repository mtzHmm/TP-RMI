package server;

import interfaces.Shape;
import interfaces.ShapeFactory;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Locale;

public class ShapeFactoryImpl extends UnicastRemoteObject implements ShapeFactory {
    public ShapeFactoryImpl() throws RemoteException {
        super();
    }

    @Override
    public Shape createShape(String type, double... params) throws RemoteException {
        if (type == null) {
            throw new RemoteException("Le type de forme ne peut pas être null");
        }

        switch (type.toLowerCase(Locale.ROOT)) {
            case "circle":
                verifierParams(type, params, 1);
                return new CircleImpl(params[0]);
            case "rectangle":
                verifierParams(type, params, 2);
                return new RectangleImpl(params[0], params[1]);
            case "triangle":
                verifierParams(type, params, 3);
                return new TriangleImpl(params[0], params[1], params[2]);
            case "pentagon":
                verifierParams(type, params, 1);
                return new PentagonImpl(params[0]);
            case "hexagon":
                verifierParams(type, params, 1);
                return new HexagonImpl(params[0]);
            default:
                throw new RemoteException("Type de forme inconnu : " + type);
        }
    }

    @Override
    public String[] availableTypes() {
        return new String[]{"circle", "rectangle", "triangle", "pentagon", "hexagon"};
    }

    private void verifierParams(String type, double[] params, int attendu) throws RemoteException {
        if (params == null || params.length != attendu) {
            throw new RemoteException("La forme '" + type + "' attend " + attendu + " paramètre(s)");
        }
    }
}
