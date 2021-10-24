package es.davidmoros.poc.userservice.service;

import es.davidmoros.poc.userservice.entity.User;
import es.davidmoros.poc.userservice.feignclients.FilmServiceClient;
import es.davidmoros.poc.userservice.feignclients.SerieServiceClient;
import es.davidmoros.poc.userservice.model.Film;
import es.davidmoros.poc.userservice.model.Serie;
import es.davidmoros.poc.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SerieServiceClient serieServiceClient;

    @Autowired
    FilmServiceClient filmServiceClient;

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<Serie> getSeries(int userId) {
        return serieServiceClient.getSeries(userId);
    }

    public List<Film> getFilms(int userId) {
        return filmServiceClient.getFilms(userId);
    }

    public Serie saveSerie(int userId, Serie serie) {
        serie.setUserId(userId);
        return serieServiceClient.save(serie);
    }

    public Film saveFilm(int userId, Film film) {
        film.setUserId(userId);
        return filmServiceClient.save(film);
    }

    public Map<String, Object> getAllWatching(int userId) {
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            result.put("Mensaje", "no existe el usuario");
            return result;
        }
        result.put("User", user);
        List<Serie> series = serieServiceClient.getSeries(userId);
        if(series.isEmpty()) {
            result.put("Series", "este usuario no ve series");
        } else {
            result.put("Series", series);
        }
        List<Film> films = filmServiceClient.getFilms(userId);
        if(films.isEmpty()) {
            result.put("Films", "este usuario ve películas");
        } else {
            result.put("Films", films);
        }
        return result;
    }
}
