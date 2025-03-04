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
import org.junit.jupiter.api.Test;
import org.llorllale.cactoos.matchers.Throws;
import org.xembly.Directives;
import org.xembly.ImpossibleModificationException;
import org.xembly.Xembler;

/**
 * Tests for {@link EOdom_parser}.
 *
 * @since 0.0.0
 * @checkstyle TypeNameCheck (3 lines)
 */
final class EOdom_parserTest {

    @Test
    void parsersStringIntoDocument() throws ImpossibleModificationException {
        final Phi parse = this.parser("<books><book title=\"Object Thinking\"/></books>");
        final Phi doc = parse.take("as-string");
        MatcherAssert.assertThat(
            "Parsed document doesn't match with expected",
            new Dataized(doc).asString(),
            Matchers.equalTo(
                new Xembler(
                    new Directives().add("books").add("book").attr("title", "Object Thinking")
                ).xml()
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
