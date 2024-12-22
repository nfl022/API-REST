CREATE TABLE pacientes(

 id BIGINT NOT NULL AUTO_INCREMENT,
     nombre VARCHAR(100) NOT NULL,
     email VARCHAR(100) NOT NULL,
     documento VARCHAR(6) NOT NULL UNIQUE,
     telefono VARCHAR(100) NOT NULL,
     calle VARCHAR(100) NOT NULL,
     distrito VARCHAR(100) NOT NULL,
     complemento VARCHAR(100) NOT NULL,
     numero VARCHAR(20) NOT NULL,
     ciudad VARCHAR(100) NOT NULL,
       status TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY(id)
);