curl --location 'http://localhost:8081/realms/SpringBootKeycloak/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Cookie: JSESSIONID=EE37EFB9E48FFC92C6FC37B76725F6F6' \
--data-urlencode 'client_id=login-app' \
--data-urlencode 'username=user1' \
--data-urlencode 'password=user@1' \
--data-urlencode 'grant_type=password'