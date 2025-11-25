plugins {
    id("java")
    `maven-publish`
}

group = "com.javi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
var okHttpVersion = "5.3.2"
var jacksonVersion = "3.0.2"
var jUnitVersion = "5.10.0"
dependencies {
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation("com.squareup.okhttp3:okhttp:${okHttpVersion}")
    // https://mvnrepository.com/artifact/tools.jackson.core/jackson-databind
    implementation("tools.jackson.core:jackson-databind:${jacksonVersion}")
    testImplementation(platform("org.junit:junit-bom:${jUnitVersion}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/javicaib/matomo-api-client")
            credentials {
                username = System.getenv("GITHUB_USER")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}