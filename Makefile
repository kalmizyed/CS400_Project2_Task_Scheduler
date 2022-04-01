runTests: FrontendDeveloperTests.class
	java -jar junit5.jar --class-path . --scan-classpath

clean:
	rm *.class

FrontendDeveloperTests.class: FrontendDeveloperTests.java TaskScheduleBackend.class TaskScheduleUI.class TextUITester.class Task.class ITaskSchedulerBackend.class ITask.class SortedCollectionInterface.class ITaskSchedulerFrontend.class IExtendedSortedCollection.class
	javac -cp .:junit5.jar FrontendDeveloperTests.java -Xlint


TaskScheduleBackend.class: TaskScheduleBackend.java
	javac TaskScheduleBackend.java

TaskScheduleUI.class: TaskScheduleUI.java
	javac TaskScheduleUI.java

TextUITester.class: TextUITester.java
	javac TextUITester.java

Task.class: Task.java
	javac Task.java

ITaskSchedulerBackend.class: ITaskSchedulerBackend.java
	javac ITaskSchedulerBackend.java

ITask.class: ITask.java
	javac ITask.java


SortedCollectionInterface.class: SortedCollectionInterface.java
	javac SortedCollectionInterface.java

ITaskSchedulerFrontend.class: ITaskSchedulerFrontend.java
	javac ITaskSchedulerFrontend.java

IExtendedSortedCollection.class: IExtendedSortedCollection.java
	javac IExtendedSortedCollection.java
