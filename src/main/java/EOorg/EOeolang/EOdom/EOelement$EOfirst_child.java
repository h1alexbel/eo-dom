package EOorg.EOeolang.EOdom;

import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;
import org.w3c.dom.Element;

/**
 * First child of the element in the tree.
 *
 * @since 0.0.0
 */
@XmirObject(oname = "element.first-child")
public final class EOelement$EOfirst_child extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws Exception {
        final Phi elem = Phi.Φ.take("org.eolang.dom.element").copy();
        elem.put(
            "xml",
            new Data.ToPhi(
                new XmlNode.Default(
                    (Element)
                        new XmlNode.Default(new Dataized(this.take(Attr.RHO).take("xml")).asString())
                            .self().getFirstChild()
                ).asString().getBytes()
            )
        );
        return elem;
    }
}
