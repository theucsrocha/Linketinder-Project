FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY build/install/linketinder-backend /app

RUN chmod +x /app/bin/linketinder-backend

CMD ["/app/bin/linketinder-backend"]