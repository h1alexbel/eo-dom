package EOorg.EOeolang.EOdom;

import java.util.List;
import java.util.Map;
import org.cactoos.Scalar;
import org.w3c.dom.Node;

/**
 * Map of siblings.
 * @since 0.0.0
 */
public final class SibMap implements Scalar<Map<Node, List<Node>>> {

    @Override
    public Map<Node, List<Node>> value() throws Exception {
        throw new UnsupportedOperationException("#value()");
    }
}
