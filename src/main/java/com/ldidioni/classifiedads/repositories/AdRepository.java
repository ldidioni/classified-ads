package com.ldidioni.classifiedads.repositories;

import com.ldidioni.classifiedads.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdRepository extends JpaRepository<Ad, Integer>
{
    List<Ad> findDistinctByTitleIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(String title, String description);

    @Query(value = "SELECT * FROM ads a JOIN categories c ON a.category_id = c.id " +
            "WHERE ( a.title LIKE %:query% OR a.description LIKE %:query% ) " +
            "AND c.name = :categoryName", nativeQuery=true)
    List<Ad> findCustom(@Param("query") String query,
                        @Param("categoryName") String categoryName);

    @Query(value = "SELECT * FROM ads a JOIN categories c ON a.category_id = c.id JOIN ads_tags adta ON adta.ad_id = a.id " +
            "JOIN tags t ON t.id = adta.tag_id WHERE ( a.title LIKE %:query% OR a.description LIKE %:query% ) " +
            "AND c.name = :categoryName AND t.name IN :tagNames", nativeQuery=true)
    List<Ad> findCustomWithTags(@Param("query") String query,
                                @Param("categoryName") String categoryName,
                                @Param("tagNames") List<String> tagNames);
}
