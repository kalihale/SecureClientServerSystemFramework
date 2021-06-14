/*
 * https://www.comrevo.com/2019/07/Sending-objects-over-sockets-Java-example-How-to-send-serialized-object-over-network-in-Java.html
 */

package ExamplesAndReferences;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SerializationClient
{
    public static void main(String[] args) throws Exception
    {
        sample obj=new sample();
        obj.name="Ramesh";
        obj.city="Pune";
        obj.contactnum="9090909090";

        Socket socket = new Socket("localhost", 7000);
        System.out.println("Connected");

        //Serialization
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        System.out.println("Sending values to the ServerSocket");
        oos.writeObject(obj);

        sample o2 = new sample();

        System.out.println("Sending values a second time");
        o2.name = "Kali";
        o2.city = "TO";
        o2.contactnum = "123456789";
        oos.writeObject(o2);

        obj.name = "tryagain";

        oos.writeObject(obj);

        System.out.println("Closing socket and terminating program.");
        socket.close();
    }
}
