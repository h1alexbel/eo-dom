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
 * Next sibling of the current node.
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "element.next-sibling")
public final class EOelement$EOserialized$EOnext_sibling extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws Exception {
        final Phi elem = Phi.Φ.take("org.eolang.dom.element").take("serialized").copy();
        final XmlNode.Default source = new XmlNode.Default(
            new Dataized(this.take(Attr.RHO).take("nsib")).asString()
        );
        final XmlNode.Default parent = new XmlNode.Default(
            new Dataized(this.take(Attr.RHO).take("parent")).asString()
        );
        elem.put("src", new Data.ToPhi(source.asString().getBytes()));
        elem.put("parent", new Data.ToPhi(parent.asString().getBytes()));
        final XmlNode.Default first = new XmlNode.Default(parent.getFirstChild());
        if (first.getNextSibling() != null) {
            final XmlNode.Default sibled = new XmlNode.Default(first.getNextSibling());
            if (sibled.getNextSibling() != null) {
                elem.put(
                    "nsib",
                    new Data.ToPhi(
                        new XmlNode.Default(sibled.getNextSibling())
                            .asString().getBytes()
                    )
                );
            }
        }
        return elem;
    }
}
