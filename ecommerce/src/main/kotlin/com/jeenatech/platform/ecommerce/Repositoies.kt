package com.jeenatech.platform.ecommerce

import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<ArticleEntity, Long>{
    fun findAllByOrderByCreatedAtDesc() : Iterable<Article>

}