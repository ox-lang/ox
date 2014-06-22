# Oxlang Grammar

Clojure's grammar is at the "form" level, being a single top level s
expression. In contrast, Oxlang seeks to do whole file parses and
assumes that code reloading must occur at the whole module or whole
scope level rather than at the single form level. This is a
consequence of Oxlang's immutable and platform agnostic environment
implementation.

## Reader literals

### Symbols

### Keywords

### Numbers

### Lists

### Vectors

### Maps

### Sets

### Quote

### Characters

### Deref

### Metadata

### Dispatch

### Syntax-quote

## Parser grammar

FIXME
