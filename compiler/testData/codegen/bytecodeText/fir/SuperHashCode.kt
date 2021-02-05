// TARGET_BACKEND: JVM
// WITH_RUNTIME

interface Point {
    val x: Int
    val y: Int
}

class DelegatePoint(
    val original: Point?, override val x: Int, override val y: Int,
    private var listComputation: (() -> List<String>)? = null
) : Point {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DelegatePoint

        return (original ?: this) === (other.original ?: other)
    }

    override fun hashCode(): Int = original?.hashCode() ?: super.hashCode()

    override fun toString() = "Point($x $y)"
}

fun box(): String {
    val point = DelegatePoint(null, 1, 2)
    val point2 = DelegatePoint(point, 3, 4)
    point == point2
    val code = point.hashCode()
    return "OK"
}

// 1 INVOKEVIRTUAL java/lang/Object.hashCode() \(\)I
// 1 INVOKESPECIAL java/lang/Object.hashCode() \(\)I