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
import org.eolang.Atom;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.PhVoid;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Elements by tag name in the given namespace.
 *
 * @since 0.0.0
 * @checkstyle TypeNameCheck (5 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
@XmirObject(oname = "doc.xml.get-elements-by-tag-name-ns")
public final class EOdoc$EOxml$EOget_elements_by_tag_name_ns extends PhDefault implements Atom {

    /**
     * Ctor.
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOdoc$EOxml$EOget_elements_by_tag_name_ns() {
        this.add("ns", new PhVoid("ns"));
        this.add("name", new PhVoid("name"));
    }

    @Override
    public Phi lambda() throws XmlParseException, TransformerException {
        return new NodesCollection(
            new XmlNode.Default(
                new Dataized(this.take(Phi.RHO).take("serialized")).asString()
            ).getElementsByTagNameNs(
                new Dataized(this.take("ns")).asString(),
                new Dataized(this.take("name")).asString()
            )
        ).value();
    }
}
