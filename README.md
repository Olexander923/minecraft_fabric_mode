# minecraft_fabric_mode
for test

## Стек
Java 21, PostgreSQL, Minecraft API, Protobuf 3, Hibernate,Gradle 8.14

## Инструкция к запуску
1. Скопировать config/mymod.example.properties → config/mymod.properties
2. Настроить свои данные БД в mymod.properties
3. При необходимости собрать проект ./gradlew build
4. Запустить сервер ./gradlew runServer
5. Запустить клиента ./gradlew runClient --args="--username PlayerTest --offline"
6. Подключиться к серверу, нажать V,появиться окно для ввода сообщений

   
