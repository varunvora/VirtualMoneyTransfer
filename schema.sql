DROP DATABASE vmt;
CREATE DATABASE vmt;
\c vmt

CREATE TABLE users (
	u_id integer primary key,
	fname varchar(50),
	lname varchar(50),
	email varchar(50) unique,
	phone bigint unique,
	password varchar(50));

CREATE TABLE rates (
	currency varchar(50) unique,
	symbol varchar(5) primary key,
	rate numeric(18, 8) check (rate >= 0));

CREATE TABLE account (
	u_id integer,
	currency varchar(50),
	balance numeric(18, 8) check (balance >= 0),
	primary key(u_id, currency),
	foreign key (u_id) references users on delete cascade,
	foreign key (currency) references rates on delete cascade);

CREATE TABLE token (
	t_id serial primary key,
	from_id integer references users(u_id) on delete cascade,
	to_id integer references users(u_id) on delete cascade,
	currency varchar(50) references rates(symbol) on delete cascade,
	amount numeric(18, 8) check (amount > 0),
	creation_time timestamp default now());

CREATE TABLE admin (
	a_id integer primary key,
	password varchar(50));

CREATE TABLE transaction (
	t_id integer primary key, 
	from_id integer references users(u_id) on delete set null,
	to_id integer references users(u_id) on delete set null,
	currency varchar(50) references rates(symbol) on delete cascade,
	amount numeric(18, 8) check (amount > 0),
	creation_time timestamp,
	a_id integer references admin on delete set null);

CREATE TABLE announcement (
	a_id integer references admin,
	message varchar(500),
	creation_time timestamp default now());

CREATE TABLE contacts (
	u_id integer references users,
	message varchar(500),
	creation_time timestamp default now(),
	primary key(u_id, message));
