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
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link EOdoc}.
 *
 * @since 0.0.0
 */
final class EOdocTest {

    @Test
    void createsDocument() {
        final Phi doc = Phi.Φ.take("org.eolang.dom.doc").copy();
        doc.put("data", new Data.ToPhi("<program/>"));
        MatcherAssert.assertThat(
            "Document was not created, but it should",
            new Dataized(doc.take("serialized")).asString(),
            Matchers.equalTo("<?xml version=\"1.0\" encoding=\"UTF-8\"?><program/>")
        );
    }

    @Test
    void throwsErrorIfInvalidXmlPassed() {
        final Phi doc = Phi.Φ.take("org.eolang.dom.doc").copy();
        doc.put("data", new Data.ToPhi("broken"));
        Assertions.assertThrows(
            EOerror.ExError.class,
            () -> new Dataized(doc.take("xml")).asString(),
            () -> "Error should be thrown, since XML is invalid"
        );
    }

    @Test
    void findsElementInDocument() {
        final Phi doc = Phi.Φ.take("org.eolang.dom.doc").copy();
        doc.put("data", new Data.ToPhi("<program><test>here</test></program>"));
        final Phi elem = doc.take("elem");
        elem.put("ename", new Data.ToPhi("program"));
        MatcherAssert.assertThat(
            "Element result doesn't match with expected",
            new Dataized(elem).asString(),
            Matchers.equalTo("<program>\n   <test>here</test>\n</program>\n")
        );
    }

    @Test
    void findsElementInEmptyRoot() {
        final Phi doc = Phi.Φ.take("org.eolang.dom.doc").copy();
        doc.put("data", new Data.ToPhi("<program/>"));
        final Phi elem = doc.take("elem");
        elem.put("ename", new Data.ToPhi("program"));
        MatcherAssert.assertThat(
            "Element result doesn't match with expected",
            new Dataized(elem).asString(),
            Matchers.equalTo("<program/>\n")
        );
    }
}
