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

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Tests for {@link XmlNode}.
 *
 * @since 0.0.0
 */
final class XmlNodeTest {

    @Test
    void parsesDocumentFromString() {
        MatcherAssert.assertThat(
            "Parsed document doesn't match with expected",
            new XmlNode.Default("<program><test foo=\"f\">bar</test></program>")
                .elem("program").elem("test").asString(),
            Matchers.equalTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test foo=\"f\">bar</test>"
            )
        );
    }

    @Test
    void parsesNodeFromNextElement() {
        MatcherAssert.assertThat(
            "Parsed document doesn't match with expected",
            new XmlNode.Default("<program><test foo=\"f\">bar</test></program>")
                .elem("test").asString(),
            Matchers.equalTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test foo=\"f\">bar</test>"
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
    void findsAttribute() {
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
    void returnsTextInside() {
        MatcherAssert.assertThat(
            "Text node does not match with expected",
            new XmlNode.Default("<program>foo</program>").text(),
            Matchers.equalTo("foo")
        );
    }

    @Test
    void returnsTextAfterChaining() {
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
    void returnsEmptyText() {
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
            () -> new XmlNode.Default("<program/>").elem("f").text(),
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
            () -> new XmlNode.Default("<program/>").elem("f").attr("x"),
            new Throws<>(
                "Cannot read 'x' attribute inside, since node itself is empty!",
                IllegalStateException.class
            )
        );
    }
}
