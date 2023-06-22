package com.sushil.qr.models

class SessionModel(
    val access_token: String,
    val token_type: String,
    val expires_in: Long,
    val userName: String,
    val issued: String,
    val expires: String,
)

