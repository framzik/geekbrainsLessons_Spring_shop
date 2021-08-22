package ru.khrebtov.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khrebtov.persist.entity.Picture;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    List<Picture> getAllByProductId(Long productId);
}
