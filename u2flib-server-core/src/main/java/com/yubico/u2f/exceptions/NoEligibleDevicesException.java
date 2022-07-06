package com.yubico.u2f.exceptions;

import com.yubico.u2f.data.DeviceRegistration;

/**
 * @deprecated The java-u2flib-server library is obsolete. Use <a
 * href="https://developers.yubico.com/java-webauthn-server/">java-webauthn-server</a> instead.
 */
@Deprecated
@SuppressWarnings("deprecation")
public class NoEligibleDevicesException extends NoEligableDevicesException {

    public NoEligibleDevicesException(Iterable<? extends DeviceRegistration> devices, String message, Throwable cause) {
        super(devices, message, cause);
    }

    public NoEligibleDevicesException(Iterable<? extends DeviceRegistration> devices, String message) {
        super(devices, message);
    }

}
