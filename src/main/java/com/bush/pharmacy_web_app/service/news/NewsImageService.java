package com.bush.pharmacy_web_app.service.news;

import com.bush.pharmacy_web_app.model.dto.news.NewsImageDto;
import com.bush.pharmacy_web_app.model.entity.news.News;
import com.bush.pharmacy_web_app.model.entity.news.NewsImage;
import com.bush.pharmacy_web_app.repository.news.NewsImageRepository;
import com.bush.pharmacy_web_app.repository.news.NewsRepository;
import com.bush.pharmacy_web_app.service.exception.StorageException;
import com.bush.pharmacy_web_app.service.filesystem.FileSystemStorageService;
import com.bush.pharmacy_web_app.service.news.mapper.NewsImageCreateMapper;
import com.bush.pharmacy_web_app.service.news.mapper.NewsImageReadMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsImageService {
    private final NewsImageRepository imageRepository;
    private final NewsRepository newsRepository;

    private final NewsImageCreateMapper createMapper;
    private final NewsImageReadMapper newsImageReadMapper;

    private final FileSystemStorageService fileSystemStorageService;

    public Resource findImageById(Long imageId) {
        return fileSystemStorageService.loadAsResource("news/" + imageId,
                imageRepository.findById(imageId)
                        .map(NewsImage::getImageLinkPath)
                        .orElseThrow(IllegalArgumentException::new));
    }

    @Transactional
    public NewsImageDto attachImageToNews(String slug, MultipartFile multipartFile) {
        News news = newsRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        NewsImage newsImage = Optional.ofNullable(multipartFile)
                .map(file -> createMapper.mapToNewsImage(multipartFile, news))
                .map(imageRepository::save)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        try {
            fileSystemStorageService.storeByFullPath(multipartFile, newsImage.getImageLinkPath());
            return newsImageReadMapper.map(newsImage);
        } catch (StorageException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteNewsImages(News news) {
        news.getNewsImageList().forEach(image -> fileSystemStorageService.delete(image.getImageLinkPath()));
    }

    @Transactional
    public void deleteNewsImageById(Long id) {
        Optional.ofNullable(id)
                .flatMap(imageRepository::findById)
                .ifPresentOrElse(image -> {
                    imageRepository.delete(image);
                    fileSystemStorageService.delete(image.getImageLinkPath());
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }
}
