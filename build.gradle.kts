// Top-level build.gradle.kts file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "7.0.2" apply false
}

// Cấu hình cho tất cả các dự án
allprojects {
    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

// Cấu hình clean task
tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}