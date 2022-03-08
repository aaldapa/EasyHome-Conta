/* Datos maestros para la tabla users
 */--password 123 (codificada)
INSERT INTO `users` (`id_user`, `cuenta_no_expirada`,`cuenta_no_bloqueada`, `apellido1`, `apellido2`, `credencial_no_expirada`, `activo`, `fecha_creacion`, `fecha_ultimo_login`, `nombre`,`password`,`username`, `photo`)
VALUES (1,1, 1, 'Cuesta', 'Gutiérrez',  1,  1,  CURDATE(),  NULL, 'Alberto','$2a$10$B4OHFDyJZrFeHVNVJmUT9.X3V5bDq7gaxEupX57Ywpm1YcoMU9.3i', 'Alberto', NULL);

/* Datos maestros para las tablas roles y user_roles
 * Para dar permisos de administracion al usuario 1
 */ 
INSERT INTO roles(id_rol,descripcion, role) VALUES (1,'', 'Administrador'),(2,'', 'Usuario');
INSERT INTO user_roles(id_user, id_rol) VALUES (1,1), (1,2);


/*Datos maestros para la tabla `tipo_productos` */
INSERT INTO `tipo_productos`(`id_tipo_producto`,`baja`,`nombre`,`notas`,`operable`) VALUES (1,'N','Cuenta corriente','Libreta de ahorro','S'),(2,'N','Depósito','Imposición a plazo fijo (I.P.F.)','N'),(3,'N','Tarjeta','Tarjeta de débito','S');

/* Creacion de la vista vista_operaciones*/
DROP TABLE IF EXISTS `vista_operaciones` ;

CREATE OR REPLACE
    VIEW `vista_operaciones` 
    AS
SELECT
  CONCAT(SUBSTR(`op`.`fecha`,1,4),SUBSTR(`op`.`fecha`,6,2)) AS `id_vista`,
  SUM(IF((`op`.`importe` >= 0 ),`op`.`importe`,0)) AS `ingresos`,
  SUM(IF((`op`.`importe` < 0 ),`op`.`importe`,0)) AS `gastos`,
  SUM(IF((`op`.`importe` >= 0 ),`op`.`importe`,0)) + SUM(IF((`op`.`importe` < 0 ),`op`.`importe`,0)) AS `balance`,
  SUBSTR(`op`.`fecha`,6,2) AS `mes_numero`,
  (CASE SUBSTR(`op`.`fecha`,6,2) WHEN '01' THEN 'Enero' WHEN '02' THEN 'Febrero' WHEN '03' THEN 'Marzo' WHEN '04' THEN 'Abril' WHEN '05' THEN 'Mayo' WHEN '06' THEN 'Junio' WHEN '07' THEN 'Julio' WHEN '08' THEN 'Agosto' WHEN '09' THEN 'Septiembre' WHEN '10' THEN 'Octubre' WHEN '11' THEN 'Noviembre' WHEN '12' THEN 'Diciembre' END) AS `mes`,
  SUBSTR(`op`.`fecha`,1,4) AS `anio`,
  `op`.`id_producto` AS `id_producto`
FROM `operaciones` `op`
WHERE `op`.traspaso=0
GROUP BY `op`.`id_producto`,SUBSTR(`op`.`fecha`,1,4),SUBSTR(`op`.`fecha`,6,2)
ORDER BY `op`.`id_producto`,SUBSTR(`op`.`fecha`,1,4),SUBSTR(`op`.`fecha`,6,2)
;	