/*
 * Solstice (2026)
 * © Stark — github.com/urstark
 * 
 * Based on ArchiveTune (2026)
 * © Rukamori — github.com/rukamori
 * 
 * GPL-3.0 License | Contributors: see git history
 * Do not remove or alter this notice. - Per GPL-3.0 Section 4 & Section 5
 */
package urstark.solstice.innertube.models.body

import kotlinx.serialization.Serializable
import urstark.solstice.innertube.models.Context
import urstark.solstice.innertube.models.Continuation

@Serializable
data class BrowseBody(
    val context: Context,
    val browseId: String?,
    val params: String?,
    val continuation: String?,
)
