drop table admin;
drop table cliente;
drop table portafoglio;
drop table crypto;
drop table transazione;
drop table invoice;

create table Admin (email varchar(255) not null, cognome varchar(255) not null, nome varchar(255) not null, password varchar(255) not null, primary key (email)) engine=InnoDB;
create table Cliente (email varchar(255) not null, blocked bit not null, cognome varchar(255) not null, nome varchar(255) not null, password varchar(255) not null, tipologia enum ('COMPRATORE','VENDITORE') not null, primary key (email)) engine=InnoDB;
create table Crypto (simbolo varchar(255) not null, nome varchar(255) not null, urlImmagine varchar(255) not null, primary key (simbolo)) engine=InnoDB;
create table Invoice (id varchar(255) not null, coin varchar(255) not null, dataApertura datetime(6) not null, dataScadenza datetime(6) not null, descrizione varchar(255) not null, emailDst varchar(255), emailSrc varchar(255), invoiceId varchar(255) not null, statoDst enum ('ANNULLATO','CONFERMATO','IN_ATTESA') not null, statoSrc enum ('ANNULLATO','CONFERMATO','IN_ATTESA') not null, status varchar(255) not null, url varchar(255) not null, usdAmount float(53) not null, primary key (id)) engine=InnoDB;
create table Notifica (id bigint not null, emailCliente varchar(255), invoiceId varchar(255), primary key (id)) engine=InnoDB;
create table notificaIdSeq (next_val bigint) engine=InnoDB;
insert into notificaIdSeq values ( 1 );
create table Portafoglio (indirizzo varchar(255) not null, blocked bit not null, creazione datetime(6) not null, emailCliente varchar(255), qrCode varchar(255) not null, saldo float(53) not null, scadenza datetime(6) not null, simbolo varchar(255), primary key (indirizzo)) engine=InnoDB;
create table Transazione (id varchar(255) not null, amount float(53) not null, date datetime(6) not null, fees float(53) not null, tipo enum ('DEPOSITO','PRELIEVO') not null, toAddress varchar(255), txId varchar(255) not null, primary key (id)) engine=InnoDB;
alter table Portafoglio add constraint FK1ud685q7o86j7o7skdnnre5r4 foreign key (simbolo) references Crypto (simbolo);
create table RichiestaWithdraw (id bigint not null, emailCliente varchar(255), primary key (id)) engine=InnoDB;
create table withdrawIdSeq (next_val bigint) engine=InnoDB;
insert into withdrawIdSeq values ( 1 );

-- Password: Password01$
insert into crypto values ('TCN', 'TestCoin', 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/180px-No_image_available.svg.png');
insert into admin values ('admin@escram.com', false, 'Rossi', 'Mario', '$2a$10$efOjSSxwHIb0GWyHUzr0AOgBIU7aYkT/Be82NnxxywO22EgOiq2OK');
insert into cliente values ('mario.rossi@gmail.com', false, 'Rossi', 'Mario', '$2a$10$efOjSSxwHIb0GWyHUzr0AOgBIU7aYkT/Be82NnxxywO22EgOiq2OK', 'COMPRATORE');
insert into cliente values ('giorgio.verdi@gmail.com', false, 'Verdi', 'Giorgio', '$2a$10$efOjSSxwHIb0GWyHUzr0AOgBIU7aYkT/Be82NnxxywO22EgOiq2OK', 'VENDITORE');