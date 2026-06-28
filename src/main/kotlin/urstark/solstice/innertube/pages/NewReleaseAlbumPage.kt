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

import urstark.solstice.innertube.models.AlbumItem
import urstark.solstice.innertube.models.AlbumReleaseType
import urstark.solstice.innertube.models.Artist
import urstark.solstice.innertube.models.MusicTwoRowItemRenderer
import urstark.solstice.innertube.models.oddElements
import urstark.solstice.innertube.models.splitBySeparator

object NewReleaseAlbumPage {
    fun fromMusicTwoRowItemRenderer(renderer: MusicTwoRowItemRenderer): AlbumItem? {
        val subtitleRuns = renderer.subtitle?.runs ?: return null
        val subtitleGroups = subtitleRuns.splitBySeparator()

        return AlbumItem(
            browseId = renderer.navigationEndpoint.browseEndpoint?.browseId ?: return null,
            playlistId =
                renderer.thumbnailOverlay
                    ?.musicItemThumbnailOverlayRenderer
                    ?.content
                    ?.musicPlayButtonRenderer
                    ?.playNavigationEndpoint
                    ?.watchPlaylistEndpoint
                    ?.playlistId ?: return null,
            title =
                renderer.title.runs
                    ?.firstOrNull()
                    ?.text ?: return null,
            artists =
                subtitleGroups.getOrNull(1)?.oddElements()?.map {
                    Artist(
                        name = it.text,
                        id = it.navigationEndpoint?.browseEndpoint?.browseId,
                    )
                } ?: return null,
            year =
                subtitleRuns
                    .lastOrNull()
                    ?.text
                    ?.toIntOrNull(),
            releaseType =
                AlbumReleaseType.fromLabel(
                    subtitleGroups.firstOrNull()?.joinToString(separator = "") { it.text },
                ),
            thumbnail = renderer.thumbnailRenderer.musicThumbnailRenderer?.getThumbnailUrl() ?: return null,
            explicit =
                renderer.subtitleBadges?.find {
                    it.musicInlineBadgeRenderer?.icon?.iconType == "MUSIC_EXPLICIT_BADGE"
                } != null,
        )
    }
}
