This repository written in Java is a similar version to another existing repository (https://github.com/alsoba13/micronaut4-read-timeout-in-connection-pool-issue) written in Kotlin for reproducing the ReadTimeout issue in micronaut 4: (Closed ticket: https://github.com/micronaut-projects/micronaut-core/issues/9843).

With the fix provided in Micronaut 4.1.2 and beyond, the ReadTimeout issue no longer exists. However, it throws another exception, which is ResponseClosedException. 

This repository has the code to reproduce the issue with ResponseClosedException, as described in () 

It is also to implements a Micronaut 4.1.x service that counts the number of bytes of https://micronaut.io/launch landing page.

# How to reproduce
Use Java 17. 

##  Run test
1. Run test(s) 
   ```
    ./gradlew test --info
   ```
   or

   run the ReproduceErrorTest.java

2. The current repository overrides the connection-pool-idle-timeout to 7s (default as 10s). When running the test for requests with delays of 6.9s, requests would consistently encounter this ResponseClosedException issue due to it utilizing the stale connections from connection pool that are about to timeout.
   If this value is not being overriden, check when the delay of 4.9s in the test, at some point, some request(s) fails as well.

3. If the issue is not found in step 2, do gradlew clean and redo step 1 & 2
   ```
    ./gradlew clean
   ```
