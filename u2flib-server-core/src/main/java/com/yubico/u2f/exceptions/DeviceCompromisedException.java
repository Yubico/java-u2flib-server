package com.yubico.u2f.exceptions;

import com.yubico.u2f.data.DeviceRegistration;
import lombok.Getter;

/**
 * @deprecated The java-u2flib-server library is obsolete. Use <a
 * href="https://developers.yubico.com/java-webauthn-server/">java-webauthn-server</a> instead.
 */
@Deprecated
@Getter
public class DeviceCompromisedException extends U2fAuthenticationException {
    private final DeviceRegistration deviceRegistration;

    public DeviceCompromisedException(DeviceRegistration deviceRegistration, String message, Throwable cause) {
        super(message, cause);
        this.deviceRegistration = deviceRegistration;
    }

    public DeviceCompromisedException(DeviceRegistration deviceRegistration, String message) {
        super(message);
        this.deviceRegistration = deviceRegistration;
    }

}
