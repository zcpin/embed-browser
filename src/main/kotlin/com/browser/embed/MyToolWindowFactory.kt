package com.browser.embed

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class MyToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(
        project: Project,
        toolWindow: ToolWindow
    ) {
        val browserToolWindow = BrowserToolWindow(project, toolWindow)
        val content = ContentFactory.getInstance().createContent(browserToolWindow.content, "", false)
        toolWindow.contentManager.addContent(content)
    }

}