DROP TABLE IF EXISTS `DNS`.`HostAddress` ;

CREATE TABLE IF NOT EXISTS `DNS`.`HostAddress` (
  `ip` VARCHAR(15) NOT NULL,
  `dominio` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ip`));
  
insert into DNS.HostAddress values('172.217.28.100', 'www.google.com');
insert into DNS.HostAddress values('200.3.149.136', 'www.javeriana.edu.co');
insert into DNS.HostAddress values('104.244.42.193', 'twitter.com');
insert into DNS.HostAddress values('52.38.159.119', 'www.twitch.com');
insert into DNS.HostAddress values('52.203.125.101', 'www.twitch.com');
insert into DNS.HostAddress values('52.24.254.52', 'www.twitch.com');
insert into DNS.HostAddress values('162.159.128.233', 'www.discord.com');
insert into DNS.HostAddress values('162.159.136.232', 'www.discord.com');
insert into DNS.HostAddress values('162.159.137.232', 'www.discord.com');
insert into DNS.HostAddress values('162.159.135.232', 'www.discord.com');
insert into DNS.HostAddress values('162.159.138.232', 'www.discord.com');
insert into DNS.HostAddress values('149.154.167.99', 'www.telegram.org');
insert into DNS.HostAddress values('3.90.171.193', 'www.epicgames.com');
insert into DNS.HostAddress values('3.215.54.173', 'www.epicgames.com');
insert into DNS.HostAddress values('54.208.91.142', 'www.epicgames.com');
insert into DNS.HostAddress values('3.234.1.63', 'www.epicgames.com');
insert into DNS.HostAddress values('3.218.170.55', 'www.epicgames.com');
insert into DNS.HostAddress values('52.73.171.141', 'www.epicgames.com');
insert into DNS.HostAddress values('23.20.17.124', 'www.epicgames.com');
insert into DNS.HostAddress values('54.147.101.192', 'www.epicgames.com');
insert into DNS.HostAddress values('104.17.115.17', 'www.canva.com');
insert into DNS.HostAddress values('104.17.114.17', 'www.canva.com');
insert into DNS.HostAddress values('69.172.200.161', 'www.habbo.com');
insert into DNS.HostAddress values('86.105.245.69', 'www.panamericana.com');
insert into DNS.HostAddress values('194.224.110.42', 'www.movistar.com');
insert into DNS.HostAddress values('54.230.31.16', 'www.sgc.gov.co');
insert into DNS.HostAddress values('54.230.31.23', 'www.sgc.gov.co');
insert into DNS.HostAddress values('54.230.31.17', 'www.sgc.gov.co');
insert into DNS.HostAddress values('54.230.31.29', 'www.sgc.gov.co');
insert into DNS.HostAddress values('186.31.104.171', 'www.minsalud.gov.co');
insert into DNS.HostAddress values('104.21.48.221', 'www.scribe.com');
insert into DNS.HostAddress values('172.67.188.57', 'www.scribe.com');
insert into DNS.HostAddress values('190.145.152.118', 'www.sisben.gov.co');
insert into DNS.HostAddress values('186.113.7.153', 'www.sena.edu.co');
insert into DNS.HostAddress values('52.232.179.209', 'www.gobiernobogota.gov.co');
delete from DNS.HostAddress where ip =  '23.46.195.210'; 
select * from DNS.HostAddress;