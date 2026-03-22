package org.elliotnash.coordsoffline

import org.bukkit.Bukkit

fun getMCVersion(): MCVersion {
    val version = Bukkit.getBukkitVersion().split("-")[0]
    return MCVersion.fromString(version)
}

class InvalidMCVersion(message: String) : Exception(message)

class MCVersion(
    val major: Int,
    val minor: Int,
    val patch: Int
) : Comparable<MCVersion> {
    companion object {
        fun fromString(version: String): MCVersion {
            val el = version.split(".")
            try {
                if (el.size == 2) {
                    return MCVersion(el[0].toInt(), el[1].toInt(), 0)
                }
                if (el.size == 3) {
                    return MCVersion(el[0].toInt(), el[1].toInt(), el[2].toInt())
                }
            } catch (_: NumberFormatException) {
                throw InvalidMCVersion("Invalid MC version: $version!")
            }
            throw InvalidMCVersion("Invalid MC version: $version!")
        }
    }

    override fun compareTo(other: MCVersion): Int {
        if (major > other.major) return 1
        if (major < other.major) return -1
        if (minor > other.minor) return 1
        if (minor < other.minor) return -1
        if (patch > other.patch) return 1
        if (patch < other.patch) return -1
        return 0
    }
    override fun equals(other: Any?): Boolean {
        if (other is MCVersion) {
            return major == other.major && minor == other.minor && patch == other.patch
        }
        return false
    }
    override fun hashCode(): Int {
        return major * 43981 + minor * 19284 + patch * 93029
    }
    override fun toString(): String {
        return "${major}.${minor}.${patch}"
    }
}
