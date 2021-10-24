package es.davidmoros.poc.filmservice.repository;

import es.davidmoros.poc.filmservice.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Integer> {

    List<Film> findByUserId(int userId);
}
