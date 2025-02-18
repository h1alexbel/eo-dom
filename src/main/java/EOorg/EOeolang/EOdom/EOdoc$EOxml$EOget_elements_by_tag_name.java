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

import javax.xml.transform.TransformerException;
import org.eolang.AtVoid;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;
import org.w3c.dom.NodeList;

/**
 * Get DOM elements by tag name.
 * @since 0.0.0
 * @todo #8:45min Return proper error, if element not found with tag name.
 *  We should return proper error, if element is not found. Currently, the
 *  default EO error message will be thrown. Don't forget to add unit test.
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "doc.xml.get-elements-by-tag-name")
public final class EOdoc$EOxml$EOget_elements_by_tag_name extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOdoc$EOxml$EOget_elements_by_tag_name() {
        this.add("name", new AtVoid("name"));
    }

    @Override
    public Phi lambda() throws XmlParseException, TransformerException {
        final NodeList nodes = new XmlNode.Default(
            new Dataized(this.take(Attr.RHO).take("serialized")).asString()
        ).getElementsByTagName(new Dataized(this.take("name")).asString());
        return new NodesCollection(nodes).value();
//        final StringBuilder serialized = new StringBuilder();
//        for (int pos = 0; pos < nodes.getLength(); pos += 1) {
//            final StringWriter writer = new StringWriter();
//            TransformerFactory.newInstance().newTransformer()
//                .transform(new DOMSource(nodes.item(pos)), new StreamResult(writer));
//            serialized.append(writer).append('\n');
//        }
//        final Phi collection = Phi.Φ.take("org.eolang.dom.html-collection");
//        collection.put("nodes", new Data.ToPhi(serialized.toString().getBytes()));
//        return collection;
    }
}
