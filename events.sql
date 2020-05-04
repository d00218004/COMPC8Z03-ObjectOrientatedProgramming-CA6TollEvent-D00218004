-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 25, 2020 at 05:39 PM
-- Server version: 10.4.11-MariaDB
-- PHP Version: 7.4.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `toll_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `events`
--

CREATE TABLE `events` (
  `ID` int(11) NOT NULL,
  `REGISTRATION` text NOT NULL,
  `IMAGEID` text NOT NULL,
  `TIMESTAMP` bigint(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `events`
--

INSERT INTO `events` (`ID`, `REGISTRATION`, `IMAGEID`, `TIMESTAMP`) VALUES
(1, '191LH1111', '30402', 20200214101530),
(2, '182D3456', '30403', 20200214101531),
(3, '171MN3034', '30404', 20200214101532),
(4, '201LH304', '30405', 20200214131533),
(5, '192D3456', '30406', 20200214131538),
(6, '161C3456', '30407', 20200214141535),
(7, '201LH3025', '30408', 20200214151536),
(8, '192D33457', '30409', 20200214161537),
(9, '161C3457', '30410', 20200214221538),
(10, '191LH1111', '30411', 20200214231539),
(11, '191LH1112', '30412', 20200215121540),
(12, '191LH1113', '30413', 20200215121541),
(13, '191LH1114', '30414', 20200215121542),
(14, '201LH304', '30415', 20200215121543),
(15, '201LH305', '30416', 20200215121544),
(16, '201LH306', '30417', 20200215121545),
(17, '201LH307', '30418', 20200215121546),
(18, '201LH308', '30419', 20200215211547),
(19, '201XX309', '30420', 20200215221548),
(20, '191LH1111', '30421', 20200216111649),
(21, '152DL345', '30422', 20200216111650),
(22, '201LH304', '30423', 20200216111651),
(23, '201LH305', '30424', 20200216111652),
(24, '201LH3064', '30425', 20200216111653),
(25, '201LH3076', '30426', 20200216111654),
(26, '201LH3083', '30427', 20200216111655),
(27, '201LH309', '30428', 20200216111656),
(28, '201LH310', '30429', 20200216111657),
(29, '201LH311', '30430', 20200216111658),
(30, '201LH312', '30431', 20200216111659),
(31, '191LH1111', '30432', 20200217132001),
(32, '201CN3456', '30433', 20200217142502),
(33, '201CN3457', '30434', 20200217162003),
(34, '201LH304', '30435', 20200217162004),
(35, '181MH3456', '30436', 20200217173305),
(36, '181MH3456', '30437', 20200217182006),
(37, '181MH3458', '30438', 20200217182007),
(38, '181MH3459', '30439', 20200217185808),
(39, '181XX3460', '30440', 20200217232009),
(40, '181MH3461', '30441', 20200217232510),
(48, '201LH444', '33333', 1562537493),
(49, '201LH444', '33333', 1562537493),
(50, '201LH444', '33333', 1562537493),
(51, '201LH444', '33333', 1562537493);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `events`
--
ALTER TABLE `events`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
