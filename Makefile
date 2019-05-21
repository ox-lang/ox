.PHONY: format
format:
	astyle --recursive -i --mode=java -s2 src/java\*
