package com.example.filialscheduler.client

import com.example.filialscheduler.constant.EMAIL_ALARM_SENDER
import com.example.filialscheduler.constant.PROTOTYPE_BEAN
import com.example.filialscheduler.property.MailProperty
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import org.springframework.context.annotation.Scope
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import java.io.StringWriter
import kotlin.text.Charsets.UTF_8

@Scope(PROTOTYPE_BEAN)
@Component(EMAIL_ALARM_SENDER)
class EmailClient(
    private val javaMailSender: JavaMailSender,
    private val mailProperty: MailProperty,
) : AlarmSender {
    lateinit var to: String

    override suspend fun send(
        message: String,
    ) =
        coroutineScope {
            val writer = StringWriter()
            val msg = writer.appendHTML().html {
                head {
                    style {
                        +"""
                        $CONTENT {
                            font-size: 17px;
                            padding-right: 30px;
                            padding-left: 30px;
                        }
                    """.trimIndent()
                    }
                }

                body {
                    h1(TITLE) {
                        style = "font-size: 30px; padding-right: 30px; padding-left: 30px;"
                        +message
                    }
                }
            }.toString()

            val internetAddress = withContext(Dispatchers.IO) {
                InternetAddress(mailProperty.username, PERSONAL)
            }
            val mimeMessage = javaMailSender.createMimeMessage()
                .apply {
                    addRecipients(MimeMessage.RecipientType.TO, to)
                    subject = message
                    setText(msg, UTF_8.name(), HTML)
                    setFrom(internetAddress)
                }

            javaMailSender.send(mimeMessage)
        }

    companion object {
        private const val CONTENT = ".content"
        private const val TITLE = "title"
        private const val PERSONAL = "Cherhy Jung"
        private const val HTML = "html"
    }
}