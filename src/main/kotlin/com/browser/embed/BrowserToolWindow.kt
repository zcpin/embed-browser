package com.browser.embed

import com.browser.embed.actions.AddFavoriteAction
import com.browser.embed.actions.ToogleFavoritesAction
import com.browser.embed.actions.ToggleableViewAction
import com.browser.embed.ui.AddressBarPanel
import com.browser.embed.ui.FavoritesPanel
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.ui.OnePixelSplitter
import com.intellij.ui.components.JBTextField
import com.intellij.ui.jcef.JBCefBrowser
import org.cef.browser.CefBrowser
import org.cef.browser.CefFrame
import org.cef.handler.CefLoadHandlerAdapter
import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.SwingUtilities

class BrowserToolWindow(val project: Project, toolWindow: ToolWindow) {
    val content: JPanel

    private val browser: JBCefBrowser
    private val splitter: OnePixelSplitter
    private val favoritesPanel: JPanel
    val addressBar: AddressBarPanel


    init {

        browser = createBrowser()
        addressBar = AddressBarPanel(browser)
        favoritesPanel = createFavoritesPanel()
        splitter = OnePixelSplitter(false, 0.2f).apply {
            firstComponent = favoritesPanel
            firstComponent.isVisible = false
            secondComponent = createBrowserPanel()
            secondComponent.add(addressBar, BorderLayout.NORTH)
        }


        content = JPanel(BorderLayout()).apply {
            add(splitter, BorderLayout.CENTER)
        }

        val titleActions = mutableListOf<AnAction>().apply {
            add(AddFavoriteAction(browser, favoritesPanel, project))
            add(ToogleFavoritesAction(splitter))
            add(ToggleableViewAction(this@BrowserToolWindow))
        }

        // 监听地址栏变化
        browser.jbCefClient.addLoadHandler(object : CefLoadHandlerAdapter() {
            override fun onLoadEnd(browser: CefBrowser?, frame: CefFrame?, httpStatusCode: Int) {
                SwingUtilities.invokeLater {
                    addressBar.addressField.text = browser?.url ?: ""
                }
            }
        }, browser.cefBrowser)

        // 添加标题栏按钮
        toolWindow.setTitleActions(titleActions)
    }

    private fun createBrowser(useMobileUA: Boolean = false): JBCefBrowser {
        val browser = JBCefBrowser()
        if (useMobileUA){
            val ua = "Mozilla/5.0 (iPhone; CPU iPhone OS 15_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148"
            val script = """
                Object.defineProperty(navigator, 'userAgent', {
                    get: () => '$ua'
                });
            """.trimIndent()

            browser.cefBrowser.executeJavaScript(script, browser.cefBrowser.url, 0)
        }
        return browser
    }

    fun reloadBrowserWithUA(isMobile: Boolean) {
        val url = browser.cefBrowser.url
        content.removeAll()
        val newBrowser = createBrowser(isMobile)
        val addressBar = AddressBarPanel(newBrowser, JBTextField(url))
        splitter.secondComponent = JPanel(BorderLayout()).apply {
            add(addressBar, BorderLayout.NORTH)
            add(newBrowser.component, BorderLayout.CENTER)
        }
        content.add(splitter, BorderLayout.CENTER)
        content.revalidate()
        content.repaint()

        SwingUtilities.invokeLater {
            newBrowser.loadURL("about: blank")
            newBrowser.loadURL(url)
        }
    }

    private fun createBrowserPanel(): JPanel {
        val addressBar = AddressBarPanel(browser)
        return JPanel(BorderLayout()).apply {
            add(addressBar, BorderLayout.NORTH)
            add(browser.component, BorderLayout.CENTER)
        }
    }

    private fun createFavoritesPanel(): FavoritesPanel {
        val favoritesManager = FavoritesManager.getInstance()
        return FavoritesPanel(browser, addressBar, project).apply {
            favoritesManager.getFavorites().forEach {
                favoritesManager.addFavorite(it)
            }
        }
    }
}

