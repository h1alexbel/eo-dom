/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import org.cactoos.list.ListOf;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Length of node collection.
 *
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "html-collection.length")
public final class EOhtml_collection$EOlength extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws Exception {
        final int result;
        final String xml = new Dataized(this.take(Attr.RHO).take("nodes"))
            .asString();
        if (xml.isEmpty()) {
            result = 0;
        } else {
            result = new ListOf<>(xml.split("\n")).size();
        }
        return new Data.ToPhi(result);
    }
}
