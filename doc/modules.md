# clojure.core

## Macros

### clojure.core/when-first

1.  Arities

    [bindings & body]

2.  Docs

    bindings => x xs
    
    Roughly the same as (when (seq xs) (let [x (first xs)] body)) but xs is evaluated only once

### clojure.core/cond->>

1.  Arities

    [expr & clauses]

2.  Docs

    Takes an expression and a set of test/form pairs. Threads expr (via ->>)
      through each form for which the corresponding test expression
      is true.  Note that, unlike cond branching, cond->> threading does not short circuit
      after the first true test expression.

### clojure.core/gen-class

1.  Arities

    [& options]

2.  Docs

    When compiling, generates compiled bytecode for a class with the
      given package-qualified :name (which, as all names in these
      parameters, can be a string or symbol), and writes the .class file
      to the **compile-path** directory.  When not compiling, does
      nothing. The gen-class construct contains no implementation, as the
      implementation will be dynamically sought by the generated class in
      functions in an implementing Clojure namespace. Given a generated
      class org.mydomain.MyClass with a method named mymethod, gen-class
      will generate an implementation that looks for a function named by 
      (str prefix mymethod) (default prefix: "-") in a
      Clojure namespace specified by :impl-ns
      (defaults to the current namespace). All inherited methods,
      generated methods, and init and main functions (see :methods, :init,
      and :main below) will be found similarly prefixed. By default, the
      static initializer for the generated class will attempt to load the
      Clojure support code for the class as a resource from the classpath,
      e.g. in the example case, \`\`org/mydomain/MyClass\_<sub>init</sub>.class\`\`. This
      behavior can be controlled by :load-impl-ns
    
    Note that methods with a maximum of 18 parameters are supported.
    
    In all subsequent sections taking types, the primitive types can be
    referred to by their Java names (int, float etc), and classes in the
    java.lang package can be used without a package qualifier. All other
    classes must be fully qualified.
    
    Options should be a set of key/value pairs, all except for :name are optional:
    
    :name aname
    
    The package-qualified name of the class to be generated
    
    :extends aclass
    
    Specifies the superclass, the non-private methods of which will be
    overridden by the class. If not provided, defaults to Object.
    
    :implements [interface &#x2026;]
    
    One or more interfaces, the methods of which will be implemented by the class.
    
    :init name
    
    If supplied, names a function that will be called with the arguments
    to the constructor. Must return [ [superclass-constructor-args] state] 
    If not supplied, the constructor args are passed directly to
    the superclass constructor and the state will be nil
    
    :constructors {[param-types] [super-param-types], &#x2026;}
    
    By default, constructors are created for the generated class which
    match the signature(s) of the constructors for the superclass. This
    parameter may be used to explicitly specify constructors, each entry
    providing a mapping from a constructor signature to a superclass
    constructor signature. When you supply this, you must supply an :init
    specifier. 
    
    :post-init name
    
    If supplied, names a function that will be called with the object as
    the first argument, followed by the arguments to the constructor.
    It will be called every time an object of this class is created,
    immediately after all the inherited constructors have completed.
    It's return value is ignored.
    
    :methods [ [name [param-types] return-type], &#x2026;]
    
    The generated class automatically defines all of the non-private
    methods of its superclasses/interfaces. This parameter can be used
    to specify the signatures of additional methods of the generated
    class. Static methods can be specified with ^{:static true} in the
    signature's metadata. Do not repeat superclass/interface signatures
    here.
    
    :main boolean
    
    If supplied and true, a static public main function will be generated. It will
    pass each string of the String[] argument as a separate argument to
    a function called (str prefix main).
    
    :factory name
    
    If supplied, a (set of) public static factory function(s) will be
    created with the given name, and the same signature(s) as the
    constructor(s).
    
    :state name
    
    If supplied, a public final instance field with the given name will be
    created. You must supply an :init function in order to provide a
    value for the state. Note that, though final, the state can be a ref
    or agent, supporting the creation of Java objects with transactional
    or asynchronous mutation semantics.
    
    :exposes {protected-field-name {:get name :set name}, &#x2026;}
    
    Since the implementations of the methods of the generated class
    occur in Clojure functions, they have no access to the inherited
    protected fields of the superclass. This parameter can be used to
    generate public getter/setter methods exposing the protected field(s)
    for use in the implementation.
    
    :exposes-methods {super-method-name exposed-name, &#x2026;}
    
    It is sometimes necessary to call the superclass' implementation of an
    overridden method.  Those methods may be exposed and referred in 
    the new method implementation by a local name.
    
    :prefix string
    
    Default: "-" Methods called e.g. Foo will be looked up in vars called
    prefixFoo in the implementing ns.
    
    :impl-ns name
    
    Default: the name of the current ns. Implementations of methods will be 
    looked up in this namespace.
    
    :load-impl-ns boolean
    
    Default: true. Causes the static initializer for the generated class
    to reference the load code for the implementing namespace. Should be
    true when implementing-ns is the default, false if you intend to
    load the code via some other method.

### clojure.core/while

1.  Arities

    [test & body]

2.  Docs

    Repeatedly executes body while test expression is true. Presumes
      some side-effect will cause test to become false/nil. Returns nil

### clojure.core/import

1.  Arities

    [& import-symbols-or-lists]

2.  Docs

    import-list => (package-symbol class-name-symbols\*)
    
    For each name in class-name-symbols, adds a mapping from name to the
    class named by package.name to the current namespace. Use :import in the ns
    macro in preference to calling this directly.

### clojure.core/pvalues

1.  Arities

    [& exprs]

2.  Docs

    Returns a lazy sequence of the values of the exprs, which are
      evaluated in parallel

### clojure.core/bound-fn

1.  Arities

    [& fntail]

2.  Docs

    Returns a function defined by the given fntail, which will install the
      same bindings in effect as in the thread at the time bound-fn was called.
      This may be used to define a helper function which runs on a different
      thread, but needs the same bindings in place.

### clojure.core/dosync

1.  Arities

    [& exprs]

2.  Docs

    Runs the exprs (in an implicit do) in a transaction that encompasses
      exprs and any nested calls.  Starts a transaction if none is already
      running on this thread. Any uncaught exception will abort the
      transaction and flow out of dosync. The exprs may be run more than
      once, but any effects on Refs will be atomic.

### clojure.core/with-loading-context

1.  Arities

    [& body]

2.  Docs

    nil

### clojure.core/..

1.  Arities

    [x form]
    [x form & more]

2.  Docs

    form => fieldName-symbol or (instanceMethodName-symbol args\*)
    
    Expands into a member access (.) of the first member on the first
    argument, followed by the next member on the result, etc. For
    instance:
    
    (.. System (getProperties) (get "os.name"))
    
    expands to:
    
    (. (. System (getProperties)) (get "os.name"))
    
    but is easier to write, read, and understand.

### clojure.core/delay

1.  Arities

    [& body]

2.  Docs

    Takes a body of expressions and yields a Delay object that will
      invoke the body only the first time it is forced (with force or deref/@), and
      will cache the result and return it on all subsequent force
      calls. See also - realized?

### clojure.core/gen-interface

1.  Arities

    [& options]

2.  Docs

    When compiling, generates compiled bytecode for an interface with
      the given package-qualified :name (which, as all names in these
      parameters, can be a string or symbol), and writes the .class file
      to the **compile-path** directory.  When not compiling, does nothing.
    
    In all subsequent sections taking types, the primitive types can be
    referred to by their Java names (int, float etc), and classes in the
    java.lang package can be used without a package qualifier. All other
    classes must be fully qualified.
    
    Options should be a set of key/value pairs, all except for :name are
    optional:
    
    :name aname
    
    The package-qualified name of the class to be generated
    
    :extends [interface &#x2026;]
    
    One or more interfaces, which will be extended by this interface.
    
    :methods [ [name [param-types] return-type], &#x2026;]
    
    This parameter is used to specify the signatures of the methods of
    the generated interface.  Do not repeat superinterface signatures
    here.

### clojure.core/with-bindings

1.  Arities

    [binding-map & body]

2.  Docs

    Takes a map of Var/value pairs. Installs for the given Vars the associated
      values as thread-local bindings. The executes body. Pops the installed
      bindings after body was evaluated. Returns the value of body.

### clojure.core/if-not

1.  Arities

    [test then]
    [test then else]

2.  Docs

    Evaluates test. If logical false, evaluates and returns then expr, 
      otherwise else expr, if supplied, else nil.

### clojure.core/doseq

1.  Arities

    [seq-exprs & body]

2.  Docs

    Repeatedly executes body (presumably for side-effects) with
      bindings and filtering as provided by "for".  Does not retain
      the head of the sequence. Returns nil.

### clojure.core/deftype

1.  Arities

    [name [& fields] & opts+specs]

2.  Docs

    (deftype name [fields\*]  options\* specs\*)
    
    Currently there are no options.
    
    Each spec consists of a protocol or interface name followed by zero
    or more method bodies:
    
    protocol-or-interface-or-Object
    (methodName [args\*] body)\*
    
    Dynamically generates compiled bytecode for class with the given
    name, in a package with the same name as the current namespace, the
    given fields, and, optionally, methods for protocols and/or
    interfaces. 
    
    The class will have the (by default, immutable) fields named by
    fields, which can have type hints. Protocols/interfaces and methods
    are optional. The only methods that can be supplied are those
    declared in the protocols/interfaces.  Note that method bodies are
    not closures, the local environment includes only the named fields,
    and those fields can be accessed directy. Fields can be qualified
    with the metadata :volatile-mutable true or :unsynchronized-mutable
    true, at which point (set! afield aval) will be supported in method
    bodies. Note well that mutable fields are extremely difficult to use
    correctly, and are present only to facilitate the building of higher
    level constructs, such as Clojure's reference types, in Clojure
    itself. They are for experts only - if the semantics and
    implications of :volatile-mutable or :unsynchronized-mutable are not
    immediately apparent to you, you should not be using them.
    
    Method definitions take the form:
    
    (methodname [args\*] body)
    
    The argument and return types can be hinted on the arg and
    methodname symbols. If not supplied, they will be inferred, so type
    hints should be reserved for disambiguation.
    
    Methods should be supplied for all methods of the desired
    protocol(s) and interface(s). You can also define overrides for
    methods of Object. Note that a parameter must be supplied to
    correspond to the target object ('this' in Java parlance). Thus
    methods for interfaces will take one more argument than do the
    interface declarations. Note also that recur calls to the method
    head should **not** pass the target object, it will be supplied
    automatically and can not be substituted.
    
    In the method bodies, the (unqualified) name can be used to name the
    class (for calls to new, instance? etc).
    
    When AOT compiling, generates compiled bytecode for a class with the
    given name (a symbol), prepends the current ns as the package, and
    writes the .class file to the **compile-path** directory.
    
    One constructor will be defined, taking the designated fields.  Note
    that the field names \_<sub>meta</sub> and \_<sub>extmap</sub> are currently reserved and
    should not be used when defining your own types.
    
    Given (deftype TypeName &#x2026;), a factory function called ->TypeName
    will be defined, taking positional parameters for the fields

### clojure.core/when-let

1.  Arities

    [bindings & body]

2.  Docs

    bindings => binding-form test
    
    When test is true, evaluates body with binding-form bound to the value of test

### clojure.core/if-some

1.  Arities

    [bindings then]
    [bindings then else & oldform]

2.  Docs

    bindings => binding-form test
    
    If test is not nil, evaluates then with binding-form bound to the
    value of test, if not, yields else

### clojure.core/with-precision

1.  Arities

    [precision & exprs]

2.  Docs

    Sets the precision and rounding mode to be used for BigDecimal operations.
    
    Usage: (with-precision 10 (/ 1M 3))
    or:    (with-precision 10 :rounding HALF<sub>DOWN</sub> (/ 1M 3))
    
    The rounding mode is one of CEILING, FLOOR, HALF<sub>UP</sub>, HALF<sub>DOWN</sub>,
    HALF<sub>EVEN</sub>, UP, DOWN and UNNECESSARY; it defaults to HALF<sub>UP</sub>.

### clojure.core/lazy-seq

1.  Arities

    [& body]

2.  Docs

    Takes a body of expressions that returns an ISeq or nil, and yields
      a Seqable object that will invoke the body only the first time seq
      is called, and will cache the result and return it on all subsequent
      seq calls. See also - realized?

### clojure.core/let

1.  Arities

    [bindings & body]

2.  Docs

    binding => binding-form init-expr
    
    Evaluates the exprs in a lexical context in which the symbols in
    the binding-forms are bound to their respective init-exprs or parts
    therein.

### clojure.core/->

1.  Arities

    [x & forms]

2.  Docs

    Threads the expr through the forms. Inserts x as the
      second item in the first form, making a list of it if it is not a
      list already. If there are more forms, inserts the first form as the
      second item in second form, etc.

### clojure.core/defstruct

1.  Arities

    [name & keys]

2.  Docs

    Same as (def name (create-struct keys&#x2026;))

### clojure.core/doto

1.  Arities

    [x & forms]

2.  Docs

    Evaluates x then calls all of the methods and functions with the
      value of x supplied at the front of the given arguments.  The forms
      are evaluated in order.  Returns x.
    
    (doto (new java.util.HashMap) (.put "a" 1) (.put "b" 2))

### clojure.core/areduce

1.  Arities

    [a idx ret init expr]

2.  Docs

    Reduces an expression across an array a, using an index named idx,
      and return value named ret, initialized to init, setting ret to the 
      evaluation of expr at each step, returning ret.

### clojure.core/definline

1.  Arities

    [name & decl]

2.  Docs

    Experimental - like defmacro, except defines a named function whose
      body is the expansion, calls to which may be expanded inline as if
      it were a macro. Cannot be used with variadic (&) args.

### clojure.core/future

1.  Arities

    [& body]

2.  Docs

    Takes a body of expressions and yields a future object that will
      invoke the body in another thread, and will cache the result and
      return it on all subsequent calls to deref/@. If the computation has
      not yet finished, calls to deref/@ will block, unless the variant of
      deref with timeout is used. See also - realized?.

### clojure.core/fn

1.  Arities

    [& sigs]

2.  Docs

    params => positional-params\* , or positional-params\* & next-param
      positional-param => binding-form
      next-param => binding-form
      name => symbol
    
    Defines a function

### clojure.core/definterface

1.  Arities

    [name & sigs]

2.  Docs

    Creates a new Java interface with the given name and method sigs.
      The method return types and parameter types may be specified with type hints,
      defaulting to Object if omitted.
    
    (definterface MyInterface
      (<sup>int</sup> method1 [x])
      (<sup>Bar</sup> method2 [<sup>Baz</sup> b ^Quux q]))

### clojure.core/as->

1.  Arities

    [expr name & forms]

2.  Docs

    Binds name to expr, evaluates the first form in the lexical context
      of that binding, then binds name to that result, repeating for each
      successive form, returning the result of the last form.

### clojure.core/when-not

1.  Arities

    [test & body]

2.  Docs

    Evaluates test. If logical false, evaluates body in an implicit do.

### clojure.core/when

1.  Arities

    [test & body]

2.  Docs

    Evaluates test. If logical true, evaluates body in an implicit do.

### clojure.core/some->>

1.  Arities

    [expr & forms]

2.  Docs

    When expr is not nil, threads it into the first form (via ->>),
      and when that result is not nil, through the next etc

### clojure.core/defn

1.  Arities

    [name doc-string? attr-map? [params\*] prepost-map? body]
    [name doc-string? attr-map? ([params\*] prepost-map? body) + attr-map?]

2.  Docs

    Same as (def name (fn [params\* ] exprs\*)) or (def
        name (fn ([params\* ] exprs\*)+)) with any doc-string or attrs added
        to the var metadata. prepost-map defines a map with optional keys
    
    :pre and :post that contain collections of pre or post conditions.

### clojure.core/ns

1.  Arities

    [name docstring? attr-map? references\*]

2.  Docs

    Sets **ns** to the namespace named by name (unevaluated), creating it
      if needed.  references can be zero or more of: (:refer-clojure &#x2026;)
      (:require &#x2026;) (:use &#x2026;) (:import &#x2026;) (:load &#x2026;) (:gen-class)
      with the syntax of refer-clojure/require/use/import/load/gen-class
      respectively, except the arguments are unevaluated and need not be
      quoted. (:gen-class &#x2026;), when supplied, defaults to :name
      corresponding to the ns name, :main true, :impl-ns same as ns, and
    
    :init-impl-ns true. All options of gen-class are
    supported. The :gen-class directive is ignored when not
    compiling. If :gen-class is not supplied, when compiled only an
    nsname\_<sub>init</sub>.class will be generated. If :refer-clojure is not used, a
    default (refer 'clojure.core) is used.  Use of ns is preferred to
    individual calls to in-ns/require/use/import:
    
    (ns foo.bar
      (:refer-clojure :exclude [ancestors printf])
      (:require (clojure.contrib sql combinatorics))
      (:use (my.lib this that))
      (:import (java.util Date Timer Random)
               (java.sql Connection Statement)))

### clojure.core/amap

1.  Arities

    [a idx ret expr]

2.  Docs

    Maps an expression across an array a, using an index named idx, and
      return value named ret, initialized to a clone of a, then setting 
      each element of ret to the evaluation of expr, returning the new 
      array ret.

### clojure.core/declare

1.  Arities

    [& names]

2.  Docs

    defs the supplied var names with no bindings, useful for making forward declarations.

### clojure.core/or

1.  Arities

    []
    [x]
    [x & next]

2.  Docs

    Evaluates exprs one at a time, from left to right. If a form
      returns a logical true value, or returns that value and doesn't
      evaluate any of the other expressions, otherwise it returns the
      value of the last expression. (or) returns nil.

### clojure.core/extend-type

1.  Arities

    [t & specs]

2.  Docs

    A macro that expands into an extend call. Useful when you are
      supplying the definitions explicitly inline, extend-type
      automatically creates the maps required by extend.  Propagates the
      class as a type hint on the first argument of all fns.
    
    (extend-type MyType 
      Countable
        (cnt [c] &#x2026;)
      Foo
        (bar [x y] &#x2026;)
        (baz ([x] &#x2026;) ([x y & zs] &#x2026;)))
    
    expands into:
    
    (extend MyType
     Countable
       {:cnt (fn [c] &#x2026;)}
     Foo
       {:baz (fn ([x] &#x2026;) ([x y & zs] &#x2026;))
    
    :bar (fn [x y] &#x2026;)})

### clojure.core/defmethod

1.  Arities

    [multifn dispatch-val & fn-tail]

2.  Docs

    Creates and installs a new method of multimethod associated with dispatch-value. 

### clojure.core/time

1.  Arities

    [expr]

2.  Docs

    Evaluates expr and prints the time it took.  Returns the value of
     expr.

### clojure.core/memfn

1.  Arities

    [name & args]

2.  Docs

    Expands into code that creates a fn that expects to be passed an
      object and any args and calls the named instance method on the
      object passing the args. Use when you want to treat a Java method as
      a first-class fn. name may be type-hinted with the method receiver's
      type in order to avoid reflective calls.

### clojure.core/extend-protocol

1.  Arities

    [p & specs]

2.  Docs

    Useful when you want to provide several implementations of the same
      protocol all at once. Takes a single protocol and the implementation
      of that protocol for one or more types. Expands into calls to
      extend-type:
    
    (extend-protocol Protocol
      AType
        (foo [x] &#x2026;)
        (bar [x y] &#x2026;)
      BType
        (foo [x] &#x2026;)
        (bar [x y] &#x2026;)
      AClass
        (foo [x] &#x2026;)
        (bar [x y] &#x2026;)
      nil
        (foo [x] &#x2026;)
        (bar [x y] &#x2026;))
    
    expands into:
    
    (do
     (clojure.core/extend-type AType Protocol 
       (foo [x] &#x2026;) 
       (bar [x y] &#x2026;))
     (clojure.core/extend-type BType Protocol 
       (foo [x] &#x2026;) 
       (bar [x y] &#x2026;))
     (clojure.core/extend-type AClass Protocol 
       (foo [x] &#x2026;) 
       (bar [x y] &#x2026;))
     (clojure.core/extend-type nil Protocol 
       (foo [x] &#x2026;) 
       (bar [x y] &#x2026;)))

### clojure.core/cond->

1.  Arities

    [expr & clauses]

2.  Docs

    Takes an expression and a set of test/form pairs. Threads expr (via ->)
      through each form for which the corresponding test
      expression is true. Note that, unlike cond branching, cond-> threading does
      not short circuit after the first true test expression.

### clojure.core/dotimes

1.  Arities

    [bindings & body]

2.  Docs

    bindings => name n
    
    Repeatedly executes body (presumably for side-effects) with name
    bound to integers from 0 through n-1.

### clojure.core/reify

1.  Arities

    [& opts+specs]

2.  Docs

    reify is a macro with the following structure:
    
    (reify options\* specs\*)
    
    Currently there are no options.
    
    Each spec consists of the protocol or interface name followed by zero
    or more method bodies:
    
    protocol-or-interface-or-Object
    (methodName [args+] body)\*
    
    Methods should be supplied for all methods of the desired
    protocol(s) and interface(s). You can also define overrides for
    methods of Object. Note that the first parameter must be supplied to
    correspond to the target object ('this' in Java parlance). Thus
    methods for interfaces will take one more argument than do the
    interface declarations.  Note also that recur calls to the method
    head should **not** pass the target object, it will be supplied
    automatically and can not be substituted.
    
    The return type can be indicated by a type hint on the method name,
    and arg types can be indicated by a type hint on arg names. If you
    leave out all hints, reify will try to match on same name/arity
    method in the protocol(s)/interface(s) - this is preferred. If you
    supply any hints at all, no inference is done, so all hints (or
    default of Object) must be correct, for both arguments and return
    type. If a method is overloaded in a protocol/interface, multiple
    independent method definitions must be supplied.  If overloaded with
    same arity in an interface you must specify complete hints to
    disambiguate - a missing hint implies Object.
    
    recur works to method heads The method bodies of reify are lexical
    closures, and can refer to the surrounding local scope:
    
    (str (let [f "foo"] 
         (reify Object 
           (toString [this] f))))
    == "foo"
    
    (seq (let [f "foo"] 
         (reify clojure.lang.Seqable 
           (seq [this] (seq f)))))
    == (\f \o \o))
    
    reify always implements clojure.lang.IObj and transfers meta
    data of the form to the created object.
    
    (meta ^{:k :v} (reify Object (toString [this] "foo")))
    == {:k :v}

### clojure.core/with-open

1.  Arities

    [bindings & body]

2.  Docs

    bindings => [name init &#x2026;]
    
    Evaluates body in a try expression with names bound to the values
    of the inits, and a finally clause that calls (.close name) on each
    name in reverse order.

### clojure.core/defonce

1.  Arities

    [name expr]

2.  Docs

    defs name to have the root value of the expr iff the named var has no root value,
      else expr is unevaluated

### clojure.core/defn-

1.  Arities

    [name & decls]

2.  Docs

    same as defn, yielding non-public def

### clojure.core/defprotocol

1.  Arities

    [name & opts+sigs]

2.  Docs

    A protocol is a named set of named methods and their signatures:
      (defprotocol AProtocolName
    
    ;optional doc string
    "A doc string for AProtocol abstraction"
    
    ;method signatures
      (bar [this a b] "bar docs")
      (baz [this a] [this a b] [this a b c] "baz docs"))
    
    No implementations are provided. Docs can be specified for the
    protocol overall and for each method. The above yields a set of
    polymorphic functions and a protocol object. All are
    namespace-qualified by the ns enclosing the definition The resulting
    functions dispatch on the type of their first argument, which is
    required and corresponds to the implicit target object ('this' in 
    Java parlance). defprotocol is dynamic, has no special compile-time 
    effect, and defines no new types or classes. Implementations of 
    the protocol methods can be provided using extend.
    
    defprotocol will automatically generate a corresponding interface,
    with the same name as the protocol, i.e. given a protocol:
    my.ns/Protocol, an interface: my.ns.Protocol. The interface will
    have methods corresponding to the protocol functions, and the
    protocol will automatically work with instances of the interface.
    
    Note that you should not use this interface with deftype or
    reify, as they support the protocol directly:
    
    (defprotocol P 
      (foo [this]) 
      (bar-me [this] [this y]))
    
    (deftype Foo [a b c] 
     P
      (foo [this] a)
      (bar-me [this] b)
      (bar-me [this y] (+ c y)))
    
    (bar-me (Foo. 1 2 3) 42)
    => 45
    
    (foo 
      (let [x 42]
        (reify P 
          (foo [this] 17)
          (bar-me [this] x)
          (bar-me [this y] x))))
    => 17

### clojure.core/sync

1.  Arities

    [flags-ignored-for-now & body]

2.  Docs

    transaction-flags => TBD, pass nil for now
    
    Runs the exprs (in an implicit do) in a transaction that encompasses
    exprs and any nested calls.  Starts a transaction if none is already
    running on this thread. Any uncaught exception will abort the
    transaction and flow out of sync. The exprs may be run more than
    once, but any effects on Refs will be atomic.

### clojure.core/assert

1.  Arities

    [x]
    [x message]

2.  Docs

    Evaluates expr and throws an exception if it does not evaluate to
      logical true.

### clojure.core/letfn

1.  Arities

    [fnspecs & body]

2.  Docs

    fnspec ==> (fname [params\*] exprs) or (fname ([params\*] exprs)+)
    
    Takes a vector of function specs and a body, and generates a set of
    bindings of functions to their names. All of the names are available
    in all of the definitions of the functions, as well as the body.

### clojure.core/proxy-super

1.  Arities

    [meth & args]

2.  Docs

    Use to call a superclass method in the body of a proxy method. 
      Note, expansion captures 'this

### clojure.core/loop

1.  Arities

    [bindings & body]

2.  Docs

    Evaluates the exprs in a lexical context in which the symbols in
      the binding-forms are bound to their respective init-exprs or parts
      therein. Acts as a recur target.

### clojure.core/with-out-str

1.  Arities

    [& body]

2.  Docs

    Evaluates exprs in a context in which **out** is bound to a fresh
      StringWriter.  Returns the string created by any nested printing
      calls.

### clojure.core/condp

1.  Arities

    [pred expr & clauses]

2.  Docs

    Takes a binary predicate, an expression, and a set of clauses.
      Each clause can take the form of either:
    
    test-expr result-expr
    
    test-expr :>> result-fn
    
    Note :>> is an ordinary keyword.
    
    For each clause, (pred test-expr expr) is evaluated. If it returns
    logical true, the clause is a match. If a binary clause matches, the
    result-expr is returned, if a ternary clause matches, its result-fn,
    which must be a unary function, is called with the result of the
    predicate as its argument, the result of that call being the return
    value of condp. A single default expression can follow the clauses,
    and its value will be returned if no clause matches. If no default
    expression is provided and no clause matches, an
    IllegalArgumentException is thrown.

### clojure.core/cond

1.  Arities

    [& clauses]

2.  Docs

    Takes a set of test/expr pairs. It evaluates each test one at a
      time.  If a test returns logical true, cond evaluates and returns
      the value of the corresponding expr and doesn't evaluate any of the
      other tests or exprs. (cond) returns nil.

### clojure.core/with-in-str

1.  Arities

    [s & body]

2.  Docs

    Evaluates body in a context in which **in** is bound to a fresh
      StringReader initialized with the string s.

### clojure.core/some->

1.  Arities

    [expr & forms]

2.  Docs

    When expr is not nil, threads it into the first form (via ->),
      and when that result is not nil, through the next etc

### clojure.core/for

1.  Arities

    [seq-exprs body-expr]

2.  Docs

    List comprehension. Takes a vector of one or more
       binding-form/collection-expr pairs, each followed by zero or more
       modifiers, and yields a lazy sequence of evaluations of expr.
       Collections are iterated in a nested fashion, rightmost fastest,
       and nested coll-exprs can refer to bindings created in prior
       binding-forms.  Supported modifiers are: :let [binding-form expr &#x2026;],
    
    :while test, :when test.
    
    (take 100 (for [x (range 100000000) y (range 1000000) :while (< y x)] [x y]))

### clojure.core/binding

1.  Arities

    [bindings & body]

2.  Docs

    binding => var-symbol init-expr
    
    Creates new bindings for the (already-existing) vars, with the
    supplied initial values, executes the exprs in an implicit do, then
    re-establishes the bindings that existed before.  The new bindings
    are made in parallel (unlike let); all init-exprs are evaluated
    before the vars are bound to their new values.

### clojure.core/with-local-vars

1.  Arities

    [name-vals-vec & body]

2.  Docs

    varbinding=> symbol init-expr
    
    Executes the exprs in a context in which the symbols are bound to
    vars with per-thread bindings to the init-exprs.  The symbols refer
    to the var objects themselves, and must be accessed with var-get and
    var-set

### clojure.core/defmacro

1.  Arities

    [name doc-string? attr-map? [params\*] body]
    [name doc-string? attr-map? ([params\*] body) + attr-map?]

2.  Docs

    Like defn, but the resulting function name is declared as a
      macro and will be used as a macro by the compiler when it is
      called.

### clojure.core/proxy

1.  Arities

    [class-and-interfaces args & fs]

2.  Docs

    class-and-interfaces - a vector of class names
    
    args - a (possibly empty) vector of arguments to the superclass
    constructor.
    
    f => (name [params\*] body) or
    (name ([params\*] body) ([params+] body) &#x2026;)
    
    Expands to code which creates a instance of a proxy class that
    implements the named class/interface(s) by calling the supplied
    fns. A single class, if provided, must be first. If not provided it
    defaults to Object.
    
    The interfaces names must be valid interface types. If a method fn
    is not provided for a class method, the superclass methd will be
    called. If a method fn is not provided for an interface method, an
    UnsupportedOperationException will be thrown should it be
    called. Method fns are closures and can capture the environment in
    which proxy is called. Each method fn takes an additional implicit
    first arg, which is bound to 'this. Note that while method fns can
    be provided to override protected methods, they have no other access
    to protected members, nor to super, as these capabilities cannot be
    proxied.

### clojure.core/with-redefs

1.  Arities

    [bindings & body]

2.  Docs

    binding => var-symbol temp-value-expr
    
    Temporarily redefines Vars while executing the body.  The
    temp-value-exprs will be evaluated and each resulting value will
    replace in parallel the root value of its Var.  After the body is
    executed, the root values of all the Vars will be set back to their
    old values.  These temporary changes will be visible in all threads.
    Useful for mocking out functions during testing.

### clojure.core/locking

1.  Arities

    [x & body]

2.  Docs

    Executes exprs in an implicit do, while holding the monitor of x.
      Will release the monitor of x in all circumstances.

### clojure.core/defmulti

1.  Arities

    [name docstring? attr-map? dispatch-fn & options]

2.  Docs

    Creates a new multimethod with the associated dispatch function.
      The docstring and attribute-map are optional.
    
    Options are key-value pairs and may be one of:
    
    :default
    
    The default dispatch value, defaults to :default
    
    :hierarchy
    
    The value used for hierarchical dispatch (e.g. ::square is-a ::shape)
    
    Hierarchies are type-like relationships that do not depend upon type
    inheritance. By default Clojure's multimethods dispatch off of a
    global hierarchy map.  However, a hierarchy relationship can be
    created with the derive function used to augment the root ancestor
    created with make-hierarchy.
    
    Multimethods expect the value of the hierarchy option to be supplied as
    a reference type e.g. a var (i.e. via the Var-quote dispatch macro #'
    or the var special form).

### clojure.core/if-let

1.  Arities

    [bindings then]
    [bindings then else & oldform]

2.  Docs

    bindings => binding-form test
    
    If test is true, evaluates then with binding-form bound to the value of 
    test, if not, yields else

### clojure.core/case

1.  Arities

    [e & clauses]

2.  Docs

    Takes an expression, and a set of clauses.
    
    Each clause can take the form of either:
    
    test-constant result-expr
    
    (test-constant1 &#x2026; test-constantN)  result-expr
    
    The test-constants are not evaluated. They must be compile-time
    literals, and need not be quoted.  If the expression is equal to a
    test-constant, the corresponding result-expr is returned. A single
    default expression can follow the clauses, and its value will be
    returned if no clause matches. If no default expression is provided
    and no clause matches, an IllegalArgumentException is thrown.
    
    Unlike cond and condp, case does a constant-time dispatch, the
    clauses are not considered sequentially.  All manner of constant
    expressions are acceptable in case, including numbers, strings,
    symbols, keywords, and (Clojure) composites thereof. Note that since
    lists are used to group multiple constants that map to the same
    expression, a vector can be used to match a list if needed. The
    test-constants need not be all of the same type.

### clojure.core/io!

1.  Arities

    [& body]

2.  Docs

    If an io! block occurs in a transaction, throws an
      IllegalStateException, else runs body in an implicit do. If the
      first expression in body is a literal string, will use that as the
      exception message.

### clojure.core/lazy-cat

1.  Arities

    [& colls]

2.  Docs

    Expands to code which yields a lazy sequence of the concatenation
      of the supplied colls.  Each coll expr is not evaluated until it is
      needed. 
    
    (lazy-cat xs ys zs) `=` (concat (lazy-seq xs) (lazy-seq ys) (lazy-seq zs))

### clojure.core/comment

1.  Arities

    [& body]

2.  Docs

    Ignores body, yields nil

### clojure.core/defrecord

1.  Arities

    [name [& fields] & opts+specs]

2.  Docs

    (defrecord name [fields\*]  options\* specs\*)
    
    Currently there are no options.
    
    Each spec consists of a protocol or interface name followed by zero
    or more method bodies:
    
    protocol-or-interface-or-Object
    (methodName [args\*] body)\*
    
    Dynamically generates compiled bytecode for class with the given
    name, in a package with the same name as the current namespace, the
    given fields, and, optionally, methods for protocols and/or
    interfaces.
    
    The class will have the (immutable) fields named by
    fields, which can have type hints. Protocols/interfaces and methods
    are optional. The only methods that can be supplied are those
    declared in the protocols/interfaces.  Note that method bodies are
    not closures, the local environment includes only the named fields,
    and those fields can be accessed directly.
    
    Method definitions take the form:
    
    (methodname [args\*] body)
    
    The argument and return types can be hinted on the arg and
    methodname symbols. If not supplied, they will be inferred, so type
    hints should be reserved for disambiguation.
    
    Methods should be supplied for all methods of the desired
    protocol(s) and interface(s). You can also define overrides for
    methods of Object. Note that a parameter must be supplied to
    correspond to the target object ('this' in Java parlance). Thus
    methods for interfaces will take one more argument than do the
    interface declarations. Note also that recur calls to the method
    head should **not** pass the target object, it will be supplied
    automatically and can not be substituted.
    
    In the method bodies, the (unqualified) name can be used to name the
    class (for calls to new, instance? etc).
    
    The class will have implementations of several (clojure.lang)
    interfaces generated automatically: IObj (metadata support) and
    IPersistentMap, and all of their superinterfaces.
    
    In addition, defrecord will define type-and-value-based =,
    and will defined Java .hashCode and .equals consistent with the
    contract for java.util.Map.
    
    When AOT compiling, generates compiled bytecode for a class with the
    given name (a symbol), prepends the current ns as the package, and
    writes the .class file to the **compile-path** directory.
    
    Two constructors will be defined, one taking the designated fields
    followed by a metadata map (nil for none) and an extension field
    map (nil for none), and one taking only the fields (using nil for
    meta and extension fields). Note that the field names \_<sub>meta</sub>
    and \_<sub>extmap</sub> are currently reserved and should not be used when
    defining your own records.
    
    Given (defrecord TypeName &#x2026;), two factory functions will be
    defined: ->TypeName, taking positional parameters for the fields,
    and map->TypeName, taking a map of keywords to field values.

### clojure.core/and

1.  Arities

    []
    [x]
    [x & next]

2.  Docs

    Evaluates exprs one at a time, from left to right. If a form
      returns logical false (nil or false), and returns that value and
      doesn't evaluate any of the other expressions, otherwise it returns
      the value of the last expr. (and) returns true.

### clojure.core/when-some

1.  Arities

    [bindings & body]

2.  Docs

    bindings => binding-form test
    
    When test is not nil, evaluates body with binding-form bound to the
    value of test

### clojure.core/->>

1.  Arities

    [x & forms]

2.  Docs

    Threads the expr through the forms. Inserts x as the
      last item in the first form, making a list of it if it is not a
      list already. If there are more forms, inserts the first form as the
      last item in second form, etc.

### clojure.core/refer-clojure

1.  Arities

    [& filters]

2.  Docs

    Same as (refer 'clojure.core <filters>)

## Functions

### clojure.core/primitives-classnames

1.  Arities

2.  Docs

    nil

### clojure.core/+'

1.  Arities

    []
    [x]
    [x y]
    [x y & more]

2.  Docs

    Returns the sum of nums. (+) returns 0. Supports arbitrary precision.
      See also: +

### clojure.core/decimal?

1.  Arities

    [n]

2.  Docs

    Returns true if n is a BigDecimal

### clojure.core/restart-agent

1.  Arities

    [a new-state & options]

2.  Docs

    When an agent is failed, changes the agent state to new-state and
      then un-fails the agent so that sends are allowed again.  If
      a :clear-actions true option is given, any actions queued on the
      agent that were being held while it was failed will be discarded,
      otherwise those held actions will proceed.  The new-state must pass
      the validator if any, or restart will throw an exception and the
      agent will remain failed with its old state and error.  Watchers, if
      any, will NOT be notified of the new state.  Throws an exception if
      the agent is not failed.

### clojure.core/sort-by

1.  Arities

    [keyfn coll]
    [keyfn comp coll]

2.  Docs

    Returns a sorted sequence of the items in coll, where the sort
      order is determined by comparing (keyfn item).  If no comparator is
      supplied, uses compare.  comparator must implement
      java.util.Comparator.  If coll is a Java array, it will be modified.
      To avoid this, sort a copy of the array.

### clojure.core/macroexpand

1.  Arities

    [form]

2.  Docs

    Repeatedly calls macroexpand-1 on form until it no longer
      represents a macro form, then returns it.  Note neither
      macroexpand-1 nor macroexpand expand macros in subforms.

### clojure.core/ensure

1.  Arities

    [ref]

2.  Docs

    Must be called in a transaction. Protects the ref from modification
      by other transactions.  Returns the in-transaction-value of
      ref. Allows for more concurrency than (ref-set ref @ref)

### clojure.core/chunk-first

1.  Arities

    [s]

2.  Docs

    nil

### clojure.core/tree-seq

1.  Arities

    [branch? children root]

2.  Docs

    Returns a lazy sequence of the nodes in a tree, via a depth-first walk.
       branch? must be a fn of one arg that returns true if passed a node
       that can have children (but may not).  children must be a fn of one
       arg that returns a sequence of the children. Will only be called on
       nodes for which branch? returns true. Root is the root node of the
      tree.

### clojure.core/unchecked-remainder-int

1.  Arities

    [x y]

2.  Docs

    Returns the remainder of division of x by y, both int.
      Note - uses a primitive operator subject to truncation.

### clojure.core/seq

1.  Arities

    [coll]

2.  Docs

    Returns a seq on the collection. If the collection is
        empty, returns nil.  (seq nil) returns nil. seq also works on
        Strings, native Java arrays (of reference types) and any objects
        that implement Iterable.

### clojure.core/reduce

1.  Arities

    [f coll]
    [f val coll]

2.  Docs

    f should be a function of 2 arguments. If val is not supplied,
      returns the result of applying f to the first 2 items in coll, then
      applying f to that result and the 3rd item, etc. If coll contains no
      items, f must accept no arguments as well, and reduce returns the
      result of calling f with no arguments.  If coll has only 1 item, it
      is returned and f is not called.  If val is supplied, returns the
      result of applying f to val and the first item in coll, then
      applying f to that result and the 2nd item, etc. If coll contains no
      items, returns val and f is not called.

### clojure.core/find-ns

1.  Arities

    [sym]

2.  Docs

    Returns the namespace named by the symbol or nil if it doesn't exist.

### clojure.core/get-thread-bindings

1.  Arities

    []

2.  Docs

    Get a map with the Var/value pairs which is currently in effect for the
      current thread.

### clojure.core/contains?

1.  Arities

    [coll key]

2.  Docs

    Returns true if key is present in the given collection, otherwise
      returns false.  Note that for numerically indexed collections like
      vectors and Java arrays, this tests if the numeric key is within the
      range of indexes. 'contains?' operates constant or logarithmic time;
      it will not perform a linear search for a value.  See also 'some'.

### clojure.core/every?

1.  Arities

    [pred coll]

2.  Docs

    Returns true if (pred x) is logical true for every x in coll, else
      false.

### clojure.core/proxy-mappings

1.  Arities

    [proxy]

2.  Docs

    Takes a proxy instance and returns the proxy's fn map.

### clojure.core/keep-indexed

1.  Arities

    [f coll]

2.  Docs

    Returns a lazy sequence of the non-nil results of (f index item). Note,
      this means false return values will be included.  f must be free of
      side-effects.

### clojure.core/subs

1.  Arities

    [s start]
    [s start end]

2.  Docs

    Returns the substring of s beginning at start inclusive, and ending
      at end (defaults to length of string), exclusive.

### clojure.core/ref-min-history

1.  Arities

    [ref]
    [ref n]

2.  Docs

    Gets the min-history of a ref, or sets it and returns the ref

### clojure.core/set

1.  Arities

    [coll]

2.  Docs

    Returns a set of the distinct elements of coll.

### clojure.core/take-last

1.  Arities

    [n coll]

2.  Docs

    Returns a seq of the last n items in coll.  Depending on the type
      of coll may be no better than linear time.  For vectors, see also subvec.

### clojure.core/bit-set

1.  Arities

    [x n]

2.  Docs

    Set bit at index n

### clojure.core/butlast

1.  Arities

    [coll]

2.  Docs

    Return a seq of all but the last item in coll, in linear time

### clojure.core/satisfies?

1.  Arities

    [protocol x]

2.  Docs

    Returns true if x satisfies the protocol

### clojure.core/line-seq

1.  Arities

    [rdr]

2.  Docs

    Returns the lines of text from rdr as a lazy sequence of strings.
      rdr must implement java.io.BufferedReader.

### clojure.core/unchecked-subtract-int

1.  Arities

    [x y]

2.  Docs

    Returns the difference of x and y, both int.
      Note - uses a primitive operator subject to overflow.

### clojure.core/take-nth

1.  Arities

    [n coll]

2.  Docs

    Returns a lazy seq of every nth item in coll.

### clojure.core/first

1.  Arities

    [coll]

2.  Docs

    Returns the first item in the collection. Calls seq on its
        argument. If coll is nil, returns nil.

### clojure.core/re-groups

1.  Arities

    [m]

2.  Docs

    Returns the groups from the most recent match/find. If there are no
      nested groups, returns a string of the entire match. If there are
      nested groups, returns a vector of the groups, the first element
      being the entire match.

### clojure.core/seq?

1.  Arities

    [x]

2.  Docs

    Return true if x implements ISeq

### clojure.core/dec'

1.  Arities

    [x]

2.  Docs

    Returns a number one less than num. Supports arbitrary precision.
      See also: dec

### clojure.core/ns-unmap

1.  Arities

    [ns sym]

2.  Docs

    Removes the mappings for the symbol from the namespace.

### clojure.core/println-str

1.  Arities

    [& xs]

2.  Docs

    println to a string, returning it

### clojure.core/with-bindings\*

1.  Arities

    [binding-map f & args]

2.  Docs

    Takes a map of Var/value pairs. Installs for the given Vars the associated
      values as thread-local bindings. Then calls f with the supplied arguments.
      Pops the installed bindings after f returned. Returns whatever f returns.

### clojure.core/iterator-seq

1.  Arities

    [iter]

2.  Docs

    Returns a seq on a java.util.Iterator. Note that most collections
      providing iterators implement Iterable and thus support seq directly.

### clojure.core/iterate

1.  Arities

    [f x]

2.  Docs

    Returns a lazy sequence of x, (f x), (f (f x)) etc. f must be free of side-effects

### clojure.core/slurp

1.  Arities

    [f & opts]

2.  Docs

    Opens a reader on f and reads all its contents, returning a string.
      See clojure.java.io/reader for a complete list of supported arguments.

### clojure.core/newline

1.  Arities

    []

2.  Docs

    Writes a platform-specific newline to **out**

### clojure.core/short-array

1.  Arities

    [size-or-seq]
    [size init-val-or-seq]

2.  Docs

    Creates an array of shorts

### clojure.core/fn?

1.  Arities

    [x]

2.  Docs

    Returns true if x implements Fn, i.e. is an object created via fn.

### clojure.core/doall

1.  Arities

    [coll]
    [n coll]

2.  Docs

    When lazy sequences are produced via functions that have side
      effects, any effects other than those needed to produce the first
      element in the seq do not occur until the seq is consumed. doall can
      be used to force any effects. Walks through the successive nexts of
      the seq, retains the head and returns it, thus causing the entire
      seq to reside in memory at one time.

### clojure.core/prefers

1.  Arities

    [multifn]

2.  Docs

    Given a multimethod, returns a map of preferred value -> set of other values

### clojure.core/enumeration-seq

1.  Arities

    [e]

2.  Docs

    Returns a seq on a java.util.Enumeration

### clojure.core/dissoc

1.  Arities

    [map]
    [map key]
    [map key & ks]

2.  Docs

    dissoc[iate]. Returns a new map of the same (hashed/sorted) type,
      that does not contain a mapping for key(s).

### clojure.core/atom

1.  Arities

    [x]
    [x & options]

2.  Docs

    Creates and returns an Atom with an initial value of x and zero or
      more options (in any order):
    
    :meta metadata-map
    
    :validator validate-fn
    
    If metadata-map is supplied, it will become the metadata on the
    atom. validate-fn must be nil or a side-effect-free fn of one
    argument, which will be passed the intended new state on any state
    change. If the new state is unacceptable, the validate-fn should
    return false or throw an exception.

### clojure.core/bit-shift-right

1.  Arities

    [x n]

2.  Docs

    Bitwise shift right

### clojure.core/print-method

1.  Arities

2.  Docs

    nil

### clojure.core/peek

1.  Arities

    [coll]

2.  Docs

    For a list or queue, same as first, for a vector, same as, but much
      more efficient than, last. If the collection is empty, returns nil.

### clojure.core/aget

1.  Arities

    [array idx]
    [array idx & idxs]

2.  Docs

    Returns the value at the index/indices. Works on Java arrays of all
      types.

### clojure.core/last

1.  Arities

    [coll]

2.  Docs

    Return the last item in coll, in linear time

### clojure.core/pr

1.  Arities

    []
    [x]
    [x & more]

2.  Docs

    Prints the object(s) to the output stream that is the current value
      of **out**.  Prints the object(s), separated by spaces if there is
      more than one.  By default, pr and prn print in a way that objects
      can be read by the reader

### clojure.core/namespace

1.  Arities

    [x]

2.  Docs

    Returns the namespace String of a symbol or keyword, or nil if not present.

### clojure.core/push-thread-bindings

1.  Arities

    [bindings]

2.  Docs

    WARNING: This is a low-level function. Prefer high-level macros like
      binding where ever possible.
    
    Takes a map of Var/value pairs. Binds each Var to the associated value for
    the current thread. Each call **MUST** be accompanied by a matching call to
    pop-thread-bindings wrapped in a try-finally!
    
    (push-thread-bindings bindings)
    (try
      &#x2026;
      (finally
        (pop-thread-bindings)))

### clojure.core/bases

1.  Arities

    [c]

2.  Docs

    Returns the immediate superclass and direct interfaces of c, if any

### clojure.core/=

1.  Arities

    [x]
    [x y]
    [x y & more]

2.  Docs

    Equality. Returns true if x equals y, false if not. Same as
      Java x.equals(y) except it also works for nil, and compares
      numbers and collections in a type-independent manner.  Clojure's immutable data
      structures define equals() (and thus =) as a value, not an identity,
      comparison.

### clojure.core/remove-ns

1.  Arities

    [sym]

2.  Docs

    Removes the namespace named by the symbol. Use with caution.
      Cannot be used to remove the clojure namespace.

### clojure.core/take

1.  Arities

    [n coll]

2.  Docs

    Returns a lazy sequence of the first n items in coll, or all items if
      there are fewer than n.

### clojure.core/vector?

1.  Arities

    [x]

2.  Docs

    Return true if x implements IPersistentVector

### clojure.core/thread-bound?

1.  Arities

    [& vars]

2.  Docs

    Returns true if all of the vars provided as arguments have thread-local bindings.
       Implies that set!'ing the provided vars will succeed.  Returns true if no vars are provided.

### clojure.core/send-via

1.  Arities

    [executor a f & args]

2.  Docs

    Dispatch an action to an agent. Returns the agent immediately.
      Subsequently, in a thread supplied by executor, the state of the agent
      will be set to the value of:
    
    (apply action-fn state-of-agent args)

### clojure.core/boolean

1.  Arities

    [x]

2.  Docs

    Coerce to boolean

### clojure.core/bit-shift-left

1.  Arities

    [x n]

2.  Docs

    Bitwise shift left

### clojure.core/find-var

1.  Arities

    [sym]

2.  Docs

    Returns the global var named by the namespace-qualified symbol, or
      nil if no var with that name.

### clojure.core/rand-int

1.  Arities

    [n]

2.  Docs

    Returns a random integer between 0 (inclusive) and n (exclusive).

### clojure.core/aclone

1.  Arities

    [array]

2.  Docs

    Returns a clone of the Java array. Works on arrays of known
      types.

### clojure.core/chunk

1.  Arities

    [b]

2.  Docs

    nil

### clojure.core/dec

1.  Arities

    [x]

2.  Docs

    Returns a number one less than num. Does not auto-promote
      longs, will throw on overflow. See also: dec'

### clojure.core/future-call

1.  Arities

    [f]

2.  Docs

    Takes a function of no args and yields a future object that will
      invoke the function in another thread, and will cache the result and
      return it on all subsequent calls to deref/@. If the computation has
      not yet finished, calls to deref/@ will block, unless the variant
      of deref with timeout is used. See also - realized?.

### clojure.core/resultset-seq

1.  Arities

    [rs]

2.  Docs

    Creates and returns a lazy sequence of structmaps corresponding to
      the rows in the java.sql.ResultSet rs

### clojure.core/struct

1.  Arities

    [s & vals]

2.  Docs

    Returns a new structmap instance with the keys of the
      structure-basis. vals must be supplied for basis keys in order -
      where values are not supplied they will default to nil.

### clojure.core/map

1.  Arities

    [f coll]
    [f c1 c2]
    [f c1 c2 c3]
    [f c1 c2 c3 & colls]

2.  Docs

    Returns a lazy sequence consisting of the result of applying f to the
      set of first items of each coll, followed by applying f to the set
      of second items in each coll, until any one of the colls is
      exhausted.  Any remaining items in other colls are ignored. Function
      f should accept number-of-colls arguments.

### clojure.core/juxt

1.  Arities

    [f]
    [f g]
    [f g h]
    [f g h & fs]

2.  Docs

    Takes a set of functions and returns a fn that is the juxtaposition
      of those fns.  The returned fn takes a variable number of args, and
      returns a vector containing the result of applying each fn to the
      args (left-to-right).
      ((juxt a b c) x) => [(a x) (b x) (c x)]

### clojure.core/ns-publics

1.  Arities

    [ns]

2.  Docs

    Returns a map of the public intern mappings for the namespace.

### clojure.core/<

1.  Arities

    [x]
    [x y]
    [x y & more]

2.  Docs

    Returns non-nil if nums are in monotonically increasing order,
      otherwise false.

### clojure.core/\*source-path\*

1.  Arities

2.  Docs

    nil

### clojure.core/test

1.  Arities

    [v]

2.  Docs

    test [v] finds fn at key :test in var metadata and calls it,
      presuming failure will throw exception

### clojure.core/rest

1.  Arities

    [coll]

2.  Docs

    Returns a possibly empty seq of the items after the first. Calls seq on its
      argument.

### clojure.core/ex-data

1.  Arities

    [ex]

2.  Docs

    Returns exception data (a map) if ex is an IExceptionInfo.
       Otherwise returns nil.

### clojure.core/compile

1.  Arities

    [lib]

2.  Docs

    Compiles the namespace named by the symbol lib into a set of
      classfiles. The source for the lib must be in a proper
      classpath-relative directory. The output files will go into the
      directory specified by **compile-path**, and that directory too must
      be in the classpath.

### clojure.core/isa?

1.  Arities

    [child parent]
    [h child parent]

2.  Docs

    Returns true if (= child parent), or child is directly or indirectly derived from
      parent, either via a Java type inheritance relationship or a
      relationship established via derive. h must be a hierarchy obtained
      from make-hierarchy, if not supplied defaults to the global
      hierarchy

### clojure.core/munge

1.  Arities

    [s]

2.  Docs

    nil

### clojure.core/set-error-mode!

1.  Arities

    [a mode-keyword]

2.  Docs

    Sets the error-mode of agent a to mode-keyword, which must be
      either :fail or :continue.  If an action being run by the agent
      throws an exception or doesn't pass the validator fn, an
      error-handler may be called (see set-error-handler!), after which,
      if the mode is :continue, the agent will continue as if neither the
      action that caused the error nor the error itself ever happened.
    
    If the mode is :fail, the agent will become failed and will stop
    accepting new 'send' and 'send-off' actions, and any previously
    queued actions will be held until a 'restart-agent'.  Deref will
    still work, returning the state of the agent before the error.

### clojure.core/re-seq

1.  Arities

    [re s]

2.  Docs

    Returns a lazy sequence of successive matches of pattern in string,
      using java.util.regex.Matcher.find(), each such match processed with
      re-groups.

### clojure.core/char?

1.  Arities

    [x]

2.  Docs

    Return true if x is a Character

### clojure.core/make-hierarchy

1.  Arities

    []

2.  Docs

    Creates a hierarchy object for use with derive, isa? etc.

### clojure.core/set-agent-send-executor!

1.  Arities

    [executor]

2.  Docs

    Sets the ExecutorService to be used by send

### clojure.core/keep

1.  Arities

    [f coll]

2.  Docs

    Returns a lazy sequence of the non-nil results of (f item). Note,
      this means false return values will be included.  f must be free of
      side-effects.

### clojure.core/char

1.  Arities

    [x]

2.  Docs

    Coerce to char

### clojure.core/mapcat

1.  Arities

    [f & colls]

2.  Docs

    Returns the result of applying concat to the result of applying map
      to f and colls.  Thus function f should return a collection.

### clojure.core/unchecked-long

1.  Arities

    [x]

2.  Docs

    Coerce to long. Subject to rounding or truncation.

### clojure.core/aset-long

1.  Arities

    [array idx val]
    [array idx idx2 & idxv]

2.  Docs

    Sets the value at the index/indices. Works on arrays of long. Returns val.

### clojure.core/some?

1.  Arities

    [x]

2.  Docs

    Returns true if x is not nil, false otherwise.

### clojure.core/unchecked-negate

1.  Arities

    [x]

2.  Docs

    Returns the negation of x, a long.
      Note - uses a primitive operator subject to overflow.

### clojure.core/\*command-line-args\*

1.  Arities

2.  Docs

    A sequence of the supplied command line arguments, or nil if
      none were supplied

### clojure.core/reverse

1.  Arities

    [coll]

2.  Docs

    Returns a seq of the items in coll in reverse order. Not lazy.

### clojure.core/range

1.  Arities

    []
    [end]
    [start end]
    [start end step]

2.  Docs

    Returns a lazy seq of nums from start (inclusive) to end
      (exclusive), by step, where start defaults to 0, step to 1, and end to
      infinity. When step is equal to 0, returns an infinite sequence of
      start. When start is equal to end, returns empty list.

### clojure.core/sort

1.  Arities

    [coll]
    [comp coll]

2.  Docs

    Returns a sorted sequence of the items in coll. If no comparator is
      supplied, uses compare.  comparator must implement
      java.util.Comparator.  If coll is a Java array, it will be modified.
      To avoid this, sort a copy of the array.

### clojure.core/-cache-protocol-fn

1.  Arities

    [pf x c interf]

2.  Docs

    nil

### clojure.core/unchecked-inc-int

1.  Arities

    [x]

2.  Docs

    Returns a number one greater than x, an int.
      Note - uses a primitive operator subject to overflow.

### clojure.core/map-indexed

1.  Arities

    [f coll]

2.  Docs

    Returns a lazy sequence consisting of the result of applying f to 0
      and the first item of coll, followed by applying f to 1 and the second
      item in coll, etc, until coll is exhausted. Thus function f should
      accept 2 arguments, index and item.

### clojure.core/rand-nth

1.  Arities

    [coll]

2.  Docs

    Return a random element of the (sequential) collection. Will have
      the same performance characteristics as nth for the given
      collection.

### clojure.core/comp

1.  Arities

    []
    [f]
    [f g]
    [f g h]
    [f1 f2 f3 & fs]

2.  Docs

    Takes a set of functions and returns a fn that is the composition
      of those fns.  The returned fn takes a variable number of args,
      applies the rightmost of fns to the args, the next
      fn (right-to-left) to the result, etc.

### clojure.core/await

1.  Arities

    [& agents]

2.  Docs

    Blocks the current thread (indefinitely!) until all actions
      dispatched thus far, from this thread or agent, to the agent(s) have
      occurred.  Will block on failed agents.  Will never return if
      a failed agent is restarted with :clear-actions true.

### clojure.core/spit

1.  Arities

    [f content & options]

2.  Docs

    Opposite of slurp.  Opens f with writer, writes content, then
      closes f. Options passed to clojure.java.io/writer.

### clojure.core/future-done?

1.  Arities

    [f]

2.  Docs

    Returns true if future f is done

### clojure.core/\*read-eval\*

1.  Arities

2.  Docs

    Defaults to true (or value specified by system property, see below)
      \*\*\*This setting implies that the full power of the reader is in play,
      including syntax that can cause code to execute. It should never be
      used with untrusted sources. See also: clojure.edn/read.\*\*\*
    
    When set to logical false in the thread-local binding,
    the eval reader (#=) and record/type literal syntax are disabled in read/load.
    Example (will fail): (binding [\*read-eval\* false] (read-string "#=(\* 2 21)"))
    
    The default binding can be controlled by the system property
    'clojure.read.eval' System properties can be set on the command line
    like this:
    
    java -Dclojure.read.eval=false &#x2026;
    
    The system property can also be set to 'unknown' via
    -Dclojure.read.eval=unknown, in which case the default binding
    is :unknown and all reads will fail in contexts where **read-eval**
    has not been explicitly bound to either true or false. This setting
    can be a useful diagnostic tool to ensure that all of your reads
    occur in considered contexts. You can also accomplish this in a
    particular scope by binding **read-eval** to :unknown

### clojure.core/dorun

1.  Arities

    [coll]
    [n coll]

2.  Docs

    When lazy sequences are produced via functions that have side
      effects, any effects other than those needed to produce the first
      element in the seq do not occur until the seq is consumed. dorun can
      be used to force any effects. Walks through the successive nexts of
      the seq, does not retain the head and returns nil.

### clojure.core/disj

1.  Arities

    [set]
    [set key]
    [set key & ks]

2.  Docs

    disj[oin]. Returns a new set of the same (hashed/sorted) type, that
      does not contain key(s).

### clojure.core/\*2

1.  Arities

2.  Docs

    bound in a repl thread to the second most recent value printed

### clojure.core/eval

1.  Arities

    [form]

2.  Docs

    Evaluates the form data structure (not text!) and returns the result.

### clojure.core/cons

1.  Arities

    [x seq]

2.  Docs

    Returns a new seq where x is the first element and seq is
        the rest.

### clojure.core/refer

1.  Arities

    [ns-sym & filters]

2.  Docs

    refers to all public vars of ns, subject to filters.
      filters can include at most one each of:
    
    :exclude list-of-symbols
    
    :only list-of-symbols
    
    :rename map-of-fromsymbol-tosymbol
    
    For each public interned var in the namespace named by the symbol,
    adds a mapping from the name of the var to the var to the current
    namespace.  Throws an exception if name is already mapped to
    something else in the current namespace. Filters can be used to
    select a subset, via inclusion or exclusion, or to provide a mapping
    to a symbol different from the var's name, in order to prevent
    clashes. Use :use in the ns macro in preference to calling this directly.

### clojure.core/print-dup

1.  Arities

2.  Docs

    nil

### clojure.core/-reset-methods

1.  Arities

    [protocol]

2.  Docs

    nil

### clojure.core/floats

1.  Arities

    [xs]

2.  Docs

    Casts to float[]

### clojure.core/pos?

1.  Arities

    [x]

2.  Docs

    Returns true if num is greater than zero, else false

### clojure.core/fnil

1.  Arities

    [f x]
    [f x y]
    [f x y z]

2.  Docs

    Takes a function f, and returns a function that calls f, replacing
      a nil first argument to f with the supplied value x. Higher arity
      versions can replace arguments in the second and third
      positions (y, z). Note that the function f can take any number of
      arguments, not just the one(s) being nil-patched.

### clojure.core/merge-with

1.  Arities

    [f & maps]

2.  Docs

    Returns a map that consists of the rest of the maps conj-ed onto
      the first.  If a key occurs in more than one map, the mapping(s)
      from the latter (left-to-right) will be combined with the mapping in
      the result by calling (f val-in-result val-in-latter).

### clojure.core/nthrest

1.  Arities

    [coll n]

2.  Docs

    Returns the nth rest of coll, coll when n is 0.

### clojure.core/load

1.  Arities

    [& paths]

2.  Docs

    Loads Clojure code from resources in classpath. A path is interpreted as
      classpath-relative if it begins with a slash or relative to the root
      directory for the current namespace otherwise.

### clojure.core/\*verbose-defrecords\*

1.  Arities

2.  Docs

    nil

### clojure.core/sequential?

1.  Arities

    [coll]

2.  Docs

    Returns true if coll implements Sequential

### clojure.core/\*print-level\*

1.  Arities

2.  Docs

    **print-level** controls how many levels deep the printer will
      print nested objects. If it is bound to logical false, there is no
      limit. Otherwise, it must be bound to an integer indicating the maximum
      level to print. Each argument to print is at level 0; if an argument is a
      collection, its items are at level 1; and so on. If an object is a
      collection and is at a level greater than or equal to the value bound to
      **print-level**, the printer prints '#' to represent it. The root binding
      is nil indicating no limit.

### clojure.core/shuffle

1.  Arities

    [coll]

2.  Docs

    Return a random permutation of coll

### clojure.core/boolean-array

1.  Arities

    [size-or-seq]
    [size init-val-or-seq]

2.  Docs

    Creates an array of booleans

### clojure.core/find

1.  Arities

    [map key]

2.  Docs

    Returns the map entry for key, or nil if key not present.

### clojure.core/alength

1.  Arities

    [array]

2.  Docs

    Returns the length of the Java array. Works on arrays of all
      types.

### clojure.core/bit-xor

1.  Arities

    [x y]
    [x y & more]

2.  Docs

    Bitwise exclusive or

### clojure.core/deliver

1.  Arities

    [promise val]

2.  Docs

    Delivers the supplied value to the promise, releasing any pending
      derefs. A subsequent call to deliver on a promise will have no effect.

### clojure.core/unsigned-bit-shift-right

1.  Arities

    [x n]

2.  Docs

    Bitwise shift right, without sign-extension.

### clojure.core/neg?

1.  Arities

    [x]

2.  Docs

    Returns true if num is less than zero, else false

### clojure.core/var-set

1.  Arities

    [x val]

2.  Docs

    Sets the value in the var object to val. The var must be
     thread-locally bound.

### clojure.core/unchecked-float

1.  Arities

    [x]

2.  Docs

    Coerce to float. Subject to rounding.

### clojure.core/pmap

1.  Arities

    [f coll]
    [f coll & colls]

2.  Docs

    Like map, except f is applied in parallel. Semi-lazy in that the
      parallel computation stays ahead of the consumption, but doesn't
      realize the entire result unless required. Only useful for
      computationally intensive functions where the time of f dominates
      the coordination overhead.

### clojure.core/error-mode

1.  Arities

    [a]

2.  Docs

    Returns the error-mode of agent a.  See set-error-mode!

### clojure.core/num

1.  Arities

    [x]

2.  Docs

    Coerce to Number

### clojure.core/reduced?

1.  Arities

    [x]

2.  Docs

    Returns true if x is the result of a call to reduced

### clojure.core/disj!

1.  Arities

    [set]
    [set key]
    [set key & ks]

2.  Docs

    disj[oin]. Returns a transient set of the same (hashed/sorted) type, that
      does not contain key(s).

### clojure.core/float?

1.  Arities

    [n]

2.  Docs

    Returns true if n is a floating point number

### clojure.core/aset-float

1.  Arities

    [array idx val]
    [array idx idx2 & idxv]

2.  Docs

    Sets the value at the index/indices. Works on arrays of float. Returns val.

### clojure.core/bean

1.  Arities

    [x]

2.  Docs

    Takes a Java object and returns a read-only implementation of the
      map abstraction based upon its JavaBean properties.

### clojure.core/booleans

1.  Arities

    [xs]

2.  Docs

    Casts to boolean[]

### clojure.core/ns-unalias

1.  Arities

    [ns sym]

2.  Docs

    Removes the alias for the symbol from the namespace.

### clojure.core/int-array

1.  Arities

    [size-or-seq]
    [size init-val-or-seq]

2.  Docs

    Creates an array of ints

### clojure.core/set?

1.  Arities

    [x]

2.  Docs

    Returns true if x implements IPersistentSet

### clojure.core/inc'

1.  Arities

    [x]

2.  Docs

    Returns a number one greater than num. Supports arbitrary precision.
      See also: inc

### clojure.core/flush

1.  Arities

    []

2.  Docs

    Flushes the output stream that is the current value of
      **out**

### clojure.core/take-while

1.  Arities

    [pred coll]

2.  Docs

    Returns a lazy sequence of successive items from coll while
      (pred item) returns true. pred must be free of side-effects.

### clojure.core/vary-meta

1.  Arities

    [obj f & args]

2.  Docs

    Returns an object of the same type and value as obj, with
      (apply f (meta obj) args) as its metadata.

### clojure.core/<=

1.  Arities

    [x]
    [x y]
    [x y & more]

2.  Docs

    Returns non-nil if nums are in monotonically non-decreasing order,
      otherwise false.

### clojure.core/alter

1.  Arities

    [ref fun & args]

2.  Docs

    Must be called in a transaction. Sets the in-transaction-value of
      ref to:
    
    (apply fun in-transaction-value-of-ref args)
    
    and returns the in-transaction-value of ref.

### clojure.core/-'

1.  Arities

    [x]
    [x y]
    [x y & more]

2.  Docs

    If no ys are supplied, returns the negation of x, else subtracts
      the ys from x and returns the result. Supports arbitrary precision.
      See also: -

### clojure.core/conj!

1.  Arities

    [coll x]

2.  Docs

    Adds x to the transient collection, and return coll. The 'addition'
      may happen at different 'places' depending on the concrete type.

### clojure.core/repeatedly

1.  Arities

    [f]
    [n f]

2.  Docs

    Takes a function of no args, presumably with side effects, and
      returns an infinite (or length n if supplied) lazy sequence of calls
      to it

### clojure.core/zipmap

1.  Arities

    [keys vals]

2.  Docs

    Returns a map with the keys mapped to the corresponding vals.

### clojure.core/alter-var-root

1.  Arities

    [v f & args]

2.  Docs

    Atomically alters the root binding of var v by applying f to its
      current value plus any args

### clojure.core/biginteger

1.  Arities

    [x]

2.  Docs

    Coerce to BigInteger

### clojure.core/remove

1.  Arities

    [pred coll]

2.  Docs

    Returns a lazy sequence of the items in coll for which
      (pred item) returns false. pred must be free of side-effects.

### clojure.core/\*

1.  Arities

    []
    [x]
    [x y]
    [x y & more]

2.  Docs

    Returns the product of nums. (\*) returns 1. Does not auto-promote
      longs, will throw on overflow. See also: \*'

### clojure.core/re-pattern

1.  Arities

    [s]

2.  Docs

    Returns an instance of java.util.regex.Pattern, for use, e.g. in
      re-matcher.

### clojure.core/min

1.  Arities

    [x]
    [x y]
    [x y & more]

2.  Docs

    Returns the least of the nums.

### clojure.core/pop!

1.  Arities

    [coll]

2.  Docs

    Removes the last item from a transient vector. If
      the collection is empty, throws an exception. Returns coll

### clojure.core/chunk-append

1.  Arities

    [b x]

2.  Docs

    nil

### clojure.core/prn-str

1.  Arities

    [& xs]

2.  Docs

    prn to a string, returning it

### clojure.core/format

1.  Arities

    [fmt & args]

2.  Docs

    Formats a string using java.lang.String.format, see java.util.Formatter for format
      string syntax

### clojure.core/reversible?

1.  Arities

    [coll]

2.  Docs

    Returns true if coll implements Reversible

### clojure.core/shutdown-agents

1.  Arities

    []

2.  Docs

    Initiates a shutdown of the thread pools that back the agent
      system. Running actions will complete, but no new actions will be
      accepted

### clojure.core/conj

1.  Arities

    [coll x]
    [coll x & xs]

2.  Docs

    conj[oin]. Returns a new collection with the xs
        'added'. (conj nil item) returns (item).  The 'addition' may
        happen at different 'places' depending on the concrete type.

### clojure.core/bound?

1.  Arities

    [& vars]

2.  Docs

    Returns true if all of the vars provided as arguments have any bound value, root or thread-local.
       Implies that deref'ing the provided vars will succeed. Returns true if no vars are provided.

### clojure.core/\*print-length\*

1.  Arities

2.  Docs

    **print-length** controls how many items of each collection the
      printer will print. If it is bound to logical false, there is no
      limit. Otherwise, it must be bound to an integer indicating the maximum
      number of items of each collection to print. If a collection contains
      more items, the printer will print items up to the limit followed by
      '&#x2026;' to represent the remaining items. The root binding is nil
      indicating no limit.

### clojure.core/\*file\*

1.  Arities

2.  Docs

    The path of the file being evaluated, as a String.
    
    When there is no file, e.g. in the REPL, the value is not defined.

### clojure.core/compare-and-set!

1.  Arities

    [atom oldval newval]

2.  Docs

    Atomically sets the value of atom to newval if and only if the
      current value of the atom is identical to oldval. Returns true if
      set happened, else false

### clojure.core/\*use-context-classloader\*

1.  Arities

2.  Docs

    nil

### clojure.core/await1

1.  Arities

    [a]

2.  Docs

    nil

### clojure.core/ref-set

1.  Arities

    [ref val]

2.  Docs

    Must be called in a transaction. Sets the value of ref.
      Returns val.

### clojure.core/pop-thread-bindings

1.  Arities

    []

2.  Docs

    Pop one set of bindings pushed with push-binding before. It is an error to
      pop bindings without pushing before.

### clojure.core/interleave

1.  Arities

    []
    [c1]
    [c1 c2]
    [c1 c2 & colls]

2.  Docs

    Returns a lazy seq of the first item in each coll, then the second etc.

### clojure.core/printf

1.  Arities

    [fmt & args]

2.  Docs

    Prints formatted output, as per format

### clojure.core/map?

1.  Arities

    [x]

2.  Docs

    Return true if x implements IPersistentMap

### clojure.core/\*err\*

1.  Arities

2.  Docs

    A java.io.Writer object representing standard error for print operations.
    
    Defaults to System/err, wrapped in a PrintWriter

### clojure.core/get

1.  Arities

    [map key]
    [map key not-found]

2.  Docs

    Returns the value mapped to key, not-found or nil if key not present.

### clojure.core/identity

1.  Arities

    [x]

2.  Docs

    Returns its argument.

### clojure.core/into

1.  Arities

    [to from]

2.  Docs

    Returns a new coll consisting of to-coll with all of the items of
      from-coll conjoined.

### clojure.core/long

1.  Arities

    [x]

2.  Docs

    Coerce to long

### clojure.core/double

1.  Arities

    [x]

2.  Docs

    Coerce to double

### clojure.core/nfirst

1.  Arities

    [x]

2.  Docs

    Same as (next (first x))

### clojure.core/meta

1.  Arities

    [obj]

2.  Docs

    Returns the metadata of obj, returns nil if there is no metadata.

### clojure.core/find-protocol-impl

1.  Arities

    [protocol x]

2.  Docs

    nil

### clojure.core/bit-and-not

1.  Arities

    [x y]
    [x y & more]

2.  Docs

    Bitwise and with complement

### clojure.core/\*default-data-reader-fn\*

1.  Arities

2.  Docs

    When no data reader is found for a tag and **default-data-reader-fn**
      is non-nil, it will be called with two arguments,
      the tag and the value.  If **default-data-reader-fn** is nil (the
      default), an exception will be thrown for the unknown tag.

### clojure.core/var?

1.  Arities

    [v]

2.  Docs

    Returns true if v is of type clojure.lang.Var

### clojure.core/method-sig

1.  Arities

    [meth]

2.  Docs

    nil

### clojure.core/unchecked-add-int

1.  Arities

    [x y]

2.  Docs

    Returns the sum of x and y, both int.
      Note - uses a primitive operator subject to overflow.

### clojure.core/unquote-splicing

1.  Arities

2.  Docs

    nil

### clojure.core/hash-ordered-coll

1.  Arities

    [coll]

2.  Docs

    Returns the hash code, consistent with =, for an external ordered
       collection implementing Iterable.
       See <http://clojure.org/data_structures#hash> for full algorithms.

### clojure.core/reset-meta!

1.  Arities

    [iref metadata-map]

2.  Docs

    Atomically resets the metadata for a namespace/var/ref/agent/atom

### clojure.core/cycle

1.  Arities

    [coll]

2.  Docs

    Returns a lazy (infinite!) sequence of repetitions of the items in coll.

### clojure.core/seque

1.  Arities

    [s]
    [n-or-q s]

2.  Docs

    Creates a queued seq on another (presumably lazy) seq s. The queued
      seq will produce a concrete seq in the background, and can get up to
      n items ahead of the consumer. n-or-q can be an integer n buffer
      size, or an instance of java.util.concurrent BlockingQueue. Note
      that reading from a seque can block if the reader gets ahead of the
      producer.

### clojure.core/empty?

1.  Arities

    [coll]

2.  Docs

    Returns true if coll has no items - same as (not (seq coll)).
      Please use the idiom (seq x) rather than (not (empty? x))

### clojure.core/short

1.  Arities

    [x]

2.  Docs

    Coerce to short

### clojure.core/filterv

1.  Arities

    [pred coll]

2.  Docs

    Returns a vector of the items in coll for which
      (pred item) returns true. pred must be free of side-effects.

### clojure.core/hash

1.  Arities

    [x]

2.  Docs

    Returns the hash code of its argument. Note this is the hash code
      consistent with =, and thus is different than .hashCode for Integer,
      Short, Byte and Clojure collections.

### clojure.core/quot

1.  Arities

    [num div]

2.  Docs

    quot[ient] of dividing numerator by denominator.

### clojure.core/ns-aliases

1.  Arities

    [ns]

2.  Docs

    Returns a map of the aliases for the namespace.

### clojure.core/read

1.  Arities

    []
    [stream]
    [stream eof-error? eof-value]
    [stream eof-error? eof-value recursive?]

2.  Docs

    Reads the next object from stream, which must be an instance of
      java.io.PushbackReader or some derivee.  stream defaults to the
      current value of **in**.
    
    Note that read can execute code (controlled by **read-eval**),
    and as such should be used only with trusted sources.
    
    For data structure interop use clojure.edn/read

### clojure.core/unchecked-double

1.  Arities

    [x]

2.  Docs

    Coerce to double. Subject to rounding.

### clojure.core/key

1.  Arities

    [e]

2.  Docs

    Returns the key of the map entry.

### clojure.core/longs

1.  Arities

    [xs]

2.  Docs

    Casts to long[]

### clojure.core/not=

1.  Arities

    [x]
    [x y]
    [x y & more]

2.  Docs

    Same as (not (= obj1 obj2))

### clojure.core/string?

1.  Arities

    [x]

2.  Docs

    Return true if x is a String

### clojure.core/aset-double

1.  Arities

    [array idx val]
    [array idx idx2 & idxv]

2.  Docs

    Sets the value at the index/indices. Works on arrays of double. Returns val.

### clojure.core/unchecked-multiply-int

1.  Arities

    [x y]

2.  Docs

    Returns the product of x and y, both int.
      Note - uses a primitive operator subject to overflow.

### clojure.core/chunk-rest

1.  Arities

    [s]

2.  Docs

    nil

### clojure.core/pcalls

1.  Arities

    [& fns]

2.  Docs

    Executes the no-arg fns in parallel, returning a lazy sequence of
      their values

### clojure.core/\*allow-unresolved-vars\*

1.  Arities

2.  Docs

    nil

### clojure.core/remove-all-methods

1.  Arities

    [multifn]

2.  Docs

    Removes all of the methods of multimethod.

### clojure.core/ns-resolve

1.  Arities

    [ns sym]
    [ns env sym]

2.  Docs

    Returns the var or Class to which a symbol will be resolved in the
      namespace (unless found in the environment), else nil.  Note that
      if the symbol is fully qualified, the var/Class to which it resolves
      need not be present in the namespace.

### clojure.core/aset-boolean

1.  Arities

    [array idx val]
    [array idx idx2 & idxv]

2.  Docs

    Sets the value at the index/indices. Works on arrays of boolean. Returns val.

### clojure.core/trampoline

1.  Arities

    [f]
    [f & args]

2.  Docs

    trampoline can be used to convert algorithms requiring mutual
      recursion without stack consumption. Calls f with supplied args, if
      any. If f returns a fn, calls that fn with no arguments, and
      continues to repeat, until the return value is not a fn, then
      returns that non-fn value. Note that if you want to return a fn as a
      final value, you must wrap it in some data structure and unpack it
      after trampoline returns.

### clojure.core/\*1

1.  Arities

2.  Docs

    bound in a repl thread to the most recent value printed

### clojure.core/vec

1.  Arities

    [coll]

2.  Docs

    Creates a new vector containing the contents of coll. Java arrays
      will be aliased and should not be modified.

### clojure.core/\*print-meta\*

1.  Arities

2.  Docs

    If set to logical true, when printing an object, its metadata will also
      be printed in a form that can be read back by the reader.
    
    Defaults to false.

### clojure.core/int

1.  Arities

    [x]

2.  Docs

    Coerce to int

### clojure.core/ns-refers

1.  Arities

    [ns]

2.  Docs

    Returns a map of the refer mappings for the namespace.

### clojure.core/rand

1.  Arities

    []
    [n]

2.  Docs

    Returns a random floating point number between 0 (inclusive) and
      n (default 1) (exclusive).

### clojure.core/second

1.  Arities

    [x]

2.  Docs

    Same as (first (next x))

### clojure.core/vector-of

1.  Arities

    [t]
    [t & elements]

2.  Docs

    Creates a new vector of a single primitive type t, where t is one
      of :int :long :float :double :byte :short :char or :boolean. The
      resulting vector complies with the interface of vectors in general,
      but stores the values unboxed internally.
    
    Optionally takes one or more elements to populate the vector.

### clojure.core/hash-combine

1.  Arities

    [x y]

2.  Docs

    nil

### clojure.core/>

1.  Arities

    [x]
    [x y]
    [x y & more]

2.  Docs

    Returns non-nil if nums are in monotonically decreasing order,
      otherwise false.

### clojure.core/replace

1.  Arities

    [smap coll]

2.  Docs

    Given a map of replacement pairs and a vector/collection, returns a
      vector/seq with any elements = a key in smap replaced with the
      corresponding val in smap

### clojure.core/associative?

1.  Arities

    [coll]

2.  Docs

    Returns true if coll implements Associative

### clojure.core/unchecked-int

1.  Arities

    [x]

2.  Docs

    Coerce to int. Subject to rounding or truncation.

### clojure.core/set-error-handler!

1.  Arities

    [a handler-fn]

2.  Docs

    Sets the error-handler of agent a to handler-fn.  If an action
      being run by the agent throws an exception or doesn't pass the
      validator fn, handler-fn will be called with two arguments: the
      agent and the exception.

### clojure.core/keyword?

1.  Arities

    [x]

2.  Docs

    Return true if x is a Keyword

### clojure.core/force

1.  Arities

    [x]

2.  Docs

    If x is a Delay, returns the (possibly cached) value of its expression, else returns x

### clojure.core/bound-fn\*

1.  Arities

    [f]

2.  Docs

    Returns a function, which will install the same bindings in effect as in
      the thread at the time bound-fn\* was called and then call f with any given
      arguments. This may be used to define a helper function which runs on a
      different thread, but needs the same bindings in place.

### clojure.core/namespace-munge

1.  Arities

    [ns]

2.  Docs

    Convert a Clojure namespace name to a legal Java package name.

### clojure.core/group-by

1.  Arities

    [f coll]

2.  Docs

    Returns a map of the elements of coll keyed by the result of
      f on each element. The value at each key will be a vector of the
      corresponding elements, in the order they appeared in coll.

### clojure.core/prn

1.  Arities

    [& more]

2.  Docs

    Same as pr followed by (newline). Observes **flush-on-newline**

### clojure.core/extend

1.  Arities

    [atype & proto+mmaps]

2.  Docs

    Implementations of protocol methods can be provided using the extend construct:
    
    (extend AType
      AProtocol
       {:foo an-existing-fn
    
    :bar (fn [a b] &#x2026;)
    
      :baz (fn ([a]&#x2026;) ([a b] &#x2026;)&#x2026;)}
    BProtocol 
      {&#x2026;} 
    &#x2026;)
    
    extend takes a type/class (or interface, see below), and one or more
    protocol + method map pairs. It will extend the polymorphism of the
    protocol's methods to call the supplied methods when an AType is
    provided as the first argument. 
    
    Method maps are maps of the keyword-ized method names to ordinary
    fns. This facilitates easy reuse of existing fns and fn maps, for
    code reuse/mixins without derivation or composition. You can extend
    an interface to a protocol. This is primarily to facilitate interop
    with the host (e.g. Java) but opens the door to incidental multiple
    inheritance of implementation since a class can inherit from more
    than one interface, both of which extend the protocol. It is TBD how
    to specify which impl to use. You can extend a protocol on nil.
    
    If you are supplying the definitions explicitly (i.e. not reusing
    exsting functions or mixin maps), you may find it more convenient to
    use the extend-type or extend-protocol macros.
    
    Note that multiple independent extend clauses can exist for the same
    type, not all protocols need be defined in a single extend call.
    
    See also:
    extends?, satisfies?, extenders

### clojure.core/unchecked-multiply

1.  Arities

    [x y]

2.  Docs

    Returns the product of x and y, both long.
      Note - uses a primitive operator subject to overflow.

### clojure.core/default-data-readers

1.  Arities

2.  Docs

    Default map of data reader functions provided by Clojure. May be
      overridden by binding **data-readers**.

### clojure.core/->VecSeq

1.  Arities

    [am vec anode i offset]

2.  Docs

    Positional factory function for class clojure.core.VecSeq.

### clojure.core/even?

1.  Arities

    [n]

2.  Docs

    Returns true if n is even, throws an exception if n is not an integer

### clojure.core/unchecked-dec

1.  Arities

    [x]

2.  Docs

    Returns a number one less than x, a long.
      Note - uses a primitive operator subject to overflow.

### clojure.core/double-array

1.  Arities

    [size-or-seq]
    [size init-val-or-seq]

2.  Docs

    Creates an array of doubles

### clojure.core/in-ns

1.  Arities

    [name]

2.  Docs

    Sets **ns** to the namespace named by the symbol, creating it if needed.

### clojure.core/create-ns

1.  Arities

    [sym]

2.  Docs

    Create a new namespace named by the symbol if one doesn't already
      exist, returns it or the already-existing namespace of the same
      name.

### clojure.core/re-matcher

1.  Arities

    [re s]

2.  Docs

    Returns an instance of java.util.regex.Matcher, for use, e.g. in
      re-find.

### clojure.core/ref

1.  Arities

    [x]
    [x & options]

2.  Docs

    Creates and returns a Ref with an initial value of x and zero or
      more options (in any order):
    
    :meta metadata-map
    
    :validator validate-fn
    
    :min-history (default 0)
    
    :max-history (default 10)
    
    If metadata-map is supplied, it will become the metadata on the
    ref. validate-fn must be nil or a side-effect-free fn of one
    argument, which will be passed the intended new state on any state
    change. If the new state is unacceptable, the validate-fn should
    return false or throw an exception. validate-fn will be called on
    transaction commit, when all refs have their final values.
    
    Normally refs accumulate history dynamically as needed to deal with
    read demands. If you know in advance you will need history you can
    set :min-history to ensure it will be available when first needed (instead
    of after a read fault). History is limited, and the limit can be set
    with :max-history.

### clojure.core/bigint

1.  Arities

    [x]

2.  Docs

    Coerce to BigInt

### clojure.core/extends?

1.  Arities

    [protocol atype]

2.  Docs

    Returns true if atype extends protocol

### clojure.core/promise

1.  Arities

    []

2.  Docs

    Returns a promise object that can be read with deref/@, and set,
      once only, with deliver. Calls to deref/@ prior to delivery will
      block, unless the variant of deref with timeout is used. All
      subsequent derefs will return the same delivered value without
      blocking. See also - realized?.

### clojure.core/aset-char

1.  Arities

    [array idx val]
    [array idx idx2 & idxv]

2.  Docs

    Sets the value at the index/indices. Works on arrays of char. Returns val.

### clojure.core/rseq

1.  Arities

    [rev]

2.  Docs

    Returns, in constant time, a seq of the items in rev (which
      can be a vector or sorted-map), in reverse order. If rev is empty returns nil

### clojure.core/construct-proxy

1.  Arities

    [c & ctor-args]

2.  Docs

    Takes a proxy class and any arguments for its superclass ctor and
      creates and returns an instance of the proxy.

### clojure.core/agent-errors

1.  Arities

    [a]

2.  Docs

    DEPRECATED: Use 'agent-error' instead.
      Returns a sequence of the exceptions thrown during asynchronous
      actions of the agent.

### clojure.core/\*compile-files\*

1.  Arities

2.  Docs

    Set to true when compiling files, false otherwise.

### clojure.core/\*math-context\*

1.  Arities

2.  Docs

    nil

### clojure.core/float

1.  Arities

    [x]

2.  Docs

    Coerce to float

### clojure.core/pr-str

1.  Arities

    [& xs]

2.  Docs

    pr to a string, returning it

### clojure.core/concat

1.  Arities

    []
    [x]
    [x y]
    [x y & zs]

2.  Docs

    Returns a lazy seq representing the concatenation of the elements in the supplied colls.

### clojure.core/aset-short

1.  Arities

    [array idx val]
    [array idx idx2 & idxv]

2.  Docs

    Sets the value at the index/indices. Works on arrays of short. Returns val.

### clojure.core/set-agent-send-off-executor!

1.  Arities

    [executor]

2.  Docs

    Sets the ExecutorService to be used by send-off

### clojure.core/symbol

1.  Arities

    [name]
    [ns name]

2.  Docs

    Returns a Symbol with the given namespace and name.

### clojure.core/to-array-2d

1.  Arities

    [coll]

2.  Docs

    Returns a (potentially-ragged) 2-dimensional array of Objects
      containing the contents of coll, which can be any Collection of any
      Collection.

### clojure.core/mod

1.  Arities

    [num div]

2.  Docs

    Modulus of num and div. Truncates toward negative infinity.

### clojure.core/pop

1.  Arities

    [coll]

2.  Docs

    For a list or queue, returns a new list/queue without the first
      item, for a vector, returns a new vector without the last item. If
      the collection is empty, throws an exception.  Note - not the same
      as next/butlast.

### clojure.core/use

1.  Arities

    [& args]

2.  Docs

    Like 'require, but also refers to each lib's namespace using
      clojure.core/refer. Use :use in the ns macro in preference to calling
      this directly.
    
    'use accepts additional options in libspecs: :exclude, :only, :rename.
    The arguments and semantics for :exclude, :only, and :rename are the same
    as those documented for clojure.core/refer.

### clojure.core/unquote

1.  Arities

2.  Docs

    nil

### clojure.core/dissoc!

1.  Arities

    [map key]
    [map key & ks]

2.  Docs

    Returns a transient map that doesn't contain a mapping for key(s).

### clojure.core/reductions

1.  Arities

    [f coll]
    [f init coll]

2.  Docs

    Returns a lazy seq of the intermediate values of the reduction (as
      per reduce) of coll by f, starting with init.

### clojure.core/aset-byte

1.  Arities

    [array idx val]
    [array idx idx2 & idxv]

2.  Docs

    Sets the value at the index/indices. Works on arrays of byte. Returns val.

### clojure.core/ref-history-count

1.  Arities

    [ref]

2.  Docs

    Returns the history count of a ref

### clojure.core/-

1.  Arities

    [x]
    [x y]
    [x y & more]

2.  Docs

    If no ys are supplied, returns the negation of x, else subtracts
      the ys from x and returns the result. Does not auto-promote
      longs, will throw on overflow. See also: -'

### clojure.core/assoc!

1.  Arities

    [coll key val]
    [coll key val & kvs]

2.  Docs

    When applied to a transient map, adds mapping of key(s) to
      val(s). When applied to a transient vector, sets the val at index.
      Note - index must be <= (count vector). Returns coll.

### clojure.core/hash-set

1.  Arities

    []
    [& keys]

2.  Docs

    Returns a new hash set with supplied keys.  Any equal keys are
      handled as if by repeated uses of conj.

### clojure.core/reduce-kv

1.  Arities

    [f init coll]

2.  Docs

    Reduces an associative collection. f should be a function of 3
      arguments. Returns the result of applying f to init, the first key
      and the first value in coll, then applying f to that result and the
      2nd key and value, etc. If coll contains no entries, returns init
      and f is not called. Note that reduce-kv is supported on vectors,
      where the keys will be the ordinals.

### clojure.core/cast

1.  Arities

    [c x]

2.  Docs

    Throws a ClassCastException if x is not a c, else returns x.

### clojure.core/reset!

1.  Arities

    [atom newval]

2.  Docs

    Sets the value of atom to newval without regard for the
      current value. Returns newval.

### clojure.core/name

1.  Arities

    [x]

2.  Docs

    Returns the name String of a string, symbol or keyword.

### clojure.core/ffirst

1.  Arities

    [x]

2.  Docs

    Same as (first (first x))

### clojure.core/sorted-set

1.  Arities

    [& keys]

2.  Docs

    Returns a new sorted set with supplied keys.  Any equal keys are
      handled as if by repeated uses of conj.

### clojure.core/counted?

1.  Arities

    [coll]

2.  Docs

    Returns true if coll implements count in constant time

### clojure.core/byte-array

1.  Arities

    [size-or-seq]
    [size init-val-or-seq]

2.  Docs

    Creates an array of bytes

### clojure.core/println

1.  Arities

    [& more]

2.  Docs

    Same as print followed by (newline)

### clojure.core/macroexpand-1

1.  Arities

    [form]

2.  Docs

    If form represents a macro form, returns its expansion,
      else returns form.

### clojure.core/assoc-in

1.  Arities

    [m [k & ks] v]

2.  Docs

    Associates a value in a nested associative structure, where ks is a
      sequence of keys and v is the new value and returns a new nested structure.
      If any levels do not exist, hash-maps will be created.

### clojure.core/char-name-string

1.  Arities

2.  Docs

    Returns name string for char or nil if none

### clojure.core/bit-test

1.  Arities

    [x n]

2.  Docs

    Test bit at index n

### clojure.core/EMPTY-NODE

1.  Arities

2.  Docs

    nil

### clojure.core/memoize

1.  Arities

    [f]

2.  Docs

    Returns a memoized version of a referentially transparent function. The
      memoized version of the function keeps a cache of the mapping from arguments
      to results and, when calls with the same arguments are repeated often, has
      higher performance at the expense of higher memory use.

### clojure.core/alter-meta!

1.  Arities

    [iref f & args]

2.  Docs

    Atomically sets the metadata for a namespace/var/ref/agent/atom to be:
    
    (apply f its-current-meta args)
    
    f must be free of side-effects

### clojure.core/future?

1.  Arities

    [x]

2.  Docs

    Returns true if x is a future

### clojure.core/zero?

1.  Arities

    [x]

2.  Docs

    Returns true if num is zero, else false

### clojure.core/require

1.  Arities

    [& args]

2.  Docs

    Loads libs, skipping any that are already loaded. Each argument is
      either a libspec that identifies a lib, a prefix list that identifies
      multiple libs whose names share a common prefix, or a flag that modifies
      how all the identified libs are loaded. Use :require in the ns macro
      in preference to calling this directly.
    
    Libs
    
    A 'lib' is a named set of resources in classpath whose contents define a
    library of Clojure code. Lib names are symbols and each lib is associated
    with a Clojure namespace and a Java package that share its name. A lib's
    name also locates its root directory within classpath using Java's
    package name to classpath-relative path mapping. All resources in a lib
    should be contained in the directory structure under its root directory.
    All definitions a lib makes should be in its associated namespace.
    
    'require loads a lib by loading its root resource. The root resource path
    is derived from the lib name in the following manner:
    Consider a lib named by the symbol 'x.y.z; it has the root directory
    <classpath>/x/y/, and its root resource is <classpath>/x/y/z.clj. The root
    resource should contain code to create the lib's namespace (usually by using
    the ns macro) and load any additional lib resources.
    
    Libspecs
    
    A libspec is a lib name or a vector containing a lib name followed by
    options expressed as sequential keywords and arguments.
    
    Recognized options:
    
    :as takes a symbol as its argument and makes that symbol an alias to the
      lib's namespace in the current namespace.
    
    :refer takes a list of symbols to refer from the namespace or the :all
      keyword to bring in all public vars.
    
    Prefix Lists
    
    It's common for Clojure code to depend on several libs whose names have
    the same prefix. When specifying libs, prefix lists can be used to reduce
    repetition. A prefix list contains the shared prefix followed by libspecs
    with the shared prefix removed from the lib names. After removing the
    prefix, the names that remain must not contain any periods.
    
    Flags
    
    A flag is a keyword.
    Recognized flags: :reload, :reload-all, :verbose
    
    :reload forces loading of all the identified libs even if they are
      already loaded
    
    :reload-all implies :reload and also forces loading of all libs that the
      identified libs directly or indirectly load via require or use
    
    :verbose triggers printing information about each load, alias, and refer
    
    Example:
    
    The following would load the libraries clojure.zip and clojure.set
    abbreviated as 's'.
    
    (require '(clojure zip [set :as s]))

### clojure.core/unchecked-dec-int

1.  Arities

    [x]

2.  Docs

    Returns a number one less than x, an int.
      Note - uses a primitive operator subject to overflow.

### clojure.core/persistent!

1.  Arities

    [coll]

2.  Docs

    Returns a new, persistent version of the transient collection, in
      constant time. The transient collection cannot be used after this
      call, any such use will throw an exception.

### clojure.core/nnext

1.  Arities

    [x]

2.  Docs

    Same as (next (next x))

### clojure.core/add-watch

1.  Arities

    [reference key fn]

2.  Docs

    Adds a watch function to an agent/atom/var/ref reference. The watch
      fn must be a fn of 4 args: a key, the reference, its old-state, its
      new-state. Whenever the reference's state might have been changed,
      any registered watches will have their functions called. The watch fn
      will be called synchronously, on the agent's thread if an agent,
      before any pending sends if agent or ref. Note that an atom's or
      ref's state may have changed again prior to the fn call, so use
      old/new-state rather than derefing the reference. Note also that watch
      fns may be called from multiple threads simultaneously. Var watchers
      are triggered only by root binding changes, not thread-local
      set!s. Keys must be unique per reference, and can be used to remove
      the watch with remove-watch, but are otherwise considered opaque by
      the watch mechanism.

### clojure.core/not-every?

1.  Arities

    [pred coll]

2.  Docs

    Returns false if (pred x) is logical true for every x in
      coll, else true.

### clojure.core/class?

1.  Arities

    [x]

2.  Docs

    Returns true if x is an instance of Class

### clojure.core/rem

1.  Arities

    [num div]

2.  Docs

    remainder of dividing numerator by denominator.

### clojure.core/agent-error

1.  Arities

    [a]

2.  Docs

    Returns the exception thrown during an asynchronous action of the
      agent if the agent is failed.  Returns nil if the agent is not
      failed.

### clojure.core/some

1.  Arities

    [pred coll]

2.  Docs

    Returns the first logical true value of (pred x) for any x in coll,
      else nil.  One common idiom is to use a set as pred, for example
      this will return :fred if :fred is in the sequence, otherwise nil:
      (some #{:fred} coll)

### clojure.core/future-cancelled?

1.  Arities

    [f]

2.  Docs

    Returns true if future f is cancelled

### clojure.core/struct-map

1.  Arities

    [s & inits]

2.  Docs

    Returns a new structmap instance with the keys of the
      structure-basis. keyvals may contain all, some or none of the basis
      keys - where values are not supplied they will default to nil.
      keyvals can also contain keys not in the basis.

### clojure.core/drop

1.  Arities

    [n coll]

2.  Docs

    Returns a lazy sequence of all but the first n items in coll.

### clojure.core/\*data-readers\*

1.  Arities

2.  Docs

    Map from reader tag symbols to data reader Vars.
    
    When Clojure starts, it searches for files named 'data<sub>readers</sub>.clj'
    at the root of the classpath. Each such file must contain a literal
    map of symbols, like this:
    
    {foo/bar my.project.foo/bar
     foo/baz my.project/baz}
    
    The first symbol in each pair is a tag that will be recognized by
    the Clojure reader. The second symbol in the pair is the
    fully-qualified name of a Var which will be invoked by the reader to
    parse the form following the tag. For example, given the
    data<sub>readers</sub>.clj file above, the Clojure reader would parse this
    form:
    
    \#foo/bar [1 2 3]
    
    by invoking the Var #'my.project.foo/bar on the vector [1 2 3]. The
    data reader function is invoked on the form AFTER it has been read
    as a normal Clojure data structure by the reader.
    
    Reader tags without namespace qualifiers are reserved for
    Clojure. Default reader tags are defined in
    clojure.core/default-data-readers but may be overridden in
    data<sub>readers</sub>.clj or by rebinding this Var.

### clojure.core/nth

1.  Arities

    [coll index]
    [coll index not-found]

2.  Docs

    Returns the value at the index. get returns nil if index out of
      bounds, nth throws an exception unless not-found is supplied.  nth
      also works for strings, Java arrays, regex Matchers and Lists, and,
      in O(n) time, for sequences.

### clojure.core/sorted?

1.  Arities

    [coll]

2.  Docs

    Returns true if coll implements Sorted

### clojure.core/nil?

1.  Arities

    [x]

2.  Docs

    Returns true if x is nil, false otherwise.

### clojure.core/split-at

1.  Arities

    [n coll]

2.  Docs

    Returns a vector of [(take n coll) (drop n coll)]

### clojure.core/\*e

1.  Arities

2.  Docs

    bound in a repl thread to the most recent exception caught by the repl

### clojure.core/load-reader

1.  Arities

    [rdr]

2.  Docs

    Sequentially read and evaluate the set of forms contained in the
      stream/file

### clojure.core/select-keys

1.  Arities

    [map keyseq]

2.  Docs

    Returns a map containing only those entries in map whose key is in keys

### clojure.core/bit-and

1.  Arities

    [x y]
    [x y & more]

2.  Docs

    Bitwise and

### clojure.core/list\*

1.  Arities

    [args]
    [a args]
    [a b args]
    [a b c args]
    [a b c d & more]

2.  Docs

    Creates a new list containing the items prepended to the rest, the
      last of which will be treated as a sequence.

### clojure.core/update-in

1.  Arities

    [m [k & ks] f & args]

2.  Docs

    'Updates' a value in a nested associative structure, where ks is a
      sequence of keys and f is a function that will take the old value
      and any supplied args and return the new value, and returns a new
      nested structure.  If any levels do not exist, hash-maps will be
      created.

### clojure.core/prefer-method

1.  Arities

    [multifn dispatch-val-x dispatch-val-y]

2.  Docs

    Causes the multimethod to prefer matches of dispatch-val-x over dispatch-val-y 
       when there is a conflict

### clojure.core/aset-int

1.  Arities

    [array idx val]
    [array idx idx2 & idxv]

2.  Docs

    Sets the value at the index/indices. Works on arrays of int. Returns val.

### clojure.core/\*clojure-version\*

1.  Arities

2.  Docs

    The version info for Clojure core, as a map containing :major :minor 
    
    :incremental and :qualifier keys. Feature releases may increment 
    
    :minor and/or :major, bugfix releases will increment :incremental. 
    Possible values of :qualifier include "GA", "SNAPSHOT", "RC-x" "BETA-x"

### clojure.core/\*'

1.  Arities

    []
    [x]
    [x y]
    [x y & more]

2.  Docs

    Returns the product of nums. (\*) returns 1. Supports arbitrary precision.
      See also: \*

### clojure.core/instance?

1.  Arities

    [c x]

2.  Docs

    Evaluates x and tests if it is an instance of the class
        c. Returns true or false

### clojure.core/mix-collection-hash

1.  Arities

    [hash-basis count]

2.  Docs

    Mix final collection hash for ordered or unordered collections.
       hash-basis is the combined collection hash, count is the number
       of elements included in the basis. Note this is the hash code
       consistent with =, different from .hashCode.
       See <http://clojure.org/data_structures#hash> for full algorithms.

### clojure.core/re-find

1.  Arities

    [m]
    [re s]

2.  Docs

    Returns the next regex match, if any, of string to pattern, using
      java.util.regex.Matcher.find().  Uses re-groups to return the
      groups.

### clojure.core/val

1.  Arities

    [e]

2.  Docs

    Returns the value in the map entry.

### clojure.core/unchecked-add

1.  Arities

    [x y]

2.  Docs

    Returns the sum of x and y, both long.
      Note - uses a primitive operator subject to overflow.

### clojure.core/loaded-libs

1.  Arities

    []

2.  Docs

    Returns a sorted set of symbols naming the currently loaded libs

### clojure.core/->Vec

1.  Arities

    [am cnt shift root tail \_meta]

2.  Docs

    Positional factory function for class clojure.core.Vec.

### clojure.core/not

1.  Arities

    [x]

2.  Docs

    Returns true if x is logical false, false otherwise.

### clojure.core/with-meta

1.  Arities

    [obj m]

2.  Docs

    Returns an object of the same type and value as obj, with
        map m as its metadata.

### clojure.core/the-ns

1.  Arities

    [x]

2.  Docs

    If passed a namespace, returns it. Else, when passed a symbol,
      returns the namespace named by it, throwing an exception if not
      found.

### clojure.core/record?

1.  Arities

    [x]

2.  Docs

    Returns true if x is a record

### clojure.core/type

1.  Arities

    [x]

2.  Docs

    Returns the :type metadata of x, or its Class if none

### clojure.core/identical?

1.  Arities

    [x y]

2.  Docs

    Tests if 2 arguments are the same object

### clojure.core/unchecked-divide-int

1.  Arities

    [x y]

2.  Docs

    Returns the division of x by y, both int.
      Note - uses a primitive operator subject to truncation.

### clojure.core/ns-name

1.  Arities

    [ns]

2.  Docs

    Returns the name of the namespace, a symbol.

### clojure.core/max-key

1.  Arities

    [k x]
    [k x y]
    [k x y & more]

2.  Docs

    Returns the x for which (k x), a number, is greatest.

### clojure.core/\*unchecked-math\*

1.  Arities

2.  Docs

    While bound to true, compilations of +, -, \*, inc, dec and the
      coercions will be done without overflow checks. Default: false.

### clojure.core/\*out\*

1.  Arities

2.  Docs

    A java.io.Writer object representing standard output for print operations.
    
    Defaults to System/out, wrapped in an OutputStreamWriter

### clojure.core/file-seq

1.  Arities

    [dir]

2.  Docs

    A tree seq on java.io.Files

### clojure.core/agent

1.  Arities

    [state & options]

2.  Docs

    Creates and returns an agent with an initial value of state and
      zero or more options (in any order):
    
    :meta metadata-map
    
    :validator validate-fn
    
    :error-handler handler-fn
    
    :error-mode mode-keyword
    
    If metadata-map is supplied, it will become the metadata on the
    agent. validate-fn must be nil or a side-effect-free fn of one
    argument, which will be passed the intended new state on any state
    change. If the new state is unacceptable, the validate-fn should
    return false or throw an exception.  handler-fn is called if an
    action throws an exception or if validate-fn rejects a new state &#x2013;
    see set-error-handler! for details.  The mode-keyword may be either
    
    :continue (the default if an error-handler is given) or :fail (the
    default if no error-handler is given) &#x2013; see set-error-mode! for
    details.

### clojure.core/ns-map

1.  Arities

    [ns]

2.  Docs

    Returns a map of all the mappings for the namespace.

### clojure.core/set-validator!

1.  Arities

    [iref validator-fn]

2.  Docs

    Sets the validator-fn for a var/ref/agent/atom. validator-fn must be nil or a
      side-effect-free fn of one argument, which will be passed the intended
      new state on any state change. If the new state is unacceptable, the
      validator-fn should return false or throw an exception. If the current state (root
      value if var) is not acceptable to the new validator, an exception
      will be thrown and the validator will not be changed.

### clojure.core/swap!

1.  Arities

    [atom f]
    [atom f x]
    [atom f x y]
    [atom f x y & args]

2.  Docs

    Atomically swaps the value of atom to be:
      (apply f current-value-of-atom args). Note that f may be called
      multiple times, and thus should be free of side effects.  Returns
      the value that was swapped in.

### clojure.core/vals

1.  Arities

    [map]

2.  Docs

    Returns a sequence of the map's values, in the same order as (seq map).

### clojure.core/unchecked-subtract

1.  Arities

    [x y]

2.  Docs

    Returns the difference of x and y, both long.
      Note - uses a primitive operator subject to overflow.

### clojure.core/\*warn-on-reflection\*

1.  Arities

2.  Docs

    When set to true, the compiler will emit warnings when reflection is
      needed to resolve Java method calls or field accesses.
    
    Defaults to false.

### clojure.core/sorted-set-by

1.  Arities

    [comparator & keys]

2.  Docs

    Returns a new sorted set with supplied keys, using the supplied
      comparator.  Any equal keys are handled as if by repeated uses of
      conj.

### clojure.core/\*compile-path\*

1.  Arities

2.  Docs

    Specifies the directory where 'compile' will write out .class
      files. This directory must be in the classpath for 'compile' to
      work.
    
    Defaults to "classes"

### clojure.core/true?

1.  Arities

    [x]

2.  Docs

    Returns true if x is the value true, false otherwise.

### clojure.core/release-pending-sends

1.  Arities

    []

2.  Docs

    Normally, actions sent directly or indirectly during another action
      are held until the action completes (changes the agent's
      state). This function can be used to dispatch any pending sent
      actions immediately. This has no impact on actions sent during a
      transaction, which are still held until commit. If no action is
      occurring, does nothing. Returns the number of actions dispatched.

### clojure.core/print

1.  Arities

    [& more]

2.  Docs

    Prints the object(s) to the output stream that is the current value
      of **out**.  print and println produce output for human consumption.

### clojure.core/empty

1.  Arities

    [coll]

2.  Docs

    Returns an empty collection of the same category as coll, or nil

### clojure.core/remove-method

1.  Arities

    [multifn dispatch-val]

2.  Docs

    Removes the method of multimethod associated with dispatch-value.

### clojure.core/\*in\*

1.  Arities

2.  Docs

    A java.io.Reader object representing standard input for read operations.
    
    Defaults to System/in, wrapped in a LineNumberingPushbackReader

### clojure.core/print-ctor

1.  Arities

    [o print-args w]

2.  Docs

    nil

### clojure.core//

1.  Arities

    [x]
    [x y]
    [x y & more]

2.  Docs

    If no denominators are supplied, returns 1/numerator,
      else returns numerator divided by all of the denominators.

### clojure.core/read-line

1.  Arities

    []

2.  Docs

    Reads the next line from stream that is the current value of **in** .

### clojure.core/bit-or

1.  Arities

    [x y]
    [x y & more]

2.  Docs

    Bitwise or

### clojure.core/clear-agent-errors

1.  Arities

    [a]

2.  Docs

    DEPRECATED: Use 'restart-agent' instead.
      Clears any exceptions thrown during asynchronous actions of the
      agent, allowing subsequent actions to occur.

### clojure.core/vector

1.  Arities

    []
    [a]
    [a b]
    [a b c]
    [a b c d]
    [a b c d & args]

2.  Docs

    Creates a new vector containing the args.

### clojure.core/>=

1.  Arities

    [x]
    [x y]
    [x y & more]

2.  Docs

    Returns non-nil if nums are in monotonically non-increasing order,
      otherwise false.

### clojure.core/drop-last

1.  Arities

    [s]
    [n s]

2.  Docs

    Return a lazy sequence of all but the last n (default 1) items in coll

### clojure.core/not-empty

1.  Arities

    [coll]

2.  Docs

    If coll is empty, returns nil, else coll

### clojure.core/distinct

1.  Arities

    [coll]

2.  Docs

    Returns a lazy sequence of the elements of coll with duplicates removed

### clojure.core/partition

1.  Arities

    [n coll]
    [n step coll]
    [n step pad coll]

2.  Docs

    Returns a lazy sequence of lists of n items each, at offsets step
      apart. If step is not supplied, defaults to n, i.e. the partitions
      do not overlap. If a pad collection is supplied, use its elements as
      necessary to complete last partition upto n items. In case there are
      not enough padding elements, return a partition with less than n items.

### clojure.core/add-classpath

1.  Arities

    [url]

2.  Docs

    DEPRECATED 
    
    Adds the url (String or URL object) to the classpath per
    URLClassLoader.addURL

### clojure.core/bit-flip

1.  Arities

    [x n]

2.  Docs

    Flip bit at index n

### clojure.core/long-array

1.  Arities

    [size-or-seq]
    [size init-val-or-seq]

2.  Docs

    Creates an array of longs

### clojure.core/descendants

1.  Arities

    [tag]
    [h tag]

2.  Docs

    Returns the immediate and indirect children of tag, through a
      relationship established via derive. h must be a hierarchy obtained
      from make-hierarchy, if not supplied defaults to the global
      hierarchy. Note: does not work on Java type inheritance
      relationships.

### clojure.core/merge

1.  Arities

    [& maps]

2.  Docs

    Returns a map that consists of the rest of the maps conj-ed onto
      the first.  If a key occurs in more than one map, the mapping from
      the latter (left-to-right) will be the mapping in the result.

### clojure.core/accessor

1.  Arities

    [s key]

2.  Docs

    Returns a fn that, given an instance of a structmap with the basis,
      returns the value at the key.  The key must be in the basis. The
      returned function should be (slightly) more efficient than using
      get, but such use of accessors should be limited to known
      performance-critical areas.

### clojure.core/integer?

1.  Arities

    [n]

2.  Docs

    Returns true if n is an integer

### clojure.core/mapv

1.  Arities

    [f coll]
    [f c1 c2]
    [f c1 c2 c3]
    [f c1 c2 c3 & colls]

2.  Docs

    Returns a vector consisting of the result of applying f to the
      set of first items of each coll, followed by applying f to the set
      of second items in each coll, until any one of the colls is
      exhausted.  Any remaining items in other colls are ignored. Function
      f should accept number-of-colls arguments.

### clojure.core/partition-all

1.  Arities

    [n coll]
    [n step coll]

2.  Docs

    Returns a lazy sequence of lists like partition, but may include
      partitions with fewer than n items at the end.

### clojure.core/partition-by

1.  Arities

    [f coll]

2.  Docs

    Applies f to each value in coll, splitting it each time f returns
       a new value.  Returns a lazy seq of partitions.

### clojure.core/numerator

1.  Arities

    [r]

2.  Docs

    Returns the numerator part of a Ratio.

### clojure.core/object-array

1.  Arities

    [size-or-seq]

2.  Docs

    Creates an array of objects

### clojure.core/derive

1.  Arities

    [tag parent]
    [h tag parent]

2.  Docs

    Establishes a parent/child relationship between parent and
      tag. Parent must be a namespace-qualified symbol or keyword and
      child can be either a namespace-qualified symbol or keyword or a
      class. h must be a hierarchy obtained from make-hierarchy, if not
      supplied defaults to, and modifies, the global hierarchy.

### clojure.core/load-string

1.  Arities

    [s]

2.  Docs

    Sequentially read and evaluate the set of forms contained in the
      string

### clojure.core/special-symbol?

1.  Arities

    [s]

2.  Docs

    Returns true if s names a special form

### clojure.core/ancestors

1.  Arities

    [tag]
    [h tag]

2.  Docs

    Returns the immediate and indirect parents of tag, either via a Java type
      inheritance relationship or a relationship established via derive. h
      must be a hierarchy obtained from make-hierarchy, if not supplied
      defaults to the global hierarchy

### clojure.core/subseq

1.  Arities

    [sc test key]
    [sc start-test start-key end-test end-key]

2.  Docs

    sc must be a sorted collection, test(s) one of <, <=, > or
      >=. Returns a seq of those entries with keys ek for
      which (test (.. sc comparator (compare ek key)) 0) is true

### clojure.core/error-handler

1.  Arities

    [a]

2.  Docs

    Returns the error-handler of agent a, or nil if there is none.
      See set-error-handler!

### clojure.core/gensym

1.  Arities

    []
    [prefix-string]

2.  Docs

    Returns a new symbol with a unique name. If a prefix string is
      supplied, the name is prefix# where # is some unique number. If
      prefix is not supplied, the prefix is 'G\_<sub>'</sub>.

### clojure.core/ratio?

1.  Arities

    [n]

2.  Docs

    Returns true if n is a Ratio

### clojure.core/delay?

1.  Arities

    [x]

2.  Docs

    returns true if x is a Delay created with delay

### clojure.core/intern

1.  Arities

    [ns name]
    [ns name val]

2.  Docs

    Finds or creates a var named by the symbol name in the namespace
      ns (which can be a symbol or a namespace), setting its root binding
      to val if supplied. The namespace must exist. The var will adopt any
      metadata from the name symbol.  Returns the var.

### clojure.core/print-simple

1.  Arities

    [o w]

2.  Docs

    nil

### clojure.core/flatten

1.  Arities

    [x]

2.  Docs

    Takes any nested combination of sequential things (lists, vectors,
      etc.) and returns their contents as a single, flat sequence.
      (flatten nil) returns an empty sequence.

### clojure.core/doubles

1.  Arities

    [xs]

2.  Docs

    Casts to double[]

### clojure.core/remove-watch

1.  Arities

    [reference key]

2.  Docs

    Removes a watch (set by add-watch) from a reference

### clojure.core/ex-info

1.  Arities

    [msg map]
    [msg map cause]

2.  Docs

    Create an instance of ExceptionInfo, a RuntimeException subclass
       that carries a map of additional data.

### clojure.core/ifn?

1.  Arities

    [x]

2.  Docs

    Returns true if x implements IFn. Note that many data structures
      (e.g. sets and maps) implement IFn

### clojure.core/proxy-name

1.  Arities

    [super interfaces]

2.  Docs

    nil

### clojure.core/ns-interns

1.  Arities

    [ns]

2.  Docs

    Returns a map of the intern mappings for the namespace.

### clojure.core/all-ns

1.  Arities

    []

2.  Docs

    Returns a sequence of all namespaces.

### clojure.core/find-protocol-method

1.  Arities

    [protocol methodk x]

2.  Docs

    nil

### clojure.core/subvec

1.  Arities

    [v start]
    [v start end]

2.  Docs

    Returns a persistent vector of the items in vector from
      start (inclusive) to end (exclusive).  If end is not supplied,
      defaults to (count vector). This operation is O(1) and very fast, as
      the resulting vector shares structure with the original and no
      trimming is done.

### clojure.core/partial

1.  Arities

    [f]
    [f arg1]
    [f arg1 arg2]
    [f arg1 arg2 arg3]
    [f arg1 arg2 arg3 & more]

2.  Docs

    Takes a function f and fewer than the normal arguments to f, and
      returns a fn that takes a variable number of additional args. When
      called, the returned function calls f with args + additional args.

### clojure.core/chunked-seq?

1.  Arities

    [s]

2.  Docs

    nil

### clojure.core/find-keyword

1.  Arities

    [name]
    [ns name]

2.  Docs

    Returns a Keyword with the given namespace and name if one already
      exists.  This function will not intern a new keyword. If the keyword
      has not already been interned, it will return nil.  Do not use :
      in the keyword strings, it will be added automatically.

### clojure.core/replicate

1.  Arities

    [n x]

2.  Docs

    DEPRECATED: Use 'repeat' instead.
       Returns a lazy seq of n xs.

### clojure.core/min-key

1.  Arities

    [k x]
    [k x y]
    [k x y & more]

2.  Docs

    Returns the x for which (k x), a number, is least.

### clojure.core/reduced

1.  Arities

    [x]

2.  Docs

    Wraps x in a way such that a reduce will terminate with the value x

### clojure.core/char-escape-string

1.  Arities

2.  Docs

    Returns escape string for char or nil if none

### clojure.core/re-matches

1.  Arities

    [re s]

2.  Docs

    Returns the match, if any, of string to pattern, using
      java.util.regex.Matcher.matches().  Uses re-groups to return the
      groups.

### clojure.core/array-map

1.  Arities

    []
    [& keyvals]

2.  Docs

    Constructs an array-map. If any keys are equal, they are handled as
      if by repeated uses of assoc.

### clojure.core/unchecked-byte

1.  Arities

    [x]

2.  Docs

    Coerce to byte. Subject to rounding or truncation.

### clojure.core/ns-imports

1.  Arities

    [ns]

2.  Docs

    Returns a map of the import mappings for the namespace.

### clojure.core/send-off

1.  Arities

    [a f & args]

2.  Docs

    Dispatch a potentially blocking action to an agent. Returns the
      agent immediately. Subsequently, in a separate thread, the state of
      the agent will be set to the value of:
    
    (apply action-fn state-of-agent args)

### clojure.core/every-pred

1.  Arities

    [p]
    [p1 p2]
    [p1 p2 p3]
    [p1 p2 p3 & ps]

2.  Docs

    Takes a set of predicates and returns a function f that returns true if all of its
      composing predicates return a logical true value against all of its arguments, else it returns
      false. Note that f is short-circuiting in that it will stop execution on the first
      argument that triggers a logical false result against the original predicates.

### clojure.core/keys

1.  Arities

    [map]

2.  Docs

    Returns a sequence of the map's keys, in the same order as (seq map).

### clojure.core/rationalize

1.  Arities

    [num]

2.  Docs

    returns the rational value of num

### clojure.core/load-file

1.  Arities

    [name]

2.  Docs

    Sequentially read and evaluate the set of forms contained in the file.

### clojure.core/distinct?

1.  Arities

    [x]
    [x y]
    [x y & more]

2.  Docs

    Returns true if no two of the arguments are =

### clojure.core/extenders

1.  Arities

    [protocol]

2.  Docs

    Returns a collection of the types explicitly extending protocol

### clojure.core/unchecked-short

1.  Arities

    [x]

2.  Docs

    Coerce to short. Subject to rounding or truncation.

### clojure.core/methods

1.  Arities

    [multifn]

2.  Docs

    Given a multimethod, returns a map of dispatch values -> dispatch fns

### clojure.core/odd?

1.  Arities

    [n]

2.  Docs

    Returns true if n is odd, throws an exception if n is not an integer

### clojure.core/->ArrayChunk

1.  Arities

    [am arr off end]

2.  Docs

    Positional factory function for class clojure.core.ArrayChunk.

### clojure.core/float-array

1.  Arities

    [size-or-seq]
    [size init-val-or-seq]

2.  Docs

    Creates an array of floats

### clojure.core/\*3

1.  Arities

2.  Docs

    bound in a repl thread to the third most recent value printed

### clojure.core/alias

1.  Arities

    [alias namespace-sym]

2.  Docs

    Add an alias in the current namespace to another
      namespace. Arguments are two symbols: the alias to be used, and
      the symbolic name of the target namespace. Use :as in the ns macro in preference
      to calling this directly.

### clojure.core/frequencies

1.  Arities

    [coll]

2.  Docs

    Returns a map from distinct items in coll to the number of times
      they appear.

### clojure.core/read-string

1.  Arities

    [s]

2.  Docs

    Reads one object from the string s.
    
    Note that read-string can execute code (controlled by **read-eval**),
    and as such should be used only with trusted sources.
    
    For data structure interop use clojure.edn/read-string

### clojure.core/rsubseq

1.  Arities

    [sc test key]
    [sc start-test start-key end-test end-key]

2.  Docs

    sc must be a sorted collection, test(s) one of <, <=, > or
      >=. Returns a reverse seq of those entries with keys ek for
      which (test (.. sc comparator (compare ek key)) 0) is true

### clojure.core/inc

1.  Arities

    [x]

2.  Docs

    Returns a number one greater than num. Does not auto-promote
      longs, will throw on overflow. See also: inc'

### clojure.core/get-method

1.  Arities

    [multifn dispatch-val]

2.  Docs

    Given a multimethod and a dispatch value, returns the dispatch fn
      that would apply to that value, or nil if none apply and no default

### clojure.core/bit-clear

1.  Arities

    [x n]

2.  Docs

    Clear bit at index n

### clojure.core/filter

1.  Arities

    [pred coll]

2.  Docs

    Returns a lazy sequence of the items in coll for which
      (pred item) returns true. pred must be free of side-effects.

### clojure.core/list

1.  Arities

    [& items]

2.  Docs

    Creates a new list containing the items.

### clojure.core/+

1.  Arities

    []
    [x]
    [x y]
    [x y & more]

2.  Docs

    Returns the sum of nums. (+) returns 0. Does not auto-promote
      longs, will throw on overflow. See also: +'

### clojure.core/split-with

1.  Arities

    [pred coll]

2.  Docs

    Returns a vector of [(take-while pred coll) (drop-while pred coll)]

### clojure.core/aset

1.  Arities

    [array idx val]
    [array idx idx2 & idxv]

2.  Docs

    Sets the value at the index/indices. Works on Java arrays of
      reference types. Returns val.

### clojure.core/->VecNode

1.  Arities

    [edit arr]

2.  Docs

    Positional factory function for class clojure.core.VecNode.

### clojure.core/keyword

1.  Arities

    [name]
    [ns name]

2.  Docs

    Returns a Keyword with the given namespace and name.  Do not use :
      in the keyword strings, it will be added automatically.

### clojure.core/\*ns\*

1.  Arities

2.  Docs

    A clojure.lang.Namespace object representing the current namespace.

### clojure.core/destructure

1.  Arities

    [bindings]

2.  Docs

    nil

### clojure.core/\*assert\*

1.  Arities

2.  Docs

    nil

### clojure.core/chars

1.  Arities

    [xs]

2.  Docs

    Casts to chars[]

### clojure.core/str

1.  Arities

    []
    [x]
    [x & ys]

2.  Docs

    With no args, returns the empty string. With one arg x, returns
      x.toString().  (str nil) returns the empty string. With more than
      one arg, returns the concatenation of the str values of the args.

### clojure.core/next

1.  Arities

    [coll]

2.  Docs

    Returns a seq of the items after the first. Calls seq on its
      argument.  If there are no more items, returns nil.

### clojure.core/hash-map

1.  Arities

    []
    [& keyvals]

2.  Docs

    keyval => key val
      Returns a new hash map with supplied mappings.  If any keys are
      equal, they are handled as if by repeated uses of assoc.

### clojure.core/underive

1.  Arities

    [tag parent]
    [h tag parent]

2.  Docs

    Removes a parent/child relationship between parent and
      tag. h must be a hierarchy obtained from make-hierarchy, if not
      supplied defaults to, and modifies, the global hierarchy.

### clojure.core/ref-max-history

1.  Arities

    [ref]
    [ref n]

2.  Docs

    Gets the max-history of a ref, or sets it and returns the ref

### clojure.core/false?

1.  Arities

    [x]

2.  Docs

    Returns true if x is the value false, false otherwise.

### clojure.core/\*print-readably\*

1.  Arities

2.  Docs

    When set to logical false, strings and characters will be printed with
      non-alphanumeric characters converted to the appropriate escape sequences.
    
    Defaults to true

### clojure.core/ints

1.  Arities

    [xs]

2.  Docs

    Casts to int[]

### clojure.core/class

1.  Arities

    [x]

2.  Docs

    Returns the Class of x

### clojure.core/some-fn

1.  Arities

    [p]
    [p1 p2]
    [p1 p2 p3]
    [p1 p2 p3 & ps]

2.  Docs

    Takes a set of predicates and returns a function f that returns the first logical true value
      returned by one of its composing predicates against any of its arguments, else it returns
      logical false. Note that f is short-circuiting in that it will stop execution on the first
      argument that triggers a logical true result against the original predicates.

### clojure.core/\*flush-on-newline\*

1.  Arities

2.  Docs

    When set to true, output will be flushed whenever a newline is printed.
    
    Defaults to true.

### clojure.core/to-array

1.  Arities

    [coll]

2.  Docs

    Returns an array of Objects containing the contents of coll, which
      can be any Collection.  Maps to java.util.Collection.toArray().

### clojure.core/bigdec

1.  Arities

    [x]

2.  Docs

    Coerce to BigDecimal

### clojure.core/list?

1.  Arities

    [x]

2.  Docs

    Returns true if x implements IPersistentList

### clojure.core/bit-not

1.  Arities

    [x]

2.  Docs

    Bitwise complement

### clojure.core/xml-seq

1.  Arities

    [root]

2.  Docs

    A tree seq on the xml elements as per xml/parse

### clojure.core/byte

1.  Arities

    [x]

2.  Docs

    Coerce to byte

### clojure.core/max

1.  Arities

    [x]
    [x y]
    [x y & more]

2.  Docs

    Returns the greatest of the nums.

### clojure.core/==

1.  Arities

    [x]
    [x y]
    [x y & more]

2.  Docs

    Returns non-nil if nums all have the equivalent
      value (type-independent), otherwise false

### clojure.core/\*agent\*

1.  Arities

2.  Docs

    The agent currently running an action on this thread, else nil

### clojure.core/parents

1.  Arities

    [tag]
    [h tag]

2.  Docs

    Returns the immediate parents of tag, either via a Java type
      inheritance relationship or a relationship established via derive. h
      must be a hierarchy obtained from make-hierarchy, if not supplied
      defaults to the global hierarchy

### clojure.core/count

1.  Arities

    [coll]

2.  Docs

    Returns the number of items in the collection. (count nil) returns
    1.  Also works on strings, arrays, and Java Collections and Maps

### clojure.core/supers

1.  Arities

    [class]

2.  Docs

    Returns the immediate and indirect superclasses and interfaces of c, if any

### clojure.core/\*fn-loader\*

1.  Arities

2.  Docs

    nil

### clojure.core/sorted-map-by

1.  Arities

    [comparator & keyvals]

2.  Docs

    keyval => key val
      Returns a new sorted map with supplied mappings, using the supplied
      comparator.  If any keys are equal, they are handled as if by
      repeated uses of assoc.

### clojure.core/apply

1.  Arities

    [f args]
    [f x args]
    [f x y args]
    [f x y z args]
    [f a b c d & args]

2.  Docs

    Applies fn f to the argument list formed by prepending intervening arguments to args.

### clojure.core/interpose

1.  Arities

    [sep coll]

2.  Docs

    Returns a lazy seq of the elements of coll separated by sep

### clojure.core/deref

1.  Arities

    [ref]
    [ref timeout-ms timeout-val]

2.  Docs

    Also reader macro: @ref/@agent/@var/@atom/@delay/@future/@promise. Within a transaction,
      returns the in-transaction-value of ref, else returns the
      most-recently-committed value of ref. When applied to a var, agent
      or atom, returns its current state. When applied to a delay, forces
      it if not already forced. When applied to a future, will block if
      computation not complete. When applied to a promise, will block
      until a value is delivered.  The variant taking a timeout can be
      used for blocking references (futures and promises), and will return
      timeout-val if the timeout (in milliseconds) is reached before a
      value is available. See also - realized?.

### clojure.core/assoc

1.  Arities

    [map key val]
    [map key val & kvs]

2.  Docs

    assoc[iate]. When applied to a map, returns a new map of the
        same (hashed/sorted) type, that contains the mapping of key(s) to
        val(s). When applied to a vector, returns a new vector that
        contains val at index. Note - index must be <= (count vector).

### clojure.core/rational?

1.  Arities

    [n]

2.  Docs

    Returns true if n is a rational number

### clojure.core/transient

1.  Arities

    [coll]

2.  Docs

    Returns a new, transient version of the collection, in constant time.

### clojure.core/clojure-version

1.  Arities

    []

2.  Docs

    Returns clojure version as a printable string.

### clojure.core/chunk-cons

1.  Arities

    [chunk rest]

2.  Docs

    nil

### clojure.core/comparator

1.  Arities

    [pred]

2.  Docs

    Returns an implementation of java.util.Comparator based upon pred.

### clojure.core/sorted-map

1.  Arities

    [& keyvals]

2.  Docs

    keyval => key val
      Returns a new sorted map with supplied mappings.  If any keys are
      equal, they are handled as if by repeated uses of assoc.

### clojure.core/send

1.  Arities

    [a f & args]

2.  Docs

    Dispatch an action to an agent. Returns the agent immediately.
      Subsequently, in a thread from a thread pool, the state of the agent
      will be set to the value of:
    
    (apply action-fn state-of-agent args)

### clojure.core/drop-while

1.  Arities

    [pred coll]

2.  Docs

    Returns a lazy sequence of the items in coll starting from the first
      item for which (pred item) returns logical false.

### clojure.core/proxy-call-with-super

1.  Arities

    [call this meth]

2.  Docs

    nil

### clojure.core/realized?

1.  Arities

    [x]

2.  Docs

    Returns true if a value has been produced for a promise, delay, future or lazy sequence.

### clojure.core/char-array

1.  Arities

    [size-or-seq]
    [size init-val-or-seq]

2.  Docs

    Creates an array of chars

### clojure.core/resolve

1.  Arities

    [sym]
    [env sym]

2.  Docs

    same as (ns-resolve **ns** symbol) or (ns-resolve **ns** &env symbol)

### clojure.core/compare

1.  Arities

    [x y]

2.  Docs

    Comparator. Returns a negative number, zero, or a positive number
      when x is logically 'less than', 'equal to', or 'greater than'
      y. Same as Java x.compareTo(y) except it also works for nil, and
      compares numbers and collections in a type-independent manner. x
      must implement Comparable

### clojure.core/complement

1.  Arities

    [f]

2.  Docs

    Takes a fn f and returns a fn that takes the same arguments as f,
      has the same effects, if any, and returns the opposite truth value.

### clojure.core/\*compiler-options\*

1.  Arities

2.  Docs

    A map of keys to options.
      Note, when binding dynamically make sure to merge with previous value.
      Supported options:
    
    :elide-meta - a collection of metadata keys to elide during compilation.
    
    :disable-locals-clearing - set to true to disable clearing, useful for using a debugger
    Alpha, subject to change.

### clojure.core/\*print-dup\*

1.  Arities

2.  Docs

    When set to logical true, objects will be printed in a way that preserves
      their type when read in later.
    
    Defaults to false.

### clojure.core/with-redefs-fn

1.  Arities

    [binding-map func]

2.  Docs

    Temporarily redefines Vars during a call to func.  Each val of
      binding-map will replace the root value of its key which must be
      a Var.  After func is called with no args, the root values of all
      the Vars will be set back to their old values.  These temporary
      changes will be visible in all threads.  Useful for mocking out
      functions during testing.

### clojure.core/sequence

1.  Arities

    [coll]

2.  Docs

    Coerces coll to a (possibly empty) sequence, if it is not already
      one. Will not force a lazy seq. (sequence nil) yields ()

### clojure.core/constantly

1.  Arities

    [x]

2.  Docs

    Returns a function that takes any number of arguments and returns x.

### clojure.core/get-proxy-class

1.  Arities

    [& bases]

2.  Docs

    Takes an optional single class followed by zero or more
      interfaces. If not supplied class defaults to Object.  Creates an
      returns an instance of a proxy class derived from the supplied
      classes. The resulting value is cached and used for any subsequent
      requests for the same class set. Returns a Class object.

### clojure.core/make-array

1.  Arities

    [type len]
    [type dim & more-dims]

2.  Docs

    Creates and returns an array of instances of the specified class of
      the specified dimension(s).  Note that a class object is required.
      Class objects can be obtained by using their imported or
      fully-qualified name.  Class objects for the primitive types can be
      obtained using, e.g., Integer/TYPE.

### clojure.core/shorts

1.  Arities

    [xs]

2.  Docs

    Casts to shorts[]

### clojure.core/update-proxy

1.  Arities

    [proxy mappings]

2.  Docs

    Takes a proxy instance and a map of strings (which must
      correspond to methods of the proxy superclass/superinterfaces) to
      fns (which must take arguments matching the corresponding method,
      plus an additional (explicit) first arg corresponding to this, and
      updates (via assoc) the proxy's fn map. nil can be passed instead of
      a fn, in which case the corresponding method will revert to the
      default behavior. Note that this function can be used to update the
      behavior of an existing instance without changing its identity.
      Returns the proxy.

### clojure.core/unchecked-negate-int

1.  Arities

    [x]

2.  Docs

    Returns the negation of x, an int.
      Note - uses a primitive operator subject to overflow.

### clojure.core/hash-unordered-coll

1.  Arities

    [coll]

2.  Docs

    Returns the hash code, consistent with =, for an external unordered
       collection implementing Iterable. For maps, the iterator should
       return map entries whose hash is computed as
         (hash-ordered-coll [k v]).
       See <http://clojure.org/data_structures#hash> for full algorithms.

### clojure.core/repeat

1.  Arities

    [x]
    [n x]

2.  Docs

    Returns a lazy (infinite!, or length n if supplied) sequence of xs.

### clojure.core/unchecked-inc

1.  Arities

    [x]

2.  Docs

    Returns a number one greater than x, a long.
      Note - uses a primitive operator subject to overflow.

### clojure.core/nthnext

1.  Arities

    [coll n]

2.  Docs

    Returns the nth next of coll, (seq coll) when n is 0.

### clojure.core/create-struct

1.  Arities

    [& keys]

2.  Docs

    Returns a structure basis object.

### clojure.core/get-validator

1.  Arities

    [iref]

2.  Docs

    Gets the validator-fn for a var/ref/agent/atom.

### clojure.core/number?

1.  Arities

    [x]

2.  Docs

    Returns true if x is a Number

### clojure.core/await-for

1.  Arities

    [timeout-ms & agents]

2.  Docs

    Blocks the current thread until all actions dispatched thus
      far (from this thread or agent) to the agents have occurred, or the
      timeout (in milliseconds) has elapsed. Returns logical false if
      returning due to timeout, logical true otherwise.

### clojure.core/chunk-next

1.  Arities

    [s]

2.  Docs

    nil

### clojure.core/print-str

1.  Arities

    [& xs]

2.  Docs

    print to a string, returning it

### clojure.core/not-any?

1.  Arities

    [pred coll]

2.  Docs

    Returns false if (pred x) is logical true for any x in coll,
      else true.

### clojure.core/into-array

1.  Arities

    [aseq]
    [type aseq]

2.  Docs

    Returns an array with components set to the values in aseq. The array's
      component type is type if provided, or the type of the first value in
      aseq if present, or Object. All values in aseq must be compatible with
      the component type. Class objects for the primitive types can be obtained
      using, e.g., Integer/TYPE.

### clojure.core/init-proxy

1.  Arities

    [proxy mappings]

2.  Docs

    Takes a proxy instance and a map of strings (which must
      correspond to methods of the proxy superclass/superinterfaces) to
      fns (which must take arguments matching the corresponding method,
      plus an additional (explicit) first arg corresponding to this, and
      sets the proxy's fn map.  Returns the proxy.

### clojure.core/chunk-buffer

1.  Arities

    [capacity]

2.  Docs

    nil

### clojure.core/symbol?

1.  Arities

    [x]

2.  Docs

    Return true if x is a Symbol

### clojure.core/unchecked-char

1.  Arities

    [x]

2.  Docs

    Coerce to char. Subject to rounding or truncation.

### clojure.core/future-cancel

1.  Arities

    [f]

2.  Docs

    Cancels the future, if possible.

### clojure.core/var-get

1.  Arities

    [x]

2.  Docs

    Gets the value in the var object

### clojure.core/commute

1.  Arities

    [ref fun & args]

2.  Docs

    Must be called in a transaction. Sets the in-transaction-value of
      ref to:
    
    (apply fun in-transaction-value-of-ref args)
    
    and returns the in-transaction-value of ref.
    
    At the commit point of the transaction, sets the value of ref to be:
    
    (apply fun most-recently-committed-value-of-ref args)
    
    Thus fun should be commutative, or, failing that, you must accept
    last-one-in-wins behavior.  commute allows for more concurrency than
    ref-set.

### clojure.core/coll?

1.  Arities

    [x]

2.  Docs

    Returns true if x implements IPersistentCollection

### clojure.core/get-in

1.  Arities

    [m ks]
    [m ks not-found]

2.  Docs

    Returns the value in a nested associative structure,
      where ks is a sequence of keys. Returns nil if the key
      is not present, or the not-found value if supplied.

### clojure.core/fnext

1.  Arities

    [x]

2.  Docs

    Same as (first (next x))

### clojure.core/denominator

1.  Arities

    [r]

2.  Docs

    Returns the denominator part of a Ratio.

### clojure.core/bytes

1.  Arities

    [xs]

2.  Docs

    Casts to bytes[]

# clojure.data

## Macros

## Functions

### clojure.data/Diff

1.  Arities

2.  Docs

    Implementation detail. Subject to change.

### clojure.data/diff-similar

1.  Arities

    [a b]

2.  Docs

    Implementation detail. Subject to change.

### clojure.data/EqualityPartition

1.  Arities

2.  Docs

    Implementation detail. Subject to change.

### clojure.data/equality-partition

1.  Arities

    [x]

2.  Docs

    Implementation detail. Subject to change.

### clojure.data/diff

1.  Arities

    [a b]

2.  Docs

    Recursively compares a and b, returning a tuple of
      [things-only-in-a things-only-in-b things-in-both].
      Comparison rules:
    -   For equal a and b, return [nil nil a].
    -   Maps are subdiffed where keys match and values differ.
    -   Sets are never subdiffed.
    -   All sequential things are treated as associative collections
        by their indexes, with results returned as vectors.
    -   Everything else (including strings!) is treated as
        an atom and compared for equality.

# clojure.edn

## Macros

## Functions

### clojure.edn/read-string

1.  Arities

    [s]
    [opts s]

2.  Docs

    Reads one object from the string s. Returns nil when s is nil or empty.
    
    Reads data in the edn format (subset of Clojure data):
    <http://edn-format.org>
    
    opts is a map as per clojure.edn/read

### clojure.edn/read

1.  Arities

    []
    [stream]
    [opts stream]

2.  Docs

    Reads the next object from stream, which must be an instance of
      java.io.PushbackReader or some derivee.  stream defaults to the
      current value of **in**.
    
    Reads data in the edn format (subset of Clojure data):
    <http://edn-format.org>
    
    opts is a map that can include the following keys:
    
    :eof - value to return on end-of-file. When not supplied, eof throws an exception.
    
    :readers  - a map of tag symbols to data-reader functions to be considered before default-data-readers.
                When not supplied, only the default-data-readers will be used.
    
    :default - A function of two args, that will, if present and no reader is found for a tag,
               be called with the tag and the value.

# clojure.instant

## Macros

## Functions

### clojure.instant/parse-timestamp

1.  Arities

2.  Docs

    Parse a string containing an RFC3339-like like timestamp.
    
    The function new-instant is called with the following arguments.
    
                  min  max           default
                  &#x2014;  &#x2014;&#x2014;&#x2014;&#x2014;  -&#x2014;&#x2014;
    years          0           9999      N/A (s must provide years)
    months         1             12        1
    days           1             31        1 (actual max days depends
    hours          0             23        0  on month and year)
    minutes        0             59        0
    seconds        0             60        0 (though 60 is only valid
    nanoseconds    0      999999999        0  when minutes is 59)
    offset-sign   -1              1        0
    offset-hours   0             23        0
    offset-minutes 0             59        0
    
    These are all integers and will be non-nil. (The listed defaults
    will be passed if the corresponding field is not present in s.)
    
    Grammar (of s):
    
    date-fullyear   = 4DIGIT
    date-month      = 2DIGIT  ; 01-12
    date-mday       = 2DIGIT  ; 01-28, 01-29, 01-30, 01-31 based on
                              ; month/year
    time-hour       = 2DIGIT  ; 00-23
    time-minute     = 2DIGIT  ; 00-59
    time-second     = 2DIGIT  ; 00-58, 00-59, 00-60 based on leap second
                              ; rules
    time-secfrac    = '.' 1\*DIGIT
    time-numoffset  = ('+' / '-') time-hour ':' time-minute
    time-offset     = 'Z' / time-numoffset
    
    time-part       = time-hour [ ':' time-minute [ ':' time-second
                      [time-secfrac] [time-offset] ] ]
    
    timestamp       = date-year [ '-' date-month [ '-' date-mday
                      [ 'T' time-part ] ] ]
    
    Unlike RFC3339:
    
    -   we only parse the timestamp format
    -   timestamp can elide trailing components
    -   time-offset is optional (defaults to +00:00)
    
    Though time-offset is syntactically optional, a missing time-offset
    will be treated as if the time-offset zero (+00:00) had been
    specified.

### clojure.instant/read-instant-date

1.  Arities

2.  Docs

    To read an instant as a java.util.Date, bind **data-readers** to a map with
    this var as the value for the 'inst key. The timezone offset will be used
    to convert into UTC.

### clojure.instant/read-instant-calendar

1.  Arities

2.  Docs

    To read an instant as a java.util.Calendar, bind **data-readers** to a map with
    this var as the value for the 'inst key.  Calendar preserves the timezone
    offset.

### clojure.instant/read-instant-timestamp

1.  Arities

2.  Docs

    To read an instant as a java.sql.Timestamp, bind **data-readers** to a
    map with this var as the value for the 'inst key. Timestamp preserves
    fractional seconds with nanosecond precision. The timezone offset will
    be used to convert into UTC.

### clojure.instant/validated

1.  Arities

    [new-instance]

2.  Docs

    Return a function which constructs and instant by calling constructor
    after first validating that those arguments are in range and otherwise
    plausible. The resulting function will throw an exception if called
    with invalid arguments.

# clojure.pprint

## Macros

### clojure.pprint/print-length-loop

1.  Arities

    [bindings & body]

2.  Docs

    A version of loop that iterates at most **print-length** times. This is designed 
    for use in pretty-printer dispatch functions.

### clojure.pprint/pprint-logical-block

1.  Arities

    [options\* body]

2.  Docs

    Execute the body as a pretty printing logical block with output to **out** which 
    must be a pretty printing writer. When used from pprint or cl-format, this can be 
    assumed. 
    
    This function is intended for use when writing custom dispatch functions.
    
    Before the body, the caller can optionally specify options: :prefix, :per-line-prefix, 
    and :suffix.

### clojure.pprint/pp

1.  Arities

    []

2.  Docs

    A convenience macro that pretty prints the last thing output. This is
    exactly equivalent to (pprint \*1).

### clojure.pprint/formatter-out

1.  Arities

    [format-in]

2.  Docs

    Makes a function which can directly run format-in. The function is
    fn [& args] &#x2026; and returns nil. This version of the formatter macro is
    designed to be used with **out** set to an appropriate Writer. In particular,
    this is meant to be used as part of a pretty printer dispatch method.
    
    format-in can be either a control string or a previously compiled format.

### clojure.pprint/formatter

1.  Arities

    [format-in]

2.  Docs

    Makes a function which can directly run format-in. The function is
    fn [stream & args] &#x2026; and returns nil unless the stream is nil (meaning 
    output to a string) in which case it returns the resulting string.
    
    format-in can be either a control string or a previously compiled format.

### clojure.pprint/with-pprint-dispatch

1.  Arities

    [function & body]

2.  Docs

    Execute body with the pretty print dispatch function bound to function.

## Functions

### clojure.pprint/pprint

1.  Arities

    [object]
    [object writer]

2.  Docs

    Pretty print object to the optional output writer. If the writer is not provided, 
    print the object to the currently bound value of **out**.

### clojure.pprint/simple-dispatch

1.  Arities

    [object]

2.  Docs

    The pretty print dispatch function for simple data structure format.

### clojure.pprint/get-pretty-writer

1.  Arities

    [writer]

2.  Docs

    Returns the java.io.Writer passed in wrapped in a pretty writer proxy, unless it's 
    already a pretty writer. Generally, it is unnecessary to call this function, since pprint,
    write, and cl-format all call it if they need to. However if you want the state to be 
    preserved across calls, you will want to wrap them with this. 
    
    For example, when you want to generate column-aware output with multiple calls to cl-format, 
    do it like in this example:
    
    (defn print-table [aseq column-width]
      (binding [\*out\* (get-pretty-writer **out**)]
        (doseq [row aseq]
          (doseq [col row]
            (cl-format true "~4D~7,vT" col column-width))
          (prn))))
    
    Now when you run:
    
    user> (print-table (map #(vector % (\* % %) (\* % % %)) (range 1 11)) 8)
    
    It prints a table of squares and cubes for the numbers from 1 to 10:
    
     1      1       1    
     2      4       8    
     3      9      27    
     4     16      64    
     5     25     125    
     6     36     216    
     7     49     343    
     8     64     512    
     9     81     729    
    10    100    1000

### clojure.pprint/\*print-suppress-namespaces\*

1.  Arities

2.  Docs

    Don't print namespaces with symbols. This is particularly useful when 
    pretty printing the results of macro expansions

### clojure.pprint/\*print-pretty\*

1.  Arities

2.  Docs

    Bind to true if you want write to use pretty printing

### clojure.pprint/\*print-pprint-dispatch\*

1.  Arities

2.  Docs

    The pretty print dispatch function. Use with-pprint-dispatch or set-pprint-dispatch
    to modify.

### clojure.pprint/pprint-newline

1.  Arities

    [kind]

2.  Docs

    Print a conditional newline to a pretty printing stream. kind specifies if the 
    newline is :linear, :miser, :fill, or :mandatory. 
    
    This function is intended for use when writing custom dispatch functions.
    
    Output is sent to **out** which must be a pretty printing writer.

### clojure.pprint/code-dispatch

1.  Arities

    [object]

2.  Docs

    The pretty print dispatch function for pretty printing Clojure code.

### clojure.pprint/pprint-tab

1.  Arities

    [kind colnum colinc]

2.  Docs

    Tab at this point in the pretty printing stream. kind specifies whether the tab
    is :line, :section, :line-relative, or :section-relative. 
    
    Colnum and colinc specify the target column and the increment to move the target
    forward if the output is already past the original target.
    
    This function is intended for use when writing custom dispatch functions.
    
    Output is sent to **out** which must be a pretty printing writer.
    
    THIS FUNCTION IS NOT YET IMPLEMENTED.

### clojure.pprint/print-table

1.  Arities

    [ks rows]
    [rows]

2.  Docs

    Prints a collection of maps in a textual table. Prints table headings
       ks, and then a line of output for each row, corresponding to the keys
       in ks. If ks are not specified, use the keys of the first item in rows.

### clojure.pprint/set-pprint-dispatch

1.  Arities

    [function]

2.  Docs

    Set the pretty print dispatch function to a function matching (fn [obj] &#x2026;)
    where obj is the object to pretty print. That function will be called with **out** set
    to a pretty printing writer to which it should do its printing.
    
    For example functions, see simple-dispatch and code-dispatch in 
    clojure.pprint.dispatch.clj.

### clojure.pprint/fresh-line

1.  Arities

    []

2.  Docs

    Make a newline if **out** is not already at the beginning of the line. If **out** is
    not a pretty writer (which keeps track of columns), this function always outputs a newline.

### clojure.pprint/pprint-indent

1.  Arities

    [relative-to n]

2.  Docs

    Create an indent at this point in the pretty printing stream. This defines how 
    following lines are indented. relative-to can be either :block or :current depending 
    whether the indent should be computed relative to the start of the logical block or
    the current column position. n is an offset. 
    
    This function is intended for use when writing custom dispatch functions.
    
    Output is sent to **out** which must be a pretty printing writer.

### clojure.pprint/\*print-radix\*

1.  Arities

2.  Docs

    Print a radix specifier in front of integers and rationals. If **print-base** is 2, 8, 
    or 16, then the radix specifier used is #b, #o, or #x, respectively. Otherwise the 
    radix specifier is in the form #XXr where XX is the decimal value of **print-base** 

### clojure.pprint/cl-format

1.  Arities

    [writer format-in & args]

2.  Docs

    An implementation of a Common Lisp compatible format function. cl-format formats its
    arguments to an output stream or string based on the format control string given. It 
    supports sophisticated formatting of structured data.
    
    Writer is an instance of java.io.Writer, true to output to **out** or nil to output 
    to a string, format-in is the format control string and the remaining arguments 
    are the data to be formatted.
    
    The format control string is a string to be output with embedded 'format directives' 
    describing how to format the various arguments passed in.
    
    If writer is nil, cl-format returns the formatted result string. Otherwise, cl-format 
    returns nil.
    
    For example:
     (let [results [46 38 22]]
            (cl-format true "There `[are`;is~:;are~]~:\* `d result`:p: ~{~d~^, ~}~%" 
                       (count results) results))
    
    Prints to **out**:
     There are 3 results: 46, 38, 22
    
    Detailed documentation on format control strings is available in the "Common Lisp the 
    Language, 2nd edition", Chapter 22 (available online at:
    <http://www.cs.cmu.edu/afs/cs.cmu.edu/project/ai-repository/ai/html/cltl/clm/node200.html#SECTION002633000000000000000>) 
    and in the Common Lisp HyperSpec at 
    <http://www.lispworks.com/documentation/HyperSpec/Body/22_c.htm>

### clojure.pprint/\*print-miser-width\*

1.  Arities

2.  Docs

    The column at which to enter miser style. Depending on the dispatch table, 
    miser style add newlines in more places to try to keep lines short allowing for further 
    levels of nesting.

### clojure.pprint/write

1.  Arities

    [object & kw-args]

2.  Docs

    Write an object subject to the current bindings of the printer control variables.
    Use the kw-args argument to override individual variables for this call (and any 
    recursive calls). Returns the string result if :stream is nil or nil otherwise.
    
    The following keyword arguments can be passed with values:
      Keyword              Meaning                              Default value
    
    :stream              Writer for output or nil             true (indicates **out**)
    
    :base                Base to use for writing rationals    Current value of **print-base**
    
    :circle\*             If true, mark circular structures    Current value of **print-circle**
    
    :length              Maximum elements to show in sublists Current value of **print-length**
    
    :level               Maximum depth                        Current value of **print-level**
    
    :lines\*              Maximum lines of output              Current value of **print-lines**
    
    :miser-width         Width to enter miser mode            Current value of **print-miser-width**
    
    :dispatch            The pretty print dispatch function   Current value of **print-pprint-dispatch**
    
    :pretty              If true, do pretty printing          Current value of **print-pretty**
    
    :radix               If true, prepend a radix specifier   Current value of **print-radix**
    
    :readably\*           If true, print readably              Current value of **print-readably**
    
    :right-margin        The column for the right margin      Current value of **print-right-margin**
    
    :suppress-namespaces If true, no namespaces in symbols    Current value of **print-suppress-namespaces**
    
    -   = not yet supported

### clojure.pprint/\*print-right-margin\*

1.  Arities

2.  Docs

    Pretty printing will try to avoid anything going beyond this column.
    Set it to nil to have pprint let the line be arbitrarily long. This will ignore all 
    non-mandatory newlines.

### clojure.pprint/write-out

1.  Arities

    [object]

2.  Docs

    Write an object to **out** subject to the current bindings of the printer control 
    variables. Use the kw-args argument to override individual variables for this call (and 
    any recursive calls).
    
    **out** must be a PrettyWriter if pretty printing is enabled. This is the responsibility
    of the caller.
    
    This method is primarily intended for use by pretty print dispatch functions that 
    already know that the pretty printer will have set up their environment appropriately.
    Normal library clients should use the standard "write" interface. 

### clojure.pprint/\*print-base\*

1.  Arities

2.  Docs

    The base to use for printing integers and rationals.

# clojure.reflect

## Macros

## Functions

### clojure.reflect/->Field

1.  Arities

    [name type declaring-class flags]

2.  Docs

    Positional factory function for class clojure.reflect.Field.

### clojure.reflect/->Method

1.  Arities

    [name return-type declaring-class parameter-types exception-types flags]

2.  Docs

    Positional factory function for class clojure.reflect.Method.

### clojure.reflect/TypeReference

1.  Arities

2.  Docs

    A TypeReference can be unambiguously converted to a type name on
       the host platform.
    
    All typerefs are normalized into symbols. If you need to
    normalize a typeref yourself, call typesym.

### clojure.reflect/reflect

1.  Arities

    [obj & options]

2.  Docs

    Alpha - subject to change.
       Reflect on the type of obj (or obj itself if obj is a class).
       Return value and options are the same as for type-reflect. 

### clojure.reflect/map->Field

1.  Arities

    [m\_<sub>5869</sub>\_<sub>auto</sub>\_<sub>]</sub>

2.  Docs

    Factory function for class clojure.reflect.Field, taking a map of keywords to field values.

### clojure.reflect/map->Method

1.  Arities

    [m\_<sub>5869</sub>\_<sub>auto</sub>\_<sub>]</sub>

2.  Docs

    Factory function for class clojure.reflect.Method, taking a map of keywords to field values.

### clojure.reflect/typename

1.  Arities

    [o]

2.  Docs

    Returns Java name as returned by ASM getClassName, e.g. byte[], java.lang.String[]

### clojure.reflect/->JavaReflector

1.  Arities

    [classloader]

2.  Docs

    Positional factory function for class clojure.reflect.JavaReflector.

### clojure.reflect/->AsmReflector

1.  Arities

    [class-resolver]

2.  Docs

    Positional factory function for class clojure.reflect.AsmReflector.

### clojure.reflect/resolve-class

1.  Arities

    [this name]

2.  Docs

    Given a class name, return that typeref's class bytes as an InputStream.

### clojure.reflect/flag-descriptors

1.  Arities

2.  Docs

    The Java access bitflags, along with their friendly names and
    the kinds of objects to which they can apply.

### clojure.reflect/do-reflect

1.  Arities

    [reflector typeref]

2.  Docs

    nil

### clojure.reflect/ClassResolver

1.  Arities

2.  Docs

    nil

### clojure.reflect/Reflector

1.  Arities

2.  Docs

    Protocol for reflection implementers.

### clojure.reflect/->Constructor

1.  Arities

    [name declaring-class parameter-types exception-types flags]

2.  Docs

    Positional factory function for class clojure.reflect.Constructor.

### clojure.reflect/map->Constructor

1.  Arities

    [m\_<sub>5869</sub>\_<sub>auto</sub>\_<sub>]</sub>

2.  Docs

    Factory function for class clojure.reflect.Constructor, taking a map of keywords to field values.

### clojure.reflect/type-reflect

1.  Arities

    [typeref & options]

2.  Docs

    Alpha - subject to change.
       Reflect on a typeref, returning a map with :bases, :flags, and
    
    :members. In the discussion below, names are always Clojure symbols.
    
    :bases            a set of names of the type's bases
    
    :flags            a set of keywords naming the boolean attributes
                      of the type.
    
    :members          a set of the type's members. Each membrer is a map
                      and can be a constructor, method, or field.
    
    Keys common to all members:
    
    :name             name of the type 
    
    :declaring-class  name of the declarer
    
    :flags            keyword naming boolean attributes of the member
    
    Keys specific to constructors:
    
    :parameter-types  vector of parameter type names
    
    :exception-types  vector of exception type names
    
    Key specific to methods:
    
    :parameter-types  vector of parameter type names
    
    :exception-types  vector of exception type names
    
    :return-type      return type name
    
    Keys specific to fields:
    
    :type             type name
    
    Options:
    
    :ancestors     in addition to the keys described above, also
                   include an :ancestors key with the entire set of
                   ancestors, and add all ancestor members to
    
    :members.
    
    :reflector     implementation to use. Defaults to JavaReflector,
                   AsmReflector is also an option.

# clojure.repl

## Macros

### clojure.repl/doc

1.  Arities

    [name]

2.  Docs

    Prints documentation for a var or special form given its name

### clojure.repl/dir

1.  Arities

    [nsname]

2.  Docs

    Prints a sorted directory of public vars in a namespace

### clojure.repl/source

1.  Arities

    [n]

2.  Docs

    Prints the source code for the given symbol, if it can find it.
      This requires that the symbol resolve to a Var defined in a
      namespace for which the .clj is in the classpath.
    
    Example: (source filter)

## Functions

### clojure.repl/source-fn

1.  Arities

    [x]

2.  Docs

    Returns a string of the source code for the given symbol, if it can
      find it.  This requires that the symbol resolve to a Var defined in
      a namespace for which the .clj is in the classpath.  Returns nil if
      it can't find the source.  For most REPL usage, 'source' is more
      convenient.
    
    Example: (source-fn 'filter)

### clojure.repl/stack-element-str

1.  Arities

    [el]

2.  Docs

    Returns a (possibly unmunged) string representation of a StackTraceElement

### clojure.repl/find-doc

1.  Arities

    [re-string-or-pattern]

2.  Docs

    Prints documentation for any var whose documentation or name
     contains a match for re-string-or-pattern

### clojure.repl/pst

1.  Arities

    []
    [e-or-depth]
    [e depth]

2.  Docs

    Prints a stack trace of the exception, to the depth requested. If none supplied, uses the root cause of the
      most recent repl exception (\*e), and a depth of 12.

### clojure.repl/dir-fn

1.  Arities

    [ns]

2.  Docs

    Returns a sorted seq of symbols naming public vars in
      a namespace

### clojure.repl/set-break-handler!

1.  Arities

    []
    [f]

2.  Docs

    Register INT signal handler.  After calling this, Ctrl-C will cause
      the given function f to be called with a single argument, the signal.
      Uses thread-stopper if no function given.

### clojure.repl/root-cause

1.  Arities

    [t]

2.  Docs

    Returns the initial cause of an exception or error by peeling off all of
      its wrappers

### clojure.repl/demunge

1.  Arities

    [fn-name]

2.  Docs

    Given a string representation of a fn class,
      as in a stack trace element, returns a readable version.

### clojure.repl/thread-stopper

1.  Arities

    []
    [thread]

2.  Docs

    Returns a function that takes one arg and uses that as an exception message
      to stop the given thread.  Defaults to the current thread

### clojure.repl/apropos

1.  Arities

    [str-or-pattern]

2.  Docs

    Given a regular expression or stringable thing, return a seq of
    all definitions in all currently-loaded namespaces that match the
    str-or-pattern.

# clojure.set

## Macros

## Functions

### clojure.set/union

1.  Arities

    []
    [s1]
    [s1 s2]
    [s1 s2 & sets]

2.  Docs

    Return a set that is the union of the input sets

### clojure.set/map-invert

1.  Arities

    [m]

2.  Docs

    Returns the map with the vals mapped to the keys.

### clojure.set/join

1.  Arities

    [xrel yrel]
    [xrel yrel km]

2.  Docs

    When passed 2 rels, returns the rel corresponding to the natural
      join. When passed an additional keymap, joins on the corresponding
      keys.

### clojure.set/select

1.  Arities

    [pred xset]

2.  Docs

    Returns a set of the elements for which pred is true

### clojure.set/intersection

1.  Arities

    [s1]
    [s1 s2]
    [s1 s2 & sets]

2.  Docs

    Return a set that is the intersection of the input sets

### clojure.set/superset?

1.  Arities

    [set1 set2]

2.  Docs

    Is set1 a superset of set2?

### clojure.set/index

1.  Arities

    [xrel ks]

2.  Docs

    Returns a map of the distinct values of ks in the xrel mapped to a
      set of the maps in xrel with the corresponding values of ks.

### clojure.set/subset?

1.  Arities

    [set1 set2]

2.  Docs

    Is set1 a subset of set2?

### clojure.set/rename

1.  Arities

    [xrel kmap]

2.  Docs

    Returns a rel of the maps in xrel with the keys in kmap renamed to the vals in kmap

### clojure.set/rename-keys

1.  Arities

    [map kmap]

2.  Docs

    Returns the map with the keys in kmap renamed to the vals in kmap

### clojure.set/project

1.  Arities

    [xrel ks]

2.  Docs

    Returns a rel of the elements of xrel with only the keys in ks

### clojure.set/difference

1.  Arities

    [s1]
    [s1 s2]
    [s1 s2 & sets]

2.  Docs

    Return a set that is the first set without elements of the remaining sets

# clojure.stacktrace

## Macros

## Functions

### clojure.stacktrace/print-throwable

1.  Arities

    [tr]

2.  Docs

    Prints the class and message of a Throwable.

### clojure.stacktrace/root-cause

1.  Arities

    [tr]

2.  Docs

    Returns the last 'cause' Throwable in a chain of Throwables.

### clojure.stacktrace/e

1.  Arities

    []

2.  Docs

    REPL utility.  Prints a brief stack trace for the root cause of the
      most recent exception.

### clojure.stacktrace/print-cause-trace

1.  Arities

    [tr]
    [tr n]

2.  Docs

    Like print-stack-trace but prints chained exceptions (causes).

### clojure.stacktrace/print-trace-element

1.  Arities

    [e]

2.  Docs

    Prints a Clojure-oriented view of one element in a stack trace.

### clojure.stacktrace/print-stack-trace

1.  Arities

    [tr]
    [tr n]

2.  Docs

    Prints a Clojure-oriented stack trace of tr, a Throwable.
      Prints a maximum of n stack frames (default: unlimited).
      Does not print chained exceptions (causes).

# clojure.string

## Macros

## Functions

### clojure.string/capitalize

1.  Arities

    [s]

2.  Docs

    Converts first character of the string to upper-case, all other
      characters to lower-case.

### clojure.string/reverse

1.  Arities

    [s]

2.  Docs

    Returns s with its characters reversed.

### clojure.string/join

1.  Arities

    [coll]
    [separator coll]

2.  Docs

    Returns a string of all elements in coll, as returned by (seq coll),
       separated by an optional separator.

### clojure.string/replace-first

1.  Arities

    [s match replacement]

2.  Docs

    Replaces the first instance of match with replacement in s.
    
    match/replacement can be:
    
    char / char
    string / string
    pattern / (string or function of match).
    
    See also replace.
    
    The replacement is literal (i.e. none of its characters are treated
    specially) for all cases above except pattern / string.
    
    For pattern / string, $1, $2, etc. in the replacement string are
    substituted with the string that matched the corresponding
    parenthesized group in the pattern.  If you wish your replacement
    string r to be used literally, use (re-quote-replacement r) as the
    replacement argument.  See also documentation for
    java.util.regex.Matcher's appendReplacement method.
    
    Example:
    (clojure.string/replace-first "swap first two words"
                                  #"(\w+)(\s+)(\w+)" "$3$2$1")
    -> "first swap two words"

### clojure.string/escape

1.  Arities

    [s cmap]

2.  Docs

    Return a new string, using cmap to escape each character ch
       from s as follows:
    
    If (cmap ch) is nil, append ch to the new string.
    If (cmap ch) is non-nil, append (str (cmap ch)) instead.

### clojure.string/re-quote-replacement

1.  Arities

    [replacement]

2.  Docs

    Given a replacement string that you wish to be a literal
       replacement for a pattern match in replace or replace-first, do the
       necessary escaping of special characters in the replacement.

### clojure.string/replace

1.  Arities

    [s match replacement]

2.  Docs

    Replaces all instance of match with replacement in s.
    
    match/replacement can be:
    
    string / string
    char / char
    pattern / (string or function of match).
    
    See also replace-first.
    
    The replacement is literal (i.e. none of its characters are treated
    specially) for all cases above except pattern / string.
    
    For pattern / string, $1, $2, etc. in the replacement string are
    substituted with the string that matched the corresponding
    parenthesized group in the pattern.  If you wish your replacement
    string r to be used literally, use (re-quote-replacement r) as the
    replacement argument.  See also documentation for
    java.util.regex.Matcher's appendReplacement method.
    
    Example:
    (clojure.string/replace "Almost Pig Latin" #"\b(\w)(\w+)\b" "$2$1ay")
    -> "lmostAay igPay atinLay"

### clojure.string/split-lines

1.  Arities

    [s]

2.  Docs

    Splits s on \n or \r\n.

### clojure.string/lower-case

1.  Arities

    [s]

2.  Docs

    Converts string to all lower-case.

### clojure.string/trim-newline

1.  Arities

    [s]

2.  Docs

    Removes all trailing newline \n or return \r characters from
      string.  Similar to Perl's chomp.

### clojure.string/upper-case

1.  Arities

    [s]

2.  Docs

    Converts string to all upper-case.

### clojure.string/split

1.  Arities

    [s re]
    [s re limit]

2.  Docs

    Splits string on a regular expression.  Optional argument limit is
      the maximum number of splits. Not lazy. Returns vector of the splits.

### clojure.string/trimr

1.  Arities

    [s]

2.  Docs

    Removes whitespace from the right side of string.

### clojure.string/trim

1.  Arities

    [s]

2.  Docs

    Removes whitespace from both ends of string.

### clojure.string/triml

1.  Arities

    [s]

2.  Docs

    Removes whitespace from the left side of string.

### clojure.string/blank?

1.  Arities

    [s]

2.  Docs

    True if s is nil, empty, or contains only whitespace.

# clojure.template

## Macros

### clojure.template/do-template

1.  Arities

    [argv expr & values]

2.  Docs

    Repeatedly copies expr (in a do block) for each group of arguments
      in values.  values are automatically partitioned by the number of
      arguments in argv, an argument vector as in defn.
    
    Example: (macroexpand '(do-template [x y] (+ y x) 2 4 3 5))
             ;=> (do (+ 4 2) (+ 5 3))

## Functions

### clojure.template/apply-template

1.  Arities

    [argv expr values]

2.  Docs

    For use in macros.  argv is an argument list, as in defn.  expr is
      a quoted expression using the symbols in argv.  values is a sequence
      of values to be used for the arguments.
    
    apply-template will recursively replace argument symbols in expr
    with their corresponding values, returning a modified expr.
    
    Example: (apply-template '[x] '(+ x x) ')
             ;=> (+ 2 2)

# clojure.test

## Macros

### clojure.test/are

1.  Arities

    [argv expr & args]

2.  Docs

    Checks multiple assertions with a template expression.
      See clojure.template/do-template for an explanation of
      templates.
    
    Example: (are [x y] (= x y)  
                  2 (+ 1 1)
                  4 (\* 2 2))
    Expands to: 
             (do (is (= 2 (+ 1 1)))
                 (is (= 4 (\* 2 2))))
    
    Note: This breaks some reporting features, such as line numbers.

### clojure.test/testing

1.  Arities

    [string & body]

2.  Docs

    Adds a new string to the list of testing contexts.  May be nested,
      but must occur inside a test function (deftest).

### clojure.test/set-test

1.  Arities

    [name & body]

2.  Docs

    Experimental.
      Sets :test metadata of the named var to a fn with the given body.
      The var must already exist.  Does not modify the value of the var.
    
    When **load-tests** is false, set-test is ignored.

### clojure.test/with-test

1.  Arities

    [definition & body]

2.  Docs

    Takes any definition form (that returns a Var) as the first argument.
      Remaining body goes in the :test metadata function for that Var.
    
    When **load-tests** is false, only evaluates the definition, ignoring
    the tests.

### clojure.test/is

1.  Arities

    [form]
    [form msg]

2.  Docs

    Generic assertion macro.  'form' is any predicate test.
      'msg' is an optional message to attach to the assertion.
    
    Example: (is (= 4 (+ 2 2)) "Two plus two should be 4")
    
    Special forms:
    
    (is (thrown? c body)) checks that an instance of c is thrown from
    body, fails if not; then returns the thing thrown.
    
    (is (thrown-with-msg? c re body)) checks that an instance of c is
    thrown AND that the message on the exception matches (with
    re-find) the regular expression re.

### clojure.test/deftest

1.  Arities

    [name & body]

2.  Docs

    Defines a test function with no arguments.  Test functions may call
      other tests, so tests may be composed.  If you compose tests, you
      should also define a function named test-ns-hook; run-tests will
      call test-ns-hook instead of testing all vars.
    
    Note: Actually, the test body goes in the :test metadata on the var,
    and the real function (the value of the var) calls test-var on
    itself.
    
    When **load-tests** is false, deftest is ignored.

### clojure.test/with-test-out

1.  Arities

    [& body]

2.  Docs

    Runs body with **out** bound to the value of **test-out**.

### clojure.test/deftest-

1.  Arities

    [name & body]

2.  Docs

    Like deftest but creates a private var.

### clojure.test/try-expr

1.  Arities

    [msg form]

2.  Docs

    Used by the 'is' macro to catch unexpected exceptions.
      You don't call this.

## Functions

### clojure.test/test-all-vars

1.  Arities

    [ns]

2.  Docs

    Calls test-vars on every var interned in the namespace, with fixtures.

### clojure.test/test-var

1.  Arities

    [v]

2.  Docs

    If v has a function in its :test metadata, calls that function,
      with **testing-vars** bound to (conj **testing-vars** v).

### clojure.test/do-report

1.  Arities

    [m]

2.  Docs

    Add file and line information to a test result and call report.
       If you are writing a custom assert-expr method, call this function
       to pass test results to report.

### clojure.test/run-all-tests

1.  Arities

    []
    [re]

2.  Docs

    Runs all tests in all namespaces; prints results.
      Optional argument is a regular expression; only namespaces with
      names matching the regular expression (with re-matches) will be
      tested.

### clojure.test/assert-any

1.  Arities

    [msg form]

2.  Docs

    Returns generic assertion code for any test, including macros, Java
      method calls, or isolated symbols.

### clojure.test/testing-contexts-str

1.  Arities

    []

2.  Docs

    Returns a string representation of the current test context. Joins
      strings in **testing-contexts** with spaces.

### clojure.test/file-position

1.  Arities

    [n]

2.  Docs

    Returns a vector [filename line-number] for the nth call up the
      stack.
    
    Deprecated in 1.2: The information needed for test reporting is
    now on :file and :line keys in the result map.

### clojure.test/join-fixtures

1.  Arities

    [fixtures]

2.  Docs

    Composes a collection of fixtures, in order.  Always returns a valid
      fixture function, even if the collection is empty.

### clojure.test/get-possibly-unbound-var

1.  Arities

    [v]

2.  Docs

    Like var-get but returns nil if the var is unbound.

### clojure.test/assert-expr

1.  Arities

2.  Docs

    nil

### clojure.test/report

1.  Arities

2.  Docs

    Generic reporting function, may be overridden to plug in
       different report formats (e.g., TAP, JUnit).  Assertions such as
       'is' call 'report' to indicate results.  The argument given to
       'report' will be a map with a :type key.  See the documentation at
       the top of test<sub>is</sub>.clj for more information on the types of
       arguments for 'report'.

### clojure.test/compose-fixtures

1.  Arities

    [f1 f2]

2.  Docs

    Composes two fixture functions, creating a new fixture function
      that combines their behavior.

### clojure.test/\*stack-trace-depth\*

1.  Arities

2.  Docs

    The maximum depth of stack traces to print when an Exception
      is thrown during a test.  Defaults to nil, which means print the 
      complete stack trace.

### clojure.test/\*report-counters\*

1.  Arities

2.  Docs

    nil

### clojure.test/\*load-tests\*

1.  Arities

2.  Docs

    True by default.  If set to false, no test functions will
       be created by deftest, set-test, or with-test.  Use this to omit
       tests when compiling or loading production code.

### clojure.test/assert-predicate

1.  Arities

    [msg form]

2.  Docs

    Returns generic assertion code for any functional predicate.  The
      'expected' argument to 'report' will contains the original form, the
      'actual' argument will contain the form with all its sub-forms
      evaluated.  If the predicate returns false, the 'actual' form will
      be wrapped in (not&#x2026;).

### clojure.test/function?

1.  Arities

    [x]

2.  Docs

    Returns true if argument is a function or a symbol that resolves to
      a function (not a macro).

### clojure.test/test-vars

1.  Arities

    [vars]

2.  Docs

    Groups vars by their namespace and runs test-vars on them with
       appropriate fixtures applied.

### clojure.test/successful?

1.  Arities

    [summary]

2.  Docs

    Returns true if the given test summary indicates all tests
      were successful, false otherwise.

### clojure.test/use-fixtures

1.  Arities

2.  Docs

    Wrap test runs in a fixture function to perform setup and
      teardown. Using a fixture-type of :each wraps every test
      individually, while:once wraps the whole run in a single function.

### clojure.test/inc-report-counter

1.  Arities

    [name]

2.  Docs

    Increments the named counter in **report-counters**, a ref to a map.
      Does nothing if **report-counters** is nil.

### clojure.test/testing-vars-str

1.  Arities

    [m]

2.  Docs

    Returns a string representation of the current test.  Renders names
      in **testing-vars** as a list, then the source file and line of
      current assertion.

### clojure.test/\*testing-contexts\*

1.  Arities

2.  Docs

    nil

### clojure.test/test-ns

1.  Arities

    [ns]

2.  Docs

    If the namespace defines a function named test-ns-hook, calls that.
      Otherwise, calls test-all-vars on the namespace.  'ns' is a
      namespace object or a symbol.
    
    Internally binds **report-counters** to a ref initialized to
    **initial-report-counters**.  Returns the final, dereferenced state of
    **report-counters**.

### clojure.test/run-tests

1.  Arities

    []
    [& namespaces]

2.  Docs

    Runs all tests in the given namespaces; prints results.
      Defaults to current namespace if none given.  Returns a map
      summarizing test results.

### clojure.test/\*testing-vars\*

1.  Arities

2.  Docs

    nil

### clojure.test/\*test-out\*

1.  Arities

2.  Docs

    nil

### clojure.test/\*initial-report-counters\*

1.  Arities

2.  Docs

    nil

# clojure.uuid

## Macros

## Functions

# clojure.walk

## Macros

## Functions

### clojure.walk/postwalk

1.  Arities

    [f form]

2.  Docs

    Performs a depth-first, post-order traversal of form.  Calls f on
      each sub-form, uses f's return value in place of the original.
      Recognizes all Clojure data structures. Consumes seqs as with doall.

### clojure.walk/keywordize-keys

1.  Arities

    [m]

2.  Docs

    Recursively transforms all map keys from strings to keywords.

### clojure.walk/walk

1.  Arities

    [inner outer form]

2.  Docs

    Traverses form, an arbitrary data structure.  inner and outer are
      functions.  Applies inner to each element of form, building up a
      data structure of the same type, then applies outer to the result.
      Recognizes all Clojure data structures. Consumes seqs as with doall.

### clojure.walk/prewalk-replace

1.  Arities

    [smap form]

2.  Docs

    Recursively transforms form by replacing keys in smap with their
      values.  Like clojure/replace but works on any data structure.  Does
      replacement at the root of the tree first.

### clojure.walk/stringify-keys

1.  Arities

    [m]

2.  Docs

    Recursively transforms all map keys from keywords to strings.

### clojure.walk/prewalk

1.  Arities

    [f form]

2.  Docs

    Like postwalk, but does pre-order traversal.

### clojure.walk/postwalk-demo

1.  Arities

    [form]

2.  Docs

    Demonstrates the behavior of postwalk by printing each form as it is
      walked.  Returns form.

### clojure.walk/prewalk-demo

1.  Arities

    [form]

2.  Docs

    Demonstrates the behavior of prewalk by printing each form as it is
      walked.  Returns form.

### clojure.walk/macroexpand-all

1.  Arities

    [form]

2.  Docs

    Recursively performs all possible macroexpansions in form.

### clojure.walk/postwalk-replace

1.  Arities

    [smap form]

2.  Docs

    Recursively transforms form by replacing keys in smap with their
      values.  Like clojure/replace but works on any data structure.  Does
      replacement at the leaves of the tree first.

# clojure.xml

## Macros

## Functions

### clojure.xml/tag

1.  Arities

2.  Docs

    nil

### clojure.xml/\*sb\*

1.  Arities

2.  Docs

    nil

### clojure.xml/\*state\*

1.  Arities

2.  Docs

    nil

### clojure.xml/\*stack\*

1.  Arities

2.  Docs

    nil

### clojure.xml/element

1.  Arities

2.  Docs

    nil

### clojure.xml/\*current\*

1.  Arities

2.  Docs

    nil

### clojure.xml/content

1.  Arities

2.  Docs

    nil

### clojure.xml/content-handler

1.  Arities

2.  Docs

    nil

### clojure.xml/parse

1.  Arities

    [s]
    [s startparse]

2.  Docs

    Parses and loads the source s, which can be a File, InputStream or
      String naming a URI. Returns a tree of the xml/element struct-map,
      which has the keys :tag, :attrs, and :content. and accessor fns tag,
      attrs, and content. Other parsers can be supplied by passing
      startparse, a fn taking a source and a ContentHandler and returning
      a parser

### clojure.xml/startparse-sax

1.  Arities

    [s ch]

2.  Docs

    nil

### clojure.xml/emit

1.  Arities

    [x]

2.  Docs

    nil

### clojure.xml/attrs

1.  Arities

2.  Docs

    nil

### clojure.xml/emit-element

1.  Arities

    [e]

2.  Docs

    nil

# clojure.zip

## Macros

## Functions

### clojure.zip/rightmost

1.  Arities

    [loc]

2.  Docs

    Returns the loc of the rightmost sibling of the node at this loc, or self

### clojure.zip/insert-child

1.  Arities

    [loc item]

2.  Docs

    Inserts the item as the leftmost child of the node at this loc,
      without moving

### clojure.zip/left

1.  Arities

    [loc]

2.  Docs

    Returns the loc of the left sibling of the node at this loc, or nil

### clojure.zip/path

1.  Arities

    [loc]

2.  Docs

    Returns a seq of nodes leading to this loc

### clojure.zip/leftmost

1.  Arities

    [loc]

2.  Docs

    Returns the loc of the leftmost sibling of the node at this loc, or self

### clojure.zip/append-child

1.  Arities

    [loc item]

2.  Docs

    Inserts the item as the rightmost child of the node at this loc,
      without moving

### clojure.zip/branch?

1.  Arities

    [loc]

2.  Docs

    Returns true if the node at loc is a branch

### clojure.zip/children

1.  Arities

    [loc]

2.  Docs

    Returns a seq of the children of node at loc, which must be a branch

### clojure.zip/remove

1.  Arities

    [loc]

2.  Docs

    Removes the node at loc, returning the loc that would have preceded
      it in a depth-first walk.

### clojure.zip/down

1.  Arities

    [loc]

2.  Docs

    Returns the loc of the leftmost child of the node at this loc, or
      nil if no children

### clojure.zip/replace

1.  Arities

    [loc node]

2.  Docs

    Replaces the node at this loc, without moving

### clojure.zip/zipper

1.  Arities

    [branch? children make-node root]

2.  Docs

    Creates a new zipper structure. 
    
    branch? is a fn that, given a node, returns true if can have
    children, even if it currently doesn't.
    
    children is a fn that, given a branch node, returns a seq of its
    children.
    
    make-node is a fn that, given an existing node and a seq of
    children, returns a new branch node with the supplied children.
    root is the root node.

### clojure.zip/end?

1.  Arities

    [loc]

2.  Docs

    Returns true if loc represents the end of a depth-first walk

### clojure.zip/edit

1.  Arities

    [loc f & args]

2.  Docs

    Replaces the node at this loc with the value of (f node args)

### clojure.zip/make-node

1.  Arities

    [loc node children]

2.  Docs

    Returns a new branch node, given an existing node and new
      children. The loc is only used to supply the constructor.

### clojure.zip/vector-zip

1.  Arities

    [root]

2.  Docs

    Returns a zipper for nested vectors, given a root vector

### clojure.zip/node

1.  Arities

    [loc]

2.  Docs

    Returns the node at loc

### clojure.zip/up

1.  Arities

    [loc]

2.  Docs

    Returns the loc of the parent of the node at this loc, or nil if at
      the top

### clojure.zip/insert-right

1.  Arities

    [loc item]

2.  Docs

    Inserts the item as the right sibling of the node at this loc,
      without moving

### clojure.zip/rights

1.  Arities

    [loc]

2.  Docs

    Returns a seq of the right siblings of this loc

### clojure.zip/root

1.  Arities

    [loc]

2.  Docs

    zips all the way up and returns the root node, reflecting any
     changes.

### clojure.zip/next

1.  Arities

    [loc]

2.  Docs

    Moves to the next loc in the hierarchy, depth-first. When reaching
      the end, returns a distinguished loc detectable via end?. If already
      at the end, stays there.

### clojure.zip/seq-zip

1.  Arities

    [root]

2.  Docs

    Returns a zipper for nested sequences, given a root sequence

### clojure.zip/insert-left

1.  Arities

    [loc item]

2.  Docs

    Inserts the item as the left sibling of the node at this loc,
     without moving

### clojure.zip/prev

1.  Arities

    [loc]

2.  Docs

    Moves to the previous loc in the hierarchy, depth-first. If already
      at the root, returns nil.

### clojure.zip/right

1.  Arities

    [loc]

2.  Docs

    Returns the loc of the right sibling of the node at this loc, or nil

### clojure.zip/lefts

1.  Arities

    [loc]

2.  Docs

    Returns a seq of the left siblings of this loc

### clojure.zip/xml-zip

1.  Arities

    [root]

2.  Docs

    Returns a zipper for xml elements (as from xml/parse),
      given a root element
