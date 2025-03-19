/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import org.eolang.AtVoid;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Serialized element.
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "element.serialized")
public final class EOelement$EOφ extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOelement$EOφ() {
        this.add("xml", new AtVoid("xml"));
        this.add("parent", new AtVoid("parent"));
    }

    @Override
    public Phi lambda() throws Exception {
        final Phi serialized = this.take(Attr.RHO).take("serialized");
        serialized.put(
            "src",
            new Data.ToPhi(
                new XmlNode.Default(
                    new Dataized(this.take("xml")).asString()
                ).asString().getBytes()
            )
        );
        serialized.put(
            "parent",
            new Data.ToPhi(
                new XmlNode.Default(
                    new Dataized(this.take("parent")).asString()
                ).asString().getBytes()
            )
        );
        return serialized;
    }
}
