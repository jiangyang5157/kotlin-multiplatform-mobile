package com.gmail.jiangyang5157.router.core

/**
 * # EmptyRoute
 * Object representing an empty [Route].
 *
 * ## Usage
 * - For transitions that expect a route for either the "fromRoute" or "toRoute" when there is no "fromRoute" or "toRoute" (because it may be the first route to be pushed)
 */
object EmptyRoute : Route
