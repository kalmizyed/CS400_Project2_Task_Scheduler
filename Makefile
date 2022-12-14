############
# FULL APP #
############

run: TaskSchedulerApp.class TaskSchedulerBackend.class
	java TaskSchedulerApp
clean:
	rm *.class

runTests: runFrontendDeveloperTests runDataWranglerTests runAlgorithmEngineerTests runBackendDeveloperTests

TaskSchedulerApp.class: TaskSchedulerApp.java
	javac TaskSchedulerApp.java

#################
# DATA WRANGLER #
#################

runDataWranglerTests: DataWranglerTests.class
	java -jar junit5.jar -cp . --scan-classpath -n DataWranglerTests

DataWranglerTests.class: DataWranglerTests.java 
	javac -cp .:junit5.jar DataWranglerTests.java -Xlint

ITreeFileHandler.class: ITreeFileHandler.java ITask.class IExtendedSortedCollection.class 
	javac ITreeFileHandler.java

TreeFileHandler.class: TreeFileHandler.java ITreeFileHandler.class ITask.class Task.class RedBlackTree.class
	javac TreeFileHandler.java

ITask.class: ITask.java
	javac ITask.java

Task.class: Task.java ITask.class
	javac Task.java

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

TaskScheduleUI.class: TaskScheduleUI.java ITaskSchedulerFrontend.class ITaskSchedulerBackend.class
	javac TaskScheduleUI.java

ITaskSchedulerFrontend.class: ITaskSchedulerFrontend.java
	javac ITaskSchedulerFrontend.java
#####################
# BACKEND DEVELOPER #
#####################
runBackendDeveloperTests: BackendDeveloperTests.class junit5.jar	
	java -jar junit5.jar -cp . --scan-classpath -n BackendDeveloperTests
BackendDeveloperTests.class: BackendDeveloperTests.java TaskSchedulerBackend.class
	javac -cp .:junit5.jar BackendDeveloperTests.java

TaskSchedulerBackend.class: TaskSchedulerBackend.java ITaskSchedulerBackend.class ITask.class Task.class IExtendedSortedCollection.class ITreeFileHandler.class TreeFileHandler.class
	javac TaskSchedulerBackend.java
ITaskSchedulerBackend.class: ITaskSchedulerBackend.java ITask.class
	javac ITaskSchedulerBackend.java
TaskPlaceholder.class: TaskPlaceholder.java ITask.class
	javac TaskPlaceholder.java
IExtendedSortedCollection.class: IExtendedSortedCollection.java SortedCollectionInterface.class
	javac IExtendedSortedCollection.java
SortedCollectionInterface.class: SortedCollectionInterface.java
	javac SortedCollectionInterface.java
TreeFileHandlerPlaceholderBD.class: TreeFileHandlerPlaceholderBD.java ITreeFileHandler.class ITask.class
	javac TreeFileHandlerPlaceholderBD.java
