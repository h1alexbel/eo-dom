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
 * Text content retrieval from DOM element.
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "element.text-content")
public final class EOelement$EOserialized$EOtext_content extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws XmlParseException {
        return new Data.ToPhi(
            new XmlNode.Default(new Dataized(this.take(Phi.RHO).take("src")).asString())
                .text().getBytes()
        );
    }
}
