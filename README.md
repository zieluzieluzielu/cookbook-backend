# CookBook - Backend application
![CookBook logo](https://i.imgur.com/Cd5H84o.png)




## PROJECT DESCRIPTION

CookBook application was created to help in gathering recipes in one place (app), with matching specific wine (type and product with price) to the dish (based on products) and is counting nutrition values (kcal, carbohydrates, protein and fat) of each ingredient.

Project is connected to two external API's: Spoonacular and Edamam.

**MAIN FEATURES:**

- gathering the recipes by products, recipe category, description, preparation time etc.
- filtering the recipes by i.a. product, recipe category, preparation time
- wine selector api -> founding the best fitting wine to recipe based on main ingredients
- nutrition values api -> summing all nutrition values (kcal, carbo, protein, fat) by taking quantity of each ingredient of recipe.
- adding recipes, products and ingredients

**ADDING RECIPE:**

1. First create a recipe
2. Then add an ingredients to recipe. If the products are not available in the list - add them.
3. After successfully added recipe with products, you can find the recipe in the list, see the suggested wines and calculated nutrition values.


**API free calls limits:**
- Edamam:  5000/month
- Spoonacular: 150/day

## TECHNOLOGIES USED

Project korzystający z Gradle i Spring Boot (2.3.0). Java 8
- lombok
- springweb
- gradle
- springboot 2.3.0
- Vaadin for front-end
- zapytania namedquery korzystające z języka HQL

do projketu zostala zbudowana lokalnie baza danych.



## HOW TO RUN

To have the full functionality - please import the dump of the database (MYSQL).

App database is currently stored internally, to be downloaded here: http://www.test.com

To run this project in your IDE, you need to have Lombok plugin and enabled annotation processing.
This project has a front-end instance:
Front-end address: http://localhost:8081/

database details (mysql)
url: jdbc:mysql://localhost:3306/cookbook?serverTimezone=Europe/Warsaw&useSSL=false&allowPublicKeyRetrieval=true
username / password: mzielinski


## FRONT-END

This project has a back-end instance. Front-end address: http://localhost:8081/

https://github.com/zieluzieluzielu/cookbook-frontend

## TO BE DEVELOPED
- improving the form of adding recipes (more friendly UI)
- layout adaptations
- fixing minor bugs
- user registration  / login (with WebSecurityConfigurerAdapter) + adding specific roles 
- postgresql connection
- deleting of objects
- poszerzenie bazy danych o informacje o logowaniach, data dodania przepisu
- tagi do dania?
- postgresql
- scheduler do przepisow sciaga kalorycznosc i zapisuje do bazy.
- add kcal per person
- infomracja o ilosciach osob : FOR 2 PEOPLE / FOR 4 PEOPLE

## DEMO APP

To be published soon.
