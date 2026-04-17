package client;

import interfaces.Shape;
import interfaces.ShapeFactory;

import java.rmi.Naming;
import java.util.Arrays;

public class MainClient {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 1100;

        ShapeFactory fabrique = (ShapeFactory) Naming.lookup("rmi://" + host + ":" + port + "/ShapeFactory");

        System.out.println("Formes disponibles : " + Arrays.toString(fabrique.availableTypes()));

        Shape cercle    = fabrique.createShape("circle",    7.0);
        Shape rectangle = fabrique.createShape("rectangle", 3.0, 8.0);
        Shape triangle  = fabrique.createShape("triangle",  5.0, 12.0, 13.0);
        Shape pentagone = fabrique.createShape("pentagon",  4.0);
        Shape hexagone  = fabrique.createShape("hexagon",   6.0);

        afficher(cercle);
        afficher(rectangle);
        afficher(triangle);
        afficher(pentagone);
        afficher(hexagone);
    }

    private static void afficher(Shape forme) throws Exception {
        System.out.printf("%s -> aire=%.4f perimetre=%.4f%n",
                forme.describe(), forme.area(), forme.perimeter());
    }
}
