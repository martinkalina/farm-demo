package cz.mkalina.farm.demo

import com.nhaarman.mockitokotlin2.*
import cz.mkalina.farm.demo.persistence.FarmRepository
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
class FarmControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc


    @MockBean
    private lateinit var farmRepository: FarmRepository

    @Test
    fun testCreate() {

        whenever(farmRepository.create(any()))
                .thenReturn(
                        createFarm().copy(id = 1)
                )


        mockMvc.perform(
                MockMvcRequestBuilders.post("/farms").contentType(
                        MediaType.APPLICATION_JSON
                ).content(createJson())
        )
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.jsonPath("id", equalTo(1)))
                .andReturn()

        verify(farmRepository).create(argThat { this.name == "test_name" })
    }

    @Test
    fun testCreateFail() {


        mockMvc.perform(
                MockMvcRequestBuilders.post("/farms").contentType(
                        MediaType.APPLICATION_JSON
                ).content(createInvalidJson())
        )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
                .andReturn()


    }

    @Test
    fun testUpdate() {

        whenever(farmRepository.update(any(), any()))
                .thenReturn(true)


        mockMvc.perform(
                MockMvcRequestBuilders.put("/farms/1").contentType(
                        MediaType.APPLICATION_JSON
                ).content(createJson())
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()

        verify(farmRepository).update(argThat { this.name == "test_name" }, eq(1))
    }


    @Test
    fun testFindById() {
        whenever(farmRepository.findById(any()))
                .thenReturn(
                        createFarm().copy(id = 1)
                )
        mockMvc.perform(
                MockMvcRequestBuilders.get("/farms/1")
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("id", equalTo(1)))
                .andReturn()

        verify(farmRepository).findById(eq(1))

    }

    @Test
    fun testFindAll() {
        whenever(farmRepository.findAll())
                .thenReturn(
                        listOf(
                                createFarm().copy(id = 1),
                                createFarm().copy(id = 2)
                        )
                )
        mockMvc.perform(
                MockMvcRequestBuilders.get("/farms")
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", equalTo(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", equalTo(2)))
                .andReturn()

        verify(farmRepository).findAll()

    }


    @Test
    fun testDelete() {
        whenever(farmRepository.deleteFarm(any()))
                .thenReturn(true)
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/farms/1")
        )
                .andExpect(MockMvcResultMatchers.status().isNoContent)
                .andReturn()

        verify(farmRepository).deleteFarm(any())
    }

}