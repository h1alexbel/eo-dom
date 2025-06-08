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
 * Document atom.
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "doc.xml")
public final class EOdoc$EOφ extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOdoc$EOφ() {
        this.add("data", new PhVoid("data"));
    }

    @Override
    public Phi lambda() {
        final Phi xml = this.take(Phi.RHO).take("xml");
        try {
            xml.put(
                "serialized",
                new Data.ToPhi(
                    new XmlNode.Default(
                        new Dataized(this.take("data")).asString()
                    ).asString().getBytes()
                )
            );
        } catch (final XmlParseException exception) {
            throw new ExFailure("XML document syntax is invalid", exception);
        }
        return xml;
    }
}
