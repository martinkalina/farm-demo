package cz.mkalina.farm.demo.dto


data class FarmDto(
        val id: Int = 0,
        val name: String,
        val note: String? = null,
        val fields: List<FieldDto>
)

