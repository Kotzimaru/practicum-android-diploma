package ru.practicum.android.diploma.util.storage.db.converter

import ru.practicum.android.diploma.detail.domain.models.CurrencyDetailVacancy
import ru.practicum.android.diploma.detail.domain.models.DetailVacancy
import ru.practicum.android.diploma.detail.domain.models.SalaryDetailVacancy
import ru.practicum.android.diploma.search.data.models.dto.Salary
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.util.storage.db.entity.FavoriteEntity
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class FavoriteDbConverter {
    fun map(favoriteVacancy: FavoriteEntity): DetailVacancy {
        return DetailVacancy(
            id = favoriteVacancy.id,
            name = favoriteVacancy.name,
            idEmployment = favoriteVacancy.idEmployment,
            nameEmployment = favoriteVacancy.nameEmployment,
            alternateUrlEmployer = favoriteVacancy.alternateUrlEmployer,
            blacklistedEmployer = favoriteVacancy.blacklistedEmployer,
            idEmployer = favoriteVacancy.idEmployer,
            logoUrlsEmployerOriginal = favoriteVacancy.logoUrlsEmployerOriginal,
            nameEmployer = favoriteVacancy.nameEmployer,
            trustedEmployer = favoriteVacancy.trustedEmployer,
            urlEmployer = favoriteVacancy.urlEmployer,
            address = favoriteVacancy.areaName,
            idExperience = favoriteVacancy.idExperience,
            nameExperience = favoriteVacancy.nameExperience,
            salary = SalaryDetailVacancy(
                from = favoriteVacancy.from,
                to = favoriteVacancy.to,
                currency = CurrencyDetailVacancy.getCurrency(favoriteVacancy.currency!!),
            ),
            description = favoriteVacancy.description,
            keySkills = favoriteVacancy.keySkills,
            phone = favoriteVacancy.phone,
            contactName = favoriteVacancy.contactName,
            email = favoriteVacancy.email,
            comment = favoriteVacancy.comment,
            url = favoriteVacancy.url
        )
    }

    fun map(favoriteVacancy: DetailVacancy): FavoriteEntity {
        return FavoriteEntity(
            id = favoriteVacancy.id,
            name = favoriteVacancy.name,
            description = favoriteVacancy.description,
            url = favoriteVacancy.url,
            // area
            areaName = favoriteVacancy.address!!,
            // employer
            idEmployer = favoriteVacancy.idEmployer,
            alternateUrlEmployer = favoriteVacancy.alternateUrlEmployer,
            blacklistedEmployer = favoriteVacancy.blacklistedEmployer,
            logoUrlsEmployerOriginal = favoriteVacancy.logoUrlsEmployerOriginal,
            nameEmployer = favoriteVacancy.nameEmployer,
            trustedEmployer = favoriteVacancy.trustedEmployer,
            urlEmployer = favoriteVacancy.urlEmployer,
            // employment
            idEmployment = favoriteVacancy.idEmployment,
            nameEmployment = favoriteVacancy.nameEmployment,
            // experience
            idExperience = favoriteVacancy.idExperience,
            nameExperience = favoriteVacancy.nameExperience,
            // currency
            currency = favoriteVacancy.salary?.currency?.symbol,
            from = favoriteVacancy.salary?.from,
            gross = favoriteVacancy.salary?.gross!!,
            to = favoriteVacancy.salary?.to,
            // skills
            keySkills = favoriteVacancy.keySkills,
            // contacts
            phone = favoriteVacancy.phone,
            contactName = favoriteVacancy.contactName,
            email = favoriteVacancy.email,
            comment = favoriteVacancy.comment
        )
    }

    fun map2(favoriteVacancy: FavoriteEntity): Vacancy {
        return Vacancy(
            id = favoriteVacancy.id,
            area = favoriteVacancy.areaName,
            // val department: String?,
            employerImgUrl = favoriteVacancy.logoUrlsEmployerOriginal ?: "",
            employer = favoriteVacancy.nameEmployer!!,
            name = favoriteVacancy.name,
            salary = formSalaryString(
                Salary(
                    currency = favoriteVacancy.currency,
                    from = favoriteVacancy.from,
                    gross = favoriteVacancy.gross,
                    to = favoriteVacancy.to
                )
            ),
            type = ""
        )
    }

    private fun formSalaryString(salary: Salary?): String {
        val df = DecimalFormat()
        df.isGroupingUsed = true
        df.groupingSize = 3

        val decimalFormatSymbols = DecimalFormatSymbols()
        decimalFormatSymbols.setDecimalSeparator(',')
        decimalFormatSymbols.setGroupingSeparator(' ')

        df.decimalFormatSymbols = decimalFormatSymbols

        if (salary == null) return "зарплата не указана"
        if (salary.from == null && salary.to != null) {
            return "до ${df.format(salary.to)} ${salary.currency}"
        }
        if (salary.from != null && salary.to == null) {
            return "от ${df.format(salary.from)} ${salary.currency}"
        }
        if (salary.from == null && salary.to == null) {
            return "зарплата не указана"
        }
        return "от ${df.format(salary.from)} до ${df.format(salary.to)} ${salary.currency}"
    }
}
