package kang.min.gu.convention.plugin

import com.android.build.api.dsl.ApplicationExtension
import kang.min.gu.convention.DependencyUnitValue
import kang.min.gu.convention.configuration.application.configureApplicationBuildType
import kang.min.gu.convention.configuration.application.configureApplicationDefault
import kang.min.gu.convention.configuration.configBasicOption
import kang.min.gu.convention.configuration.setJvmTarget
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

internal class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {

        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            with(pluginManager) {
                apply(libs.findPlugin("android.application").get().get().pluginId)
                apply(libs.findPlugin("kotlin.android").get().get().pluginId)
            }


            extensions.configure<ApplicationExtension> {
                namespace = "kang.min.gu"
                configureApplicationDefault()
                configureApplicationBuildType()
                configBasicOption()

                sourceSets {
                    getByName("main") {
                        java {
                            manifest.srcFile("src/main/AndroidManifest.xml")
                        }
                    }
                }


                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }
            extensions.setJvmTarget()

            dependencies { // 의존성 library 세팅
                with(DependencyUnitValue) {
                    implementation(libs.findBundle("kotlin.ktx").get())
                    androidTestImplementation(platform(libs.findLibrary("androidx.compose.bom").get()))
                    androidTestImplementation(libs.findBundle("test").get())
                    debugImplementation(libs.findBundle("debug.test").get())

                    implementation(project(":core:domain"))
                }
            }
        }
    }
}