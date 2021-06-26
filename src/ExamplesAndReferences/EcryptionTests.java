package ExamplesAndReferences;

import Security.AES256;
import Security.RSA;

import javax.crypto.SecretKey;
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
        AES256<String> aes = new AES256<>();
        SecretKey sc = AES256.getKeyFromPassword("password", "random");
        System.out.println(message);

        byte[] encrypted = aes.encryptString(message, sc);
        String encryptedString = aes.bytesToString(encrypted);
        System.out.println(encryptedString);

        byte[] encrypted2 = aes.stringToBytes(encryptedString);
        String decryptedString = aes.decryptString(encrypted2, sc);
        System.out.println(decryptedString);

        RSA<String> rsa = new RSA<>();
        KeyPair keys = RSA.generateRSAKeys();
        byte[] rsae = rsa.encryptString(message, keys.getPublic());
        String rsas = rsa.decryptString(rsae, keys.getPrivate());
        System.out.println(rsas);
    }
}
