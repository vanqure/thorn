plugins {
    `thorn-java`
    `thorn-publish`
    `thorn-repositories`
}

dependencies {
    api(project(":thorn-codecs:thorn-codec-common"))
    api(libs.jackson.databind)
}

thornPublish {
    artifactId = "thorn-codec-jackson"
}