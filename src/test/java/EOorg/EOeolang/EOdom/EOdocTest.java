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

import EOorg.EOeolang.EOerror;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Tests for {@link EOdoc}.
 *
 * @since 0.0.0
 */
@SuppressWarnings("PMD.TooManyMethods")
final class EOdocTest {

    @Test
    void createsDocument() {
        MatcherAssert.assertThat(
            "Document was not created, but it should",
            new Dataized(this.document("<program/>").take("serialized")).asString(),
            Matchers.equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?><program/>")
        );
    }

    @Test
    void throwsErrorIfInvalidXmlPassed() {
        MatcherAssert.assertThat(
            "Error was not thrown, though XML is invalid",
            () -> new Dataized(this.document("broken").take("serialized")).asString(),
            new Throws<>(
                Matchers.containsString("XML document syntax is invalid"),
                EOerror.ExError.class
            )
        );
    }

    @Test
    void retrievesElementsByTagName() {
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
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><book title=\"Object Thinking\"/>"
            )
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

    private Phi parsedDocument(final String data) {
        final Phi parse = Phi.Φ.take("org.eolang.dom.dom-parser").copy()
            .take("parse-from-string");
        parse.put("data", new Data.ToPhi(data));
        return parse;
    }

    private Phi document(final String xml) {
        final Phi doc = Phi.Φ.take("org.eolang.dom.doc").copy();
        doc.put("data", new Data.ToPhi(xml));
        return doc;
    }
}
