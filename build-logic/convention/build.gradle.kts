plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_18
    targetCompatibility = JavaVersion.VERSION_18
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.compiler.extension)
}

gradlePlugin {
    plugins {
        register("AndroidApplicationPlugin") {
            id = "kangmingu.plugin.application"
            implementationClass = "kang.min.gu.convention.plugin.AndroidApplicationPlugin"
        }

        register("AndroidApplicationComposePlugin") {
            id = "kangmingu.plugin.application.compose"
            implementationClass = "kang.min.gu.convention.plugin.AndroidApplicationComposePlugin"
        }
    }
}