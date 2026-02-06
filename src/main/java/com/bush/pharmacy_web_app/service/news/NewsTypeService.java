package com.bush.pharmacy_web_app.service.news;

import com.bush.pharmacy_web_app.model.dto.news.NewsTypeDto;
import com.bush.pharmacy_web_app.model.entity.news.News;
import com.bush.pharmacy_web_app.model.entity.news.NewsType;
import com.bush.pharmacy_web_app.repository.news.NewsTypeRepository;
import com.bush.pharmacy_web_app.service.news.mapper.NewsTypeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsTypeService {
    private final NewsTypeRepository typeRepository;

    private final NewsTypeMapper typeMapper;

    public List<NewsTypeDto> findAllNewsTypes() {
        return typeRepository.findAll().stream()
                .map(typeMapper::mapToNewsTypeDto)
                .toList();
    }

    public NewsType getReferenceById(Short id) {
        return typeRepository.getReferenceById(id);
    }
}
