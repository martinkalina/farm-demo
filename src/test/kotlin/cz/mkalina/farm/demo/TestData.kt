package cz.mkalina.farm.demo

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.Polygon
import cz.mkalina.farm.demo.model.Farm
import cz.mkalina.farm.demo.model.Field
import org.intellij.lang.annotations.Language

fun createFarm(): Farm {
    val builder = GeometryFactory()
    val polygon = builder.createPolygon(
            arrayOf(
                    Coordinate(0.0, 0.0),
                    Coordinate(1.0, 0.0),
                    Coordinate(1.0, 1.0),
                    Coordinate(0.0, 1.0),
                    Coordinate(0.0, 0.0)
            )
    )

    return Farm(
            name = "test farm",
            fields = listOf(
                    Field(
                            name = "test field",
                            borders = polygon
                    )
            )
    )


}

fun createPolygon(): Polygon {
    val builder = GeometryFactory()
    return builder.createPolygon(
            arrayOf(
                    Coordinate(5.0, 5.0),
                    Coordinate(10.0, 10.0),
                    Coordinate(15.0, 5.0),
                    Coordinate(5.0, 5.0)
            )
    )
}

fun createJson() : String {

    @Language("json")
    val jsonString = """
            {
              "name": "test_name",
              "note": "test_note",
              "fields":  [
                {
                  "name": "test_field_name",
                  "borders": [
                      {"x":  0.0, "y":  0.0},
                      {"x":  1.0, "y":  0.0},
                      {"x":  1.0, "y":  1.0},
                      {"x":  0.0, "y":  1.0},
                      {"x":  0.0, "y":  0.0}
                  ]
                }    
              ]
            }
        """.trimIndent()

    return jsonString

}

fun createInvalidJson() : String {

    @Language("json")
    val jsonString = """
            {
              "name": "test_name",
              "note": "test_note",
              "fields":  [
                {
                  "name": "test_field_name",
                  "borders": [
                      {"x":  0.0, "y":  0.0},
                      {"x":  1.0, "y":  0.0},
                      {"x":  2.0, "y":  0.0},
                      {"x":  0.0, "y":  0.0}
                  ]
                }    
              ]
            }
        """.trimIndent()

    return jsonString

}