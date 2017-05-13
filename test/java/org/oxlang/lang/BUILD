junit_tests(
  name='lexer_test',
  sources=['LexerTest.java'],
  dependencies=[
    'src/antlr/org/oxlang/lang:parser',
    '3rdparty/jvm:antlr4',
  ]
)

junit_tests(
  name='reader_test',
  sources=['OxlangReaderTest.java'],
  dependencies=[
    'src/java/org/oxlang/lang',
    '3rdparty/jvm:antlr4',
  ],
)

jvm_binary(
  name='lexer_driver',
  source='LexerDriver.java',
  main='org.oxlang.lang.LexerDriver',
  dependencies=[
    'src/antlr/org/oxlang/lang:parser',
    'src/java/org/oxlang/lang',
    '3rdparty/jvm:antlr4',
  ]
)
