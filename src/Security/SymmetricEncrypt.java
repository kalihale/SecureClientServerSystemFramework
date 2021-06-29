package Security;

import java.io.Serializable;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface SymmetricEncrypt<T extends Serializable> extends Encrypt<T>
{
    Key getKeyFromPassword(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException;

    String generateSalt();
}
