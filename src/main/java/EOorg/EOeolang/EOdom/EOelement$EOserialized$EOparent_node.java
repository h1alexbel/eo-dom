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
 * Parent node.
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "element.parent-node")
public final class EOelement$EOserialized$EOparent_node extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws Exception {
        final Phi elem = Phi.Φ.take("org.eolang.dom.element").take(Phi.PHI).copy();
        final byte[] data = new XmlNode.Default(
            new Dataized(this.take(Phi.RHO).take("parent")).asString()
        ).asString().getBytes();
        elem.put("xml", new Data.ToPhi(data));
        elem.put("parent", new Data.ToPhi(data));
        return elem;
    }
}
