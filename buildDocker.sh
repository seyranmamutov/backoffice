./mvnw clean package
docker build -t backoffice_backend .
docker save backoffice_backend > backoffice_backend
