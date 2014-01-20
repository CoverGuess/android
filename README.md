CoverGuess Android
=======

Compiling
======

    This project is fully compatible with Android studio on Linux (NOT TESTED ON ECLIPSE)

    <ul>
        <li>clone the repository</li>
        <li>run gradle task : 'getProjectDependencies' (ex: <em>./gradlew getProjectDependencies</em>)<li>
        <li>Generate DAO model using DaoGenerator module<li>
        <li>Build the native code (in CoverGuess/src/main/jni using ndk-build)</li>
        <li>run gradle task : 'nativeLibsToJar' (ex: <em>./gradlew nativeLibsToJar</em>)</li>
        <li>You're ready to run the app \o/ !</li>
    </ul>
