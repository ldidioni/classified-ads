package com.ldidioni.classifiedads.repositories;

import com.ldidioni.classifiedads.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Integer>
{
    List<Ad> findDistinctByTitleIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(String title, String description);
/*
    @Query("select a from Ad a inner join a.category c " +
            "where a.title like %:title or a.description like %:description " +
            "and c.name = :categoryName") // inner join a.tags t // and t.name in :tagNames
    List<Ad> findCustom(@Param("title") String title,
                        @Param("description") String description,
                        @Param("categoryName") String categoryName); //,@Param("tagNames") String[] tagNames
*/
    @Query(value = "SELECT * FROM ads a JOIN categories c ON a.category_id = c.id JOIN ads_tags adta ON adta.ad_id = a.id " +
            "WHERE a.title LIKE %:query% OR a.description LIKE %:query% AND c.name = :categoryName", nativeQuery=true)
    List<Ad> findCustom(@Param("query") String query,
                        @Param("categoryName") String categoryName);

}
