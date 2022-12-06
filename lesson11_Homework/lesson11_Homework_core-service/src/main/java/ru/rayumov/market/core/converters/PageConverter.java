package ru.rayumov.market.core.converters;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.rayumov.market.api.PageDto;

@Component
public class PageConverter {

    public PageDto entityToDto(Page page) {
        return new PageDto(page.getContent(), page.getTotalPages(), page.getNumber());
    }
}
