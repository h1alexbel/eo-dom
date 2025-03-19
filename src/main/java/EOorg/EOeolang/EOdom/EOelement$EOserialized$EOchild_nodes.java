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
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Child nodes of the current element.
 *
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "element.child-nodes")
public final class EOelement$EOserialized$EOchild_nodes extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws Exception {
        return new NodesCollection(
            new XmlNode.Default(new Dataized(this.take(Attr.RHO).take("src")).asString())
                .self().getChildNodes()
        ).value();
    }
}
