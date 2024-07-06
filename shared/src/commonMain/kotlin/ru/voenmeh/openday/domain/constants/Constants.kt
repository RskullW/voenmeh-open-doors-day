package ru.voenmeh.openday.domain.constants

object Constants {

    object Numbers {
        const val numbersOfFaculty = 7
        const val maxNumberQuest = 8
        const val maxEmsWordQuest = 64
        const val maxMistakes = 2
    }

    object Strings {
        val titleError: String = "Ой, кажется что-то пошло не так!"
        val unexpectedMessage: String = "Произошла непредвиденная ошибка. Пожалуйста, перезапустите приложение или обратитесь к специалисту."
        val checkAnswer: String = "Проверить ответ"
        val menuTitle: String = "БГТУ «ВОЕНМЕХ»\nим Д. Ф. Устинова"
        val inputField: String = "Поле для ввода"
        val learnMore: String = "Изучить подробнее"
        val incorrectAnswer: String = "Неправильный ответ :( Попробуй ещё раз!"
        val incorrectQr: String = "QR-код недействителен"
        val scanQr: String = "Сканирование QR-код"
        val scanQrError: String = "Ошибка при сканировании QR-кода"
        val repeatScanQr: String = "Вы уже открыли это достижение"

        object Achievement {
            val lockedTitle: String = "Карточка заблокирована!"
            val lockedDescription: String = "Для того чтобы разблокировать карточку, необходимо найти QR-код, отсканировать его и выполнить небольшое задание."
            val achievementUnlocked: String = "Жетон разблокирован!"
        }
        object Faculty {
            val byFacultyA = "факультета А"
            val byFacultyE = "факультета Е"
            val byFacultyI = "факультета И"
            val byFacultyO = "факультета О"
            val byFacultyR = "факультета Р"
            val byFacultySPO = "факультета СПО"
            val byFacultyVUC = "военного учебного центра"
        }
        object Common {
            val ok = "Ок"
            val by = "От"
            val close: String = "Закрыть"
            val achievement: String = "Достижения"
            val quest: String = "Задание"
            val hint: String = "Подсказка"
        }
    }

    object Region {
        val RU: String = "+7"
    }

}