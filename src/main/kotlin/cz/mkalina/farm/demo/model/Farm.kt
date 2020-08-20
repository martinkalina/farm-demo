package cz.mkalina.farm.demo.model


data class Farm(
        val id: Int = 0,
        val name: String,
        val note: String? = null,
        val fields: List<Field>
)

