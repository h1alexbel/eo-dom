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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EOdoc}.
 *
 * @since 0.0.0
 */
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
        Assertions.assertThrows(
            EOerror.ExError.class,
            () -> new Dataized(this.document("broken").take("xml")).asString(),
            () -> "Error should be thrown, since XML is invalid"
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
            new Dataized(elem).asString(),
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
            new Dataized(elem).asString(),
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
            new Dataized(elem).asString(),
            Matchers.equalTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><test>here</test>"
            )
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

    /**
     * Returns attribute inside child node.
     * @todo #9:45min Enable this test on attribute fetching after child object
     *  cascading will be implemented. Currenlty, we can't take `attr` object from
     *  `elem` object. It should be possible to do this in EO:
     *  <pre>
     *  {@code
     *  ((doc "...").elem "f").attr "x"
     *  }
     *  </pre>
     */
    @Disabled
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

    /**
     * Returns text node inside child.
     * @todo #10:35min Enable this test on text retrieval from child node.
     *  We should enable this test after cascading in child objects will be implemented.
     */
    @Disabled
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

    private Phi document(final String xml) {
        final Phi doc = Phi.Φ.take("org.eolang.dom.doc").copy();
        doc.put("data", new Data.ToPhi(xml));
        return doc;
    }
}
