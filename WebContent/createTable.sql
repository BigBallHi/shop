CREATE TABLE `good` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userid` int(10) unsigned NOT NULL,
  `info` char(100) NOT NULL,
  `clicktimes` int(10) unsigned DEFAULT '0',
  `price` decimal(7,2) NOT NULL,
  `category` char(10) NOT NULL,
  `tbl` varchar(11) NOT NULL,
  `locked` tinyint(1) unsigned DEFAULT '0',
  `lockeduserid` int(10) unsigned DEFAULT '0',
  `lockedtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
 CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `phone` char(11) NOT NULL,
  `pwd` char(16) NOT NULL,
  `name` char(10) DEFAULT '二站新人',
  `info` char(100) DEFAULT '这是系统自带的签名',
  `avatar` char(11) DEFAULT 'avatar.jpg',
  `qq` char(10) DEFAULT '',
  `email` char(30) DEFAULT '',
  `realname` char(10) DEFAULT '',
  `location` char(10) DEFAULT '不公布-不公布',
  `sex` char(3) DEFAULT '不公布',
  `seesex` tinyint(1) unsigned DEFAULT '0',
  `seephone` tinyint(1) unsigned DEFAULT '0',
  `seeqq` tinyint(1) unsigned DEFAULT '0',
  `seeelse` tinyint(1) unsigned DEFAULT '0',
  `signtime` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
CREATE TABLE `need` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userid` int(10) unsigned NOT NULL,
  `info` char(100) NOT NULL,
  `price` decimal(7,2) NOT NULL,
  `category` char(10) NOT NULL,
  `locked` tinyint(1) unsigned DEFAULT '0',
  `lockeduserid` int(10) unsigned DEFAULT '0',
  `lockedtime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
CREATE TABLE `tag` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `goodid` int(10) unsigned NOT NULL,
  `tag` char(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
CREATE TABLE `pic` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `goodid` int(10) unsigned NOT NULL,
  `pic` varchar(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
CREATE TABLE `goodmsg` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `srcuserid` int(10) unsigned NOT NULL,
  `taruserid` int(10) unsigned NOT NULL,
  `goodid` int(10) unsigned NOT NULL,
  `msgcontent` char(100) DEFAULT '',
  `type` char(2) NOT NULL,
  `readed` tinyint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `needmsg` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `srcuserid` int(10) unsigned NOT NULL,
  `taruserid` int(10) unsigned NOT NULL,
  `needid` int(10) unsigned NOT NULL,
  `msgcontent` char(100) DEFAULT '',
  `readed` tinyint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
CREATE TABLE `goodcantlocked` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userid` int(10) unsigned NOT NULL,
  `goodid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
CREATE TABLE `needcantlocked` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `gmt_create` datetime DEFAULT CURRENT_TIMESTAMP,
  `gmt_modified` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `userid` int(10) unsigned NOT NULL,
  `needid` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
