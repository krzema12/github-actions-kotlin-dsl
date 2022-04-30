package it.krzeminski.githubactions.expr

import kotlin.String
import kotlin.collections.List

public object ReleaseEventReleaseAuthor : ExprContext("github.event.release.author") {
  public val login: String by map

  public val id: String by map

  public val node_id: String by map

  public val avatar_url: String by map

  public val gravatar_id: String by map

  public val url: String by map

  public val html_url: String by map

  public val followers_url: String by map

  public val following_url: String by map

  public val gists_url: String by map

  public val starred_url: String by map

  public val subscriptions_url: String by map

  public val organizations_url: String by map

  public val repos_url: String by map

  public val events_url: String by map

  public val received_events_url: String by map

  public val type: String by map

  public val site_admin: String by map
}

public object ReleaseEventRelease : ExprContext("github.event.release") {
  public val url: String by map

  public val assets_url: String by map

  public val upload_url: String by map

  public val html_url: String by map

  public val id: String by map

  public val node_id: String by map

  public val tag_name: String by map

  public val target_commitish: String by map

  public val name: String by map

  public val draft: String by map

  public val author: ReleaseEventReleaseAuthor = ReleaseEventReleaseAuthor

  public val prerelease: String by map

  public val created_at: String by map

  public val published_at: String by map

  public val assets: List<String> = FakeList("github.event.release.assets")

  public val tarball_url: String by map

  public val zipball_url: String by map

  public val body: String by map
}

public object ReleaseEventRepositoryOwner : ExprContext("github.event.repository.owner") {
  public val login: String by map

  public val id: String by map

  public val node_id: String by map

  public val avatar_url: String by map

  public val gravatar_id: String by map

  public val url: String by map

  public val html_url: String by map

  public val followers_url: String by map

  public val following_url: String by map

  public val gists_url: String by map

  public val starred_url: String by map

  public val subscriptions_url: String by map

  public val organizations_url: String by map

  public val repos_url: String by map

  public val events_url: String by map

  public val received_events_url: String by map

  public val type: String by map

  public val site_admin: String by map
}

public object ReleaseEventRepository : ExprContext("github.event.repository") {
  public val id: String by map

  public val node_id: String by map

  public val name: String by map

  public val full_name: String by map

  public val `private`: String by map

  public val owner: ReleaseEventRepositoryOwner = ReleaseEventRepositoryOwner

  public val html_url: String by map

  public val description: String by map

  public val fork: String by map

  public val url: String by map

  public val forks_url: String by map

  public val keys_url: String by map

  public val collaborators_url: String by map

  public val teams_url: String by map

  public val hooks_url: String by map

  public val issue_events_url: String by map

  public val events_url: String by map

  public val assignees_url: String by map

  public val branches_url: String by map

  public val tags_url: String by map

  public val blobs_url: String by map

  public val git_tags_url: String by map

  public val git_refs_url: String by map

  public val trees_url: String by map

  public val statuses_url: String by map

  public val languages_url: String by map

  public val stargazers_url: String by map

  public val contributors_url: String by map

  public val subscribers_url: String by map

  public val subscription_url: String by map

  public val commits_url: String by map

  public val git_commits_url: String by map

  public val comments_url: String by map

  public val issue_comment_url: String by map

  public val contents_url: String by map

  public val compare_url: String by map

  public val merges_url: String by map

  public val archive_url: String by map

  public val downloads_url: String by map

  public val issues_url: String by map

  public val pulls_url: String by map

  public val milestones_url: String by map

  public val notifications_url: String by map

  public val labels_url: String by map

  public val releases_url: String by map

  public val deployments_url: String by map

  public val created_at: String by map

  public val updated_at: String by map

  public val pushed_at: String by map

  public val git_url: String by map

  public val ssh_url: String by map

  public val clone_url: String by map

  public val svn_url: String by map

  public val homepage: String by map

  public val size: String by map

  public val stargazers_count: String by map

  public val watchers_count: String by map

  public val language: String by map

  public val has_issues: String by map

  public val has_projects: String by map

  public val has_downloads: String by map

  public val has_wiki: String by map

  public val has_pages: String by map

  public val forks_count: String by map

  public val mirror_url: String by map

  public val archived: String by map

  public val disabled: String by map

  public val open_issues_count: String by map

  public val license: String by map

  public val forks: String by map

  public val open_issues: String by map

  public val watchers: String by map

  public val default_branch: String by map
}

public object ReleaseEventSender : ExprContext("github.event.sender") {
  public val login: String by map

  public val id: String by map

  public val node_id: String by map

  public val avatar_url: String by map

  public val gravatar_id: String by map

  public val url: String by map

  public val html_url: String by map

  public val followers_url: String by map

  public val following_url: String by map

  public val gists_url: String by map

  public val starred_url: String by map

  public val subscriptions_url: String by map

  public val organizations_url: String by map

  public val repos_url: String by map

  public val events_url: String by map

  public val received_events_url: String by map

  public val type: String by map

  public val site_admin: String by map
}

public object ReleaseEvent : ExprContext("github.event") {
  public val action: String by map

  public val release: ReleaseEventRelease = ReleaseEventRelease

  public val repository: ReleaseEventRepository = ReleaseEventRepository

  public val sender: ReleaseEventSender = ReleaseEventSender
}
