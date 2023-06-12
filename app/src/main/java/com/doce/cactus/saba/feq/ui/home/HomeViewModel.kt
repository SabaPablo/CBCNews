package com.doce.cactus.saba.feq.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doce.cactus.saba.feq.models.Show
import com.doce.cactus.saba.feq.repositories.ShowRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val showRepository: ShowRepository) : ViewModel(){

    private val eventsChannel = Channel<HomeEvents>(Channel.UNLIMITED)
    val events = eventsChannel.receiveAsFlow()

    private val _showsLiveData: MutableLiveData<List<Show>> = MutableLiveData<List<Show>>()
    val showsLiveData: LiveData<List<Show>> = _showsLiveData
    val showsFilteredByDay: MutableLiveData<List<Show>> = MutableLiveData()


    fun getShows(){
        viewModelScope.launch(Dispatchers.IO) {
            showRepository.getShows().onStart {

            }.catch {

            }.collect {
                _showsLiveData.postValue(it)
                showsFilteredByDay.postValue(getShowsOf(1))
            }
        }
    }

    private fun getShowsOf(day: Int): List<Show>? {
        return showsLiveData.value?.filter { it.day == day }
    }


    fun showEventsDay(day: Int) {
        showsFilteredByDay.postValue(getShowsOf(day))
    }



}

sealed class HomeEvents {
    object ErrorNews : HomeEvents()
    object ErrorDBNews : HomeEvents()
    object EmptyDBNews : HomeEvents()

}