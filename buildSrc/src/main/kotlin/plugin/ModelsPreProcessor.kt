package plugin

import com.github.javaparser.JavaParser
import com.github.javaparser.printer.PrettyPrinter
import org.gradle.api.Plugin
import org.gradle.api.Project

interface ModelsPreProcessorConfig {
    var srcDir: String
    var packageNames: Set<String>
    var processor: AbstractJavaProcessor
}

class ModelsPreProcessorPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val extension = project.extensions.create(
            "modelsPreProcess",
            ModelsPreProcessorConfig::class.java
        )
        project.task("modelsPreProcess") {
            doLast {
                val parser = JavaParser()
                val printer = PrettyPrinter()
                extension.packageNames.forEach { pkg ->
                    extension.processor.processPackage(parser, printer, extension.srcDir, pkg)
                }
            }
        }
    }
}