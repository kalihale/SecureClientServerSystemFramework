package ExamplesAndReferences;

import ObjectsToPass.User;
import Security.AES256;
import Security.AsymmetricEncrypt;
import Security.RSA;
import Security.SymmetricEncrypt;

import javax.crypto.SealedObject;
import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class EcryptionTests
{
    public static void main(String[] args)
    {
        try
        {
            testEncryption();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        } catch (InvalidKeySpecException e)
        {
            e.printStackTrace();
        }
    }
    static void testEncryption() throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        String message = "This is a message";
        User user = new User(1, "f401", "username", "password", "thing",
                "blah", "whatever");
        System.out.println("Original message: " + message);

        SymmetricEncrypt<Serializable> aes = new AES256<>();
        Key key = aes.getKeyFromPassword("password", "random");

        String encryptedAES = aes.bytesToString(aes.encryptString(message, key));
        SealedObject encryptedObjAES = aes.encryptObject(user, key);
        System.out.println("Encrypted with AES: " + encryptedAES);

        String decrypted = aes.decryptString(aes.stringToBytes(encryptedAES), key);
        User decryptedObjAES = (User) aes.decryptObject(encryptedObjAES, key);
        System.out.println("Decrypted with AES: " + decrypted);
        decryptedObjAES.printEverything();

        AsymmetricEncrypt<Serializable> rsa = new RSA<>();
        KeyPair keys = rsa.getKeyPair();

        String encryptedRSA = rsa.bytesToString(rsa.encryptString(message, keys.getPublic()));
        SealedObject encryptedObjRSA = rsa.encryptObject(user, keys.getPublic());
        System.out.println("Encrypted with RSA: " + encryptedRSA);

        String decryptedRSA = rsa.decryptString(rsa.stringToBytes(encryptedRSA), keys.getPrivate());
        User decryptedObjRSA = (User) rsa.decryptObject(encryptedObjRSA, keys.getPrivate());
        System.out.println("Decrypted with RSA: " + decryptedRSA);
        decryptedObjRSA.printEverything();

    }
}
