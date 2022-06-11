/*
 * https://www.comrevo.com/2019/07/Sending-objects-over-sockets-Java-example-How-to-send-serialized-object-over-network-in-Java.html
 */

package ExamplesAndReferences;

import org.signal.libsignal.protocol.*;
import org.signal.libsignal.protocol.ecc.Curve;
import org.signal.libsignal.protocol.ecc.ECKeyPair;
import org.signal.libsignal.protocol.ecc.ECPublicKey;
import org.signal.libsignal.protocol.message.CiphertextMessage;
import org.signal.libsignal.protocol.message.PreKeySignalMessage;
import org.signal.libsignal.protocol.message.SignalMessage;
import org.signal.libsignal.protocol.state.PreKeyBundle;
import org.signal.libsignal.protocol.state.PreKeyRecord;
import org.signal.libsignal.protocol.state.SignalProtocolStore;
import org.signal.libsignal.protocol.state.SignedPreKeyRecord;
import org.signal.libsignal.protocol.state.impl.InMemorySignalProtocolStore;
import org.signal.libsignal.protocol.util.KeyHelper;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SerializationServer
{
    public static void main(String[] args) throws Exception
    {
        // ／(^ㅅ^)＼ Create server address
        SignalProtocolAddress server = new SignalProtocolAddress("server", 0);
        // ／(^ㅅ^)＼ Generate identity key pair
        IdentityKeyPair identityKeyPair = IdentityKeyPair.generate();
        // ／(^ㅅ^)＼ Generate SignalProtocolStore for the server
        SignalProtocolStore serverStore = new InMemorySignalProtocolStore(identityKeyPair, KeyHelper.generateRegistrationId(false));
        // ／(^ㅅ^)＼ RegistrationID
        int registrationID = serverStore.getLocalRegistrationId();

        // ／(^ㅅ^)＼ Generate preKeyPairs (signed and unsigned) using Curve
        ECKeyPair preKeyPair = Curve.generateKeyPair();
        ECKeyPair signedPreKeyPair = Curve.generateKeyPair();
        int deviceID = server.getDeviceId();
        long timestamp = System.currentTimeMillis();

        // ／(^ㅅ^)＼ Generate signed prekey signature
        byte[] signedPreKeySignature = Curve.calculateSignature(identityKeyPair.getPrivateKey(), signedPreKeyPair.getPublicKey().serialize());

        // ／(^ㅅ^)＼ Public keys
        IdentityKey identityKey = identityKeyPair.getPublicKey();
        ECPublicKey preKeyPublic = preKeyPair.getPublicKey();
        ECPublicKey signedPreKeyPublic = signedPreKeyPair.getPublicKey();

        // ／(^ㅅ^)＼ Create preKeyBundle
        PreKeyBundle preKeyBundle = new PreKeyBundle(registrationID, deviceID, 0, preKeyPublic, 0, signedPreKeyPublic
                , signedPreKeySignature, identityKey);

        // ／(^ㅅ^)＼ Create preKeyRecord
        PreKeyRecord preKeyRecord = new PreKeyRecord(preKeyBundle.getPreKeyId(), preKeyPair);
        SignedPreKeyRecord signedPreKeyRecord = new SignedPreKeyRecord(0, timestamp, signedPreKeyPair,
                signedPreKeySignature);

        ServerSocket ss = new ServerSocket(7000);
        System.out.println("ServerSocket awaiting connections...");
        Socket socket = ss.accept();
        System.out.println("Connection from " + socket);

        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        System.out.println("Sending values to the ServerSocket");
        oos.writeObject(preKeyBundle);

        //Deserialization
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);
        PreKeyBundle clientBundle = (PreKeyBundle) ois.readObject();

        is = socket.getInputStream();
        ois = new ObjectInputStream(is);
        PreKeySignalMessage obj1 = (PreKeySignalMessage) ois.readObject();

        SignalProtocolAddress client = new SignalProtocolAddress("client", 1);

        SessionBuilder builder = new SessionBuilder(serverStore, client);

        builder.process(clientBundle);

        SessionCipher cipher = new SessionCipher(serverStore, client);

        byte[] decrypted = cipher.decrypt(obj1);

        System.out.println("Values received from Client are:-");

        System.out.println(new String(decrypted, StandardCharsets.UTF_8));

        CiphertextMessage message = cipher.encrypt("Server to client".getBytes(StandardCharsets.UTF_8));
        byte[] rawText = message.serialize();

        SignalMessage signalMessage = new SignalMessage(rawText);


        System.out.println("Closing sockets.");
        ss.close();
        socket.close();
    }
}