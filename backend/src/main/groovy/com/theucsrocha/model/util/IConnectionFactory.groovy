package com.theucsrocha.model.util

import groovy.sql.Sql

interface IConnectionFactory {
    Sql getConnection()
}
