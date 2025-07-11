object UpdateStrategyFactory {
    fun getStrategy(updateType: String): UpdateStrategy? {
        return when (updateType) {
            "created" -> CreatedUpdateStrategy()
            "shipped" -> ShippedUpdateStrategy()
            "location" -> LocationUpdateStrategy()
            "delivered" -> GenericStatusStrategy("delivered")
            "noteadded" -> NoteAddedUpdateStrategy()
            "lost" -> GenericStatusStrategy("lost")
            "canceled" -> GenericStatusStrategy("canceled")
            "delayed" -> DelayedUpdateStrategy()
            else -> null
        }
    }
}
