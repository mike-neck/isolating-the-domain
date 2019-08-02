package example;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.importer.ImportOptions;
import com.tngtech.archunit.library.Architectures;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

class DependencyTest {

    @Test
    void architectureRule() {
        ImportOptions options = new ImportOptions().with(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .with(ImportOption.Predefined.DO_NOT_INCLUDE_JARS);
        JavaClasses importedClasses = new ClassFileImporter(options)
                .importPackages("example");
        Architectures.LayeredArchitecture layeredArchitecture = layeredArchitecture()
                .layer("Controller")
                .definedBy("example.api..", "example.presentation..")
                .layer("Use-Cases")
                .definedBy("example.application..")
                .layer("Entities")
                .definedBy("example.domain..")
                .layer("Gateway")
                .definedBy("example.infrastructure..")
                .whereLayer("Controller").mayNotBeAccessedByAnyLayer()
                .whereLayer("Use-Cases").mayOnlyBeAccessedByLayers("Controller", "Gateway")
                .whereLayer("Entities").mayOnlyBeAccessedByLayers("Use-Cases", "Controller", "Gateway");
        layeredArchitecture.check(importedClasses);
    }
}
