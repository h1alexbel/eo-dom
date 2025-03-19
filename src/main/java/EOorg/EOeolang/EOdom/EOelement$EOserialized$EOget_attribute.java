/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import org.eolang.AtVoid;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Attribute retrieval from DOM element.
 *
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "element.get-attribute")
public final class EOelement$EOserialized$EOget_attribute extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOelement$EOserialized$EOget_attribute() {
        this.add("attr", new AtVoid("attr"));
    }

    @Override
    public Phi lambda() throws XmlParseException {
        return new Data.ToPhi(
            new XmlNode.Default(new Dataized(this.take(Attr.RHO).take("xml")).asString())
                .attr(new Dataized(this.take("attr")).asString())
                .getBytes()
        );
    }
}
