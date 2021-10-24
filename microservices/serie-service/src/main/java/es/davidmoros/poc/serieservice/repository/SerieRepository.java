package es.davidmoros.poc.serieservice.repository;

import es.davidmoros.poc.serieservice.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Integer> {

    List<Serie> findByUserId(int userId);
}
