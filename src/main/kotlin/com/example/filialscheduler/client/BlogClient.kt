package com.example.filialscheduler.client

import com.example.filialscheduler.constant.VELOG_ENDPOINT
import com.example.filialscheduler.property.CherhyProperty
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class BlogClient(
    private val cherhyProperty: CherhyProperty,
) {
    private val webClient = WebClient.create(VELOG_ENDPOINT)

    suspend fun getLastPostId() = fetchRenderedBlog().latestId

    private suspend fun fetchRenderedBlog(): String {
        val path = "/${cherhyProperty.blogUser}/posts"
        return webClient.get()
            .uri(path)
            .retrieve()
            .bodyToMono(String::class.java)
            .awaitSingle()
            ?: throw RuntimeException("블로그 정보를 가져 오는데 실패 했습니다.")
    }

    private val String.latestId: String
        get() {
            val initialData = this.split("initialData").last()
            return initialData.split("id\\\":\\\"")[1].substringBefore("\\")
        }
}