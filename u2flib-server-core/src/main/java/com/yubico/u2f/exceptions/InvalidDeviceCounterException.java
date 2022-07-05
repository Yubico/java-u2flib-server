package com.yubico.u2f.exceptions;

import com.yubico.u2f.data.DeviceRegistration;

/**
 * @deprecated The java-u2flib-server library is obsolete. Use <a
 * href="https://developers.yubico.com/java-webauthn-server/">java-webauthn-server</a> instead.
 */
@Deprecated
public class InvalidDeviceCounterException extends DeviceCompromisedException {
    public InvalidDeviceCounterException(DeviceRegistration registration) {
        super(registration, "The device's internal counter was was smaller than expected." +
                "It's possible that the device has been cloned!");
    }
}
