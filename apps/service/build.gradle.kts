import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
  id("io.spring.dependency-management") version "1.1.5"
  kotlin("jvm") version "1.9.24"
  kotlin("plugin.spring") version "1.9.24"
  kotlin("plugin.allopen") version "1.9.24"
  kotlin("plugin.serialization") version "2.0.0"
  id("org.springframework.boot") version "3.3.2"
  id("org.openapi.generator") version "7.7.0"
  id("org.jetbrains.gradle.liquibase") version "1.5.2"
}

group = "ch.example"

version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_21

repositories { mavenCentral() }

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  runtimeOnly("org.postgresql:postgresql")
  implementation("org.liquibase:liquibase-core")
  implementation("io.github.wimdeblauwe:error-handling-spring-boot-starter:4.3.0")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")
  developmentOnly("org.springframework.boot:spring-boot-devtools")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.security:spring-security-test")
  implementation("org.slf4j:slf4j-api:2.0.13")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.1")
  liquibaseRuntime("org.liquibase:liquibase-core")
  liquibaseRuntime("org.postgresql:postgresql")
  testImplementation("io.strikt:strikt-core:0.35.1")
}

allOpen {
  annotation("javax.persistence.Entity")
  annotation("javax.persistence.MappedSuperclass")
  annotation("javax.persistence.Embeddable")
}

tasks.getByName<BootRun>("bootRun") {
  environment.put("SPRING_PROFILES_ACTIVE", environment.get("SPRING_PROFILES_ACTIVE") ?: "local")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjsr305=strict")
    jvmTarget = "21"
  }
}

tasks.withType<Test> { useJUnitPlatform() }

openApiGenerate {
  generatorName.set("kotlin-spring")
  inputSpec.set("$rootDir/../../shared/openapi/tsp-output/@typespec/openapi3/openapi.yaml")
  apiPackage.set("ch.example.app.api")
  modelPackage.set("ch.example.app.dto")
  modelNameSuffix.set("DTO")
  generateApiDocumentation.set(false)
  generateApiTests.set(false)
  configOptions.put("dateLibrary", "java8")
  configOptions.put("interfaceOnly", "true")
  configOptions.put("useSpringBoot3", "true")
  configOptions.put("delegatePattern", "false")
}

sourceSets.named("main").configure {
  extensions
    .getByName<SourceDirectorySet>("kotlin")
    .srcDirs(project.layout.buildDirectory.dir("generate-resources/main"))
}

liquibase {
  activities {
    all {
      properties {
        changeLogFile.set("./src/main/resources/domain/changelog-master.yml")
        driver.set("org.postgresql.Driver")
        includeSystemClasspath.set(true)
      }
    }
    register("local") {
      properties {
        url.set("jdbc:postgresql://localhost:5432/sampledb")
        username.set("postgres")
        password.set("postgres")
      }
    }
  }
}
