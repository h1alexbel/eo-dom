# EO objects for DOM manipulation

<img alt="logo" src="https://www.objectionary.com/cactus.svg" height="100px" />

[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/h1alexbel/eo-dom)](http://www.rultor.com/p/h1alexbel/eo-dom)
[![We recommend IntelliJ IDEA](https://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[EO] objects for manipulations with [DOM].

**Motivation**. [EO], an experimental programming language lacks objects for
XML manipulation. `eo-dom` offers standard [objects](#objects) for building,
traversing XML documents using [DOM] and [XPath].

## Objects

Here is how it works:

```eo
+package org.eolang.dom

[] > creates-document-from-string
  (doc "<document/>").as-string > @ # <document/>

[] > creates-document-from-file
  (doc "data.xml").as-string > @ # <root><data>test</data></root>

[] > locates-element
  (doc "data.xml").xpath "//data/text()" # test

[] > appends-element
  ((doc "data.xml").xpath "/root").append "kid" # <root><kid/><data>test</data></root> 
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