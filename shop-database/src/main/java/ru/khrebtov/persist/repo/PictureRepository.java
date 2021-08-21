package ru.khrebtov.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.khrebtov.persist.entity.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {

}
