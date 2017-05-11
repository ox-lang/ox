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
    'src/antlr/org/oxlang/lang:parser',
    '3rdparty/jvm:antlr4',
  ]
)

java_library(
  name='lang',
  dependencies=[
    ':reader',
  ],
)
