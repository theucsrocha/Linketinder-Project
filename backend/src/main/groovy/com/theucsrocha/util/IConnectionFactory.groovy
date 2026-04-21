package com.theucsrocha.util

import groovy.sql.Sql

interface IConnectionFactory {
    Sql getConnection()
}
