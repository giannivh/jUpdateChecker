jUpdateChecker
==============

jUpdateChecker is a Java library to check for updates.

Requirements
------------

* Java 1.6+

Screen shots
------------

![Screen shot 1](https://raw.github.com/giannivh/jUpdateChecker/master/ScreenShot1.png "Screen shot 1")

![Screen shot 2](https://raw.github.com/giannivh/jUpdateChecker/master/ScreenShot2.png "Screen shot 2")

![Screen shot 3](https://raw.github.com/giannivh/jUpdateChecker/master/ScreenShot3.png "Screen shot 3")

![Screen shot 4](https://raw.github.com/giannivh/jUpdateChecker/master/ScreenShot4.png "Screen shot 4")

How to use
----------

Add the maven repo to your pom file:

```xml
<repositories>
    <repository>
        <id>giannivanhoecke</id>
        <url>http://maven.giannivanhoecke.com/</url>
        <releases>
            <enabled>true</enabled>
        </releases>
        <snapshots>
            <enabled>false</enabled>
        </snapshots>
    </repository>
</repositories>
```

Add the dependency to your pom file:
```xml
<dependencies>
    <dependency>
        <groupId>com.giannivanhoecke.jupdate-checker</groupId>
        <artifactId>jupdate-checker-core</artifactId>
        <version>0.1</version>
    </dependency>
</dependencies>
```

Example on how to add the update checker to your Java app:
```java
String  appName         = "Test";
Version currentVersion  = new Version( 0, 1 );
String  newVersionURL   = "http://giannivanhoecke.com/dev/jupdatecheckertestappversion.txt";
String  releaseNotesURL = "http://giannivanhoecke.com/dev/jupdatecheckertestappversionrn.html";
String  downloadPageURL = "http://giannivanhoecke.com/";

JUpdateChecker jUpdateChecker =
        new JUpdateChecker( appName, currentVersion, newVersionURL, releaseNotesURL, downloadPageURL, null,
                "http://giannivanhoecke.com/dev/jupdatecheckertestappversionfile.txt" );

try {

    //Show gui...
    jUpdateChecker.checkForUpdates( false );
}
catch( IOException e ) {

    e.printStackTrace();
}
catch( InvalidVersionException e ) {

    e.printStackTrace();
}
```