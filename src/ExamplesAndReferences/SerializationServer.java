/*
 * https://www.comrevo.com/2019/07/Sending-objects-over-sockets-Java-example-How-to-send-serialized-object-over-network-in-Java.html
 */

package ExamplesAndReferences;

import org.signal.libsignal.protocol.IdentityKeyPair;
import org.signal.libsignal.protocol.SignalProtocolAddress;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SerializationServer
{
    public static void main(String[] args) throws Exception
    {
        // ／(•ㅅ•)＼ Generate identity key pair
        //           This has a public and a private key
        IdentityKeyPair serverIPK = IdentityKeyPair.generate();
        SignalProtocolAddress server = new SignalProtocolAddress("server", 0);
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