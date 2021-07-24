# CKFinder 3 for Java - demo application

This repository contains ready-to-use code samples created for the CKFinder Java connector.


## Run the demo application

1. Clone this repository (or download ZIP).
2. Enter the directory and build the project.

   If you are using Gradle, execute:
   
   ```sh
   ./gradlew build
   ```

   If you are using Maven, execute:
   
   ```sh
   ./mvnw package
   ```
3. Modify configuration to enable the application.

   In the `ckfinder.yml` configuration file change the value of parameter named `enabled` to `true` as follows:
   
   ```yaml
   enabled: true # Change this value to "true" to enable the application
   ```
   
4. Run the demo application from the command line with:

   If you are using Gradle:

   ```sh
   java -jar build/libs/ckfinder-docs-samples-java-3.5.2.jar
   ```

   If you are using Maven:

   ```sh
   java -jar target/ckfinder-docs-samples-java-3.5.2.jar
   ```
   
4. Open http://localhost:8080/ckfinder/static/samples/index.html in your browser to see CKFinder samples.

## License

Copyright (c) 2019, CKSource - Frederico Knabben. All rights reserved.
For license details see: [LICENSE.md](https://github.com/ckfinder/ckfinder-docs-samples-java/blob/master/LICENSE.md).

## Sources of Intellectual Property used in this code

CKFinder is distributed under the [CKFinder License Agreement](http://cksource.com/license/ckfinder) In order to purchase a license visit the [CKEditor Ecosystem website](https://ckeditor.com/pricing/).
