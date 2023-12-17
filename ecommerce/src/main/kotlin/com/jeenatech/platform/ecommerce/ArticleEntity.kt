package com.jeenatech.platform.ecommerce

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class ArticleEntity (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long?= null,
    var title : String,
    var content : String,
    var createdAt : LocalDateTime = LocalDateTime.now(),
    var slug : String = title.toSlug()

)