package EOorg.EOeolang.EOdom; // NOPMD

import org.cactoos.list.ListOf;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Length of node collection.
 *
 * @since 0.0.0
 */
@XmirObject(oname = "html-collection.length")
public final class EOhtml_collection$EOlength extends PhDefault implements Atom {

    @Override
    public Phi lambda() throws Exception {
        return new Data.ToPhi(
            new ListOf<>(
                new Dataized(this.take(Attr.RHO).take("nodes"))
                    .asString().split("\n")
            ).size()
        );
    }
}
