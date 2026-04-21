package com.theucsrocha.util

class ConnectionFactory {

    static IConnectionFactory create(String databaseType) {
        switch (databaseType?.trim()?.toLowerCase()) {
            case "postgres":
            case "postgresql":
                return new PostgresConnectionFactory()
            default:
                throw new IllegalArgumentException("Banco não suportado: ${databaseType}")
        }
    }
}
