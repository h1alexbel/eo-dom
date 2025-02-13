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
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.cactoos.io.InputOf;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * XML Document Node.
 *
 * @since 0.0.0
 */
public interface XmlNode {

    /**
     * Element inside the node.
     *
     * @param name Name
     * @return XML element inside the node
     */
    XmlNode elem(String name);

    String attr(String aname);

    /**
     * Serialize node as string.
     *
     * @return XML Node as string
     */
    String asString();

    /**
     * Empty XML node.
     *
     * @since 0.0.0
     */
    final class Empty implements XmlNode {

        @Override
        public XmlNode elem(final String name) {
            throw new IllegalStateException(
                String.format(
                    "There is no '%s' element inside, since node itself is empty!",
                    name
                )
            );
        }

        @Override
        public String attr(final String aname) {
            throw new IllegalStateException(
                String.format(
                    "There is no '%s' attribute inside, since node itself is empty!",
                    aname
                )
            );
        }

        @Override
        public String asString() {
            throw new IllegalStateException("Node is empty");
        }
    }

    /**
     * Default XML node.
     *
     * @since 0.0.0
     */
    final class Default implements XmlNode {

        /**
         * Base element.
         */
        private final Element base;

        /**
         * Ctor.
         * @param xml XML as string
         */
        Default(final String xml) {
            this(XmlNode.Default.fromString(xml));
        }

        /**
         * Ctor.
         * @param element Element
         */
        Default(final Element element) {
            this.base = element;
        }

        @Override
        public XmlNode elem(final String name) {
            final XmlNode result;
            if (this.base.getNodeName().equals(name)) {
                result = this;
            } else {
                final NodeList nodes = this.base.getElementsByTagName(name);
                if (nodes.getLength() > 0) {
                    result = new XmlNode.Default((Element) nodes.item(0));
                } else {
                    result = new XmlNode.Empty();
                }
            }
            return result;
        }

        @Override
        public String attr(final String aname) {
            final String found = this.base.getAttribute(aname);
            if (found.isBlank()) {
                throw new IllegalArgumentException(
                    String.format("Attribute '%s' was not found in the node", aname)
                );
            }
            return found;
        }

        @Override
        public String asString() {
            final StringWriter writer = new StringWriter();
            try {
                TransformerFactory.newInstance().newTransformer().transform(
                    new DOMSource(this.base), new StreamResult(writer)
                );
            } catch (final TransformerException exception) {
                throw new IllegalStateException("Cannot transform node to string", exception);
            }
            return writer.toString();
        }

        /**
         * Create element from string.
         * @param xml XML as string
         * @return Element
         * @checkstyle IllegalCatchCheck (10 lines)
         */
        @SuppressWarnings("PMD.AvoidCatchingGenericException")
        private static Element fromString(final String xml) {
            try {
                return DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputOf(xml).stream())
                    .getDocumentElement();
            } catch (final Exception exception) {
                throw new IllegalStateException(
                    String.format("Cannot parse XML string: '%s'", xml),
                    exception
                );
            }
        }
    }
}
