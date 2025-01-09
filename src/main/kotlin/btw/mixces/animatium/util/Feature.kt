package btw.mixces.animatium.util

enum class Feature {
    MISS_PENALTY,
    LEFT_CLICK_ITEM_USAGE;

    companion object {
        fun byId(id: String): Feature? {
            return when (id) {
                "miss_penalty" -> MISS_PENALTY
                "left_click_item_usage" -> LEFT_CLICK_ITEM_USAGE
                else -> null
            }
        }
    }
}