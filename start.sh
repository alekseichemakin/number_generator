./mvnw clean package -DskipTests
cp target/number_generator-0.0.1-SNAPSHOT.jar src/main/docker
docker-compose -f src/main/docker/docker-compose.yml down
docker-compose -f src/main/docker/docker-compose.yml up