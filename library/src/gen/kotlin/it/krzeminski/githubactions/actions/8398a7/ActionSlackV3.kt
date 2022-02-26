// This file was generated using 'wrapper-generator' module. Don't change it by hand, your changes will
// be overwritten with the next wrapper code regeneration. Instead, consider introducing changes to the
// generator itself.
package it.krzeminski.githubactions.actions.`8398a7`

import it.krzeminski.githubactions.actions.Action
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

/**
 * Action: action-slack
 *
 * You can notify slack of GitHub Actions.
 *
 * [Action on GitHub](https://github.com/8398a7/action-slack)
 */
public class ActionSlackV3(
    /**
     * Specify success or failure or cancelled or custom.
     */
    public val status: ActionSlackV3.Status,
    /**
     * You can choose the items you want to add to the fields at the time of notification.
     * If you have more than one, please enter it in csv format.
     * e.g. commit,repo
     */
    public val fields: List<String>? = null,
    /**
     * json payload
     * refs
     * https://github.com/slackapi/node-slack-sdk/blob/v5.0.0/packages/webhook/src/IncomingWebhook.ts#L91-L106)
     */
    public val customPayload: String? = null,
    /**
     * Specify channel or here or `user_id`.
     * refs: https://api.slack.com/reference/surfaces/formatting#mentioning-users
     */
    public val mention: String? = null,
    /**
     * Specify success or failure or cancelled or custom or always.
     * Multiple statuses can be specified in csv format.
     * e.g. success,failure
     */
    public val ifMention: List<String>? = null,
    /**
     * User name for slack notification.
     */
    public val authorName: String? = null,
    /**
     * You can overwrite text.
     */
    public val text: String? = null,
    /**
     * override the legacy integration's default name.
     */
    public val username: String? = null,
    /**
     * an emoji code string to use in place of the default icon.
     */
    public val iconEmoji: String? = null,
    /**
     * an icon image URL string to use in place of the default icon.
     */
    public val iconUrl: String? = null,
    /**
     * override the legacy integration's default channel. This should be an ID, such as C8UJ12P4P.
     */
    public val channel: String? = null,
    /**
     * Use this if you want to overwrite the job name.
     */
    public val jobName: String? = null,
    /**
     * Use this if you wish to use a different GitHub token than the one provided by the workflow.
     */
    public val githubToken: String? = null,
    /**
     * Specify if you want to use GitHub Enterprise.
     */
    public val githubBaseUrl: String? = null
) : Action("8398a7", "action-slack", "v3") {
    @Suppress("SpreadOperator")
    public override fun toYamlArguments() = linkedMapOf(
        *listOfNotNull(
            "status" to status.stringValue,
            fields?.let { "fields" to it.joinToString(",") },
            customPayload?.let { "custom_payload" to it },
            mention?.let { "mention" to it },
            ifMention?.let { "if_mention" to it.joinToString(",") },
            authorName?.let { "author_name" to it },
            text?.let { "text" to it },
            username?.let { "username" to it },
            iconEmoji?.let { "icon_emoji" to it },
            iconUrl?.let { "icon_url" to it },
            channel?.let { "channel" to it },
            jobName?.let { "job_name" to it },
            githubToken?.let { "github_token" to it },
            githubBaseUrl?.let { "github_base_url" to it },
        ).toTypedArray()
    )

    public sealed class Status(
        public val stringValue: String
    ) {
        public object Success : ActionSlackV3.Status("success")

        public object Failure : ActionSlackV3.Status("failure")

        public object Cancelled : ActionSlackV3.Status("cancelled")

        public class Custom(
            customStringValue: String
        ) : ActionSlackV3.Status(customStringValue)
    }
}
