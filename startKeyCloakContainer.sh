docker run -p 8081:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin  \
                       -d  --name keycloak  \  
                       quay.io/keycloak/keycloak:23.0.7 start-dev  
read -p "Press enter to continue"
containerName=keycloak
docker logs --follow $containerName