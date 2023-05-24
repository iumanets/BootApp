package com.ironsource.bootapp.ui.state

sealed interface ScreenState {
    object Loading : ScreenState
    object Error :ScreenState
    data class Loaded(val text:String) : ScreenState
}