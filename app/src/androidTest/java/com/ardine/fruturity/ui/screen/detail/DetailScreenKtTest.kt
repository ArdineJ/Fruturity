package com.ardine.fruturity.ui.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.ardine.fruturity.R
import com.ardine.fruturity.model.FruitOrder
import com.ardine.fruturity.model.Fruits
import com.ardine.fruturity.onNodeWithStringId
import com.ardine.fruturity.ui.theme.FruturityTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailContentTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private val fakeFruitOrder = FruitOrder(
        fruits = Fruits(4, R.drawable.blueberry, "Blueberry", "Tiny, juicy berries packed with antioxidants.", 8000),
        count = 0
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            FruturityTheme {
                DetailContent(
                    fakeFruitOrder.fruits.image,
                    fakeFruitOrder.fruits.title,
                    fakeFruitOrder.fruits.description,
                    fakeFruitOrder.fruits.price,
                    fakeFruitOrder.count,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_titleAndPriceDisplayed() {
        composeTestRule.onNodeWithText(fakeFruitOrder.fruits.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.price,
                fakeFruitOrder.fruits.price
            )
        ).assertIsDisplayed()
    }

    @Test
    fun increaseProduct_buttonEnabledAfterIncrement() {
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.plus_symbol).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun increaseProduct_counterIsCorrectAfterMultipleIncrements() {
        composeTestRule.onNodeWithStringId(R.string.plus_symbol)
            .performClick()
            .performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("2"))
    }

    @Test
    fun decreaseProduct_counterRemainsZero() {
        composeTestRule.onNodeWithStringId(R.string.minus_symbol)
            .performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }
}