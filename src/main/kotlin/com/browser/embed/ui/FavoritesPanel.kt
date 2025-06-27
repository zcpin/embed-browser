package com.browser.embed.ui

import com.browser.embed.FavoritesManager
import com.browser.embed.model.FavoritePage
import com.intellij.ide.DataManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.intellij.ui.awt.RelativePoint
import com.intellij.ui.components.JBList
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.jcef.JBCefBrowser
import java.awt.BorderLayout
import java.awt.event.MouseAdapter
import javax.swing.*

class FavoritesPanel(private val browser: JBCefBrowser, addressBar: AddressBarPanel, project: Project) :
    JPanel(BorderLayout()) {
    private val favoritesModel = DefaultListModel<FavoritePage>()
    private val favoritesList = JBList(favoritesModel).apply {
        cellRenderer = object : DefaultListCellRenderer() {
            override fun getListCellRendererComponent(
                list: JList<*>?,
                value: Any?,
                index: Int,
                isSelected: Boolean,
                cellHasFocus: Boolean
            ): java.awt.Component {
                val label = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus) as JLabel
                val favorite = value as FavoritePage
                label.text = favorite.title
                return label
            }
        }

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: java.awt.event.MouseEvent?) {
                if (e?.clickCount == 2) {
                    selectedValue?.let {
                        browser.loadURL(it.url)
                        addressBar.addressField.text = it.url // 刷新地址栏
                    }
                }
            }

            override fun mousePressed(e: java.awt.event.MouseEvent?) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    val index = locationToIndex(e?.point)
                    selectedIndex = index
                    var dataContext = DataManager.getInstance().getDataContext(this@apply)
                    val popup = JBPopupFactory.getInstance().createActionGroupPopup("操作", DefaultActionGroup(object :
                        AnAction("删除") {
                        override fun actionPerformed(p0: AnActionEvent) {
                            if (selectedValue != null) {
                                FavoritesManager.getInstance().removeFavorite(selectedValue!!)
                                updateList()
                            }

                        }
                    }), dataContext, JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, true)
                    e?.let { popup.show(RelativePoint(it)) }
                }
            }
        })
    }

    fun updateList() {
        val favorites = FavoritesManager.getInstance().getFavorites()
        val model = DefaultListModel<FavoritePage>().apply {
            favorites.forEach {
                addElement(it)
            }
        }
        favoritesList.model = model
    }


    init {
        val scrollPane = JBScrollPane(favoritesList)
        val topPanel = JPanel(BorderLayout())
        topPanel.add(scrollPane, BorderLayout.CENTER)
        add(topPanel, BorderLayout.CENTER)

        FavoritesManager.getInstance().getFavorites().forEach {
            favoritesModel.addElement(it)
        }
    }
}