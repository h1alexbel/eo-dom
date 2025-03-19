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
 * Find the last child of the given element in the tree.
 *
 * @checkstyle TypeNameCheck (5 lines)
 * @since 0.0.0
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "element.last-child")
public final class EOelement$EOserialized$EOlast_child extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws Exception {
        final Phi elem = Phi.Φ.take("org.eolang.dom.element").take("serialized").copy();
        final XmlNode.Default source = new XmlNode.Default(
            new XmlNode.Default(
                new Dataized(this.take(Attr.RHO).take("src")).asString()
            ).getLastChild()
        );
        elem.put(
            "src",
            new Data.ToPhi(
                source.asString().getBytes()
            )
        );
        if (source.self() != null) {
            elem.put(
                "parent", new Data.ToPhi(
                    new XmlNode.Default(source.getParentNode()).asString()
                        .getBytes()
                )
            );
        }
        return elem;
    }
}
