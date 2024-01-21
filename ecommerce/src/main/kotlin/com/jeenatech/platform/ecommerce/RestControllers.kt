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
class ArticleController (val awsCognitoServiceClient : AwsCognitoServiceClient) {
    val numberOfThreads = 4 // Adjust as needed
    val threadPool = newFixedThreadPoolContext(numberOfThreads, "CustomThreadPool")
    val articles = mutableListOf(Article("My first work","This is content"))

    @GetMapping
    fun articles() = articles

    @PostMapping("/signup")
    fun createUser(userSignUp: UserSignUp) {
        try {
            runBlocking {
                repeat(1) {
                    GlobalScope.launch(Dispatchers.Default ) {
                        val result =awsCognitoServiceClient.signUp("sharmaji","Lauda@1234","shar.vikash@gmail.com")
                        val dd = result;
                    }
                }
            }
        }
        catch (e:Exception) {

        }



    }


}