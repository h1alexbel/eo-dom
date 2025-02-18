package EOorg.EOeolang.EOdom;

import javax.xml.transform.TransformerException;
import org.eolang.AtVoid;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;
import org.w3c.dom.NodeList;

/**
 * Elements by tag name in the given namespace.
 *
 * @since 0.0.0
 */
@XmirObject(oname = "doc.xml.get-elements-by-tag-name-ns")
public final class EOdoc$EOxml$EOget_elements_by_tag_name_ns extends PhDefault implements Atom {

    public EOdoc$EOxml$EOget_elements_by_tag_name_ns() {
        this.add("ns", new AtVoid("ns"));
        this.add("name", new AtVoid("name"));
    }

    @Override
    public Phi lambda() throws XmlParseException, TransformerException {
        final NodeList nodes = new XmlNode.Default(
            new Dataized(this.take(Attr.RHO).take("serialized")).asString()
        ).getElementsByTagNameNs(
            new Dataized(this.take("ns")).asString(),
            new Dataized(this.take("name")).asString()
        );
        return new NodesCollection(nodes).value();
    }
}
