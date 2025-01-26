package com.spotmap.spotmapandroid.Screens.AddSpot

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spotmap.spotmapandroid.Class.Coordinate
import com.spotmap.spotmapandroid.Class.Spot
import com.spotmap.spotmapandroid.Class.SpotType
import com.spotmap.spotmapandroid.Services.APIService
import com.spotmap.spotmapandroid.Services.StorageService
import com.spotmap.spotmapandroid.Services.UserHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class AddSpotScreenViewModel (val userHandler: UserHandler,
                              val apiService: APIService,
                              val storageService: StorageService): ViewModel() {

    enum class AddSpotScreenViewType { NOTLOGGED, ADDFORM, LOADING, FAILED, SUCCED }

    private val _viewToDisplay = MutableLiveData(getViewToDisplay())
    val viewToDisplay: LiveData<AddSpotScreenViewType> = _viewToDisplay

    init {
        userHandler.userDidUpdate.observeForever(Observer {
            if(_viewToDisplay.value != AddSpotScreenViewType.LOADING) {
                _viewToDisplay.value = getViewToDisplay()
            }
        })
    }

    private fun getViewToDisplay(): AddSpotScreenViewType {
        if(userHandler.getUser() == null) {
            return AddSpotScreenViewType.NOTLOGGED
        } else {
            return AddSpotScreenViewType.ADDFORM
        }
    }

    fun createSpot(name: String,
                   description: String,
                   type: SpotType,
                   selectedImage: List<ImageView>) {

        _viewToDisplay.value = AddSpotScreenViewType.LOADING

        viewModelScope.launch {
            try {
                val fakeLati = Random.nextDouble(48.80, 48.90)
                val fakeLong = Random.nextDouble(2.26, 2.40)

                val creator = userHandler.getUserLight()
                if (creator != null) {

                    val newSpot = Spot(
                        name = name,
                        creator = creator,
                        description = description,
                        spotEnum = type,
                        coordinate = Coordinate(fakeLati, fakeLong),
                        imageUrls = listOf())

                    val urls: MutableList<String> = mutableListOf()
                    coroutineScope {
                        val deferredResults = selectedImage.map { image ->
                            async {
                                val downloadUrl = storageService.save(image, newSpot)
                                downloadUrl
                            }
                        }
                        urls.addAll(deferredResults.awaitAll())
                    }

                    newSpot.imageUrls = urls
                    apiService.addSpot(newSpot)
                    _viewToDisplay.value = AddSpotScreenViewType.SUCCED
                } else {
                    throw Throwable(message = "Failed to create spot: user is null")
                }
            } catch (e: Exception) {
                _viewToDisplay.value = AddSpotScreenViewType.FAILED
            }
        }
    }

//    fun createSpot(name: String, description: String, type: SpotType, selectedImages: List<ImageView>) {
//        _viewToDisplay.value = AddSpotScreenViewType.LOADING
//
//        val fakeLati = Random.nextDouble(48.80, 48.90)
//        val fakeLong = Random.nextDouble(2.26, 2.40)
//
//        viewModelScope.launch {
//            try {
//                val creator = userHandler.getUserLight()
//
//                if (creator != null) {
//                    val newSpot = Spot(
//                        name = name,
//                        creator = creator,
//                        description = description,
//                        spotEnum = type,
//                        coordinate = Coordinate(fakeLati, fakeLong),
//                        imageUrls = listOf() // Initially empty list
//                    )
//
//                    val deferredUploads = selectedImages.map { image ->
//                        async  {
//                            storageService.save(image, newSpot, object : StorageService.UploadCallback {
//                                override fun onSuccess(downloadUrl: String) {
//                                    return downloadUrl
//                                }
//                                override fun onFailure() {
//                                    throw Throwable(message = "Creation spot failed: Images upload failed")
//                                }
//                            })
//                        }
//                    }
//
//                    val imageUrls: MutableList<String> = mutableListOf()
//                    for (image in selectedImages) {
//
//                    }
//
//
//                } else {
//                    throw Throwable(message = "Creation spot failed: user is null")
//                }
//            } catch (e: Exception) {
//                launch(Dispatchers.Main) {
//                    _viewToDisplay.value = AddSpotScreenViewType.FAILED
//                }
//            }
//        }
//    }
}
