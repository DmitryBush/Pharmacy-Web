package com.bush.pharmacy_web_app.service.news;

import com.bush.pharmacy_web_app.model.dto.news.NewsImageDto;
import com.bush.pharmacy_web_app.model.dto.news.NewsReadDto;
import com.bush.pharmacy_web_app.model.entity.news.News;
import com.bush.pharmacy_web_app.model.entity.news.NewsImage;
import com.bush.pharmacy_web_app.repository.news.NewsImageRepository;
import com.bush.pharmacy_web_app.service.filesystem.FileSystemStorageService;
import com.bush.pharmacy_web_app.service.news.mapper.NewsImageCreateMapper;
import com.bush.pharmacy_web_app.service.news.mapper.NewsImageReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsImageService {
    private final NewsImageRepository imageRepository;

    private final NewsImageCreateMapper createMapper;
    private final NewsImageReadMapper newsImageReadMapper;

    private final FileSystemStorageService fileSystemStorageService;

    public Resource findImageById(Long imageId) {
        return fileSystemStorageService.loadAsResource("news/" + imageId,
                imageRepository.findById(imageId)
                        .map(NewsImage::getImageWorkPath)
                        .orElseThrow(IllegalArgumentException::new));
    }

    public void createNewsImage(final List<MultipartFile> multipartFiles, final News news) {
        for (MultipartFile multipartFile : multipartFiles) {
            if (Objects.nonNull(multipartFile)) {
                NewsImage newsImage = createMapper.mapToNewsImage(multipartFile, news);
                news.addImage(newsImage);
                fileSystemStorageService.store(multipartFile,
                        newsImage.getImageWorkPath(), "news/%d/".formatted(newsImage.getId()));
            }
        }
    }

    public void deleteNewsImagesByNewsId(Long newsId) {
        fileSystemStorageService.delete("news/%d".formatted(newsId));
    }
}
