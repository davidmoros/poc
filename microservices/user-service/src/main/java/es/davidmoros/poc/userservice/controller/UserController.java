package es.davidmoros.poc.userservice.controller;

import es.davidmoros.poc.userservice.entity.User;
import es.davidmoros.poc.userservice.model.Film;
import es.davidmoros.poc.userservice.model.Serie;
import es.davidmoros.poc.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        List<User> users = userService.getAll();
        if(users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id) {
        User user = userService.getUserById(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping()
    public ResponseEntity<User> save(@RequestBody User user) {
        User userNew = userService.save(user);
        return ResponseEntity.ok(userNew);
    }

    @CircuitBreaker(name = "serieCB", fallbackMethod = "fallBackGetSeries")
    @GetMapping("/{userId}/series")
    public ResponseEntity<List<Serie>> getSeries(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Serie> series = userService.getSeries(userId);
        return ResponseEntity.ok(series);
    }

    @CircuitBreaker(name = "serieCB", fallbackMethod = "fallBackSaveSerie")
    @PostMapping("/{userId}/serie")
    public ResponseEntity<Serie> saveSerie(@PathVariable("userId") int userId, @RequestBody Serie serie) {
        if(userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        Serie serieNew = userService.saveSerie(userId, serie);
        return ResponseEntity.ok(serie);
    }

    @CircuitBreaker(name = "filmCB", fallbackMethod = "fallBackGetFilms")
    @GetMapping("/{userId}/films")
    public ResponseEntity<List<Film>> getFilms(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if(user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Film> films = userService.getFilms(userId);
        return ResponseEntity.ok(films);
    }

    @CircuitBreaker(name = "filmCB", fallbackMethod = "fallBackSaveFilm")
    @PostMapping("/{userId}/film")
    public ResponseEntity<Film> saveFilm(@PathVariable("userId") int userId, @RequestBody Film film) {
        if(userService.getUserById(userId) == null) {
            return ResponseEntity.notFound().build();
        }
        Film filmNew = userService.saveFilm(userId, film);
        return ResponseEntity.ok(film);
    }

    @CircuitBreaker(name = "allCB", fallbackMethod = "fallBackGetAll")
    @GetMapping("/{userId}/all")
    public ResponseEntity<Map<String, Object>> getAllWatching(@PathVariable("userId") int userId) {
        Map<String, Object> result = userService.getAllWatching(userId);
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<List<Serie>> fallBackGetSeries(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity(String.format("El usuario %s perdió la conexión con internet", userId), HttpStatus.OK);
    }

    private ResponseEntity<Serie> fallBackSaveSerie(@PathVariable("userId") int userId, @RequestBody Serie serie, RuntimeException e) {
        return new ResponseEntity(String.format("El usuario %s no paga la cuota de Netflix", userId), HttpStatus.OK);
    }

    private ResponseEntity<List<Film>> fallBackGetFilms(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity(String.format("El usuario %s perdió la conexión con internet", userId), HttpStatus.OK);
    }

    private ResponseEntity<Serie> fallBackSaveFilm(@PathVariable("userId") int userId, @RequestBody Film film, RuntimeException e) {
        return new ResponseEntity(String.format("El usuario %s no paga la cuota de Netflix", userId), HttpStatus.OK);
    }

    public ResponseEntity<Map<String, Object>> fallBackGetAll(@PathVariable("userId") int userId, RuntimeException e) {
        return new ResponseEntity("El usuario " + userId + " tiene los vehículos en el taller", HttpStatus.OK);
    }

}
