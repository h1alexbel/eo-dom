/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import java.util.List;
import org.cactoos.list.ListOf;
import org.eolang.AtVoid;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * DOM element at index from node collection.
 *
 * @checkstyle TypeNameCheck (5 lines)
 * @since 0.0.0
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "html-collection.at")
public final class EOhtml_collection$EOat extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOhtml_collection$EOat() {
        this.add("pos", new AtVoid("pos"));
    }

    @Override
    public Phi lambda() {
        final Double pos = new Dataized(this.take("pos")).asNumber();
        final List<String> nodes = new ListOf<>(
            new Dataized(this.take(Attr.RHO).take("nodes"))
                .asString().split("\n")
        );
        final Phi element = Phi.Φ.take("org.eolang.dom.element").take(Attr.PHI).copy();
        element.put("xml", new Data.ToPhi(nodes.get(pos.intValue())));
        element.put("parent", new Data.ToPhi(nodes.get(pos.intValue())));
        return element;
    }
}
