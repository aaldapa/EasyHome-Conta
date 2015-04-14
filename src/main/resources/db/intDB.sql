--password 123 (codificada)
INSERT INTO user_roles(id_user, id_rol) VALUES (1,2);INSERT INTO `easyhome-conta`.`users`
            (`id_user`,
             `cuenta_no_expirada`,
             `cuenta_no_bloqueada`,
             `apellido1`,
             `apellido2`,
             `credencial_no_expirada`,
             `activo`,
             `fecha_creacion`,
             `fecha_ultimo_login`,
             `nombre`,
             `password`,
             `username`,
             `photo`)
VALUES (1,
        1,
        1,
        'Cuesta',
        'Gutiérrez',
        1,
        1,
        CURDATE(),
        NULL,
        'Alberto',
        '$2a$10$B4OHFDyJZrFeHVNVJmUT9.X3V5bDq7gaxEupX57Ywpm1YcoMU9.3i',
        'Alberto',
        NULL);
INSERT INTO roles(id_rol,descripcion, role) VALUES (1,'', 'Administrador');
INSERT INTO roles(id_rol,descripcion, role) VALUES (2,'', 'Usuario');
INSERT INTO user_roles(id_user, id_rol)VALUES (1,1);
INSERT INTO user_roles(id_user, id_rol) VALUES (1,2);