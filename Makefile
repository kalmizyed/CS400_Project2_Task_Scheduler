############
# FULL APP #
############

run: TaskSchedulerApp.class
	java TaskSchedulerApp
clean:
	rm *.class

runTests: runDataWranglerTests runAlgorithmEngineerTests runFrontendDeveloperTests

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

runFrontendDeveloperTests: FrontendDeveloperTests.class
	java -jar junit5.jar -cp . --scan-classpath -n FrontendDeveloperTests

FrontendDeveloperTests.class: TextUITester.class TaskScheduleUI.class
	javac -cp .:junit5.jar FrontendDeveloperTests.java

TextUITester.class: TextUITester.java
	javac TextUITester.java

TaskScheduleUI.class: TaskScheduleUI.java ITaskSchedulerFrontend.class
	javac TaskScheduleUI.java

ITaskSchedulerFrontend.class: ITaskSchedulerFrontend.java
	javac ITaskSchedulerFrontend.java
