# Proguard rules for Crystal App
-keepattributes *Annotation*
-dontwarn sun.misc.**
-keep class ir.marheil.crystal.core.database.entity.** { *; }
-keep class ir.marheil.crystal.core.model.** { *; }
