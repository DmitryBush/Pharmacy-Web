package com.bush.pharmacy_web_app.service.news;

import com.bush.pharmacy_web_app.model.dto.news.NewsImageDto;
import com.bush.pharmacy_web_app.model.entity.news.News;
import com.bush.pharmacy_web_app.model.entity.news.NewsImage;
import com.bush.pharmacy_web_app.repository.news.NewsImageRepository;
import com.bush.pharmacy_web_app.repository.news.NewsRepository;
import com.bush.pharmacy_web_app.service.exception.StorageException;
import com.bush.pharmacy_web_app.service.news.mapper.NewsImageCreateMapper;
import com.bush.pharmacy_web_app.service.news.mapper.NewsImageReadMapper;
import com.bush.pharmacy_web_app.service.storage.BucketConstantEnum;
import com.bush.pharmacy_web_app.service.storage.ObjectStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsImageService {
    private final NewsImageRepository imageRepository;
    private final NewsRepository newsRepository;

    private final NewsImageCreateMapper createMapper;
    private final NewsImageReadMapper newsImageReadMapper;

    private final ObjectStorageService objectStorageService;

    public Resource findImageById(Long imageId) {
        return Optional.ofNullable(imageId)
                .flatMap(imageRepository::findById)
                .map(NewsImage::getImageLinkPath)
                .map(link -> objectStorageService.loadResource(BucketConstantEnum.NEWS, link))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public List<NewsImageDto> attachImageToNews(String slug, List<MultipartFile> multipartFiles) {
        News news = newsRepository.findBySlug(slug)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        List<NewsImage> newsImageList = new ArrayList<>();
        try {
            for (MultipartFile file : multipartFiles) {
                NewsImage image = createMapper.mapToNewsImage(file, news);
                objectStorageService.store(file, BucketConstantEnum.NEWS, image.getImageLinkPath());
                newsImageList.add(image);
            }
            imageRepository.saveAll(newsImageList);
            return newsImageList.stream()
                    .map(newsImageReadMapper::map)
                    .toList();
        } catch (StorageException e) {
            newsImageList.forEach(image ->
                    objectStorageService.delete(BucketConstantEnum.NEWS, image.getImageLinkPath()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void deleteNewsImages(News news) {
        news.getNewsImageList().forEach(image ->
                objectStorageService.delete(BucketConstantEnum.NEWS, image.getImageLinkPath()));
    }

    @Transactional
    public void deleteNewsImageById(Long id) {
        Optional.ofNullable(id)
                .flatMap(imageRepository::findById)
                .ifPresentOrElse(image -> {
                    imageRepository.delete(image);
                    objectStorageService.delete(BucketConstantEnum.NEWS, image.getImageLinkPath());
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                });
    }
}
