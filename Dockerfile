FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# 1. Объявляем аргументы, которые гитхаб передаст во время сборки
ARG PORT
ARG SPRING_PROFILES_ACTIVE
ARG APP_SECURITY_USERNAME
ARG APP_SECURITY_PASSWORD
ARG YDB_URL
ARG YDB_KEY

# 2. Превращаем их в постоянные переменные окружения внутри контейнера
ENV PORT=${PORT}
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}
ENV APP_SECURITY_USERNAME=${APP_SECURITY_USERNAME}
ENV APP_SECURITY_PASSWORD=${APP_SECURITY_PASSWORD}
ENV YDB_URL=${YDB_URL}
ENV YDB_KEY=${YDB_KEY}

COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
