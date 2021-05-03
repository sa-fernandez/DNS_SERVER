DROP TABLE IF EXISTS `DNS`.`HostAddressIPv6` ;

CREATE TABLE IF NOT EXISTS `DNS`.`HostAddressIPv6` (
  `ip` VARCHAR(40) NOT NULL,
  `dominio` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`ip`));

insert into DNS.HostAddressIPv6 values('2a01:5b40:248:0:0:0:0:52', 'www.ipv6.org');
insert into DNS.HostAddressIPv6 values('2a02:9009:0:aa:aa01:0:0:0', 'www.movistar.com');
insert into DNS.HostAddressIPv6 values('2600:9000:21de:2400:5:93ba:5580:93a1', 'www.sgc.gov.co');
insert into DNS.HostAddressIPv6 values('2801:18c:0:0:0:0:0:171', 'www.minsalud.gov.co');
insert into DNS.HostAddressIPv6 values('2606:4700:3035:0:0:0:6815:30dd', 'www.scribe.com');
insert into DNS.HostAddressIPv6 values('2606:4700:3035:0:0:0:ac43:bc39', 'www.scribe.com');
insert into DNS.HostAddressIPv6 values('2800:482:4000:b00:0:0:0:90', 'www.sisben.gov.co');
insert into DNS.HostAddressIPv6 values('2801:1ca:2:1225:dc01:ceda:ac1d:1caa', 'www.sena.edu.co');
insert into DNS.HostAddressIPv6 values('2801:11:8800:c32:0:0:0:2', 'www.gobiernobogota.gov.co');
insert into DNS.HostAddressIPv6 values('2800:3f0:4005:400:0:0:0:2004', 'www.google.com');
insert into DNS.HostAddressIPv6 values('2001:67c:4e8:1003:2:100:0:a', 'www.telegram.org');
insert into DNS.HostAddressIPv6 values('2001:67c:4e8:1003:6:100:0:a', 'www.telegram.org');
insert into DNS.HostAddressIPv6 values('2606:4700:0:0:0:0:6811:7311', 'www.canva.com'); 
insert into DNS.HostAddressIPv6 values('2606:4700:0:0:0:0:6811:7211', 'www.canva.com');
delete from DNS.HostAddressIPv6 where ip =  '2606:4700:0:0:0:0:0:7311'; 
update DNS.HostAddressIPv6 set dominio = 'www.sgc.gov.co' where ip = '2600:9000:21de:2400:5:93ba:5580:93a1';
select * from DNS.HostAddressIPv6;