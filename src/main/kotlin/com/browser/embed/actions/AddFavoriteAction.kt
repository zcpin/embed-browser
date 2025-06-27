package com.browser.embed.actions

import com.browser.embed.FavoritesManager
import com.browser.embed.model.FavoritePage
import com.browser.embed.ui.FavoritesPanel
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.ui.jcef.JBCefBrowser

class AddFavoriteAction(private val browser: JBCefBrowser,private val favoritesPanel: FavoritesPanel,private val project: Project) : AnAction(
    "添加收藏", "收藏当前页面", AllIcons.General.Add
) {
    override fun actionPerformed(p0: AnActionEvent) {
        val currentUrl = browser.cefBrowser.url
        val currentTitle = Messages.showInputDialog(project, "收藏:${currentUrl}", "添加收藏", null)
        if (!currentTitle.isNullOrBlank()) {
            FavoritesManager.getInstance().addFavorite(FavoritePage(currentTitle, currentUrl))
            Messages.showInfoMessage(project, "收藏成功", "添加收藏")
            favoritesPanel.updateList()
        }
    }
}