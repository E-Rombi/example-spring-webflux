# example-spring-webflux

Stack:
 - Spring Webflux
 - Spring Data R2DBC
 - PostgreSQL
 - Docker
 - Flyway Migrations
 - Keycloak


## Tests

I accomplish stress test during 30 seconds on endpoint GET /hello without authentication e authorization.

Here're the results!

Fields | Average Response | Errors | Throughput | Requests
--- | --- | --- | --- |--- 
 Results | 1078ms | 0.0% | 1778 req/sec | 56.429 
 

*I'm starting with Webflux, so if you have constructive criticism i'm open!
