package com.gsuatavosdaniel.encurtador_url.links;

import jakarta.transaction.Transactional;
import org.hashids.Hashids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LinkServiceImpl implements LinkService {

    private final LinksRepository linksRepository;
    private final Hashids hashids;
    private final static Logger log = LoggerFactory.getLogger(LinkServiceImpl.class);

    public LinkServiceImpl(LinksRepository linksRepository, Hashids hashids) {
        this.linksRepository = linksRepository;
        this.hashids = hashids;
    }

    @Value("${DOMINIO_APP}")
    String dominioUrl;

    @Override
    public LinkResponse savedLink(LinkRequest linkRequest) {

        Optional<Links> linkExiste = linksRepository.findByLongUrl(linkRequest.longUrl());

        if (linkExiste.isPresent()) {
            throw new UrlJaExisteException();
        }

        Links linkLong = new Links(linkRequest.longUrl());

        Links linkSaved = linksRepository.save(linkLong);

        log.info("Link Salvado com sucesso: {}", linkSaved);

        Long idLink = linkSaved.getId();

        String linkCurto =hashids.encode(idLink);

        String linkCompleto = dominioUrl + "/" + linkCurto;

        log.info("Link encurtado com sucesso: {}", linkCompleto);

        return new LinkResponse(linkCompleto);
    }
}
