# multi-threading-nasa-neo-api
### Purpose ###
Multi-threading sample project using Callable.

Consume NASA API: https://api.nasa.gov/api.html#NeoWS, and process using multi-threading

### Logic & Judgment ###
The largest NEO is determined by the average of "estimated_diameter_min" and "estimated_diameter_max".

The closest NEO is determined by "miss_distance" from "close_approach_data".

For both, kilometer data were used.

### Import & Build & Run ###
git clone the project and import as maven project to your preferable IDE.

Use ```mvn install``` to build the project, and this will also create a jar file in the project's target folder.
You will see "nasa-api-reporter.jar" has been created.
In order to run from your IDE, run it as Java Application. The main function to start is in "StartupReporter" class.

Also you can run the application using ```java -jar target/nasa-api-reporter.jar``` from the console.
Make sure "java" path and jar file location are specified properly for your environment.

Output will be both in the console and log file.

### Log ###
Log file is created when the application is executed.
From IDE it creates "neo-reporter.log" in the project folder.

If you run it from your console, the file is created in the folder where the application is running.
You can customize log file using ```java -jar target/nasa-api-reporter.jar > custom_folder/custom_file.log```
This case output won't be printed in the console, but in the file.

### JUnit Testing ###
Unit test is included.
