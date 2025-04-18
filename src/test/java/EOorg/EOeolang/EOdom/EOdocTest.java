/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import EOorg.EOeolang.EOerror;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.llorllale.cactoos.matchers.Throws;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

/**
 * Tests for {@link EOdoc}.
 *
 * @since 0.0.0
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.AvoidDuplicateLiterals"})
final class EOdocTest {

    @Test
    void createsAnElement() {
        final Phi doc = this.parsedDocument("<x/>");
        final Phi create = doc.take("create-element");
        create.put("lname", new Data.ToPhi("y"));
        MatcherAssert.assertThat(
            "Element XML does not match with expected",
            new Dataized(create.take("as-string")).asString(),
            Matchers.equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<y/>\n")
        );
    }

    @ParameterizedTest
    @ValueSource(
        strings = {
            "?", ">", "<", "$", "!", "@", "&", "*", "(", ")", "#", "%", "^", "+", "~", "'", "[",
            "]"
        }
    )
    void throwsOnInvalidCharacterInCreateElement(final String lname) {
        final Phi doc = this.parsedDocument("<f/>");
        final Phi create = doc.take("create-element");
        create.put("lname", new Data.ToPhi(lname));
        MatcherAssert.assertThat(
            String.format(
                "Exception is not thrown, though element name: '%s' is invalid",
                lname
            ),
            () -> new Dataized(create.take("as-string")).asString(),
            new Throws<>(
                Matchers.containsString("An invalid or illegal XML character is specified"),
                EOerror.ExError.class
            )
        );
    }

    @Test
    void appendsElementToExistingDocument() throws ImpossibleModificationException {
        final Phi root = this.parsedDocument("<foo/>");
        final Phi create = root.take("create-element");
        create.put("lname", new Data.ToPhi("bar"));
        final Phi append = root.take("append-child");
        append.put("child", create);
        MatcherAssert.assertThat(
            "Resulted document does not match with expected",
            new Dataized(append.take("as-string")).asString(),
            Matchers.equalTo(
                new Xembler(new Directives().add("foo").add("bar")).xml()
            )
        );
    }

    @Test
    void appendsElementWithAttributeAndTextContent() throws ImpossibleModificationException {
        final Phi root = this.parsedDocument("<fizz/>");
        final Phi create = root.take("create-element");
        create.put("lname", new Data.ToPhi("buzz"));
        final Phi wattr = create.take("with-attribute");
        wattr.put("attr", new Data.ToPhi("f"));
        wattr.put("value", new Data.ToPhi("val"));
        final Phi wtext = wattr.take("with-text");
        wtext.put("content", new Data.ToPhi("boom!"));
        final Phi append = root.take("append-child");
        append.put("child", wtext);
        MatcherAssert.assertThat(
            "Resulted document does not match with expected",
            new Dataized(append.take("as-string")).asString(),
            Matchers.equalTo(
                new Xembler(
                    new Directives().add("fizz").add("buzz").attr("f", "val").set("boom!")
                ).xml()
            )
        );
    }

    @Test
    void throwsErrorIfInvalidXmlPassed() {
        MatcherAssert.assertThat(
            "Error was not thrown, though XML is invalid",
            () -> new Dataized(this.parsedDocument("broken").take("serialized")).asString(),
            new Throws<>(
                Matchers.containsString("XML document syntax is invalid"),
                EOerror.ExError.class
            )
        );
    }

    @Test
    void retrievesElementsByTagName() throws ImpossibleModificationException {
        final Phi doc = this.parsedDocument(
            "<books><book title=\"Object Thinking\"/><book title=\"Elegant Objects Vol 1.\"/></books>"
        );
        final Phi retrieval = doc.take("get-elements-by-tag-name");
        retrieval.put("name", new Data.ToPhi("book"));
        final Phi locate = retrieval.take("at");
        locate.put("pos", new Data.ToPhi(0));
        MatcherAssert.assertThat(
            "Retrieved element does not match with expected",
            new Dataized(locate.take("as-string")).asString(),
            Matchers.equalTo(
                new Xembler(new Directives().add("book").attr("title", "Object Thinking")).xml()
            )
        );
    }

    @Test
    void retrievesElementsByTagNameInNs() {
        final Phi doc = this.parsedDocument(
            String.join(
                "\n",
                "<app xmlns:e=\"https://example.com/e\" xmlns:t=\"https://example.com/t\">",
                "<e:foo>Boom e</e:foo>",
                "<t:foo>Boom t</t:foo>",
                "</app>"
            )
        );
        final Phi retrieval = doc.take("get-elements-by-tag-name-ns");
        retrieval.put("ns", new Data.ToPhi("https://example.com/e"));
        retrieval.put("name", new Data.ToPhi("foo"));
        final Phi locate = retrieval.take("at");
        locate.put("pos", new Data.ToPhi(0));
        MatcherAssert.assertThat(
            "Text content does not match with expected",
            new Dataized(locate.take("text-content")).asString(),
            Matchers.equalTo("Boom e")
        );
    }

    @Test
    void retrievesElementsWithWildcardNs() {
        final Phi doc = this.parsedDocument(
            String.join(
                "\n",
                "<main xmlns:foo=\"https://foo.com\" xmlns:bar=\"https://bar.com\">",
                "<foo:o>foo o</foo:o>",
                "<bar:o>bar o</bar:o>",
                "<x>just x</x>",
                "</main>"
            )
        );
        final Phi retrieval = doc.take("get-elements-by-tag-name-ns");
        retrieval.put("ns", new Data.ToPhi("*"));
        retrieval.put("name", new Data.ToPhi("o"));
        MatcherAssert.assertThat(
            "Nodes count does not match with expected",
            new Dataized(retrieval.take("length")).asNumber().intValue(),
            Matchers.equalTo(2)
        );
    }

    @Test
    void retrievesAllElementsInGivenNamespace() {
        final Phi doc = this.parsedDocument(
            String.join(
                "\n",
                "<main xmlns:one=\"https://one.com\" xmlns:two=\"https://two.com\">",
                "<one:foo>x</one:foo>",
                "<one:bar>y</one:bar>",
                "<one:xyz>z</one:xyz>",
                "</main>"
            )
        );
        final Phi retrieval = doc.take("get-elements-by-tag-name-ns");
        retrieval.put("ns", new Data.ToPhi("https://one.com"));
        retrieval.put("name", new Data.ToPhi("*"));
        MatcherAssert.assertThat(
            "Nodes count does not match with expected",
            new Dataized(retrieval.take("length")).asNumber().intValue(),
            Matchers.equalTo(3)
        );
    }

    @Test
    void retrievesWildcardElementsInWildcardNs() {
        final Phi doc = this.parsedDocument(
            String.join(
                "\n",
                "<main xmlns:one=\"https://one.com\" xmlns:two=\"https://two.com\">",
                "<one:foo>x</one:foo>",
                "<one:bar>y</one:bar>",
                "<one:xyz>z</one:xyz>",
                "<two:t>z</two:t>",
                "<two:f>z</two:f>",
                "</main>"
            )
        );
        final Phi retrieval = doc.take("get-elements-by-tag-name-ns");
        retrieval.put("ns", new Data.ToPhi("*"));
        retrieval.put("name", new Data.ToPhi("*"));
        MatcherAssert.assertThat(
            "Nodes count does not match with expected",
            new Dataized(retrieval.take("length")).asNumber().intValue(),
            Matchers.equalTo(6)
        );
    }

    @Test
    void findsElementsWithIdTogetherWithChildElements() {
        final Phi bid = this.parsedDocument(
            String.join(
                "\n",
                "<app>",
                "<x id='wanted'><child>child is here</child></x>",
                "<x id='x'/>",
                "</app>"
            )
        ).take("get-element-by-id");
        bid.put("identifier", new Data.ToPhi("wanted"));
        MatcherAssert.assertThat(
            "Found element does not match with expected",
            new Dataized(bid.take("text-content")).asString(),
            Matchers.equalTo("child is here")
        );
    }

    /**
     * Finds element with identifier, supplied in DTD.
     *
     * @todo #48:60min Enable this test after `getElementById()` will take an account ID
     *  specified in DTD. Currenlty, we use `org.jsoup` library that checks `id` attribute
     *  in HTML documents by default. Let's make it possible to configure identifiers from
     *  their definitions, supplied in DTD schema. Check
     *  <a href="https://stackoverflow.com/questions/3423430/java-xml-dom-how-are-id-attributes-special">this</a>
     *  for more information about document identifiers.
     */
    @Disabled
    @Test
    void findsElementWithIdentifierWithSuppliedDtd() {
        final Phi bid = this.parsedDocument(
            String.join(
                "\n",
                "<?xml version='1.0'?>",
                "<!DOCTYPE foo [",
                "<!ELEMENT foo (bar+)>",
                "<!ELEMENT bar EMPTY>",
                "<!ATTLIST bar supplied ID #REQUIRED>",
                "]>",
                "<foo>",
                "<bar supplied='bar123'/>",
                "<bar supplied='bar456'/>",
                "</foo>"
            )
        ).take("get-element-by-id");
        bid.put("identifier", new Data.ToPhi("bar123"));
        MatcherAssert.assertThat(
            "Found element does not match with expected",
            new Dataized(bid.take("as-string")).asString(),
            Matchers.equalTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><bar supplied=\"bar123\"/>"
            )
        );
    }

    @Test
    void retrievesParentNode() {
        final Phi get = this.parsedDocument(
            "<trip oneway=\"maybe\"><station id='MOW'>Moscow</station><station id='PVG'>Shanghai</station></trip>"
        ).take("get-element-by-id");
        get.put("identifier", new Data.ToPhi("MOW"));
        final Phi attr = get.take("parent-node").take("get-attribute");
        attr.put("attr", new Data.ToPhi("oneway"));
        MatcherAssert.assertThat(
            "Attribute of parent node was not retrieved",
            new Dataized(attr).asString(),
            Matchers.equalTo("maybe")
        );
    }

    @Test
    void evaluatesXpath() {
        final Phi evaluate = this.parsedDocument(
            "<books><book id='1' title='EO vol.3'/><book id='2' title='How to Write a Better Thesis'/></books>"
        ).take("evaluate");
        evaluate.put("xpath", new Data.ToPhi("//book[@id='2']/@title"));
        evaluate.put("return", new Data.ToPhi("string"));
        final String result = new Dataized(
            evaluate
        ).asString();
        final String expected = "How to Write a Better Thesis";
        MatcherAssert.assertThat(
            String.format(
                "Result of XPath evaluation: '%s' does not match with expected: '%s'",
                result, expected
            ),
            result,
            Matchers.equalTo(expected)
        );
    }

    @Test
    void evaluatesXpathAsNode() throws ImpossibleModificationException {
        final Phi evaluate = this.parsedDocument(
            "<blogs><blog id='1'>yegor256.com</blog><blog id='2'>l3r8y.ru</blog><blog id='3'>h1alexbel.xyz</blog></blogs>"
        ).take("evaluate");
        evaluate.put("xpath", new Data.ToPhi("//blog[@id='2']"));
        evaluate.put("return", new Data.ToPhi("node"));
        MatcherAssert.assertThat(
            "Resulted node does not match with expected",
            new Dataized(evaluate.take("as-string")).asString(),
            Matchers.equalTo(
                new Xembler(new Directives().add("blog").attr("id", "2").set("l3r8y.ru")).xml()
            )
        );
    }

    @Test
    void evaluatesXpathAsNodeSet() {
        final Phi evaluate = this.parsedDocument(
            "<objects><o base='f'/><o base='f'/><o base='a'/></objects>"
        ).take("evaluate");
        evaluate.put("xpath", new Data.ToPhi("//o[@base='f']"));
        evaluate.put("return", new Data.ToPhi("nodeset"));
        MatcherAssert.assertThat(
            "Resulted node does not match with expected",
            new Dataized(evaluate.take("length")).asNumber().intValue(),
            Matchers.equalTo(2)
        );
    }

    @Test
    void evaluatesXpathAsNumber() {
        final Phi evaluate = this.parsedDocument(
            "<books><book id='2' author='Justin Zobel'/><book id='1' author='David West'/></books>"
        ).take("evaluate");
        evaluate.put("xpath", new Data.ToPhi("//book[@author='David West']/@id"));
        evaluate.put("return", new Data.ToPhi("number"));
        MatcherAssert.assertThat(
            "Retrieved number does not match with expected",
            new Dataized(evaluate).asNumber().intValue(),
            Matchers.equalTo(1)
        );
    }

    @Test
    void evaluatesXpathAsBoolean() {
        final Phi evaluate = this.parsedDocument(
            "<objects><o name='Q.org.eolang.f'/></objects>"
        ).take("evaluate");
        evaluate.put("xpath", new Data.ToPhi("//o[1][starts-with(@name, 'Q.org.eolang')]"));
        evaluate.put("return", new Data.ToPhi("boolean"));
        MatcherAssert.assertThat(
            "Retrieved boolean value does not match with expected",
            new Dataized(evaluate).asBool(),
            Matchers.equalTo(true)
        );
    }

    private Phi parsedDocument(final String data) {
        final Phi parse = Phi.Φ.take("org.eolang.dom.dom-parser").copy()
            .take("parse-from-string");
        parse.put("data", new Data.ToPhi(data));
        return parse;
    }
}
