/*
 * Copyright 2014 Yubico.
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Use of this source code is governed by a BSD-style
 * license that can be found in the LICENSE file or at
 * https://developers.google.com/open-source/licenses/bsd
 */

package com.yubico.u2f.exceptions;

/**
 * @deprecated The java-u2flib-server library is obsolete. Use <a
 * href="https://developers.yubico.com/java-webauthn-server/">java-webauthn-server</a> instead.
 */
@Deprecated
@SuppressWarnings("serial")
public class U2fBadInputException extends Exception {

    public U2fBadInputException(String message) {
        super(message);
    }

    public U2fBadInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
