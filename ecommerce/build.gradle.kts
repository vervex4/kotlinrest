import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.20"
	kotlin("plugin.spring") version "1.9.20"
	kotlin("plugin.jpa") version  "1.9.20"
}

group = "com.jeenatech..platform"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {
	val coroutinesversion = "1.7.1"
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("aws.sdk.kotlin:s3:1.0.0")
	implementation("aws.sdk.kotlin:cognitoidentityprovider:1.0.0") {
		exclude("com.squareup.okhttp3:okhttp")
	}
	implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")
	implementation("aws.sdk.kotlin:cognitoidentity:1.0.0")
	implementation("aws.sdk.kotlin:secretsmanager:1.0.0")
	implementation("aws.smithy.kotlin:http-client-engine-okhttp:0.30.0")
	implementation("aws.smithy.kotlin:http-client-engine-crt:0.30.0")
	testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:$coroutinesversion")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$coroutinesversion")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$coroutinesversion")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-debug:$coroutinesversion")
//	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

	runtimeOnly("org.postgresql:postgresql")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
