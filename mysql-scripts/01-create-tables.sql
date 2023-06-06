CREATE DATABASE IF NOT EXISTS `library-grema`;
USE `library-grema`;

CREATE TABLE `library-grema`.usuario(
	 nif VARCHAR(9)  PRIMARY KEY NOT NULL,
	 nombre longtext NOT NULL,
	 password longtext NOT NULL,
	 correoElectronico longtext not null,
	 fechaNacimiento date,
	 telefono BIGINT,
	 direccion longtext,
	 webPersonal longtext,
	 rol VARCHAR(20))
	 
CREATE TABLE `library-grema`.cuota (
	idCuota BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	year BIGINT not null,
	nifUsuario VARCHAR(9) not null,
	FOREIGN KEY (nifUsuario) REFERENCES usuario(nif))
	
CREATE TABLE `library-grema`.pareja (
	idPareja BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(30) NOT NULL,
	nifUsuario VARCHAR(9) not null,
	FOREIGN KEY (nifUsuario) REFERENCES usuario(nif))

CREATE TABLE `library-grema`.hijo (
	idHijo BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(30) NOT NULL,
	nifUsuario VARCHAR(9) not null,
	FOREIGN KEY (nifUsuario) REFERENCES usuario(nif))

CREATE TABLE `library-grema`.libro (
	idLibro BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	titulo longtext not null,
	autores VARCHAR(150) not null,
	isbn VARCHAR(50),
	edad BIGINT,
	editorial VARCHAR(50),
	fechaEdicion date,
	lenguaPublicacion VARCHAR(20),
	lenguaTraduccion VARCHAR(20),
	numeroPaginas BIGINT,
	descripcion longtext,
	edicion int,
	formato VARCHAR(100),
	genero VARCHAR(100),
	copias BIGINT,
	portada longtext,
	imagen2 longtext,
	imagen3 longtext
	)



CREATE TABLE `library-grema`.reservas (
		id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
		nifUsuario VARCHAR(9) not null,
		idLibro BIGINT UNSIGNED not null,
		fechaInicio date,
		fechaFin date,
		estadoReserva VARCHAR(20),
		FOREIGN KEY (nifUsuario) REFERENCES usuario(nif),
		FOREIGN KEY (idLibro) REFERENCES libro(id))
