runTests: AlgorithmEngineerTests.class junit5.jar
	java -jar junit5.jar -cp . --scan-classpath -n AlgorithmEngineerTests

AlgorithmEngineerTests.class: AlgorithmEngineerTests.java RedBlackTree.class
	javac -cp .:junit5.jar AlgorithmEngineerTests.java

RedBlackTree.class: RedBlackTree.java
	javac -cp .:junit5.jar RedBlackTree.java

clean:
	rm *.class