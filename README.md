This repository written in Java is a similar version to another existing repository (https://github.com/alsoba13/micronaut4-read-timeout-in-connection-pool-issue) written in Kotlin for reproducing the ReadTimeout issue in micronaut 4: (Closed ticket: https://github.com/micronaut-projects/micronaut-core/issues/9843).

With the fix provided in Micronaut 4.1.2 and beyond, the ReadTimeout issue no longer exists. However, it throws another exception, which is ResponseClosedException. 

This repository has the code to reproduce the issue with ResponseClosedException, as described in () 

It implements a Micronaut 4.1.x service that counts the number of bytes of https://micronaut.io/launch landing page.

# How to reproduce
Use Java 17. 

##  Run test
1. Run test(s) 
   ```
    ./gradlew test --info
   ```
or

run the ReproduceErrorTest.java

2. Check when the delay is set to 4.9s, at some point, the request(s) fails due to it utilizing the stale connection from connection pool that is about to timeout. The current default value of readTimeout in HttpClientConfiguration class is 10 in micronaut 4.1.2 through 4.1.4. It's twice of the delay thus can reproduce the exception easily.

3. If the issue is not found in step 2, do gradlew clean and redo step 1 & 2
   ```
    ./gradlew clean
   ```
