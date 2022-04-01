############
# FULL APP #
############

run: TaskSchedulerApp.class
	java TaskSchedulerApp

clean:
	rm *.class

runTests: runDataWranglerTests runAlgorithmEngineerTests

TaskSchedulerApp.class: TaskSchedulerApp.java
	javac TaskSchedulerApp.java

#################
# DATA WRANGLER #
#################

runDataWranglerTests: DataWranglerTests.class
	java -jar junit5.jar -cp . --scan-classpath -n DataWranglerTests

DataWranglerTests.class: DataWranglerTests.java 
	javac -cp .:junit5.jar DataWranglerTests.java -Xlint

#####################
# BACKEND DEVELOPER #
#####################

######################
# ALGORITHM ENGINEER #
######################

runAlgorithmEngineerTests: AlgorithmEngineerTests.class junit5.jar
	java -jar junit5.jar -cp . --scan-classpath -n AlgorithmEngineerTests

AlgorithmEngineerTests.class: AlgorithmEngineerTests.java RedBlackTree.class
	javac -cp .:junit5.jar AlgorithmEngineerTests.java

RedBlackTree.class: RedBlackTree.java
	javac -cp .:junit5.jar RedBlackTree.java

######################
# FRONTEND DEVELOPER #
######################