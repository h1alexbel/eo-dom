package EOorg.EOeolang.EOdom;

import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Parent node.
 * @since 0.0.0
 */
@XmirObject(oname = "element.parent-node")
public final class EOelement$EOserialized$EOparent_node extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws Exception {
        final Phi elem = Phi.Φ.take("org.eolang.dom.element").take(Attr.PHI).copy();
        final XmlNode.Default parent = new XmlNode.Default(
            new Dataized(this.take(Attr.RHO).take("parent")).asString()
        );
        elem.put(
            "xml", new Data.ToPhi(parent.asString().getBytes())
        );
        elem.put(
            "parent", new Data.ToPhi(parent.asString().getBytes())
        );
        return elem;
    }
}
