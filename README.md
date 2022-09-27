## Works only on Windows x64

## Preparing the project

1. Extract [`server.zip`](server.zip) to the root directory of the project.
   The directory should look like this:
    ```
    - .vscode
    - server
        - apache-tomcat-8.0.47
        - jre1.8.0_231
    - dist
    - src
    ...
    ```
2. Open the root directory with VSCode

## Setting up vscode:

1. Install the [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack).
   This extension requires a JDK11+ installation. You can get it [here](https://www.oracle.com/java/technologies/downloads/#jdk17-windows).

    #### If vscode doesn't recognize the java project, open any `.java` file contained in the [`src/main/java`](/src/main/java) directory to kick off the Java Language Server.

2. Install the [Tomcat for Java](https://marketplace.visualstudio.com/items?itemName=adashen.vscode-tomcat) extension

</br>

## Setting up the project:

1. Add the following to `settings.json` - Command Palette (Ctrl + Shift + P) > Preferences: Open Settings (JSON)

    Note: Replace `C:/Projects` with the path where the project is located on your machine

    Note: The path should be absolute and point to [`server/jre1.8.0_231`](server/jre1.8.0_231)

    ```	
    "java.configuration.runtimes": [
        {
            "name": "JavaSE-1.8",
            "path": "C:/Projects/web/server/jre1.8.0_231",
            "default": true
        }
    ]
    ```

2. Command Palette (Ctrl + Shift + P) > Add Tomcat Server > Select [`server/apache-tomcat-8.0.47`](server/apache-tomcat-8.0.47)

3. Restart VSCode
    
## Building the project:

1. Command Palette (Ctrl + Shift + P) > Java: Force Java Compilation > Full

    Note: A successful compilation should generate a `classes` directory inside [`dist/WEB-INF`](/dist/WEB-INF/)
    containing `.class` files.

2. Enable automatic compilation on source code changes by checking the Java > Autobuild: Enabled option in settings, or by adding the following option to `settings.json`:

    ```"java.autobuild.enabled": true```

## Running the project

1. Right click [`dist`](/dist) and select `Run on Tomcat Server`
2. Access the project root at [`http://localhost:8080`](http://localhost:8080)
3. Test the REST API at [`http://localhost:8080/api/products`](http://localhost:8080/api/products)

## Debugging the project

1. Right click [`dist`](/dist) and select `Debug on Tomcat Server`
2. Access the project root at [`http://localhost:8080`](http://localhost:8080)
3. Test the REST API at [`http://localhost:8080/api/products`](http://localhost:8080/api/products)
4. Change some code in [`src/main/java`](/src/main/java)
5. Save the changes, then hit the ⚡ icon in the debugging toolbar to hot-reload the code into Tomcat.

    Note: Due to the nature of the JVM not all types of changes can be hot-reloaded.
    If this happens recompile the project and debug again.
6. The changes should be reflected in the next request.
7. Test the debugger by inserting a breakpoint anywhere in the source code.

## Important notes

- Always stop the tomcat server before exiting VSCode. Otherwise tomcat will not release the port `8080` and you will not be able to run it again.

  You can stop it by using Command Palette (Ctrl + Shift + P) > Stop Tomcat Server.

  If this does not work you can use the command `./catalina.bat stop` inside [`server/apache-tomcat-8.0.47/bin`](/server/apache-tomcat-8.0.47/bin)

- The controllers use GSON to serdes api requests and responses

[`config/JerseyConfig.java`](/src/main/java/config/JerseyConfig.java)
- The api base path `/api` is defined with 
```java
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig { }
```

- Register dependency injection classes with
```java
register(ProductsService.class);

register(new AbstractBinder() {
    @Override
    protected void configure() {
        bindAsContract(ProductsService.class).in(Immediate.class);
    }
});
```

- Register the package for controller resolution, the server won't start without this (change this if you change the controllers package name):

```java
packages("controllers");
```


[`server/apache-tomcat-8.0.47/conf/server.xml`](/server/apache-tomcat-8.0.47/conf/server.xml)
- You can change the api port here
```xml
<Connector port="8080" protocol="HTTP/1.1" connectionTimeout="20000" redirectPort="8443" />
```

