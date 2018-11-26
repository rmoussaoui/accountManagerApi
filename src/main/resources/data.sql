INSERT INTO user (user_id, firstname, lastname, username, password,user_type, role) VALUES
  (10, 'John', 'Doe', 'john.doe', '$2a$10$qtH0F1m488673KwgAfFXEOWxsoZSeHqqlB/8BTt3a6gsI5c2mdlfe', 'client', 'CLIENT'),
  (20, 'Luke', 'Skywalker','lsky', '$2a$10$qtH0F1m488673KwgAfFXEOWxsoZSeHqqlB/8BTt3a6gsI5c2mdlfe', 'client','CLIENT'),
  (30, '','','admin','$2y$10$sDKFic1HdimSsKxgoX.1F.sGGNHYyM7ZxkSXO2/G/nGdrL9J9lLHy','user','ADMIN');
INSERT INTO account (account_id, balance, owner_user_id) VALUES
	('1111', 30000.00, 10),
	('2222', 50000.00, 20),
	('3333', 40000.00, 20);
	
	


