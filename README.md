## Tech stack
1. **Frontend** : Sapper,Svelte,Npm,Gulp.
2. **Backend:**: Spring Boot(Core,Data,Web,Batch,Validation), Hibernate,Flyway,Swagger,Lombok
3. **Database:**: Mysql

## Run Demo
1. Make sure **docker** and **docker-compose** is installed
Versions needed:
 -docker version: 18.06.1-ce
 -docker-compose version: 1.28.2
2. Review **docker-compose.yaml** configuration. It should work with by default also.
3. Attention to **`/tmp/backoffice`** volume mapping for **back** container. It is where TSV files will be loaded.
4. Run **`./runDemo.sh`** to load docker images and start demo on localhost.
5. <http://localhost:3000> - access to application. You can play with CRUD here.
<http://localhost:8080/swagger-ui.html> - swagger UI. 
6. **/tmp/backoffice/brands**  folder is watched for new TSV files to import brands. Any files put there will be processed and imported to DB. 
7. **/tmp/backoffice/inventories**  folder is watched for new TSV files to import inventories. Any files put there will be processed and imported to DB. 

## Build
##### Backend
./mvnw clean package
##### Frontend
cd backoffice-ui && npm install && gulp prod-build

## Development
Run **gulp** from  backoffice-ui directory to start frontend for development.
 - npm version: 6.9.0
 - nodejs version: v10.16.3
