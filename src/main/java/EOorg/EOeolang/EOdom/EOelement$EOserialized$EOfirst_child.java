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
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;
import org.w3c.dom.Element;

/**
 * First child of the element in the tree.
 *
 * @checkstyle TypeNameCheck (5 lines)
 * @since 0.0.0
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "element.first-child")
public final class EOelement$EOserialized$EOfirst_child extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws Exception {
        final Phi elem = Phi.Φ.take("org.eolang.dom.element").take(Attr.PHI).copy();
        final XmlNode.Default parent = new XmlNode.Default(
            new Dataized(this.take(Attr.RHO).take("parent")).asString()
        );
        elem.put(
            "xml",
            new Data.ToPhi(
                new XmlNode.Default(
                    (Element)
                        parent.self().getFirstChild()
                ).asString()
            )
        );
        elem.put(
            "parent", new Data.ToPhi(parent.asString())
        );
        return elem;
    }
}
