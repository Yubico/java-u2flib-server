/*
 * Copyright 2014 Yubico.
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file or at
 * https://developers.google.com/open-source/licenses/bsd
 */

package com.yubico.u2f.data.messages;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yubico.u2f.data.messages.json.JsonSerializable;
import com.yubico.u2f.data.messages.json.Persistable;
import com.yubico.u2f.exceptions.U2fBadInputException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import lombok.EqualsAndHashCode;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @deprecated The java-u2flib-server library is obsolete. Use <a
 * href="https://developers.yubico.com/java-webauthn-server/">java-webauthn-server</a> instead.
 */
@Deprecated
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode
public class SignResponse extends JsonSerializable implements Persistable {
    private static final int MAX_SIZE = 20000;

    /* base64(client data) */
    @JsonProperty("clientData")
    private final String clientDataRaw;

    @JsonIgnore
    private transient ClientData clientDataRef;

    /* base64(raw response from U2F device) */
    @JsonProperty
    private final String signatureData;

    /* keyHandle originally passed */
    @JsonProperty
    private final String keyHandle;

    @JsonCreator
    public SignResponse(@JsonProperty("clientData") String clientData, @JsonProperty("signatureData") String signatureData, @JsonProperty("keyHandle") String keyHandle) throws U2fBadInputException {
        this.clientDataRaw = checkNotNull(clientData);
        this.signatureData = checkNotNull(signatureData);
        this.keyHandle = checkNotNull(keyHandle);
        clientDataRef = new ClientData(clientData);
    }

    @JsonIgnore
    public ClientData getClientData() {
        return clientDataRef;
    }

    public String getSignatureData() {
        return signatureData;
    }

    public String getKeyHandle() {
        return keyHandle;
    }

    public String getRequestId() {
        return getClientData().getChallenge();
    }

    public static SignResponse fromJson(String json) throws U2fBadInputException {
        checkArgument(json.length() < MAX_SIZE, "Client response bigger than allowed");
        return fromJson(json, SignResponse.class);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        try {
            clientDataRef = new ClientData(clientDataRaw);
        } catch (U2fBadInputException e) {
            throw new IOException(e);
        }
    }
}
