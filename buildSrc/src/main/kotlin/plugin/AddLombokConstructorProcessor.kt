package plugin

import com.github.javaparser.ast.CompilationUnit
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration
import com.github.javaparser.ast.body.ConstructorDeclaration
import com.github.javaparser.ast.body.FieldDeclaration
import com.github.javaparser.ast.body.Parameter

class AddLombokConstructorProcessor : AbstractJavaProcessor() {

    override fun processClassOrInterfaceDeclaration(clazz: ClassOrInterfaceDeclaration, javaFile: CompilationUnit) {
        if (clazz.isInterface) {
            return
        }
        val fields = clazz.findAll(FieldDeclaration::class.java)
            .filter { it.isPrivate && !it.isStatic }
        val constructors = clazz.findAll(ConstructorDeclaration::class.java)
        val paramsCount = fields.size
        if (constructors.none { it.findAll(Parameter::class.java).isEmpty() }) {
            javaFile.addImportInternal("lombok.NoArgsConstructor")
            clazz.addAnnotationInternal("NoArgsConstructor")
        }

        if (constructors.none { it.findAll(Parameter::class.java).size == paramsCount }) {
            javaFile.addImportInternal("lombok.AllArgsConstructor")
            clazz.addAnnotationInternal("AllArgsConstructor")
        }

        javaFile.addImportInternal("lombok.Builder")
        clazz.addAnnotationInternal("Builder")
    }

}