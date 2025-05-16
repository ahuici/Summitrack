package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.DAO.IMonteDAO;
import com.example.masanz.aimar.actividades.model.entity.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MonteService {

    private final Integer saltoHora = 6; //Numero de horas de diferencia entre cada actualizacion del tiempo meteorologico

    @Autowired
    private IMonteDAO monteDAO;

    @Autowired
    private PersonaService personaService;

    public List<Monte> getAll(){
        return monteDAO.findAll();
    }

    public Monte findByID(Integer id){
        return monteDAO.findById(id).orElse(null);
    }

    public String save(Monte monte){
        if (monte == null) return "Ha habido un error al guardar el monte";
        else if (monte.getNombre().length() < 3) return "El nombre del monte debe tener al menos 3 caracteres";
        else if (monte.getNombre().matches("\\d+")) return "El nombre debe estar compuesto por letras";
        else if (monte.getAltura() < 250) return "La altura del monte debe de ser de al menos 250m";
        else if (monte.getUbicacion().length() < 5) return "La ubicación del monte debe tener al menos 5 caracteres";
        else if (monte.getUbicacion().matches("\\d+")) return "La ubicación debe estar compuesto por letras";
        else if (monte.getLatitud() > 90 || monte.getLatitud() < -90) return "La latitud debe ser mayor de -90º y menor de 90º";
        else if (monte.getLongitud() > 180 || monte.getLongitud() < -180) return "La longitud debe ser mayor de -180º y menor de 180º";

        monteDAO.save(monte);
        return null;
    }

    public boolean existe(Monte monte){
        return monteDAO.existsById(monte.getId());
    }

    public void delete(Monte monte){
        monteDAO.delete(monte);
    }

    /*
    * Elimina el monte dado de la lista de favoritos
     */
    public void deleteFavorito(Integer id){
        Monte monte = findByID(id);
        monte.setFavorito(Boolean.FALSE);
        save(monte);
    }

    /*
    * Obtener todos los montes favoritos
     */
    public List<Monte> getFavoritos(){
        List<Monte> favoritos = new ArrayList<>();
        for (Monte monte : getAll()){
            if (monte.isFavorito()) favoritos.add(monte);
        }
        return favoritos;
    }

    /*
    * Obtener el tiempo en un monte haciendo peticiones a una API, se devuelve una lista de TiempoDTO para facilitar su manejo
     */
    public List<TiempoDTO> getTiempoMonte(Monte monte){
        List<TiempoDTO> tiempo = new ArrayList<>();

        Double latitud = monte.getLatitud();
        Double longitud = monte.getLongitud();
        Integer altura = monte.getAltura();

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.open-meteo.com/v1/forecast?latitude=" + latitud +
                            "&longitude=" + longitud + "&elevation=" + altura
                            + "&hourly=temperature_2m" +
                            "&hourly=wind_speed_10m" +
                            "&hourly=precipitation_probability" +
                            "&hourly=snow_depth" +
                            "&hourly=snowfall"))
                    .header("User-Agent", "SummitrackApp/1.0 (aimarhuici@gmail.com)")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Sacar nombre del JSON
            ObjectMapper objetoJSON = new ObjectMapper();
            JsonNode nodoRoot = objetoJSON.readTree( response.body());
            JsonNode horario = nodoRoot.path("hourly");

            List<String> horas = new ArrayList<>();
            List<Double> temperaturas = new ArrayList<>();
            List<Double> viento = new ArrayList<>();
            List<Integer> probLluvia = new ArrayList<>();
            List<Double> nieveAcumulada = new ArrayList<>();
            List<Double> nuevaNieve = new ArrayList<>();

            for (JsonNode node : horario.path("time")) {
                horas.add(node.asText());
            }

            for (JsonNode node : horario.path("temperature_2m")) {
                temperaturas.add(node.asDouble());
            }

            for (JsonNode node : horario.path("wind_speed_10m")) {
                viento.add(node.asDouble());
            }

            for (JsonNode node : horario.path("precipitation_probability")) {
                probLluvia.add(node.asInt());
            }

            for (JsonNode node : horario.path("snowfall")) {
                nuevaNieve.add(node.asDouble());
            }

            for (JsonNode node : horario.path("snow_depth")) {
                nieveAcumulada.add(node.asDouble());
            }

            for (int i = 0; i < horas.size(); i += saltoHora){
                DateTimeFormatter formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                DateTimeFormatter formatoSalida = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                LocalDateTime fechaHora = LocalDateTime.parse(horas.get(i), formatoEntrada);

                tiempo.add(new TiempoDTO(fechaHora.getDayOfMonth(),fechaHora.getMonth().getValue(), fechaHora.getHour(),
                        temperaturas.get(i), viento.get(i), probLluvia.get(i),nieveAcumulada.get(i), nuevaNieve.get(i)));
            }
            tiempo.sort(Comparator.comparing(TiempoDTO::getDia));
            return tiempo;
        } catch (Exception e) {
            System.out.println("ERROR: getTiempoDeMonte (MonteController) --> "); e.printStackTrace();
        }

        return null;
    }

    public List<MapaDTO> pasarMonteAMonteDTO(){
        List<Monte> montesSinDTO = getAll();
        List<MapaDTO> montesConDTO = new ArrayList<>();
        for (Monte monte : montesSinDTO){
            montesConDTO.add(new MapaDTO(monte.getId(), monte.getNombre(), monte.getLatitud(), monte.getLongitud()));
        }

        return montesConDTO;
    }

    /*
    * Hace una peticion a una API para sacar el nombre de las coordenadas
     */
    public String sacarNombrePorCordenadas(Double latitud, Double longitud){
        String nombre = "Sin especificar";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://nominatim.openstreetmap.org/reverse?lat=" + latitud + "&lon=" + longitud + "&format=json"))
                    .header("User-Agent", "SummitrackApp/1.0 (aimarhuici@gmail.com)")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Sacar nombre del JSON
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree( response.body());
            JsonNode addressNode = rootNode.path("name");
            nombre = addressNode.asText();
        } catch (Exception e) {
            System.out.println("ERROR: SacarNombrePorCordenadas (MapaController) --> "); e.printStackTrace();
        }
        return nombre;
    }

    public List<Monte> ordenar(String campo, String orden, List<Monte> montes){
        if (campo != null && orden != null){
            if (campo.equals("nombre") && orden.equals("asc")) montes.sort(Comparator.comparing(Monte::getNombre));
            else if (campo.equals("nombre")) montes.sort(Comparator.comparing(Monte::getNombre).reversed());
            else if (campo.equals("altura") && orden.equals("asc")) montes.sort(Comparator.comparing(Monte::getAltura));
            else montes.sort(Comparator.comparing(Monte::getAltura).reversed());
        }
        return montes;
    }

    public Map<String, List<TiempoDTO>> getTiempoAgrupadoPorDia(Monte monte) {
        List<TiempoDTO> tiempos = getTiempoMonte(monte);
        tiempos.sort(Comparator.comparingInt(TiempoDTO::getMes).thenComparingInt(TiempoDTO::getDia));
        Map<String, List<TiempoDTO>> sinOrdenar = tiempos.stream()
                .collect(Collectors.groupingBy(t -> t.getDia() + "/" + t.getMes(), LinkedHashMap::new, Collectors.toList()));

        return sinOrdenar;
    }

    /*
    * Devuelve una lista con todos los montes ascendidos por la persona dada
     */
    public Map<Monte, Integer> getMontesUnicos(Integer id) {
        Map<Monte, Integer> montes = new HashMap<>();
        Persona persona = personaService.findByID(id);

        for (Monte monte : getAll()){
            for (Ascension ascension : monte.getAsciende()){
                for (Completa completa : ascension.getCompleta()){
                    if (completa.getPersona().equals(persona)){
                        if (montes.keySet().contains(monte)){
                            montes.put(monte, montes.get(monte) + 1);
                        }
                        else montes.put(monte, 1);
                    }
                }
            }
        }

        // Ordenar el mapa por valores (número de ascensiones) ChatGPT
        return montes.entrySet().stream()
                .sorted(Map.Entry.<Monte, Integer>comparingByValue(Comparator.reverseOrder())) // Orden descendente
                .collect(Collectors.toMap(
                        Map.Entry::getKey,                  // Clave
                        Map.Entry::getValue,                // Valor
                        (e1, e2) -> e1,                    // Si hay duplicados, mantener el primero
                        LinkedHashMap::new                 // Usar LinkedHashMap para mantener el orden
                ));
    }
}

