plugins {
    `thorn-java`
    `thorn-publish`
    `thorn-repositories`
}

dependencies {
    api(project(":thorn-codecs:thorn-codec-common"))
}

thornPublish {
    artifactId = "thorn-bridge"
}