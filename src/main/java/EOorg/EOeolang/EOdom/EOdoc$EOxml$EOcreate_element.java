/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import javax.xml.parsers.DocumentBuilderFactory;
import org.eolang.Atom;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.PhVoid;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Create DOM element.
 *
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "doc.xml.create-element")
public final class EOdoc$EOxml$EOcreate_element extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOdoc$EOxml$EOcreate_element() {
        this.add("lname", new PhVoid("lname"));
    }

    @Override
    public Phi lambda() throws Exception {
        final Phi elem = Phi.Φ.take("org.eolang.dom.element").take(Phi.PHI).copy();
        final String node = new XmlNode.Default(
            DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument()
                .createElement(new Dataized(this.take("lname")).asString())
        ).asString();
        elem.put("xml", new Data.ToPhi(node));
        elem.put("parent", new Data.ToPhi(node));
        return elem;
    }
}
