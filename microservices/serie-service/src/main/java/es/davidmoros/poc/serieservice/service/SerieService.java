package es.davidmoros.poc.serieservice.service;

import es.davidmoros.poc.serieservice.entity.Serie;
import es.davidmoros.poc.serieservice.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

    @Autowired
    SerieRepository serieRepository;

    public List<Serie> getAll() {
        return serieRepository.findAll();
    }

    public Serie getSerieById(int id) {
        return serieRepository.findById(id).orElse(null);
    }

    public Serie save(Serie serie) {
        return serieRepository.save(serie);
    }

    public List<Serie> byUserId(int userId) {
        return serieRepository.findByUserId(userId);
    }
}
