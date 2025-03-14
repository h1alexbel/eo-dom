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

/**
 * Finds the next sibling of the current node.
 * @since 0.0.0
 */
@XmirObject(oname = "element.next-sibling")
public final class EOelement$EOserialized$EOnext_sibling extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws Exception {
        final Phi elem = Phi.Φ.take("org.eolang.dom.element").copy();
        final XmlNode.Default xml = new XmlNode.Default(
            new Dataized(this.take(Attr.RHO).take("xml")).asString()
        );
        System.out.println(xml.self().getNextSibling());
        elem.put(
            "xml",
            new Data.ToPhi(
                ""
            )
        );
        return elem;
    }
}
