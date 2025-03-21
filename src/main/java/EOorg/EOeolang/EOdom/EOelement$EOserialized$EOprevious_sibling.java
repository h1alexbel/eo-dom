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
 * Previous sibling of the current node.
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "element.previous-sibling")
public final class EOelement$EOserialized$EOprevious_sibling extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws Exception {
        final Phi elem = Phi.Φ.take("org.eolang.dom.element").take("serialized").copy();
        final XmlNode.Default source = new XmlNode.Default(
            new Dataized(this.take(Attr.RHO).take("psib")).asString()
        );
        final XmlNode.Default parent = new XmlNode.Default(
            new Dataized(this.take(Attr.RHO).take("parent")).asString()
        );
        elem.put("src", new Data.ToPhi(source.asString().getBytes()));
        elem.put("parent", new Data.ToPhi(parent.asString().getBytes()));
        final XmlNode.Default last = new XmlNode.Default(parent.getLastChild());
        if (last.getPreviousSibling() != null) {
            final XmlNode.Default sibled = new XmlNode.Default(last.getPreviousSibling());
            if (sibled.getPreviousSibling() != null) {
                elem.put(
                    "psib",
                    new Data.ToPhi(
                        new XmlNode.Default(sibled.getPreviousSibling())
                            .asString().getBytes()
                    )
                );
            }
        }
        return elem;
    }
}
