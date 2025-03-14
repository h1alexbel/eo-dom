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

/**
 * Tests for {@link XmlNode}.
 *
 * @since 0.0.0
 */
@SuppressWarnings("PMD.TooManyMethods")
final class XmlNodeTest {

    @Test
    void throwsIoExceptionOnBrokenXml() {
        MatcherAssert.assertThat(
            "Exception was not thrown, but it should",
            () -> new XmlNode.Default("broken"),
            new Throws<>("Cannot parse XML string: 'broken'", XmlParseException.class)
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
    void returnsEmptyText() throws XmlParseException {
        MatcherAssert.assertThat(
            "Text should be empty",
            new XmlNode.Default("<program/>").text(),
            Matchers.emptyString()
        );
    }
}
