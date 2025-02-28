/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.eolang.AtVoid;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Append child to the document.
 *
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "doc.xml.append-child")
public final class EOdoc$EOxml$EOappend_child extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOdoc$EOxml$EOappend_child() {
        this.add("child", new AtVoid("child"));
    }

    @Override
    public Phi lambda() throws ParserConfigurationException, IOException, SAXException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        final Document base = builder.parse(
            new InputSource(
                new StringReader(new Dataized(this.take(Attr.RHO).take("serialized")).asString())
            )
        );
        base.getDocumentElement().appendChild(
            base.importNode(
                builder.parse(
                    new InputSource(
                        new StringReader(
                            new Dataized(this.take("child").take("as-string")).asString()
                        )
                    )
                ).getDocumentElement(),
                true
            )
        );
        final Phi fresh = Phi.Φ.take("org.eolang.dom.doc").copy().take(Attr.PHI);
        fresh.put(
            "data",
            new Data.ToPhi(new XmlNode.Default(base.getDocumentElement()).asString().getBytes())
        );
        return fresh;
    }
}
