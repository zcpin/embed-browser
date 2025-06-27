package com.browser.embed.ui

import com.intellij.icons.AllIcons
import com.intellij.ui.components.JBTextField
import com.intellij.ui.jcef.JBCefBrowser
import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JPanel

class AddressBarPanel(private val browser: JBCefBrowser, val addressField: JBTextField = JBTextField("about:blank")) :
    JPanel(BorderLayout()) {
    init {
        val goButton = JButton(AllIcons.Actions.Refresh).apply {
            addActionListener {
                val url = addressField.text.trim()
                if (url.isEmpty()) {
                    return@addActionListener
                }
                browser.loadURL(addressField.text)
            }
        }
        add(addressField, BorderLayout.CENTER)
        add(goButton, BorderLayout.EAST)
    }
}