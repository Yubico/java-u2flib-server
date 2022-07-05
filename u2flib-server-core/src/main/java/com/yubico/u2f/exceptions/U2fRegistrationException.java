package com.yubico.u2f.exceptions;

/**
 * Base class for exceptions thrown when a U2F registration ceremony fails.
 *
 * @deprecated The java-u2flib-server library is obsolete. Use <a
 * href="https://developers.yubico.com/java-webauthn-server/">java-webauthn-server</a> instead.
 */
@Deprecated
public class U2fRegistrationException extends U2fCeremonyException {
    public U2fRegistrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public U2fRegistrationException(String message) {
        super(message);
    }
}
