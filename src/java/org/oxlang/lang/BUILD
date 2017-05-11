java_library(
  name='lang_deps',
  dependencies=[
    '3rdparty/jvm:jetbrains-annotations',
    'src/java/io/lacuna/bifurcan:bifurcan',
  ],
) 

java_library(
  name='reader',
  sources=['Reader.java'],
  dependencies=[
    '3rdparty/jvm:antlr-runtime',
    'src/antlr/org/oxlang/lang:parser'
  ]
)

jvm_binary(
  name='reader_test',
  source='ReaderTest.java',
  dependencies=[
    ':reader',
  ],
)

java_library(
  name='lang',
  dependencies=[
    ':reader',
  ],
)
