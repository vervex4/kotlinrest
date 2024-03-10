package com.jeenatech.platform.ecommerce

import aws.sdk.kotlin.services.cognitoidentityprovider.model.InvalidPasswordException
import com.jeenatech.platform.ecommerce.usermanagement.AwsCognitoServiceClient
import com.jeenatech.platform.ecommerce.usermanagement.model.UserSignUp
import kotlinx.coroutines.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(val userRepository: UserRepository, val awsCognitoServiceClient: AwsCognitoServiceClient) {

    //get all users
    @GetMapping("")
    fun getAllUsers(): List<User> =
        userRepository.findAll().toList()

    //create user
    @PostMapping("")
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val savedUser = userRepository.save(user)
        return ResponseEntity(savedUser, HttpStatus.CREATED)
    }

    @OptIn(DelicateCoroutinesApi::class)
    @PostMapping("/signup")
     fun createUser(userSignUp: UserSignUp): Any {
        var responseEntity :ResponseEntity<String>
        return try {
            runBlocking {

                GlobalScope.launch(Dispatchers.Default) {
                    responseEntity= awsCognitoServiceClient.signUp(userSignUp.userName, userSignUp.password, userSignUp.emailId)
                }
            }

        } catch (ez: InvalidPasswordException) {
            ResponseEntity.badRequest().body(ez.message)
        }
        catch (e: Exception) {
            ResponseEntity.badRequest().body("Something is wrong")
        }

    }
    @OptIn(DelicateCoroutinesApi::class)
    @PostMapping("/login")
    fun loginUser(userSignUp: UserSignUp): Any {
        return try {
            runBlocking {

                GlobalScope.launch(Dispatchers.Default) {
                    ResponseEntity.ok( awsCognitoServiceClient.login (userSignUp.userName, userSignUp.password,))
                }
            }

        } catch (e: Exception) {
            ResponseEntity.badRequest().body("Something is wrong")
        }

    }

    //get user by id
    @GetMapping("/{id}")
    fun getUserById(@PathVariable("id") userId: Int): ResponseEntity<User> {
        val user = userRepository.findById(userId).orElse(null)
        return if (user != null) {
            ResponseEntity(user, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    //update user
    @PutMapping("/{id}")
    fun updateUserById(@PathVariable("id") userId: Int, @RequestBody user: User): ResponseEntity<User> {
        val existingUser = userRepository.findById(userId).orElse(null)

        if (existingUser == null){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        val updatedUser = existingUser.copy(name = user.name, email = user.email)
        userRepository.save(updatedUser)
        return ResponseEntity(updatedUser, HttpStatus.OK)
    }

    //delete user
    @DeleteMapping("/{id}")
    fun deletedUSerById(@PathVariable("id") userId: Int): ResponseEntity<User> {
        if (!userRepository.existsById(userId)){
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }

        userRepository.deleteById(userId)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }
}