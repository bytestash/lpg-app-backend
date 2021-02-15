mvn clean package -DskipTests
docker kill lpg-app-backend
docker rm lpg-app-backend
docker build . -t lpg-app-backend
docker run -d -p 8080:8080 --name lpg-app-backend lpg-app-backend
echo "Service Started..."
