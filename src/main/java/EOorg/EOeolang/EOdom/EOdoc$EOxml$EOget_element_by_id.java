package EOorg.EOeolang.EOdom;

import org.eolang.AtVoid;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;
import org.jsoup.Jsoup;

/**
 * Retrieval of the element with specified `id` property.
 *
 * @since 0.0.0
 */
@XmirObject(oname = "doc.xml.get-element-by-id")
public final class EOdoc$EOxml$EOget_element_by_id extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    public EOdoc$EOxml$EOget_element_by_id() {
        this.add("identifier", new AtVoid("identifier"));
    }

    @Override
    public Phi lambda() throws XmlParseException {
        final Phi element = Phi.Φ.take("org.eolang.dom.element");
        element.put(
            "xml",
            new Data.ToPhi(
                new XmlNode.Default(
                    Jsoup.parse(new Dataized(this.take(Attr.RHO).take("serialized")).asString())
                        .getElementById(new Dataized(this.take("identifier")).asString())
                        .toString()
                ).asString().getBytes()
            )
        );
        return element;
    }
}
