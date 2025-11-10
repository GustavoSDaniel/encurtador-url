package com.gsuatavosdaniel.encurtador_url.links;

import org.springframework.stereotype.Component;

@Component
public class LinkMapper {

    LinkResponse toLinkResponse(Links links) {
        if(links==null) return null;

        return new LinkResponse(
                links.getLongUrl()
        );
    }
}
