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
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * First child of the element in the tree.
 *
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "element.first-child")
public final class EOelement$EOserialized$EOfirst_child extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws Exception {
        final Phi elem = Phi.Φ.take("org.eolang.dom.element").take("serialized").copy();
        final XmlNode.Default source = new XmlNode.Default(
            new XmlNode.Default(
                new Dataized(this.take(Phi.RHO).take("src")).asString()
            ).getFirstChild()
        );
        elem.put(
            "src",
            new Data.ToPhi(
                source.asString().getBytes()
            )
        );
        if (source.self() != null) {
            elem.put(
                "parent",
                new Data.ToPhi(
                    new XmlNode.Default(source.getParentNode()).asString()
                        .getBytes()
                )
            );
            elem.put(
                "nsib",
                new Data.ToPhi(new XmlNode.Default(source.getNextSibling()).asString().getBytes())
            );
        }
        return elem;
    }
}
