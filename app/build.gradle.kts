plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.step"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.step"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        
        // Thêm hỗ trợ MultiDex
        multiDexEnabled = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            // Tắt minification và shrinking trong debug để tránh D8 errors
            isShrinkResources = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    
    // Thêm cấu hình Java toolchain
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(11))
        }
    }
    
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
    
    // Cấu hình xử lý AAR dependencies
    packagingOptions {
        resources {
            excludes += listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/*.kotlin_module",
                "META-INF/proguard/**",
                "META-INF/maven/**",
                "META-INF/versions/**"
            )
        }
        jniLibs {
            useLegacyPackaging = true
        }
    }
    
    // Tắt D8 dexing in process để tránh lỗi NullPointerException
    dexOptions {
        preDexLibraries = false
        javaMaxHeapSize = "4g" // Tăng memory dành cho dex process
        keepRuntimeAnnotatedClasses = false
    }
}

dependencies {
    // Thêm hỗ trợ MultiDex
    implementation("androidx.multidex:multidex:2.0.1")
    
    // AndroidX Core - sử dụng phiên bản cụ thể để tránh xung đột
    implementation("androidx.core:core:1.12.0")
    
    // AndroidX AppCompat
    implementation("androidx.appcompat:appcompat:1.6.1")
    
    // Material Design
    implementation("com.google.android.material:material:1.10.0")
    
    // ConstraintLayout
    implementation("androidx.constraintlayout:constraintlayout:2.0.4") // Hạ phiên bản để tránh xung đột
    
    // Activity
    implementation("androidx.activity:activity:1.8.1") 
    
    // ViewModel và LiveData - Cùng phiên bản để tránh xung đột
    val lifecycleVersion = "2.6.1"
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-runtime:$lifecycleVersion")
    
    // Room Database - Cùng phiên bản để tránh xung đột
    val roomVersion = "2.5.2"
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    
    // Biểu đồ
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0-alpha")
    
    // WorkManager cho background tasks
    implementation("androidx.work:work-runtime:2.8.1")
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

// Xử lý xung đột dependencies
configurations.all {
    resolutionStrategy {
        // Force sử dụng các phiên bản cụ thể để tránh xung đột
        force(
            "androidx.core:core:1.12.0",
            "androidx.appcompat:appcompat:1.6.1",
            "androidx.lifecycle:lifecycle-runtime:2.6.1",
            "androidx.lifecycle:lifecycle-viewmodel:2.6.1",
            "androidx.lifecycle:lifecycle-livedata:2.6.1",
            "androidx.room:room-runtime:2.5.2",
            "androidx.constraintlayout:constraintlayout:2.0.4"
        )
        
        // Giới hạn các dependencies cũ để tránh xung đột
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
}