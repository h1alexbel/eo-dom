# EO objects for DOM manipulation

<img alt="logo" src="https://www.objectionary.com/cactus.svg" height="100px" />

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/h1alexbel/eo-dom)](http://www.rultor.com/p/h1alexbel/eo-dom)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![mvn](https://github.com/h1alexbel/eo-dom/actions/workflows/mvn.yml/badge.svg)](https://github.com/h1alexbel/eo-dom/actions/workflows/mvn.yml)
[![PDD status](http://www.0pdd.com/svg?name=h1alexbel/eo-dom)](http://www.0pdd.com/p?name=h1alexbel/eo-dom)
[![Hits-of-Code](https://hitsofcode.com/github/h1alexbel/eo-dom)](https://hitsofcode.com/view/github/h1alexbel/eo-dom)
[![codecov](https://codecov.io/gh/h1alexbel/eo-dom/graph/badge.svg?token=fgmeoDDwlT)](https://codecov.io/gh/h1alexbel/eo-dom)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/h1alexbel/eo-dom/blob/master/LICENSE.txt)

[EO] objects for manipulations with [DOM].

**Motivation**. [EO], an experimental programming language lacks objects for
XML manipulation. `eo-dom` offers standard [objects](#objects) for building,
traversing XML documents using [DOM] and [XPath].

## Objects

Here is how it works:

```eo
+alias org.eolang.dom.doc
+alias org.eolang.dom.dom-parser

# Test that checks eo-dom functionality.
[] > finds-element-at-index
  dom-parser.parse-from-string > doc
    "<books><book title='Object Thinking'/><book title='Elegant Objects Vol 1.'/></books>"
  doc.get-elements-by-tag-name "book" > books
  books.at 0 > first
  first.get-attribute "title" > title
  eq. > @
    title
    "Object Thinking"
```

## How to contribute?

Make sure that you have Java 11+ installed on your system, then fork this
repository, make changes, send us a [pull request][guidelines]. We will
review your changes and apply them to the `master` branch shortly, provided
they don't violate our quality standards. To avoid frustration, before sending
us your pull request please run full build:

```bash
mvn clean install -Pqulice
```

[EO]: https://www.eolang.org
[DOM]: https://en.wikipedia.org/wiki/Document_Object_Model
[XPath]: https://en.wikipedia.org/wiki/XPath
[guidelines]: https://www.yegor256.com/2014/04/15/github-guidelines.html
