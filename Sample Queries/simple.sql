\c vmt
select u_id, balance from account where currency = 'BTC';
select a_id, count(a_id) from transaction group by a_id;
select currency, sum(amount) as total from token group by currency;
select avg(balance) as Average_DASH from account where currency = 'DAS';
select (select count(*) from users) / (select count(*) from admin) as Users per admin;
select currency, rate from rates order by rate desc;
select * from contacts where date(creation_time) = date '2017-03-17';
