# Currency App

## REST API

Rest API provides 4 endpoints preceded by

```
    http://localhost:8080/api
```
#### 1. /get-all-available

```
    curl -X GET "http://localhost:8080/api/get-all-available"
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
            "requestUrl": "http://localhost:8080/api/all-available-currencies",
            "timeCreated": "2021-02-24T23:53:17.432"
        },
        {
            "requestUrl": "http://localhost:8080/api/all-available-currencies",
            "timeCreated": "2021-02-24T23:53:17.448"
        },
        {
            "requestUrl": "http://api.nbp.pl/api/exchangerates/rates/a/eur?format=json",
            "timeCreated": "2021-02-24T23:53:18.859"
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

By default, base is set to 'PLN' and currencies are set to 'EUR'.

If base or desired currency code is not contained in the app then it throws an exception (CurrencyNotSupportedException with code 400).



## GUI

In order to run frontend localy perform these steps:

1. Ensure that the Spring Boot application is on
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