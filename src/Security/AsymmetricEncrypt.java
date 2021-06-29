package Security;

import java.io.Serializable;
import java.security.KeyPair;

public interface AsymmetricEncrypt<S extends Serializable> extends Encrypt<S>
{
    KeyPair getKeyPair();

}
