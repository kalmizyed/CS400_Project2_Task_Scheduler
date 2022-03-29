clean:
	rm *.class
runTests: BackendDeveloperTests.class junit5.jar
	java -jar junit5.jar -cp . --scan-classpath -n BackendDeveloperTests
BackendDeveloperTests.class: BackendDeveloperTests.java TaskSchedulerBackend.class
	javac -cp .:junit5.jar BackendDeveloperTests.java

TaskSchedulerBackend.class: TaskSchedulerBackend.java ITaskSchedulerBackend.class ITask.class TaskPlaceholder.class IExtendedSortedCollection.class ITreeFileHandler.class TreeFileHandlerPlaceholder.class
	javac TaskSchedulerBackend.java
ITaskSchedulerBackend.class: ITaskSchedulerBackend.java ITask.class
	javac ITaskSchedulerBackend.java
ITask.class: ITask.java
	javac ITask.java
TaskPlaceholder.class: TaskPlaceholder.java ITask.class
	javac TaskPlaceholder.java
IExtendedSortedCollection.class: IExtendedSortedCollection.java SortedCollectionInterface.class
	javac IExtendedSortedCollection.java
SortedCollectionInterface.class: SortedCollectionInterface.java
	javac SortedCollectionInterface.java
ITreeFileHandler.class: ITreeFileHandler.java ITask.class IExtendedSortedCollection.class 
	javac ITreeFileHandler.java
TreeFileHandlerPlaceholderBD.class: TreeFileHandlerPlaceholderBD.java ITreeFileHandler.class ITask.class
	javac TreeFileHandlerPlaceholderBD.java