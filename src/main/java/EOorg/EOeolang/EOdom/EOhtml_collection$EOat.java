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
import org.eolang.Atom;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.PhVoid;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * DOM element at index from node collection.
 *
 * @since 0.0.0
 * @todo #64:45min Set correct parent attribute when of node collection.
 *  Currently, we are setting the same node as we found in collection by requested position.
 *  Instead, we should modify our html-collection to support parent node, and put into
 *  parent attribute parent node of the resulted collection. Don't forget to add this EO
 *  test into doc-tests.eo:
 *
 *  <pre>
 *  {@code
 *  [] > retrieves-parent-node
 *   dom-parser.parse-from-string > doc
 *     "xml here..."
 *   doc.get-elements-by-tag-name "station" > stations
 *   stations.at 0 > first
 *   first.parent-node > trip
 *   trip.get-attribute "oneway" > result
 *   eq. > @
 *     result
 *     "maybe"
 *  }
 *  </pre>
 *
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "html-collection.at")
public final class EOhtml_collection$EOat extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOhtml_collection$EOat() {
        this.add("pos", new PhVoid("pos"));
    }

    @Override
    public Phi lambda() {
        final Double pos = new Dataized(this.take("pos")).asNumber();
        final List<String> nodes = new ListOf<>(
            new Dataized(this.take(Phi.RHO).take("nodes"))
                .asString().split("\n")
        );
        final Phi element = Phi.Φ.take("org.eolang.dom.element").take(Phi.PHI).copy();
        element.put("xml", new Data.ToPhi(nodes.get(pos.intValue())));
        element.put("parent", new Data.ToPhi(nodes.get(pos.intValue())));
        return element;
    }
}
