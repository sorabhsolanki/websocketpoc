create table `tournament` (
  `id` VARCHAR(40) NOT NULL,
  `name` varchar(100) NOT NULL,
  `admin_name` varchar(40) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `is_active` TINYINT(1) NOT NULL,
  PRIMARY KEY(`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(40) NOT NULL,
  `country_code` varchar(3) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `is_active` TINYINT(1) NOT NULL,
  PRIMARY KEY(`id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into user(user_name, country_code, phone, is_active) VALUES ('rachit' , '91', '0123456789', TRUE );
insert into user(user_name, country_code, phone, is_active) VALUES ('sorabh' , '91', '0123456789', TRUE );
insert into user(user_name, country_code, phone, is_active) VALUES ('akhilesh' , '91', '0123456789', TRUE );
insert into user(user_name, country_code, phone, is_active) VALUES ('diwakar' , '91', '0123456789', TRUE );


create table `user_tournament` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `tournament_id` varchar(40) NOT NULL,
  PRIMARY KEY(`id`),
  CONSTRAINT `FK_user_id_to_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_tournament_id_to_tournament` FOREIGN KEY (`tournament_id`) REFERENCES `tournament` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
