package com.example.filialscheduler.extension

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.ReactiveHttpOutputMessage
import org.springframework.web.reactive.function.BodyInserter
import org.springframework.web.reactive.function.BodyInserters

fun String.toSlackMessage(): BodyInserter<String, ReactiveHttpOutputMessage> =
    BodyInserters.fromValue(
        ObjectMapper().writeValueAsString(
            mapOf("text" to this)
        )
    )