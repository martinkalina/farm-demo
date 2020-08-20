package cz.mkalina.farm.demo.persistence

import cz.mkalina.farm.demo.persistence.tables.Farm
import cz.mkalina.farm.demo.persistence.tables.Field
import org.jooq.DSLContext
import org.springframework.stereotype.Repository


@Repository
class FarmRepository(
        private val dsl: DSLContext
) {

    fun create(farm: cz.mkalina.farm.demo.model.Farm): cz.mkalina.farm.demo.model.Farm {

        val farmId = farm.let {
            dsl.insertInto(Farm.FARM)
                    .set(Farm.FARM.NAME, it.name)
                    .set(Farm.FARM.NOTE, it.note)
                    .returningResult(Farm.FARM.ID)
                    .fetchOne()
                    .get(Farm.FARM.ID);
        }

        val fields = farm.fields.map {
            createField(it, farmId)
        }
        return farm.copy(
                id = farmId,
                fields = fields
        )
    }

    fun createField(field: cz.mkalina.farm.demo.model.Field, farmId: Int): cz.mkalina.farm.demo.model.Field {
        return dsl.insertInto(Field.FIELD)
                .set(Field.FIELD.NAME, field.name)
                .set(Field.FIELD.BORDERS, field.borders)
                .set(Field.FIELD.FARM_ID, farmId)
                .returningResult(Field.FIELD.ID)
                .fetchOne()
                .get(Field.FIELD.ID)
                .let {
                    field.copy(id = it)
                }
    }

    fun removeField(fieldId: Int): Boolean = dsl
            .deleteFrom(Field.FIELD)
            .where(Field.FIELD.ID.eq(fieldId))
            .execute() != 0

    fun deleteFarm(farmId: Int): Boolean = dsl
            .deleteFrom(Farm.FARM)
            .where(Farm.FARM.ID.eq(farmId))
            .execute() != 0

    fun findById(farmId: Int): cz.mkalina.farm.demo.model.Farm? = dsl.select(Farm.FARM.asterisk())
            .from(Farm.FARM)
            .where(Farm.FARM.ID.eq(farmId))
            .fetchAny()
            .takeIf {
                it != null
            }?.map {
                cz.mkalina.farm.demo.model.Farm(
                        id = it.get(Farm.FARM.ID),
                        name = it.get(Farm.FARM.NAME),
                        note = it.get(Farm.FARM.NOTE),
                        fields = loadFields(farmId)
                )
            }

    private fun loadFields(farmId: Int): List<cz.mkalina.farm.demo.model.Field> {
        return dsl.select(Field.FIELD.asterisk())
                .from(Field.FIELD)
                .where(Field.FIELD.FARM_ID.eq(farmId))
                .fetch()
                .map {
                    cz.mkalina.farm.demo.model.Field(
                            id = it.get(Field.FIELD.ID),
                            name = it.get(Field.FIELD.NAME),
                            borders = it.get(Field.FIELD.BORDERS)
                    )
                }.toList()
    }

    fun update(farm: cz.mkalina.farm.demo.model.Farm, farmId: Int): Boolean = dsl
            .update(Farm.FARM)
            .set(Farm.FARM.NAME, farm.name)
            .set(Farm.FARM.NOTE, farm.note)
            .where(Farm.FARM.ID.eq(farmId))
            .execute() != 0 //TODO fields are not updated here


    fun findAll(): List<cz.mkalina.farm.demo.model.Farm> {
        return dsl.select(Farm.FARM.asterisk())
                .from(Farm.FARM)
                .fetch()
                .map {
                    cz.mkalina.farm.demo.model.Farm(
                            id = it.get(Farm.FARM.ID),
                            name = it.get(Farm.FARM.NAME),
                            note = it.get(Farm.FARM.NOTE),
                            fields = loadFields(it.get(Farm.FARM.ID))
                    )
                }
    }


}