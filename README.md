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

3. Run the demo application from the command line with:

   If you are using Gradle:

   ```sh
   java -jar build/libs/ckfinder-docs-samples-java-3.5.0-BETA.jar
   ```

   If you are using Maven:

   ```sh
   java -jar target/ckfinder-docs-samples-java-3.5.0-BETA.jar 
   ```
   
4. Open http://localhost:8080/ckfinder/static/samples/index.html in your browser to see CKFinder samples.

**IMPORTANT**: This demo application has the authentication disabled. For simplicity, the authenticator [always returns `true`](https://github.com/ckfinder/ckfinder-docs-samples-java/blob/master/src/main/java/example/ckfinder/authentication/AlwaysTrueAuthenticator.java#L22),
which is obviously not secure. Yor authenticator should never do that. By doing so, you are allowing **anyone** to upload and list the files on your server. **Do not use this demo on production!**


## License

Copyright (c) 2019, CKSource - Frederico Knabben. All rights reserved.
For license details see: [LICENSE.md](https://github.com/ckfinder/ckfinder-docs-samples-java/blob/master/LICENSE.md).
