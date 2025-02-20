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
@SuppressWarnings("PMD.TooManyMethods")
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
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    void setsAttribute() {
        final Phi with = this.parsed("<foo/>").take("with-attribute");
        final String set = "f";
        final String value = "test";
        with.put("attr", new Data.ToPhi(set));
        with.put("value", new Data.ToPhi(value));
        final Phi attr = with.take("get-attribute");
        attr.put("attr", new Data.ToPhi(set));
        MatcherAssert.assertThat(
            "Attribute value does not match with expected",
            new Dataized(attr).asString(),
            Matchers.equalTo(value)
        );
    }

    @Test
    void createsElementWithAttributeAndText() {
        final Phi title = this.parsed("<book/>").take("with-attribute");
        title.put("attr", new Data.ToPhi("title"));
        title.put("value", new Data.ToPhi("Code Complete"));
        final Phi author = title.take("with-attribute");
        author.put("attr", new Data.ToPhi("author"));
        author.put("value", new Data.ToPhi("Steve McConnell"));
        final Phi text = author.take("with-text");
        text.put("content", new Data.ToPhi("A Practical Handbook of Software Construction"));
        MatcherAssert.assertThat(
            "Output element does not match with expected",
            new Dataized(text.take("as-string")).asString().replaceAll("><", ">\n<"),
            Matchers.equalTo(
                String.join(
                    "\n",
                    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>",
                    "<book author=\"Steve McConnell\" title=\"Code Complete\">A Practical Handbook of Software Construction</book>"
                )
            )
        );
    }

    @Test
    void setsAttributeToCompositeElement() {
        final Phi with = this.parsed("<foo><test><a/></test></foo>").take("with-attribute");
        with.put("attr", new Data.ToPhi("set"));
        with.put("value", new Data.ToPhi("v"));
        MatcherAssert.assertThat(
            "Result XML does not match with expected",
            new Dataized(with.take("as-string")).asString(),
            Matchers.equalTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><foo set=\"v\"><test><a/></test></foo>"
            )
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
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    void setsTextContent() {
        final String content = "bar";
        final Phi with = this.parsed("<foo/>").take("with-text");
        with.put("content", new Data.ToPhi(content));
        MatcherAssert.assertThat(
            "Attribute value does not match with expected",
            new Dataized(with.take("text-content")).asString(),
            Matchers.equalTo(content)
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

    @Test
    void retrievesChildNodes() {
        MatcherAssert.assertThat(
            "Child nodes count does not match with expected",
            new Dataized(
                this.parsed("<top><a title='app'/><f>foo</f></top>")
                    .take("child-nodes")
                    .take("length")
            ).asNumber().intValue(),
            Matchers.equalTo(2)
        );
    }

    @Test
    void retrievesSecondChildNode() {
        final Phi locate = this.parsed(
            "<top><foo title='f'>bar</foo><main title='app'>x</main></top>"
            )
            .take("child-nodes")
            .take("at");
        locate.put("pos", new Data.ToPhi(1));
        final Phi attr = locate.take("get-attribute");
        attr.put("attr", new Data.ToPhi("title"));
        MatcherAssert.assertThat(
            "Attribute value does not match with expected",
            new Dataized(attr).asString(),
            Matchers.equalTo("app")
        );
        MatcherAssert.assertThat(
            "Text inside the node does not match with expected",
            new Dataized(locate.take("text-content")).asString(),
            Matchers.equalTo("x")
        );
    }

    @Test
    void retrievesComplexChildNode() {
        final Phi locate = this.parsed(
            "<top><child><next n='2'><here title='we are at the bottom'/></next></child></top>"
            )
            .take("child-nodes")
            .take("at");
        locate.put("pos", new Data.ToPhi(0));
        MatcherAssert.assertThat(
            "Resulted child node does not match with expected",
            new Dataized(locate.take("as-string")).asString(),
            Matchers.equalTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><child><next n=\"2\"><here title=\"we are at the bottom\"/></next></child>"
            )
        );
    }

    @Test
    void retrievesZeroElementsInChildFreeNode() {
        MatcherAssert.assertThat(
            "Size should be zero, since there is no nodes inside",
            new Dataized(
                this.parsed("<defects/>")
                    .take("child-nodes")
                    .take("length")
            ).asNumber().intValue(),
            Matchers.equalTo(0)
        );
    }

    private Phi parsed(final String xml) {
        final Phi element = Phi.Φ.take("org.eolang.dom.element").copy();
        element.put("xml", new Data.ToPhi(xml));
        return element;
    }
}
