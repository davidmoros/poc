package es.davidmoros.poc.serieservice.controller;

import es.davidmoros.poc.serieservice.entity.Serie;
import es.davidmoros.poc.serieservice.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/serie")
public class SerieController {

    @Autowired
    SerieService serieService;

    @GetMapping
    public ResponseEntity<List<Serie>> getAll() {
        List<Serie> series = serieService.getAll();
        if(series.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(series);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serie> getById(@PathVariable("id") int id) {
        Serie serie = serieService.getSerieById(id);
        if(serie == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(serie);
    }

    @PostMapping()
    public ResponseEntity<Serie> save(@RequestBody Serie serie) {
        Serie serieNew = serieService.save(serie);
        return ResponseEntity.ok(serieNew);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Serie>> getByUserId(@PathVariable("userId") int userId) {
        List<Serie> series = serieService.byUserId(userId);
        return ResponseEntity.ok(series);
    }

}
