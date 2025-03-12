package com.github.wubuku.sui.utils;

import org.bouncycastle.crypto.digests.Blake2bDigest;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;

public class SignatureUtils {
    private SignatureUtils() {
    }

    public static byte[] blake2b(byte[] input, int outputLength) {
        Blake2bDigest digest = new Blake2bDigest(outputLength * 8);
        digest.update(input, 0, input.length);
        byte[] hash = new byte[digest.getDigestSize()];
        digest.doFinal(hash, 0);
        return hash;
    }

    public static byte[] ed25519Sign(byte[] privateKey, byte[] data) {

        // 2. cal Blake2b hash
        Blake2bDigest digest = new Blake2bDigest(256); // 32 bytes * 8 = 256 bits
        digest.update(data, 0, data.length);
        byte[] messageHash = new byte[32];
        digest.doFinal(messageHash, 0);

        Ed25519PrivateKeyParameters key = new Ed25519PrivateKeyParameters(privateKey,
                0);
        Ed25519Signer signer = new Ed25519Signer();

        signer.init(true, key);
//        signer.update(data, 0, data.length);
        signer.update(messageHash, 0, messageHash.length);
        byte[] rst = signer.generateSignature();
        return rst;
    }

    public static boolean ed25519Verify(byte[] publicKey, byte[] data, byte[] signature) {
        Ed25519PublicKeyParameters key = new Ed25519PublicKeyParameters(publicKey, 0);
        Ed25519Signer signer = new Ed25519Signer();
        signer.init(false, key);
        signer.update(data, 0, data.length);
        return signer.verifySignature(signature);
    }


}
