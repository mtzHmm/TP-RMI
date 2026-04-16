package client;

import interfaces.Vector2D;
import interfaces.VectorService;

import java.rmi.Naming;

public class VectorClient {
    public static void main(String[] args) throws Exception {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 1099;

        VectorService service = (VectorService) Naming.lookup("rmi://" + host + ":" + port + "/VectorService");

        Vector2D v = new Vector2D(3, 4);
        double before = service.magnitude(v);
        v.x = 300;
        double after = service.magnitude(new Vector2D(3, 4));

        System.out.println("Magnitude of initial (3,4): " + before);
        System.out.println("Magnitude after local client change on separate call: " + after);
        System.out.println("Normalized (3,4): " + service.normalize(new Vector2D(3, 4)));
        System.out.println("Add (1,2)+(3,4): " + service.add(new Vector2D(1, 2), new Vector2D(3, 4)));
    }
}
