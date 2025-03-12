package com.github.wubuku.sui.utils;

import com.google.common.primitives.Bytes;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;

import java.util.Base64;

public class TransactionUtils {
    /**
     * From TypeScript definition:
     * <p>
     * <pre>
     * // See: sui/crates/sui-types/src/intent.rs
     * // This is currently hardcoded with [IntentScope::TransactionData = 0, Version::V0 = 0, AppId::Sui = 0]
     * const INTENT_BYTES = [0, 0, 0];
     * </pre>
     */
    public static final byte[] INTENT_BYTES = new byte[]{0, 0, 0};



    private TransactionUtils() {
    }



    /**
     * @param privateKey private key
     * @param txBytes    BCS serialized transaction data bytes without its type tag, as base-64 encoded string.
     */
    public static byte[] ed25519SignTransactionBytes(byte[] privateKey, String txBytes) {
        return ed25519SignTransactionBytes(privateKey, Base64.getDecoder().decode(txBytes));
    }

    /**
     * @param privateKey private key
     * @param txBytes    BCS serialized transaction data bytes without its type tag.
     */
    public static byte[] ed25519SignTransactionBytes(byte[] privateKey, byte[] txBytes) {
        return SignatureUtils.ed25519Sign(privateKey, Bytes.concat(TransactionUtils.INTENT_BYTES, txBytes));
    }

    //
    public static boolean verifySignature(byte[] publicKeyBytes, byte[] signature, byte[] message) {
        try {
            Ed25519PublicKeyParameters publicKey = new Ed25519PublicKeyParameters(publicKeyBytes);
            Ed25519Signer verifier = new Ed25519Signer();
            verifier.init(false, publicKey);
            verifier.update(message, 0, message.length);
            return verifier.verifySignature(signature);
        } catch (Exception e) {
            throw new RuntimeException("Failed to verify signature", e);
        }
    }

}
