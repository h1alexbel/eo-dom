/*
 * SPDX-FileCopyrightText: Copyright (c) 2016-2025 Objectionary.com
 * SPDX-License-Identifier: MIT
 */
/*
 * @checkstyle PackageNameCheck (4 lines)
 * @checkstyle TrailingCommentCheck (3 lines)
 */
package EOorg.EOeolang.EOdom; // NOPMD

import javax.xml.transform.TransformerException;
import org.eolang.AtVoid;
import org.eolang.Atom;
import org.eolang.Attr;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Get DOM elements by tag name.
 * @since 0.0.0
 * @todo #8:45min Return proper error, if element not found with tag name.
 *  We should return proper error, if element is not found. Currently, the
 *  default EO error message will be thrown. Don't forget to add unit test.
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "doc.xml.get-elements-by-tag-name")
public final class EOdoc$EOxml$EOget_elements_by_tag_name extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOdoc$EOxml$EOget_elements_by_tag_name() {
        this.add("name", new AtVoid("name"));
    }

    @Override
    public Phi lambda() throws XmlParseException, TransformerException {
        return new NodesCollection(
            new XmlNode.Default(
                new Dataized(this.take(Attr.RHO).take("serialized")).asString()
            ).getElementsByTagName(new Dataized(this.take("name")).asString())
        ).value();
    }
}
