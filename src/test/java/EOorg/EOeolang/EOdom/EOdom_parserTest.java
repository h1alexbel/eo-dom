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
 * Tests for {@link EOdom_parser}.
 *
 * @since 0.0.0
 * @checkstyle TypeNameCheck (3 lines)
 */
final class EOdom_parserTest {

    @Test
    void parsersStringIntoDocument() {
        final Phi parse = this.parser("<books><book title=\"Object Thinking\"/></books>");
        final Phi doc = parse.take("as-string");
        MatcherAssert.assertThat(
            "Parsed document doesn't match with expected",
            new Dataized(doc).asString(),
            Matchers.equalTo(
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?><books><book title=\"Object Thinking\"/></books>"
            )
        );
    }

    @Test
    void throwsOnInvalidXml() {
        final Phi parser = this.parser("<broken xml here>");
        MatcherAssert.assertThat(
            "Parsed document doesn't match with expected",
            () -> new Dataized(parser.take("doc").take("as-string")).asString(),
            new Throws<>(
                Matchers.containsString("XML document syntax is invalid: '<broken xml here>'"),
                EOerror.ExError.class
            )
        );
    }

    private Phi parser(final String data) {
        final Phi parse = Phi.Φ.take("org.eolang.dom.dom-parser").copy()
            .take("parse-from-string");
        parse.put("data", new Data.ToPhi(data));
        return parse;
    }
}
