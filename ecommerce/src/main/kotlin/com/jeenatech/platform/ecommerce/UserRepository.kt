package com.jeenatech.platform.ecommerce

import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Int>