package Security;

import javax.crypto.SealedObject;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

public class NoEncrypt<S extends String> implements Encrypt<S>
{
    @Override
    public SealedObject encryptObject(S message, Key key)
    {
        return null;
    }

    @Override
    public S decryptObject(SealedObject message, Key key)
    {
        return null;
    }

    @Override
    public byte[] encryptString(String message, Key key)
    {
        return message.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String decryptString(byte[] message, Key key)
    {
        return new String(Base64.getDecoder().decode(message));
    }

    @Override
    public String bytesToString(byte[] message)
    {
        return null;
    }

    @Override
    public byte[] stringToBytes(String message)
    {
        return new byte[0];
    }
}
