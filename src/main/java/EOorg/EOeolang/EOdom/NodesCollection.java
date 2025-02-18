/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016-2025 Objectionary.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
