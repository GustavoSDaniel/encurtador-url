package com.gsuatavosdaniel.encurtador_url.links;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LinkService {

     LinkResponse savedLink(LinkRequest linkRequest);

     String getOriginalLink(String linkCurto);

     Page<LinkResponse> getAllLinks(Pageable pageable);

     void deleteLink(Long id);
}
