package com.browser.embed

import com.browser.embed.model.FavoritePage
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.service

@Service
@State(name = "FavoritesManager", storages = [com.intellij.openapi.components.Storage("favorites.xml")])
class FavoritesManager : PersistentStateComponent<FavoritesManager.State> {
    data class State(
        var favorites: MutableList<FavoritePage> = mutableListOf(),
    )

    private var state = State()
    override fun getState() = state

    override fun loadState(state: FavoritesManager.State) {
        this.state = state
    }

    fun addFavorite(favorite: FavoritePage) {
        if (!state.favorites.any { it.url == favorite.url }) {
            state.favorites.add(favorite)
        }
    }

    fun removeFavorite(favorite: FavoritePage) {
        state.favorites.remove(favorite)
    }

    fun getFavorites(): List<FavoritePage> = state.favorites

    companion object{
        fun getInstance() = service<FavoritesManager>()
    }
}