package com.bush.pharmacy_web_app.service.news;

import com.bush.pharmacy_web_app.model.entity.news.NewsImage;
import com.bush.pharmacy_web_app.repository.news.NewsImageRepository;
import com.bush.pharmacy_web_app.service.filesystem.FileSystemStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsImageService {
    private final NewsImageRepository imageRepository;

    private final FileSystemStorageService fileSystemStorageService;

    public Resource findImageById(Long imageId) {
        return fileSystemStorageService.loadAsResource("news/" + imageId,
                imageRepository.findById(imageId)
                        .map(NewsImage::getImageWorkPath)
                        .orElseThrow(IllegalArgumentException::new));
    }
}
