package kang.min.gu.convention.configuration.compose

import com.android.build.api.dsl.CommonExtension
import kang.min.gu.convention.DependencyUnitValue
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension


internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *, *>
) {
    commonExtension.apply {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        buildFeatures {
            compose = true
        }

        extensions.getByType<ComposeCompilerGradlePluginExtension>().apply {
            enableStrongSkippingMode.set(true)
            includeSourceInformation.set(true)

            reportsDestination.set(
                layout.buildDirectory.dir("compose_compiler")
            )
        }

        dependencies {
            with(DependencyUnitValue) {
                val composeBom = platform(libs.findLibrary("androidx.compose.bom").get())
                implementation(composeBom)
                implementation(libs.findBundle("compose").get())
            }
        }
    }
}