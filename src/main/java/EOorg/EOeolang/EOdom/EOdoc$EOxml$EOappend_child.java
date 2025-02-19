package EOorg.EOeolang.EOdom;

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
    public Phi lambda() throws XmlParseException {
        final String xml = new XmlNode.Default(
            (Element)
                new XmlNode.Default(
                    new Dataized(this.take(Attr.RHO).take("serialized")).asString()
                ).self().appendChild(
                    new XmlNode.Default(new Dataized(this.take("child").take("as-string")).asString())
                        .self()
                )
        ).asString();
        final Phi fresh = Phi.Φ.take("org.eolang.dom.doc").copy().take(Attr.PHI);
        fresh.put("data", new Data.ToPhi(xml));
        return fresh;
    }
}
