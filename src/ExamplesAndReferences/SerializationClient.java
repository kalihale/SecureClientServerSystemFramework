/*
 * https://www.comrevo.com/2019/07/Sending-objects-over-sockets-Java-example-How-to-send-serialized-object-over-network-in-Java.html
 */

package ExamplesAndReferences;


import Shared.sps;
import org.signal.libsignal.protocol.IdentityKeyPair;
import org.signal.libsignal.protocol.SessionBuilder;
import org.signal.libsignal.protocol.SignalProtocolAddress;
import org.signal.libsignal.protocol.ecc.Curve;
import org.signal.libsignal.protocol.state.SignedPreKeyRecord;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class SerializationClient
{
    public static void main(String[] args) throws Exception
    {

        // ／(^ㅅ^)＼ Client needs to get: Server's identity key, server's signed prekey, server's prekey signature, and
        //           one of server's one-time prekeys

        // ／(•ㅅ•)＼ Generate identity key pair
        //           This has a public and a private key
        IdentityKeyPair clientIPK = IdentityKeyPair.generate();
        // ／(•ㅅ•)＼ Generate signed pre-keys
        //           ECKeyPair is generated with Curve
        SignedPreKeyRecord spkClient = new SignedPreKeyRecord(0, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                Curve.generateKeyPair(), new byte[]{7});
        // ／(•ㅅ•)＼ TODO Create SignalProtocolStore
        sps sps = new sps(clientIPK);
        // ／(•ㅅ•)＼ Build a session using SessionBuilder
        SessionBuilder sb = new SessionBuilder(sps, new SignalProtocolAddress("server", 0));
        SignalProtocolAddress serverSPA = new SignalProtocolAddress("server", 0);

        Sample obj=new Sample();
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

        String o2 = "test";
        oos.writeObject(o2);

        obj.name = "tryagain";

        oos.writeObject(obj);

        System.out.println("Closing socket and terminating program.");
        socket.close();
    }
}
