#Contruir imagen desde un dockerfile
docker build --no-cache --rm -f "db2express-test.dockerfile" -t db2express-test:0.1.0 .

#Crear y ejecutar contenedor desde imagen
docker run --name db2express-test -p 22:22/tcp -p 50000:50000/tcp db2express-test:0.1.0 db2start
#Abrir un shell en un contenedor
docker exec -it db2express-test sh
#Parar contenedor
docker stop db2express-test
#Iniciar contenedor
docker start db2express-test