package EOorg.EOeolang.EOdom;

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
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Append child to the document.
 *
 * @since 0.0.0
 */
@XmirObject(oname = "doc.xml.append-child")
public final class EOdoc$EOxml$EOappend_child extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    public EOdoc$EOxml$EOappend_child() {
        this.add("child", new AtVoid("child"));
    }

    @Override
    public Phi lambda() throws XmlParseException, ParserConfigurationException, IOException, SAXException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = factory.newDocumentBuilder();
        final Document base = builder.parse(
            new InputSource(
                new StringReader(new Dataized(this.take(Attr.RHO).take("serialized")).asString())
            )
        );
        final Document child = builder.parse(
            new InputSource(
                new StringReader(new Dataized(this.take("child").take("as-string")).asString())
            )
        );
        final Node imported = base.importNode(child.getDocumentElement(), true);
        base.getDocumentElement().appendChild(imported);
        final Phi fresh = Phi.Φ.take("org.eolang.dom.doc").copy().take(Attr.PHI);
        fresh.put(
            "data",
            new Data.ToPhi(new XmlNode.Default(base.getDocumentElement()).asString().getBytes())
        );
        return fresh;
    }
}
