// This file was generated using 'wrapper-generator' module. Don't change it by hand, your changes will
// be overwritten with the next wrapper code regeneration. Instead, consider introducing changes to the
// generator itself.
package it.krzeminski.githubactions.actions.actions

import it.krzeminski.githubactions.actions.Action
import kotlin.Boolean
import kotlin.String
import kotlin.Suppress

/**
 * Action: Setup Java JDK
 *
 * Set up a specific version of the Java JDK and add the command-line tools to the PATH
 *
 * [Action on GitHub](https://github.com/actions/setup-java)
 */
public class SetupJavaV2(
    /**
     * The Java version to set up. Takes a whole or semver Java version. See examples of supported
     * syntax in README file
     */
    public val javaVersion: String,
    /**
     * Java distribution. See the list of supported distributions in README file
     */
    public val distribution: SetupJavaV2.Distribution,
    /**
     * The package type (jdk, jre, jdk+fx, jre+fx)
     */
    public val javaPackage: SetupJavaV2.JavaPackage? = null,
    /**
     * The architecture of the package
     */
    public val architecture: String? = null,
    /**
     * Path to where the compressed JDK is located
     */
    public val jdkFile: String? = null,
    /**
     * Set this option if you want the action to check for the latest available version that
     * satisfies the version spec
     */
    public val checkLatest: Boolean? = null,
    /**
     * ID of the distributionManagement repository in the pom.xml file. Default is `github`
     */
    public val serverId: String? = null,
    /**
     * Environment variable name for the username for authentication to the Apache Maven repository.
     * Default is $GITHUB_ACTOR
     */
    public val serverUsername: String? = null,
    /**
     * Environment variable name for password or token for authentication to the Apache Maven
     * repository. Default is $GITHUB_TOKEN
     */
    public val serverPassword: String? = null,
    /**
     * Path to where the settings.xml file will be written. Default is ~/.m2.
     */
    public val settingsPath: String? = null,
    /**
     * Overwrite the settings.xml file if it exists. Default is "true".
     */
    public val overwriteSettings: Boolean? = null,
    /**
     * GPG private key to import. Default is empty string.
     */
    public val gpgPrivateKey: String? = null,
    /**
     * Environment variable name for the GPG private key passphrase. Default is $GPG_PASSPHRASE.
     */
    public val gpgPassphrase: String? = null,
    /**
     * Name of the build platform to cache dependencies. It can be "maven" or "gradle".
     */
    public val cache: SetupJavaV2.BuildPlatform? = null,
    /**
     * Workaround to pass job status to post job step. This variable is not intended for manual
     * setting
     */
    public val jobStatus: String? = null
) : Action("actions", "setup-java", "v2") {
    @Suppress("SpreadOperator")
    public override fun toYamlArguments() = linkedMapOf(
        *listOfNotNull(
            "java-version" to javaVersion,
            "distribution" to distribution.stringValue,
            javaPackage?.let { "java-package" to it.stringValue },
            architecture?.let { "architecture" to it },
            jdkFile?.let { "jdkFile" to it },
            checkLatest?.let { "check-latest" to it.toString() },
            serverId?.let { "server-id" to it },
            serverUsername?.let { "server-username" to it },
            serverPassword?.let { "server-password" to it },
            settingsPath?.let { "settings-path" to it },
            overwriteSettings?.let { "overwrite-settings" to it.toString() },
            gpgPrivateKey?.let { "gpg-private-key" to it },
            gpgPassphrase?.let { "gpg-passphrase" to it },
            cache?.let { "cache" to it.stringValue },
            jobStatus?.let { "job-status" to it },
        ).toTypedArray()
    )

    public sealed class Distribution(
        public val stringValue: String
    ) {
        public object Adopt : SetupJavaV2.Distribution("adopt")

        public object AdoptHotspot : SetupJavaV2.Distribution("adopt-hotspot")

        public object AdoptOpenj9 : SetupJavaV2.Distribution("adopt-openj9")

        public object Liberica : SetupJavaV2.Distribution("liberica")

        public object Microsoft : SetupJavaV2.Distribution("microsoft")

        public object Temurin : SetupJavaV2.Distribution("temurin")

        public object Zulu : SetupJavaV2.Distribution("zulu")

        public class Custom(
            customStringValue: String
        ) : SetupJavaV2.Distribution(customStringValue)
    }

    public sealed class JavaPackage(
        public val stringValue: String
    ) {
        public object Jdk : SetupJavaV2.JavaPackage("jdk")

        public object Jre : SetupJavaV2.JavaPackage("jre")

        public object JdkPlusFx : SetupJavaV2.JavaPackage("jdk+fx")

        public object JrePlusFx : SetupJavaV2.JavaPackage("jre+fx")

        public class Custom(
            customStringValue: String
        ) : SetupJavaV2.JavaPackage(customStringValue)
    }

    public sealed class BuildPlatform(
        public val stringValue: String
    ) {
        public object Maven : SetupJavaV2.BuildPlatform("maven")

        public object Gradle : SetupJavaV2.BuildPlatform("gradle")

        public class Custom(
            customStringValue: String
        ) : SetupJavaV2.BuildPlatform(customStringValue)
    }
}