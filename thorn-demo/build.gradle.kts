plugins {
    `thorn-java`
    `thorn-repositories`
}

dependencies {
    api(project(":thorn-codecs:thorn-codec-jackson-msgpack"))
    api(project(":thorn-bridges:thorn-bridge-redis"))
}