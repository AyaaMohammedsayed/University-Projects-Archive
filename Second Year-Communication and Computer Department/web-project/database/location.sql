-- Table structure for table `places`
--

CREATE TABLE `places` (
  `pid` int(10) NOT NULL,
  `pname` varchar(20) NOT NULL,
  `pcity` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `places`
--

INSERT INTO `places` (`pid`, `pname`, `pcity`) VALUES
(1, 'Pyramids', 'EGYPT'),
(2, 'DAHAB', 'EGYPT'),
(3, 'Sinaa', 'EGYPT'),
(4, 'Alexandria', 'EGYPT'),
(5, 'NOBA', 'EGYPT'),
(6, 'Old Egypt', 'EGYPT');
(7, 'Luxor', 'EGYPT');
(8, 'Siwaa', 'EGYPT');