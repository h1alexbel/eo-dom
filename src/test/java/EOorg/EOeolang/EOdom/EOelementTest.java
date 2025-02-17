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

import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EOelement}.
 *
 * @since 0.0.0
 */
final class EOelementTest {

    @Test
    void printsElement() {
        final String xml = "<abc><c>x</c></abc>";
        MatcherAssert.assertThat(
            "Element does not match with expected",
            new Dataized(this.parsed(xml).take("as-string")).asString(),
            Matchers.equalTo(xml)
        );
    }

    @Test
    void retrievesAttribute() {
        final Phi attribute = this.parsed("<foo bar=\"f\"/>").take("get-attribute");
        attribute.put("attr", new Data.ToPhi("bar"));
        MatcherAssert.assertThat(
            "Attribute value does not match with expected",
            new Dataized(attribute).asString(),
            Matchers.equalTo("f")
        );
    }

    @Test
    void retrievesAttributeInCompositeElement() {
        final Phi attribute = this.parsed(
            "<a x=\"top\"><b x=\"in\"></b></a>"
        ).take("get-attribute");
        attribute.put("attr", new Data.ToPhi("x"));
        MatcherAssert.assertThat(
            "Attribute value does not match with expected",
            new Dataized(attribute).asString(),
            Matchers.equalTo("top")
        );
    }

    @Test
    void retrievesTextContent() {
        MatcherAssert.assertThat(
            "Text content does not match with expected",
            new Dataized(this.parsed("<a>some</a>").take("text-content")).asString(),
            Matchers.equalTo("some")
        );
    }

    @Test
    void retrievesTextContentFromChildInCompositeElement() {
        MatcherAssert.assertThat(
            "Text content does not match with expected",
            new Dataized(
                this.parsed("<a><child>we are here!</child></a>").take("text-content")
            ).asString(),
            Matchers.equalTo("we are here!")
        );
    }

    @Test
    void retrievesEmptyText() {
        MatcherAssert.assertThat(
            "Text content was not empty, but it should",
            new Dataized(this.parsed("<root/>").take("text-content")).asString(),
            Matchers.emptyString()
        );
    }

    private Phi parsed(final String xml) {
        final Phi element = Phi.Φ.take("org.eolang.dom.element").copy();
        element.put("xml", new Data.ToPhi(xml));
        return element;
    }
}
