package com.gsuatavosdaniel.encurtador_url.links;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@RequestMapping("")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    @Operation(summary = "Encurta uma URL longa em uma URL curta")
    public ResponseEntity<LinkResponse> saveLink(@RequestBody @Valid LinkRequest linkRequest){

        LinkResponse link = linkService.savedLink(linkRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(link);

    }

    @GetMapping("/{shortUrl}")
    @Operation(summary = "Redireciona para a URL original")
    public ResponseEntity<String> getLink(@PathVariable String shortUrl){

        String urlOriginal = linkService.getOriginalLink(shortUrl);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(urlOriginal))
                .build();

    }

    @GetMapping("/allLinks")
    @Operation(summary = "Retorna todas as URLs encurtadas")
    public ResponseEntity<Page<LinkResponse>> getAllLinks(
            @ParameterObject
            @PageableDefault(size = 20, sort = "longUrl", direction = Sort.Direction.ASC)
            Pageable pageable
    ){

        Page<LinkResponse> links = linkService.getAllLinks(pageable);

        return ResponseEntity.ok(links);

    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma URL  pelo ID")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id){

        linkService.deleteLink(id);

        return ResponseEntity.noContent().build();

    }
}
