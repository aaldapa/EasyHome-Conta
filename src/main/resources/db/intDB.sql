INSERT INTO `users` (`id_user`, `cuenta_no_expirada`, `cuenta_no_bloqueada`, `apellido1`, `apellido2`, `credencial_no_expirada`, `activo`, `nombre`, `password`, `username`)
VALUES(1, 1, 1, 'Cuesta', 'Gutiérrez', 1, 1, 'Alberto', '123', 'Alberto');
INSERT INTO roles(id_rol,descripcion, role) VALUES (1,'', 'ROLE_ADMIN');
INSERT INTO roles(id_rol,descripcion, role) VALUES (2,'', 'ROLE_USER');
INSERT INTO usuarios_roles(id_usuario, id_rol)VALUES (1,1);
INSERT INTO usuarios_roles(id_usuario, id_rol) VALUES (1,2);