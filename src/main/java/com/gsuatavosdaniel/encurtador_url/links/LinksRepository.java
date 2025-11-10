package com.gsuatavosdaniel.encurtador_url.links;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinksRepository extends JpaRepository<Links, Long> {

    Optional<Links> findByLongUrl(String longUrl);


}
