# Currency App

## Docker Compose

Start terminal in main directory - You can easily find it by presence of file named

```
Docker-compose.yml
```

and then perform command 

```$xslt
docker-compose up
```

Three container will be built and run - database, backend app and frontend app.


## REST API

Rest API provides 4 endpoints preceded by

```
    http://localhost:8080/api
```
#### 1. /get-all-available

```
    curl -X GET "http://localhost:8080/api/all-available-currencies"
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
    
Example request:

```$xslt
http://localhost:8080/api/convert?amount=1234&base=usd&desired=eur
```

JSON response:

```
1017.44
```

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
## Profiles

Application has prepared 3 profiles: local, test and prod.
You can switch between them in 

```
   application.properties
```

changing line

```
spring.profiles.active=local
```

to desired profile. All the differences are in used databases.

#### local
uses built-in H2 Database

#### test
uses PostgreSQL installed on host machine (in order to launch app on this profile, You have to prepare database in psql, credentials are stored in application-test.properties)

#### prod
takes advantage of Docker and PostgreSQL container - if You going for containers make sure this is active profile.


## GUI

In order to run frontend locally (not using docker-compose, or frontend app would't load) perform these steps:

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
