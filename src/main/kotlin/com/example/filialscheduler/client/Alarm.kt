package com.example.filialscheduler.client

class Alarm private constructor(
    val message: String,
    val to: String?,
    val from: String?,
) {
    companion object {
        fun of(
            message: String,
        ) = Alarm(
            message = message,
            to = null,
            from = null,
        )

        fun of(
            message: String,
            to: String,
        ) = Alarm(
            message = message,
            to = to,
            from = null,
        )

        fun of(
            message: String,
            to: String,
            from: String,
        ) = Alarm(
            message = message,
            to = to,
            from = from,
        )
    }
}