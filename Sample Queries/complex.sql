\c vmt

/*Query to find total worth of currency owned by an individual in dollars */
select u_id,sum(balance*rate) as Networth from account,rates where account.currency = rates.symbol group by u_id
order by Networth DESC;

/* Query to find the amount transferred and amount recieved by a user */
create view transfer as select coalesce(from_id,to_id) as u_id,Transfer_Amount,Amount_Recieved from 
(select from_id, coalesce(nullif(sum(amount*rate),0)) as Transfer_Amount from 
transaction,rates where transaction.currency = rates.symbol group by from_id) as transferred
full outer join (select to_id, coalesce(nullif(sum(amount*rate),0)) as Amount_Recieved 
from transaction,rates where transaction.currency = rates.symbol group by to_id) as recieved 
on transferred.from_id = recieved.to_id;

/*Total reserves of all currencies in the databsae*/
select currency, sum(balance) as reserves from account
group by currency;

/*All transactions made for each currency on a particular date*/
select currency, sum(amount) as total from transaction where
date(creation_time) = '2017-04-17'
group by currency;

/*List all users who have sent queries to admins in descending order*/
select u_id, count(u_id) from contacts
group by u_id order by count(u_id) desc;

/*All user details who have unconfirmed btc transactions*/
select u_id, fname, lname, phone, email from users where
u_id IN (select to_id from token
where currency = 'BTC' and amount > 1);


