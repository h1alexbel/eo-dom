/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import java.io.StringWriter;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.cactoos.Scalar;
import org.eolang.Data;
import org.eolang.Phi;
import org.w3c.dom.NodeList;

/**
 * Collection of nodes.
 * @since 0.0.0
 */
public final class NodesCollection implements Scalar<Phi> {

    /**
     * Nodes.
     */
    private final NodeList nodes;

    /**
     * Ctor.
     * @param nds Nodes
     */
    public NodesCollection(final NodeList nds) {
        this.nodes = nds;
    }

    @Override
    public Phi value() throws TransformerException {
        final StringBuilder serialized = new StringBuilder();
        for (int pos = 0; pos < this.nodes.getLength(); pos += 1) {
            final StringWriter writer = new StringWriter();
            TransformerFactory.newInstance().newTransformer()
                .transform(new DOMSource(this.nodes.item(pos)), new StreamResult(writer));
            serialized.append(writer).append('\n');
        }
        final Phi collection = Phi.Φ.take("org.eolang.dom.html-collection");
        collection.put("nodes", new Data.ToPhi(serialized.toString().getBytes()));
        return collection;
    }
}
