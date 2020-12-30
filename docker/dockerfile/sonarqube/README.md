#Contruir imagen desde un dockerfile
docker build --no-cache --rm -f "sonarqube-test.dockerfile" -t sonarqube-test:0.1.0 .

#Crear y ejecutar contenedor desde imagen
docker run --name sonarqube-test -p 9000:9000 sonarqube-test:0.1.0

#Abrir un shell en un contenedor
docker exec -it sonarqube-test sh
#Parar contenedor
docker stop sonarqube-test
#Iniciar contenedor
docker start sonarqube-test

#Restaurar por url un Quality profile desde fichero (devuelve json con parámetro profileKey)
curl -v -u admin:admin "http://localhost:9000/api/qualityprofiles/restore" --form backup="@assets/Customized rules.xml";
#Establecer como defecto un Qualityu profile (necesario parámetro profileKey devuelto por comando restore)
curl -v -u admin:admin "http://localhost:9000/api/qualityprofiles/set_default" --form profileKey=AWvVhXz6glCGhEHVh3LH


Documentación:

* https://hub.docker.com/_/sonarqube
* https://github.com/mc1arke/sonarqube-community-branch-plugin/