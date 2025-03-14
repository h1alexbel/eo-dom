package EOorg.EOeolang.EOdom;

import org.eolang.AtVoid;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;

/**
 * Serialized element.
 * @since 0.0.0
 */
public final class EOelement$EOφ extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOelement$EOφ() {
        this.add("xml", new AtVoid("xml"));
        this.add("parent", new AtVoid("parent"));
    }

    @Override
    public Phi lambda() throws Exception {
        final String xml = new Dataized(this.take("xml")).asString();
        final Phi serialized = this.take(Attr.RHO).take("serialized");
        serialized.put(
            "src",
            new Data.ToPhi(xml)
        );
        final String p = new Dataized(this.take("parent")).asString();
//        System.out.println(p);
        serialized.put("parent", new Data.ToPhi(p));
        return serialized;
    }
}
