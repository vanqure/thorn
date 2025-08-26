plugins {
    `thorn-java`
    `thorn-repositories`
}

dependencies {
    api(project(":thorn-codecs:thorn-codec-jackson"))
    api(project(":thorn-bridges:thorn-bridge-redis"))
}