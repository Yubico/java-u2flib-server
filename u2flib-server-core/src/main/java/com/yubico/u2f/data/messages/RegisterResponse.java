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
import com.google.common.base.Objects;
import com.yubico.u2f.data.messages.json.JsonSerializable;
import com.yubico.u2f.data.messages.json.Persistable;
import com.yubico.u2f.exceptions.U2fBadInputException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RegisterResponse extends JsonSerializable implements Persistable {
    private static final int MAX_SIZE = 20000;

    /**
     * base64(raw registration response message)
     */
    @JsonProperty
    private final String registrationData;

    /**
     * base64(UTF8(client data))
     */
    @JsonProperty
    private final String clientData;

    private transient ClientData clientDataRef;

    @JsonCreator
    public RegisterResponse(@JsonProperty("registrationData") String registrationData, @JsonProperty("clientData") String clientData) throws U2fBadInputException {
        this.registrationData = checkNotNull(registrationData);
        this.clientData = checkNotNull(clientData);
        this.clientDataRef = new ClientData(clientData);
    }

    public String getRegistrationData() {
        return registrationData;
    }

    @JsonIgnore
    public ClientData getClientData() {
        return clientDataRef;
    }

    public String getRequestId() {
        return getClientData().getChallenge();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(registrationData, clientData);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RegisterResponse))
            return false;
        RegisterResponse other = (RegisterResponse) obj;
        return Objects.equal(clientData, other.clientData)
                && Objects.equal(registrationData, other.registrationData);
    }

    public static RegisterResponse fromJson(String json) throws U2fBadInputException {
        checkArgument(json.length() < MAX_SIZE, "Client response bigger than allowed");
        return JsonSerializable.fromJson(json, RegisterResponse.class);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        try {
            clientDataRef = new ClientData(clientData);
        } catch (U2fBadInputException e) {
            throw new IOException(e);
        }
    }
}
