/*
 * https://www.comrevo.com/2019/07/Sending-objects-over-sockets-Java-example-How-to-send-serialized-object-over-network-in-Java.html
 */

package ExamplesAndReferences;


import org.signal.libsignal.protocol.IdentityKey;
import org.signal.libsignal.protocol.IdentityKeyPair;
import org.signal.libsignal.protocol.SignalProtocolAddress;
import org.signal.libsignal.protocol.ecc.Curve;
import org.signal.libsignal.protocol.ecc.ECKeyPair;
import org.signal.libsignal.protocol.ecc.ECPublicKey;
import org.signal.libsignal.protocol.state.PreKeyBundle;
import org.signal.libsignal.protocol.state.PreKeyRecord;
import org.signal.libsignal.protocol.state.SignalProtocolStore;
import org.signal.libsignal.protocol.state.SignedPreKeyRecord;
import org.signal.libsignal.protocol.state.impl.InMemorySignalProtocolStore;
import org.signal.libsignal.protocol.util.KeyHelper;

import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SerializationClient
{
    public static void main(String[] args) throws Exception
    {

        // ／(^ㅅ^)＼ Client needs to get: Server's identity key, server's signed prekey, server's prekey signature, and
        //           one of server's one-time prekeys

        // ／(^ㅅ^)＼ Client signal protocol address
        SignalProtocolAddress client = new SignalProtocolAddress("client", 1);
        // ／(•ㅅ•)＼ Generate identity key pair
        //           This has a public and a private key
        IdentityKeyPair clientIKP = IdentityKeyPair.generate();
        // ／(•ㅅ•)＼ Create SignalProtocolStore
        SignalProtocolStore store = new InMemorySignalProtocolStore(clientIKP, KeyHelper.generateRegistrationId(false));
        int registrationID = store.getLocalRegistrationId();

        // ／(•ㅅ•)＼ Generate prekeypair
        ECKeyPair preKeyPair = Curve.generateKeyPair();
        // ／(^ㅅ^)＼ Generate signed pre key pair
        ECKeyPair signedPreKeyPair = Curve.generateKeyPair();
        int deviceID = 1;
        long timestamp = System.currentTimeMillis();

        // ／(^ㅅ^)＼ Create signed pre key signature
        byte[] signedPreKeySignature = Curve.calculateSignature(clientIKP.getPrivateKey(),
                signedPreKeyPair.getPublicKey().serialize());

        // ／(^ㅅ^)＼ Public identity key
        IdentityKey publicIK = clientIKP.getPublicKey();
        // ／(^ㅅ^)＼ Public pre keys
        ECPublicKey preKeyPublic = preKeyPair.getPublicKey();
        ECPublicKey signedPreKeyPublic = signedPreKeyPair.getPublicKey();

        // ／(^ㅅ^)＼ Create preKeyBundle
        PreKeyBundle preKeyBundle = new PreKeyBundle(registrationID, deviceID, 0, preKeyPublic, 0, signedPreKeyPublic
                , signedPreKeySignature, publicIK);
        // ／(^ㅅ^)＼ Create preKeyRecord
        PreKeyRecord preKeyRecord = new PreKeyRecord(preKeyBundle.getPreKeyId(), preKeyPair);
        // ／(^ㅅ^)＼ Create signedPreKeyRecord
        SignedPreKeyRecord signedPreKeyRecord = new SignedPreKeyRecord(0, timestamp, signedPreKeyPair,
                signedPreKeySignature);

        store.storePreKey(0, preKeyRecord);
        store.storeSignedPreKey(0, signedPreKeyRecord);

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
