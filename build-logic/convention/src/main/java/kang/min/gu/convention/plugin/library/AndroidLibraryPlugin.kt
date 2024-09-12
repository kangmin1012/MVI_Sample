package kang.min.gu.convention.plugin.library

import com.android.build.api.dsl.LibraryExtension
import kang.min.gu.convention.DependencyUnitValue
import kang.min.gu.convention.configuration.setJvmTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply(libs.findPlugin("android.library").get().get().pluginId)
                apply(libs.findPlugin("kotlin.android").get().get().pluginId)
            }

            extensions.configure<LibraryExtension> {
                compileSdk = 34

                defaultConfig {
                    consumerProguardFiles("consumer-rules.pro")
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
            }
            extensions.setJvmTarget()

            dependencies {
                with(DependencyUnitValue) {
                    implementation(libs.findBundle("kotlin.ktx").get())
                    androidTestImplementation(libs.findBundle("test").get())
                    debugImplementation(libs.findBundle("debug.test").get())
                }
            }
        }
    }
}