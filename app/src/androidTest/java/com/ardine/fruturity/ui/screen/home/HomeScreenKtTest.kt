package com.ardine.fruturity.ui.screen.home

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.ardine.fruturity.R
import com.ardine.fruturity.model.FruitOrder
import com.ardine.fruturity.model.Fruits
import com.ardine.fruturity.ui.theme.FruturityTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val mockFruitOrder = listOf(
        FruitOrder(fruits = createFakeFruit("Blueberry"), count = 3),
        FruitOrder(fruits = createFakeFruit("Apple"), count = 5)
    )

    private fun createFakeFruit(title: String): Fruits {
        return Fruits(4, R.drawable.blueberry, title, "Tiny, juicy berries packed with antioxidants.", 8000)
    }

    @Before
    fun setUp() {
        composeTestRule.setContent {
            FruturityTheme {
                HomeContent(
                    order = mockFruitOrder,
                    navigateToDetail = {},
                    query = "SearchQuery",
                    onQueryChange = {},
                )
            }
        }
    }

    @Test
    fun homeContent_displayedProperly() {
        composeTestRule.onNodeWithText("Blueberry").assertExists()
    }
}