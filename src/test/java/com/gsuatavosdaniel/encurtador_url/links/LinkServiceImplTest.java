package com.gsuatavosdaniel.encurtador_url.links;

import org.hashids.Hashids;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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