package com.yubico.u2f.data.messages.json;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * @deprecated The java-u2flib-server library is obsolete. Use <a
 * href="https://developers.yubico.com/java-webauthn-server/">java-webauthn-server</a> instead.
 */
@Deprecated
public interface Persistable extends Serializable {
    @JsonIgnore
    public String getRequestId();

    public String toJson();
}
