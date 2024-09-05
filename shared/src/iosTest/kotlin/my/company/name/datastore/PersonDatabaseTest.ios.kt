package my.company.name.datastore

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import my.company.name.AppDatabase

actual fun getDriver() : SqlDriver {
    return NativeSqliteDriver(AppDatabase.Schema, "person.db")
}