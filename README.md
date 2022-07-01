Digital Cookbook

Environment:
1.OS: Windows
2.SDK: Java 15+
3.IDE: eclipse
4.Database: MySQL Server 8+

Set up for eclipse:
1.import existing project into workspace ( File --> Import --> General: Existing project into workspace )
2.Database configuration:
     (1) connect to local instance MySQL80
     (2) import data(Server --> data import)
     (3) click "import from self-contained file" and choose the sql file in the package
     (4) on the "Default Target Schema" row, build a new schema and choose this schema in the scroll bar
     (5) click "start import"
     (6) repeat step 4 until all 3 sql files are imported.(don't need to build new schema again once it is built)
3(optional). eclipse project configuration
     (1) download JavaFx SDK in https://gluonhq.com/products/javafx/. (preferably install the folder on C:\Program
          Files\java)
     (2) right click the project folder in package exploer and choose build path --> configure build path
     (3) in "Libraries", choose "module path", click "add external JARs", and choose all JAR files in 
          C:\Program Files\java\openjfx-18.0.1_windows-x64_bin-sdk\javafx-sdk-18.0.1\lib
     (3) In the main menu, find Run->Run configurations , click Arguments on the top. In the VM arguments field, type
          in a String like follows: --module-path "the path to your javafx lib" --add-modules javafx.controls,javafx.fxml
          One example is: --module-path "C:\Program Files\java\openjfx-18.0.1_windows-x64_bin-sdk\javafx-sdk-18.0.1
          \lib" --add-modules javafx.controls,javafx.fxml
4. run the Main.java class in scr/applicatoin folder.


