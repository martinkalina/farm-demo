# Farm project

## Install local Postgres
docker run --name postgis -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgis/postgis

## Build project
mvn clean install

*Note: this application uses database even in maven build (JOOQ generation, tests)*

## Run project
mvn spring-boot:run