CREATE TABLE `reservationsrequests` (
                                        `customer_ID` int,
                                        `first_name` varchar(30) DEFAULT NULL,
                                        `last_name` varchar(30) DEFAULT NULL,
                                        `email` varchar(30) DEFAULT NULL,
                                        `number` varchar(12) DEFAULT NULL,
                                        `time_from` time DEFAULT NULL,
                                        `time_to` time DEFAULT NULL,
                                        `reservation_date` date DEFAULT NULL,
                                        `number_of_seats` int DEFAULT NULL,
                                        `horse_power` int DEFAULT NULL,
                                        primary key (customer_ID)
);