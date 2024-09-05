package my.company.name.sqldelightconverter

import kotlinx.datetime.LocalDate
import my.company.name.model.Person

fun my.company.name.Person.toDomainModel(): Person {
    return Person(
        cf = this.cf,
        name = this.name,
        surname = this.surname,
        birthday = this.birthday ?.let { LocalDate.parse(it) } ?: LocalDate.parse("1900-01-01"), // Convert birthday string to LocalDate
        city = this.city ?: ""
    )
}

fun Person.toSqlDelightModel(): my.company.name.Person {
    return my.company.name.Person(
        cf = this.cf,
        name = this.name,
        surname = this.surname,
        birthday = this.birthday.toString(), // Convert LocalDate to string
        city = this.city
    )
}