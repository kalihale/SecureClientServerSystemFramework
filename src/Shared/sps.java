package Shared;

import org.signal.libsignal.protocol.*;
import org.signal.libsignal.protocol.groups.state.SenderKeyRecord;
import org.signal.libsignal.protocol.state.PreKeyRecord;
import org.signal.libsignal.protocol.state.SessionRecord;
import org.signal.libsignal.protocol.state.SignalProtocolStore;
import org.signal.libsignal.protocol.state.SignedPreKeyRecord;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * ／(•ㅅ•)＼
 *
 * @author Kali Hale
 * @since 2022-03-28
 */
public class sps implements SignalProtocolStore
{
    class Sender{
        SignalProtocolAddress sender;
        UUID distributionId;
        Sender(SignalProtocolAddress sender, UUID distributionId)
        {
            this.sender = sender;
            this.distributionId = distributionId;
        }

        public boolean equals(Sender o)
        {
            if(this == o) return true;
            if(o == null) return false;
            return this.sender.equals(o.sender) && this.distributionId.equals(o.distributionId);
        }
    }

    HashMap<Sender, SenderKeyRecord> senderKeys = new HashMap<>();

    SignalProtocolAddress sender;
    UUID uuid;
    SenderKeyRecord senderKeyRecord;
    IdentityKeyPair ipk;

    public sps(IdentityKeyPair ipk)
    {
        this.ipk = ipk;
    }

    /**
     * Commit to storage the {@link org.signal.libsignal.protocol.groups.state.SenderKeyRecord} for a
     * given (distributionId + senderName + deviceId) tuple.
     *
     * @param sender The address of the current client.
     * @param distributionId An opaque identifier that uniquely identifies the group (but isn't the group ID).
     * @param record the current SenderKeyRecord for the specified (distributionId + senderName + deviceId) tuple.
     */
    @Override
    public void storeSenderKey(SignalProtocolAddress sender, UUID distributionId, SenderKeyRecord record)
    {
        this.sender = sender;
        this.uuid = distributionId;
        this.senderKeyRecord = record;
//        senderKeys.put(new Sender(sender, distributionId), record);
    }

    /**
     * Returns a copy of the {@link org.signal.libsignal.protocol.groups.state.SenderKeyRecord}
     * corresponding to the (distributionId + senderName + deviceId) tuple, or `null` if one does not
     * exist.
     *
     * It is important that implementations return a copy of the current durable information.  The
     * returned SenderKeyRecord may be modified, but those changes should not have an effect on the
     * durable session state (what is returned by subsequent calls to this method) without the
     * store method being called here first.
     *
     * @param sender The address of the current client.
     * @param distributionId An opaque identifier that uniquely identifies the group (but isn't the group ID).
     * @return a copy of the SenderKeyRecord corresponding to the (id + senderName + deviceId tuple, or
     *         `null` if one does not currently exist.
     */
    @Override
    public SenderKeyRecord loadSenderKey(SignalProtocolAddress sender, UUID distributionId)
    {
//        return senderKeys.get(new Sender(sender, distributionId));
        if(this.sender == sender && this.uuid == distributionId)
        {
            return this.senderKeyRecord;
        }
        return null;
    }

    /**
     * Get the local client's identity key pair.
     *
     * @return The local client's persistent identity key pair.
     */
    @Override
    public IdentityKeyPair getIdentityKeyPair()
    {
        return null;
    }

    /**
     * Return the local client's registration ID.
     * <p>
     * Clients should maintain a registration ID, a random number
     * between 1 and 16380 that's generated once at install time.
     *
     * @return the local client's registration ID.
     */
    @Override
    public int getLocalRegistrationId()
    {
        return 0;
    }

    /**
     * Save a remote client's identity key
     * <p>
     * Store a remote client's identity key as trusted.
     *
     * @param address     The address of the remote client.
     * @param identityKey The remote client's identity key.
     * @return True if the identity key replaces a previous identity, false if not
     */
    @Override
    public boolean saveIdentity(SignalProtocolAddress address, IdentityKey identityKey)
    {
        return false;
    }

    /**
     * Verify a remote client's identity key.
     * <p>
     * Determine whether a remote client's identity is trusted.  Convention is
     * that the Signal Protocol is 'trust on first use.'  This means that
     * an identity key is considered 'trusted' if there is no entry for the recipient
     * in the local store, or if it matches the saved key for a recipient in the local
     * store.  Only if it mismatches an entry in the local store is it considered
     * 'untrusted.'
     *
     * Clients may wish to make a distinction as to how keys are trusted based on the
     * direction of travel. For instance, clients may wish to accept all 'incoming' identity
     * key changes, while only blocking identity key changes when sending a message.
     *
     * @param address     The address of the remote client.
     * @param identityKey The identity key to verify.
     * @param direction   The direction (sending or receiving) this identity is being used for.
     * @return true if trusted, false if untrusted.
     */
    @Override
    public boolean isTrustedIdentity(SignalProtocolAddress address, IdentityKey identityKey, Direction direction)
    {
        return false;
    }

    /**
     * Return the saved public identity key for a remote client
     *
     * @param address The address of the remote client
     * @return The public identity key, or null if absent
     */
    @Override
    public IdentityKey getIdentity(SignalProtocolAddress address)
    {
        return null;
    }

    /**
     * Load a local PreKeyRecord.
     *
     * @param preKeyId the ID of the local PreKeyRecord.
     * @return the corresponding PreKeyRecord.
     * @throws InvalidKeyIdException when there is no corresponding PreKeyRecord.
     */
    @Override
    public PreKeyRecord loadPreKey(int preKeyId) throws InvalidKeyIdException
    {
        return null;
    }

    /**
     * Store a local PreKeyRecord.
     *
     * @param preKeyId the ID of the PreKeyRecord to store.
     * @param record the PreKeyRecord.
     */
    @Override
    public void storePreKey(int preKeyId, PreKeyRecord record)
    {

    }

    /**
     * @param preKeyId A PreKeyRecord ID.
     * @return true if the store has a record for the preKeyId, otherwise false.
     */
    @Override
    public boolean containsPreKey(int preKeyId)
    {
        return false;
    }

    /**
     * Delete a PreKeyRecord from local storage.
     *
     * @param preKeyId The ID of the PreKeyRecord to remove.
     */
    @Override
    public void removePreKey(int preKeyId)
    {

    }

    /**
     * Returns a copy of the {@link SessionRecord} corresponding to the recipientId + deviceId tuple,
     * or a new SessionRecord if one does not currently exist.
     * <p>
     * It is important that implementations return a copy of the current durable information.  The
     * returned SessionRecord may be modified, but those changes should not have an effect on the
     * durable session state (what is returned by subsequent calls to this method) without the
     * store method being called here first.
     *
     * @param address The name and device ID of the remote client.
     * @return a copy of the SessionRecord corresponding to the recipientId + deviceId tuple, or
     *         a new SessionRecord if one does not currently exist.
     */
    @Override
    public SessionRecord loadSession(SignalProtocolAddress address)
    {
        return null;
    }

    /**
     * Returns the {@link SessionRecord}s corresponding to the given addresses.
     *
     * @param addresses The name and device ID of each remote client.
     * @return the SessionRecords corresponding to each recipientId + deviceId tuple.
     * @throws NoSessionException if any address does not have an active session.
     */
    @Override
    public List<SessionRecord> loadExistingSessions(List<SignalProtocolAddress> addresses) throws NoSessionException
    {
        return null;
    }

    /**
     * Returns all known devices with active sessions for a recipient
     *
     * @param name the name of the client.
     * @return all known sub-devices with active sessions.
     */
    @Override
    public List<Integer> getSubDeviceSessions(String name)
    {
        return null;
    }

    /**
     * Commit to storage the {@link SessionRecord} for a given recipientId + deviceId tuple.
     * @param address the address of the remote client.
     * @param record the current SessionRecord for the remote client.
     */
    @Override
    public void storeSession(SignalProtocolAddress address, SessionRecord record)
    {

    }

    /**
     * Determine whether there is a committed {@link SessionRecord} for a recipientId + deviceId tuple.
     * @param address the address of the remote client.
     * @return true if a {@link SessionRecord} exists, false otherwise.
     */
    @Override
    public boolean containsSession(SignalProtocolAddress address)
    {
        return false;
    }

    /**
     * Remove a {@link SessionRecord} for a recipientId + deviceId tuple.
     *
     * @param address the address of the remote client.
     */
    @Override
    public void deleteSession(SignalProtocolAddress address)
    {

    }

    /**
     * Remove the {@link SessionRecord}s corresponding to all devices of a recipientId.
     *
     * @param name the name of the remote client.
     */
    @Override
    public void deleteAllSessions(String name)
    {

    }

    /**
     * Load a local SignedPreKeyRecord.
     *
     * @param signedPreKeyId the ID of the local SignedPreKeyRecord.
     * @return the corresponding SignedPreKeyRecord.
     * @throws InvalidKeyIdException when there is no corresponding SignedPreKeyRecord.
     */
    @Override
    public SignedPreKeyRecord loadSignedPreKey(int signedPreKeyId) throws InvalidKeyIdException
    {
        return null;
    }

    /**
     * Load all local SignedPreKeyRecords.
     *
     * @return All stored SignedPreKeyRecords.
     */
    @Override
    public List<SignedPreKeyRecord> loadSignedPreKeys()
    {
        return null;
    }

    /**
     * Store a local SignedPreKeyRecord.
     *
     * @param signedPreKeyId the ID of the SignedPreKeyRecord to store.
     * @param record the SignedPreKeyRecord.
     */
    @Override
    public void storeSignedPreKey(int signedPreKeyId, SignedPreKeyRecord record)
    {

    }

    /**
     * @param signedPreKeyId A SignedPreKeyRecord ID.
     * @return true if the store has a record for the signedPreKeyId, otherwise false.
     */
    @Override
    public boolean containsSignedPreKey(int signedPreKeyId)
    {
        return false;
    }

    /**
     * Delete a SignedPreKeyRecord from local storage.
     *
     * @param signedPreKeyId The ID of the SignedPreKeyRecord to remove.
     */
    @Override
    public void removeSignedPreKey(int signedPreKeyId)
    {

    }
}
