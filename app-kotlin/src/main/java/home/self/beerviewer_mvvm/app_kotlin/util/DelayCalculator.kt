package home.self.beerviewer_mvvm.app_kotlin.util

import home.self.beerviewer_mvvm.app_kotlin.data.model.Delay


internal interface DelayCalculator {
    fun calculate(attempt: Int): Delay
}

class ConstantDelayCalculator(private val intervalMillis: Int = 10000) : DelayCalculator {
    override fun calculate(attempt: Int) = Delay(value = intervalMillis.toLong())
}

class LinearDelayCalculator(private val multiplier: Int = 2) : DelayCalculator {
    override fun calculate(attempt: Int) = Delay(value = attempt * multiplier * 1000L)
}

class SequenceDelayCalculator(private val defaultDelay: Int = 2): DelayCalculator {
    override fun calculate(attempt: Int) = Delay(value = attempt * 1000L + defaultDelay * 1000L)
}