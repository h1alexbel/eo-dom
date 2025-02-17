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
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;

/**
 * Tests for {@link EOdoc}.
 *
 * @since 0.0.0
 */
@SuppressWarnings("PMD.TooManyMethods")
final class EOdocTest {

    /**
     * Attribute object name.
     */
    private static final String ATTR_NAME = "attr";

    /**
     * Element object name.
     */
    private static final String ELEM_NAME = "elem";

    /**
     * Text object name.
     */
    private static final String TEXT_NAME = "text";

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
    @SuppressWarnings("PMD.AvoidDuplicateLiterals")
    void findsElementInDocument() {
        final Phi elem = this.document("<program><test>here</test></program>").take(
            EOdocTest.ELEM_NAME
        );
        elem.put("ename", new Data.ToPhi("program"));
        MatcherAssert.assertThat(
            "Element result doesn't match with expected",
            new Dataized(elem.take("as-string")).asString(),
            Matchers.equalTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><program><test>here</test></program>"
            )
        );
    }

    @Test
    void findsElementInEmptyRoot() {
        final Phi elem = this.document("<program/>").take(
            EOdocTest.ELEM_NAME
        );
        elem.put("ename", new Data.ToPhi("program"));
        MatcherAssert.assertThat(
            "Element result doesn't match with expected",
            new Dataized(elem.take("as-string")).asString(),
            Matchers.equalTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><program/>"
            )
        );
    }

    @Test
    void findsChildElement() {
        final Phi elem = this.document("<program><test>here</test></program>").take(
            EOdocTest.ELEM_NAME
        );
        elem.put("ename", new Data.ToPhi("test"));
        MatcherAssert.assertThat(
            "Element result doesn't match with expected",
            new Dataized(elem.take("as-string")).asString(),
            Matchers.equalTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>here</test>"
            )
        );
    }

    @Test
    void findsChildElementCascading() {
        final Phi elem = this.document("<a><b><c>here</c></b></a>").take(
            EOdocTest.ELEM_NAME
        );
        elem.put("ename", new Data.ToPhi("a"));
        final Phi child = elem.take(EOdocTest.ELEM_NAME);
        child.put("ename", new Data.ToPhi("b"));
        MatcherAssert.assertThat(
            "Output does not match with expected",
            new Dataized(child.take("as-string")).asString(),
            Matchers.equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?><b><c>here</c></b>")
        );
    }

    @Test
    void returnsAttributeInsideNode() {
        final Phi attr = this.document("<program test=\"f\"/>").take(
            EOdocTest.ATTR_NAME
        );
        attr.put("aname", new Data.ToPhi("test"));
        MatcherAssert.assertThat(
            "Value does not match with expected",
            new Dataized(attr).asString(),
            Matchers.equalTo("f")
        );
    }

    @Test
    void returnsAttributeInsideChildNode() {
        final Phi elem = this.document("<foo><bar x=\"ttt\"></bar></foo>").take(
            EOdocTest.ELEM_NAME
        );
        elem.put("ename", new Data.ToPhi("bar"));
        final Phi attr = elem.take(EOdocTest.ATTR_NAME);
        attr.put("aname", new Data.ToPhi("x"));
        MatcherAssert.assertThat(
            "Value does not match with expected",
            new Dataized(attr).asString(),
            Matchers.equalTo("ttt")
        );
    }

    @Test
    void returnsTextInsideNode() {
        MatcherAssert.assertThat(
            "Text does not match with expected",
            new Dataized(this.document("<foo>here</foo>").take(EOdocTest.TEXT_NAME)).asString(),
            Matchers.equalTo("here")
        );
    }

    @Test
    void returnsTextInsideChildNode() {
        final Phi child = this.document("<abc><c>x</c></abc>").take(
            EOdocTest.ELEM_NAME
        );
        child.put("ename", new Data.ToPhi("c"));
        MatcherAssert.assertThat(
            "Text does not match with expected",
            new Dataized(child.take(EOdocTest.TEXT_NAME)).asString(),
            Matchers.equalTo("x")
        );
    }

    @Test
    void throwsErrorIfAttributeIsNotFound() {
        final Phi attr = this.document("<foo/>").take(EOdocTest.ATTR_NAME);
        attr.put("aname", new Data.ToPhi("x"));
        MatcherAssert.assertThat(
            "Text does not match with expected",
            () -> new Dataized(attr).asString(),
            new Throws<>(
                Matchers.containsString("Attribute 'x' was not found in the node"),
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
        final Phi at = retrieval.take("at");
        at.put("pos", new Data.ToPhi(0));
        MatcherAssert.assertThat(
            "Retrieved element does not match with expected",
            new Dataized(at.take("as-string")).asString(),
            Matchers.equalTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><book title=\"Object Thinking\"/>"
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
