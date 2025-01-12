package btw.mixces.animatium.util

enum class Feature(val id: String) {
    MISS_PENALTY("miss_penalty"),
    LEFT_CLICK_ITEM_USAGE("left_click_item_usage");

    companion object {
        fun byId(id: String): Feature? {
            return Feature.entries.find { entry -> entry.id == id }
        }
    }
}