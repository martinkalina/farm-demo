package cz.mkalina.farm.demo.rest

import com.vividsolutions.jts.geom.Coordinate
import com.vividsolutions.jts.geom.Geometry
import com.vividsolutions.jts.geom.GeometryFactory
import com.vividsolutions.jts.geom.Polygon
import cz.mkalina.farm.demo.dto.FarmDto
import cz.mkalina.farm.demo.dto.FieldDto
import cz.mkalina.farm.demo.dto.PointDto
import cz.mkalina.farm.demo.model.Farm
import cz.mkalina.farm.demo.model.Field
import cz.mkalina.farm.demo.service.FarmService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/farms")
class FarmController(
        private val farmService: FarmService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun crate(
            @RequestBody farmDto: FarmDto
    ): FarmDto {
        return farmService.create(farmDto.toModel())
                .let { it.toDto() }
    }

    @GetMapping("/{farmId}")
    fun findById(
            @PathVariable farmId: Int
    ): FarmDto {
        return farmService.findById(farmId)?.toDto()
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @GetMapping
    fun findAll(
    ): List<FarmDto> {
        return farmService.findAll().map {
            it.toDto()
        }
    }


    @PutMapping("/{farmId}")
    fun update(
            @RequestBody farmDto: FarmDto,
            @PathVariable farmId: Int
    ) {
        farmService.update(farmDto.toModel(), farmId).also {
            if (!it) throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{farmId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
            @PathVariable farmId: Int
    ) {
        farmService.deleteFarm(farmId).also {
            if (!it) throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }

}

private fun Farm.toDto(): FarmDto = FarmDto(
        id = id,
        name = name,
        fields = fields.map { it.toDto() }
)

private fun Field.toDto(): FieldDto = FieldDto(
        id = id,
        name = name,
        borders = borders.toDto()
)

private fun Geometry.toDto(): List<PointDto> = this.coordinates.map { PointDto(it.x, it.y) }


private fun FarmDto.toModel(): Farm = Farm(
        name = name,
        fields = fields.map { it.toModel() }
)

private fun FieldDto.toModel(): Field = Field(
        name = name,
        borders = createPolygon(borders).also {
            validate(it)
        }
)

private fun createPolygon(points: List<PointDto>): Polygon = GeometryFactory()
        .createPolygon(points
                .map { Coordinate(it.x, it.y) }
                .toTypedArray()
        )

private fun validate(polygon: Polygon) {
    if (polygon.area <= 0.0) {
        throw ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Filed area is not positive")
    }
}
