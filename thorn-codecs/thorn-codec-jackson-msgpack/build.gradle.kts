plugins {
    `thorn-java`
    `thorn-publish`
    `thorn-repositories`
}

dependencies {
    api(project(":thorn-codecs:thorn-codec-jackson"))
    api(libs.msgpack.jackson.dataformat)
}

thornPublish {
    artifactId = "thorn-codec-jackson-msgpack"
}