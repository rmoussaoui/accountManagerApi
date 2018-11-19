# accountManagerApi
Bank account manager API

Exemples:

Consulter les transactions

GET  http://localhost:8080/account-manager/v1/accounts/2222/transactions


Faire un depot de fonds

POST  http://localhost:8080/account-manager/v1/accounts/2222/transactions

{
	"amount":1600,
	"transactionType": "D",
	"details":"Salaire du mois dernier."
}


Retirer des fonds

POST  http://localhost:8080/account-manager/v1/accounts/2222/transactions

{
	"amount":200,
	"transactionType": "W",
	"details":"Pour les courses."
}

