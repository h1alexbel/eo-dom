package EOorg.EOeolang.EOdom;

import org.eolang.AtVoid;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Element atom.
 *
 * @since 0.0.0
 */
@XmirObject(oname = "element.serialized")
public final class EOelement$EOφ extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    public EOelement$EOφ() {
        this.add("xml", new AtVoid("xml"));
        this.add("parent", new AtVoid("parent"));
    }

    @Override
    public Phi lambda() throws Exception {
        final Phi serialized = this.take(Attr.RHO).take("serialized");
        serialized.put(
            "src",
            new Data.ToPhi(
                new XmlNode.Default(
                    new Dataized(this.take("xml")).asString()
                ).asString().getBytes()
            )
        );
        serialized.put(
            "parent",
            new Data.ToPhi(
                new XmlNode.Default(
                    new Dataized(this.take("parent")).asString()
                ).asString().getBytes()
            )
        );
        return serialized;
    }
}
