/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

/**
 * Tests for {@link XmlNode}.
 *
 * @since 0.0.0
 */
@SuppressWarnings("PMD.TooManyMethods")
final class XmlNodeTest {

    @Test
    void parsesDocumentFromString() throws XmlParseException, ImpossibleModificationException {
        MatcherAssert.assertThat(
            "Parsed document doesn't match with expected",
            new XmlNode.Default("<program><test foo=\"f\">bar</test></program>")
                .elem("program").elem("test").asString(),
            Matchers.equalTo(
                new Xembler(
                    new Directives()
                        .add("test")
                        .attr("foo", "f")
                        .set("bar")
                ).xml()
            )
        );
    }

    @Test
    void throwsIoExceptionOnBrokenXml() {
        MatcherAssert.assertThat(
            "Exception was not thrown, but it should",
            () -> new XmlNode.Default("broken"),
            new Throws<>("Cannot parse XML string: 'broken'", XmlParseException.class)
        );
    }

    @Test
    void parsesNodeFromNextElement() throws XmlParseException, ImpossibleModificationException {
        MatcherAssert.assertThat(
            "Parsed document doesn't match with expected",
            new XmlNode.Default("<program><test foo=\"f\">bar</test></program>")
                .elem("test").asString(),
            Matchers.equalTo(
                new Xembler(
                    new Directives().add("test").attr("foo", "f").set("bar")
                ).xml()
            )
        );
    }

    @Test
    void throwsOnEmptyNodeInElementChain() {
        MatcherAssert.assertThat(
            "Exception should be thrown, but it was not",
            () -> new XmlNode.Default("<program/>").elem("f").elem("bar"),
            new Throws<>(
                "Cannot read 'bar' element inside, since node itself is empty!",
                IllegalStateException.class
            )
        );
    }

    @Test
    void throwsOnPrintingEmptyNode() {
        MatcherAssert.assertThat(
            "Exception should be thrown, but it was not",
            () -> new XmlNode.Default("<program/>").elem("f").asString(),
            new Throws<>("Node is empty", IllegalStateException.class)
        );
    }

    @Test
    void findsAttribute() throws XmlParseException {
        MatcherAssert.assertThat(
            "Attribute value does not match with expected",
            new XmlNode.Default("<program><test foo=\"f\">bar</test></program>")
                .elem("test").attr("foo"),
            Matchers.equalTo("f")
        );
    }

    @Test
    void throwsOnNonExistingAttribute() {
        MatcherAssert.assertThat(
            "Exception was not thrown, though attribute does not exists",
            () -> new XmlNode.Default("<program/>").attr("b"),
            new Throws<>("Attribute 'b' was not found in the node", IllegalArgumentException.class)
        );
    }

    @Test
    void returnsTextInside() throws XmlParseException {
        MatcherAssert.assertThat(
            "Text node does not match with expected",
            new XmlNode.Default("<program>foo</program>").text(),
            Matchers.equalTo("foo")
        );
    }

    @Test
    void returnsTextAfterChaining() throws XmlParseException {
        MatcherAssert.assertThat(
            "Output does not match with expected",
            new XmlNode.Default("<program><test foo=\"f\">bar</test></program>")
                .elem("program")
                .elem("test")
                .text(),
            Matchers.equalTo("bar")
        );
    }

    @Test
    void returnsEmptyText() throws XmlParseException {
        MatcherAssert.assertThat(
            "Text should be empty",
            new XmlNode.Default("<program/>").text(),
            Matchers.emptyString()
        );
    }

    @Test
    void throwsOnFindingTextInEmptyNode() {
        MatcherAssert.assertThat(
            "Exception was not thrown",
            () -> new XmlNode.Default("<foo/>").elem("f").text(),
            new Throws<>(
                "Cannot read text inside, since node itself is empty!",
                IllegalStateException.class
            )
        );
    }

    @Test
    void throwsOnFindingAttributeInEmptyNode() {
        MatcherAssert.assertThat(
            "Exception should be thrown, but it was not",
            () -> new XmlNode.Default("<bar/>").elem("f").attr("x"),
            new Throws<>(
                "Cannot read 'x' attribute inside, since node itself is empty!",
                IllegalStateException.class
            )
        );
    }
}
