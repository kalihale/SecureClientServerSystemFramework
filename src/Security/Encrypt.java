package Security;

public interface Encrypt<T>
{
    byte[] encrypt(T message);

    T decrypt(byte[] message);
}
