package com.th.papago.dto

data class PapagoDto(var message: Message? = null){
    data class Message(var result: Result? = null){
        data class Result(var translateText: String? = null)
    }
}
