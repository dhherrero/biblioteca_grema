CREATE TABLE usuario(
	 nif VARCHAR(9) PRIMARY KEY NOT NULL,
	 nombre longtext NOT NULL,
	 password longtext NOT NULL,
	 correoElectronico longtext not null,
	 fechaNacimiento date,
	 telefono BIGINT,
	 direccion longtext,
	 webPersonal longtext,
	 rol VARCHAR(20)
);

CREATE TABLE libro (
	id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
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
);

CREATE TABLE cuota (
	idCuota BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	year BIGINT not null,
	nifUsuario VARCHAR(9) not null,
	FOREIGN KEY (nifUsuario) REFERENCES usuario(nif) ON DELETE CASCADE
);

CREATE TABLE pareja (
	idPareja BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(30) NOT NULL,
	nifUsuario VARCHAR(9) not null,
	FOREIGN KEY (nifUsuario) REFERENCES usuario(nif) ON DELETE CASCADE
);

CREATE TABLE hijo (
	idHijo BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	nombre VARCHAR(30) NOT NULL,
	nifUsuario VARCHAR(9) not null,
	FOREIGN KEY (nifUsuario) REFERENCES usuario(nif) ON DELETE CASCADE
);

CREATE TABLE reservas (
	id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	nifUsuario VARCHAR(9) not null,
	idLibro BIGINT UNSIGNED not null,
	fechaInicio date,
	fechaFin date,
	estadoReserva VARCHAR(20),
	FOREIGN KEY (nifUsuario) REFERENCES usuario(nif) ON DELETE CASCADE,
	FOREIGN KEY (idLibro) REFERENCES libro(id) ON DELETE CASCADE
);
