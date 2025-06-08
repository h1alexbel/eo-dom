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
import org.jsoup.Jsoup;

/**
 * Retrieval of the element with specified `id` property.
 *
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "doc.xml.get-element-by-id")
public final class EOdoc$EOxml$EOget_element_by_id extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOdoc$EOxml$EOget_element_by_id() {
        this.add("identifier", new PhVoid("identifier"));
    }

    @Override
    public Phi lambda() throws XmlParseException {
        final Phi element = Phi.Φ.take("org.eolang.dom.element").take(Phi.PHI).copy();
        final String source = new Dataized(this.take(Phi.RHO).take("serialized")).asString();
        element.put(
            "xml",
            new Data.ToPhi(
                new XmlNode.Default(
                    Jsoup.parse(source)
                        .getElementById(new Dataized(this.take("identifier")).asString())
                        .toString()
                ).asString().getBytes()
            )
        );
        element.put("parent", new Data.ToPhi(source));
        return element;
    }
}
