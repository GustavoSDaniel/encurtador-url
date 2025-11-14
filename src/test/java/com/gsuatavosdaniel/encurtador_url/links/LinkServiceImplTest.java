package com.gsuatavosdaniel.encurtador_url.links;

import org.hashids.Hashids;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkServiceImplTest {

    //Arrange (preparação), act (ação), assert (Verificação)

    @Mock
    private LinksRepository linksRepository;

    @Mock
    private LinkMapper linkMapper;

    @Mock
    private Hashids hashids;

    @InjectMocks
    private LinkServiceImpl linkService;

    @Nested
    class savedLink{

        @Test
        @DisplayName("Should save link with success")
        void shouldSaveLinkWithSuccess(){

            String input = "https://github.com/GustavoSDaniel/encurtador-url";

            LinkRequest linkRequest =
                    new LinkRequest(input);

            Links linkDB = new Links(input);

            ReflectionTestUtils.setField(linkDB, "id", 1L);

            String codigoCurto = "AbC123Xy";

            String meuDominio = "http://encurtadobygustavo.com";

            String urlCompleta = meuDominio + "/" + codigoCurto;

            ReflectionTestUtils.setField(linkService, "dominioUrl", meuDominio);

            when(linksRepository.findByLongUrl(input)).thenReturn(Optional.empty());

            when(linksRepository.save(any(Links.class))).thenReturn(linkDB);

            when(hashids.encode(1L)).thenReturn(codigoCurto);

            LinkResponse response = linkService.savedLink(linkRequest);

            assertNotNull(response);
            assertEquals(urlCompleta, response.urlCurta());

            verify(linksRepository).findByLongUrl(input);
            verify(linksRepository).save(any(Links.class));
            verify(hashids).encode(1L);
        }
    }

    @Nested
    class getOriginalLink {

        @Test
        @DisplayName("Should get original link with success")
        void shouldGetOriginalLinkWithSuccess() {

            Long id = 1L;

            String input = "XdTo6OLk";

            Links linkDB = new Links("https://github.com/GustavoSDaniel");

            when(hashids.decode(input)).thenReturn( new long[]{id});
            when(linksRepository.findById(id)).thenReturn(Optional.of(linkDB));

            String output = linkService.getOriginalLink(input);

            assertNotNull(output);

            verify(linksRepository).findById(id);
            verify(hashids).decode(input);

        }
    }

    @Nested
    class getAllLinks{

        @Test
        @DisplayName("Should get all links with success")
        void shouldGetAllLinksWithSuccess(){

            Pageable pageable = Pageable.unpaged();

            Links link1 = new Links("https://github.com/GustavoSDaniel");

            Links link2 = new Links("https://www.linkedin.com/feed/");

            Links link3 = new Links("https://x.com/DevDanielSilva");

            List<Links> linksDB =  List.of(link1, link2, link3);

            Page<Links> linksPage = new PageImpl<>(linksDB, pageable, linksDB.size());

            LinkResponse linkResponse1 = new LinkResponse("https://github.com/GustavoSDaniel");

            LinkResponse linkResponse2 = new LinkResponse("https://www.linkedin.com/feed/");

            LinkResponse linkResponse3 = new LinkResponse("https://x.com/DevDanielSilva");

            when(linksRepository.findAll(pageable)).thenReturn(linksPage);

           when(linkMapper.toLinkResponse(link1)).thenReturn(linkResponse1);

           when(linkMapper.toLinkResponse(link2)).thenReturn(linkResponse2);

           when(linkMapper.toLinkResponse(link3)).thenReturn(linkResponse3);

           Page<LinkResponse> output = linkService.getAllLinks(pageable);

           assertNotNull(output);
           assertEquals(3, output.getTotalElements());

           verify(linkMapper, times(3)).toLinkResponse(any(Links.class));
           verify(linksRepository).findAll(pageable);


        }
    }

    @Nested
    class deleteLink{

        @Test
        @DisplayName("Should delete link with success")
        void shouDeleteLinkSuccess(){

            Long linkId = 1L;

            Links link = new Links(
                    "https://github.com/GustavoSDaniel/encurtador-url"
            );

            when(linksRepository.findById(linkId)).thenReturn(Optional.of(link));

            linkService.deleteLink(linkId);

            verify(linksRepository).delete(link);

        }
    }


}