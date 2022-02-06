package com.jhon.episode.domain.feed.usecase

import com.jhon.episode.domain.feed.EpisodeFeedContentRepository
import javax.inject.Inject

class EpisodeGetFeedContentListUseCase @Inject constructor(
    private val repository: EpisodeFeedContentRepository
) {
    suspend operator fun invoke(
        page: Int,
        size: Int,
        search: String? = null,
        companyType: String? = null
    ) = repository.getEpisodeFeedContentList(
        page = page,
        size = size,
        search = search,
        companyType = companyType
    )
}