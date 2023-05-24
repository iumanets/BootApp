package com.ironsource.bootapp.ui
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ironsource.bootapp.data.BootEventRepo
import com.ironsource.bootapp.data.local.model.SortingOrder
import com.ironsource.bootapp.ui.mapper.UiMapper
import com.ironsource.bootapp.ui.state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val uiMapper: UiMapper,
    private val rebootEventRepository: BootEventRepo
) : ViewModel() {
    private val _uiState = MutableLiveData<ScreenState>(ScreenState.Loading)
    val uiState: LiveData<ScreenState> = _uiState

    init {
        viewModelScope.launch {
            val reboots = rebootEventRepository.getEvents(SortingOrder.ASC)
            _uiState.value = ScreenState.Loaded(uiMapper.map(reboots.map { it.date }))
        }
    }
}
