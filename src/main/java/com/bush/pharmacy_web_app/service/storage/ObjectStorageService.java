package com.bush.pharmacy_web_app.service.storage;

import com.bush.pharmacy_web_app.service.exception.StorageException;
import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
public class ObjectStorageService {
    private final MinioClient minioClient;

    public ObjectStorageService(@Value("${storage.object.endpoint}") String endpointUrl,
                                @Value("${storage.object.access-key}") String accessKey,
                                @Value("${storage.object.secret-key}") String secretKey) {
        minioClient = MinioClient.builder()
                .endpoint(endpointUrl)
                .credentials(accessKey, secretKey)
                .build();
    }

    public void store(InputStream inputStream, Long streamSize, BucketConstantEnum bucket, String objectReference) {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket.getBucket()).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket.getBucket()).build());
            }

            minioClient.putObject(PutObjectArgs.builder()
                    .stream(inputStream, streamSize, -1)
                    .bucket(bucket.getBucket())
                    .object(objectReference)
                    .build());
        } catch (ServerException | InsufficientDataException | ErrorResponseException | IOException |
                 NoSuchAlgorithmException | InvalidKeyException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            log.error("An error has occurred while saving object");
            throw new StorageException("An error has occurred while saving object", e);
        }
    }

    public void store(MultipartFile file, BucketConstantEnum bucket, String objectReference) {
        try {
            store(file.getInputStream(), file.getSize(), bucket, objectReference);
        } catch (IOException e) {
            log.error("An error occurred while receiving the input stream");
            throw new RuntimeException("An error occurred while receiving the input stream", e);
        }
    }

    public void delete(BucketConstantEnum bucket, String objectReference) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                            .bucket(bucket.getBucket())
                            .object(objectReference)
                    .build());
        } catch (ServerException | InsufficientDataException | InternalException | XmlParserException |
                 InvalidResponseException | InvalidKeyException | NoSuchAlgorithmException | IOException |
                 ErrorResponseException e) {
            log.error("An error has occurred while deleting object");
            throw new StorageException("An error has occurred while deleting object", e);
        }
    }

    public Resource loadResource(BucketConstantEnum bucket, String objectReference) {
        try(InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket.getBucket()).object(objectReference).build())) {
            Resource resource = new ByteArrayResource(inputStream.readAllBytes());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            throw new StorageException("Resource isn't readable or exist");
        } catch (ServerException | InternalException | XmlParserException | InvalidResponseException |
                 InvalidKeyException | NoSuchAlgorithmException | IOException | ErrorResponseException |
                 InsufficientDataException e) {
            log.error("An error has occurred while reading file - {}", e.getMessage());
            throw new StorageException("An error has occurred while reading file", e);
        }
    }
}
