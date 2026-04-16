package client;

import interfaces.Shape;
import interfaces.ShapeFactory;

import java.rmi.Naming;
import java.util.Arrays;

public class MainClient {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 1099;

        ShapeFactory factory = (ShapeFactory) Naming.lookup("rmi://" + host + ":" + port + "/ShapeFactory");

        System.out.println("Available shapes: " + Arrays.toString(factory.availableTypes()));

        Shape circle = factory.createShape("circle", 5.0);
        Shape rectangle = factory.createShape("rectangle", 4.0, 6.0);
        Shape triangle = factory.createShape("triangle", 3.0, 4.0, 5.0);

        print(circle);
        print(rectangle);
        print(triangle);
    }

    private static void print(Shape shape) throws Exception {
        System.out.printf("%s -> area=%.4f perimeter=%.4f%n", shape.describe(), shape.area(), shape.perimeter());
    }
}
