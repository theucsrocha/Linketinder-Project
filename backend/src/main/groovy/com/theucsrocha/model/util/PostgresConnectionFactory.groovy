package com.theucsrocha.model.util

import groovy.sql.Sql
import java.sql.SQLException

class PostgresConnectionFactory implements IConnectionFactory {

    private static Sql instance

    @Override
    Sql getConnection() {
        try {
            if (instance == null) {
                instance = Sql.newInstance("jdbc:postgresql://postgres-db:5432/linketinder", "postgres", "1234", "org.postgresql.Driver")
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao conectar no PostgreSQL: ${e.message}", e)
        }
        return instance
    }
}
