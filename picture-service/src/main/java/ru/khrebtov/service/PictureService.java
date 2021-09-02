package ru.khrebtov.service;

import ru.khrebtov.persist.entity.Picture;

import java.util.List;
import java.util.Optional;

public interface PictureService {

    Optional<String> getPictureContentTypeById(long id);

    Optional<byte[]> getPictureDataById(long id);

    String createPicture(byte[] picture);

    List<Picture> getAllProductPictures(Long productId);

    void deleteById(Long id);
}