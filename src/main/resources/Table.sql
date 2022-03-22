CREATE TABLE `attachment` (
  `id` varchar(500) NOT NULL,
  `data` longblob,
  `file_name` varchar(5000) DEFAULT NULL,
  `file_type` varchar(5000) DEFAULT NULL,
  `creation_date` date DEFAULT NULL,
  `email_id` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `User` (
  `email_id` varchar(50) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci