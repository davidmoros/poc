package es.davidmoros.poc.userservice.feignclients;

import es.davidmoros.poc.userservice.model.Film;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "film-service")
@RequestMapping("/film")
public interface FilmServiceClient {

    @PostMapping()
    Film save(@RequestBody Film film);

    @GetMapping("/user/{userId}")
    List<Film> getFilms(@PathVariable("userId") int userId);
}
