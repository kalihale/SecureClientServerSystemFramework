package Security;

import javax.crypto.SealedObject;
import java.io.Serializable;
import java.security.Key;

public interface Encrypt<T extends Serializable>
{
    /** ／(•ㅅ•)＼
     * This method will encrypt any object that extends Serializable and return an encrypted SealedObject
     * @param message: The object to encrypt
     * @param key: The encryption key
     * @return SealedObject
     */
    SealedObject encryptObject(T message, Key key);

    /** ／(•ㅅ•)＼
     * decryptObject will decrypt an encrypted SealedObject and return an object that extends Serializable
     * @param message: The encrypted SealedObject to be decrypted
     * @param key: The decryption key
     * @return T
     */
    T decryptObject(SealedObject message, Key key);

    /** ／(•ㅅ•)＼
     * encryptString will encrypt a String and return an encrypted byte array. Some encryption algorithms
     * can decrypt byte arrays which have been cast to a String and back again, but not all algorithms.
     * @param message: The String to be encrypted
     * @param key: The encryption key
     * @return byte[]
     */
    byte[] encryptString(String message, Key key);

    /** ／(•ㅅ•)＼
     * decryptString will decrypt a byte array and return a String.
     * @param message: The byte array to be decrypted
     * @param key: The decryption key
     * @return String
     */
    String decryptString(byte[] message, Key key);
}
