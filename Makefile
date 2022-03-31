############
# FULL APP #
############

run: TaskSchedulerApp.class
	java TaskSchedulerApp

clean:
	rm *.class

runTests: runDataWranglerTests

TaskSchedulerApp.class: TaskSchedulerApp.java
	javac TaskSchedulerApp.java

#################
# DATA WRANGLER #
#################

runDataWranglerTests: DataWranglerTests.class
	java -jar junit5.jar --class-path . --scan-classpath

DataWranglerTests.class: DataWranglerTests.java 
	javac -cp .:junit5.jar DataWranglerTests.java -Xlint

#####################
# BACKEND DEVELOPER #
#####################

######################
# ALGORITHM ENGINEER #
######################

######################
# FRONTEND DEVELOPER #
######################