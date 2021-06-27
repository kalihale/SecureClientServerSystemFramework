package Security;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import java.io.Serializable;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.util.Base64;

/** ／(•ㅅ•)＼
 * RSA is an asymmetric algorithm with utilizes a public and a private key. The public key is given to the
 * other actor in the pair while the private key stays on the server.
 * NOTE: If a byte array A is sent to a string B, which is then cast back to a byte array C,
 * the RSA algorithm cannot decrypt byte array C. It can only decrypt byte array A.
 * @param <S extends Serializable>
 */

public class RSA<S extends Serializable> implements AsymmetricEncrypt<S>
{
    private static final String RSA = "RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING";

    /** ／(•ㅅ•)＼
     * generateRSAKeys creates a pair of keys for the RSA asymmetric algorithm
     * the public key should be sent to the client(s), while the private key stays on the server
     * @return KeyPair
     */
    @Override
    public KeyPair getKeyPair()
    {
        try
        {
            SecureRandom sr = new SecureRandom();
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(4096, sr);
            return kpg.generateKeyPair();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /** ／(•ㅅ•)＼
     * This will take in the object to be encrypted and the public key and return an encrypted SealedObject
     * @param message: The object to encrypt
     * @param key: The encryption key (public key only)
     * @return SealedObject
     */
    @Override
    public SealedObject encryptObject(Serializable message, Key key)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return new SealedObject(message, cipher);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /** ／(•ㅅ•)＼
     * decryptObject takes a SealedObject and a private key and returns an object S that extends Serializable
     * @param message: The encrypted SealedObject to be decrypted
     * @param key: The decryption key (private key only)
     * @return S extends Serializable
     */
    @Override
    public S decryptObject(SealedObject message, Key key)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, key);
            Serializable unsealed = (Serializable) message.getObject(cipher);
            return(S)unsealed;
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /** ／(•ㅅ•)＼
     * encryptString takes in a String and returns a byte array
     * @param message: The String to be encrypted
     * @param key: The encryption key (public key only)
     * @return byte[]
     */
    @Override
    public byte[] encryptString(String message, Key key)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(message.getBytes());
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /** ／(•ㅅ•)＼
     * decryptString will take in a byte array and return a String. However, if the encrypted byte array was cast
     * to a String, that String cannot be retrieved, cast to a byte array, and decrypted, as this method
     * will then throw a BadPaddingException.
     * @param message: The byte array to be decrypted
     * @param key: The decryption key (private key only)
     * @return String
     */
    @Override
    public String decryptString(byte[] message, Key key)
    {
        try
        {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(message);
            return new String(result);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /** ／(•ㅅ•)＼
     * bytesToString will take a byte array and return a String.
     * @param message: The byte array to cast to String
     * @return String
     */
    @Override
    public String bytesToString(byte[] message)
    {
        return Base64.getEncoder().encodeToString(message);
    }

    /** ／(•ㅅ•)＼
     * stringToBytes will take a String and return a byte array.
     * @param message: The String to cast to a byte array
     * @return byte[]
     */
    @Override
    public byte[] stringToBytes(String message)
    {
        return Base64.getDecoder().decode(message);
    }
}
