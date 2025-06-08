/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import org.eolang.Atom;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.ExFailure;
import org.eolang.PhDefault;
import org.eolang.PhVoid;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Parse source string into DOM document.
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "dom-parser.parse-from-string")
public final class EOdom_parser$EOparse_from_string extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOdom_parser$EOparse_from_string() {
        this.add("data", new PhVoid("data"));
    }

    @Override
    public Phi lambda() {
        final Phi data = this.take("data");
        final Phi xml = Phi.Φ.take("org.eolang.dom.doc").copy().take(Phi.PHI);
        try {
            xml.put(
                "data",
                new Data.ToPhi(
                    new XmlNode.Default(new Dataized(data).asString()).asString().getBytes()
                )
            );
        } catch (final XmlParseException exception) {
            throw new ExFailure(
                String.format(
                    "XML document syntax is invalid: '%s'", new Dataized(data).asString()
                ),
                exception
            );
        }
        return xml;
    }
}
