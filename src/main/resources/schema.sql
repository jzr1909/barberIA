CREATE DATABASE IF NOT EXISTS barberia
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE barberia;

CREATE TABLE IF NOT EXISTS roles (
                                     id_rol       BIGINT       NOT NULL AUTO_INCREMENT,
                                     nombre       VARCHAR(30)  NOT NULL,
    descripcion  VARCHAR(150),
    PRIMARY KEY (id_rol),
    UNIQUE KEY uk_roles_nombre (nombre)
    );

CREATE TABLE IF NOT EXISTS usuarios (
                                        id_usuario   BIGINT       NOT NULL AUTO_INCREMENT,
                                        id_rol       BIGINT       NOT NULL,
                                        nombre       VARCHAR(60)  NOT NULL,
    apellido     VARCHAR(60)  NOT NULL,
    correo       VARCHAR(120) NOT NULL,
    telefono     VARCHAR(20)  NOT NULL,
    password     VARCHAR(255) NOT NULL,
    foto_perfil  VARCHAR(255),
    estado       BOOLEAN      NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id_usuario),
    UNIQUE KEY uk_usuarios_correo (correo),
    CONSTRAINT fk_usuarios_rol FOREIGN KEY (id_rol) REFERENCES roles (id_rol)
    );

INSERT IGNORE INTO roles (nombre, descripcion) VALUES
    ('ADMIN', 'Administrador del sistema'),
    ('BARBERO', 'Barbero que atiende citas'),
    ('CLIENTE', 'Cliente que reserva servicios');

INSERT IGNORE INTO usuarios (id_rol, nombre, apellido, correo, telefono, password)
SELECT id_rol, 'Administrador', 'Principal', 'admin@barberia.com', '3000000000', '1234'
FROM roles WHERE nombre = 'ADMIN';

INSERT IGNORE INTO usuarios (id_rol, nombre, apellido, correo, telefono, password)
SELECT id_rol, 'Cliente', 'Prueba', 'cliente@barberia.com', '3111111111', '1234'
FROM roles WHERE nombre = 'CLIENTE';