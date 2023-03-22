# api-usuarios (prueba bci)
Api que permite la creacion de usuarios y telefonos asociados.

Api construidad con: 

- Api Rest / Json
- Java 11
- Spring boot
- Seguridad con token JWT
- Base datos H2 (base datos en memoria.).

<br>

## Uso de la api.  

<br>

1. Descargar el repositorio: https://github.com/andresvalb/api-usuarios.git

2. En el archivo \api-usuarios\src\main\resources\application.properties, se puede configurar la validacion del formato del password

```
#expresion regular para validar formato del password.
valida.password=^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{10}$

Con esta expresion se valida que tenga un largo de 10, que tenga mayusculas, minusculas y numeros.
```


3. En la raiz del proyecto ejecutar el comando de maven: mvn spring-boot:run

salida por consola de la ejecucion.
![Alt Text](https://raw.githubusercontent.com/andresvalb/imagenes/main/consolamaven.png)

Con esto ya tenemos desplegada el api-rest.

Disponibilizo un proyecto en postman para realizar pruebas.  [Descargar Proyecto Postman](https://raw.githubusercontent.com/andresvalb/imagenes/main/Api-Usuarios.postman_collection.json "download")








<br>
<br>


## Metodos


<br>
La api cuenta con un usuario ya creado: andresvalb@gmail.com y password: hunter123X

## 1. Generacion de token JWT.

POST -> localhost:8080/api/v1/token


Request body JSON: 

```json
{
    "username":"andresvalb@gmail.com",
    "password":"hunter123X"
}
```


Response JSON:
```json
{
  "token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbmRyZXN2YWxiQGdtYWlsLmNvbSIsImV4cCI6MTY3OTM2OTk2MywiaWF0IjoxNjc5MzYzOTYzfQ.pAgAxr9ZV3OAFzot7bKUDs239HBaznD-T0Kf4mWJgp2W_xshjzc_H0rarEtpSr6__9sA_LReqbsF85fxOgLP2w"
}
```


Con el token generado podemos consumir los otros metodos de la api, el token se debe pasar en el Header el parametro Authorization = token




![Alt Text](https://raw.githubusercontent.com/andresvalb/imagenes/main/header-Authorization.png)



## 2. Creacion de users.

POST -> localhost:8080/api/v1/users

Header
```
Authorization : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuM0Byb2RyaWd1ZXoub3JnIiwiZXhwIjoxNjc5MzY4NTgxLCJpYXQiOjE2NzkzNjI1ODF9.eRHogNlacctrsndu08LySIphGCtHvD8bdhTCmAli-kDjmY0pNwsR6_9OYdr82iRZGf7i50e4ovlM_v5toJEWWw
```

Request body JSON:

```json
{
    "name": "Gregorio Valenzuela",
    "email": "juan3@rodriguez.org",
    "password": "andresxx3X",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
}
```


Response JSON:
```json
{
    "id": "39d773ad-000b-4ef2-a366-931e08731312",
    "created": "2023-03-21T01:35:52.473+00:00",
    "modified": null,
    "last_login": "2023-03-21T01:35:52.473+00:00",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbmRyZXN2YWxiQGdtYWlsLmNvbSIsImV4cCI6MTY3OTM2ODUzOSwiaWF0IjoxNjc5MzYyNTM5fQ.WE7BLKhQnx0adA0ZIwra2lyaDKa9dzaYFqQtZCgbfHE0FxrI1SbtYGxH8_wjbzz5VsrObAz4N1WCLhKNHuDrDA",
    "isactive": true
}
```

![Alt Text](https://raw.githubusercontent.com/andresvalb/imagenes/main/creacion-usuarios.png)


## 3. Modificacion de users.

PUT -> localhost:8080/api/v1/users/juan3@rodriguez.org


Header
```
Authorization : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuM0Byb2RyaWd1ZXoub3JnIiwiZXhwIjoxNjc5MzY4NTgxLCJpYXQiOjE2NzkzNjI1ODF9.eRHogNlacctrsndu08LySIphGCtHvD8bdhTCmAli-kDjmY0pNwsR6_9OYdr82iRZGf7i50e4ovlM_v5toJEWWw
```

Request body JSON:

```json
{
    "name": "Gregorio Valenzuela d",
    "password": "andresxx3X"
}
```


Response JSON:
```json
{
    "id": "0e4c9199-9c8c-4959-a92e-a6a2bca465df",
    "created": "2023-03-20T23:51:11.367+00:00",
    "modified": "2023-03-20T23:51:15.178+00:00",
    "last_login": "2023-03-20T23:51:15.178+00:00",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbmRyZXN2YWxiQGdtYWlsLmNvbSIsImV4cCI6MTY3OTM1NjMyNSwiaWF0IjoxNjc5MzUwMzI1fQ.G8QmadMyY9wxUunWq4L3cg_EFcdvvNcpHf8FRFRJ4-EYmuHxwy_b6wZlG2kevsd4v5sD_YKstyeg5LhKU1f8zw",
    "isactive": true
}
```

![Alt Text](https://raw.githubusercontent.com/andresvalb/imagenes/main/update-usuario.png)

## 4. Recuperar informacion de un users.

GET -> localhost:8080/api/v1/users/juan3@rodriguez.org


Header
```
Authorization : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuM0Byb2RyaWd1ZXoub3JnIiwiZXhwIjoxNjc5MzY4NTgxLCJpYXQiOjE2NzkzNjI1ODF9.eRHogNlacctrsndu08LySIphGCtHvD8bdhTCmAli-kDjmY0pNwsR6_9OYdr82iRZGf7i50e4ovlM_v5toJEWWw
```

request :

```json

```


response :
```json
{
    "id": "39d773ad-000b-4ef2-a366-931e08731312",
    "name": "Gregorio Valenzuela",
    "email": "juan3@rodriguez.org",
    "password": "$2a$10$SX1M/1abY6OXkfCYVEZeGOLD5Hdd2ilP9lJmOc2rjumG7k1Buo4Sa",
    "created": "2023-03-21T01:35:52.473+00:00",
    "last_login": "2023-03-21T01:36:21.463+00:00",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbmRyZXN2YWxiQGdtYWlsLmNvbSIsImV4cCI6MTY3OTM2ODUzOSwiaWF0IjoxNjc5MzYyNTM5fQ.WE7BLKhQnx0adA0ZIwra2lyaDKa9dzaYFqQtZCgbfHE0FxrI1SbtYGxH8_wjbzz5VsrObAz4N1WCLhKNHuDrDA",
    "modified": null,
    "phones": [
        {
            "id": 3,
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        },
        {
            "id": 2,
            "number": "1234567",
            "citycode": "3",
            "contrycode": "57"
        },
        {
            "id": 1,
            "number": "1234567",
            "citycode": "2",
            "contrycode": "57"
        }
    ]
}
```

![Alt Text](https://raw.githubusercontent.com/andresvalb/imagenes/main/get-usuarios.png)




## 5. Recupero phones de un users.

GET -> localhost:8080/api/v1/users/juan3@rodriguez.org/phones


Header
```
Authorization : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuM0Byb2RyaWd1ZXoub3JnIiwiZXhwIjoxNjc5MzY4NTgxLCJpYXQiOjE2NzkzNjI1ODF9.eRHogNlacctrsndu08LySIphGCtHvD8bdhTCmAli-kDjmY0pNwsR6_9OYdr82iRZGf7i50e4ovlM_v5toJEWWw
```

request :

```json

```


response :
```json
[
    {
        "id": 3,
        "number": "123456789",
        "citycode": "1",
        "contrycode": "57"
    },
    {
        "id": 2,
        "number": "11112222",
        "citycode": "3",
        "contrycode": "57"
    },
    {
        "id": 1,
        "number": "111223344",
        "citycode": "2",
        "contrycode": "57"
    }
]
```

![Alt Text](https://raw.githubusercontent.com/andresvalb/imagenes/main/5.png)


## 6. Recupero un phones por id de un users.

GET -> localhost:8080/api/v1/users/juan3@rodriguez.org/phones/1


Header
```
Authorization : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuM0Byb2RyaWd1ZXoub3JnIiwiZXhwIjoxNjc5MzY4NTgxLCJpYXQiOjE2NzkzNjI1ODF9.eRHogNlacctrsndu08LySIphGCtHvD8bdhTCmAli-kDjmY0pNwsR6_9OYdr82iRZGf7i50e4ovlM_v5toJEWWw
```

request :

```json

```


response :
```json
[
    {
        "id": 3,
        "number": "123456789",
        "citycode": "1",
        "contrycode": "57"
    },
    {
        "id": 2,
        "number": "11112222",
        "citycode": "3",
        "contrycode": "57"
    },
    {
        "id": 1,
        "number": "111223344",
        "citycode": "2",
        "contrycode": "57"
    }
]
```

![Alt Text](https://raw.githubusercontent.com/andresvalb/imagenes/main/6.png)




## 7. Agregar un phones nuevo a la lista.

POST -> localhost:8080/api/v1/users/juan3@rodriguez.org/phones


Header
```
Authorization : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuM0Byb2RyaWd1ZXoub3JnIiwiZXhwIjoxNjc5MzY4NTgxLCJpYXQiOjE2NzkzNjI1ODF9.eRHogNlacctrsndu08LySIphGCtHvD8bdhTCmAli-kDjmY0pNwsR6_9OYdr82iRZGf7i50e4ovlM_v5toJEWWw
```

request :

```json

{
    "number": "666666666",
    "citycode": "2",
    "contrycode": "57"
}

```


response :
```json
[
    {
        "id": 3,
        "number": "123456789",
        "citycode": "1",
        "contrycode": "57"
    },
    {
        "id": 2,
        "number": "11112222",
        "citycode": "3",
        "contrycode": "57"
    },
    {
        "id": 1,
        "number": "111223344",
        "citycode": "2",
        "contrycode": "57"
    }
]
```

![Alt Text](https://raw.githubusercontent.com/andresvalb/imagenes/main/7.png)





## 8. Modificar un phone.

PUT -> localhost:8080/api/v1/users/juan3@rodriguez.org/phones/4


Header
```
Authorization : Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWFuM0Byb2RyaWd1ZXoub3JnIiwiZXhwIjoxNjc5MzY4NTgxLCJpYXQiOjE2NzkzNjI1ODF9.eRHogNlacctrsndu08LySIphGCtHvD8bdhTCmAli-kDjmY0pNwsR6_9OYdr82iRZGf7i50e4ovlM_v5toJEWWw
```

request :

```json

{
    "number": "666666666",
    "citycode": "6660",
    "contrycode": "5215"
}
```


response :
```json
{
    "id": 4,
    "number": "666666666",
    "citycode": "6660",
    "contrycode": "5215"
}
```

![Alt Text](https://raw.githubusercontent.com/andresvalb/imagenes/main/8.png)


