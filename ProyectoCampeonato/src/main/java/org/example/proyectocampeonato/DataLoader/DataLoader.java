package org.example.proyectocampeonato.DataLoader;

import lombok.RequiredArgsConstructor;
import org.example.proyectocampeonato.modelo.*;
import org.example.proyectocampeonato.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ArbitroRepository arbitroRepository;
    private final CampeonatoRepository campeonatoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ClasficacionRepository clasficacionRepository;
    private final CombateRepository combateRepository;
    private final CompetidorRepository competidorRepository;


    @Override
    public void run(String... args) throws Exception {
        if (campeonatoRepository.count() == 0) {
            cargarDatos();
        }
    }

    private void cargarDatos() {
        System.out.println("Iniciando carga de datos del Campeonato de Karate...");

        // 1. CREAR CAMPEONATOS
        Campeonato camp1 = Campeonato.builder()
                .nombre("Campeonato de España Cadete/Junior/Sub-21 2026")
                .fechaInicio(LocalDate.of(2026, 11, 10))
                .fechaFin(LocalDate.of(2026, 11, 12))
                .ubicacion("Pabellón Multiusos, Madrid")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Torneo clave para la posible clasificación al próximo campeonato de Europa")
                .build();
        campeonatoRepository.save(camp1);

        Campeonato camp2 = Campeonato.builder()
                .nombre("Campeonato de España Senior 2026")
                .fechaInicio(LocalDate.of(2026, 2, 2))
                .fechaFin(LocalDate.of(2026, 2, 4))
                .ubicacion("Pabellón de Carranque, Málaga, Andalucía, España")
                .estado("pasado")
                .nivel("Nacional")
                .descripcion("Torneo clasificatorio para el mundial")
                .build();
        campeonatoRepository.save(camp2);

        Campeonato camp3 = Campeonato.builder()
                .nombre("Campeonato de Andalucía alevin/infantil/juvenil 2026")
                .fechaInicio(LocalDate.of(2026, 3, 1))
                .fechaFin(LocalDate.of(2026, 3, 1))
                .ubicacion("Pabellón de Deportes de Córdoba, Córdoba, Andalucía, España")
                .estado("futuro")
                .nivel("Provincial")
                .descripcion("Torneo clasificatorio para el campeonato de España")
                .build();
        campeonatoRepository.save(camp3);

        Campeonato camp4 = Campeonato.builder()
                .nombre("Campeonato de Andalucía Senior 2026")
                .fechaInicio(LocalDate.of(2026, 2, 30))
                .fechaFin(LocalDate.of(2026, 2, 30))
                .ubicacion("Pabellón de Ciudad Jardín, Málaga, Andalucía")
                .estado("futuro")
                .nivel("Provincial")
                .descripcion("Torneo clasificatorio para el campeonato de España")
                .build();
        campeonatoRepository.save(camp4);

        Campeonato camp5 = Campeonato.builder()
                .nombre("Campeonato de España Universitario 2026")
                .fechaInicio(LocalDate.of(2026, 5, 27))
                .fechaFin(LocalDate.of(2026, 5, 30))
                .ubicacion("Pabellón de Ciudad Jardín, Málaga, Andalucía")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Torneo clasificatorio para el Campeonato Europeo y con recompensa de créditos")
                .build();
        campeonatoRepository.save(camp5);

        Campeonato camp6 = Campeonato.builder()
                .nombre("Liga Nacional Cadete/Junior/Sub-21 J4 2026")
                .fechaInicio(LocalDate.of(2026, 3, 7))
                .fechaFin(LocalDate.of(2026, 3, 9))
                .ubicacion("Pabellón Municipal Puerta de Santa María, 13005 Ciudad Real")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Torneo clasificatorio para el Campeonato Europeo y con recompensa de créditos")
                .build();
        campeonatoRepository.save(camp6);

        // 7. Campeonato de España Master
        Campeonato camp7 = Campeonato.builder()
                .nombre("VII Campeonato de España Master 2026")
                .fechaInicio(LocalDate.of(2026, 4, 10))
                .fechaFin(LocalDate.of(2026, 4, 12))
                .ubicacion("La Nucía, Alicante, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Campeonato nacional categoría master")
                .build();
        campeonatoRepository.save(camp7);


// 8. Campeonato de España Universitario (REAL según calendario)
        Campeonato camp8 = Campeonato.builder()
                .nombre("Campeonato de España Universitario 2026")
                .fechaInicio(LocalDate.of(2026, 4, 17))
                .fechaFin(LocalDate.of(2026, 4, 18))
                .ubicacion("Granada, Andalucía, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Campeonato universitario nacional de karate")
                .build();
        campeonatoRepository.save(camp8);


// 9. Campeonato de España Infantil
        Campeonato camp9 = Campeonato.builder()
                .nombre("Campeonato de España Infantil 2026")
                .fechaInicio(LocalDate.of(2026, 5, 8))
                .fechaFin(LocalDate.of(2026, 5, 10))
                .ubicacion("Oviedo, Asturias, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Campeonato nacional categorías infantil")
                .build();
        campeonatoRepository.save(camp9);


// 10. Campeonato de España de Clubes
        Campeonato camp10 = Campeonato.builder()
                .nombre("Campeonato de España de Clubes 2026")
                .fechaInicio(LocalDate.of(2026, 6, 19))
                .fechaFin(LocalDate.of(2026, 6, 21))
                .ubicacion("Tarragona, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Competición nacional por clubes")
                .build();
        campeonatoRepository.save(camp10);


// 11. Campeonato de España Absoluto
        Campeonato camp11 = Campeonato.builder()
                .nombre("Campeonato de España Absoluto 2026")
                .fechaInicio(LocalDate.of(2026, 3, 27))
                .fechaFin(LocalDate.of(2026, 3, 29))
                .ubicacion("Pontevedra, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Campeonato nacional absoluto")
                .build();
        campeonatoRepository.save(camp11);


// 12. Liga Nacional Equipos (Ronda)
        Campeonato camp12 = Campeonato.builder()
                .nombre("Liga Nacional Equipos - 1ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 2, 28))
                .fechaFin(LocalDate.of(2026, 2, 28))
                .ubicacion("Torrejón de Ardoz, Madrid, España")
                .estado("pasado")
                .nivel("Nacional")
                .descripcion("Primera ronda liga nacional equipos")
                .build();
        campeonatoRepository.save(camp12);


// 13. Liga Nacional Master
        Campeonato camp13 = Campeonato.builder()
                .nombre("Liga Nacional Master - 1ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 3, 1))
                .fechaFin(LocalDate.of(2026, 3, 1))
                .ubicacion("Torrejón de Ardoz, Madrid, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Primera ronda liga nacional master")
                .build();
        campeonatoRepository.save(camp13);


// 14. Campeonato España Cadete Junior Sub21 (noviembre)
        Campeonato camp14 = Campeonato.builder()
                .nombre("Campeonato de España Cadete Junior Sub-21 2026 (Final)")
                .fechaInicio(LocalDate.of(2026, 11, 27))
                .fechaFin(LocalDate.of(2026, 11, 29))
                .ubicacion("Valencia, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Campeonato nacional categorías cadete junior sub21")
                .build();
        campeonatoRepository.save(camp14);

        // ===============================
// LIGAS NACIONALES RFEK 2026
// ===============================


// 15. Liga Nacional Masculina Absoluta/Cadete - 1ª Ronda
        Campeonato camp15 = Campeonato.builder()
                .nombre("Liga Nacional Masculina Absoluta/Cadete - 1ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 1, 17))
                .fechaFin(LocalDate.of(2026, 1, 17))
                .ubicacion("Valdepeñas, Ciudad Real, España")
                .estado("pasado")
                .nivel("Nacional")
                .descripcion("Primera ronda Liga Nacional masculina absoluta y cadete")
                .build();
        campeonatoRepository.save(camp15);


// 16. Liga Iberdrola Absoluta/Cadete - 1ª Ronda
        Campeonato camp16 = Campeonato.builder()
                .nombre("Liga Iberdrola Absoluta/Cadete - 1ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 1, 18))
                .fechaFin(LocalDate.of(2026, 1, 18))
                .ubicacion("Valdepeñas, Ciudad Real, España")
                .estado("pasado")
                .nivel("Nacional")
                .descripcion("Primera ronda Liga Iberdrola femenina absoluta y cadete")
                .build();
        campeonatoRepository.save(camp16);


// 17. Liga Nacional Masculina Alevín/Infantil/Juvenil - 1ª Ronda
        Campeonato camp17 = Campeonato.builder()
                .nombre("Liga Nacional Masculina Alevín/Infantil/Juvenil - 1ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 2, 21))
                .fechaFin(LocalDate.of(2026, 2, 21))
                .ubicacion("Logroño, España")
                .estado("pasado")
                .nivel("Nacional")
                .descripcion("Primera ronda Liga Nacional categorías base")
                .build();
        campeonatoRepository.save(camp17);


// 18. Liga Iberdrola Alevín/Infantil/Juvenil - 1ª Ronda
        Campeonato camp18 = Campeonato.builder()
                .nombre("Liga Iberdrola Alevín/Infantil/Juvenil - 1ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 2, 22))
                .fechaFin(LocalDate.of(2026, 2, 22))
                .ubicacion("Logroño, España")
                .estado("pasado")
                .nivel("Nacional")
                .descripcion("Primera ronda Liga Iberdrola categorías base")
                .build();
        campeonatoRepository.save(camp18);


// 19. Liga Nacional Equipos - 1ª Ronda
        Campeonato camp19 = Campeonato.builder()
                .nombre("Liga Nacional Equipos - 1ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 2, 28))
                .fechaFin(LocalDate.of(2026, 2, 28))
                .ubicacion("Torrejón de Ardoz, Madrid, España")
                .estado("pasado")
                .nivel("Nacional")
                .descripcion("Primera ronda Liga Nacional por equipos")
                .build();
        campeonatoRepository.save(camp19);


// 20. Liga Nacional Master - 1ª Ronda
        Campeonato camp20 = Campeonato.builder()
                .nombre("Liga Nacional Master - 1ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 3, 1))
                .fechaFin(LocalDate.of(2026, 3, 1))
                .ubicacion("Torrejón de Ardoz, Madrid, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Primera ronda Liga Nacional master")
                .build();
        campeonatoRepository.save(camp20);


// 21. Liga Nacional Junior/Sub21 - 1ª Ronda
        Campeonato camp21 = Campeonato.builder()
                .nombre("Liga Nacional Junior/Sub21 - 1ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 3, 7))
                .fechaFin(LocalDate.of(2026, 3, 7))
                .ubicacion("Ciudad Real, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Primera ronda Liga Nacional junior y sub21")
                .build();
        campeonatoRepository.save(camp21);


// 22. Liga Iberdrola Junior/Sub21 - 1ª Ronda
        Campeonato camp22 = Campeonato.builder()
                .nombre("Liga Iberdrola Junior/Sub21 - 1ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 3, 8))
                .fechaFin(LocalDate.of(2026, 3, 8))
                .ubicacion("Ciudad Real, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Primera ronda Liga Iberdrola junior y sub21")
                .build();
        campeonatoRepository.save(camp22);


// 23. Liga Nacional Masculina Absoluta/Cadete - 2ª Ronda
        Campeonato camp23 = Campeonato.builder()
                .nombre("Liga Nacional Masculina Absoluta/Cadete - 2ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 10, 24))
                .fechaFin(LocalDate.of(2026, 10, 24))
                .ubicacion("Elche, Alicante, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Segunda ronda Liga Nacional masculina absoluta y cadete")
                .build();
        campeonatoRepository.save(camp23);


// 24. Liga Iberdrola Absoluta/Cadete - 2ª Ronda
        Campeonato camp24 = Campeonato.builder()
                .nombre("Liga Iberdrola Absoluta/Cadete - 2ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 10, 25))
                .fechaFin(LocalDate.of(2026, 10, 25))
                .ubicacion("Elche, Alicante, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Segunda ronda Liga Iberdrola femenina absoluta y cadete")
                .build();
        campeonatoRepository.save(camp24);


// 25. Liga Nacional Equipos - 2ª Ronda
        Campeonato camp25 = Campeonato.builder()
                .nombre("Liga Nacional Equipos - 2ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 10, 31))
                .fechaFin(LocalDate.of(2026, 10, 31))
                .ubicacion("Córdoba, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Segunda ronda Liga Nacional por equipos")
                .build();
        campeonatoRepository.save(camp25);


// 26. Liga Nacional Master - 2ª Ronda
        Campeonato camp26 = Campeonato.builder()
                .nombre("Liga Nacional Master - 2ª Ronda 2026")
                .fechaInicio(LocalDate.of(2026, 11, 1))
                .fechaFin(LocalDate.of(2026, 11, 1))
                .ubicacion("Córdoba, España")
                .estado("futuro")
                .nivel("Nacional")
                .descripcion("Segunda ronda Liga Nacional master")
                .build();
        campeonatoRepository.save(camp26);


        // 2. CREAR CATEGORÍAS (Relacionadas con el Campeonato)
        // BENJAMIN
        CategoriaId catId1 = new CategoriaId();
        catId1.setIdCampeonato(camp1.getId_campeonato());
        catId1.setModalidad("kata");
        catId1.setGenero("M");
        catId1.setEdadMaxima(7);
        catId1.setPesoMaximo(0.0);

        Categoria cat1 = new Categoria();
        cat1.setId(catId1);
        cat1.setNombre("Benjamín Masculino");
        cat1.setEdadMinima(0);
        cat1.setCampeonato(camp1);
        categoriaRepository.save(cat1);

        CategoriaId catId2 = new CategoriaId();
        catId2.setIdCampeonato(camp2.getId_campeonato());
        catId2.setModalidad("kata");
        catId2.setGenero("F");
        catId2.setEdadMaxima(7);
        catId2.setPesoMaximo(0.0);

        Categoria cat2 = new Categoria();
        cat2.setId(catId2);
        cat2.setNombre("Benjamín Femenino");
        cat2.setEdadMinima(0);
        cat2.setCampeonato(camp2);
        categoriaRepository.save(cat2);

        //ALEVIN MASC
        CategoriaId catId9 = new CategoriaId();
        catId9.setIdCampeonato(camp9.getId_campeonato());
        catId9.setModalidad("kata");
        catId9.setGenero("M");
        catId9.setEdadMaxima(9);

        Categoria cat9 = new Categoria();
        cat9.setId(catId9);
        cat9.setNombre("Alevin Mascuino");
        cat9.setEdadMinima(8);
        cat9.setCampeonato(camp9);
        categoriaRepository.save(cat9);

        CategoriaId catId3 = new CategoriaId();
        catId3.setIdCampeonato(camp3.getId_campeonato());
        catId3.setModalidad("kumite");
        catId3.setGenero("M");
        catId3.setEdadMaxima(9);
        catId3.setPesoMaximo(28.000);

        Categoria cat3 = new Categoria();
        cat3.setId(catId3);
        cat3.setNombre("Alevin Masculino Mascuino <28kg");
        cat3.setEdadMinima(8);
        cat3.setCampeonato(camp3);
        categoriaRepository.save(cat3);

        CategoriaId catId4 = new CategoriaId();
        catId4.setIdCampeonato(camp4.getId_campeonato());
        catId4.setModalidad("kumite");
        catId4.setGenero("M");
        catId4.setEdadMaxima(9);
        catId4.setPesoMaximo(34.000);

        Categoria cat4 = new Categoria();
        cat4.setId(catId4);
        cat4.setNombre("Alevin Masculino <34kg");
        cat4.setEdadMinima(8);
        cat4.setCampeonato(camp4);
        categoriaRepository.save(cat4);

        CategoriaId catId5 = new CategoriaId();
        catId5.setIdCampeonato(camp5.getId_campeonato());
        catId5.setModalidad("kumite");
        catId5.setGenero("M");
        catId5.setEdadMaxima(9);
        catId5.setPesoMinimo(34.000);

        Categoria cat5 = new Categoria();
        cat5.setId(catId5);
        cat5.setNombre("Alevin Masculino >34kg");
        cat5.setEdadMinima(8);
        cat5.setCampeonato(camp5);
        categoriaRepository.save(cat5);

        //ALEVIN FEMENINO
        CategoriaId catId10 = new CategoriaId();
        catId10.setIdCampeonato(camp10.getId_campeonato());
        catId10.setModalidad("kata");
        catId10.setGenero("F");
        catId10.setEdadMaxima(9);

        Categoria cat10 = new Categoria();
        cat10.setId(catId10);
        cat10.setNombre("Alevín Femenino");
        cat10.setEdadMinima(8);
        cat10.setCampeonato(camp10);
        categoriaRepository.save(cat10);

        CategoriaId catId6 = new CategoriaId();
        catId5.setIdCampeonato(camp6.getId_campeonato());
        catId5.setModalidad("kumite");
        catId5.setGenero("F");
        catId5.setEdadMaxima(9);
        catId5.setPesoMaximo(26.000);

        Categoria cat6 = new Categoria();
        cat6.setId(catId6);
        cat6.setNombre("Alevin Femenino <26kg");
        cat6.setEdadMinima(8);
        cat6.setCampeonato(camp6);
        categoriaRepository.save(cat6);

        CategoriaId catId7 = new CategoriaId();
        catId7.setIdCampeonato(camp7.getId_campeonato());
        catId7.setModalidad("kumite");
        catId7.setGenero("F");
        catId7.setEdadMaxima(9);
        catId7.setPesoMaximo(32.0);

        Categoria cat7 = new Categoria();
        cat7.setId(catId7);
        cat7.setNombre("Alevín Femenino <32kg");
        cat7.setEdadMinima(8);
        cat7.setCampeonato(camp7);
        categoriaRepository.save(cat7);

        CategoriaId catId8 = new CategoriaId();
        catId8.setIdCampeonato(camp8.getId_campeonato());
        catId8.setModalidad("kumite");
        catId8.setGenero("F");
        catId8.setEdadMaxima(9);
        catId8.setPesoMinimo(32.0);

        Categoria cat8 = new Categoria();
        cat8.setId(catId8);
        cat8.setNombre("Alevín Femenino >32kg");
        cat8.setEdadMinima(8);
        cat8.setCampeonato(camp8);
        categoriaRepository.save(cat8);

        //INFANTIL MASC
        CategoriaId catId11 = new CategoriaId();
        catId11.setIdCampeonato(camp11.getId_campeonato());
        catId11.setModalidad("kata");
        catId11.setGenero("M");
        catId11.setEdadMaxima(11);
        catId11.setPesoMinimo(null);
        catId11.setPesoMaximo(null);

        Categoria catInfKataM = new Categoria();
        catInfKataM.setId(catId11);
        catInfKataM.setNombre("Kata Infantil Masculino");
        catInfKataM.setEdadMinima(10);
        catInfKataM.setCampeonato(camp11);
        categoriaRepository.save(catInfKataM);

        CategoriaId catId12 = new CategoriaId();
        catId12.setIdCampeonato(camp12.getId_campeonato());
        catId12.setModalidad("kumite");
        catId12.setGenero("M");
        catId12.setEdadMaxima(11);
        catId12.setPesoMaximo(30.0);

        Categoria cat12 = new Categoria();
        cat12.setId(catId12);
        cat12.setNombre("Infantil Masculino <30kg");
        cat12.setEdadMinima(10);
        cat12.setCampeonato(camp12);
        categoriaRepository.save(cat12);

        CategoriaId catId13 = new CategoriaId();
        catId13.setIdCampeonato(camp13.getId_campeonato());
        catId13.setModalidad("kumite");
        catId13.setGenero("M");
        catId13.setEdadMaxima(11);
        catId13.setPesoMinimo(30.0);
        catId13.setPesoMaximo(35.0);

        Categoria cat13 = new Categoria();
        cat13.setId(catId13);
        cat13.setNombre("Infantil Masculino <35kg");
        cat13.setEdadMinima(10);
        cat13.setCampeonato(camp13);
        categoriaRepository.save(cat13);

        CategoriaId catId14 = new CategoriaId();
        catId14.setIdCampeonato(camp14.getId_campeonato());
        catId14.setModalidad("kumite");
        catId14.setGenero("M");
        catId14.setEdadMaxima(11);
        catId14.setPesoMinimo(35.0);
        catId14.setPesoMaximo(40.0);

        Categoria cat14 = new Categoria();
        cat14.setId(catId14);
        cat14.setNombre("Infantil Masculino <40kg");
        cat14.setEdadMinima(10);
        cat14.setCampeonato(camp14);
        categoriaRepository.save(cat14);

        CategoriaId catId15 = new CategoriaId();
        catId15.setIdCampeonato(camp15.getId_campeonato());
        catId15.setModalidad("kumite");
        catId15.setGenero("M");
        catId15.setEdadMaxima(11);
        catId15.setPesoMinimo(40.0);
        catId15.setPesoMaximo(45.0);

        Categoria cat15 = new Categoria();
        cat15.setId(catId15);
        cat15.setNombre("Infantil Masculino <45kg");
        cat15.setEdadMinima(10);
        cat15.setCampeonato(camp15);
        categoriaRepository.save(cat15);

        CategoriaId catId16 = new CategoriaId();
        catId16.setIdCampeonato(camp16.getId_campeonato());
        catId16.setModalidad("kumite");
        catId16.setGenero("M");
        catId16.setEdadMaxima(11);
        catId16.setPesoMinimo(45.0);

        Categoria cat16 = new Categoria();
        cat16.setId(catId16);
        cat16.setNombre("Infantil Masculino >45kg");
        cat16.setEdadMinima(10);
        cat16.setCampeonato(camp16);
        categoriaRepository.save(cat16);

        // Kata Infantil Femenino
        CategoriaId catId17 = new CategoriaId();
        catId17.setIdCampeonato(camp17.getId_campeonato());
        catId17.setModalidad("kata");
        catId17.setGenero("F");
        catId17.setEdadMaxima(11);

        Categoria cat17 = new Categoria();
        cat17.setId(catId17);
        cat17.setNombre("Infantil Femenino");
        cat17.setEdadMinima(10);
        cat17.setCampeonato(camp17);
        categoriaRepository.save(cat17);

        CategoriaId catId18 = new CategoriaId();
        catId18.setIdCampeonato(camp18.getId_campeonato());
        catId18.setModalidad("kumite");
        catId18.setGenero("F");
        catId18.setEdadMaxima(11);
        catId18.setPesoMaximo(30.0);

        Categoria cat18 = new Categoria();
        cat18.setId(catId18);
        cat18.setNombre("Infantil Femenino <30kg");
        cat18.setEdadMinima(10);
        cat18.setCampeonato(camp18);
        categoriaRepository.save(cat18);

        CategoriaId catId19 = new CategoriaId();
        catId19.setIdCampeonato(camp19.getId_campeonato());
        catId19.setModalidad("kumite");
        catId19.setGenero("F");
        catId19.setEdadMaxima(11);
        catId19.setPesoMinimo(30.0);
        catId19.setPesoMaximo(36.0);

        Categoria cat19 = new Categoria();
        cat19.setId(catId19);
        cat19.setNombre("Infantil Femenino <36kg");
        cat19.setEdadMinima(10);
        cat19.setCampeonato(camp19);
        categoriaRepository.save(cat19);

        CategoriaId catId20 = new CategoriaId();
        catId20.setIdCampeonato(camp20.getId_campeonato());
        catId20.setModalidad("kumite");
        catId20.setGenero("F");
        catId20.setEdadMaxima(11);
        catId20.setPesoMinimo(36.0);
        catId20.setPesoMaximo(42.0);

        Categoria cat20 = new Categoria();
        cat20.setId(catId20);
        cat20.setNombre("Infantil Femenino <42kg");
        cat20.setEdadMinima(10);
        cat20.setCampeonato(camp20);
        categoriaRepository.save(cat20);

        CategoriaId catId21 = new CategoriaId();
        catId21.setIdCampeonato(camp21.getId_campeonato());
        catId21.setModalidad("kumite");
        catId21.setGenero("F");
        catId21.setEdadMaxima(11);
        catId21.setPesoMinimo(42.0);

        Categoria cat21 = new Categoria();
        cat21.setId(catId21);
        cat21.setNombre("Infantil Femenino >42kg");
        cat21.setEdadMinima(10);
        cat21.setCampeonato(camp21);
        categoriaRepository.save(cat21);

        //JUVENIL MASC
        CategoriaId catId22 = new CategoriaId();
        catId22.setIdCampeonato(camp22.getId_campeonato());
        catId22.setModalidad("kata");
        catId22.setGenero("M");
        catId22.setEdadMaxima(13);

        Categoria cat22 = new Categoria();
        cat22.setId(catId22);
        cat22.setNombre("Juvenil Masculino");
        cat22.setEdadMinima(12);
        cat22.setCampeonato(camp22);
        categoriaRepository.save(cat22);

        CategoriaId catId23 = new CategoriaId();
        catId23.setIdCampeonato(camp23.getId_campeonato());
        catId23.setModalidad("kumite");
        catId23.setGenero("M");
        catId23.setEdadMaxima(13);
        catId23.setPesoMaximo(36.0);

        Categoria cat23 = new Categoria();
        cat23.setId(catId23);
        cat23.setNombre("Juvenil Masculino <36kg");
        cat23.setEdadMinima(12);
        cat23.setCampeonato(camp23);
        categoriaRepository.save(cat23);

        CategoriaId catId24 = new CategoriaId();
        catId24.setIdCampeonato(camp24.getId_campeonato());
        catId24.setModalidad("kumite");
        catId24.setGenero("M");
        catId24.setEdadMaxima(13);
        catId24.setPesoMinimo(36.0);
        catId24.setPesoMaximo(42.0);

        Categoria cat24 = new Categoria();
        cat24.setId(catId24);
        cat24.setNombre("Juvenil Masculino <42kg");
        cat24.setEdadMinima(12);
        cat24.setCampeonato(camp24);
        categoriaRepository.save(cat24);

        CategoriaId catId25 = new CategoriaId();
        catId25.setIdCampeonato(camp25.getId_campeonato());
        catId25.setModalidad("kumite");
        catId25.setGenero("M");
        catId25.setEdadMaxima(13);
        catId25.setPesoMinimo(42.0);
        catId25.setPesoMaximo(48.0);

        Categoria cat25 = new Categoria();
        cat25.setId(catId25);
        cat25.setNombre("Juvenil Masculino <48kg");
        cat25.setEdadMinima(12);
        cat25.setCampeonato(camp25);
        categoriaRepository.save(cat25);

        CategoriaId catId26 = new CategoriaId();
        catId26.setIdCampeonato(camp26.getId_campeonato());
        catId26.setModalidad("kumite");
        catId26.setGenero("M");
        catId26.setEdadMaxima(13);
        catId26.setPesoMinimo(48.0);
        catId26.setPesoMaximo(54.0);

        Categoria cat26 = new Categoria();
        cat26.setId(catId26);
        cat26.setNombre("Juvenil Masculino <54kg");
        cat26.setEdadMinima(12);
        cat26.setCampeonato(camp26);
        categoriaRepository.save(cat26);

        CategoriaId catId27 = new CategoriaId();
        catId27.setIdCampeonato(camp27.getId_campeonato());
        catId27.setModalidad("kumite");
        catId27.setGenero("M");
        catId27.setEdadMaxima(13);
        catId27.setPesoMinimo(54.0);
        catId27.setPesoMaximo(60.0);

        Categoria cat27 = new Categoria();
        cat27.setId(catId27);
        cat27.setNombre("Juvenil Masculino <60kg");
        cat27.setEdadMinima(12);
        cat27.setCampeonato(camp27);
        categoriaRepository.save(cat27);

        CategoriaId catId28 = new CategoriaId();
        catId28.setIdCampeonato(camp28.getId_campeonato());
        catId28.setModalidad("kumite");
        catId28.setGenero("M");
        catId28.setEdadMaxima(13);
        catId28.setPesoMinimo(60.0);

        Categoria cat28 = new Categoria();
        cat28.setId(catId28);
        cat28.setNombre("Juvenil Masculino >60kg");
        cat28.setEdadMinima(12);
        cat28.setCampeonato(camp28);
        categoriaRepository.save(cat28);




        // 3. CREAR COMPETIDORES
        Competidor comp1 = Competidor.builder()
                .nombre("Juan")
                .apellidos("Pérez Cano")
                .dni("12345678A")
                .fechaNacimiento(LocalDate.of(2000, 5, 20))
                .genero('M')
                .club("Dojo Shoto")
                .federacionAutonomica("Madrileña")
                .build();

        Competidor comp2 = Competidor.builder()
                .nombre("Alberto")
                .apellidos("Ruiz Galiano")
                .dni("87654321B")
                .fechaNacimiento(LocalDate.of(1998, 11, 2))
                .genero('M')
                .club("Karate Almería")
                .federacionAutonomica("Andaluza")
                .build();

        competidorRepository.saveAll(List.of(comp1, comp2));

        // 4. CREAR ÁRBITROS
        Arbitro arb1 = Arbitro.builder()
                .nombre("Luis")
                .apellidos("Gómez Juez")
                .licencia("LIC-2026-001")
                .fecha_nacimiento(new Date())
                .categoria_Arbitral("Nacional")
                .activo(true)
                .build();
        arbitroRepository.save(arb1);

        // 5. CREAR COMBATES
        // Relacionamos los competidores guardados
        CombateId combateId1 = new CombateId(
                comp1.getId_competidor(),
                comp2.getId_competidor(),
                1
        );

// 2. Construir el objeto Combate usando ese ID
        Combate combate1 = Combate.builder()
                .id(combateId1)
                .ronda("Final")
                .competidorRojo(comp1) // <--- ESTO ES LO QUE ESTÁ FALLANDO (asegúrate que comp1 no sea null)
                .competidorAzul(comp2) // <--- ESTO ES LO QUE ESTÁ FALLANDO (asegúrate que comp2 no sea null)
                .puntuacionRojo(3)
                .puntuacionAzul(1)
                .senshu("rojo")
                .estado("finalizado")
                .horaProgramada(LocalTime.of(10, 30))
                .horaInicioReal(LocalDateTime.now())
                .duracionSegundos(180)
                .build();
        combateRepository.save(combate1);

        System.out.println("Carga de datos de Karate finalizada con éxito.");
    }
}
