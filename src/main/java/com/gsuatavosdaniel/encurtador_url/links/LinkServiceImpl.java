package com.gsuatavosdaniel.encurtador_url.links;

import org.hashids.Hashids;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@CacheConfig(cacheNames = "links")
public class LinkServiceImpl implements LinkService {

    private final LinksRepository linksRepository;
    private final Hashids hashids;
    private final LinkMapper linkMapper;
    private final static Logger log = LoggerFactory.getLogger(LinkServiceImpl.class);

    public LinkServiceImpl(LinksRepository linksRepository, Hashids hashids, LinkMapper linkMapper) {
        this.linksRepository = linksRepository;
        this.hashids = hashids;
        this.linkMapper = linkMapper;
    }

    @Value("${DOMINIO_APP}")
    String dominioUrl;

    @Override
   @CacheEvict(cacheNames = "links-page", allEntries = true)
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

    @Override
    @Cacheable(key = "#linkCurto")
    public String getOriginalLink(String linkCurto) {

        long[] ids = hashids.decode(linkCurto);

        if (ids.length == 0) {

            log.info("Link Nao Encontrado");

            throw new LinkNotFoundException();
        }

        long idLink = ids[0];

        log.info("ID do Link Encontrado com sucesso: {}", idLink);

        return linksRepository.findById(idLink)
                .map(Links::getLongUrl)
                .orElseThrow(LinkNotFoundException::new);
    }

    @Override
    @Cacheable(
            cacheNames = "links-page",
            key = "'page-' + #pageable.pageNumber   + '-size-' + #pageable.pageSize + '-sort-' + #pageable.sort.toString()")
    public Page<LinkResponse> getAllLinks(Pageable pageable) {

        Page<Links> allLinks = linksRepository.findAll(pageable);

        if (allLinks.isEmpty()) {

            log.info("Nenhum Link encontrado");

            return Page.empty(pageable);
        }

        log.info(" Todos Links encontrados com sucesso: {}", allLinks.getTotalElements());

        return allLinks.map(linkMapper::toLinkResponse);
    }


    @Override
    @Caching(evict = {
            @CacheEvict(cacheNames = "links", allEntries = true),
            @CacheEvict(cacheNames = "links-page", allEntries = true),
    })
    public void deleteLink(Long id) {

        Links link = linksRepository.findById(id).orElseThrow(LinkNotFoundException::new);

        linksRepository.delete(link);

        log.info("Link deletado com sucesso: {}", link);
    }
}
