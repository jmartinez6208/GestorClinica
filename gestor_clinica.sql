-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-07-2021 a las 20:16:48
-- Versión del servidor: 10.4.16-MariaDB
-- Versión de PHP: 7.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gestor_clinica`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `citas`
--

CREATE TABLE `citas` (
  `ID_CIT` int(11) NOT NULL,
  `ID_PAC_ATE` varchar(10) NOT NULL,
  `ID_DOC_ATE` varchar(10) NOT NULL,
  `FEC_CIT` varchar(25) NOT NULL,
  `EST_CIT` varchar(15) NOT NULL,
  `COST_CIT` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `citas`
--

INSERT INTO `citas` (`ID_CIT`, `ID_PAC_ATE`, `ID_DOC_ATE`, `FEC_CIT`, `EST_CIT`, `COST_CIT`) VALUES
(1, '1805776208', '1805284997', '2021-Jun-09 10:00', 'PENDIENTE', '35.00'),
(2, '1805776208', '1805284997', '2021-Jun-10 11:00', 'ATENDIDA', '35.00'),
(3, '1805776208', '1805284997', '2021-Jul-07 9:00', 'ATENDIDA', '35.00'),
(4, '1805776208', '1805284997', '2021-Jul-08 10:00', 'PENDIENTE', '35.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `diagnosticos`
--

CREATE TABLE `diagnosticos` (
  `ID_DIAG` int(11) NOT NULL,
  `DESC_DIAG` varchar(100) DEFAULT NULL,
  `ID_PAC_PER` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `diagnosticos`
--

INSERT INTO `diagnosticos` (`ID_DIAG`, `DESC_DIAG`, `ID_PAC_PER`) VALUES
(1, 'dasd', '1805776208'),
(2, 'Diarrea', '1805776208'),
(3, 'Diarrea', '1805776208');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `doctores`
--

CREATE TABLE `doctores` (
  `ID_DOC` varchar(10) NOT NULL,
  `NOM_DOC` varchar(12) NOT NULL,
  `APE_DOC` varchar(12) NOT NULL,
  `CAR_DOC` varchar(30) NOT NULL,
  `ID_ESP_PER` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `doctores`
--

INSERT INTO `doctores` (`ID_DOC`, `NOM_DOC`, `APE_DOC`, `CAR_DOC`, `ID_ESP_PER`) VALUES
('1802176531', 'Marcos', 'Josue', 'Internista', 'E01'),
('1805284997', 'John', 'Arquito', 'Internista', 'E01'),
('1805776208', 'Mateo', 'Jacome', 'Especialista', 'E01');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `especialidades`
--

CREATE TABLE `especialidades` (
  `ID_ESP` varchar(3) NOT NULL,
  `NOM_ESP` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `especialidades`
--

INSERT INTO `especialidades` (`ID_ESP`, `NOM_ESP`) VALUES
('E01', 'Implantologia');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pacientes`
--

CREATE TABLE `pacientes` (
  `ID_PAC` varchar(10) NOT NULL,
  `NOM_PAC` varchar(12) NOT NULL,
  `APE_PAC` varchar(12) NOT NULL,
  `FEC_NAC` varchar(10) NOT NULL,
  `CEL_PAC` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `pacientes`
--

INSERT INTO `pacientes` (`ID_PAC`, `NOM_PAC`, `APE_PAC`, `FEC_NAC`, `CEL_PAC`) VALUES
('1800647522', 'David', 'Alain', '15/12/1997', '0993777256'),
('1802187892', 'Joao', 'Arcos', '19/10/1998', '0982444756'),
('1805776208', 'Gerardo', 'Perez', '1997-03-07', '0982666547');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tratamientos`
--

CREATE TABLE `tratamientos` (
  `ID_TRA` int(11) NOT NULL,
  `DESC_TRA` varchar(100) DEFAULT NULL,
  `ID_PAC_PER` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `tratamientos`
--

INSERT INTO `tratamientos` (`ID_TRA`, `DESC_TRA`, `ID_PAC_PER`) VALUES
(1, 'asdas', '1805776208'),
(2, 'Reposo', '1805776208'),
(3, 'Reposo', '1805776208');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `CED_USU` varchar(10) NOT NULL,
  `NOM_USU` varchar(12) NOT NULL,
  `APE_USU` varchar(12) NOT NULL,
  `CLA_USU` varchar(4) NOT NULL,
  `PER_USU` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`CED_USU`, `NOM_USU`, `APE_USU`, `CLA_USU`, `PER_USU`) VALUES
('1802', 'Mateo', 'Martinez', 'mmmm', 'clinica'),
('1803', 'John', 'Jacome', 'jjjj', 'cliente');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `citas`
--
ALTER TABLE `citas`
  ADD PRIMARY KEY (`ID_CIT`);

--
-- Indices de la tabla `diagnosticos`
--
ALTER TABLE `diagnosticos`
  ADD PRIMARY KEY (`ID_DIAG`),
  ADD KEY `FK_ID_PAC` (`ID_PAC_PER`);

--
-- Indices de la tabla `doctores`
--
ALTER TABLE `doctores`
  ADD PRIMARY KEY (`ID_DOC`),
  ADD KEY `FK_ID_ESP` (`ID_ESP_PER`);

--
-- Indices de la tabla `especialidades`
--
ALTER TABLE `especialidades`
  ADD PRIMARY KEY (`ID_ESP`);

--
-- Indices de la tabla `pacientes`
--
ALTER TABLE `pacientes`
  ADD PRIMARY KEY (`ID_PAC`);

--
-- Indices de la tabla `tratamientos`
--
ALTER TABLE `tratamientos`
  ADD PRIMARY KEY (`ID_TRA`),
  ADD KEY `FK_ID_PAC_PER` (`ID_PAC_PER`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`CED_USU`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `citas`
--
ALTER TABLE `citas`
  MODIFY `ID_CIT` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `diagnosticos`
--
ALTER TABLE `diagnosticos`
  MODIFY `ID_DIAG` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `tratamientos`
--
ALTER TABLE `tratamientos`
  MODIFY `ID_TRA` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `citas`
--
ALTER TABLE `citas`
  ADD CONSTRAINT `FK_ID_DOC_ATE` FOREIGN KEY (`ID_DOC_ATE`) REFERENCES `doctores` (`ID_DOC`),
  ADD CONSTRAINT `FK_ID_PAC_ATE` FOREIGN KEY (`ID_PAC_ATE`) REFERENCES `pacientes` (`ID_PAC`);

--
-- Filtros para la tabla `diagnosticos`
--
ALTER TABLE `diagnosticos`
  ADD CONSTRAINT `FK_ID_PAC` FOREIGN KEY (`ID_PAC_PER`) REFERENCES `pacientes` (`ID_PAC`);

--
-- Filtros para la tabla `doctores`
--
ALTER TABLE `doctores`
  ADD CONSTRAINT `FK_ID_ESP` FOREIGN KEY (`ID_ESP_PER`) REFERENCES `especialidades` (`ID_ESP`);

--
-- Filtros para la tabla `tratamientos`
--
ALTER TABLE `tratamientos`
  ADD CONSTRAINT `FK_ID_PAC_PER` FOREIGN KEY (`ID_PAC_PER`) REFERENCES `pacientes` (`ID_PAC`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
