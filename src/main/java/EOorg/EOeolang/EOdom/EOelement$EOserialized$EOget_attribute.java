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
import org.eolang.PhVoid;
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
        this.add("attr", new PhVoid("attr"));
    }

    @Override
    public Phi lambda() throws XmlParseException {
        return new Data.ToPhi(
            new XmlNode.Default(new Dataized(this.take(Phi.RHO).take("src")).asString())
                .attr(new Dataized(this.take("attr")).asString())
                .getBytes()
        );
    }
}
