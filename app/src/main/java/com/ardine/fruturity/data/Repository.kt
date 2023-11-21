package com.ardine.fruturity.data

import com.ardine.fruturity.model.FruitOrder
import com.ardine.fruturity.model.FruitsDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class Repository {
    private  val order = mutableListOf<FruitOrder>()

    init{
        if (order.isEmpty()){
            FruitsDataSource.dummyFruits.forEach{
                order.add(FruitOrder(it, 0))
            }
        }
    }

    fun getAllFruits() : Flow<List<FruitOrder>> {
        return flowOf(order)
    }

    fun getFruitOrderById(fruitId : Long): FruitOrder{
        return order.first {
            it.fruits.id == fruitId
        }
    }

    fun updateFruitOrder(fruitId: Long, newCountValue: Int): Flow<Boolean> {
        val index = order.indexOfFirst { it.fruits.id == fruitId }
        val result = if (index >= 0) {
            val fruitOrders = order[index]
            order[index] =
                fruitOrders.copy( count = newCountValue, fruits = fruitOrders.fruits)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    fun getAddedOrders(): Flow<List<FruitOrder>>{
        return getAllFruits()
            .map{ orders ->
                orders.filter { order ->
                    order.count != 0
                }
            }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                Repository().apply {
                    instance = this
                }
            }
    }
}