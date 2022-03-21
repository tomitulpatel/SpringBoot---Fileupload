CREATE TABLE `attachment` (
  `id` varchar(500) NOT NULL,
  `data` longblob,
  `file_name` varchar(5000) DEFAULT NULL,
  `file_type` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci