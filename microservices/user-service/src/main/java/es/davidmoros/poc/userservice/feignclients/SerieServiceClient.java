package es.davidmoros.poc.userservice.feignclients;

import es.davidmoros.poc.userservice.model.Serie;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "serie-service")
@RequestMapping("/serie")
public interface SerieServiceClient {

    @PostMapping()
    Serie save(@RequestBody Serie serie);

    @GetMapping("/user/{userId}")
    List<Serie> getSeries(@PathVariable("userId") int userId);
}
