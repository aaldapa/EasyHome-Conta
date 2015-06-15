/* Datos maestros para la tabla users
 */--password 123 (codificada)
INSERT INTO `easyhome-conta`.`users` (`id_user`, `cuenta_no_expirada`,`cuenta_no_bloqueada`, `apellido1`, `apellido2`, `credencial_no_expirada`, `activo`, `fecha_creacion`, `fecha_ultimo_login`, `nombre`,`password`,`username`, `photo`)
VALUES (1,1, 1, 'Cuesta', 'Gutiérrez',  1,  1,  CURDATE(),  NULL, 'Alberto','$2a$10$B4OHFDyJZrFeHVNVJmUT9.X3V5bDq7gaxEupX57Ywpm1YcoMU9.3i', 'Alberto', NULL);

/* Datos maestros para las tablas roles y user_roles
 * Para dar permisos de administracion al usuario 1
 */ 
INSERT INTO roles(id_rol,descripcion, role) VALUES (1,'', 'Administrador'),(2,'', 'Usuario');
INSERT INTO user_roles(id_user, id_rol) VALUES (1,1), (1,2);


/*Datos maestros para la tabla `tipo_productos` */
INSERT INTO `tipo_productos`(`id_tipo_producto`,`baja`,`nombre`,`notas`,`operable`) VALUES (1,'N','Cuenta corriente','Libreta de ahorro','S'),(2,'N','Depósito','Imposición a plazo fijo (I.P.F.)','N'),(3,'N','Tarjeta','Tarjeta de débito','S');

/* Sql para obtener los datos de las operaciones de un usuario categorizadas mediante su configuracion ERRONEA, no muestra las operaciones que ha categorizado otro usuario y el logado no*/
SELECT * FROM(
   (SELECT ca.nombre AS categoria, co.id_categoria, o.* FROM operaciones o 
	LEFT JOIN categoria_operaciones co ON o.id_operacion=co.id_operacion
	LEFT JOIN categorias ca ON ca.id_categoria=co.id_categoria WHERE o.id_producto=1) AS sql1)
WHERE id_categoria IS NULL OR id_categoria IN 
   (SELECT cat.id_categoria FROM categorias cat INNER JOIN users us ON cat.id_user=us.`id_user` WHERE us.id_user=1)
ORDER BY fecha;

/* Union de las select que me dan las operaciones de un usuario para un producto que tienen categorizacion
   con las operaciones que no tienen categorizacion.
*/
SET @usuario=1;

SELECT id_categoria ,`id_operacion`, fecha, concepto,importe, id_producto, notas FROM (
	(SELECT ca.nombre AS categoria, co.id_categoria, o.`id_operacion`, o.`fecha`, o.`concepto`, o.`importe`,o.`id_producto`, o.`notas`  FROM operaciones o 
		LEFT JOIN categoria_operaciones co ON o.id_operacion=co.id_operacion
		LEFT JOIN categorias ca ON ca.id_categoria=co.id_categoria 
		WHERE o.id_producto=1 AND ca.`id_user`=@usuario
	)
	UNION
	(SELECT 'NULL' AS categoria, 'NULL', o.`id_operacion`, o.`fecha`, o.`concepto`, o.`importe`,o.`id_producto`, o.`notas`  FROM operaciones o WHERE o.id_producto=1
		AND o.`id_operacion` NOT IN(
			SELECT o.id_operacion FROM operaciones o 
			LEFT JOIN categoria_operaciones co ON o.id_operacion=co.id_operacion
			LEFT JOIN categorias ca ON ca.id_categoria=co.id_categoria 
			WHERE o.id_producto=1 AND ca.`id_user`=@usuario)
	)
)AS con
ORDER BY fecha;
	