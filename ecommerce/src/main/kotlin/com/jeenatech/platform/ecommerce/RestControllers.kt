package com.jeenatech.platform.ecommerce

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/articles")
class ArticleController {

    val articles = mutableListOf(Article("My first work","This is content"))

    @GetMapping
    fun articles() = articles


}