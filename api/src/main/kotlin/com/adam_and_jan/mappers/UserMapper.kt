package com.adam_and_jan.mappers

import com.adam_and_jan.dto.UserDto
import com.adam_and_jan.dto.UserLoginDto
import com.adam_and_jan.models.User

object UserMapper {
    fun toDto(user: User): UserDto {
        return UserDto(
            user.username,
            user.email,
        )
    }
}