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
import org.w3c.dom.Element;

/**
 * Element with text content.
 *
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "element.with-text")
public final class EOelement$EOwith_text extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOelement$EOwith_text() {
        this.add("content", new AtVoid("content"));
    }

    @Override
    public Phi lambda() throws Exception {
        final Element self = new XmlNode.Default(
            new Dataized(this.take(Attr.RHO).take("xml")).asString()
        ).self();
        self.setTextContent(new Dataized(this.take("content")).asString());
        final Phi fresh = Phi.Φ.take("org.eolang.dom.element").copy();
        fresh.put("xml", new Data.ToPhi(new XmlNode.Default(self).asString().getBytes()));
        return fresh;
    }
}
