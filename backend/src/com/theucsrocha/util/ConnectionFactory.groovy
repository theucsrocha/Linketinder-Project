package com.theucsrocha.util

import groovy.sql.Sql

class ConnectionFactory {
    static Sql create() {
        return Sql.newInstance("jdbc:postgresql://localhost:5432/Linketinder", "postgres", "1234", "org.postgresql.Driver")
    }
}