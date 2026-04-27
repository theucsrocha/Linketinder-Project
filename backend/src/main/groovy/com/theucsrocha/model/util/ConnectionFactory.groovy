package com.theucsrocha.model.util

class ConnectionFactory {

    static IConnectionFactory create(String databaseType) {
        switch (databaseType?.trim()?.toLowerCase()) {
            case "postgres":
                return new PostgresConnectionFactory()
            default:
                throw new IllegalArgumentException("Banco não suportado: ${databaseType}")
        }
    }
}
