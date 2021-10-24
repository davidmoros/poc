package es.davidmoros.poc.filmservice.service;

import es.davidmoros.poc.filmservice.entity.Film;
import es.davidmoros.poc.filmservice.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilmService {

    @Autowired
    FilmRepository filmRepository;

    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    public Film getFilmById(int id) {
        return filmRepository.findById(id).orElse(null);
    }

    public Film save(Film film) {
        return filmRepository.save(film);
    }

    public List<Film> byUserId(int userId) {
        return filmRepository.findByUserId(userId);
    }
}
