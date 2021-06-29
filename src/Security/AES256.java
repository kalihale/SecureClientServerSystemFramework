/**
 * Modified from https://www.baeldung.com/java-aes-encryption-decryption on June 23, 2021
 */
package Security;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Random;

/** ／(•ㅅ•)＼
 * AES256 is a symmetric encryption algorithm. A symmetric algorithm only uses one key to both encrypt and decrypt
 * messages.
 * @param <S extends Serializable>
 */
public class AES256<S extends Serializable> implements SymmetricEncrypt<S>
{

    /** ／(•ㅅ•)＼
     * getKeyFromPassword generates a SecretKey from two strings. Here, they are the admin password and the salt.
     * @param password: This would be the admin password, but it can be any String.
     * @param salt: This is also just a String. I don't know what it will be yet. (Maybe a security question answer?)
     * @return SecretKey
     * @throws NoSuchAlgorithmException: If SecretKeyFactory.getInstance is given an invalid algorithm to create the key.
     * @throws InvalidKeySpecException: If factory.generateSecret is given invalid KeySpec.
     */
    @Override
    public Key getKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), "AES");
    }

    @Override
    public String generateSalt()
    {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 20;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    /** ／(•ㅅ•)＼
     * I don't actually know what an IVParameterSpec is, but this generates it. TODO look this up
     * @return IVParameterSpec
     */
    private static IvParameterSpec generateIV()
    {
        byte[] iv = new byte[16];
        return new IvParameterSpec(iv);
    }

    /** ／(•ㅅ•)＼
     * encryptObject will take any object that extends Serializable and return an encrypted SealedObject
     * @param message: The object to encrypt
     * @param key: The encryption key
     * @return SealedObject
     */
    @Override
    public SealedObject encryptObject(S message, Key key)
    {
        try
        {
            IvParameterSpec ivspec = generateIV();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
            return new SealedObject(message, cipher);
        }catch(Exception e)
        {
            System.out.println("Error while encrypting : " + e);
        }
        return null;
    }

    /** ／(•ㅅ•)＼
     * decryptObject will take in a SealedObject and return a decrypted object that extends Serializable
     * @param message: The encrypted SealedObject to be decrypted
     * @param key: The decryption key
     * @return S
     */
    @Override
    public S decryptObject(SealedObject message, Key key)
    {
        try
        {
            IvParameterSpec ivspec = generateIV();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
            Serializable unsealed = (Serializable) message.getObject(cipher);
            return (S) unsealed;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    /** ／(•ㅅ•)＼
     * encryptString takes in a String message and returns an encrypted byte array.
     * @param message: The String to be encrypted
     * @param key: The encryption key
     * @return byte[]
     */
    @Override
    public byte[] encryptString(String message, Key key)
    {
        try
        {
            IvParameterSpec ivspec = generateIV();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivspec);
            return cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /** ／(•ㅅ•)＼
     * decryptString will take in a byte array and return a String. Unlike RSA, this method will successfully decrypt
     * any byte array that has been cast to a String, then cast back to a byte array, making this perfect for storing
     * information in MySQL as strings and still enabling decryption after it is retrieved.
     * @param message: The byte array to be decrypted
     * @param key: The decryption key
     * @return String
     */
    @Override
    public String decryptString(byte[] message, Key key)
    {
        try
        {
            IvParameterSpec ivspec = generateIV();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(message)));
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /** ／(•ㅅ•)＼
     * Takes a byte array and casts it to a String.
     * @param message: The byte array to be sent to a String
     * @return String
     */
    @Override
    public String bytesToString(byte[] message)
    {
        return Base64.getEncoder().encodeToString(message);
    }

    /** ／(•ㅅ•)＼
     * Takes a String and returns its corresponding byte array.
     * @param message: The String to be turned into a byte array
     * @return byte[]
     */
    @Override
    public byte[] stringToBytes(String message)
    {
        return message.getBytes(StandardCharsets.UTF_8);
    }
}
