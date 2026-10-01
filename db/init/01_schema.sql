-- Esquema de la base de datos instituto_americano
-- Reconstruido a partir de los DAOs y entidades en src/modelo

CREATE DATABASE IF NOT EXISTS instituto_americano
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE instituto_americano;

-- -----------------------------------------------------
-- rol (entidades/Rol.java)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS rol (
    id_rol       INT AUTO_INCREMENT PRIMARY KEY,
    nombre_rol   VARCHAR(50)  NOT NULL UNIQUE,
    descripcion  VARCHAR(255)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- usuario (UsuarioDAO)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario (
    id_usuario      INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario  VARCHAR(50)  NOT NULL UNIQUE,
    contrasena      VARCHAR(255) NOT NULL,
    id_rol          INT          NOT NULL,
    activo          TINYINT(1)   NOT NULL DEFAULT 1,
    CONSTRAINT fk_usuario_rol FOREIGN KEY (id_rol) REFERENCES rol (id_rol)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- personal (PersonalDAO)
-- horario_* se guarda como texto HH:mm (validado en Validaciones.validarFormatoHora)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS personal (
    id_personal         INT AUTO_INCREMENT PRIMARY KEY,
    dni                 CHAR(8)       NOT NULL UNIQUE,
    nombres             VARCHAR(100)  NOT NULL,
    apellidos           VARCHAR(100)  NOT NULL,
    tipo_personal       ENUM('ADMINISTRATIVO', 'DOCENTE') NOT NULL,
    cargo               VARCHAR(100),
    fecha_contratacion  DATE          NOT NULL,
    salario_base        DECIMAL(10,2) NOT NULL DEFAULT 0,
    horario_entrada     VARCHAR(5)    NOT NULL,
    horario_salida      VARCHAR(5)    NOT NULL,
    activo              TINYINT(1)    NOT NULL DEFAULT 1
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- asistencia (AsistenciaDAO)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS asistencia (
    id_asistencia     INT AUTO_INCREMENT PRIMARY KEY,
    id_personal       INT  NOT NULL,
    fecha             DATE NOT NULL,
    hora_entrada      TIME,
    hora_salida       TIME,
    minutos_extras    INT  NOT NULL DEFAULT 0,
    minutos_tardanza  INT  NOT NULL DEFAULT 0,
    estado            ENUM('PRESENTE', 'AUSENTE', 'TARDANZA') NOT NULL DEFAULT 'PRESENTE',
    observaciones     VARCHAR(255),
    CONSTRAINT uq_asistencia_personal_fecha UNIQUE (id_personal, fecha),
    CONSTRAINT fk_asistencia_personal FOREIGN KEY (id_personal) REFERENCES personal (id_personal),
    INDEX idx_asistencia_fecha (fecha)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- planilla (PlanillaDAO)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS planilla (
    id_planilla       INT AUTO_INCREMENT PRIMARY KEY,
    periodo           CHAR(7)       NOT NULL UNIQUE,  -- YYYY-MM
    fecha_generacion  DATE          NOT NULL,
    total_planilla    DECIMAL(12,2) NOT NULL DEFAULT 0,
    estado            ENUM('GENERADA', 'PAGADA', 'ANULADA') NOT NULL DEFAULT 'GENERADA',
    generado_por      INT           NOT NULL,
    CONSTRAINT fk_planilla_usuario FOREIGN KEY (generado_por) REFERENCES usuario (id_usuario)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- detalle_planilla (PlanillaDAO)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS detalle_planilla (
    id_detalle           INT AUTO_INCREMENT PRIMARY KEY,
    id_planilla          INT           NOT NULL,
    id_personal          INT           NOT NULL,
    dias_trabajados      INT           NOT NULL DEFAULT 0,
    horas_extras         DECIMAL(8,2)  NOT NULL DEFAULT 0,
    monto_horas_extras   DECIMAL(10,2) NOT NULL DEFAULT 0,
    dias_ausencia        INT           NOT NULL DEFAULT 0,
    descuento_ausencias  DECIMAL(10,2) NOT NULL DEFAULT 0,
    minutos_tardanza     INT           NOT NULL DEFAULT 0,
    descuento_tardanzas  DECIMAL(10,2) NOT NULL DEFAULT 0,
    total_ingresos       DECIMAL(10,2) NOT NULL DEFAULT 0,
    total_descuentos     DECIMAL(10,2) NOT NULL DEFAULT 0,
    sueldo_neto          DECIMAL(10,2) NOT NULL DEFAULT 0,
    CONSTRAINT uq_detalle_planilla_personal UNIQUE (id_planilla, id_personal),
    CONSTRAINT fk_detalle_planilla FOREIGN KEY (id_planilla) REFERENCES planilla (id_planilla) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_personal FOREIGN KEY (id_personal) REFERENCES personal (id_personal)
) ENGINE=InnoDB;

-- -----------------------------------------------------
-- Datos iniciales
-- UsuarioDAO.validarLogin compara la contraseña en texto plano
-- -----------------------------------------------------
INSERT INTO rol (id_rol, nombre_rol, descripcion) VALUES
    (1, 'ADMINISTRADOR', 'Acceso total al sistema'),
    (2, 'RRHH', 'Gestión de personal, asistencia y planillas');

INSERT INTO usuario (nombre_usuario, contrasena, id_rol, activo) VALUES
    ('admin', 'admin123', 1, 1);
