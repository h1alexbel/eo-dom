package EOorg.EOeolang.EOdom;

import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * XML document as string.
 * @since 0.0.0
 */
@XmirObject(oname = "doc.xml.as-string")
public final class EOdoc$EOxml$EOas_string extends PhDefault implements Atom {

    @Override
    public Phi lambda() {
        return new PhDefault(
            new Dataized(this.take(Attr.RHO).take("serialized")).asString().getBytes()
        );
    }
}
