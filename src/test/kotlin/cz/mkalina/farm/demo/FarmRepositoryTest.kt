package cz.mkalina.farm.demo

import cz.mkalina.farm.demo.model.Field
import cz.mkalina.farm.demo.persistence.FarmRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

/**
 *
 */
@SpringBootTest
class FarmRepositoryTest {

    @Autowired
    private lateinit var repository: FarmRepository

    @Test
    fun testCrudFarm() {

        val farm = createFarm()

        val newFarm = repository.create(farm)

        assertEquals(newFarm, repository.findById(newFarm.id))


        val newField = repository.createField(
                Field(name = "test_field_2", borders = createPolygon()),
                newFarm.id)

        assertEquals(2, repository.findById(newFarm.id)?.fields?.size)


        val name2 = "test_farm_updated"
        repository.update(
                farm.copy(name = name2),
                newFarm.id
        )
        assertEquals(name2, repository.findById(newFarm.id)?.name)

        assertTrue(repository.findAll().any { it.id == newFarm.id })


        repository.removeField(newField.id)

        assertEquals(1, repository.findById(newFarm.id)?.fields?.size)


        repository.deleteFarm(newFarm.id)

        assertNull(repository.findById(newFarm.id))


    }
}
