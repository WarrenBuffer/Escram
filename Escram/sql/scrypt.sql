drop table admin;
drop table cliente;
drop table portafoglio;
drop table crypto;
drop table transazione;
drop table invoice;

create table Admin (email varchar(255) not null, cognome varchar(255) not null, nome varchar(255) not null, password varchar(255) not null, primary key (email)) engine=InnoDB;
create table Cliente (email varchar(255) not null, blocked bit not null, cognome varchar(255) not null, nome varchar(255) not null, password varchar(255) not null, tipologia enum ('COMPRATORE','VENDITORE') not null, primary key (email)) engine=InnoDB;
create table Crypto (simbolo varchar(255) not null, nome varchar(255) not null, urlImmagine varchar(255) not null, primary key (simbolo)) engine=InnoDB;
create table Invoice (id varchar(255) not null, dataApertura datetime(6) not null, dataScadenza datetime(6) not null, indirizzoDst varchar(255), indirizzoSrc varchar(255), invoiceId varchar(255) not null, statoDst enum ('ANNULLATO','CONFERMATO','IN_ATTESA') not null, statoSrc enum ('ANNULLATO','CONFERMATO','IN_ATTESA') not null, status varchar(255) not null, url varchar(255) not null, usdAmount float(53) not null, primary key (id)) engine=InnoDB;
create table Portafoglio (indirizzo varchar(255) not null, blocked bit not null, creazione datetime(6) not null, emailCliente varchar(255), qrCode varchar(255) not null, saldo float(53) not null, scadenza datetime(6) not null, simbolo varchar(255), primary key (indirizzo)) engine=InnoDB;
create table Transazione (id varchar(255) not null, amount float(53) not null, date datetime(6) not null, fees float(53) not null, tipo enum ('DEPOSITO','PRELIEVO','TRASFERIMENTO') not null, toAddress varchar(255), txId varchar(255) not null, primary key (id)) engine=InnoDB;
alter table Portafoglio add constraint FK1ud685q7o86j7o7skdnnre5r4 foreign key (simbolo) references Crypto (simbolo);

-- Password: Password01$
insert into crypto values ('TCN', 'TestCoin', 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/180px-No_image_available.svg.png');
insert into cliente values ('mario.rossi@gmail.com', false, 'Rossi', 'Mario', '$2a$10$efOjSSxwHIb0GWyHUzr0AOgBIU7aYkT/Be82NnxxywO22EgOiq2OK', 'COMPRATORE');
insert into cliente values ('giorgio.verdi@gmail.com', false, 'Verdi', 'Giorgio', '$2a$10$efOjSSxwHIb0GWyHUzr0AOgBIU7aYkT/Be82NnxxywO22EgOiq2OK', 'VENDITORE');