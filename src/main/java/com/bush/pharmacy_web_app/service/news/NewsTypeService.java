package com.bush.pharmacy_web_app.service.news;

import com.bush.pharmacy_web_app.model.entity.news.NewsType;
import com.bush.pharmacy_web_app.repository.news.NewsTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsTypeService {
    private final NewsTypeRepository typeRepository;

    public List<String> findAllNewsTypes() {
        return typeRepository.findAll().stream()
                .map(NewsType::getType)
                .toList();
    }
}
