package vn.emiu.picabe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.emiu.picabe.entity.Frame;

import java.util.List;

@Repository
public interface FrameRepository extends JpaRepository<Frame, Long> {
}
