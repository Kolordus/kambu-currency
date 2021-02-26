# Currency App

## REST API

Rest API provides 4 endpoints preceded by

```
    http://localhost:8080/api
```
#### 1. /get-all-available

```
    curl -X GET "http://localhost:8080/api/get-all-available-currencies"
```
This endpoint returns list of strings with codes of supported currencies.

JSON response:

```
[
    "GBP",
    "EUR",
    "USD",
    "AUD",
    "JPY",
    "ISK",
    "HUF",
    "PLN"
]
```

    
#### 2. /convert

    
        curl -X GET "http://localhost:8080/api/convert" \
        -d amount=1 \
        -d base=PLN \
        -d desired=EUR
        
        
Takes 3 parameters: amount (number), base (string), desired (string)

Converts given base currency to desired one and multiplies it by given amount.
By default, amount is set to 1, base currency is set as 'PLN' and desired is set as 'EUR'.

If base or desired currency code is not contained in the app then it throws an exception (CurrencyNotSupportedException with code 400).

if amount is not a positive number it throws an exception (AmountMustBePositive with code 400).

Returned value is of type double.
    
    

#### 3. /all-requests

 ```
 curl -X GET "http://localhost:8080/api/all-requests"
 ```

Returns all api (from this API and external) requests persisted in the database.

Example response JSON:

```
    [
        {
            "requestUrl": "http://localhost:8080/api/rates?currencies=usd,isk",
            "timeCreated": "2021-02-26T17:24:14.041",
            "baseCurrency": "PLN",
            "desiredCurrencies": {
                "ISK": 33.85,
                "USD": 0.27
            },
            "invokedExternalApiUrls": [
                "http://api.nbp.pl/api/exchangerates/rates/a/usd?format=json",
                "http://api.nbp.pl/api/exchangerates/rates/a/isk?format=json"
            ]
        },
        {
            "requestUrl": "http://localhost:8080/api/all-requests",
            "timeCreated": "2021-02-26T17:25:36.004"
        }
    ]

```
#### 4./rates

 ```
 curl -X GET "http://localhost:8080/api/rates" \
         -d base=PLN \
         -d currencies=EUR,GBP
 ```

Takes 2 parameters: base (string) and currencies (array of strings).

Returns information about the exchange rate between the base currency and selected currencies.

By default, base is set to 'PLN' and if no currencies are specified then list of all available currencies with rates is return.

If base or desired currency code is not contained in the app then it throws an exception (CurrencyNotSupportedException with code 400).

Example response JSON:
```
   {
       "ISK": 33.85,
       "USD": 0.27
   }
```

## GUI

In order to run frontend locally perform these steps:

1. Ensure that the Spring Boot application is on (with profile local)
2. Open frontend folder in terminal and execute command

    ```
        npm install
    ```

3. then 

    ```
        ng serve
    ```
From now on GUI is available at
 
http://localhost:4200/home
