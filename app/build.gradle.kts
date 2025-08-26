plugins {
    id("com.android.application") version "8.12.1"
    id("org.jetbrains.kotlin.android") version "2.0.20"
    id("com.google.devtools.ksp") version "2.0.20-1.0.24"
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20"
}

android {
    signingConfigs {
        create("release") {
            keyAlias = "mykey"
            keyPassword = "123456"
            storePassword = "123456"
            storeFile = file("/Users/pilafme/Library/CloudStorage/Dropbox/Code/Weekflow/app/my-release-key.keystore")
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfig = signingConfigs.getByName("release")
        }
    }
    namespace = "com.pilafme.weekflow"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pilafme.weekflow"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "2.0.20"
    }
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.compose.material3:material3:1.3.0")
    implementation("androidx.compose.ui:ui:1.7.0")
    implementation("androidx.compose.runtime:runtime:1.7.0")
    implementation("androidx.compose.runtime:runtime-livedata:1.7.0")
    implementation("androidx.compose.material:material-icons-extended:1.7.0")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.work:work-runtime-ktx:2.9.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    // Зависимости для юнит-тестов
    testImplementation("junit:junit:4.13.2")

    // Зависимости для инструментированных тестов
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}