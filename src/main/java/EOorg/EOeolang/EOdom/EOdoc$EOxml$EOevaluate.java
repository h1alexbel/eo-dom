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
import org.eolang.Atom;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.PhVoid;
import org.eolang.Phi;
import org.eolang.XmirObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Evaluate XPath expression on given document.
 *
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "doc.xml.evaluate")
public final class EOdoc$EOxml$EOevaluate extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOdoc$EOxml$EOevaluate() {
        this.add("xpath", new PhVoid("xpath"));
        this.add("return", new PhVoid("return"));
    }

    @Override
    public Phi lambda() throws Exception {
        final XPath xpath = XPathFactory.newInstance().newXPath();
        final Map<String, QName> types = new HashMap<>(0);
        types.put("NUMBER", XPathConstants.NUMBER);
        types.put("STRING", XPathConstants.STRING);
        types.put("BOOLEAN", XPathConstants.BOOLEAN);
        types.put("NODE", XPathConstants.NODE);
        types.put("NODESET", XPathConstants.NODESET);
        final QName rtype = types.get(
            new Dataized(this.take("return")).asString().toUpperCase(Locale.ROOT)
        );
        final Document doc = new XmlNode.Default(
            new Dataized(this.take(Phi.RHO).take("serialized")).asString()
        ).self().getOwnerDocument();
        final Phi result;
        final String expression = new Dataized(this.take("xpath")).asString();
        if (XPathConstants.NODE.equals(rtype)) {
            result = Phi.Φ.take("org.eolang.dom.element").take("serialized").copy();
            final byte[] data = new XmlNode.Default(
                (Element) xpath.evaluate(expression, doc, XPathConstants.NODE)
            ).asString().getBytes();
            result.put("src", new Data.ToPhi(data));
            result.put("parent", new Data.ToPhi(data));
        } else if (XPathConstants.NODESET.equals(rtype)) {
            result = new NodesCollection(
                (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET)
            ).value();
        } else {
            result = new Data.ToPhi(
                xpath.evaluate(
                    expression,
                    doc,
                    rtype
                )
            );
        }
        return result;
    }
}
