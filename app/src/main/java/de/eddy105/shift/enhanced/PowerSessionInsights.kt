package de.eddy105.shift.enhanced

/** Compatibility alias for the power-session metrics model. */
typealias PowerSessionInsights = PowerSessionMetrics

fun PowerSession.insights(): PowerSessionInsights = metrics()
