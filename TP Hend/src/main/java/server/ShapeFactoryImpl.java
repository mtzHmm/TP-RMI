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
            throw new RemoteException("Shape type cannot be null");
        }

        switch (type.toLowerCase(Locale.ROOT)) {
            case "circle":
                requireParamCount(type, params, 1);
                return new CircleImpl(params[0]);
            case "rectangle":
                requireParamCount(type, params, 2);
                return new RectangleImpl(params[0], params[1]);
            case "triangle":
                requireParamCount(type, params, 3);
                return new TriangleImpl(params[0], params[1], params[2]);
            case "pentagon":
                requireParamCount(type, params, 1);
                return new PentagonImpl(params[0]);
            default:
                throw new RemoteException("Unknown shape type: " + type);
        }
    }

    @Override
    public String[] availableTypes() {
        return new String[]{"circle", "rectangle", "triangle", "pentagon"};
    }

    private void requireParamCount(String type, double[] params, int expected) throws RemoteException {
        if (params == null || params.length != expected) {
            throw new RemoteException("Shape '" + type + "' expects " + expected + " parameter(s)");
        }
    }
}
