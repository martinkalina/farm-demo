package cz.mkalina.farm.demo.dto


data class FieldDto (
        val id: Int = 0,
        val name: String,
        val borders: List<PointDto>
)
