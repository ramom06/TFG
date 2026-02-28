package org.example.proyectocampeonato.DataLoader;

import lombok.RequiredArgsConstructor;
import org.example.proyectocampeonato.modelo.*;
import org.example.proyectocampeonato.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        List<Categoria> benjamin =  new ArrayList<>();
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

        //MASTER
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
        Categoria catMasKumF3 = Categoria.builder().nombre("Master Fem. 41-50 Open").modalidad("kumite").genero("F").edadMinima(51).edadMaxima(99).build();

        List<Categoria> masters = new ArrayList<>(List.of(catMasKatM1, catMasKatM2, catMasKatM3, catMasKatM4, catMasKatM5, catMasKatM6, catMasKatM7, catMasKatM8, catMasKatF1, catMasKatF2, catMasKatF3, catMasKatF4, catMasKatF5, catMasKatF6, catMasKatF7, catMasKatF8, catMasKumM1, catMasKumM2, catMasKumM3, catMasKumM4, catMasKumM5, catMasKumM6, catMasKumM7, catMasKumM8, catMasKumF1, catMasKumF2, catMasKumF3));
        categoriaRepository.saveAll(masters);

        List<Categoria> alvFem = alevines.stream().filter( cat -> cat.getGenero().equals("F")).toList();
        List<Categoria> infFem = infantiles.stream().filter( cat -> cat.getGenero().equals("F")).toList();
        List<Categoria> juvFem = juveniles.stream().filter( cat -> cat.getGenero().equals("F")).toList();
        List<Categoria> cadFem = cadetes.stream().filter( cat -> cat.getGenero().equals("F")).toList();
        List<Categoria> junFem = juniors.stream().filter( cat -> cat.getGenero().equals("F")).toList();
        List<Categoria> s21Fem = sub21.stream().filter( cat -> cat.getGenero().equals("F")).toList();
        List<Categoria> senFem = seniors.stream().filter( cat -> cat.getGenero().equals("F")).toList();
        List<Categoria> mastFem = masters.stream().filter( cat -> cat.getGenero().equals("F")).toList();

        List<Categoria> alvMasc = alevines.stream().filter( cat -> cat.getGenero().equals("M")).toList();
        List<Categoria> infMasc = infantiles.stream().filter( cat -> cat.getGenero().equals("M")).toList();
        List<Categoria> juvMasc = juveniles.stream().filter( cat -> cat.getGenero().equals("M")).toList();
        List<Categoria> cadMasc = cadetes.stream().filter( cat -> cat.getGenero().equals("M")).toList();
        List<Categoria> junMasc = juniors.stream().filter( cat -> cat.getGenero().equals("M")).toList();
        List<Categoria> s21Masc = sub21.stream().filter( cat -> cat.getGenero().equals("M")).toList();
        List<Categoria> senMasc = seniors.stream().filter( cat -> cat.getGenero().equals("M")).toList();
        List<Categoria> mastMasc = masters.stream().filter( cat -> cat.getGenero().equals("M")).toList();


        // CAMPEONATOS
        // 1. CREAR CAMPEONATOS (Lista depurada)
        Campeonato camp1 = Campeonato.builder().nombre("Campeonato de España Cadete/Junior/Sub-21 2026").fechaInicio(new Date(2026, 11, 10)).fechaFin(new Date(2026, 11, 12)).ubicacion("Pabellón Multiusos, Madrid").estado("futuro").nivel("Nacional").descripcion("Torneo clave para la clasificación al próximo campeonato de Europa").build();
        Campeonato camp2 = Campeonato.builder().nombre("Campeonato de España Senior 2026").fechaInicio(new Date(2026, 2, 2)).fechaFin(new Date(2026, 2, 4)).ubicacion("Pabellón de Carranque, Málaga, Andalucía, España").estado("pasado").nivel("Nacional").descripcion("Torneo clasificatorio para el mundial").build();
        Campeonato camp3 = Campeonato.builder().nombre("Campeonato de Andalucía alevin/infantil/juvenil 2026").fechaInicio(new Date(2026, 3, 1)).fechaFin(new Date(2026, 3, 1)).ubicacion("Pabellón de Deportes de Córdoba, Córdoba, Andalucía, España").estado("futuro").nivel("Provincial").descripcion("Torneo clasificatorio para el campeonato de España").build();
        Campeonato camp4 = Campeonato.builder().nombre("Campeonato de Andalucía Senior 2026").fechaInicio(new Date(2026, 2, 30)).fechaFin(new Date(2026, 2, 30)).ubicacion("Pabellón de Ciudad Jardín, Málaga, Andalucía").estado("futuro").nivel("Provincial").descripcion("Torneo clasificatorio para el campeonato de España").build();
        Campeonato camp5 = Campeonato.builder().nombre("Liga Iberdrola Cadete/Junior/Sub-21 J4 2026").fechaInicio(new Date(2026, 3, 8)).fechaFin(new Date(2026, 3, 8)).ubicacion("Pabellón Municipal Puerta de Santa María, 13005 Ciudad Real").estado("futuro").nivel("Nacional").descripcion("Torneo clasificatorio para el Campeonato Europeo y con recompensa de créditos").build();
        Campeonato camp6 = Campeonato.builder().nombre("Liga Nacional Cadete/Junior/Sub-21 J4 2026").fechaInicio(new Date(2026, 3, 7)).fechaFin(new Date(2026, 3, 7)).ubicacion("Pabellón Municipal Puerta de Santa María, 13005 Ciudad Real").estado("futuro").nivel("Nacional").descripcion("Torneo clasificatorio para el Campeonato Europeo y con recompensa de créditos").build();
        Campeonato camp7 = Campeonato.builder().nombre("VII Campeonato de España Master 2026").fechaInicio(new Date(2026, 4, 10)).fechaFin(new Date(2026, 4, 12)).ubicacion("La Nucía, Alicante, España").estado("futuro").nivel("Nacional").descripcion("Campeonato nacional categoría master").build();
        Campeonato camp8 = Campeonato.builder().nombre("Campeonato de España Universitario 2026").fechaInicio(new Date(2026, 4, 17)).fechaFin(new Date(2026, 4, 18)).ubicacion("Granada, Andalucía, España").estado("futuro").nivel("Nacional").descripcion("Campeonato universitario nacional de karate").build();
        Campeonato camp9 = Campeonato.builder().nombre("Campeonato de España Alevín/Infantil/Juvenil 2026").fechaInicio(new Date(2026, 5, 8)).fechaFin(new Date(2026, 5, 10)).ubicacion("Oviedo, Asturias, España").estado("futuro").nivel("Nacional").descripcion("Campeonato nacional categorías infantil").build();
        Campeonato camp10 = Campeonato.builder().nombre("Campeonato de España Absoluto 2026").fechaInicio(new Date(2026, 3, 27)).fechaFin(new Date(2026, 3, 29)).ubicacion("Pontevedra, España").estado("futuro").nivel("Nacional").descripcion("Campeonato nacional absoluto").build();
        Campeonato camp11 = Campeonato.builder().nombre("Liga Nacional Master - 1ª Ronda 2026").fechaInicio(new Date(2026, 3, 1)).fechaFin(new Date(2026, 3, 1)).ubicacion("Torrejón de Ardoz, Madrid, España").estado("futuro").nivel("Nacional").descripcion("Primera ronda liga nacional master").build();
        Campeonato camp12 = Campeonato.builder().nombre("Campeonato de España Cadete/Junior/Sub-21 2026 (Final)").fechaInicio(new Date(2026, 11, 27)).fechaFin(new Date(2026, 11, 29)).ubicacion("Valencia, España").estado("futuro").nivel("Nacional").descripcion("Campeonato nacional categorías cadete junior sub21").build();
        Campeonato camp13 = Campeonato.builder().nombre("Liga Nacional Masculina Absoluta/Cadete - 1ª Ronda 2026").fechaInicio(new Date(2026, 1, 17)).fechaFin(new Date(2026, 1, 17)).ubicacion("Valdepeñas, Ciudad Real, España").estado("pasado").nivel("Nacional").descripcion("Primera ronda Liga Nacional masculina absoluta y cadete").build();
        Campeonato camp14 = Campeonato.builder().nombre("Liga Iberdrola Absoluta/Cadete - 1ª Ronda 2026").fechaInicio(new Date(2026, 1, 18)).fechaFin(new Date(2026, 1, 18)).ubicacion("Valdepeñas, Ciudad Real, España").estado("pasado").nivel("Nacional").descripcion("Primera ronda Liga Iberdrola femenina absoluta y cadete").build();
        Campeonato camp15 = Campeonato.builder().nombre("Liga Nacional Masculina Alevín/Infantil/Juvenil - 1ª Ronda 2026").fechaInicio(new Date(2026, 2, 21)).fechaFin(new Date(2026, 2, 21)).ubicacion("Logroño, España").estado("pasado").nivel("Nacional").descripcion("Primera ronda Liga Nacional categorías base").build();
        Campeonato camp16 = Campeonato.builder().nombre("Liga Iberdrola Alevín/Infantil/Juvenil - 1ª Ronda 2026").fechaInicio(new Date(2026, 2, 22)).fechaFin(new Date(2026, 2, 22)).ubicacion("Logroño, España").estado("pasado").nivel("Nacional").descripcion("Primera ronda Liga Iberdrola categorías base").build();
        Campeonato camp17 = Campeonato.builder().nombre("Liga Nacional Master - 1ª Ronda 2026").fechaInicio(new Date(2026, 3, 1)).fechaFin(new Date(2026, 3, 1)).ubicacion("Torrejón de Ardoz, Madrid, España").estado("futuro").nivel("Nacional").descripcion("Primera ronda Liga Nacional master").build();
        Campeonato camp18 = Campeonato.builder().nombre("Liga Nacional Junior/Sub21 - 1ª Ronda 2026").fechaInicio(new Date(2026, 3, 7)).fechaFin(new Date(2026, 3, 7)).ubicacion("Ciudad Real, España").estado("futuro").nivel("Nacional").descripcion("Primera ronda Liga Nacional junior y sub21").build();
        Campeonato camp19 = Campeonato.builder().nombre("Liga Iberdrola Junior/Sub21 - 1ª Ronda 2026").fechaInicio(new Date(2026, 3, 8)).fechaFin(new Date(2026, 3, 8)).ubicacion("Ciudad Real, España").estado("futuro").nivel("Nacional").descripcion("Primera ronda Liga Iberdrola junior y sub21").build();
        Campeonato camp20 = Campeonato.builder().nombre("Liga Nacional Masculina Absoluta/Cadete - 2ª Ronda 2026").fechaInicio(new Date(2026, 10, 24)).fechaFin(new Date(2026, 10, 24)).ubicacion("Elche, Alicante, España").estado("futuro").nivel("Nacional").descripcion("Segunda ronda Liga Nacional masculina absoluta y cadete").build();
        Campeonato camp21 = Campeonato.builder().nombre("Liga Iberdrola Absoluta/Cadete - 2ª Ronda 2026").fechaInicio(new Date(2026, 10, 25)).fechaFin(new Date(2026, 10, 25)).ubicacion("Elche, Alicante, España").estado("futuro").nivel("Nacional").descripcion("Segunda ronda Liga Iberdrola femenina absoluta y cadete").build();
        Campeonato camp22 = Campeonato.builder().nombre("Liga Nacional Master - 2ª Ronda 2026").fechaInicio(new Date(2026, 11, 1)).fechaFin(new Date(2026, 11, 1)).ubicacion("Córdoba, España").estado("futuro").nivel("Nacional").descripcion("Segunda ronda Liga Nacional master").build();

        campeonatoRepository.saveAll(List.of(camp1, camp2, camp3, camp4, camp5, camp6, camp7, camp8, camp9, camp10, camp11, camp12, camp13, camp14, camp15, camp16, camp17, camp18, camp19, camp20, camp21, camp22));

        //Relacion Campeonato -> Categorias
        // Relación Campeonato -> Categorías
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp1, cadetes));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp1, juniors));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp1, sub21));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp2, seniors));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp3, alevines));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp3, infantiles));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp3, juveniles));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp4, seniors));

// Liga Iberdrola Cadete/Junior/Sub-21
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp5, cadFem));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp5, junFem));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp5, s21Fem));

// Liga Nacional Cadete/Junior/Sub-21
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp6, cadMasc));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp6, junMasc));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp6, s21Masc));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp7, masters));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp8, seniors));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp9, infantiles));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp10, seniors));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp11, masters));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp12, cadetes));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp12, juniors));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp12, sub21));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp13, cadetes));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp13, seniors));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp14, cadFem));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp14, senFem));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp15, alvMasc));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp15, infMasc));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp15, juvMasc));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp16, alvFem));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp16, infFem));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp16, juvFem));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp17, masters));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp18, junMasc));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp18, s21Masc));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp19, junFem));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp19, s21Fem));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp20, cadMasc));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp20, senMasc));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp21, cadFem));
        campeonato_categoriaRepository.saveAll(crearRelaciones(camp21, senFem));

        campeonato_categoriaRepository.saveAll(crearRelaciones(camp22, masters));

        // 3. CREAR COMPETIDORES
// Lista de competidores (renombrados y desordenados)
        Competidor comp1 = Competidor.builder().nombre("Antonio").apellidos("Ruiz").dni("99999991A").fechaNacimiento(LocalDate.of(1985, 1, 1)).genero('M').club("Dojo 17").federacionAutonomica("Madrileña").build();
        Competidor comp2 = Competidor.builder().nombre("Pablo").apellidos("Gómez").dni("33333331A").fechaNacimiento(LocalDate.of(2015, 1, 1)).genero('M').club("Dojo 5").federacionAutonomica("Madrileña").build();
        Competidor comp3 = Competidor.builder().nombre("Marcos").apellidos("Gil").dni("66666661A").fechaNacimiento(LocalDate.of(2009, 1, 1)).genero('M').club("Dojo 11").federacionAutonomica("Madrileña").build();
        Competidor comp4 = Competidor.builder().nombre("Vega").apellidos("Sanz").dni("11111114D").fechaNacimiento(LocalDate.of(2019, 4, 4)).genero('F').club("Dojo 2").federacionAutonomica("Andaluza").build();
        Competidor comp5 = Competidor.builder().nombre("Marc").apellidos("Pérez").dni("22222221A").fechaNacimiento(LocalDate.of(2017, 1, 1)).genero('M').club("Dojo 3").federacionAutonomica("Madrileña").build();
        Competidor comp6 = Competidor.builder().nombre("Laura").apellidos("Ortiz").dni("55555554D").fechaNacimiento(LocalDate.of(2011, 4, 4)).genero('F').club("Dojo 10").federacionAutonomica("Andaluza").build();
        Competidor comp7 = Competidor.builder().nombre("Jorge").apellidos("Ramos").dni("77777771A").fechaNacimiento(LocalDate.of(2006, 1, 1)).genero('M').club("Dojo 13").federacionAutonomica("Madrileña").build();
        Competidor comp8 = Competidor.builder().nombre("Sara").apellidos("López").dni("11111113C").fechaNacimiento(LocalDate.of(2019, 3, 3)).genero('F').club("Dojo 2").federacionAutonomica("Andaluza").build();
        Competidor comp9 = Competidor.builder().nombre("David").apellidos("Cano").dni("88888881A").fechaNacimiento(LocalDate.of(2002, 1, 1)).genero('M').club("Dojo 15").federacionAutonomica("Madrileña").build();
        Competidor comp10 = Competidor.builder().nombre("Inés").apellidos("Álvarez").dni("44444443C").fechaNacimiento(LocalDate.of(2013, 3, 3)).genero('F').club("Dojo 8").federacionAutonomica("Andaluza").build();
        Competidor comp11 = Competidor.builder().nombre("Julia").apellidos("Jiménez").dni("33333333C").fechaNacimiento(LocalDate.of(2015, 3, 3)).genero('F').club("Dojo 6").federacionAutonomica("Andaluza").build();
        Competidor comp12 = Competidor.builder().nombre("Silvia").apellidos("Torres").dni("99999994D").fechaNacimiento(LocalDate.of(1984, 4, 4)).genero('F').club("Dojo 18").federacionAutonomica("Andaluza").build();
        Competidor comp13 = Competidor.builder().nombre("Iker").apellidos("Muñoz").dni("44444442B").fechaNacimiento(LocalDate.of(2013, 2, 2)).genero('M').club("Dojo 7").federacionAutonomica("Madrileña").build();
        Competidor comp14 = Competidor.builder().nombre("Paula").apellidos("Cruz").dni("77777774D").fechaNacimiento(LocalDate.of(2006, 4, 4)).genero('F').club("Dojo 14").federacionAutonomica("Andaluza").build();
        Competidor comp15 = Competidor.builder().nombre("Adrián").apellidos("Torres").dni("55555551A").fechaNacimiento(LocalDate.of(2011, 1, 1)).genero('M').club("Dojo 9").federacionAutonomica("Madrileña").build();
        Competidor comp16 = Competidor.builder().nombre("Hugo").apellidos("Ruiz").dni("11111111A").fechaNacimiento(LocalDate.of(2019, 1, 1)).genero('M').club("Dojo 1").federacionAutonomica("Madrileña").build();
        Competidor comp17 = Competidor.builder().nombre("Alba").apellidos("Romero").dni("44444444D").fechaNacimiento(LocalDate.of(2013, 4, 4)).genero('F').club("Dojo 8").federacionAutonomica("Andaluza").build();
        Competidor comp18 = Competidor.builder().nombre("Carmen").apellidos("Garrido").dni("77777773C").fechaNacimiento(LocalDate.of(2006, 3, 3)).genero('F').club("Dojo 14").federacionAutonomica("Andaluza").build();
        Competidor comp19 = Competidor.builder().nombre("Alex").apellidos("Díaz").dni("22222222B").fechaNacimiento(LocalDate.of(2017, 2, 2)).genero('M').club("Dojo 3").federacionAutonomica("Madrileña").build();
        Competidor comp20 = Competidor.builder().nombre("Lucía").apellidos("Pérez").dni("66666664D").fechaNacimiento(LocalDate.of(2009, 4, 4)).genero('F').club("Dojo 12").federacionAutonomica("Andaluza").build();
        Competidor comp21 = Competidor.builder().nombre("Elena").apellidos("Navarro").dni("55555553C").fechaNacimiento(LocalDate.of(2011, 3, 3)).genero('F').club("Dojo 10").federacionAutonomica("Andaluza").build();
        Competidor comp22 = Competidor.builder().nombre("Marta").apellidos("Reyes").dni("88888883C").fechaNacimiento(LocalDate.of(2002, 3, 3)).genero('F').club("Dojo 16").federacionAutonomica("Andaluza").build();
        Competidor comp23 = Competidor.builder().nombre("Dani").apellidos("Moreno").dni("44444441A").fechaNacimiento(LocalDate.of(2013, 1, 1)).genero('M').club("Dojo 7").federacionAutonomica("Madrileña").build();
        Competidor comp24 = Competidor.builder().nombre("Oscar").apellidos("Castro").dni("77777772B").fechaNacimiento(LocalDate.of(2006, 2, 2)).genero('M').club("Dojo 13").federacionAutonomica("Madrileña").build();
        Competidor comp25 = Competidor.builder().nombre("Ana").apellidos("Ruiz").dni("33333334D").fechaNacimiento(LocalDate.of(2015, 4, 4)).genero('F').club("Dojo 6").federacionAutonomica("Andaluza").build();
        Competidor comp26 = Competidor.builder().nombre("Javier").apellidos("Hernández").dni("33333332B").fechaNacimiento(LocalDate.of(2015, 2, 2)).genero('M').club("Dojo 5").federacionAutonomica("Madrileña").build();
        Competidor comp27 = Competidor.builder().nombre("Abril").apellidos("Ruiz").dni("22222224D").fechaNacimiento(LocalDate.of(2017, 4, 4)).genero('F').club("Dojo 4").federacionAutonomica("Andaluza").build();
        Competidor comp28 = Competidor.builder().nombre("Isabel").apellidos("Vega").dni("88888884D").fechaNacimiento(LocalDate.of(2002, 4, 4)).genero('F').club("Dojo 16").federacionAutonomica("Andaluza").build();
        Competidor comp29 = Competidor.builder().nombre("Roberto").apellidos("García").dni("99999992B").fechaNacimiento(LocalDate.of(1982, 2, 2)).genero('M').club("Dojo 17").federacionAutonomica("Madrileña").build();
        Competidor comp30 = Competidor.builder().nombre("Noa").apellidos("Martín").dni("22222223C").fechaNacimiento(LocalDate.of(2017, 3, 3)).genero('F').club("Dojo 4").federacionAutonomica("Andaluza").build();
        Competidor comp31 = Competidor.builder().nombre("Sofía").apellidos("Serrano").dni("66666663C").fechaNacimiento(LocalDate.of(2009, 3, 3)).genero('F').club("Dojo 12").federacionAutonomica("Andaluza").build();
        Competidor comp32 = Competidor.builder().nombre("Mario").apellidos("Sánchez").dni("55555552B").fechaNacimiento(LocalDate.of(2011, 2, 2)).genero('M').club("Dojo 9").federacionAutonomica("Madrileña").build();
        Competidor comp33 = Competidor.builder().nombre("Raúl").apellidos("Vidal").dni("88888882B").fechaNacimiento(LocalDate.of(2002, 2, 2)).genero('M').club("Dojo 15").federacionAutonomica("Madrileña").build();
        Competidor comp34 = Competidor.builder().nombre("Diego").apellidos("Molina").dni("66666662B").fechaNacimiento(LocalDate.of(2009, 2, 2)).genero('M').club("Dojo 11").federacionAutonomica("Madrileña").build();
        Competidor comp35 = Competidor.builder().nombre("Leo").apellidos("García").dni("11111112B").fechaNacimiento(LocalDate.of(2019, 2, 2)).genero('M').club("Dojo 1").federacionAutonomica("Madrileña").build();
        Competidor comp36 = Competidor.builder().nombre("Carmen").apellidos("Sanz").dni("99999993C").fechaNacimiento(LocalDate.of(1988, 3, 3)).genero('F').club("Dojo 18").federacionAutonomica("Andaluza").build();

        competidorRepository.saveAll(List.of(comp1, comp2, comp3, comp4, comp5, comp6, comp7, comp8, comp9, comp10, comp11, comp12, comp13, comp14, comp15, comp16, comp17, comp18, comp19, comp20, comp21, comp22, comp23, comp24, comp25, comp26, comp27, comp28, comp29, comp30, comp31, comp32, comp33, comp34, comp35, comp36));

        // 4. CREAR ÁRBITROS
        Arbitro arb1 = Arbitro.builder().nombre("Luis").apellidos("Gómez Juez").licencia("LIC-2026-001").fecha_nacimiento(new Date()).categoria_Arbitral("Nacional").activo(true).build();
        arbitroRepository.save(arb1);

        // 5. CREAR COMBATES
        /*CombateId combateId1 = new CombateId(comp1.getId_competidor(), comp2.getId_competidor(), 1);
        Combate combate1 = Combate.builder().id(combateId1).ronda("Final").competidorRojo(comp1).competidorAzul(comp2).puntuacionRojo(3).puntuacionAzul(1).senshu("rojo").estado("finalizado").horaProgramada(LocalTime.of(10, 30)).horaInicioReal(LocalDateTime.now()).duracionSegundos(180).build();
        combateRepository.save(combate1);*/

        System.out.println("Carga de datos de Karate finalizada con éxito.");
    }

    private List<Campeonato_Categoria> crearRelaciones(Campeonato campeonato, List<Categoria> categorias) {
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
                    .fechaInicio(campeonato.getFechaInicio())
                    .fechaFin(campeonato.getFechaFin())
                    .build());
        }
        return relaciones;
    }
}