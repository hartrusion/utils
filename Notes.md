### Apache NetBeans IDE
* Ant Project
* Built classes: /build/classes/
* jar file: /dist/projectName.jar

Individual .class files are compiled on saving the .java file. All .class files 
are compiled with clean and build, this also generates .jar file.

External jar files: Properties - Libraries - Compile: Add to Classpath

### IntelliJ IDE
* Built classes: /out/production/projectName/
* jar file: /out/artifacts/utils

Classes are compiled with build, jar is build using a configuration artifact.

Project Structure - Artifacts - (+) to add new artifact. Add out/production 
directory contents to the output layout. 

Build jar with: Top Menu - Build - Build artifacts...

#### External jar files:

Project Structure - Project Settings - Modules (keep projectName selected) - 
Dependencies (Tab):

(+) 1 JARs or Directories - Select .jar file or add as library.

#### Configure artifact (jar)
Project Structure - Project Settings - Artifacts - (+) (Add) - JAR - Empty: 
Generates an artifact configuration. Set projectName, this will name the .jar 
file out.

#### Change java version
Change in Project Structure - Project Settings:
* Project - SDK
* Modules - Sources - Language Level
* Modules - Dependencies - Module SDK

AbsoluteLayout comes packed with NetBeans installation.
JAR location: \java\modules\ext
Source: \java\sources\org\netbeans\lib\awtextra

#### Notable settings
Limit Line length: Settings - Editor - Code Style - "Hard Wrap at:"
