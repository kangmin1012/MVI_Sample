package kang.min.gu.convention.configuration.application

import com.android.build.api.dsl.ApplicationExtension

internal fun ApplicationExtension.configureApplicationDefault() {
    defaultConfig {
        applicationId = "kang.min.gu"
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
}