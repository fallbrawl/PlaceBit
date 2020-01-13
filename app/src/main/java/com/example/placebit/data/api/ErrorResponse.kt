package com.example.placebit.data.api

data class ErrorResponse(val errors : ArrayList<Errors>?) {

    data class Errors(val field : String?, val errors : ArrayList<String>?)

}