package vn.emiu.picabe.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.emiu.picabe.entity.Destination;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
}
