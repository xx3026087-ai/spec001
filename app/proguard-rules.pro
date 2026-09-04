-keep class com.microworker.** { *; }
-keep interface com.microworker.** { *; }
-keepclassmembers class * {
    native <methods>;
}
-dontwarn com.microworker.**
