package com.example.payouts.activities

import com.example.payouts.model.activity.ChooseInvestigationRequest
import com.example.payouts.model.domain.Money
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AiBriefActivitiesImplTest {
    @Test
    fun `no review facts skip investigation without calling the model`() {
        val activity = AiBriefActivitiesImpl("test-qwen", "http://127.0.0.1:1")

        val choice = activity.chooseInvestigation(
            ChooseInvestigationRequest(Money(250_000, "USD"), 250_000, emptyList()),
        )

        assertTrue(choice.tools.isEmpty())
        assertEquals("No review facts supplied", choice.reason)
    }
}
