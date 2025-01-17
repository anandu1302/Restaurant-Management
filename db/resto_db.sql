-- phpMyAdmin SQL Dump
-- version 4.1.6
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jan 16, 2025 at 04:41 PM
-- Server version: 5.6.16
-- PHP Version: 5.5.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `resto_db`
--

-- --------------------------------------------------------

-- ------------------------------------------------------

-- Table structure for table `resto_cart_tbl`
--

CREATE TABLE IF NOT EXISTS `resto_cart_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `food_id` varchar(50) NOT NULL,
  `quantity` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `resto_cheftable`
--

CREATE TABLE IF NOT EXISTS `resto_cheftable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(400) NOT NULL,
  `email` varchar(400) NOT NULL,
  `password` varchar(300) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `resto_complainttable`
--

CREATE TABLE IF NOT EXISTS `resto_complainttable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(300) NOT NULL,
  `order_id` varchar(100) NOT NULL,
  `complaint` varchar(150) NOT NULL,
  `date` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;


--
-- Table structure for table `resto_feedtable`
--

CREATE TABLE IF NOT EXISTS `resto_feedtable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(150) NOT NULL,
  `rating` varchar(100) NOT NULL,
  `feedback` varchar(150) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

--
-- Table structure for table `resto_foodtable`
--

CREATE TABLE IF NOT EXISTS `resto_foodtable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `resto_id` varchar(400) NOT NULL,
  `name` varchar(200) NOT NULL,
  `price` varchar(400) NOT NULL,
  `category` varchar(500) NOT NULL,
  `description` text NOT NULL,
  `food_image` varchar(400) NOT NULL,
  `status` varchar(400) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;


--
-- Table structure for table `resto_notes_tbl`
--

CREATE TABLE IF NOT EXISTS `resto_notes_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(100) NOT NULL,
  `notes` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;


--
-- Table structure for table `resto_ordertable`
--

CREATE TABLE IF NOT EXISTS `resto_ordertable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(100) NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `food_id` varchar(200) NOT NULL,
  `price` varchar(400) NOT NULL,
  `quantity` varchar(500) NOT NULL,
  `table_no` varchar(100) NOT NULL,
  `date` varchar(100) NOT NULL,
  `status` varchar(300) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;


--
-- Table structure for table `resto_paymentable`
--

CREATE TABLE IF NOT EXISTS `resto_paymentable` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(500) NOT NULL,
  `userid` varchar(300) NOT NULL,
  `amount` varchar(100) NOT NULL,
  `card_no` varchar(300) NOT NULL,
  `card_name` varchar(300) NOT NULL,
  `cvv` varchar(300) NOT NULL,
  `date` varchar(300) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=12 ;


--
-- Table structure for table `resto_resto_tbl`
--

CREATE TABLE IF NOT EXISTS `resto_resto_tbl` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(400) NOT NULL,
  `email` varchar(400) NOT NULL,
  `password` varchar(300) NOT NULL,
  `image` varchar(300) NOT NULL,
  `place` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Table structure for table `resto_user_regi`
--

CREATE TABLE IF NOT EXISTS `resto_user_regi` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(150) NOT NULL,
  `number` varchar(150) NOT NULL,
  `address` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
