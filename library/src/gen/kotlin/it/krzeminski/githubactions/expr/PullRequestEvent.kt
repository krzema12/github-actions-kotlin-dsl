package it.krzeminski.githubactions.expr

import kotlin.String
import kotlin.collections.List

public object PullRequestEventPullRequestUser : ExprContext("github.event.pull_request.user") {
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

public object PullRequestEventPullRequestHeadUser :
    ExprContext("github.event.pull_request.head.user") {
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

public object PullRequestEventPullRequestHeadRepoOwner :
    ExprContext("github.event.pull_request.head.repo.owner") {
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

public object PullRequestEventPullRequestHeadRepo :
    ExprContext("github.event.pull_request.head.repo") {
  public val id: String by map

  public val node_id: String by map

  public val name: String by map

  public val full_name: String by map

  public val `private`: String by map

  public val owner: PullRequestEventPullRequestHeadRepoOwner =
      PullRequestEventPullRequestHeadRepoOwner

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

  public val allow_squash_merge: String by map

  public val allow_merge_commit: String by map

  public val allow_rebase_merge: String by map

  public val delete_branch_on_merge: String by map
}

public object PullRequestEventPullRequestHead : ExprContext("github.event.pull_request.head") {
  public val label: String by map

  public val ref: String by map

  public val sha: String by map

  public val user: PullRequestEventPullRequestHeadUser = PullRequestEventPullRequestHeadUser

  public val repo: PullRequestEventPullRequestHeadRepo = PullRequestEventPullRequestHeadRepo
}

public object PullRequestEventPullRequestBaseUser :
    ExprContext("github.event.pull_request.base.user") {
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

public object PullRequestEventPullRequestBaseRepoOwner :
    ExprContext("github.event.pull_request.base.repo.owner") {
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

public object PullRequestEventPullRequestBaseRepo :
    ExprContext("github.event.pull_request.base.repo") {
  public val id: String by map

  public val node_id: String by map

  public val name: String by map

  public val full_name: String by map

  public val `private`: String by map

  public val owner: PullRequestEventPullRequestBaseRepoOwner =
      PullRequestEventPullRequestBaseRepoOwner

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

  public val allow_squash_merge: String by map

  public val allow_merge_commit: String by map

  public val allow_rebase_merge: String by map

  public val delete_branch_on_merge: String by map
}

public object PullRequestEventPullRequestBase : ExprContext("github.event.pull_request.base") {
  public val label: String by map

  public val ref: String by map

  public val sha: String by map

  public val user: PullRequestEventPullRequestBaseUser = PullRequestEventPullRequestBaseUser

  public val repo: PullRequestEventPullRequestBaseRepo = PullRequestEventPullRequestBaseRepo
}

public object PullRequestEventPullRequestLinksSelf :
    ExprContext("github.event.pull_request._links.self") {
  public val href: String by map
}

public object PullRequestEventPullRequestLinksHtml :
    ExprContext("github.event.pull_request._links.html") {
  public val href: String by map
}

public object PullRequestEventPullRequestLinksIssue :
    ExprContext("github.event.pull_request._links.issue") {
  public val href: String by map
}

public object PullRequestEventPullRequestLinksComments :
    ExprContext("github.event.pull_request._links.comments") {
  public val href: String by map
}

public object PullRequestEventPullRequestLinksReviewComments :
    ExprContext("github.event.pull_request._links.review_comments") {
  public val href: String by map
}

public object PullRequestEventPullRequestLinksReviewComment :
    ExprContext("github.event.pull_request._links.review_comment") {
  public val href: String by map
}

public object PullRequestEventPullRequestLinksCommits :
    ExprContext("github.event.pull_request._links.commits") {
  public val href: String by map
}

public object PullRequestEventPullRequestLinksStatuses :
    ExprContext("github.event.pull_request._links.statuses") {
  public val href: String by map
}

public object PullRequestEventPullRequestLinks : ExprContext("github.event.pull_request._links") {
  public val self: PullRequestEventPullRequestLinksSelf = PullRequestEventPullRequestLinksSelf

  public val html: PullRequestEventPullRequestLinksHtml = PullRequestEventPullRequestLinksHtml

  public val issue: PullRequestEventPullRequestLinksIssue = PullRequestEventPullRequestLinksIssue

  public val comments: PullRequestEventPullRequestLinksComments =
      PullRequestEventPullRequestLinksComments

  public val review_comments: PullRequestEventPullRequestLinksReviewComments =
      PullRequestEventPullRequestLinksReviewComments

  public val review_comment: PullRequestEventPullRequestLinksReviewComment =
      PullRequestEventPullRequestLinksReviewComment

  public val commits: PullRequestEventPullRequestLinksCommits =
      PullRequestEventPullRequestLinksCommits

  public val statuses: PullRequestEventPullRequestLinksStatuses =
      PullRequestEventPullRequestLinksStatuses
}

public object PullRequestEventPullRequest : ExprContext("github.event.pull_request") {
  public val url: String by map

  public val id: String by map

  public val node_id: String by map

  public val html_url: String by map

  public val diff_url: String by map

  public val patch_url: String by map

  public val issue_url: String by map

  public val number: String by map

  public val state: String by map

  public val locked: String by map

  public val title: String by map

  public val user: PullRequestEventPullRequestUser = PullRequestEventPullRequestUser

  public val body: String by map

  public val created_at: String by map

  public val updated_at: String by map

  public val closed_at: String by map

  public val merged_at: String by map

  public val merge_commit_sha: String by map

  public val assignee: String by map

  public val assignees: List<String> = FakeList("github.event.pull_request.assignees")

  public val requested_reviewers: List<String> =
      FakeList("github.event.pull_request.requested_reviewers")

  public val requested_teams: List<String> = FakeList("github.event.pull_request.requested_teams")

  public val labels: List<String> = FakeList("github.event.pull_request.labels")

  public val milestone: String by map

  public val commits_url: String by map

  public val review_comments_url: String by map

  public val review_comment_url: String by map

  public val comments_url: String by map

  public val statuses_url: String by map

  public val head: PullRequestEventPullRequestHead = PullRequestEventPullRequestHead

  public val base: PullRequestEventPullRequestBase = PullRequestEventPullRequestBase

  public val _links: PullRequestEventPullRequestLinks = PullRequestEventPullRequestLinks

  public val author_association: String by map

  public val draft: String by map

  public val merged: String by map

  public val mergeable: String by map

  public val rebaseable: String by map

  public val mergeable_state: String by map

  public val merged_by: String by map

  public val comments: String by map

  public val review_comments: String by map

  public val maintainer_can_modify: String by map

  public val commits: String by map

  public val additions: String by map

  public val deletions: String by map

  public val changed_files: String by map
}

public object PullRequestEventRepositoryOwner : ExprContext("github.event.repository.owner") {
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

public object PullRequestEventRepository : ExprContext("github.event.repository") {
  public val id: String by map

  public val node_id: String by map

  public val name: String by map

  public val full_name: String by map

  public val `private`: String by map

  public val owner: PullRequestEventRepositoryOwner = PullRequestEventRepositoryOwner

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

public object PullRequestEventSender : ExprContext("github.event.sender") {
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

public object PullRequestEvent : ExprContext("github.event") {
  public val action: String by map

  public val number: String by map

  public val pull_request: PullRequestEventPullRequest = PullRequestEventPullRequest

  public val repository: PullRequestEventRepository = PullRequestEventRepository

  public val sender: PullRequestEventSender = PullRequestEventSender
}
