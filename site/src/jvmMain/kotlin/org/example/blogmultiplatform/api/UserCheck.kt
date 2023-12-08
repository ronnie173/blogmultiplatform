package org.example.blogmultiplatform.api

import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.blogmultiplatform.data.MongoDb
import org.example.blogmultiplatform.models.User
import org.example.blogmultiplatform.models.UserWithoutPassword
import java.security.MessageDigest

@Api(routeOverride = "usercheck")
suspend fun userCheck(context: ApiContext) {
  try {
      val userRequest = context.req.body?.decodeToString()?.let{
          Json.decodeFromString<User>(it)
      }

      val user = userRequest?.let {
          context.data.getValue<MongoDb>().checkUserExistence(
              User(
                  username = it.username,
                  password = hashPassword(it.password)
              )
          )
      }
      if (user != null) {
          context.res.setBodyText(
             Json.encodeToString(
                 UserWithoutPassword(
                     id = user.id,
                     username = user.username
                 )
             )
          )
      } else {
            context.res.setBodyText(
                Json.encodeToString(
                   Exception("User not found")
                )
            )
      }
  } catch (e: Exception) {
      context.res.setBodyText(
          Json.encodeToString(
              Exception(e.message)
          )
      )
  }
}

private fun hashPassword(password: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashBytes = messageDigest.digest(password.toByteArray(Charsets.UTF_8))
    val hesString = StringBuffer()

    for (byte in hashBytes) {
        hesString.append(String.format("%02x", byte))
    }
    return hesString.toString()
}