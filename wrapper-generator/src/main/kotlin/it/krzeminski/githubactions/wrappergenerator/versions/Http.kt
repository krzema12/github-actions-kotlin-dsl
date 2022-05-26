package it.krzeminski.githubactions.wrappergenerator.versions

import it.krzeminski.githubactions.wrappergenerator.domain.ActionCoords
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

val ActionCoords.apiTagsUrl: String
    get() = "https://api.github.com/repos/$owner/$name/git/matching-refs/tags/v"

val ActionCoords.apiBranchesUrl: String
    get() = "https://api.github.com/repos/$owner/$name/git/matching-refs/heads/v"

@Serializable
data class GithubRef(
    val ref: String,
)

val json = Json { ignoreUnknownKeys = true }

val okhttpClient by lazy {
    OkHttpClient()
}

fun ActionCoords.fetchAvailableVersions(githubToken: String): List<Version> {
    val tagsRequest: Request = Request.Builder()
        .header("Authorization", "token $githubToken")
        .url(apiTagsUrl)
        .build()

    val tagsContent = okhttpClient.newCall(tagsRequest).execute().use { response ->
        if (response.isSuccessful.not()) {
            println(response.headers)
            error("API rate reached?  See https://docs.github.com/en/rest/overview/resources-in-the-rest-api#rate-limiting")
        }
        response.body!!.string()
    }
    val tagsData = json.decodeFromString<List<GithubRef>>(tagsContent)

    val branchesRequest: Request = Request.Builder()
        .header("Authorization", "token $githubToken")
        .url(apiBranchesUrl)
        .build()

    val branchesContent = okhttpClient.newCall(branchesRequest).execute().use { response ->
        if (response.isSuccessful.not()) {
            println(response.headers)
            error("API rate reached?  See https://docs.github.com/en/rest/overview/resources-in-the-rest-api#rate-limiting")
        }
        response.body!!.string()
    }
    val branchesData = json.decodeFromString<List<GithubRef>>(branchesContent)

    return tagsData.versions() + branchesData.versions()
}
