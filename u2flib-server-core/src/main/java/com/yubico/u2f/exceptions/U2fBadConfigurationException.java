package com.yubico.u2f.exceptions;

/**
 * @deprecated The java-u2flib-server library is obsolete. Use <a
 * href="https://developers.yubico.com/java-webauthn-server/">java-webauthn-server</a> instead.
 */
@Deprecated
public class U2fBadConfigurationException extends Exception {
    public U2fBadConfigurationException(String message) {
        super(message);
    }

    public U2fBadConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
