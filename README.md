# Дипломный проект по курсу «Тестировщик ПО»

## О проекте
В рамках данного проекта необходимо автоматизировать тестирование комплексного сервиса покупки тура, взаимодействующего с СУБД и API Банка.

База данных хранит информацию о заказах, платежах, статусах карт, способах оплаты.

Покупка тура возможна с помощью карты и в кредит. Данные по картам обрабатываются отдельными сервисами (Payment Gate, Credit Gate)

Сервис обрабатывает только специальные номера карт, которые предоставлены для тестирования:

    APPROVED карта - 4444 4444 4444 4441

    DECLINED карта - 4444 4444 4444 4442

Приложение в собственной СУБД должно сохранять информацию о том, успешно ли был совершён платёж и каким способом. Данные карт при этом сохранять не допускается.

## Документация

[План автоматизации тестирования веб-формы сервиса покупки туров интернет-банка]()

[Отчёт о проведённом тестировании]()

[Отчет о проведенной автоматизации ]()

## 1. Необходимое окружение.
* IntelliJ IDEA
* Docker Desktop
* Java jdk-11.0.19.7
* Google Chrome версия 118.0.5993.89


## Запуск приложения

Перед запуском необходимо выполнить следующие условия:

1. Клонировать [репозиторий](https://github.com/PniVedro/diplomQA).
2. Если не установлен Docker Desktop, необходимо его [скачать](https://docs.docker.com/desktop/) и установить.
3. Запустить Docker Desktop.
4. Открыть проект в IntelliJ IDEA

## Запуск
1. Запустить контейнеры командой в корне проекта `docker-compose up`

2. В новой вкладке терминала ввести следующую команду в зависимости от базы данных
- `java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:mysql://localhost:3306/app
  ` - для MySQL
- `java -jar artifacts/aqa-shop.jar --spring.datasource.url=jdbc:postgresql://localhost:5432/app` - для PostgreSQL
3. Приложение должно запуститься по адресу http://localhost:8080/

## Запуск тестов
1. В новой вкладке терминала ввести команду в зависимости от запущенной БД в п.2 Запуска:
- `.\gradlew clean test -DdbUrl=jdbc:mysql://localhost:3306/app` - для MySQL
- `.\gradlew clean test -DdbUrl=jdbc:postgresql://localhost:5432/app` - для PostgreSQL

## Формирование отчета по результатам тестирования
1. Для получения отчета ввести в терминале команду `.\gradlew allureServe`
2. Чтобы закрыть отчет, требуется ввести команду `Ctrl + C`, чтобы подтвердить выход, необходимо ввести `Y`
3. Для остановки работы контейнеров ввести команду `docker-compose down`