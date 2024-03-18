package plugin

import com.github.javaparser.JavaParser
import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.ImportDeclaration
import com.github.javaparser.ast.NodeList
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.expr.Name
import com.github.javaparser.ast.expr.NormalAnnotationExpr
import com.github.javaparser.ast.expr.SimpleName
import com.github.javaparser.printer.PrettyPrinter
import java.nio.file.Files
import java.nio.file.Path
import kotlin.io.path.writeText

abstract class AbstractJavaProcessor {

    public fun processPackage(parser: JavaParser, printer: PrettyPrinter, srcDir: String, pkg: String) {
        val sourceFolder = Path.of(srcDir, pkg.replace('.', '/'))
        Files.list(sourceFolder)
            .map { path -> processFile(parser, printer, path) }
            .filter { it != null }
            .map { it!! }
            .forEach { (path, source) -> path.writeText(source) }
    }

    protected fun processFile(parser: JavaParser, printer: PrettyPrinter, path: Path): Pair<Path, String>? {
        val parseResult = parser.parse(path)
        return if (parseResult.isSuccessful) {
            val processedCompilationUnit = processCompilationUnit(parseResult.result.get())
            path to printer.print(processedCompilationUnit)
        } else null
    }

    protected fun processCompilationUnit(javaFile: CompilationUnit): CompilationUnit {
        javaFile.findAll(ClassOrInterfaceDeclaration::class.java)
            .forEach { processClassOrInterfaceDeclaration(it, javaFile) }
        return javaFile
    }

    protected abstract fun processClassOrInterfaceDeclaration(
        clazz: ClassOrInterfaceDeclaration,
        javaFile: CompilationUnit
    );

    protected fun CompilationUnit.addImportInternal(name: String) {
        if (imports.none { it.name == SimpleName(name) }) {
            imports.add(ImportDeclaration(name, false, false))
        }
    }

    protected fun ClassOrInterfaceDeclaration.addAnnotationInternal(name: String) {
        if (annotations.none { it.name == Name(name) }) {
            annotations.add(
                NormalAnnotationExpr(
                    Name(name),
                    NodeList()
                )
            )
        }

    }
}