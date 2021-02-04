// TARGET_BACKEND: JVM
// WITH_RUNTIME

fun bar(s: String?, t: String?): String {
    s?.let {
        t?.let {
            return it
        }
    }
    return "OK"
}

fun box(): String = bar("FAIL", null)

