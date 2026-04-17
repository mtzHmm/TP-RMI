package client;

import interfaces.Vector2D;
import interfaces.VectorService;

import java.rmi.Naming;

public class VectorClient {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 1100;

        VectorService service = (VectorService) Naming.lookup("rmi://" + host + ":" + port + "/VectorService");

        Vector2D v1 = new Vector2D(6, 8);
        Vector2D v2 = new Vector2D(1, 0);

        System.out.println("Norme de (6,8)  : " + service.magnitude(v1));
        System.out.println("Norme de (1,0)  : " + service.magnitude(v2));
        System.out.println("Normalise (6,8) : " + service.normalize(v1));
        System.out.println("Somme (6,8)+(1,0) : " + service.add(v1, v2));
    }
}
