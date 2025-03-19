/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

/**
 * Tests for {@link EOelement}.
 *
 * @since 0.0.0
 */
@SuppressWarnings("PMD.TooManyMethods")
final class EOelementTest {

    @Test
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    void printsElement() throws ImpossibleModificationException {
        final String xml = "<abc><c>x</c></abc>";
        MatcherAssert.assertThat(
            "Element does not match with expected",
            new Dataized(this.parsed(xml).take("as-string")).asString(),
            Matchers.equalTo(
                new Xembler(new Directives().add("abc").add("c").set("x")).xml()
            )
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
    void createsElementWithAttributeAndText() throws ImpossibleModificationException {
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
                new Xembler(
                    new Directives()
                        .add("book")
                        .attr("author", "Steve McConnell")
                        .attr("title", "Code Complete")
                        .set("A Practical Handbook of Software Construction")
                ).xml()
            )
        );
    }

    @Test
    void setsAttributeToCompositeElement() throws ImpossibleModificationException {
        final Phi with = this.parsed("<foo><test><a/></test></foo>").take("with-attribute");
        with.put("attr", new Data.ToPhi("set"));
        with.put("value", new Data.ToPhi("v"));
        MatcherAssert.assertThat(
            "Result XML does not match with expected",
            new Dataized(with.take("as-string")).asString(),
            Matchers.equalTo(
                new Xembler(
                    new Directives()
                        .add("foo")
                        .attr("set", "v")
                        .add("test")
                        .add("a")
                ).xml()
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
    void retrievesComplexChildNode() throws ImpossibleModificationException {
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
                new Xembler(
                    new Directives()
                        .add("child")
                        .add("next")
                        .attr("n", "2")
                        .add("here")
                        .attr("title", "we are at the bottom")
                ).xml()
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

    @Test
    void retrievesFirstChild() {
        MatcherAssert.assertThat(
            "First child does not match with expected",
            new Dataized(
                this.parsed(
                    "<top><f>first child is here</f><f>here is the second one</f></top>"
                ).take("first-child").take("text-content")
            ).asString(),
            Matchers.equalTo(
                "first child is here"
            )
        );
    }

    @ParameterizedTest
    @ValueSource(
        strings = {
            "first-child",
            "last-child"
        }
    )
    void returnsXmlHeadIfThereIsNoChildren(final String method)
        throws ImpossibleModificationException {
        MatcherAssert.assertThat(
            "Output does not match with expected",
            new Dataized(
                this.parsed("<empty/>").take(method).take("as-string")
            ).asString(),
            Matchers.equalTo(
                new Xembler(new Directives()).xml()
            )
        );
    }

    @Test
    void retrievesLastChild() {
        MatcherAssert.assertThat(
            "Text node of last child does not match with expected",
            new Dataized(
                this.parsed(
                    "<b><f>first child is here</f><f>here is the last one</f><f>and the last one</f></b>"
                ).take("last-child").take("text-content")
            ).asString(),
            Matchers.equalTo("and the last one")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"first-child", "last-child"})
    void retrievesChildrenRecursively(final String direction)
        throws ImpossibleModificationException {
        MatcherAssert.assertThat(
            "First child does not match with expected",
            new Dataized(
                this.parsed("<parent><a><b><c>finally we are here</c></b></a></parent>")
                    .take(direction)
                    .take(direction)
                    .take(direction)
                    .take("as-string")
            ).asString(),
            Matchers.equalTo(
                new Xembler(new Directives().add("c").set("finally we are here")).xml()
            )
        );
    }

    @Test
    void retrievesParentNode() throws ImpossibleModificationException {
        MatcherAssert.assertThat(
            "Retrieved parent node does not match with expected",
            new Dataized(
                this.parsed("<std><middle><child>test</child></middle></std>")
                    .take("first-child").take("parent-node").take("as-string")
            ).asString(),
            Matchers.equalTo(
                new Xembler(
                    new Directives().add("std").add("middle").add("child").set("test")
                ).xml()
            )
        );
    }

    @Test
    void goesBackByRetrievingParentNode() throws ImpossibleModificationException {
        MatcherAssert.assertThat(
            "Retrieved parent node does not match with expected",
            new Dataized(
                this.parsed("<top><inner><bottom>here is the bottom</bottom></inner></top>")
                    .take("first-child").take("first-child").take("parent-node").take("as-string")
            ).asString(),
            Matchers.equalTo(
                new Xembler(new Directives().add("inner").add("bottom").set("here is the bottom"))
                    .xml()
            )
        );
    }

    private Phi parsed(final String xml) {
        final Phi element = Phi.Φ.take("org.eolang.dom.element").take(Attr.PHI).copy();
        element.put("xml", new Data.ToPhi(xml));
        element.put("parent", new Data.ToPhi(xml));
        return element;
    }
}
