package com.viaplay.test.feature.details

import com.viaplay.test.common.base.BaseScreenState
import com.viaplay.test.common.base.ViewState
import com.viaplay.test.domain.model.Section

data class SectionViewState(
    override val base: ViewState<Section> = ViewState(),
) : BaseScreenState<Section, SectionViewState> {

    override fun copyWithBase(base: ViewState<Section>) = copy(base = base)
}