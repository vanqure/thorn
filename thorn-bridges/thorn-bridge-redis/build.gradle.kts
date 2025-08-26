plugins {
    `thorn-java`
    `thorn-publish`
    `thorn-repositories`
}

dependencies {
    api(project(":thorn-codecs:thorn-codec-common"))
    api(project(":thorn-bridges:thorn-bridge-common"))
    api(libs.lettuce.core)
}

thornPublish {
    artifactId = "thorn-bridge-redis"
}