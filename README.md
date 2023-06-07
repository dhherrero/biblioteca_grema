
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

## MANUAL DE INSTALACIÓN
### 1 .Clonar el repositorio
```
git clone https://github.com/dhherrero/biblioteca_grema
```
### 2. Abrir dos terminales (CLI)
Se requiere la utilización de dos terminales, una de las cuales desempeña el rol de servidor mientras que la otra funciona como cliente.

### Arrancar el cliente
```
cd front-biblioteca
npm ci
npm start
```
Una vez arrancado entrar en el navegador e introducir:
```
http://localhost:5173/
```

## AUTORES DEL PROYECTO
- Tutor/a: Aikaterini Koutsou
- Desarrollador: Diego Herrero
