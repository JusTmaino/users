package fr.afklm.users;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("fr.afklm.users");

        noClasses()
                .that()
                .resideInAnyPackage("fr.afklm.users.services..")
                .or()
                .resideInAnyPackage("fr.afklm.users.repositories..")
                .should().dependOnClassesThat()
                .resideInAnyPackage("..fr.afklm.users.web..")
                .because("Services and repositories should not depend on web layer")
                .check(importedClasses);
    }
}

