# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep classes referenced by AndroidManifest.xml
-keep class androidx.core.app.CoreComponentFactory { *; }
-keep class com.example.step.StepApplication { *; }

# Keep MPAndroidChart
-keep class com.github.mikephil.charting.** { *; }

# Keep Room Database classes
-keep class com.example.step.data.** { *; }
-keep class androidx.room.** { *; }

# Keep for ConstraintLayout
-keep class androidx.constraintlayout.** { *; }

# Keep for multidex
-keep class androidx.multidex.** { *; }

# Keep appcompat resources
-keep class androidx.appcompat.resources.** { *; }

# Don't warn about D8 internal classes
-dontwarn com.android.tools.r8.**
-dontwarn com.android.tools.r8.utils.**
-dontwarn com.android.tools.r8.utils.D8DiagnosticsHandler