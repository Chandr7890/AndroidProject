-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 28, 2024 at 12:58 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `patient`
--

-- --------------------------------------------------------

--
-- Table structure for table `asmwt`
--

CREATE TABLE `asmwt` (
  `pid` varchar(25) NOT NULL,
  `pbp` varchar(20) NOT NULL,
  `phr` varchar(7) NOT NULL,
  `pso2` varchar(7) NOT NULL,
  `pdys` varchar(7) NOT NULL,
  `pfat` varchar(7) NOT NULL,
  `ebp` varchar(7) NOT NULL,
  `ehr` varchar(7) NOT NULL,
  `eso2` varchar(7) NOT NULL,
  `edys` varchar(7) NOT NULL,
  `efat` varchar(7) NOT NULL,
  `t1` varchar(40) NOT NULL,
  `t2` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `asmwt`
--

INSERT INTO `asmwt` (`pid`, `pbp`, `phr`, `pso2`, `pdys`, `pfat`, `ebp`, `ehr`, `eso2`, `edys`, `efat`, `t1`, `t2`) VALUES
('er', '', '', '', '', '', '', '', '', '', '', '', ''),
('ffgh', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20241', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20242', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20243', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20247', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20249', '', '', '', '', '', '', '', '', '', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `bsmwt`
--

CREATE TABLE `bsmwt` (
  `pid` varchar(25) NOT NULL,
  `pbp` varchar(20) NOT NULL,
  `phr` varchar(7) NOT NULL,
  `pso2` varchar(7) NOT NULL,
  `pdys` varchar(7) NOT NULL,
  `pfat` varchar(7) NOT NULL,
  `ebp` varchar(7) NOT NULL,
  `ehr` varchar(7) NOT NULL,
  `eso2` varchar(7) NOT NULL,
  `edys` varchar(7) NOT NULL,
  `efat` varchar(7) NOT NULL,
  `t1` varchar(40) NOT NULL,
  `t2` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bsmwt`
--

INSERT INTO `bsmwt` (`pid`, `pbp`, `phr`, `pso2`, `pdys`, `pfat`, `ebp`, `ehr`, `eso2`, `edys`, `efat`, `t1`, `t2`) VALUES
('er', '', '', '', '', '', '', '', '', '', '', '', ''),
('ffgh', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20241', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20242', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20243', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20247', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20249', '', '', '', '', '', '', '', '', '', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `d_login`
--

CREATE TABLE `d_login` (
  `D_id` varchar(30) NOT NULL,
  `password` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `d_login`
--

INSERT INTO `d_login` (`D_id`, `password`) VALUES
('D1234', '1234');

-- --------------------------------------------------------

--
-- Table structure for table `d_profile`
--

CREATE TABLE `d_profile` (
  `D_id` varchar(20) NOT NULL,
  `D_name` varchar(50) NOT NULL,
  `D_dep` varchar(30) NOT NULL,
  `D_phno` varchar(12) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `d_profile`
--

INSERT INTO `d_profile` (`D_id`, `D_name`, `D_dep`, `D_phno`) VALUES
('D1234', 'gopikrishna', 'pulmo', '9344656793');

-- --------------------------------------------------------

--
-- Table structure for table `image`
--

CREATE TABLE `image` (
  `img` varchar(500) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `image`
--

INSERT INTO `image` (`img`) VALUES
('image 3.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `userName` varchar(20) NOT NULL,
  `password` varchar(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`userName`, `password`) VALUES
('smc20241', '0241'),
('smc20242', '0242'),
('smc20243', '0243'),
('smc20247', '0247'),
('ffgh', 'ffgh'),
('er', 'er'),
('smc20249', '0249');

-- --------------------------------------------------------

--
-- Table structure for table `patient_info`
--

CREATE TABLE `patient_info` (
  `pid` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `age` varchar(20) NOT NULL,
  `sex` varchar(5) NOT NULL,
  `cause` varchar(50) NOT NULL,
  `1Qnr1` varchar(20) NOT NULL,
  `2Qnr2` varchar(20) NOT NULL,
  `ess1` varchar(20) NOT NULL,
  `ess2` varchar(20) NOT NULL,
  `mmr1` varchar(100) NOT NULL,
  `mmr2` varchar(100) NOT NULL,
  `mbds1` varchar(20) NOT NULL,
  `mbds2` varchar(20) NOT NULL,
  `bpr` varchar(10) NOT NULL,
  `bprfvc` varchar(10) NOT NULL,
  `bprfev1` varchar(10) NOT NULL,
  `bprfef` varchar(10) NOT NULL,
  `bpo` varchar(10) NOT NULL,
  `bpofvc` varchar(10) NOT NULL,
  `bpofev1` varchar(10) NOT NULL,
  `bpofef` varchar(10) NOT NULL,
  `apr` varchar(10) NOT NULL,
  `aprfvc` varchar(10) NOT NULL,
  `aprfev1` varchar(10) NOT NULL,
  `aprfef` varchar(10) NOT NULL,
  `apo` varchar(10) NOT NULL,
  `apofvc` varchar(10) NOT NULL,
  `apofev1` varchar(10) NOT NULL,
  `apofef` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient_info`
--

INSERT INTO `patient_info` (`pid`, `name`, `age`, `sex`, `cause`, `1Qnr1`, `2Qnr2`, `ess1`, `ess2`, `mmr1`, `mmr2`, `mbds1`, `mbds2`, `bpr`, `bprfvc`, `bprfev1`, `bprfef`, `bpo`, `bpofvc`, `bpofev1`, `bpofef`, `apr`, `aprfvc`, `aprfev1`, `aprfef`, `apo`, `apofvc`, `apofev1`, `apofef`) VALUES
('smc20241', 'gopal', '20', 'male', 'smoking', '8', '5', '11', '', 'I stop for breath after walking about 100 yards or after a few minutes on the level', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20242', 'sai', '21', 'male', 'asthama', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20243', 'nani', '40', 'male', 'smoking', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20247', 'narasimha', '21', 'm', 'smoking', '7', '3', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('ffgh', 'hghg', 'ggh', 'hgh', 'gfgfgf', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('er', 'cfd', '20', 'sf', 'sf', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', ''),
('smc20249', 'sumnth', '21', 'male', 'smoking', '5', '4', '17', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '');

-- --------------------------------------------------------

--
-- Table structure for table `p_profile`
--

CREATE TABLE `p_profile` (
  `P_id` varchar(30) NOT NULL,
  `P_name` varchar(30) NOT NULL,
  `P_gender` varchar(10) NOT NULL,
  `P_phno` varchar(12) NOT NULL,
  `img` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `p_profile`
--

INSERT INTO `p_profile` (`P_id`, `P_name`, `P_gender`, `P_phno`, `img`) VALUES
('er', 'name', 'gender', 'phno', 'image'),
('ffgh', 'name', 'gender', 'phno', 'image'),
('smc20241', 'Gopal', 'male', '9347463758', 'img/smc20241.jpg'),
('smc20242', 'name', 'gender', 'phno', 'image'),
('smc20243', 'name', 'gender', 'phno', 'image'),
('smc20247', 'name', 'gender', 'phno', 'image'),
('smc20249', 'narasimha', 'gender', 'phno', 'img/smc20249.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `result`
--

CREATE TABLE `result` (
  `date` varchar(50) NOT NULL,
  `subid` varchar(40) NOT NULL,
  `resultval` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `result`
--

INSERT INTO `result` (`date`, `subid`, `resultval`) VALUES
('20-10-2024', '23', '657'),
('20-10-2024', '27', '657');

-- --------------------------------------------------------

--
-- Table structure for table `samimg`
--

CREATE TABLE `samimg` (
  `img` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `samimg`
--

INSERT INTO `samimg` (`img`) VALUES
('img/65734a4a9a310.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `tempappo`
--

CREATE TABLE `tempappo` (
  `pid` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `date` date NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tempappo`
--

INSERT INTO `tempappo` (`pid`, `name`, `date`, `status`) VALUES
('smc20241', 'gopal', '2024-01-26', 'Approved'),
('smc20242', 'sai', '2024-01-26', 'Approved'),
('smc20241', 'gopi', '2023-12-15', 'Approved'),
('smc20241', 'gopi', '2023-11-01', 'Approved'),
('smc20243', 'nani', '2024-02-05', 'Approved'),
('smc20241', 'gopi', '2024-02-02', 'Approved'),
('smc20241', 'chandra', '2024-02-03', 'Approved'),
('smc20247', 'narasimha', '2024-02-03', 'Approved'),
('smc20249', 'narasimha', '2024-02-04', 'Approved');

-- --------------------------------------------------------

--
-- Table structure for table `videos`
--

CREATE TABLE `videos` (
  `uVideos` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `videos`
--

INSERT INTO `videos` (`uVideos`) VALUES
('videos/SampleVideo_1280x720_2mb.mp4'),
('videos/SampleVideo_1280x720_2mb.mp4'),
('videos/SampleVideo_1280x720_2mb.mp4');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `asmwt`
--
ALTER TABLE `asmwt`
  ADD PRIMARY KEY (`pid`);

--
-- Indexes for table `bsmwt`
--
ALTER TABLE `bsmwt`
  ADD PRIMARY KEY (`pid`);

--
-- Indexes for table `d_login`
--
ALTER TABLE `d_login`
  ADD PRIMARY KEY (`D_id`);

--
-- Indexes for table `d_profile`
--
ALTER TABLE `d_profile`
  ADD PRIMARY KEY (`D_id`);

--
-- Indexes for table `p_profile`
--
ALTER TABLE `p_profile`
  ADD PRIMARY KEY (`P_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
