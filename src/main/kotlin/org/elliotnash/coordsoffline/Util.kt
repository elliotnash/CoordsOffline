package org.elliotnash.coordsoffline

import org.bukkit.Bukkit

//fun getNMSVersion(): NMSVersion {
//    Bukkit.getLogger().info("GETTING VERSION")
//    Bukkit.getLogger().info(Bukkit.getServer()::class.java.getPackage().name)
//    val version = Bukkit.getServer()::class.java.getPackage().name.split("\\.")[3]
//    return NMSVersion.fromString(version)
//}
//
//class InvalidNMSVersion(message: String) : Exception(message)
//
//class NMSVersion(
//    val major: Int,
//    val minor: Int,
//    val revision: Int
//) : Comparable<NMSVersion> {
//    companion object {
//        private val nmsVersionRegex = Regex("""v(\d*)_(\d*)_R(\d*)""")
//        fun fromString(version: String): NMSVersion {
//            val match = nmsVersionRegex.matchEntire(version)
//            if (match == null) {
//                throw InvalidNMSVersion("Invalid NMS version: $version!")
//            }
//            return NMSVersion(match.groupValues[0].toInt(), match.groupValues[1].toInt(), match.groupValues[2].toInt())
//        }
//    }
//
//    override fun compareTo(other: NMSVersion): Int {
//        if (major > other.major) return 1
//        if (major < other.major) return -1
//        if (minor > other.minor) return 1
//        if (minor < other.minor) return -1
//        if (revision > other.revision) return 1
//        if (revision < other.revision) return -1
//        return 0
//    }
//    override fun equals(other: Any?): Boolean {
//        if (other is NMSVersion) {
//            return major == other.major && minor == other.minor && revision == other.revision
//        }
//        return false
//    }
//    override fun hashCode(): Int {
//        return major * 32194 + minor * 12137 + revision * 23109
//    }
//    override fun toString(): String {
//        return "v${major}_${minor}_R${revision}"
//    }
//}

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
