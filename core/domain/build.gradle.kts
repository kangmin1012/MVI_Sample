plugins {
    id("kangmingu.plugin.library")
    id("kangmingu.plugin.hilt")
}

android {
    namespace = "kang.min.gu.domain"
}

dependencies {
    implementation(project(":core:data"))
}