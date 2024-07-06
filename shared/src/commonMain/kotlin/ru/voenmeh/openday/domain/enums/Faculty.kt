package ru.voenmeh.openday.domain.enums

import kotlinx.serialization.Serializable
import ru.voenmeh.openday.domain.constants.Constants

@Serializable
enum class Faculty(val value: Int) {
    A(0),
    E(1),
    I(2),
    O(3),
    R(4),
    SPO(5),
    VUC(6);

    override fun toString(): String {
        return when(this) {
            A -> {
                Constants.Strings.Faculty.byFacultyA
            }
            E -> {
                Constants.Strings.Faculty.byFacultyE
            }
            I -> {
                Constants.Strings.Faculty.byFacultyI
            }
            O -> {
                Constants.Strings.Faculty.byFacultyO
            }
            R -> {
                Constants.Strings.Faculty.byFacultyR
            }
            SPO -> {
                Constants.Strings.Faculty.byFacultySPO
            }
            else -> {
                Constants.Strings.Faculty.byFacultyVUC
            }
        }
    }

    companion object {
        fun fromInt(value: Int): Faculty {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("Unknown value: $value")
        }
    }
}
