/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

/**
 * XML parse exception.
 * @since 0.0.0
 */
final class XmlParseException extends Exception {

    /**
     * Ctor.
     * @param message Message
     * @param cause Context
     */
    XmlParseException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
