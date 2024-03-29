/*
 * Copyright 2014 Yubico.
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file or at
 * https://developers.google.com/open-source/licenses/bsd
 */

package com.yubico.u2f.data.messages.key;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.yubico.u2f.crypto.BouncyCastleCrypto;
import com.yubico.u2f.crypto.Crypto;
import com.yubico.u2f.data.messages.SignResponse;
import com.yubico.u2f.data.messages.key.util.ByteInputStream;
import com.yubico.u2f.data.messages.key.util.U2fB64Encoding;
import com.yubico.u2f.exceptions.U2fBadInputException;
import java.io.IOException;
import lombok.EqualsAndHashCode;

/**
 * The sign response produced by the token/key, which is transformed by the client into an
 * {@link SignResponse} and sent to the server.
 *
 * @deprecated The java-u2flib-server library is obsolete. Use <a
 * href="https://developers.yubico.com/java-webauthn-server/">java-webauthn-server</a> instead.
 */
@Deprecated
@EqualsAndHashCode(of = { "userPresence", "counter", "signature" })
public class RawSignResponse {
    public static final byte USER_PRESENT_FLAG = 0x01;

    private final byte userPresence;
    private final long counter;
    private final byte[] signature;
    private final Crypto crypto;

    public RawSignResponse(byte userPresence, long counter, byte[] signature) {
        this(userPresence, counter, signature, new BouncyCastleCrypto());
    }

    public RawSignResponse(byte userPresence, long counter, byte[] signature, Crypto crypto) {
        this.userPresence = userPresence;
        this.counter = counter;
        this.signature = signature;
        this.crypto = crypto;
    }

    public static RawSignResponse fromBase64(String rawDataBase64, Crypto crypto) throws U2fBadInputException {
        ByteInputStream bytes = new ByteInputStream(U2fB64Encoding.decode(rawDataBase64));
        try {
            return new RawSignResponse(
                    bytes.readSigned(),
                    bytes.readInteger(),
                    bytes.readAll(),
                    crypto
            );
        } catch (IOException e) {
            throw new U2fBadInputException("Truncated authentication data", e);
        }
    }

    public void checkSignature(String appId, String clientData, byte[] publicKey) throws U2fBadInputException {
        byte[] signedBytes = packBytesToSign(
                crypto.hash(appId),
                userPresence,
                counter,
                crypto.hash(clientData)
        );
        crypto.checkSignature(
                crypto.decodePublicKey(publicKey),
                signedBytes,
                signature
        );
    }

    public static byte[] packBytesToSign(byte[] appIdHash, byte userPresence, long counter, byte[] challengeHash) {
        ByteArrayDataOutput encoded = ByteStreams.newDataOutput();
        encoded.write(appIdHash);
        encoded.write(userPresence);
        encoded.writeInt((int) counter);
        encoded.write(challengeHash);
        return encoded.toByteArray();
    }

    /**
     * Bit 0 is set to 1, which means that user presence was verified. (This version of the protocol doesn't specify a
     * way to request sign responses without requiring user presence.) A different value of bit 0, as well as bits 1
     * through 7, are reserved for future use. The values of bit 1 through 7 SHOULD be 0
     */
    public byte getUserPresence() {
        return userPresence;
    }

    /**
     * This is the big-endian representation of a counter value that the U2F device
     * increments every time it performs a sign operation.
     */
    public long getCounter() {
        return counter;
    }

    /**
     * This is a ECDSA signature (on P-256)
     */
    public byte[] getSignature() {
        return signature;
    }

    public void checkUserPresence() throws U2fBadInputException {
        if (userPresence != USER_PRESENT_FLAG) {
            throw new U2fBadInputException("User presence invalid during signing");
        }
    }
}
