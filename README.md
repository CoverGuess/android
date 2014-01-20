CoverGuess Android
=======

Compiling
======

    This project is fully compatible with Android studio on Linux (NOT TESTED ON ECLIPSE)


        1. clone the repository
        2. run gradle task : 'getProjectDependencies' (ex: *./gradlew getProjectDependencies*)
        3. Generate DAO model using DaoGenerator module
        4. Build the native code (in *CoverGuess/src/main/jni* using *ndk-build*)
        5. run gradle task : 'nativeLibsToJar' (ex: *./gradlew nativeLibsToJar*)
        6. You're ready to run the app \o/ !
