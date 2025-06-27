package com.browser.embed.actions

import com.browser.embed.BrowserToolWindow
import com.intellij.icons.AllIcons
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class ToggleableViewAction(private val toolWindow: BrowserToolWindow) :
    AnAction("切换移动端视图", null, AllIcons.Actions.ChangeView) {
    private var isMobile = false
    override fun actionPerformed(p0: AnActionEvent) {
        isMobile = !isMobile
        toolWindow.reloadBrowserWithUA(isMobile)
    }
}