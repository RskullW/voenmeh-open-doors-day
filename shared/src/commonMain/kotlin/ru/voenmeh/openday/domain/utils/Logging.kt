package ru.voenmeh.openday.domain.utils

import ru.voenmeh.openday.domain.enums.TypeError

expect fun Log(title: String, message: String, typeError: TypeError = TypeError.DISPLAY)