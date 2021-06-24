package Security;

import ObjectsToPass.QueryObject;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AES256<S extends QueryObject> implements Encrypt<S>
{
    private static String key;
    private static String salt;
    S message;
    public AES256(String key)
    {
        this.key = key;
    }

    @Override
    public byte[] encrypt(S message)
    {
        try
        {
            byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            KeySpec spec = new PBEKeySpec(key.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder().encode(cipher.doFinal(message.getBytes()));
        }catch(Exception e)
        {
            System.out.println("Error while encrypting : " + e);
        }
        return null;
    }

    @Override
    public S decrypt(byte[] message)
    {
        return null;
    }
}
