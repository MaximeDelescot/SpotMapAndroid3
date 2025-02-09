package com.spotmap.spotmapandroid.Class

enum class LoadableResourceType {
    LOADED, LOADING, NOT_LOADED, FAILED
}

class LoadableResource<T>(var status: LoadableResourceType = LoadableResourceType.NOT_LOADED,
                          var resource: T? = null,
                          var exception: Exception? = null) {

    companion object {
        fun <T> loaded(resource: T): LoadableResource<T> {
            return LoadableResource(
                status = LoadableResourceType.LOADED,
                resource = resource,
                exception = null
            )
        }

        fun <T> loading(): LoadableResource<T> {
            return LoadableResource(status = LoadableResourceType.LOADING)
        }

        fun <T> failed(exception: Exception): LoadableResource<T> {
            return LoadableResource(status = LoadableResourceType.FAILED, exception = exception)
        }

        fun <T> notLoaded(): LoadableResource<T> {
            return LoadableResource(status = LoadableResourceType.NOT_LOADED)
        }
    }
}