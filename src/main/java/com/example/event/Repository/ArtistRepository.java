package com.example.event.Repository;

import com.example.event.Model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArtistRepository extends JpaRepository<Artist,Integer> {
    Artist getArtistById(Integer id);
}
