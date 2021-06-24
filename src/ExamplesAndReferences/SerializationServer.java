/*
 * https://www.comrevo.com/2019/07/Sending-objects-over-sockets-Java-example-How-to-send-serialized-object-over-network-in-Java.html
 */

package ExamplesAndReferences;

import java.io.*;
import java.net.*;

public class SerializationServer
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket ss = new ServerSocket(7000);
        System.out.println("ServerSocket awaiting connections...");
        Socket socket = ss.accept();
        System.out.println("Connection from " + socket);

        //Deserialization
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        Sample obj1=(Sample)ois.readObject();

        System.out.println("Values received from Client are:-");
        System.out.println("Name:"+obj1.name);
        System.out.println("City:"+obj1.city);
        System.out.println("Contact No.:"+obj1.contactnum);

        System.out.println("Closing sockets.");
        ss.close();
        socket.close();
    }
}