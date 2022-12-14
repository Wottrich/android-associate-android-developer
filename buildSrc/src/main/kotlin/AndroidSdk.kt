import java.io.File
import org.gradle.api.JavaVersion
import org.gradle.api.plugins.PluginAware
import org.gradle.kotlin.dsl.apply

private const val commonLibraryName = "common-library.gradle.kts"

object AndroidSdk {
    const val targetSdk = 33
    const val minSdk = 23
    const val buildToolsVersion = "33.0.1"
    val javaVersion = JavaVersion.VERSION_1_8
    val jvmTarget = JavaVersion.VERSION_1_8
}

fun PluginAware.applyDefaultLibraryConfiguration(file: File) {
    apply(from = "$file/$commonLibraryName")
}