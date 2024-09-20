plugins {
    id("kangmingu.plugin.library")
    id("kangmingu.plugin.hilt")
}

android {
    namespace = "kang.min.gu.datastore"
}

dependencies {
    implementation(libs.datastore.preferences)
}