package site.algosipeosseong.model

import java.time.LocalDate

data class Appointment(
    val date: LocalDate,
    val time: String,
    val content: String,
    val clinicId: String
)