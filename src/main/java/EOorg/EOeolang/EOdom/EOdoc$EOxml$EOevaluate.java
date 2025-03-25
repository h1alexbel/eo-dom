/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.eolang.AtVoid;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Evaluate XPath expression on given document.
 *
 * @checkstyle TypeNameCheck (5 lines)
 * @since 0.0.0
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "doc.xml.evaluate")
public final class EOdoc$EOxml$EOevaluate extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOdoc$EOxml$EOevaluate() {
        this.add("xpath", new AtVoid("xpath"));
    }

    @Override
    public Phi lambda() throws Exception {
        final String source = new Dataized(this.take(Attr.RHO).take("serialized")).asString();
        return new Data.ToPhi(
            XPathFactory.newInstance().newXPath().evaluate(
                new Dataized(this.take("xpath")).asString(),
                new XmlNode.Default(source).self().getOwnerDocument(),
                XPathConstants.STRING
            )
        );
    }
}
