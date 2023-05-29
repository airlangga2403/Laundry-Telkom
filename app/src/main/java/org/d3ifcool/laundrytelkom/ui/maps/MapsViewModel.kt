package org.d3ifcool.laundrytelkom.ui.maps

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3ifcool.laundrytelkom.api.MapApiLaundry
import org.d3ifcool.laundrytelkom.model.Laundry

class MapsViewModel : ViewModel() {

    private val data = MutableLiveData<List<Laundry>>()
    fun requestData() = viewModelScope.launch(Dispatchers.IO ) {
        withContext(Dispatchers.IO){
            try {
                val result = MapApiLaundry.service.getData()

                data.postValue(result.data)
            }
            catch (e: Exception) {
                Log.d("Maps","Error Maps ${e.message.toString()}")
            }
        }
    }

    fun getData(): LiveData<List<Laundry>> = data

}