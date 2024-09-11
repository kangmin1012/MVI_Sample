package kang.min.gu.convention.plugin

import com.android.build.api.dsl.ApplicationExtension
import kang.min.gu.convention.configuration.compose.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            with(pluginManager) {
                apply(libs.findPlugin("compose.compiler").get().get().pluginId)
            }

            configureAndroidCompose(extensions.getByType<ApplicationExtension>())
        }
    }
}