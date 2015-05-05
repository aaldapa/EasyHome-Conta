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


/* Sql para obtener los datos de las operaciones de un usuario categorizadas mediante su configuracion*/
SELECT * FROM(
   (SELECT ca.nombre AS categoria, co.id_categoria, o.* FROM operaciones o 
	LEFT JOIN categoria_operaciones co ON o.id_operacion=co.id_operacion
	LEFT JOIN categorias ca ON ca.id_categoria=co.id_categoria WHERE o.id_producto=1) AS sql1)
WHERE id_categoria IS NULL OR id_categoria IN 
   (SELECT cat.id_categoria FROM categorias cat INNER JOIN users us ON cat.id_user=us.`id_user` WHERE us.id_user=1)
ORDER BY fecha;