package com.theucsrocha.model.util

import groovy.sql.Sql
import java.sql.SQLException

class PostgresConnectionFactory implements IConnectionFactory {

    private static Sql instance

    @Override
    Sql getConnection() {
        try {
            if (instance == null) {
                instance = Sql.newInstance("jdbc:postgresql://localhost:5432/Linketinder", "postgres", "1234", "org.postgresql.Driver")
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao conectar no PostgreSQL: ${e.message}", e)
        }
        return instance
    }
}
