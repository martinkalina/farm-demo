package cz.mkalina.farm.demo.model

import com.vividsolutions.jts.geom.Geometry

data class Field (
        val id: Int = 0,
        val name: String,
        val borders: Geometry
)
