package Security;

public class NoEncrypt<S extends String> implements Encrypt<S>
{
    S message;

    public NoEncrypt(S message)
    {
        this.message = message;
    }

    @Override
    public byte[] encrypt(S message)
    {
        return null;
    }

    @Override
    public S decrypt(byte[] message)
    {
        return null;
    }
}
