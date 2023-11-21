package com.ardine.fruturity.ui.screen.cart

import com.ardine.fruturity.model.FruitOrder

data class CartState(
    val fruitOrder: List<FruitOrder>,
    val price: Int
)