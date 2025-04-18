/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * XML Document Node.
 *
 * @since 0.0.0
 * @todo #50:60min Refactor XmlNode interface and it's implementations.
 *  Originally it was designed to present the behaviour of node in DOM document.
 *  Now, we should refactor and decompose this interface to present basic DOM element
 *  with an entry point to W3C API.
 */
@SuppressWarnings("PMD.TooManyMethods")
public interface XmlNode {

    /**
     * Element inside the node.
     *
     * @param name Name
     * @return XML element inside the node
     */
    XmlNode elem(String name);

    /**
     * Attr.
     * @param aname Attribute name
     * @return Attribute value of the node
     */
    String attr(String aname);

    /**
     * Text inside.
     * @return Text inside the node
     */
    String text();

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
                    "Cannot read '%s' element inside, since node itself is empty!",
                    name
                )
            );
        }

        @Override
        public String attr(final String aname) {
            throw new IllegalStateException(
                String.format(
                    "Cannot read '%s' attribute inside, since node itself is empty!",
                    aname
                )
            );
        }

        @Override
        public String text() {
            throw new IllegalStateException(
                "Cannot read text inside, since node itself is empty!"
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
        Default(final String xml) throws XmlParseException {
            this(XmlNode.Default.fromString(xml));
        }

        /**
         * Ctor.
         * @param element Element
         */
        Default(final Element element) {
            this.base = element;
        }

        public Element self() {
            return this.base;
        }

        public Element getFirstChild() {
            Node node = this.self().getFirstChild();
            while (node != null && (int) node.getNodeType() == (int) Node.TEXT_NODE) {
                node = node.getNextSibling();
            }
            return (Element) node;
        }

        public Element getLastChild() {
            Node node = this.self().getLastChild();
            while (node != null && (int) node.getNodeType() == (int) Node.TEXT_NODE) {
                node = node.getPreviousSibling();
            }
            return (Element) node;
        }

        public Element getParentNode() {
            Node node = this.self().getParentNode();
            while (node != null && (int) node.getNodeType() == (int) Node.TEXT_NODE) {
                node = node.getParentNode();
            }
            return (Element) node;
        }

        public Element getNextSibling() {
            Node node = this.self().getNextSibling();
            while (node != null && (int) node.getNodeType() == (int) Node.TEXT_NODE) {
                node = node.getNextSibling();
            }
            return (Element) node;
        }

        public Element getPreviousSibling() {
            Node node = this.self().getPreviousSibling();
            while (node != null && (int) node.getNodeType() == (int) Node.TEXT_NODE) {
                node = node.getPreviousSibling();
            }
            return (Element) node;
        }

        public NodeList getElementsByTagName(final String name) {
            return this.base.getElementsByTagName(name);
        }

        public NodeList getElementsByTagNameNs(final String namespace, final String local) {
            return this.base.getOwnerDocument().getElementsByTagNameNS(namespace, local);
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
        public String text() {
            return this.base.getTextContent().trim();
        }

        @Override
        public String asString() {
            final StringWriter writer = new StringWriter();
            try {
                final Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                transformer.transform(
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
        private static Element fromString(final String xml) throws XmlParseException {
            try {
                final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                dbf.setNamespaceAware(true);
                final Element doc = dbf.newDocumentBuilder()
                    .parse(new ByteArrayInputStream(xml.getBytes()))
                    .getDocumentElement();
                doc.normalize();
                return doc;
            } catch (final ParserConfigurationException exception) {
                throw new IllegalStateException(
                    String.format(
                        "Cannot instantiate parser for XML: '%s'",
                        xml
                    ),
                    exception
                );
            } catch (final SAXException exception) {
                throw new XmlParseException(
                    String.format("Cannot parse XML string: '%s'", xml),
                    exception
                );
            } catch (final IOException exception) {
                throw new IllegalStateException("I/O failed", exception);
            }
        }
    }
}
