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
 * Element with attribute.
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "element.with-attribute")
public final class EOelement$EOserialized$EOwith_attribute extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOelement$EOserialized$EOwith_attribute() {
        this.add("attr", new AtVoid("attr"));
        this.add("value", new AtVoid("value"));
    }

    @Override
    public Phi lambda() throws Exception {
        final Element self = new XmlNode.Default(
            new Dataized(this.take(Attr.RHO).take("xml")).asString()
        ).self();
        self.setAttribute(
            new Dataized(this.take("attr")).asString(), new Dataized(this.take("value")).asString()
        );
        final Phi fresh = Phi.Φ.take("org.eolang.dom.element").copy();
        fresh.put("xml", new Data.ToPhi(new XmlNode.Default(self).asString().getBytes()));
        return fresh;
    }
}
