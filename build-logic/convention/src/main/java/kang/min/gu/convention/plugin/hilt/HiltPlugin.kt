package kang.min.gu.convention.plugin.hilt

import kang.min.gu.convention.DependencyUnitValue
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

internal class HiltPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply("kotlin-kapt")
                apply("dagger.hilt.android.plugin")
            }

            extensions.configure<KaptExtension>("kapt") {
                correctErrorTypes = true
            }

            dependencies {
                with(DependencyUnitValue) {
                    implementation(libs.findLibrary("hilt.android").get())
                    kapt(libs.findLibrary("hilt.android.compiler").get())
                }
            }
        }
    }
}