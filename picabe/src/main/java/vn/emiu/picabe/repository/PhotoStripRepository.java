package vn.emiu.picabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.emiu.picabe.entity.PhotoStrip;

import java.util.List;

@Repository
public interface PhotoStripRepository extends JpaRepository<PhotoStrip, Long> {
    List<PhotoStrip> findByUserId(Long userId);
}
