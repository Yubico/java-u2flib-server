/* Copyright 2015 Yubico */

package com.yubico.u2f.attestation;

import com.fasterxml.jackson.databind.JsonNode;

import java.security.cert.X509Certificate;

/**
 * @deprecated The java-u2flib-server library is obsolete. Use <a
 * href="https://developers.yubico.com/java-webauthn-server/">java-webauthn-server</a> instead.
 */
@Deprecated
public interface DeviceMatcher {
    public boolean matches(X509Certificate attestationCertificate, JsonNode parameters);
}
