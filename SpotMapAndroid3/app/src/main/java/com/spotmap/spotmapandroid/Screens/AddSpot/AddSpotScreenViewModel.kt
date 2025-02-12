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
import com.spotmap.spotmapandroid.Services.CompressionType
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
import kotlin.Boolean
import kotlin.random.Random

class AddSpotScreenViewModel (val userHandler: UserHandler,
                              val apiService: APIService,
                              val storageService: StorageService): ViewModel() {

    enum class AddSpotScreenViewType { NOTLOGGED, ADDFORM, LOADING, FAILED, SUCCED }

    private val _viewToDisplay = MutableLiveData(getViewToDisplay())
    val viewToDisplay: LiveData<AddSpotScreenViewType> = _viewToDisplay

    private val _newSpot = MutableLiveData<Spot?>(null)
    val newSpot: LiveData<Spot?> = _newSpot

    init {
        userHandler.userDidUpdate.observeForever(Observer {
            if (_viewToDisplay.value != AddSpotScreenViewType.LOADING) {
                _viewToDisplay.value = getViewToDisplay()
            }
        })
    }

    private fun getViewToDisplay(): AddSpotScreenViewType {
        if (userHandler.getUser() == null) {
            return AddSpotScreenViewType.NOTLOGGED
        } else {
            return AddSpotScreenViewType.ADDFORM
        }
    }

    fun createSpot(
        name: String,
        description: String,
        type: SpotType,
        selectedImage: List<ImageView>,
        needToPay: Boolean,
        shelteredFromRain: Boolean,
        hasFixedHours: Boolean,
        hasLighting: Boolean
    ) {

        viewModelScope.launch {

            _viewToDisplay.value = AddSpotScreenViewType.LOADING
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
                    normalImageUrls = listOf(),
                    smallImageUrls = listOf(),
                    needToPay = needToPay,
                    shelteredFromRain = shelteredFromRain,
                    hasFixedHours = hasFixedHours,
                    hasLighting = hasLighting,
                    commentCount = 0,
                    videoCount = 0
                )

                try {

                    val smallUrls: MutableList<String> = mutableListOf()
                    val normalUrls: MutableList<String> = mutableListOf()
                    coroutineScope {
                        val deferredNormalResults = selectedImage.map { image ->
                            async {
                                val normalUrl = storageService.save(
                                    imageView = image,
                                    spot = newSpot,
                                    compressionType = CompressionType.normal())
                                normalUrl
                            }
                        }

                        val deferredSmallResults = selectedImage.map { image ->
                            async {
                                val smallUrl = storageService.save(
                                    imageView = image,
                                    spot = newSpot,
                                    compressionType = CompressionType.verySmall())
                                smallUrl
                            }
                        }

                        normalUrls.addAll(deferredNormalResults.awaitAll())
                        smallUrls.addAll(deferredSmallResults.awaitAll())
                    }
                    newSpot.smallImageUrls = smallUrls
                    newSpot.normalImageUrls = normalUrls

                    apiService.addSpot(newSpot)
                    _viewToDisplay.value = AddSpotScreenViewType.SUCCED
                    _newSpot.value = newSpot

                } catch (e: Exception) {
                    Log.d("TEST ERROR", "$e")
                    storageService.deleteSpotFolder(newSpot)
                    _viewToDisplay.value = AddSpotScreenViewType.FAILED
                }
            }
        }
    }
}
