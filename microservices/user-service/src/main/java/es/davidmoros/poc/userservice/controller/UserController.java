package es.davidmoros.poc.userservice.controller;

import es.davidmoros.poc.userservice.entity.User;
import es.davidmoros.poc.userservice.model.Film;
import es.davidmoros.poc.userservice.model.Serie;
import es.davidmoros.poc.userservice.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import brave.ScopedSpan;
import brave.Span;
import brave.Tracer;
import brave.Tracer.SpanInScope;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;
    @Autowired 
    Tracer tracer;

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        log.trace(String.format("Entering %s.%s", this.getClass().getName(), "getAll()"));

        // Start a new trace or a span within an existing trace representing an operation
        ScopedSpan scopedSpan = tracer.startScopedSpan("scopedSpan");
        try {
            Thread.sleep(2000);
        } catch (RuntimeException | Error e) {
            scopedSpan.error(e); // Unless you handle exceptions, you might not know the operation failed!
            throw e;
        } catch (InterruptedException e) {
            scopedSpan.error(e);
            e.printStackTrace();
        } finally {
            scopedSpan.finish(); // always finish the span
        }

        Span span = tracer.nextSpan().name("span").start();
        // Put the span in "scope" so that downstream code such as loggers can see trace IDs
        try (SpanInScope ws = tracer.withSpanInScope(span)) {
            Thread.sleep(2000);
        } catch (RuntimeException | Error e) {
            span.error(e); // Unless you handle exceptions, you might not know the operation failed!
            throw e;
        } catch (InterruptedException e) {
            scopedSpan.error(e);
            e.printStackTrace();
        } finally {
            span.finish(); // note the scope is independent of the span. Always finish a span.
        }

        List<User> users = userService.getAll();
        if(users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") int id) {
        log.trace(String.format("Entering %s.%s", this.getClass().getName(), String.format("getById(%s)", id)));
        
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
