JAVAC = javac
JAVA = java
SOURCES = $(wildcard *.java)
WIN_CLASSPATH = ".;./lib/json-20180813.jar;./lib/couchbase-core-io-1.7.6-javadoc.jar;./lib/couchbase-core-io-1.7.6-sources.jar;./lib/couchbase-core-io-1.7.6.jar;./lib/couchbase-java-client-2.7.6-javadoc.jar;./lib/couchbase-java-client-2.7.6-sources.jar;./lib/couchbase-java-client-2.7.6.jar;./lib/opentracing-api-0.31.0-sources.jar;./lib/opentracing-api-0.31.0.jar;./lib/rxjava-1.3.8-sources.jar;./lib/rxjava-1.3.8.jar"
NIX_CLASSPATH = ".:./lib/json-20180813.jar:./lib/couchbase-core-io-1.7.6-javadoc.jar:./lib/couchbase-core-io-1.7.6-sources.jar:./lib/couchbase-core-io-1.7.6.jar:./lib/couchbase-java-client-2.7.6-javadoc.jar:./lib/couchbase-java-client-2.7.6-sources.jar:./lib/couchbase-java-client-2.7.6.jar:./lib/opentracing-api-0.31.0-sources.jar:./lib/opentracing-api-0.31.0.jar:./lib/rxjava-1.3.8-sources.jar:./lib/rxjava-1.3.8.jar"

win_main: $(SOURCES)
	$(JAVAC) -cp $(WIN_CLASSPATH) $(SOURCES)

nix_main: $(SOURCES)
	$(JAVAC) -cp $(NIX_CLASSPATH) $(SOURCES)

runwindows: win_main
	$(JAVA) -cp $(WIN_CLASSPATH) SocketServer

runlinux: nix_main
	$(JAVA) -cp $(NIX_CLASSPATH) SocketServer

clean:
	rm *.class
