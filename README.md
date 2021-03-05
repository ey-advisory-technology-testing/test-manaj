# TestmanaJ

![GitHub repo size](https://img.shields.io/github/repo-size/scottydocs/README-template.md)
![GitHub contributors](https://img.shields.io/github/contributors/scottydocs/README-template.md)
[![release](https://github.com/ey-advisory-technology-testing/test-manaj/actions/workflows/release.yml/badge.svg)](https://github.com/ey-advisory-technology-testing/test-manaj/actions/workflows/release.yml)

TestmanaJ is a automated connector utility that injects test results into common test management tools.

TestmanaJ's detailed and customizable data-driven properties *save the user time* in manually uploading test results and facilitates a **single** location for manual and automated test execution reporting.

## Features
* Dynamically construct API Requests for common third-party Test Management Tools
* Easy to use
* High level of Customization
* Works with any Java Framework
* Detailed Reports made Easy
* Prebuilt Implementations with Common Frameworks
* Fast Execution


## Requirements

* You have installed at least <a href="https://maven.apache.org/download.cgi" > Maven 3.5 </a> or higher
* You have installed at least <a href="https://www.oracle.com/java/technologies/javase-downloads.html" > Java 8 </a> or higher


## Installing TestmanaJ

To install TestmanaJ, follow these steps:

* Download the jar file from the GitHub repository.
* Import the Jar into your project's IDE. You may also reference the JAR file into your `pom.xml`, or any other method of your choosing.
* Download the resources folder and place on your local. This directory can be placed anywhere on your native project or windows explorer. 


## Configuring TestmanaJ

If using ALM, open your resources folder and enter into the configuration.properties file. There you can route your ALM credentials into TestmanaJ. 

At bare minimum we recommend to: 
 * Fill out configuration.properties
 * Add name, test-instance, testcycl-id, cycle-id, test-id, subtype-id, status and owner to test-run.properties
 * Add test-run-id and TestRunSteps to test-run-step.properties

Below is a list of test run, and run step properties available for setting, if using ALM:


<table>
<tr>
  <th>Test Run Properties</th>
  <td>
  comments
| status
| execution date
| execution time
| owner
| cycle id
| test config id
| subtype id
| test id
| test name
| ei system id
| pc procedure name
| has linkage
| path
| state
| test config id
| name
| os build
| os sp
| test cycle name
| os config
| cycle
| duration
| last modified date/time
| test description
| assign rcyc
| test sync
| test instance
| cycle name
| pc reservation id
| attachment(s)
  </td>
</tr>
<tr>
  <th>Test Step Properties</th>
  <td>
| Test id
| Comp status
| Description
| Rel Obj id
| Obj id
| Has linkage
| Execution date
| Execution time
| Desstep id
| Has picture
| Tree parent id
| Bpt path
| Actual
| Step order
| Level
| Expected
| Line number
| Comp subtype name
| Extended reference
| Execution time
| Parent id
  </td>
</tr>
<tr>

</table>



## Executing in TestmanaJ
* This is as simple as calling the ExecuteApp method. Initially, we recommend to validate results in your third-party test management tool.


## Usage Example
Below you will find an example of a hashmap that will pass the `ExecuteApp` class to the test management tool of your choosing, it is important to note that though this is a hardcoded example, you will generate the hashmaps dynamically from your framework. 

```java

   public Foo bar() {

       String testCaseName = "Google Search - Negative test";

        LinkedHashMap<String, String> runProperties = new LinkedHashMap<>();
        runProperties.put("attachment", // Your file path );
        runProperties.put("comments", "This is a comment");

        LinkedHashMap<String, LinkedHashMap<String, String>> steps = new LinkedHashMap<>();

        LinkedHashMap<String, String> stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Passed");
        stepProperties.put("description", "Given I navigate to www.google.com");
        stepProperties.put("execution-time",//"HH:MM:SS");
        steps.put("Step 1", stepProperties);

        stepProperties = new LinkedHashMap<>();
        stepProperties.put("status", "Passed");
        stepProperties.put("description", "Given I enter text search into search.bar");
        stepProperties.put("expected", "Google Home Page Should Load");
        steps.put("Step 2", stepProperties);
       

        String resoucesPath = SystemUtils.getUserDir().toString();

        updateTestCase(testCaseName, steps, runProperties, resoucesPath);
   }

public static void updateTestCase(String testCaseName, LinkedHashMap<String, 
LinkedHashMap<String, String>> steps, LinkedHashMap<String, String> runProperties, 
String resourcesPath) throws Exception {
      App executeApp = new App(resourcesPath,"resources/");
      executeApp.updateTestResult(testCaseName, steps, runProperties);
   }

```

Below you will find an example of a dynamically integrated hashmap using bdd that will pass the `ExecuteApp` class to test management tool.

Initally, this is the setup creating the different objects and variables needed. 

```java
public class Hooks {
    Logger logger = Logger.getLogger(Hooks.class.getName());

      LinkedHashMap<String, String> runProperties = new LinkedHashMap<>();
      LinkedHashMap<String, LinkedHashMap<String, String>> steps = new LinkedHashMap<>();
int c=0;
      LinkedHashMap<String, String> stepProperties = new LinkedHashMap<>();
      LinkedHashMap<String, String> DateAndTime = new LinkedHashMap<>();
      LinkedHashMap<Integer, LinkedHashMap<String, String>> trys = new LinkedHashMap<>();

      ArrayList<String> passorfail = new ArrayList<String>();
      String errors = "";


      public String deploy_connector=GlobalProperties.getConfigProperties()
                                                     .getProperty("deploy_connector")
                                                     .toLowerCase();
 ```
 These two functions add properties to a step (date, time) and add properties to a run (date, time)
 ```java

      public void addStepProperties(String key, String value) {
                      if (deploy_connector.equals("true")) {
                                      stepProperties.put(key, value);
                      }
      }
      public void addRunProperties(String key, String value){
                      if (deploy_connector.equals("true")) {
                                      runProperties.put(key, value);
                      }
      }
                
```
This function happens before every Test. Additonally, the file path needs to change for the specific user in order to save the log file that will be created. Further down, you will see the beforeStep function adds the DateAndTime to each step to track when they're being executed. 
```java

@Before
public void beforeTest(Scenario scenario) throws Throwable {
  if (GlobalProperties.getConfigProperties().getProperty("deploy_connector")
                                            .equalsIgnoreCase("true")) { 

// toggle for deploy data to reporting tool
                  File file = new File("C:\\ Your file path);
                  passorfail = new ArrayList<String>();
                  int c=0;
                  runProperties = new LinkedHashMap<>();

//Format data to put into runProperties
                  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                  Date date = new Date();
                  String DT = formatter.format(date);
                  String[] DateTime = DT.split(" ");
                  runProperties.put("execution-date", DateTime[0]);
                  runProperties.put("execution-time", DateTime[1]);
                  ArrayList<Object> arlist = new ArrayList<Object>();
                  ArrayList<String> teststeps = new ArrayList<String>();

//Accessing fields to get step name/Description to add into Steps Hashmap
                  Object cases = FieldUtils.readField(scenario, "testCase", true);
                  Object steps_all = FieldUtils.readField(cases, "testSteps", true);
                  for (Object o : (ArrayList) steps_all) {
                                  arlist.add(FieldUtils.readField(o, "step", true));
                  }
                  for (Object stepin : (ArrayList<Object>) arlist) {
                                  teststeps.add(FieldUtils
                                           .readField(stepin, "text", true)
                                           .toString());
                  }
                  
//Loop to add the data into Step hash map using step properties
                  for (int i = 0; i < teststeps.size(); i++) {

                                  stepProperties = new LinkedHashMap<>();

//Format data to put into stepProperties
                                  stepProperties.put("description", teststeps.get(i));
                                  stepProperties.put("status", "No Run"); //Default status- No run
                                  steps.put("Step " + (i + 1), stepProperties);
        }
    }
}

@BeforeStep
public void beforeStep(Scenario scenario) throws Throwable {
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date1 = new Date();
                String DT1 = formatter1.format(date1);
                String[] DateTime1 = DT1.split(" ");

                DateAndTime.put("execution-date", DateTime1[0]);
                DateAndTime.put("execution-time", DateTime1[1]);
                if(steps.containsKey("Steps "+(c+1))){
                    steps.get("Steps "+(c+1)).putAll(DateAndTime);
    }
                c++;
}
                
```
After the step is executed, the status of "pass" or "fail" is returned from your third-party test management tool and added to an array list. 
```java

@AfterStep
public void afterStep(Scenario scenario) throws Throwable {
                //getting each step status and adding to an array list
                passorfail.add(scenario.getStatus().toString());
}

```
It is important to note, the file path needs to change for the specific user in order to save the log file created. Likewise, in afterTest if the scenario failed the error messages will be written to the file specified in the file path. Additonally, it will capture a screenshot of the failed step and adds it to the run properties. 

```java
@After
public void afterTest(Scenario scenario) throws Throwable {

    if (GlobalProperties.getConfigProperties()
                        .getProperty("deploy_connector")
                        .equalsIgnoreCase("true")) {
    try {

    //To change the status of all passed steps
    if (scenario.getStatus()
                .toString()
                .equals("PASSED")) {

                    for (int i = 0; i < passorfail.size(); i++) {
                                    if (passorfail.get(i).equals("PASSED")) {
                                                    passorfail.set(i, "Done");
                                                    steps.get("Step " + (i + 1)).put("status", "Passed");
            }
        }
    } 

else {
                    //triggering exception for when status is Failed
                    throw new Exception();
    }

} catch (Exception e) {

    if (scenario.isFailed()) {
                    File file = new File("C:\\ Your file path);
                    StringWriter error = new StringWriter();
                    e.printStackTrace(new PrintWriter(error));
                    String test = error.toString();

                    //write to log file
                    FileOutputStream outputStream = new FileOutputStream(file);
                    //stack trace to String
                    byte[] strToBytes = test.getBytes();
                    outputStream.write(strToBytes);
                    outputStream.close();

      //To access error field in step_results Field
      Field field = FieldUtils.getField(scenario.getClass(), "stepResults", true);
      try {
                      ArrayList<Result> results = (ArrayList<Result>) field.get(scenario);
                      for (Result result : results) {
                                      if (result.getError() != null) {
                                                      errors = (FieldUtils.readField(result.getError(), "detailMessage", true)).toString();
                                                      errors=errors+" Check the attachments for more technical data";
                                      }
                      }

      } catch (Exception javaErr) {
                      // To handle unknown exception
                      System.out.println(javaErr);
      }
      //Loop to change status of failed Test steps
      for (int i = 0; i < passorfail.size(); i++) {
                      if (passorfail.get(i).equals("FAILED")) {
                                      passorfail.set(i, "Done");
    steps.get("Step " + (i + 1)).put("status", "Failed");
                Object cases = FieldUtils.readField(scenario, "testCase", true);
                steps.get("Step " + (i + 1)).put("actual", errors);
} else  {
                                if (passorfail.get(i).equals("PASSED")) {
                                                passorfail.set(i, "Done");
                                                steps.get("Step " + (i + 1)).put("status", "Passed");
                                }
                }
}
                System.out.println(steps);
                //Taking screenshot for Failed Step and adding to runProperties
                String screenshotpath = ScreenShotMethods.captureScreenShot(OutputType.FILE, scenario);
                scenario.embed(((TakesScreenshot) DriverUtil.getDefaultDriver()).getScreenshotAs(OutputType.BYTES), "image/png");
                                //adds logger file and screenshot to attachments
                                runProperties.put("attachment",String.valueOf(file)+";"+screenshotpath);
                }
}
String testCaseName = scenario.getName();
//Deploying Test data to test management tool
App app = new App(System.getProperty("user.dir"), "resources/");
app.updateTestResult(testCaseName, steps, runProperties);
        }
    }
}

```

## Contributing to TestmanaJ

To contribute to TestmanaJ, follow these steps:

1. Fork this repository.
2. Create a branch: `git checkout -b <branch_name>`.
3. Make your changes and commit them: `git commit -m '<commit_message>'`
4. Push to the original branch: `git push origin <project_name>/<location>`
5. Create the pull request.

Alternatively see the GitHub documentation on [creating a pull request](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request).

<!---## Contributors

Thanks to the following people who have contributed to this project:

* [@sethmwatson](https://github.com/sethmwatson) 📖
* Manjunath Purad 🐛
* Teddy Gajewski 🐛
* Justin Hanke 🐛

You might want to consider using something like the [All Contributors](https://github.com/all-contributors/all-contributors) specification and its [emoji key](https://allcontributors.org/docs/en/emoji-key).
--->

<!---## Contact

If you have absolutely any questions or concerns do not hesitate to contact our support team.  

 EY_NGeTAF_Support.GID@ey.net --->
 
 
## Agenda

* Evaluate Architecture to support additional test-management tools
* Increase Unit Test Coverage
    * method uploadAttachment
    * method uploadAttachmentToStep
* Expand documentation via Github Wiki





 
