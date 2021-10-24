package es.davidmoros.poc.filmservice.controller;

import es.davidmoros.poc.filmservice.entity.Film;
import es.davidmoros.poc.filmservice.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Autowired
    FilmService filmService;

    @GetMapping
    public ResponseEntity<List<Film>> getAll() {
        List<Film> films = filmService.getAll();
        if(films.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(films);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getById(@PathVariable("id") int id) {
        Film film = filmService.getFilmById(id);
        if(film == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(film);
    }

    @PostMapping()
    public ResponseEntity<Film> save(@RequestBody Film film) {
        Film filmNew = filmService.save(film);
        return ResponseEntity.ok(filmNew);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Film>> getByUserId(@PathVariable("userId") int userId) {
        List<Film> films = filmService.byUserId(userId);
        return ResponseEntity.ok(films);
    }
}
