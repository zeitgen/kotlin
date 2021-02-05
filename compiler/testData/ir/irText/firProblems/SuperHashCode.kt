interface Point

class DelegatePoint(val original: Point?) : Point {
    override fun hashCode(): Int = original?.hashCode() ?: super.hashCode()
}