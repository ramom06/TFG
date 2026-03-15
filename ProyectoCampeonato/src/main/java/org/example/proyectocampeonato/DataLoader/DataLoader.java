package org.example.proyectocampeonato.DataLoader;

import lombok.RequiredArgsConstructor;
import org.example.proyectocampeonato.modelo.*;
import org.example.proyectocampeonato.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
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
    private final Campeonato_CategoriaRepository campeonato_categoriaRepository;
    private final InscripcionRepository inscripcionRepository;
    private final PasswordEncoder passwordEncoder;

    // Helper para convertir LocalDate a Date
    private Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public void run(String... args) throws Exception {
        if (campeonatoRepository.count() == 0) {
            cargarDatos();
        }
    }

    private void cargarDatos() {
        System.out.println("Iniciando carga de datos del Campeonato de Karate...");

        // 2. CREAR CATEGORÍAS
        // BENJAMIN
        Categoria catBenjM = Categoria.builder().nombre("Benjamín Masculino").modalidad("kata").genero("M").edadMinima(0).edadMaxima(7).build();
        Categoria catBenjF = Categoria.builder().nombre("Benjamín Femenino").modalidad("kata").genero("F").edadMinima(0).edadMaxima(7).build();
        List<Categoria> benjamin = new ArrayList<>();
        benjamin.add(catBenjM); benjamin.add(catBenjF);
        categoriaRepository.saveAll(benjamin);

        // ALEVIN
        Categoria catAlevKataM = Categoria.builder().nombre("Alevín Masculino Kata").modalidad("kata").genero("M").edadMinima(8).edadMaxima(9).build();
        Categoria catAlevKataF = Categoria.builder().nombre("Alevín Femenino Kata").modalidad("kata").genero("F").edadMinima(8).edadMaxima(9).build();
        Categoria catAlevKumM1 = Categoria.builder().nombre("Alevín Masculino <28kg").modalidad("kumite").genero("M").edadMinima(8).edadMaxima(9).pesoMaximo(28.0).build();
        Categoria catAlevKumM2 = Categoria.builder().nombre("Alevín Masculino <34kg").modalidad("kumite").genero("M").edadMinima(8).edadMaxima(9).pesoMaximo(34.0).build();
        Categoria catAlevKumM3 = Categoria.builder().nombre("Alevín Masculino >34kg").modalidad("kumite").genero("M").edadMinima(8).edadMaxima(9).pesoMinimo(34.0).build();
        Categoria catAlevKumF1 = Categoria.builder().nombre("Alevín Femenino <26kg").modalidad("kumite").genero("F").edadMinima(8).edadMaxima(9).pesoMaximo(26.0).build();
        Categoria catAlevKumF2 = Categoria.builder().nombre("Alevín Femenino <32kg").modalidad("kumite").genero("F").edadMinima(8).edadMaxima(9).pesoMaximo(32.0).build();
        Categoria catAlevKumF3 = Categoria.builder().nombre("Alevín Femenino >32kg").modalidad("kumite").genero("F").edadMinima(8).edadMaxima(9).pesoMinimo(32.0).build();
        List<Categoria> alevines = new ArrayList<>();
        alevines.add(catAlevKataM); alevines.add(catAlevKataF); alevines.add(catAlevKumM1); alevines.add(catAlevKumM2); alevines.add(catAlevKumM3); alevines.add(catAlevKumF1); alevines.add(catAlevKumF2); alevines.add(catAlevKumF3);
        categoriaRepository.saveAll(alevines);

        // INFANTIL
        Categoria catInfKataM = Categoria.builder().nombre("Infantil Masculino Kata").modalidad("kata").genero("M").edadMinima(10).edadMaxima(11).build();
        Categoria catInfKataF = Categoria.builder().nombre("Infantil Femenino Kata").modalidad("kata").genero("F").edadMinima(10).edadMaxima(11).build();
        Categoria catInfKumM1 = Categoria.builder().nombre("Infantil Masculino <30kg").modalidad("kumite").genero("M").edadMinima(10).edadMaxima(11).pesoMaximo(30.0).build();
        Categoria catInfKumM2 = Categoria.builder().nombre("Infantil Masculino <35kg").modalidad("kumite").genero("M").edadMinima(10).edadMaxima(11).pesoMaximo(35.0).build();
        Categoria catInfKumM3 = Categoria.builder().nombre("Infantil Masculino <40kg").modalidad("kumite").genero("M").edadMinima(10).edadMaxima(11).pesoMaximo(40.0).build();
        Categoria catInfKumM4 = Categoria.builder().nombre("Infantil Masculino <45kg").modalidad("kumite").genero("M").edadMinima(10).edadMaxima(11).pesoMaximo(45.0).build();
        Categoria catInfKumM5 = Categoria.builder().nombre("Infantil Masculino >45kg").modalidad("kumite").genero("M").edadMinima(10).edadMaxima(11).pesoMinimo(45.0).build();
        Categoria catInfKumF1 = Categoria.builder().nombre("Infantil Femenino <30kg").modalidad("kumite").genero("F").edadMinima(10).edadMaxima(11).pesoMaximo(30.0).build();
        Categoria catInfKumF2 = Categoria.builder().nombre("Infantil Femenino <36kg").modalidad("kumite").genero("F").edadMinima(10).edadMaxima(11).pesoMaximo(36.0).build();
        Categoria catInfKumF3 = Categoria.builder().nombre("Infantil Femenino <42kg").modalidad("kumite").genero("F").edadMinima(10).edadMaxima(11).pesoMaximo(42.0).build();
        Categoria catInfKumF4 = Categoria.builder().nombre("Infantil Femenino >42kg").modalidad("kumite").genero("F").edadMinima(10).edadMaxima(11).pesoMinimo(42.0).build();
        List<Categoria> infantiles = new ArrayList<>();
        infantiles.add(catInfKataM); infantiles.add(catInfKataF); infantiles.add(catInfKumF1); infantiles.add(catInfKumF2); infantiles.add(catInfKumF3); infantiles.add(catInfKumF4); infantiles.add(catInfKumM1); infantiles.add(catInfKumM2); infantiles.add(catInfKumM3); infantiles.add(catInfKumM4); infantiles.add(catInfKumM5);
        categoriaRepository.saveAll(infantiles);

        // JUVENIL
        Categoria catJuvKataM = Categoria.builder().nombre("Juvenil Masculino Kata").modalidad("kata").genero("M").edadMinima(12).edadMaxima(13).build();
        Categoria catJuvKataF = Categoria.builder().nombre("Juvenil Femenino Kata").modalidad("kata").genero("F").edadMinima(12).edadMaxima(13).build();
        Categoria catJuvKumM1 = Categoria.builder().nombre("Juvenil Masculino <36kg").modalidad("kumite").genero("M").edadMinima(12).edadMaxima(13).pesoMaximo(36.0).build();
        Categoria catJuvKumM2 = Categoria.builder().nombre("Juvenil Masculino <42kg").modalidad("kumite").genero("M").edadMinima(12).edadMaxima(13).pesoMaximo(42.0).build();
        Categoria catJuvKumM3 = Categoria.builder().nombre("Juvenil Masculino <48kg").modalidad("kumite").genero("M").edadMinima(12).edadMaxima(13).pesoMaximo(48.0).build();
        Categoria catJuvKumM4 = Categoria.builder().nombre("Juvenil Masculino <54kg").modalidad("kumite").genero("M").edadMinima(12).edadMaxima(13).pesoMaximo(54.0).build();
        Categoria catJuvKumM5 = Categoria.builder().nombre("Juvenil Masculino <60kg").modalidad("kumite").genero("M").edadMinima(12).edadMaxima(13).pesoMaximo(60.0).build();
        Categoria catJuvKumM6 = Categoria.builder().nombre("Juvenil Masculino >60kg").modalidad("kumite").genero("M").edadMinima(12).edadMaxima(13).pesoMinimo(60.0).build();
        Categoria catJuvKumF1 = Categoria.builder().nombre("Juvenil Femenino <37kg").modalidad("kumite").genero("F").edadMinima(12).edadMaxima(13).pesoMaximo(37.0).build();
        Categoria catJuvKumF2 = Categoria.builder().nombre("Juvenil Femenino <42kg").modalidad("kumite").genero("F").edadMinima(12).edadMaxima(13).pesoMaximo(42.0).build();
        Categoria catJuvKumF3 = Categoria.builder().nombre("Juvenil Femenino <47kg").modalidad("kumite").genero("F").edadMinima(12).edadMaxima(13).pesoMaximo(47.0).build();
        Categoria catJuvKumF4 = Categoria.builder().nombre("Juvenil Femenino <52kg").modalidad("kumite").genero("F").edadMinima(12).edadMaxima(13).pesoMaximo(52.0).build();
        Categoria catJuvKumF5 = Categoria.builder().nombre("Juvenil Femenino >52kg").modalidad("kumite").genero("F").edadMinima(12).edadMaxima(13).pesoMinimo(52.0).build();
        List<Categoria> juveniles = new ArrayList<>();
        juveniles.add(catJuvKataF); juveniles.add(catJuvKataM); juveniles.add(catJuvKumF1); juveniles.add(catJuvKumF2); juveniles.add(catJuvKumF3); juveniles.add(catJuvKumF4); juveniles.add(catJuvKumF5); juveniles.add(catJuvKumM1); juveniles.add(catJuvKumM2); juveniles.add(catJuvKumM3); juveniles.add(catJuvKumM4); juveniles.add(catJuvKumM5);
        categoriaRepository.saveAll(juveniles);

        // CADETE
        Categoria catCadKataM = Categoria.builder().nombre("Cadete Masculino Kata").modalidad("kata").genero("M").edadMinima(14).edadMaxima(15).build();
        Categoria catCadKataF = Categoria.builder().nombre("Cadete Femenino Kata").modalidad("kata").genero("F").edadMinima(14).edadMaxima(15).build();
        Categoria catCadKumM1 = Categoria.builder().nombre("Cadete Masculino <52kg").modalidad("kumite").genero("M").edadMinima(14).edadMaxima(15).pesoMaximo(52.0).build();
        Categoria catCadKumM2 = Categoria.builder().nombre("Cadete Masculino <57kg").modalidad("kumite").genero("M").edadMinima(14).edadMaxima(15).pesoMaximo(57.0).build();
        Categoria catCadKumM3 = Categoria.builder().nombre("Cadete Masculino <63kg").modalidad("kumite").genero("M").edadMinima(14).edadMaxima(15).pesoMaximo(63.0).build();
        Categoria catCadKumM4 = Categoria.builder().nombre("Cadete Masculino <70kg").modalidad("kumite").genero("M").edadMinima(14).edadMaxima(15).pesoMaximo(70.0).build();
        Categoria catCadKumM5 = Categoria.builder().nombre("Cadete Masculino >70kg").modalidad("kumite").genero("M").edadMinima(14).edadMaxima(15).pesoMinimo(70.0).build();
        Categoria catCadKumF1 = Categoria.builder().nombre("Cadete Femenino <47kg").modalidad("kumite").genero("F").edadMinima(14).edadMaxima(15).pesoMaximo(47.0).build();
        Categoria catCadKumF2 = Categoria.builder().nombre("Cadete Femenino <54kg").modalidad("kumite").genero("F").edadMinima(14).edadMaxima(15).pesoMaximo(54.0).build();
        Categoria catCadKumF3 = Categoria.builder().nombre("Cadete Femenino <61kg").modalidad("kumite").genero("F").edadMinima(14).edadMaxima(15).pesoMaximo(61.0).build();
        Categoria catCadKumF4 = Categoria.builder().nombre("Cadete Femenino >61kg").modalidad("kumite").genero("F").edadMinima(14).edadMaxima(15).pesoMinimo(61.0).build();
        List<Categoria> cadetes = new ArrayList<>();
        cadetes.add(catCadKataM); cadetes.add(catCadKataF); cadetes.add(catCadKumF1); cadetes.add(catCadKumF2); cadetes.add(catCadKumF3); cadetes.add(catCadKumF4); cadetes.add(catCadKumM1); cadetes.add(catCadKumM2); cadetes.add(catCadKumM3); cadetes.add(catCadKumM4); cadetes.add(catCadKumM5);
        categoriaRepository.saveAll(cadetes);

        // JUNIOR
        Categoria catJunKatM = Categoria.builder().nombre("Junior Masculino Kata").modalidad("kata").genero("M").edadMinima(16).edadMaxima(17).build();
        Categoria catJunKatF = Categoria.builder().nombre("Junior Femenino Kata").modalidad("kata").genero("F").edadMinima(16).edadMaxima(17).build();
        Categoria catJunKumM1 = Categoria.builder().nombre("Junior Masculino <55kg").modalidad("kumite").genero("M").edadMinima(16).edadMaxima(17).pesoMaximo(55.0).build();
        Categoria catJunKumM2 = Categoria.builder().nombre("Junior Masculino <61kg").modalidad("kumite").genero("M").edadMinima(16).edadMaxima(17).pesoMaximo(61.0).build();
        Categoria catJunKumM3 = Categoria.builder().nombre("Junior Masculino <68kg").modalidad("kumite").genero("M").edadMinima(16).edadMaxima(17).pesoMaximo(68.0).build();
        Categoria catJunKumM4 = Categoria.builder().nombre("Junior Masculino <76kg").modalidad("kumite").genero("M").edadMinima(16).edadMaxima(17).pesoMaximo(76.0).build();
        Categoria catJunKumM5 = Categoria.builder().nombre("Junior Masculino >76kg").modalidad("kumite").genero("M").edadMinima(16).edadMaxima(17).pesoMinimo(76.0).build();
        Categoria catJunKumF1 = Categoria.builder().nombre("Junior Femenino <48kg").modalidad("kumite").genero("F").edadMinima(16).edadMaxima(17).pesoMaximo(48.0).build();
        Categoria catJunKumF2 = Categoria.builder().nombre("Junior Femenino <53kg").modalidad("kumite").genero("F").edadMinima(16).edadMaxima(17).pesoMaximo(53.0).build();
        Categoria catJunKumF3 = Categoria.builder().nombre("Junior Femenino <59kg").modalidad("kumite").genero("F").edadMinima(16).edadMaxima(17).pesoMaximo(59.0).build();
        Categoria catJunKumF4 = Categoria.builder().nombre("Junior Femenino <66kg").modalidad("kumite").genero("F").edadMinima(16).edadMaxima(17).pesoMaximo(66.0).build();
        Categoria catJunKumF5 = Categoria.builder().nombre("Junior Femenino >66kg").modalidad("kumite").genero("F").edadMinima(16).edadMaxima(17).pesoMinimo(66.0).build();
        List<Categoria> juniors = new ArrayList<>();
        juniors.add(catJunKatF); juniors.add(catJunKatM); juniors.add(catJunKumF1); juniors.add(catJunKumF2); juniors.add(catJunKumF3); juniors.add(catJunKumF4); juniors.add(catJunKumF5); juniors.add(catJunKumM1); juniors.add(catJunKumM2); juniors.add(catJunKumM3); juniors.add(catJunKumM4); juniors.add(catJunKumM5);
        categoriaRepository.saveAll(juniors);

        // SUB-21
        Categoria catSub21KatM = Categoria.builder().nombre("Sub21 Masculino Kata").modalidad("kata").genero("M").edadMinima(18).edadMaxima(20).build();
        Categoria catSub21KatF = Categoria.builder().nombre("Sub21 Femenino Kata").modalidad("kata").genero("F").edadMinima(18).edadMaxima(20).build();
        Categoria catSub21KumM1 = Categoria.builder().nombre("Sub21 Masculino <60kg").modalidad("kumite").genero("M").edadMinima(18).edadMaxima(20).pesoMaximo(60.0).build();
        Categoria catSub21KumM2 = Categoria.builder().nombre("Sub21 Masculino <67kg").modalidad("kumite").genero("M").edadMinima(18).edadMaxima(20).pesoMaximo(67.0).build();
        Categoria catSub21KumM3 = Categoria.builder().nombre("Sub21 Masculino <75kg").modalidad("kumite").genero("M").edadMinima(18).edadMaxima(20).pesoMaximo(75.0).build();
        Categoria catSub21KumM4 = Categoria.builder().nombre("Sub21 Masculino <84kg").modalidad("kumite").genero("M").edadMinima(18).edadMaxima(20).pesoMaximo(84.0).build();
        Categoria catSub21KumM5 = Categoria.builder().nombre("Sub21 Masculino >84kg").modalidad("kumite").genero("M").edadMinima(18).edadMaxima(20).pesoMinimo(84.0).build();
        Categoria catSub21KumF1 = Categoria.builder().nombre("Sub21 Femenino <50kg").modalidad("kumite").genero("F").edadMinima(18).edadMaxima(20).pesoMaximo(50.0).build();
        Categoria catSub21KumF2 = Categoria.builder().nombre("Sub21 Femenino <55kg").modalidad("kumite").genero("F").edadMinima(18).edadMaxima(20).pesoMaximo(55.0).build();
        Categoria catSub21KumF3 = Categoria.builder().nombre("Sub21 Femenino <61kg").modalidad("kumite").genero("F").edadMinima(18).edadMaxima(20).pesoMaximo(61.0).build();
        Categoria catSub21KumF4 = Categoria.builder().nombre("Sub21 Femenino <68kg").modalidad("kumite").genero("F").edadMinima(18).edadMaxima(20).pesoMaximo(68.0).build();
        Categoria catSub21KumF5 = Categoria.builder().nombre("Sub21 Femenino >68kg").modalidad("kumite").genero("F").edadMinima(18).edadMaxima(20).pesoMinimo(68.0).build();
        List<Categoria> sub21 = new ArrayList<>();
        sub21.add(catSub21KatF); sub21.add(catSub21KatM); sub21.add(catSub21KumF1); sub21.add(catSub21KumF2); sub21.add(catSub21KumF3); sub21.add(catSub21KumF4); sub21.add(catSub21KumF5); sub21.add(catSub21KumM1); sub21.add(catSub21KumM2); sub21.add(catSub21KumM3); sub21.add(catSub21KumM4); sub21.add(catSub21KumM5);
        categoriaRepository.saveAll(sub21);

        // SENIOR
        Categoria catSenKatM = Categoria.builder().nombre("Senior Masculino Kata").modalidad("kata").genero("M").edadMinima(16).edadMaxima(99).build();
        Categoria catSenKatF = Categoria.builder().nombre("Senior Femenino Kata").modalidad("kata").genero("F").edadMinima(16).edadMaxima(99).build();
        Categoria catSenKumM1 = Categoria.builder().nombre("Senior Masculino <60kg").modalidad("kumite").genero("M").edadMinima(18).edadMaxima(99).pesoMaximo(60.0).build();
        Categoria catSenKumM2 = Categoria.builder().nombre("Senior Masculino <67kg").modalidad("kumite").genero("M").edadMinima(18).edadMaxima(99).pesoMaximo(67.0).build();
        Categoria catSenKumM3 = Categoria.builder().nombre("Senior Masculino <75kg").modalidad("kumite").genero("M").edadMinima(18).edadMaxima(99).pesoMaximo(75.0).build();
        Categoria catSenKumM4 = Categoria.builder().nombre("Senior Masculino <84kg").modalidad("kumite").genero("M").edadMinima(18).edadMaxima(99).pesoMaximo(84.0).build();
        Categoria catSenKumM5 = Categoria.builder().nombre("Senior Masculino >84kg").modalidad("kumite").genero("M").edadMinima(18).edadMaxima(99).pesoMinimo(84.0).build();
        Categoria catSenKumF1 = Categoria.builder().nombre("Senior Femenino <50kg").modalidad("kumite").genero("F").edadMinima(18).edadMaxima(99).pesoMaximo(50.0).build();
        Categoria catSenKumF2 = Categoria.builder().nombre("Senior Femenino <55kg").modalidad("kumite").genero("F").edadMinima(18).edadMaxima(99).pesoMaximo(55.0).build();
        Categoria catSenKumF3 = Categoria.builder().nombre("Senior Femenino <61kg").modalidad("kumite").genero("F").edadMinima(18).edadMaxima(99).pesoMaximo(61.0).build();
        Categoria catSenKumF4 = Categoria.builder().nombre("Senior Femenino <68kg").modalidad("kumite").genero("F").edadMinima(18).edadMaxima(99).pesoMaximo(68.0).build();
        Categoria catSenKumF5 = Categoria.builder().nombre("Senior Femenino >68kg").modalidad("kumite").genero("F").edadMinima(18).edadMaxima(99).pesoMinimo(68.0).build();
        List<Categoria> seniors = new ArrayList<>();
        seniors.add(catSenKatF); seniors.add(catSenKatM); seniors.add(catSenKumF1); seniors.add(catSenKumF2); seniors.add(catSenKumF3); seniors.add(catSenKumF4); seniors.add(catSenKumF5); seniors.add(catSenKumM1); seniors.add(catSenKumM2); seniors.add(catSenKumM3); seniors.add(catSenKumM4); seniors.add(catSenKumM5);
        categoriaRepository.saveAll(seniors);

        // MASTER
        Categoria catMasKatM1 = Categoria.builder().nombre("Master Masculino 35-40 Kata").modalidad("kata").genero("M").edadMinima(35).edadMaxima(40).build();
        Categoria catMasKatM2 = Categoria.builder().nombre("Master Masculino 41-45 Kata").modalidad("kata").genero("M").edadMinima(41).edadMaxima(45).build();
        Categoria catMasKatM3 = Categoria.builder().nombre("Master Masculino 46-50 Kata").modalidad("kata").genero("M").edadMinima(46).edadMaxima(50).build();
        Categoria catMasKatM4 = Categoria.builder().nombre("Master Masculino 51-55 Kata").modalidad("kata").genero("M").edadMinima(51).edadMaxima(55).build();
        Categoria catMasKatM5 = Categoria.builder().nombre("Master Masculino 56-60 Kata").modalidad("kata").genero("M").edadMinima(56).edadMaxima(60).build();
        Categoria catMasKatM6 = Categoria.builder().nombre("Master Masculino 61-65 Kata").modalidad("kata").genero("M").edadMinima(61).edadMaxima(65).build();
        Categoria catMasKatM7 = Categoria.builder().nombre("Master Masculino 66-70 Kata").modalidad("kata").genero("M").edadMinima(66).edadMaxima(70).build();
        Categoria catMasKatM8 = Categoria.builder().nombre("Master Masculino 71-99 Kata").modalidad("kata").genero("M").edadMinima(71).edadMaxima(99).build();
        Categoria catMasKatF1 = Categoria.builder().nombre("Master Femenino 35-40 Kata").modalidad("kata").genero("F").edadMinima(35).edadMaxima(40).build();
        Categoria catMasKatF2 = Categoria.builder().nombre("Master Femenino 41-45 Kata").modalidad("kata").genero("F").edadMinima(41).edadMaxima(45).build();
        Categoria catMasKatF3 = Categoria.builder().nombre("Master Femenino 46-50 Kata").modalidad("kata").genero("F").edadMinima(46).edadMaxima(50).build();
        Categoria catMasKatF4 = Categoria.builder().nombre("Master Femenino 51-55 Kata").modalidad("kata").genero("F").edadMinima(51).edadMaxima(55).build();
        Categoria catMasKatF5 = Categoria.builder().nombre("Master Femenino 56-60 Kata").modalidad("kata").genero("F").edadMinima(56).edadMaxima(60).build();
        Categoria catMasKatF6 = Categoria.builder().nombre("Master Femenino 61-65 Kata").modalidad("kata").genero("F").edadMinima(61).edadMaxima(65).build();
        Categoria catMasKatF7 = Categoria.builder().nombre("Master Femenino 66-70 Kata").modalidad("kata").genero("F").edadMinima(66).edadMaxima(70).build();
        Categoria catMasKatF8 = Categoria.builder().nombre("Master Femenino 71-99 Kata").modalidad("kata").genero("F").edadMinima(71).edadMaxima(99).build();
        Categoria catMasKumM1 = Categoria.builder().nombre("Master Masc. 35-40 -75kg").modalidad("kumite").genero("M").edadMinima(35).edadMaxima(40).pesoMaximo(75.0).build();
        Categoria catMasKumM2 = Categoria.builder().nombre("Master Masc. 35-40 +75kg").modalidad("kumite").genero("M").edadMinima(35).edadMaxima(40).pesoMinimo(75.0).build();
        Categoria catMasKumM3 = Categoria.builder().nombre("Master Masc. 41-50 -75kg").modalidad("kumite").genero("M").edadMinima(41).edadMaxima(50).pesoMaximo(75.0).build();
        Categoria catMasKumM4 = Categoria.builder().nombre("Master Masc. 41-50 +75kg").modalidad("kumite").genero("M").edadMinima(41).edadMaxima(50).pesoMinimo(75.0).build();
        Categoria catMasKumM5 = Categoria.builder().nombre("Master Masc. 51-60 -75kg").modalidad("kumite").genero("M").edadMinima(51).edadMaxima(60).pesoMaximo(75.0).build();
        Categoria catMasKumM6 = Categoria.builder().nombre("Master Masc. 51-60 +75kg").modalidad("kumite").genero("M").edadMinima(51).edadMaxima(60).pesoMinimo(75.0).build();
        Categoria catMasKumM7 = Categoria.builder().nombre("Master Masc. +61 -75kg").modalidad("kumite").genero("M").edadMinima(61).edadMaxima(99).pesoMaximo(75.0).build();
        Categoria catMasKumM8 = Categoria.builder().nombre("Master Masc. +61 +75kg").modalidad("kumite").genero("M").edadMinima(61).edadMaxima(99).pesoMinimo(75.0).build();
        Categoria catMasKumF1 = Categoria.builder().nombre("Master Fem. 35-40 Open").modalidad("kumite").genero("F").edadMinima(35).edadMaxima(40).build();
        Categoria catMasKumF2 = Categoria.builder().nombre("Master Fem. 41-50 Open").modalidad("kumite").genero("F").edadMinima(41).edadMaxima(50).build();
        Categoria catMasKumF3 = Categoria.builder().nombre("Master Fem. 51+ Open").modalidad("kumite").genero("F").edadMinima(51).edadMaxima(99).build();

        List<Categoria> masters = new ArrayList<>(List.of(catMasKatM1, catMasKatM2, catMasKatM3, catMasKatM4, catMasKatM5, catMasKatM6, catMasKatM7, catMasKatM8, catMasKatF1, catMasKatF2, catMasKatF3, catMasKatF4, catMasKatF5, catMasKatF6, catMasKatF7, catMasKatF8, catMasKumM1, catMasKumM2, catMasKumM3, catMasKumM4, catMasKumM5, catMasKumM6, catMasKumM7, catMasKumM8, catMasKumF1, catMasKumF2, catMasKumF3));
        categoriaRepository.saveAll(masters);

        List<Categoria> alvFem = alevines.stream().filter(cat -> cat.getGenero().equals("F")).toList();
        List<Categoria> infFem = infantiles.stream().filter(cat -> cat.getGenero().equals("F")).toList();
        List<Categoria> juvFem = juveniles.stream().filter(cat -> cat.getGenero().equals("F")).toList();
        List<Categoria> cadFem = cadetes.stream().filter(cat -> cat.getGenero().equals("F")).toList();
        List<Categoria> junFem = juniors.stream().filter(cat -> cat.getGenero().equals("F")).toList();
        List<Categoria> s21Fem = sub21.stream().filter(cat -> cat.getGenero().equals("F")).toList();
        List<Categoria> senFem = seniors.stream().filter(cat -> cat.getGenero().equals("F")).toList();

        List<Categoria> alvMasc = alevines.stream().filter(cat -> cat.getGenero().equals("M")).toList();
        List<Categoria> infMasc = infantiles.stream().filter(cat -> cat.getGenero().equals("M")).toList();
        List<Categoria> juvMasc = juveniles.stream().filter(cat -> cat.getGenero().equals("M")).toList();
        List<Categoria> cadMasc = cadetes.stream().filter(cat -> cat.getGenero().equals("M")).toList();
        List<Categoria> junMasc = juniors.stream().filter(cat -> cat.getGenero().equals("M")).toList();
        List<Categoria> s21Masc = sub21.stream().filter(cat -> cat.getGenero().equals("M")).toList();
        List<Categoria> senMasc = seniors.stream().filter(cat -> cat.getGenero().equals("M")).toList();
// CAMPEONATOS - usando toDate() para convertir LocalDate a Date correctamente
        Campeonato camp1 = Campeonato.builder().nombre("Campeonato de España Cadete/Junior/Sub-21 2026").fechaInicio(toDate(LocalDate.of(2026, 11, 10))).fechaFin(toDate(LocalDate.of(2026, 11, 12))).ubicacion("Pabellón Multiusos, Madrid").estado("futuro").nivel("Nacional").descripcion("Torneo clave para la clasificación al próximo campeonato de Europa").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp2 = Campeonato.builder().nombre("Campeonato de España Senior 2026").fechaInicio(toDate(LocalDate.of(2026, 2, 2))).fechaFin(toDate(LocalDate.of(2026, 2, 4))).ubicacion("Pabellón de Carranque, Málaga, Andalucía, España").estado("pasado").nivel("Nacional").descripcion("Torneo clasificatorio para el mundial").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp3 = Campeonato.builder().nombre("Campeonato de Andalucía alevin/infantil/juvenil 2026").fechaInicio(toDate(LocalDate.of(2026, 3, 1))).fechaFin(toDate(LocalDate.of(2026, 3, 1))).ubicacion("Pabellón de Deportes de Córdoba, Córdoba, Andalucía, España").estado("futuro").nivel("Provincial").descripcion("Torneo clasificatorio para el campeonato de España").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp4 = Campeonato.builder().nombre("Campeonato de Andalucía Senior 2026").fechaInicio(toDate(LocalDate.of(2026, 3, 28))).fechaFin(toDate(LocalDate.of(2026, 3, 28))).ubicacion("Pabellón de Ciudad Jardín, Málaga, Andalucía").estado("futuro").nivel("Provincial").descripcion("Torneo clasificatorio para el campeonato de España").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp5 = Campeonato.builder().nombre("Liga Iberdrola Cadete/Junior/Sub-21 J4 2026").fechaInicio(toDate(LocalDate.of(2026, 3, 8))).fechaFin(toDate(LocalDate.of(2026, 3, 8))).ubicacion("Pabellón Municipal Puerta de Santa María, 13005 Ciudad Real").estado("futuro").nivel("Nacional").descripcion("Torneo clasificatorio para el Campeonato Europeo y con recompensa de créditos").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp6 = Campeonato.builder().nombre("Liga Nacional Cadete/Junior/Sub-21 J4 2026").fechaInicio(toDate(LocalDate.of(2026, 3, 7))).fechaFin(toDate(LocalDate.of(2026, 3, 7))).ubicacion("Pabellón Municipal Puerta de Santa María, 13005 Ciudad Real").estado("futuro").nivel("Nacional").descripcion("Torneo clasificatorio para el Campeonato Europeo y con recompensa de créditos").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp7 = Campeonato.builder().nombre("VII Campeonato de España Master 2026").fechaInicio(toDate(LocalDate.of(2026, 4, 10))).fechaFin(toDate(LocalDate.of(2026, 4, 12))).ubicacion("La Nucía, Alicante, España").estado("futuro").nivel("Nacional").descripcion("Campeonato nacional categoría master").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp8 = Campeonato.builder().nombre("Campeonato de España Universitario 2026").fechaInicio(toDate(LocalDate.of(2026, 4, 17))).fechaFin(toDate(LocalDate.of(2026, 4, 18))).ubicacion("Granada, Andalucía, España").estado("futuro").nivel("Nacional").descripcion("Campeonato universitario nacional de karate").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp9 = Campeonato.builder().nombre("Campeonato de España Alevín/Infantil/Juvenil 2026").fechaInicio(toDate(LocalDate.of(2026, 5, 8))).fechaFin(toDate(LocalDate.of(2026, 5, 10))).ubicacion("Oviedo, Asturias, España").estado("futuro").nivel("Nacional").descripcion("Campeonato nacional categorías infantil").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp10 = Campeonato.builder().nombre("Campeonato de España Absoluto 2026").fechaInicio(toDate(LocalDate.of(2026, 3, 27))).fechaFin(toDate(LocalDate.of(2026, 3, 29))).ubicacion("Pontevedra, España").estado("futuro").nivel("Nacional").descripcion("Campeonato nacional absoluto").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp11 = Campeonato.builder().nombre("Liga Nacional Master - 1ª Ronda 2026").fechaInicio(toDate(LocalDate.of(2026, 3, 1))).fechaFin(toDate(LocalDate.of(2026, 3, 1))).ubicacion("Torrejón de Ardoz, Madrid, España").estado("futuro").nivel("Nacional").descripcion("Primera ronda liga nacional master").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp12 = Campeonato.builder().nombre("Campeonato de España Cadete/Junior/Sub-21 2026 (Final)").fechaInicio(toDate(LocalDate.of(2026, 11, 27))).fechaFin(toDate(LocalDate.of(2026, 11, 29))).ubicacion("Valencia, España").estado("futuro").nivel("Nacional").descripcion("Campeonato nacional categorías cadete junior sub21").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp13 = Campeonato.builder().nombre("Liga Nacional Masculina Absoluta/Cadete - 1ª Ronda 2026").fechaInicio(toDate(LocalDate.of(2026, 1, 17))).fechaFin(toDate(LocalDate.of(2026, 1, 17))).ubicacion("Valdepeñas, Ciudad Real, España").estado("pasado").nivel("Nacional").descripcion("Primera ronda Liga Nacional masculina absoluta y cadete").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp14 = Campeonato.builder().nombre("Liga Iberdrola Absoluta/Cadete - 1ª Ronda 2026").fechaInicio(toDate(LocalDate.of(2026, 1, 18))).fechaFin(toDate(LocalDate.of(2026, 1, 18))).ubicacion("Valdepeñas, Ciudad Real, España").estado("pasado").nivel("Nacional").descripcion("Primera ronda Liga Iberdrola femenina absoluta y cadete").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp15 = Campeonato.builder().nombre("Liga Nacional Masculina Alevín/Infantil/Juvenil - 1ª Ronda 2026").fechaInicio(toDate(LocalDate.of(2026, 2, 21))).fechaFin(toDate(LocalDate.of(2026, 2, 21))).ubicacion("Logroño, España").estado("pasado").nivel("Nacional").descripcion("Primera ronda Liga Nacional categorías base").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp16 = Campeonato.builder().nombre("Liga Iberdrola Alevín/Infantil/Juvenil - 1ª Ronda 2026").fechaInicio(toDate(LocalDate.of(2026, 2, 22))).fechaFin(toDate(LocalDate.of(2026, 2, 22))).ubicacion("Logroño, España").estado("pasado").nivel("Nacional").descripcion("Primera ronda Liga Iberdrola categorías base").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp17 = Campeonato.builder().nombre("Liga Nacional Master - 1ª Ronda 2026").fechaInicio(toDate(LocalDate.of(2026, 3, 1))).fechaFin(toDate(LocalDate.of(2026, 3, 1))).ubicacion("Torrejón de Ardoz, Madrid, España").estado("futuro").nivel("Nacional").descripcion("Primera ronda Liga Nacional master").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp18 = Campeonato.builder().nombre("Liga Nacional Junior/Sub21 - 1ª Ronda 2026").fechaInicio(toDate(LocalDate.of(2026, 3, 7))).fechaFin(toDate(LocalDate.of(2026, 3, 7))).ubicacion("Ciudad Real, España").estado("futuro").nivel("Nacional").descripcion("Primera ronda Liga Nacional junior y sub21").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp19 = Campeonato.builder().nombre("Liga Iberdrola Junior/Sub21 - 1ª Ronda 2026").fechaInicio(toDate(LocalDate.of(2026, 3, 8))).fechaFin(toDate(LocalDate.of(2026, 3, 8))).ubicacion("Ciudad Real, España").estado("futuro").nivel("Nacional").descripcion("Primera ronda Liga Iberdrola junior y sub21").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp20 = Campeonato.builder().nombre("Liga Nacional Masculina Absoluta/Cadete - 2ª Ronda 2026").fechaInicio(toDate(LocalDate.of(2026, 10, 24))).fechaFin(toDate(LocalDate.of(2026, 10, 24))).ubicacion("Elche, Alicante, España").estado("futuro").nivel("Nacional").descripcion("Segunda ronda Liga Nacional masculina absoluta y cadete").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp21 = Campeonato.builder().nombre("Liga Iberdrola Absoluta/Cadete - 2ª Ronda 2026").fechaInicio(toDate(LocalDate.of(2026, 10, 25))).fechaFin(toDate(LocalDate.of(2026, 10, 25))).ubicacion("Elche, Alicante, España").estado("futuro").nivel("Nacional").descripcion("Segunda ronda Liga Iberdrola femenina absoluta y cadete").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();
        Campeonato camp22 = Campeonato.builder().nombre("Liga Nacional Master - 2ª Ronda 2026").fechaInicio(toDate(LocalDate.of(2026, 11, 1))).fechaFin(toDate(LocalDate.of(2026, 11, 1))).ubicacion("Córdoba, España").estado("futuro").nivel("Nacional").descripcion("Segunda ronda Liga Nacional master").urlPortada("https://livesportscoring.com/2026/RFEK/RFEK2026_Absoluto/docs/RFEK2026_Absoluto.jpg").build();

        campeonatoRepository.saveAll(List.of(camp1, camp2, camp3, camp4, camp5, camp6, camp7, camp8, camp9, camp10, camp11, camp12, camp13, camp14, camp15, camp16, camp17, camp18, camp19, camp20, camp21, camp22));

        // Relacion Campeonato -> Categorias
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp1, cadetes));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp1, juniors));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp1, sub21));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp2, seniors));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp3, alevines));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp3, infantiles));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp3, juveniles));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp4, seniors));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp5, cadFem));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp5, junFem));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp5, s21Fem));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp6, cadMasc));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp6, junMasc));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp6, s21Masc));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp7, masters));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp8, seniors));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp9, infantiles));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp10, seniors));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp11, masters));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp12, cadetes));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp12, juniors));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp12, sub21));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp13, cadetes));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp13, seniors));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp14, cadFem));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp14, senFem));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp15, alvMasc));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp15, infMasc));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp15, juvMasc));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp16, alvFem));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp16, infFem));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp16, juvFem));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp17, masters));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp18, junMasc));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp18, s21Masc));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp19, junFem));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp19, s21Fem));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp20, cadMasc));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp20, senMasc));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp21, cadFem));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp21, senFem));
        campeonato_categoriaRepository.saveAll(createCampeonatoCategoria(camp22, masters));

        // 3. CREAR COMPETIDORES
        // Usuario.rol es obligatorio → asignamos COMPETIDOR
        Competidor senM1  = Competidor.builder().nombre("Juan").apellidos("Pérez Cano").dni("12345678A").email("juan.perez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2000, 5, 20))).genero('M').club("Dojo Shoto").federacionAutonomica("Comunidad de Madrid").build();
        Competidor senM2  = Competidor.builder().nombre("Alberto").apellidos("Ruiz Galiano").dni("87654321B").email("alberto.ruiz@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1998, 11, 2))).genero('M').club("Karate Almería").federacionAutonomica("Andalucía").build();
        Competidor senM3  = Competidor.builder().nombre("Carlos").apellidos("López Martín").dni("11223344C").email("carlos.lopez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2001, 3, 15))).genero('M').club("Karate Aragón").federacionAutonomica("Aragón").build();
        Competidor senM4  = Competidor.builder().nombre("Sergio").apellidos("Fernández Ruiz").dni("55667788G").email("sergio.fernandez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1997, 4, 30))).genero('M').club("Karate Santander").federacionAutonomica("Cantabria").build();
        Competidor senM5  = Competidor.builder().nombre("Miguel").apellidos("Rodríguez Blanco").dni("77889900I").email("miguel.rodriguez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1996, 12, 5))).genero('M').club("Karate Valladolid").federacionAutonomica("Castilla y León").build();
        Competidor senM6  = Competidor.builder().nombre("Javier").apellidos("Moreno Castillo").dni("99001122K").email("javier.moreno@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1999, 2, 14))).genero('M').club("Karate Mérida").federacionAutonomica("Extremadura").build();
        Competidor senM7  = Competidor.builder().nombre("David").apellidos("Romero Iglesias").dni("10203040M").email("david.romero@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1998, 5, 3))).genero('M').club("Karate Logroño").federacionAutonomica("La Rioja").build();
        Competidor senM8  = Competidor.builder().nombre("Pablo").apellidos("Torres Herrera").dni("30405060O").email("pablo.torres@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2001, 10, 16))).genero('M').club("Karate Murcia").federacionAutonomica("Región de Murcia").build();
        Competidor senM9  = Competidor.builder().nombre("Iñaki").apellidos("Etxebarria Zubieta").dni("50607080Q").email("inaki.etxebarria@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2000, 6, 11))).genero('M').club("Karate Bilbao").federacionAutonomica("País Vasco").build();
        Competidor senM10 = Competidor.builder().nombre("Alejandro").apellidos("Vega Santana").dni("70809000S").email("alejandro.vega@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2003, 4, 19))).genero('M').club("Karate Melilla").federacionAutonomica("Ciudad Autónoma de Melilla").build();
        Competidor senM11 = Competidor.builder().nombre("Fernando").apellidos("Castillo Reyes").dni("11122233T").email("fernando.castillo@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1995, 8, 7))).genero('M').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor senM12 = Competidor.builder().nombre("Raúl").apellidos("Molina Pardo").dni("22233344U").email("raul.molina@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1999, 1, 25))).genero('M').club("Karate Pamplona").federacionAutonomica("Comunidad Foral de Navarra").build();
        Competidor senM13 = Competidor.builder().nombre("Óscar").apellidos("Blanco Fuentes").dni("33344455V").email("oscar.blanco@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2002, 7, 13))).genero('M').club("Karate Toledo").federacionAutonomica("Castilla-La Mancha").build();
        Competidor senM14 = Competidor.builder().nombre("Marcos").apellidos("Herrera Gutiérrez").dni("44455566W").email("marcos.herrera@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1997, 3, 28))).genero('M').club("Karate Barcelona").federacionAutonomica("Cataluña").build();
        Competidor senM15 = Competidor.builder().nombre("Antonio").apellidos("Soto Medina").dni("55566677X").email("antonio.soto@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2000, 11, 9))).genero('M').club("Karate Santiago").federacionAutonomica("Galicia").build();
        Competidor senM16 = Competidor.builder().nombre("Diego").apellidos("Cruz Navarro").dni("66677788Y").email("diego.cruz@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1994, 6, 15))).genero('M').club("Karate Oviedo").federacionAutonomica("Principado de Asturias").build();
        Competidor senM17 = Competidor.builder().nombre("Rubén").apellidos("Vidal Campos").dni("77788899Z").email("ruben.vidal@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2001, 9, 3))).genero('M').club("Karate Palma").federacionAutonomica("Islas Baleares").build();
        Competidor senM18 = Competidor.builder().nombre("Adrián").apellidos("Aguilar Delgado").dni("88899900A").email("adrian.aguilar@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1998, 4, 22))).genero('M').club("Karate Las Palmas").federacionAutonomica("Canarias").build();
        Competidor senM19 = Competidor.builder().nombre("Álvaro").apellidos("Marín Expósito").dni("99900011B").email("alvaro.marin@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2003, 12, 1))).genero('M').club("Karate Ceuta").federacionAutonomica("Ciudad Autónoma de Ceuta").build();
        Competidor senM20 = Competidor.builder().nombre("Gonzalo").apellidos("Pascual Llorente").dni("00011122C").email("gonzalo.pascual@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1996, 7, 18))).genero('M').club("Dojo Shoto Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor senM21 = Competidor.builder().nombre("Nicolás").apellidos("Serrano Bravo").dni("11100200D").email("nicolas.serrano@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2000, 2, 14))).genero('M').club("Karate Sevilla").federacionAutonomica("Andalucía").build();
        Competidor senM22 = Competidor.builder().nombre("Hugo").apellidos("Ramos Ibáñez").dni("22200300E").email("hugo.ramos@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1999, 8, 30))).genero('M').club("Karate Zaragoza").federacionAutonomica("Aragón").build();
        Competidor senM23 = Competidor.builder().nombre("Víctor").apellidos("Pons Leal").dni("33300400F").email("victor.pons@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2002, 5, 11))).genero('M').club("Karate Alicante").federacionAutonomica("Comunitat Valenciana").build();
        Competidor senM24 = Competidor.builder().nombre("Eduardo").apellidos("Fuentes Cortés").dni("44400500G").email("eduardo.fuentes@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1995, 10, 27))).genero('M').club("Karate Vigo").federacionAutonomica("Galicia").build();

        Competidor senF1  = Competidor.builder().nombre("María").apellidos("García Fernández").dni("22334455D").email("maria.garcia@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1999, 7, 8))).genero('F').club("Karate Oviedo").federacionAutonomica("Principado de Asturias").build();
        Competidor senF2  = Competidor.builder().nombre("Laura").apellidos("González Díaz").dni("44556677F").email("laura.gonzalez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2000, 9, 12))).genero('F').club("Karate Las Palmas").federacionAutonomica("Canarias").build();
        Competidor senF3  = Competidor.builder().nombre("Ana").apellidos("Sánchez Torres").dni("66778899H").email("ana.sanchez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2003, 6, 18))).genero('F').club("Karate Toledo").federacionAutonomica("Castilla-La Mancha").build();
        Competidor senF4  = Competidor.builder().nombre("Elena").apellidos("Jiménez Moreno").dni("88990011J").email("elena.jimenez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2001, 8, 22))).genero('F').club("Karate Barcelona").federacionAutonomica("Cataluña").build();
        Competidor senF5  = Competidor.builder().nombre("Sofía").apellidos("Álvarez Prieto").dni("00112233L").email("sofia.alvarez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2002, 11, 9))).genero('F').club("Karate Santiago").federacionAutonomica("Galicia").build();
        Competidor senF6  = Competidor.builder().nombre("Lucía").apellidos("Serrano Molina").dni("20304050N").email("lucia.serrano@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2000, 7, 27))).genero('F').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor senF7  = Competidor.builder().nombre("Claudia").apellidos("Ramírez Ortega").dni("40506070P").email("claudia.ramirez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1997, 3, 21))).genero('F').club("Karate Pamplona").federacionAutonomica("Comunidad Foral de Navarra").build();
        Competidor senF8  = Competidor.builder().nombre("Nuria").apellidos("Campos Benítez").dni("60708090R").email("nuria.campos@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1999, 1, 7))).genero('F').club("Karate Ceuta").federacionAutonomica("Ciudad Autónoma de Ceuta").build();
        Competidor senF9  = Competidor.builder().nombre("Patricia").apellidos("Ortiz Guerrero").dni("11200300A").email("patricia.ortiz@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1998, 4, 15))).genero('F').club("Karate Bilbao").federacionAutonomica("País Vasco").build();
        Competidor senF10 = Competidor.builder().nombre("Marta").apellidos("León Carrasco").dni("22300400B").email("marta.leon@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2001, 12, 3))).genero('F').club("Karate Murcia").federacionAutonomica("Región de Murcia").build();
        Competidor senF11 = Competidor.builder().nombre("Cristina").apellidos("Vargas Escribano").dni("33400500C").email("cristina.vargas@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2000, 3, 19))).genero('F').club("Karate Logroño").federacionAutonomica("La Rioja").build();
        Competidor senF12 = Competidor.builder().nombre("Isabel").apellidos("Mora Cabrera").dni("44500600D").email("isabel.mora@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1996, 9, 8))).genero('F').club("Karate Santander").federacionAutonomica("Cantabria").build();
        Competidor senF13 = Competidor.builder().nombre("Rebeca").apellidos("Pardo Montoya").dni("55600700E").email("rebeca.pardo@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2002, 6, 25))).genero('F').club("Karate Extremadura").federacionAutonomica("Extremadura").build();
        Competidor senF14 = Competidor.builder().nombre("Verónica").apellidos("Cano Iglesias").dni("66700800F").email("veronica.cano@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1999, 11, 14))).genero('F').club("Karate Valladolid").federacionAutonomica("Castilla y León").build();
        Competidor senF15 = Competidor.builder().nombre("Beatriz").apellidos("Prieto Alonso").dni("77800900G").email("beatriz.prieto@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2003, 2, 7))).genero('F').club("Karate Aragón").federacionAutonomica("Aragón").build();
        Competidor senF16 = Competidor.builder().nombre("Sandra").apellidos("Gil Méndez").dni("88900000H").email("sandra.gil@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1997, 8, 31))).genero('F').club("Karate Palma").federacionAutonomica("Islas Baleares").build();
        Competidor senF17 = Competidor.builder().nombre("Irene").apellidos("Santos Quintero").dni("99000111I").email("irene.santos@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2000, 5, 16))).genero('F').club("Karate Almería").federacionAutonomica("Andalucía").build();
        Competidor senF18 = Competidor.builder().nombre("Pilar").apellidos("Flores Navas").dni("10100200J").email("pilar.flores@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2001, 1, 29))).genero('F').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor senF19 = Competidor.builder().nombre("Carmen").apellidos("Alvarado Peña").dni("12100300A").email("carmen.alvarado@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2001, 3, 14))).genero('F').club("Karate Sevilla").federacionAutonomica("Andalucía").build();
        Competidor senF20 = Competidor.builder().nombre("Rocío").apellidos("Delgado Vega").dni("12100301B").email("rocio1.delgado@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1999, 8, 22))).genero('F').club("Karate Granada").federacionAutonomica("Andalucía").build();
        Competidor senF21 = Competidor.builder().nombre("Natalia").apellidos("Fuentes Ríos").dni("12100302C").email("natalia.fuentes@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2002, 11, 5))).genero('F').club("Karate Bilbao").federacionAutonomica("País Vasco").build();
        Competidor senF22 = Competidor.builder().nombre("Marta").apellidos("Crespo Solís").dni("12100303D").email("marta.crespo@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2000, 6, 30))).genero('F').club("Karate Barcelona").federacionAutonomica("Cataluña").build();
        Competidor senF23 = Competidor.builder().nombre("Alba").apellidos("Navarro Gil").dni("12100304E").email("alba.navarro@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1998, 1, 17))).genero('F').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor senF24 = Competidor.builder().nombre("Cristina").apellidos("Ortega Luna").dni("12100305F").email("cristina.ortega@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2003, 4, 9))).genero('F').club("Karate Murcia").federacionAutonomica("Región de Murcia").build();
        Competidor senF25 = Competidor.builder().nombre("Patricia").apellidos("Lara Bernal").dni("12100306G").email("patricia.lara@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2001, 9, 25))).genero('F').club("Karate Málaga").federacionAutonomica("Andalucía").build();
        Competidor senF26 = Competidor.builder().nombre("Lorena").apellidos("Vargas Pinto").dni("12100307H").email("lorena.vargas@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(1997, 12, 3))).genero('F').club("Karate Zaragoza").federacionAutonomica("Aragón").build();

        Competidor cadM1 = Competidor.builder().nombre("Mateo").apellidos("Reyes Gallardo").dni("11111101A").email("mateo.reyes@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2011, 3, 10))).genero('M').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor cadM2 = Competidor.builder().nombre("Samuel").apellidos("Vega Torres").dni("11111102B").email("samuel.vega@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2010, 7, 22))).genero('M').club("Karate Sevilla").federacionAutonomica("Andalucía").build();
        Competidor cadM3 = Competidor.builder().nombre("Daniel").apellidos("Castro Morales").dni("11111103C").email("daniel.castro@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2011, 11, 5))).genero('M').club("Karate Zaragoza").federacionAutonomica("Aragón").build();
        Competidor cadM4 = Competidor.builder().nombre("Lucas").apellidos("Jiménez Roca").dni("11111104D").email("lucas.jimenez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2010, 4, 18))).genero('M').club("Karate Bilbao").federacionAutonomica("País Vasco").build();
        Competidor cadM5 = Competidor.builder().nombre("Martín").apellidos("Ortega Sanz").dni("11111105E").email("martin.ortega@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2012, 1, 30))).genero('M').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor cadM6 = Competidor.builder().nombre("Tomás").apellidos("Fuentes Moya").dni("11111106F").email("tomas.fuentes@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2011, 8, 14))).genero('M').club("Karate Barcelona").federacionAutonomica("Cataluña").build();
        Competidor cadM7 = Competidor.builder().nombre("Ignacio").apellidos("Blanco Rueda").dni("11111107G").email("ignacio.blanco@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2010, 12, 3))).genero('M').club("Karate Murcia").federacionAutonomica("Región de Murcia").build();
        Competidor cadM8 = Competidor.builder().nombre("Rodrigo").apellidos("Alonso Vera").dni("11111108H").email("rodrigo.alonso@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2012, 5, 27))).genero('M').club("Karate Valladolid").federacionAutonomica("Castilla y León").build();

        Competidor cadF1  = Competidor.builder().nombre("Carla").apellidos("Navarro Soler").dni("22222201A").email("carla.navarro@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2011, 2, 15))).genero('F').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor cadF2  = Competidor.builder().nombre("Andrea").apellidos("Molina Ibáñez").dni("22222202B").email("andrea.molina@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2010, 6, 9))).genero('F').club("Karate Granada").federacionAutonomica("Andalucía").build();
        Competidor cadF3  = Competidor.builder().nombre("Paula").apellidos("Herrero Cid").dni("22222203C").email("paula.herrero@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2012, 10, 21))).genero('F').club("Karate Pamplona").federacionAutonomica("Comunidad Foral de Navarra").build();
        Competidor cadF4  = Competidor.builder().nombre("Alba").apellidos("Pinto Serrano").dni("22222204D").email("alba.pinto@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2011, 4, 6))).genero('F').club("Karate Santander").federacionAutonomica("Cantabria").build();
        Competidor cadF5  = Competidor.builder().nombre("Nora").apellidos("Medina Rubio").dni("22222205E").email("nora.medina@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2010, 9, 28))).genero('F').club("Karate Logroño").federacionAutonomica("La Rioja").build();
        Competidor cadF6  = Competidor.builder().nombre("Julia").apellidos("Varela Peña").dni("22222206F").email("julia.varela@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2012, 3, 17))).genero('F').club("Karate Galicia").federacionAutonomica("Galicia").build();
        Competidor cadF7  = Competidor.builder().nombre("Elena").apellidos("Vidal Soto").dni("22222207G").email("elena.vidal@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2011, 5, 3))).genero('F').club("Karate Alicante").federacionAutonomica("Comunitat Valenciana").build();
        Competidor cadF8  = Competidor.builder().nombre("Nora").apellidos("Campos Ruiz").dni("22222208H").email("nora.campos@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2010, 9, 17))).genero('F').club("Karate Málaga").federacionAutonomica("Andalucía").build();
        Competidor cadF9  = Competidor.builder().nombre("Sofía").apellidos("Ibáñez Crespo").dni("22222209I").email("sofia.ibanez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2012, 2, 28))).genero('F').club("Karate Murcia").federacionAutonomica("Región de Murcia").build();
        Competidor cadF10 = Competidor.builder().nombre("Claudia").apellidos("Serrano Moya").dni("22222210J").email("claudia.serrano@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2011, 7, 11))).genero('F').club("Karate Córdoba").federacionAutonomica("Andalucía").build();
        Competidor cadF11 = Competidor.builder().nombre("Gabriela").apellidos("Mora Peña").dni("22222211K").email("gabriela.mora@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2010, 11, 4))).genero('F').club("Karate Zaragoza").federacionAutonomica("Aragón").build();
        Competidor cadF12 = Competidor.builder().nombre("Lucía").apellidos("Romero Lago").dni("22222212L").email("lucia.romero@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2012, 4, 19))).genero('F').club("Karate Vigo").federacionAutonomica("Galicia").build();

        Competidor junM1 = Competidor.builder().nombre("Álex").apellidos("Romero Cuesta").dni("33333301A").email("alex.romero@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2009, 5, 12))).genero('M').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor junM2 = Competidor.builder().nombre("Iker").apellidos("Zubieta Larrea").dni("33333302B").email("iker.zubieta@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2008, 11, 25))).genero('M').club("Karate San Sebastián").federacionAutonomica("País Vasco").build();
        Competidor junM3 = Competidor.builder().nombre("Unai").apellidos("Arrizabalaga Olaiz").dni("33333303C").email("unai.arrizabalaga@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2009, 3, 8))).genero('M').club("Karate Bilbao").federacionAutonomica("País Vasco").build();
        Competidor junM4 = Competidor.builder().nombre("Joel").apellidos("Giménez Puig").dni("33333304D").email("joel.gimenez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2010, 1, 14))).genero('M').club("Karate Barcelona").federacionAutonomica("Cataluña").build();
        Competidor junM5 = Competidor.builder().nombre("Pol").apellidos("Soler Massana").dni("33333305E").email("pol.soler@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2009, 7, 30))).genero('M').club("Karate Lleida").federacionAutonomica("Cataluña").build();
        Competidor junM6 = Competidor.builder().nombre("Enrique").apellidos("Pascual Delgado").dni("33333306F").email("enrique.pascual@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2008, 9, 19))).genero('M').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor junM7 = Competidor.builder().nombre("Borja").apellidos("Cabrera Núñez").dni("33333307G").email("borja.cabrera@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2010, 4, 3))).genero('M').club("Karate Tenerife").federacionAutonomica("Canarias").build();
        Competidor junM8 = Competidor.builder().nombre("Jonás").apellidos("Ferrer Llorens").dni("33333308H").email("jonas.ferrer@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2009, 12, 11))).genero('M').club("Karate Alicante").federacionAutonomica("Comunitat Valenciana").build();

        Competidor junF1 = Competidor.builder().nombre("Laia").apellidos("Bosch Martí").dni("44444401A").email("laia.bosch@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2009, 2, 20))).genero('F').club("Karate Barcelona").federacionAutonomica("Cataluña").build();
        Competidor junF2 = Competidor.builder().nombre("Aroa").apellidos("Esteban Muñoz").dni("44444402B").email("aroa.esteban@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2008, 8, 7))).genero('F').club("Karate Zaragoza").federacionAutonomica("Aragón").build();
        Competidor junF3 = Competidor.builder().nombre("Itziar").apellidos("Mendoza Arrieta").dni("44444403C").email("itziar.mendoza@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2010, 5, 15))).genero('F').club("Karate Vitoria").federacionAutonomica("País Vasco").build();
        Competidor junF4 = Competidor.builder().nombre("Amaia").apellidos("Garrido Urresti").dni("44444404D").email("amaia.garrido@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2009, 10, 29))).genero('F').club("Karate Pamplona").federacionAutonomica("Comunidad Foral de Navarra").build();
        Competidor junF5 = Competidor.builder().nombre("Mireia").apellidos("Coll Segura").dni("44444405E").email("mireia.coll@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2008, 4, 3))).genero('F').club("Karate Tarragona").federacionAutonomica("Cataluña").build();
        Competidor junF6 = Competidor.builder().nombre("Naiara").apellidos("Bengoechea Landa").dni("44444406F").email("naiara.bengoechea@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2010, 1, 22))).genero('F').club("Karate Bilbao").federacionAutonomica("País Vasco").build();

        Competidor sub21M1 = Competidor.builder().nombre("Mario").apellidos("Pena Álvarez").dni("55555501A").email("mario.pena@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2006, 4, 8))).genero('M').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor sub21M2 = Competidor.builder().nombre("Andrés").apellidos("Lara Espinosa").dni("55555502B").email("andres.lara@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2007, 9, 17))).genero('M').club("Karate Málaga").federacionAutonomica("Andalucía").build();
        Competidor sub21M3 = Competidor.builder().nombre("Guillermo").apellidos("Tejada Fuertes").dni("55555503C").email("guillermo.tejada@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2005, 12, 30))).genero('M').club("Karate Aragón").federacionAutonomica("Aragón").build();
        Competidor sub21M4 = Competidor.builder().nombre("Héctor").apellidos("Montoya Castañeda").dni("55555504D").email("hector.montoya@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2006, 7, 5))).genero('M').club("Karate Murcia").federacionAutonomica("Región de Murcia").build();
        Competidor sub21M5 = Competidor.builder().nombre("Lorenzo").apellidos("Gallego Pedrosa").dni("55555505E").email("lorenzo.gallego@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2007, 2, 19))).genero('M').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor sub21M6 = Competidor.builder().nombre("Nicolás").apellidos("Ibáñez Mena").dni("55555506F").email("nicolas.ibanez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2005, 10, 11))).genero('M').club("Karate Barcelona").federacionAutonomica("Cataluña").build();
        Competidor sub21M7 = Competidor.builder().nombre("Jaime").apellidos("Paredes Ojeda").dni("55555507G").email("jaime.paredes@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2006, 1, 24))).genero('M').club("Karate Galicia").federacionAutonomica("Galicia").build();
        Competidor sub21M8 = Competidor.builder().nombre("Pelayo").apellidos("Menéndez Quirós").dni("55555508H").email("pelayo.menendez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2007, 6, 13))).genero('M').club("Karate Oviedo").federacionAutonomica("Principado de Asturias").build();

        Competidor sub21F1 = Competidor.builder().nombre("Valeria").apellidos("Bravo Hidalgo").dni("66666601A").email("valeria.bravo@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2006, 3, 14))).genero('F').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor sub21F2 = Competidor.builder().nombre("Daniela").apellidos("Crespo Zamora").dni("66666602B").email("daniela.crespo@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2007, 8, 28))).genero('F').club("Karate Sevilla").federacionAutonomica("Andalucía").build();
        Competidor sub21F3 = Competidor.builder().nombre("Natalia").apellidos("Ríos Baena").dni("66666603C").email("natalia.rios@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2005, 11, 3))).genero('F').club("Karate Cataluña").federacionAutonomica("Cataluña").build();
        Competidor sub21F4 = Competidor.builder().nombre("Leire").apellidos("Urrutia Ibarra").dni("66666604D").email("leire.urrutia@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2006, 5, 17))).genero('F').club("Karate San Sebastián").federacionAutonomica("País Vasco").build();
        Competidor sub21F5 = Competidor.builder().nombre("Rocío").apellidos("Delgado Peña").dni("66666605E").email("rocio.delgado@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2007, 1, 6))).genero('F').club("Karate Málaga").federacionAutonomica("Andalucía").build();
        Competidor sub21F6 = Competidor.builder().nombre("Ainhoa").apellidos("Txabarri Elorza").dni("66666606F").email("ainhoa.txabarri@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2005, 7, 21))).genero('F').club("Karate Bilbao").federacionAutonomica("País Vasco").build();

        Competidor infM1 = Competidor.builder().nombre("Leo").apellidos("Muñoz Palma").dni("77777701A").email("leo.munoz@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2015, 3, 5))).genero('M').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor infM2 = Competidor.builder().nombre("Marcos").apellidos("Rojo Calvo").dni("77777702B").email("marcos.rojo@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2014, 9, 18))).genero('M').club("Karate Sevilla").federacionAutonomica("Andalucía").build();
        Competidor infM3 = Competidor.builder().nombre("Adrián").apellidos("Lozano Rubio").dni("77777703C").email("adrian.lozano@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2015, 6, 12))).genero('M').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor infM4 = Competidor.builder().nombre("Ángel").apellidos("Pereira Barros").dni("77777704D").email("angel.pereira@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2014, 12, 27))).genero('M').club("Karate Galicia").federacionAutonomica("Galicia").build();
        Competidor infM5 = Competidor.builder().nombre("Bruno").apellidos("Salas Acosta").dni("77777705E").email("bruno.salas@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2015, 8, 4))).genero('M').club("Karate Aragón").federacionAutonomica("Aragón").build();
        Competidor infM6 = Competidor.builder().nombre("Kevin").apellidos("Ruiz Chávez").dni("77777706F").email("kevin.ruiz@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2014, 5, 20))).genero('M').club("Karate Canarias").federacionAutonomica("Canarias").build();

        Competidor infF1 = Competidor.builder().nombre("Emma").apellidos("Blanco García").dni("88888801A").email("emma.blanco@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2015, 1, 15))).genero('F').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor infF2 = Competidor.builder().nombre("Olivia").apellidos("Morales Vega").dni("88888802B").email("olivia.morales@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2014, 7, 9))).genero('F').club("Karate Andalucía").federacionAutonomica("Andalucía").build();
        Competidor infF3 = Competidor.builder().nombre("Valentina").apellidos("Ríos Cano").dni("88888803C").email("valentina.rios@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2015, 11, 23))).genero('F').club("Karate Cataluña").federacionAutonomica("Cataluña").build();
        Competidor infF4 = Competidor.builder().nombre("Sara").apellidos("Torrent Mas").dni("88888804D").email("sara.torrent@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2014, 4, 6))).genero('F').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor infF5 = Competidor.builder().nombre("Chloe").apellidos("Martín Sousa").dni("88888805E").email("chloe.martin@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2015, 9, 30))).genero('F').club("Karate Galicia").federacionAutonomica("Galicia").build();

        Competidor alevM1 = Competidor.builder().nombre("Ian").apellidos("Durán Pedraza").dni("99999901A").email("ian.duran@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2017, 4, 2))).genero('M').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor alevM2 = Competidor.builder().nombre("Izan").apellidos("Moreira Soto").dni("99999902B").email("izan.moreira@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2016, 10, 16))).genero('M').club("Karate Andalucía").federacionAutonomica("Andalucía").build();
        Competidor alevM3 = Competidor.builder().nombre("Luca").apellidos("Ferreira Gomes").dni("99999903C").email("luca.ferreira@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2017, 7, 28))).genero('M').club("Karate Cataluña").federacionAutonomica("Cataluña").build();
        Competidor alevM4 = Competidor.builder().nombre("Dario").apellidos("Casas Moreno").dni("99999904D").email("dario.casas@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2016, 2, 11))).genero('M').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor alevM5 = Competidor.builder().nombre("Thiago").apellidos("Alves Costa").dni("99999905E").email("thiago.alves@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2017, 12, 5))).genero('M').club("Karate Aragón").federacionAutonomica("Aragón").build();

        Competidor alevF1 = Competidor.builder().nombre("Martina").apellidos("Iglesias Pino").dni("10101001A").email("martina.iglesias@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2017, 3, 19))).genero('F').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor alevF2 = Competidor.builder().nombre("Vera").apellidos("Giménez Ruiz").dni("10101002B").email("vera.gimenez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2016, 8, 7))).genero('F').club("Karate Sevilla").federacionAutonomica("Andalucía").build();
        Competidor alevF3 = Competidor.builder().nombre("Giulia").apellidos("Romano Pons").dni("10101003C").email("giulia.romano@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2017, 1, 24))).genero('F').club("Karate Cataluña").federacionAutonomica("Cataluña").build();
        Competidor alevF4 = Competidor.builder().nombre("Asia").apellidos("Delgado Leal").dni("10101004D").email("asia.delgado@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2016, 6, 13))).genero('F').club("Karate Canarias").federacionAutonomica("Canarias").build();

        Competidor juvM1 = Competidor.builder().nombre("Kai").apellidos("Santos Flores").dni("20202001A").email("kai.santos@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2013, 5, 6))).genero('M').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor juvM2 = Competidor.builder().nombre("Erik").apellidos("Palomar Cid").dni("20202002B").email("erik.palomar@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2012, 11, 20))).genero('M').club("Karate Cataluña").federacionAutonomica("Cataluña").build();
        Competidor juvM3 = Competidor.builder().nombre("Nil").apellidos("Ferret Compte").dni("20202003C").email("nil.ferret@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2013, 8, 14))).genero('M').club("Karate Girona").federacionAutonomica("Cataluña").build();
        Competidor juvM4 = Competidor.builder().nombre("Marc").apellidos("Puig Serra").dni("20202004D").email("marc.puig@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2012, 4, 3))).genero('M').club("Karate Baleares").federacionAutonomica("Islas Baleares").build();
        Competidor juvM5 = Competidor.builder().nombre("Noel").apellidos("Vázquez Castro").dni("20202005E").email("noel.vazquez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2013, 1, 27))).genero('M').club("Karate Galicia").federacionAutonomica("Galicia").build();
        Competidor juvM6 = Competidor.builder().nombre("Nico").apellidos("Herrero Piña").dni("20202006F").email("nico.herrero@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2012, 9, 8))).genero('M').club("Karate Castilla").federacionAutonomica("Castilla y León").build();

        Competidor juvF1 = Competidor.builder().nombre("Inés").apellidos("Mora Bernal").dni("30303001A").email("ines.mora@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2013, 4, 10))).genero('F').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor juvF2 = Competidor.builder().nombre("Ayla").apellidos("Fernández Ruiz").dni("30303002B").email("ayla.fernandez@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2012, 10, 24))).genero('F').club("Karate Andalucía").federacionAutonomica("Andalucía").build();
        Competidor juvF3 = Competidor.builder().nombre("Noa").apellidos("Rivas Llopis").dni("30303003C").email("noa.rivas@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2013, 7, 18))).genero('F').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor juvF4 = Competidor.builder().nombre("Zoe").apellidos("Andrade Lima").dni("30303004D").email("zoe.andrade@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2012, 3, 5))).genero('F').club("Karate Canarias").federacionAutonomica("Canarias").build();
        Competidor juvF5 = Competidor.builder().nombre("Hana").apellidos("Muñoz Takahashi").dni("30303005E").email("hana.munoz@karate.es").password(passwordEncoder.encode("password")).rol(Usuario.Rol.COMPETIDOR).fechaNacimiento(toDate(LocalDate.of(2013, 12, 1))).genero('F').club("Karate Aragón").federacionAutonomica("Aragón").build();
        competidorRepository.saveAll(List.of(
                senM1, senM2, senM3, senM4, senM5, senM6, senM7, senM8, senM9, senM10, senM11, senM12, senM13, senM14, senM15, senM16, senM17, senM18, senM19, senM20, senM21, senM22, senM23, senM24, senF1, senF2, senF3, senF4, senF5, senF6, senF7, senF8, senF9, senF10, senF11, senF12, senF13, senF14, senF15, senF16, senF17, senF18, senF19, senF20, senF21, senF22, senF23, senF24, senF25, senF26,
                cadM1, cadM2, cadM3, cadM4, cadM5, cadM6, cadM7, cadM8, cadF1, cadF2, cadF3, cadF4, cadF5, cadF6, cadF7, cadF8, cadF9, cadF10, cadF11, cadF12,
                junM1, junM2, junM3, junM4, junM5, junM6, junM7, junM8, junF1, junF2, junF3, junF4, junF5, junF6,
                sub21M1, sub21M2, sub21M3, sub21M4, sub21M5, sub21M6, sub21M7, sub21M8, sub21F1, sub21F2, sub21F3, sub21F4, sub21F5, sub21F6,
                infM1, infM2, infM3, infM4, infM5, infM6, infF1, infF2, infF3, infF4, infF5,
                alevM1, alevM2, alevM3, alevM4, alevM5, alevF1, alevF2, alevF3, alevF4,
                juvM1, juvM2, juvM3, juvM4, juvM5, juvM6, juvF1, juvF2, juvF3, juvF4, juvF5
        ));

        // INSCRIPCIONES
        List<Inscripcion> inscripciones = new ArrayList<>();

        // --- CAMP2: Campeonato España Senior ---
        inscripciones.add(ins(camp2, catSenKatM, senM1)); inscripciones.add(ins(camp2, catSenKatM, senM2)); inscripciones.add(ins(camp2, catSenKatM, senM3)); inscripciones.add(ins(camp2, catSenKatM, senM4)); inscripciones.add(ins(camp2, catSenKatM, senM5)); inscripciones.add(ins(camp2, catSenKatM, senM6));
        inscripciones.add(ins(camp2, catSenKatM, senM7));inscripciones.add(ins(camp2, catSenKatF, senF1)); inscripciones.add(ins(camp2, catSenKatF, senF2)); inscripciones.add(ins(camp2, catSenKatF, senF3)); inscripciones.add(ins(camp2, catSenKatF, senF4)); inscripciones.add(ins(camp2, catSenKatF, senF5)); inscripciones.add(ins(camp2, catSenKatF, senF6));
        inscripciones.add(ins(camp2, catSenKumM2, senM8)); inscripciones.add(ins(camp2, catSenKumM2, senM9)); inscripciones.add(ins(camp2, catSenKumM2, senM10)); inscripciones.add(ins(camp2, catSenKumM2, senM11)); inscripciones.add(ins(camp2, catSenKumM2, senM12)); inscripciones.add(ins(camp2, catSenKumM2, senM13));
        inscripciones.add(ins(camp2, catSenKumM3, senM14)); inscripciones.add(ins(camp2, catSenKumM3, senM15)); inscripciones.add(ins(camp2, catSenKumM3, senM16)); inscripciones.add(ins(camp2, catSenKumM3, senM17)); inscripciones.add(ins(camp2, catSenKumM3, senM18)); inscripciones.add(ins(camp2, catSenKumM3, senM19));
        inscripciones.add(ins(camp2, catSenKumM4, senM20)); inscripciones.add(ins(camp2, catSenKumM4, senM21)); inscripciones.add(ins(camp2, catSenKumM4, senM22)); inscripciones.add(ins(camp2, catSenKumM4, senM23)); inscripciones.add(ins(camp2, catSenKumM4, senM24));
        inscripciones.add(ins(camp2, catSenKumF2, senF7)); inscripciones.add(ins(camp2, catSenKumF2, senF8)); inscripciones.add(ins(camp2, catSenKumF2, senF9)); inscripciones.add(ins(camp2, catSenKumF2, senF10)); inscripciones.add(ins(camp2, catSenKumF2, senF11)); inscripciones.add(ins(camp2, catSenKumF2, senF12));
        inscripciones.add(ins(camp2, catSenKumF3, senF13)); inscripciones.add(ins(camp2, catSenKumF3, senF14)); inscripciones.add(ins(camp2, catSenKumF3, senF15)); inscripciones.add(ins(camp2, catSenKumF3, senF16)); inscripciones.add(ins(camp2, catSenKumF3, senF17)); inscripciones.add(ins(camp2, catSenKumF3, senF18));

        // --- CAMP4: Campeonato Andalucía Senior ---
        inscripciones.add(ins(camp4, catSenKatM, senM1)); inscripciones.add(ins(camp4, catSenKatM, senM3)); inscripciones.add(ins(camp4, catSenKatM, senM5)); inscripciones.add(ins(camp4, catSenKatM, senM7)); inscripciones.add(ins(camp4, catSenKatM, senM9)); inscripciones.add(ins(camp4, catSenKatM, senM11)); inscripciones.add(ins(camp4, catSenKatM, senM13));
        inscripciones.add(ins(camp4, catSenKatF, senF1)); inscripciones.add(ins(camp4, catSenKatF, senF3)); inscripciones.add(ins(camp4, catSenKatF, senF5)); inscripciones.add(ins(camp4, catSenKatF, senF7)); inscripciones.add(ins(camp4, catSenKatF, senF9)); inscripciones.add(ins(camp4, catSenKatF, senF11));
        inscripciones.add(ins(camp4, catSenKumM3, senM2)); inscripciones.add(ins(camp4, catSenKumM3, senM4)); inscripciones.add(ins(camp4, catSenKumM3, senM6)); inscripciones.add(ins(camp4, catSenKumM3, senM8));inscripciones.add(ins(camp4, catSenKumM3, senM10)); inscripciones.add(ins(camp4, catSenKumM3, senM12));
        inscripciones.add(ins(camp4, catSenKumF3, senF2)); inscripciones.add(ins(camp4, catSenKumF3, senF4)); inscripciones.add(ins(camp4, catSenKumF3, senF6)); inscripciones.add(ins(camp4, catSenKumF3, senF8)); inscripciones.add(ins(camp4, catSenKumF3, senF10)); inscripciones.add(ins(camp4, catSenKumF3, senF12));

        // --- CAMP15: Liga Nacional Base Masculina ---
        inscripciones.add(ins(camp15, catCadKataM, cadM1)); inscripciones.add(ins(camp15, catCadKataM, cadM2));
        inscripciones.add(ins(camp15, catCadKataM, cadM3)); inscripciones.add(ins(camp15, catCadKataM, cadM4));
        inscripciones.add(ins(camp15, catCadKataM, cadM5)); inscripciones.add(ins(camp15, catCadKataM, cadM6));
        inscripciones.add(ins(camp15, catCadKumM2, cadM7)); inscripciones.add(ins(camp15, catCadKumM2, cadM8));
        inscripciones.add(ins(camp15, catCadKumM2, cadM1)); inscripciones.add(ins(camp15, catCadKumM2, cadM3));
        inscripciones.add(ins(camp15, catCadKumM2, cadM5));
        inscripciones.add(ins(camp15, catSenKumM3, senM1)); inscripciones.add(ins(camp15, catSenKumM3, senM3));
        inscripciones.add(ins(camp15, catSenKumM3, senM5)); inscripciones.add(ins(camp15, catSenKumM3, senM7));
        inscripciones.add(ins(camp15, catSenKumM3, senM9)); inscripciones.add(ins(camp15, catSenKumM3, senM11));

        // --- CAMP16: Liga Iberdrola Base Femenina ---
        inscripciones.add(ins(camp16, catCadKataF, cadF1)); inscripciones.add(ins(camp16, catCadKataF, cadF2));
        inscripciones.add(ins(camp16, catCadKataF, cadF3)); inscripciones.add(ins(camp16, catCadKataF, cadF4));
        inscripciones.add(ins(camp16, catCadKataF, cadF5)); inscripciones.add(ins(camp16, catCadKataF, cadF6));
        inscripciones.add(ins(camp16, catCadKumF2, cadF1)); inscripciones.add(ins(camp16, catCadKumF2, cadF2));
        inscripciones.add(ins(camp16, catCadKumF2, cadF4)); inscripciones.add(ins(camp16, catCadKumF2, cadF5));
        inscripciones.add(ins(camp16, catSenKumF2, senF1)); inscripciones.add(ins(camp16, catSenKumF2, senF3));
        inscripciones.add(ins(camp16, catSenKumF2, senF5)); inscripciones.add(ins(camp16, catSenKumF2, senF7));
        inscripciones.add(ins(camp16, catSenKumF2, senF9)); inscripciones.add(ins(camp16, catSenKumF2, senF11));

        // --- CAMP17: Liga Nacional Base Masculina Alevín/Infantil/Juvenil ---
        inscripciones.add(ins(camp17, catAlevKataM, alevM1)); inscripciones.add(ins(camp17, catAlevKataM, alevM2));
        inscripciones.add(ins(camp17, catAlevKataM, alevM3)); inscripciones.add(ins(camp17, catAlevKataM, alevM4));
        inscripciones.add(ins(camp17, catAlevKataM, alevM5));
        inscripciones.add(ins(camp17, catInfKataM, infM1)); inscripciones.add(ins(camp17, catInfKataM, infM2));
        inscripciones.add(ins(camp17, catInfKataM, infM3)); inscripciones.add(ins(camp17, catInfKataM, infM4));
        inscripciones.add(ins(camp17, catInfKataM, infM5)); inscripciones.add(ins(camp17, catInfKataM, infM6));
        inscripciones.add(ins(camp17, catJuvKataM, juvM1)); inscripciones.add(ins(camp17, catJuvKataM, juvM2));
        inscripciones.add(ins(camp17, catJuvKataM, juvM3)); inscripciones.add(ins(camp17, catJuvKataM, juvM4));
        inscripciones.add(ins(camp17, catJuvKataM, juvM5)); inscripciones.add(ins(camp17, catJuvKataM, juvM6));
        inscripciones.add(ins(camp17, catJuvKumM2, juvM1)); inscripciones.add(ins(camp17, catJuvKumM2, juvM3));
        inscripciones.add(ins(camp17, catJuvKumM2, juvM5)); inscripciones.add(ins(camp17, catJuvKumM2, juvM2));
        inscripciones.add(ins(camp17, catJuvKumM2, juvM4));

        // --- CAMP18: Liga Iberdrola Base Femenina Alevín/Infantil/Juvenil ---
        inscripciones.add(ins(camp18, catAlevKataF, alevF1)); inscripciones.add(ins(camp18, catAlevKataF, alevF2));
        inscripciones.add(ins(camp18, catAlevKataF, alevF3)); inscripciones.add(ins(camp18, catAlevKataF, alevF4));
        inscripciones.add(ins(camp18, catInfKataF, infF1)); inscripciones.add(ins(camp18, catInfKataF, infF2));
        inscripciones.add(ins(camp18, catInfKataF, infF3)); inscripciones.add(ins(camp18, catInfKataF, infF4));
        inscripciones.add(ins(camp18, catInfKataF, infF5));
        inscripciones.add(ins(camp18, catJuvKataF, juvF1)); inscripciones.add(ins(camp18, catJuvKataF, juvF2));
        inscripciones.add(ins(camp18, catJuvKataF, juvF3)); inscripciones.add(ins(camp18, catJuvKataF, juvF4));
        inscripciones.add(ins(camp18, catJuvKataF, juvF5));
        inscripciones.add(ins(camp18, catJuvKumF2, juvF1)); inscripciones.add(ins(camp18, catJuvKumF2, juvF2));
        inscripciones.add(ins(camp18, catJuvKumF2, juvF3)); inscripciones.add(ins(camp18, catJuvKumF2, juvF4));

        // --- CAMP3: Campeonato Andalucía Base ---
        inscripciones.add(ins(camp3, catAlevKataM, alevM1)); inscripciones.add(ins(camp3, catAlevKataM, alevM2));
        inscripciones.add(ins(camp3, catAlevKataM, alevM3)); inscripciones.add(ins(camp3, catAlevKataM, alevM4));
        inscripciones.add(ins(camp3, catAlevKataM, alevM5));
        inscripciones.add(ins(camp3, catAlevKataF, alevF1)); inscripciones.add(ins(camp3, catAlevKataF, alevF2));
        inscripciones.add(ins(camp3, catAlevKataF, alevF3)); inscripciones.add(ins(camp3, catAlevKataF, alevF4));
        inscripciones.add(ins(camp3, catInfKataM, infM1)); inscripciones.add(ins(camp3, catInfKataM, infM2));
        inscripciones.add(ins(camp3, catInfKataM, infM3)); inscripciones.add(ins(camp3, catInfKataM, infM4));
        inscripciones.add(ins(camp3, catInfKataM, infM5)); inscripciones.add(ins(camp3, catInfKataM, infM6));
        inscripciones.add(ins(camp3, catInfKataF, infF1)); inscripciones.add(ins(camp3, catInfKataF, infF2));
        inscripciones.add(ins(camp3, catInfKataF, infF3)); inscripciones.add(ins(camp3, catInfKataF, infF4));
        inscripciones.add(ins(camp3, catInfKataF, infF5));
        inscripciones.add(ins(camp3, catJuvKataM, juvM1)); inscripciones.add(ins(camp3, catJuvKataM, juvM2));
        inscripciones.add(ins(camp3, catJuvKataM, juvM3)); inscripciones.add(ins(camp3, catJuvKataM, juvM4));
        inscripciones.add(ins(camp3, catJuvKataM, juvM5)); inscripciones.add(ins(camp3, catJuvKataM, juvM6));
        inscripciones.add(ins(camp3, catJuvKataF, juvF1)); inscripciones.add(ins(camp3, catJuvKataF, juvF2));
        inscripciones.add(ins(camp3, catJuvKataF, juvF3)); inscripciones.add(ins(camp3, catJuvKataF, juvF4));
        inscripciones.add(ins(camp3, catJuvKataF, juvF5));

        // --- CAMP6: Liga Nacional Cadete/Junior/Sub21 ---
        inscripciones.add(ins(camp6, catCadKataM, cadM1)); inscripciones.add(ins(camp6, catCadKataM, cadM2));
        inscripciones.add(ins(camp6, catCadKataM, cadM3)); inscripciones.add(ins(camp6, catCadKataM, cadM4));
        inscripciones.add(ins(camp6, catCadKataM, cadM5)); inscripciones.add(ins(camp6, catCadKataM, cadM6));
        inscripciones.add(ins(camp6, catCadKataF, cadF1)); inscripciones.add(ins(camp6, catCadKataF, cadF2));
        inscripciones.add(ins(camp6, catCadKataF, cadF3)); inscripciones.add(ins(camp6, catCadKataF, cadF4));
        inscripciones.add(ins(camp6, catCadKataF, cadF5)); inscripciones.add(ins(camp6, catCadKataF, cadF6));
        inscripciones.add(ins(camp6, catJunKatM, junM1)); inscripciones.add(ins(camp6, catJunKatM, junM2));
        inscripciones.add(ins(camp6, catJunKatM, junM3)); inscripciones.add(ins(camp6, catJunKatM, junM4));
        inscripciones.add(ins(camp6, catJunKatM, junM5)); inscripciones.add(ins(camp6, catJunKatM, junM6));
        inscripciones.add(ins(camp6, catJunKatF, junF1)); inscripciones.add(ins(camp6, catJunKatF, junF2));
        inscripciones.add(ins(camp6, catJunKatF, junF3)); inscripciones.add(ins(camp6, catJunKatF, junF4));
        inscripciones.add(ins(camp6, catJunKatF, junF5)); inscripciones.add(ins(camp6, catJunKatF, junF6));
        inscripciones.add(ins(camp6, catJunKumM2, junM7)); inscripciones.add(ins(camp6, catJunKumM2, junM8));
        inscripciones.add(ins(camp6, catJunKumM2, junM1)); inscripciones.add(ins(camp6, catJunKumM2, junM3));
        inscripciones.add(ins(camp6, catJunKumM2, junM5));
        inscripciones.add(ins(camp6, catSub21KatM, sub21M1)); inscripciones.add(ins(camp6, catSub21KatM, sub21M2));
        inscripciones.add(ins(camp6, catSub21KatM, sub21M3)); inscripciones.add(ins(camp6, catSub21KatM, sub21M4));
        inscripciones.add(ins(camp6, catSub21KatM, sub21M5)); inscripciones.add(ins(camp6, catSub21KatM, sub21M6));
        inscripciones.add(ins(camp6, catSub21KatF, sub21F1)); inscripciones.add(ins(camp6, catSub21KatF, sub21F2));
        inscripciones.add(ins(camp6, catSub21KatF, sub21F3)); inscripciones.add(ins(camp6, catSub21KatF, sub21F4));
        inscripciones.add(ins(camp6, catSub21KatF, sub21F5)); inscripciones.add(ins(camp6, catSub21KatF, sub21F6));

        // --- CAMP11: Campeonato España Absoluto ---
        inscripciones.add(ins(camp11, catSenKatM, senM2)); inscripciones.add(ins(camp11, catSenKatM, senM4));
        inscripciones.add(ins(camp11, catSenKatM, senM6)); inscripciones.add(ins(camp11, catSenKatM, senM8));
        inscripciones.add(ins(camp11, catSenKatM, senM10)); inscripciones.add(ins(camp11, catSenKatM, senM12));
        inscripciones.add(ins(camp11, catSenKatM, senM14));
        inscripciones.add(ins(camp11, catSenKatF, senF2)); inscripciones.add(ins(camp11, catSenKatF, senF4));
        inscripciones.add(ins(camp11, catSenKatF, senF6)); inscripciones.add(ins(camp11, catSenKatF, senF8));
        inscripciones.add(ins(camp11, catSenKatF, senF10)); inscripciones.add(ins(camp11, catSenKatF, senF12));
        inscripciones.add(ins(camp11, catSenKumM3, senM16)); inscripciones.add(ins(camp11, catSenKumM3, senM17));
        inscripciones.add(ins(camp11, catSenKumM3, senM18)); inscripciones.add(ins(camp11, catSenKumM3, senM19));
        inscripciones.add(ins(camp11, catSenKumM3, senM20)); inscripciones.add(ins(camp11, catSenKumM3, senM21));
        inscripciones.add(ins(camp11, catSenKumF3, senF13)); inscripciones.add(ins(camp11, catSenKumF3, senF14));
        inscripciones.add(ins(camp11, catSenKumF3, senF15)); inscripciones.add(ins(camp11, catSenKumF3, senF16));
        inscripciones.add(ins(camp11, catSenKumF3, senF17)); inscripciones.add(ins(camp11, catSenKumF3, senF18));

        // --- CAMP21: Liga Iberdrola Junior/Sub21 ---
        inscripciones.add(ins(camp21, catJunKatM, junM1)); inscripciones.add(ins(camp21, catJunKatM, junM2));
        inscripciones.add(ins(camp21, catJunKatM, junM3)); inscripciones.add(ins(camp21, catJunKatM, junM4));
        inscripciones.add(ins(camp21, catJunKatM, junM5)); inscripciones.add(ins(camp21, catJunKatM, junM6));
        inscripciones.add(ins(camp21, catJunKatF, junF1)); inscripciones.add(ins(camp21, catJunKatF, junF2));
        inscripciones.add(ins(camp21, catJunKatF, junF3)); inscripciones.add(ins(camp21, catJunKatF, junF4));
        inscripciones.add(ins(camp21, catJunKatF, junF5)); inscripciones.add(ins(camp21, catJunKatF, junF6));
        inscripciones.add(ins(camp21, catSub21KatM, sub21M1)); inscripciones.add(ins(camp21, catSub21KatM, sub21M2));
        inscripciones.add(ins(camp21, catSub21KatM, sub21M3)); inscripciones.add(ins(camp21, catSub21KatM, sub21M4));
        inscripciones.add(ins(camp21, catSub21KatM, sub21M5)); inscripciones.add(ins(camp21, catSub21KatM, sub21M6));
        inscripciones.add(ins(camp21, catSub21KumM2, sub21M7)); inscripciones.add(ins(camp21, catSub21KumM2, sub21M8));
        inscripciones.add(ins(camp21, catSub21KumM2, sub21M1)); inscripciones.add(ins(camp21, catSub21KumM2, sub21M3));
        inscripciones.add(ins(camp21, catSub21KumM2, sub21M5));

        // --- CAMP22: Liga Iberdrola Junior/Sub21 Femenina ---
        inscripciones.add(ins(camp22, catJunKatF, junF1)); inscripciones.add(ins(camp22, catJunKatF, junF2));
        inscripciones.add(ins(camp22, catJunKatF, junF3)); inscripciones.add(ins(camp22, catJunKatF, junF4));
        inscripciones.add(ins(camp22, catJunKatF, junF5)); inscripciones.add(ins(camp22, catJunKatF, junF6));
        inscripciones.add(ins(camp22, catJunKumF2, junF1)); inscripciones.add(ins(camp22, catJunKumF2, junF2));
        inscripciones.add(ins(camp22, catJunKumF2, junF3)); inscripciones.add(ins(camp22, catJunKumF2, junF5));
        inscripciones.add(ins(camp22, catSub21KatF, sub21F1)); inscripciones.add(ins(camp22, catSub21KatF, sub21F2));
        inscripciones.add(ins(camp22, catSub21KatF, sub21F3)); inscripciones.add(ins(camp22, catSub21KatF, sub21F4));
        inscripciones.add(ins(camp22, catSub21KatF, sub21F5)); inscripciones.add(ins(camp22, catSub21KatF, sub21F6));
        inscripciones.add(ins(camp22, catSub21KumF2, sub21F1)); inscripciones.add(ins(camp22, catSub21KumF2, sub21F2));
        inscripciones.add(ins(camp22, catSub21KumF2, sub21F4)); inscripciones.add(ins(camp22, catSub21KumF2, sub21F6));

        // --- CAMP7: Campeonato España Master ---
        inscripciones.add(ins(camp7, catSenKatM, senM1)); inscripciones.add(ins(camp7, catSenKatM, senM5));
        inscripciones.add(ins(camp7, catSenKatM, senM9)); inscripciones.add(ins(camp7, catSenKatM, senM13));
        inscripciones.add(ins(camp7, catSenKatM, senM17)); inscripciones.add(ins(camp7, catSenKatM, senM21));
        inscripciones.add(ins(camp7, catSenKatF, senF1)); inscripciones.add(ins(camp7, catSenKatF, senF5));
        inscripciones.add(ins(camp7, catSenKatF, senF9)); inscripciones.add(ins(camp7, catSenKatF, senF13));
        inscripciones.add(ins(camp7, catSenKatF, senF17));
        inscripciones.add(ins(camp7, catSenKumM4, senM2)); inscripciones.add(ins(camp7, catSenKumM4, senM6));
        inscripciones.add(ins(camp7, catSenKumM4, senM10)); inscripciones.add(ins(camp7, catSenKumM4, senM14));
        inscripciones.add(ins(camp7, catSenKumM4, senM18)); inscripciones.add(ins(camp7, catSenKumM4, senM22));
        inscripciones.add(ins(camp7, catSenKumF4, senF2)); inscripciones.add(ins(camp7, catSenKumF4, senF6));
        inscripciones.add(ins(camp7, catSenKumF4, senF10)); inscripciones.add(ins(camp7, catSenKumF4, senF14));
        inscripciones.add(ins(camp7, catSenKumF4, senF18));

        // --- CAMP8: Campeonato España Universitario ---
        inscripciones.add(ins(camp8, catSub21KatM, sub21M1)); inscripciones.add(ins(camp8, catSub21KatM, sub21M2));
        inscripciones.add(ins(camp8, catSub21KatM, sub21M3)); inscripciones.add(ins(camp8, catSub21KatM, sub21M4));
        inscripciones.add(ins(camp8, catSub21KatM, sub21M5)); inscripciones.add(ins(camp8, catSub21KatM, sub21M6));
        inscripciones.add(ins(camp8, catSub21KatF, sub21F1)); inscripciones.add(ins(camp8, catSub21KatF, sub21F2));
        inscripciones.add(ins(camp8, catSub21KatF, sub21F3)); inscripciones.add(ins(camp8, catSub21KatF, sub21F4));
        inscripciones.add(ins(camp8, catSub21KatF, sub21F5));
        inscripciones.add(ins(camp8, catSenKatM, senM3)); inscripciones.add(ins(camp8, catSenKatM, senM7));
        inscripciones.add(ins(camp8, catSenKatM, senM11)); inscripciones.add(ins(camp8, catSenKatM, senM15));
        inscripciones.add(ins(camp8, catSenKatM, senM19)); inscripciones.add(ins(camp8, catSenKatM, senM23));
        inscripciones.add(ins(camp8, catSenKatF, senF3)); inscripciones.add(ins(camp8, catSenKatF, senF7));
        inscripciones.add(ins(camp8, catSenKatF, senF11)); inscripciones.add(ins(camp8, catSenKatF, senF15));

        // --- CAMP9: Campeonato España Infantil ---
        inscripciones.add(ins(camp9, catInfKataM, infM1)); inscripciones.add(ins(camp9, catInfKataM, infM2));
        inscripciones.add(ins(camp9, catInfKataM, infM3)); inscripciones.add(ins(camp9, catInfKataM, infM4));
        inscripciones.add(ins(camp9, catInfKataM, infM5)); inscripciones.add(ins(camp9, catInfKataM, infM6));
        inscripciones.add(ins(camp9, catInfKataF, infF1)); inscripciones.add(ins(camp9, catInfKataF, infF2));
        inscripciones.add(ins(camp9, catInfKataF, infF3)); inscripciones.add(ins(camp9, catInfKataF, infF4));
        inscripciones.add(ins(camp9, catInfKataF, infF5));
        inscripciones.add(ins(camp9, catInfKumM2, infM1)); inscripciones.add(ins(camp9, catInfKumM2, infM2));
        inscripciones.add(ins(camp9, catInfKumM2, infM3)); inscripciones.add(ins(camp9, catInfKumM2, infM5));
        inscripciones.add(ins(camp9, catInfKumF2, infF1)); inscripciones.add(ins(camp9, catInfKumF2, infF2));
        inscripciones.add(ins(camp9, catInfKumF2, infF3)); inscripciones.add(ins(camp9, catInfKumF2, infF4));

        // --- CAMP5: Liga Iberdrola Cadete/Junior/Sub21 ---
        inscripciones.add(ins(camp5, catSub21KatM, sub21M1)); inscripciones.add(ins(camp5, catSub21KatM, sub21M2));
        inscripciones.add(ins(camp5, catSub21KatM, sub21M3)); inscripciones.add(ins(camp5, catSub21KatM, sub21M4));
        inscripciones.add(ins(camp5, catSub21KatM, sub21M5)); inscripciones.add(ins(camp5, catSub21KatM, sub21M6));
        inscripciones.add(ins(camp5, catSub21KatM, sub21M7)); inscripciones.add(ins(camp5, catSub21KatM, sub21M8));
        inscripciones.add(ins(camp5, catSub21KatF, sub21F1)); inscripciones.add(ins(camp5, catSub21KatF, sub21F2));
        inscripciones.add(ins(camp5, catSub21KatF, sub21F3)); inscripciones.add(ins(camp5, catSub21KatF, sub21F4));
        inscripciones.add(ins(camp5, catSub21KatF, sub21F5)); inscripciones.add(ins(camp5, catSub21KatF, sub21F6));
        inscripciones.add(ins(camp5, catSenKatM, senM4)); inscripciones.add(ins(camp5, catSenKatM, senM8));
        inscripciones.add(ins(camp5, catSenKatM, senM12)); inscripciones.add(ins(camp5, catSenKatM, senM16));
        inscripciones.add(ins(camp5, catSenKatM, senM20)); inscripciones.add(ins(camp5, catSenKatM, senM24));
        inscripciones.add(ins(camp5, catSenKatF, senF4)); inscripciones.add(ins(camp5, catSenKatF, senF8));
        inscripciones.add(ins(camp5, catSenKatF, senF12)); inscripciones.add(ins(camp5, catSenKatF, senF16));

        // --- CAMP10: Campeonato España Absoluto ---
        inscripciones.add(ins(camp10, catSenKatM, senM1)); inscripciones.add(ins(camp10, catSenKatM, senM5));
        inscripciones.add(ins(camp10, catSenKatM, senM9)); inscripciones.add(ins(camp10, catSenKatM, senM13));
        inscripciones.add(ins(camp10, catSenKatM, senM17)); inscripciones.add(ins(camp10, catSenKatM, senM21));
        inscripciones.add(ins(camp10, catSenKatF, senF1)); inscripciones.add(ins(camp10, catSenKatF, senF5));
        inscripciones.add(ins(camp10, catSenKatF, senF9)); inscripciones.add(ins(camp10, catSenKatF, senF13));
        inscripciones.add(ins(camp10, catJunKatM, junM1)); inscripciones.add(ins(camp10, catJunKatM, junM3));
        inscripciones.add(ins(camp10, catJunKatM, junM5)); inscripciones.add(ins(camp10, catJunKatM, junM7));
        inscripciones.add(ins(camp10, catJunKatF, junF1)); inscripciones.add(ins(camp10, catJunKatF, junF3));
        inscripciones.add(ins(camp10, catJunKatF, junF5));
        inscripciones.add(ins(camp10, catCadKataM, cadM1)); inscripciones.add(ins(camp10, catCadKataM, cadM3));
        inscripciones.add(ins(camp10, catCadKataM, cadM5)); inscripciones.add(ins(camp10, catCadKataM, cadM7));
        inscripciones.add(ins(camp10, catCadKataF, cadF1)); inscripciones.add(ins(camp10, catCadKataF, cadF3));
        inscripciones.add(ins(camp10, catCadKataF, cadF5));
        inscripciones.add(ins(camp10, catInfKataM, infM1)); inscripciones.add(ins(camp10, catInfKataM, infM3));
        inscripciones.add(ins(camp10, catInfKataM, infM5));
        inscripciones.add(ins(camp10, catInfKataF, infF1)); inscripciones.add(ins(camp10, catInfKataF, infF3));
        inscripciones.add(ins(camp10, catInfKataF, infF5));
        inscripciones.add(ins(camp10, catJuvKataM, juvM1)); inscripciones.add(ins(camp10, catJuvKataM, juvM3));
        inscripciones.add(ins(camp10, catJuvKataM, juvM5));
        inscripciones.add(ins(camp10, catJuvKataF, juvF1)); inscripciones.add(ins(camp10, catJuvKataF, juvF3));
        inscripciones.add(ins(camp10, catJuvKataF, juvF5));

        // --- CAMP1: Campeonato España Cadete/Junior/Sub21 ---
        inscripciones.add(ins(camp1, catCadKataM, cadM1)); inscripciones.add(ins(camp1, catCadKataM, cadM2));
        inscripciones.add(ins(camp1, catCadKataM, cadM3)); inscripciones.add(ins(camp1, catCadKataM, cadM4));
        inscripciones.add(ins(camp1, catCadKataM, cadM5)); inscripciones.add(ins(camp1, catCadKataM, cadM6));
        inscripciones.add(ins(camp1, catCadKataM, cadM7)); inscripciones.add(ins(camp1, catCadKataM, cadM8));
        inscripciones.add(ins(camp1, catCadKataF, cadF1)); inscripciones.add(ins(camp1, catCadKataF, cadF2));
        inscripciones.add(ins(camp1, catCadKataF, cadF3)); inscripciones.add(ins(camp1, catCadKataF, cadF4));
        inscripciones.add(ins(camp1, catCadKataF, cadF5)); inscripciones.add(ins(camp1, catCadKataF, cadF6));
        inscripciones.add(ins(camp1, catJunKatM, junM1)); inscripciones.add(ins(camp1, catJunKatM, junM2));
        inscripciones.add(ins(camp1, catJunKatM, junM3)); inscripciones.add(ins(camp1, catJunKatM, junM4));
        inscripciones.add(ins(camp1, catJunKatM, junM5)); inscripciones.add(ins(camp1, catJunKatM, junM6));
        inscripciones.add(ins(camp1, catJunKatM, junM7)); inscripciones.add(ins(camp1, catJunKatM, junM8));
        inscripciones.add(ins(camp1, catJunKatF, junF1)); inscripciones.add(ins(camp1, catJunKatF, junF2));
        inscripciones.add(ins(camp1, catJunKatF, junF3)); inscripciones.add(ins(camp1, catJunKatF, junF4));
        inscripciones.add(ins(camp1, catJunKatF, junF5)); inscripciones.add(ins(camp1, catJunKatF, junF6));
        inscripciones.add(ins(camp1, catSub21KatM, sub21M1)); inscripciones.add(ins(camp1, catSub21KatM, sub21M2));
        inscripciones.add(ins(camp1, catSub21KatM, sub21M3)); inscripciones.add(ins(camp1, catSub21KatM, sub21M4));
        inscripciones.add(ins(camp1, catSub21KatM, sub21M5)); inscripciones.add(ins(camp1, catSub21KatM, sub21M6));
        inscripciones.add(ins(camp1, catSub21KatM, sub21M7)); inscripciones.add(ins(camp1, catSub21KatM, sub21M8));
        inscripciones.add(ins(camp1, catSub21KatF, sub21F1)); inscripciones.add(ins(camp1, catSub21KatF, sub21F2));
        inscripciones.add(ins(camp1, catSub21KatF, sub21F3)); inscripciones.add(ins(camp1, catSub21KatF, sub21F4));
        inscripciones.add(ins(camp1, catSub21KatF, sub21F5)); inscripciones.add(ins(camp1, catSub21KatF, sub21F6));
        inscripciones.add(ins(camp1, catJunKumM2, junM1)); inscripciones.add(ins(camp1, catJunKumM2, junM3));
        inscripciones.add(ins(camp1, catJunKumM2, junM5)); inscripciones.add(ins(camp1, catJunKumM2, junM7));
        inscripciones.add(ins(camp1, catJunKumM3, junM2)); inscripciones.add(ins(camp1, catJunKumM3, junM4));
        inscripciones.add(ins(camp1, catJunKumM3, junM6)); inscripciones.add(ins(camp1, catJunKumM3, junM8));
        inscripciones.add(ins(camp1, catSub21KumM2, sub21M1)); inscripciones.add(ins(camp1, catSub21KumM2, sub21M3));
        inscripciones.add(ins(camp1, catSub21KumM2, sub21M5)); inscripciones.add(ins(camp1, catSub21KumM2, sub21M7));
        inscripciones.add(ins(camp1, catSub21KumF2, sub21F1)); inscripciones.add(ins(camp1, catSub21KumF2, sub21F3));
        inscripciones.add(ins(camp1, catSub21KumF2, sub21F5));

        inscripciones.add(ins(camp14, catCadKataF, cadF2)); inscripciones.add(ins(camp14, catCadKataF, cadF4));
        inscripciones.add(ins(camp14, catCadKataF, cadF6)); inscripciones.add(ins(camp14, catCadKataF, cadF1));
        inscripciones.add(ins(camp14, catCadKataF, cadF3));
// --- CAMP14: Liga Iberdrola Absoluta/Cadete - 1ª Ronda 2026 ---
// Cadete Femenino Kata (ya tiene 5, añadimos 2 más para tener 7)
        inscripciones.add(ins(camp14, catCadKataF, cadF1)); inscripciones.add(ins(camp14, catCadKataF, cadF2));
        inscripciones.add(ins(camp14, catCadKataF, cadF3)); inscripciones.add(ins(camp14, catCadKataF, cadF4));
        inscripciones.add(ins(camp14, catCadKataF, cadF6)); inscripciones.add(ins(camp14, catCadKataF, cadF7));
        inscripciones.add(ins(camp14, catCadKataF, cadF8));

// Cadete Femenino Kumite <47kg
        inscripciones.add(ins(camp14, catCadKumF1, cadF1)); inscripciones.add(ins(camp14, catCadKumF1, cadF3));
        inscripciones.add(ins(camp14, catCadKumF1, cadF9)); inscripciones.add(ins(camp14, catCadKumF1, cadF10));

// Cadete Femenino Kumite <54kg
        inscripciones.add(ins(camp14, catCadKumF2, cadF2)); inscripciones.add(ins(camp14, catCadKumF2, cadF4));
        inscripciones.add(ins(camp14, catCadKumF2, cadF11)); inscripciones.add(ins(camp14, catCadKumF2, cadF12)); inscripciones.add(ins(camp14, catCadKumF3, cadF5)); inscripciones.add(ins(camp14, catCadKumF3, cadF6));
        inscripciones.add(ins(camp14, catCadKumF3, cadF7)); inscripciones.add(ins(camp14, catCadKumF3, cadF8)); inscripciones.add(ins(camp14, catCadKumF4, cadF9)); inscripciones.add(ins(camp14, catCadKumF4, cadF10)); inscripciones.add(ins(camp14, catCadKumF4, cadF11)); inscripciones.add(ins(camp14, catCadKumF4, cadF12));
        inscripciones.add(ins(camp14, catSenKatF, senF1));  inscripciones.add(ins(camp14, catSenKatF, senF2)); inscripciones.add(ins(camp14, catSenKatF, senF3));  inscripciones.add(ins(camp14, catSenKatF, senF4)); inscripciones.add(ins(camp14, catSenKatF, senF19)); inscripciones.add(ins(camp14, catSenKatF, senF20));
        inscripciones.add(ins(camp14, catSenKumF1, senF5));  inscripciones.add(ins(camp14, catSenKumF1, senF6)); inscripciones.add(ins(camp14, catSenKumF1, senF21)); inscripciones.add(ins(camp14, catSenKumF1, senF22)); inscripciones.add(ins(camp14, catSenKumF2, senF7));  inscripciones.add(ins(camp14, catSenKumF2, senF8));
        inscripciones.add(ins(camp14, catSenKumF2, senF23)); inscripciones.add(ins(camp14, catSenKumF2, senF24)); inscripciones.add(ins(camp14, catSenKumF3, senF9));  inscripciones.add(ins(camp14, catSenKumF3, senF10)); inscripciones.add(ins(camp14, catSenKumF3, senF25)); inscripciones.add(ins(camp14, catSenKumF3, senF26));
        inscripciones.add(ins(camp14, catSenKumF4, senF11)); inscripciones.add(ins(camp14, catSenKumF4, senF12)); inscripciones.add(ins(camp14, catSenKumF4, senF19)); inscripciones.add(ins(camp14, catSenKumF4, senF20)); inscripciones.add(ins(camp14, catSenKumF5, senF13)); inscripciones.add(ins(camp14, catSenKumF5, senF14));
        inscripciones.add(ins(camp14, catSenKumF5, senF21)); inscripciones.add(ins(camp14, catSenKumF5, senF22));

        // --- CAMP13: Liga Master ---
        inscripciones.add(ins(camp13, catSenKatM, senM4)); inscripciones.add(ins(camp13, catSenKatM, senM8));
        inscripciones.add(ins(camp13, catSenKatM, senM12)); inscripciones.add(ins(camp13, catSenKatM, senM16));
        inscripciones.add(ins(camp13, catSenKatM, senM20)); inscripciones.add(ins(camp13, catSenKatM, senM24));
        inscripciones.add(ins(camp13, catSenKatF, senF4)); inscripciones.add(ins(camp13, catSenKatF, senF8));
        inscripciones.add(ins(camp13, catSenKatF, senF12)); inscripciones.add(ins(camp13, catSenKatF, senF16));
        inscripciones.add(ins(camp13, catSenKumM5, senM1)); inscripciones.add(ins(camp13, catSenKumM5, senM5));
        inscripciones.add(ins(camp13, catSenKumM5, senM9)); inscripciones.add(ins(camp13, catSenKumM5, senM13));
        inscripciones.add(ins(camp13, catSenKumM5, senM17)); inscripciones.add(ins(camp13, catSenKumM5, senM21));

        inscripcionRepository.saveAll(inscripciones);


        List<Combate> combates = new ArrayList<>();

        // Helper local: obtiene la Campeonato_Categoria o lanza excepción
        // (puedes extraerlo como método privado si prefieres)

        // ── CAMP1: Campeonato España Cadete/Junior/Sub-21 ─────────────────────
        combates.addAll(generarRonda1(camp1, catCadKataM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp1.getId_campeonato(), catCadKataM.getId_categoria())).orElseThrow(),
                List.of(cadM1, cadM2, cadM3, cadM4, cadM5, cadM6, cadM7, cadM8), 1, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp1, catCadKataF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp1.getId_campeonato(), catCadKataF.getId_categoria())).orElseThrow(),
                List.of(cadF1, cadF2, cadF3, cadF4, cadF5, cadF6), 2, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp1, catJunKatM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp1.getId_campeonato(), catJunKatM.getId_categoria())).orElseThrow(),
                List.of(junM1, junM2, junM3, junM4, junM5, junM6, junM7, junM8), 3, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp1, catJunKatF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp1.getId_campeonato(), catJunKatF.getId_categoria())).orElseThrow(),
                List.of(junF1, junF2, junF3, junF4, junF5, junF6), 4, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp1, catSub21KatM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp1.getId_campeonato(), catSub21KatM.getId_categoria())).orElseThrow(),
                List.of(sub21M1, sub21M2, sub21M3, sub21M4, sub21M5, sub21M6, sub21M7, sub21M8), 5, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp1, catSub21KatF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp1.getId_campeonato(), catSub21KatF.getId_categoria())).orElseThrow(),
                List.of(sub21F1, sub21F2, sub21F3, sub21F4, sub21F5, sub21F6), 6, java.time.LocalTime.of(9, 0), 15));

        // ── CAMP2: Campeonato España Senior ───────────────────────────────────
        combates.addAll(generarRonda1(camp2, catSenKatM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp2.getId_campeonato(), catSenKatM.getId_categoria())).orElseThrow(),
                List.of(senM1, senM2, senM3, senM4, senM5, senM6, senM7, senM8, senM9, senM10, senM11, senM12, senM13, senM14), 1, java.time.LocalTime.of(9, 0), 10));

        combates.addAll(generarRonda1(camp2, catSenKatF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp2.getId_campeonato(), catSenKatF.getId_categoria())).orElseThrow(),
                List.of(senF1, senF2, senF3, senF4, senF5, senF6, senF7, senF8, senF9, senF10, senF11, senF12), 2, java.time.LocalTime.of(9, 0), 10));

        // ── CAMP3: Campeonato Andalucía Base ──────────────────────────────────
        combates.addAll(generarRonda1(camp3, catAlevKataM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp3.getId_campeonato(), catAlevKataM.getId_categoria())).orElseThrow(),
                List.of(alevM1, alevM2, alevM3, alevM4, alevM5), 1, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp3, catAlevKataF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp3.getId_campeonato(), catAlevKataF.getId_categoria())).orElseThrow(),
                List.of(alevF1, alevF2, alevF3, alevF4), 2, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp3, catInfKataM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp3.getId_campeonato(), catInfKataM.getId_categoria())).orElseThrow(),
                List.of(infM1, infM2, infM3, infM4, infM5, infM6), 3, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp3, catInfKataF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp3.getId_campeonato(), catInfKataF.getId_categoria())).orElseThrow(),
                List.of(infF1, infF2, infF3, infF4, infF5), 4, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp3, catJuvKataM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp3.getId_campeonato(), catJuvKataM.getId_categoria())).orElseThrow(),
                List.of(juvM1, juvM2, juvM3, juvM4, juvM5), 5, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp3, catJuvKataF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp3.getId_campeonato(), catJuvKataF.getId_categoria())).orElseThrow(),
                List.of(juvF1, juvF2, juvF3, juvF4, juvF5), 6, java.time.LocalTime.of(9, 0), 15));

        // ── CAMP4: Campeonato Andalucía Senior ────────────────────────────────
        combates.addAll(generarRonda1(camp4, catSenKatM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp4.getId_campeonato(), catSenKatM.getId_categoria())).orElseThrow(),
                List.of(senM1, senM3, senM5, senM7, senM9, senM11, senM13), 1, java.time.LocalTime.of(9, 0), 10));

        combates.addAll(generarRonda1(camp4, catSenKatF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp4.getId_campeonato(), catSenKatF.getId_categoria())).orElseThrow(),
                List.of(senF1, senF3, senF5, senF7, senF9, senF11), 2, java.time.LocalTime.of(9, 0), 10));

        // ── CAMP5: Liga Iberdrola Cadete/Junior/Sub21 Femenina ────────────────
        combates.addAll(generarRonda1(camp5, catSub21KatF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp5.getId_campeonato(), catSub21KatF.getId_categoria())).orElseThrow(),
                List.of(sub21F1, sub21F2, sub21F3, sub21F4, sub21F5, sub21F6), 1, java.time.LocalTime.of(9, 0), 15));

        // ── CAMP6: Liga Iberdrola Cadete/Junior/Sub21 Masculino ───────────────
        combates.addAll(generarRonda1(camp6, catSub21KatM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp6.getId_campeonato(), catSub21KatM.getId_categoria())).orElseThrow(),
                List.of(sub21M1, sub21M2, sub21M3, sub21M4, sub21M5, sub21M6, sub21M7, sub21M8), 1, java.time.LocalTime.of(9, 0), 15));

        // ── CAMP9: Campeonato España Infantil ─────────────────────────────────
        combates.addAll(generarRonda1(camp9, catInfKataM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp9.getId_campeonato(), catInfKataM.getId_categoria())).orElseThrow(),
                List.of(infM1, infM2, infM3, infM4, infM5, infM6), 1, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp9, catInfKataF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp9.getId_campeonato(), catInfKataF.getId_categoria())).orElseThrow(),
                List.of(infF1, infF2, infF3, infF4, infF5), 2, java.time.LocalTime.of(9, 0), 15));

        // ── CAMP10: Campeonato España Absoluto ────────────────────────────────
        combates.addAll(generarRonda1(camp10, catSenKatM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp10.getId_campeonato(), catSenKatM.getId_categoria())).orElseThrow(),
                List.of(senM1, senM2, senM3, senM4, senM5, senM6, senM7, senM8,
                        senM9, senM10, senM11, senM12, senM13, senM14, senM15, senM16), 1, java.time.LocalTime.of(9, 0), 10));

        combates.addAll(generarRonda1(camp10, catSenKatF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp10.getId_campeonato(), catSenKatF.getId_categoria())).orElseThrow(),
                List.of(senF1, senF2, senF3, senF4, senF5, senF6, senF7, senF8,
                        senF9, senF10, senF11, senF12), 2, java.time.LocalTime.of(9, 0), 10));

        // ── CAMP15: Liga Nacional Base Masculina ──────────────────────────────
        combates.addAll(generarRonda1(camp15, catAlevKataM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp15.getId_campeonato(), catAlevKataM.getId_categoria())).orElseThrow(),
                List.of(alevM1, alevM2, alevM3, alevM4, alevM5), 1, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp15, catInfKataM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp15.getId_campeonato(), catInfKataM.getId_categoria())).orElseThrow(),
                List.of(infM1, infM2, infM3, infM4, infM5, infM6), 2, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp15, catJuvKataM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp15.getId_campeonato(), catJuvKataM.getId_categoria())).orElseThrow(),
                List.of(juvM1, juvM2, juvM3, juvM4, juvM5), 3, java.time.LocalTime.of(9, 0), 15));

        // ── CAMP16: Liga Iberdrola Base Femenina (alvFem + infFem + juvFem) ───
        combates.addAll(generarRonda1(camp16, catAlevKataF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp16.getId_campeonato(), catAlevKataF.getId_categoria())).orElseThrow(),
                List.of(alevF1, alevF2, alevF3, alevF4), 1, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp16, catInfKataF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp16.getId_campeonato(), catInfKataF.getId_categoria())).orElseThrow(),
                List.of(infF1, infF2, infF3, infF4, infF5), 2, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp16, catJuvKataF,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp16.getId_campeonato(), catJuvKataF.getId_categoria())).orElseThrow(),
                List.of(juvF1, juvF2, juvF3, juvF4, juvF5), 3, java.time.LocalTime.of(9, 0), 15));

        // ── CAMP17: Liga Nacional Master (masters) ────────────────────────────
        combates.addAll(generarRonda1(camp17, catMasKatM1,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp17.getId_campeonato(), catMasKatM1.getId_categoria())).orElseThrow(),
                List.of(senM1, senM2, senM3, senM4), 1, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp17, catMasKatF1,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp17.getId_campeonato(), catMasKatF1.getId_categoria())).orElseThrow(),
                List.of(senF1, senF2, senF3, senF4), 2, java.time.LocalTime.of(9, 0), 15));

        // ── CAMP18: Liga Nacional Junior/Sub21 (junMasc + s21Masc) ───────────
        combates.addAll(generarRonda1(camp18, catJunKatM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp18.getId_campeonato(), catJunKatM.getId_categoria())).orElseThrow(),
                List.of(junM1, junM2, junM3, junM4, junM5, junM6), 1, java.time.LocalTime.of(9, 0), 15));

        combates.addAll(generarRonda1(camp18, catSub21KatM,
                campeonato_categoriaRepository.findById(new Campeonato_Categoria_Id(camp18.getId_campeonato(), catSub21KatM.getId_categoria())).orElseThrow(),
                List.of(sub21M1, sub21M2, sub21M3, sub21M4, sub21M5, sub21M6), 2, java.time.LocalTime.of(9, 0), 15));

        combateRepository.saveAll(combates);

        // 4. CREAR ÁRBITROS
        // Arbitro extiende Usuario, campos reales: licencia, categoriaArbitral
        // Campos de Usuario: nombre, apellidos, email, password, rol, fechaNacimiento, genero, dni
        Arbitro arb1 = Arbitro.builder()
                .nombre("Luis")
                .apellidos("Gómez Juez")
                .dni("12345678Z")
                .email("luis.gomez@arbitros.es")
                .password("password")
                .rol(Usuario.Rol.ARBITRO)
                .fechaNacimiento(toDate(LocalDate.of(1975, 5, 15)))
                .genero('M')
                .licencia("LIC-2026-001")
                .categoriaArbitral("Nacional")
                .build();
        arbitroRepository.save(arb1);

        System.out.println("Carga de datos de Karate finalizada con éxito.");
    }

    // Crea las relaciones Campeonato <-> Categoria (sin fechaInicio/fechaFin, que no existen en el modelo)
    private List<Campeonato_Categoria> createCampeonatoCategoria(Campeonato campeonato, List<Categoria> categorias) {
        List<Campeonato_Categoria> relaciones = new ArrayList<>();
        for (Categoria categoria : categorias) {
            Campeonato_Categoria_Id id = new Campeonato_Categoria_Id(
                    campeonato.getId_campeonato(),
                    categoria.getId_categoria()
            );
            relaciones.add(Campeonato_Categoria.builder()
                    .id(id)
                    .campeonato(campeonato)
                    .categoria(categoria)
                    .build());
        }
        return relaciones;
    }

    // Helper para crear inscripciones
    private Inscripcion ins(Campeonato campeonato, Categoria categoria, Competidor competidor) {
        return Inscripcion.builder()
                .id_inscripcion(new Inscripcion_Id(
                        campeonato.getId_campeonato(),
                        categoria.getId_categoria(),
                        competidor.getIdUsuario()
                ))
                .campeonato(campeonato)
                .categoria(categoria)
                .competidor(competidor)
                .build();
    }

    private List<Combate> generarRonda1(
            Campeonato campeonato,
            Categoria categoria,
            Campeonato_Categoria cc,
            List<Competidor> competidores,
            int tatami,
            java.time.LocalTime horaInicio,
            int minutosPorCombate) {

        List<Combate> combates = new ArrayList<>();
        int numeroCombate = 1;
        java.time.LocalTime hora = horaInicio;

        for (int i = 0; i < competidores.size(); i += 2) {
            Competidor rojo = competidores.get(i);
            Competidor azul = (i + 1 < competidores.size()) ? competidores.get(i + 1) : null; // bye si impar

            Combate_Id id = new Combate_Id(
                    campeonato.getId_campeonato(),
                    categoria.getId_categoria(),
                    tatami,
                    numeroCombate
            );

            Combate c = Combate.builder()
                    .id(id)
                    .ronda("R1")
                    .estado("pendiente")
                    .competidorRojo(rojo)
                    .competidorAzul(azul)
                    .campeonatoCategoria(cc)
                    .puntuacionRojo(0)
                    .puntuacionAzul(0)
                    .senshu(null)
                    .observaciones(azul == null ? "BYE - pasa automáticamente" : null)
                    .horaProgramada(hora)
                    .horaInicioReal(java.time.LocalDateTime.now()) // placeholder; se actualiza al iniciar
                    .duracionSegundos(180) // 3 minutos estándar kumite
                    .build();

            combates.add(c);
            numeroCombate++;
            hora = hora.plusMinutes(minutosPorCombate);
        }
        return combates;
    }
}