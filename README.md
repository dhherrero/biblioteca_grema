
# BIBLIOTECA GREMA   <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQK1EXoz3XJaySWZve9D05r3nb4zXsfVW2NphmTbOtr8KVs2JVbFze83CdBuCPJc_J9gkk&usqp=CAU" alt="libro" width="35">
Proyecto de aplicación web para la Asociación sociocultural de familias griego-españolas en Madrid. Se encarga de gestionar el inventario de los libros y los préstamos de los mismos. 
Trata de aplicación web con REST, es un sistema basado en la arquitectura REST que permite la comunicación entre el cliente y el servidor a través del protocolo HTTP, el cliente (front) se ha desarrollado en React, y el servidor (back) se ha desarrollado en Spring Boot.

## FUNCIONALIDADES 
- Añadir/eliminar libros
- Préstamos de libros
- Ampliar/cancelar préstamos de libros
- Gestión de usuarios de la biblioteca
## REQUISITOS PREVIOS
  Para lograr la plena funcionalidad de este proyecto, es imprescindible contar con la instalación previa de los siguientes componentes:
- Node.js
- Java version 8.0
- Maven 
- Mysql

## MANUAL DE INSTALACIÓN
### 1 .Clonar el repositorio
```
git clone https://github.com/dhherrero/biblioteca_grema
```
### 2. Base de datos
Abrir terminal y ejecutar los comandos de los siguientes apartados, donde 'tu_usuario' es el usuario de mysql. En el backend esta preconfigurado el usuario como 'root', y la password '12345678', en el caso de no querer hacer cambios en la preconfiguración del backend hay crear un usuario (aparatado 2.1) y contraseña de esas carcateristicas. En el caso de ya tener un usuario y contraseña en mysql ir directamente al apartado 2.2
#### 2.1 Crear usuarios correspondientes a la preconfiguración
Donde 'usuario_administrador' es el usuario con rol administrador en mysql
```
mysql -u usuario_administrador -p
CREATE USER 'root'@'localhost' IDENTIFIED BY '12345678';
GRANT ALL PRIVILEGES ON *.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```
#### 2.2 En el caso de no haber creado el usuario
Hay que cambiar la clase de conexión a la base de datos en el backend. En la ruta:
```
src/main/java/com/app/backbiblioteca/Back/config/DatabaseConfig.java
```
En las lineas 35 y 37 del documento
````
//usuario de la base de datos mysql
dataSource.setUsername("root");
//contraseña de la base de datos mysql
dataSource.setPassword("12345678");
````
Cambiar "root" por el nombre de usuario de mysql, y "12345678" por la contraseña del usuario mysql
#### 2.3 Scripts sql
Donde 'tu_usuario' es si se ha ejecutado el apartado 2.1 es 'root', y sino el usuario que se tenga en la terminal mysql
```
mysql -u tu_usuario -p
mysql -u tu_usuario -p library_grema < /mysql-scripts/01-create-tables.sql
mysql -u tu_usuario -p library_grema < /mysql-scripts/02-insert-data.sql

```
### 3. Abrir dos terminales (CLI)
Se requiere la utilización de dos terminales, una de las cuales desempeña el rol de servidor mientras que la otra funciona como cliente.

### 4. Arrancar el servidor
Mediante la primera terminal (servidor), se procede a ejecutar las siguientes instrucciones:
```
cd back-biblioteca
./mvnw clean package
java -jar target/Back-Biblioteca-0.0.1-SNAPSHOT.jar
```
### 5. Arrancar el cliente
Mediante la segunda terminal (cliente), se procede a ejecutar las siguientes instrucciones:
```
cd front-biblioteca
npm ci
npm start
```
### 6. Abrir navegador
Introducir la siguiente URL:
```
http://localhost:5173/
```

## AUTORES DEL PROYECTO
- Tutor/a: Aikaterini Koutsou
- Desarrollador: Diego Herrero
