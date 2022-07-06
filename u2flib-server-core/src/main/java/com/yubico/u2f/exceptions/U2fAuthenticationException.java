package com.yubico.u2f.exceptions;

/**
 * Base class for exceptions thrown when a U2F authentication ceremony fails.
 *
 * @deprecated The java-u2flib-server library is obsolete. Use <a
 * href="https://developers.yubico.com/java-webauthn-server/">java-webauthn-server</a> instead.
 */
@Deprecated
public class U2fAuthenticationException extends U2fCeremonyException {
    public U2fAuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public U2fAuthenticationException(String message) {
        super(message);
    }
}
