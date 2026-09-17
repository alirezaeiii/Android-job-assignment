package com.viaplay.test.feature.dashboard

import com.viaplay.test.common.base.BaseScreenState
import com.viaplay.test.common.base.ViewState
import com.viaplay.test.domain.model.Link

data class LinksViewState(
    override val base: ViewState<List<Link>> = ViewState(),
) : BaseScreenState<List<Link>, LinksViewState> {

    override fun copyWithBase(base: ViewState<List<Link>>) = copy(base = base)
}