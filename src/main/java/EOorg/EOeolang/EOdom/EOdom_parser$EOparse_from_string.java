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

import org.eolang.AtVoid;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.ExFailure;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Parse source string into DOM document.
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "dom-parser.parse-from-string")
public final class EOdom_parser$EOparse_from_string extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOdom_parser$EOparse_from_string() {
        this.add("data", new AtVoid("data"));
    }

    @Override
    public Phi lambda() {
        final Phi data = this.take("data");
        final Phi xml = Phi.Φ.take("org.eolang.dom.doc").copy().take(Attr.PHI);
        try {
            xml.put(
                "data",
                new Data.ToPhi(
                    new XmlNode.Default(new Dataized(data).asString()).asString().getBytes()
                )
            );
        } catch (final XmlParseException exception) {
            throw new ExFailure(
                String.format(
                    "XML document syntax is invalid: '%s'", new Dataized(data).asString()
                ),
                exception
            );
        }
        return xml;
    }
}
