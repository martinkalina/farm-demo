package cz.mkalina.farm.demo.service

import cz.mkalina.farm.demo.model.Farm
import cz.mkalina.farm.demo.persistence.FarmRepository
import org.springframework.stereotype.Service

@Service
class FarmService (
        private val farmRepository: FarmRepository
){
    fun create(farm: Farm) = farmRepository.create(farm)

    fun findById(farmId: Int): Farm? = farmRepository.findById(farmId)

    fun update(farm: Farm, farmId: Int): Boolean = farmRepository.update(farm, farmId)

    fun deleteFarm(farmId: Int): Boolean = farmRepository.deleteFarm(farmId)

    fun findAll(): List<Farm>  = farmRepository.findAll()




}
