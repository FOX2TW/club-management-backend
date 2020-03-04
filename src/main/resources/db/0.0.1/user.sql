create table t_user(
 `id` int unsigned not null primary key auto_increment,
 `username` varchar(100) not null,
 `phone` varchar(11) not null,
 `password` varchar(200) not null,
 `status` tinyint(1) not null default 1,
 `profile_image_path` varchar(200),
 `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
 `created_by` varchar(100),
 `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 `updated_by` varchar(100)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
