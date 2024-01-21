package com.jeenatech.platform.ecommerce

import com.jeenatech.platform.ecommerce.usermanagement.AwsCognitoServiceClient
import com.jeenatech.platform.ecommerce.usermanagement.model.UserSignUp
import kotlinx.coroutines.*
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController () {
    val numberOfThreads = 4 // Adjust as needed
    val threadPool = newFixedThreadPoolContext(numberOfThreads, "CustomThreadPool")
    val articles = mutableListOf(Article("My first work","This is content"))

    @GetMapping
    fun articles() = articles




}