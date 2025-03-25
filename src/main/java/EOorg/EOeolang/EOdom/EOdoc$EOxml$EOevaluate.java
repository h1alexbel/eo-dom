/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
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
import org.w3c.dom.Element;

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
        this.add("return", new AtVoid("return"));
    }

    @Override
    public Phi lambda() throws Exception {
        final String source = new Dataized(this.take(Attr.RHO).take("serialized")).asString();
        final XPath xpath = XPathFactory.newInstance().newXPath();
        final Map<String, QName> types = new HashMap<>(0);
        types.put("STRING", XPathConstants.STRING);
        types.put("NODE", XPathConstants.NODE);
        types.put("NODESET", XPathConstants.NODESET);
        types.put("NUMBER", XPathConstants.NUMBER);
        final QName rtype = types.get(
            new Dataized(this.take("return")).asString().toUpperCase(Locale.ROOT)
        );
        final Phi result;
        if (XPathConstants.NODE.equals(rtype)) {
            result = Phi.Φ.take("org.eolang.dom.element").take("serialized").copy();
            final byte[] data = new XmlNode.Default(
                (Element) xpath.evaluate(
                    new Dataized(this.take("xpath")).asString(),
                    new XmlNode.Default(source).self().getOwnerDocument(),
                    XPathConstants.NODE
                )
            ).asString().getBytes();
            result.put("src", new Data.ToPhi(data));
            result.put("parent", new Data.ToPhi(data));
//            System.out.println(
//                xpath.evaluate(
//                    new Dataized(this.take("xpath")).asString(),
//                    new XmlNode.Default(source).self().getOwnerDocument(),
//                    XPathConstants.NODE
//                )
//            );
//            Phi.Φ.take("org.eolang.dom.element");
        } else {
            result = new Data.ToPhi(
                xpath.evaluate(
                    new Dataized(this.take("xpath")).asString(),
                    new XmlNode.Default(source).self().getOwnerDocument(),
                    XPathConstants.STRING
                )
            );
        }
        return result;
    }
}
