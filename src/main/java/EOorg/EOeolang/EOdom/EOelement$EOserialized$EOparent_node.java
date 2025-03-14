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
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Parent node.
 * @since 0.0.0
 */
@XmirObject(oname = "element.parent-node")
public final class EOelement$EOserialized$EOparent_node extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws Exception {
        final Phi element = Phi.Φ.take("org.eolang.dom.element").take("serialized").copy();
//        return this.take(Attr.RHO).take("parent");
        return element;
    }
}
