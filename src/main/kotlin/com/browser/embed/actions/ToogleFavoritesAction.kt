package com.browser.embed.actions

import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.ui.OnePixelSplitter

class ToogleFavoritesAction(private val splitter: OnePixelSplitter) :
    AnAction("显示/隐藏收藏夹", null, AllIcons.General.Pin) {
    override fun actionPerformed(p0: AnActionEvent) {
        splitter.firstComponent.isVisible = !splitter.firstComponent.isVisible
    }
}