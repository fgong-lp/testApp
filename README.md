This repository written in Java is a similar version to another existing repository (https://github.com/alsoba13/micronaut4-read-timeout-in-connection-pool-issue) written in Kotlin for reproducing the ReadTimeout issue in micronaut 4 (Closed ticket: https://github.com/micronaut-projects/micronaut-core/issues/9843).

Having applied Micronaut 4.1.2 to our apps, sadly to say, we continue seeing the ReadTimeout exception, less frequently than before (was roughly 3% occurrence in production). Moreover, another new exception - ResponseClosedException starts to show up. 

This repository has the code to reproduce the issue with ResponseClosedException, as described in (https://github.com/micronaut-projects/micronaut-core/issues/9995) 

(It is also to implements a Micronaut 4.1.x service that counts the number of bytes of https://micronaut.io/launch landing page.)

# How to reproduce
Use Java 17. 

##  Run test
1. Run test(s) 
   ```
    ./gradlew test --info
   ```
   or

   run the ReproduceErrorTest.java

2. Without re-configuring any config, check that when running the test for requests with delays of 4.9-ish to 5.9-ish seconds, every other request would consistently fail, throwing the ResponseClosedException.

3. If the issue is not found in step 2, do gradlew clean and redo step 1 & 2
   ```
    ./gradlew clean
   ```
