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
package urstark.solstice.innertube.pages

import urstark.solstice.innertube.models.BrowseEndpoint
import urstark.solstice.innertube.models.GridRenderer
import urstark.solstice.innertube.models.MusicNavigationButtonRenderer
import urstark.solstice.innertube.models.SectionListRenderer

data class MoodAndGenres(
    val title: String,
    val items: List<Item>,
) {
    data class Item(
        val title: String,
        val stripeColor: Long,
        val endpoint: BrowseEndpoint,
    )

    companion object {
        fun fromSectionListRendererContent(content: SectionListRenderer.Content): MoodAndGenres? {
            return MoodAndGenres(
                title =
                    content.gridRenderer
                        ?.header
                        ?.gridHeaderRenderer
                        ?.title
                        ?.runs
                        ?.firstOrNull()
                        ?.text ?: return null,
                items =
                    content.gridRenderer.items
                        .mapNotNull(GridRenderer.Item::musicNavigationButtonRenderer)
                        .mapNotNull(::fromMusicNavigationButtonRenderer),
            )
        }

        fun fromMusicNavigationButtonRenderer(renderer: MusicNavigationButtonRenderer): Item? {
            return Item(
                title =
                    renderer.buttonText.runs
                        ?.firstOrNull()
                        ?.text ?: return null,
                stripeColor = renderer.solid?.leftStripeColor ?: return null,
                endpoint = renderer.clickCommand.browseEndpoint ?: return null,
            )
        }
    }
}
