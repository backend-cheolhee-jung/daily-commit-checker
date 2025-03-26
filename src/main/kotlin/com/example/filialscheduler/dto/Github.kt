package com.example.filialscheduler.dto

import com.fasterxml.jackson.annotation.JsonCreator
import java.time.ZonedDateTime

data class GithubRepositoryName @JsonCreator constructor(
    val name: String
)

data class GitHubCommit(
    val sha: String,
    val commit: CommitDetail,
) {
    val yesterdayCommit
        get() = date.startsWith(
            ZonedDateTime.now().minusDays(1).toLocalDate().toString()
        )

    private val date: String
        get() = commit.committer.date
}

data class CommitDetail(
    val author: Committer,
    val message: String,
    val committer: Committer,
    val url: String,
)

data class Committer(
    val name: String,
    val email: String,
    val date: String,
)
