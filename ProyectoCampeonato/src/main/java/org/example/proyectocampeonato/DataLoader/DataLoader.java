package org.example.proyectocampeonato.DataLoader;

import lombok.RequiredArgsConstructor;
import org.example.proyectocampeonato.modelo.*;
import org.example.proyectocampeonato.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
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
// Lista de competidores (renombrados y desordenados)
        Competidor senM1  = Competidor.builder().nombre("Juan").apellidos("Pérez Cano").dni("12345678A").fechaNacimiento(LocalDate.of(2000, 5, 20)).genero('M').club("Dojo Shoto").federacionAutonomica("Comunidad de Madrid").build();
        Competidor senM2  = Competidor.builder().nombre("Alberto").apellidos("Ruiz Galiano").dni("87654321B").fechaNacimiento(LocalDate.of(1998, 11, 2)).genero('M').club("Karate Almería").federacionAutonomica("Andalucía").build();
        Competidor senM3  = Competidor.builder().nombre("Carlos").apellidos("López Martín").dni("11223344C").fechaNacimiento(LocalDate.of(2001, 3, 15)).genero('M').club("Karate Aragón").federacionAutonomica("Aragón").build();
        Competidor senM4  = Competidor.builder().nombre("Sergio").apellidos("Fernández Ruiz").dni("55667788G").fechaNacimiento(LocalDate.of(1997, 4, 30)).genero('M').club("Karate Santander").federacionAutonomica("Cantabria").build();
        Competidor senM5  = Competidor.builder().nombre("Miguel").apellidos("Rodríguez Blanco").dni("77889900I").fechaNacimiento(LocalDate.of(1996, 12, 5)).genero('M').club("Karate Valladolid").federacionAutonomica("Castilla y León").build();
        Competidor senM6  = Competidor.builder().nombre("Javier").apellidos("Moreno Castillo").dni("99001122K").fechaNacimiento(LocalDate.of(1999, 2, 14)).genero('M').club("Karate Mérida").federacionAutonomica("Extremadura").build();
        Competidor senM7  = Competidor.builder().nombre("David").apellidos("Romero Iglesias").dni("10203040M").fechaNacimiento(LocalDate.of(1998, 5, 3)).genero('M').club("Karate Logroño").federacionAutonomica("La Rioja").build();
        Competidor senM8  = Competidor.builder().nombre("Pablo").apellidos("Torres Herrera").dni("30405060O").fechaNacimiento(LocalDate.of(2001, 10, 16)).genero('M').club("Karate Murcia").federacionAutonomica("Región de Murcia").build();
        Competidor senM9  = Competidor.builder().nombre("Iñaki").apellidos("Etxebarria Zubieta").dni("50607080Q").fechaNacimiento(LocalDate.of(2000, 6, 11)).genero('M').club("Karate Bilbao").federacionAutonomica("País Vasco").build();
        Competidor senM10 = Competidor.builder().nombre("Alejandro").apellidos("Vega Santana").dni("70809000S").fechaNacimiento(LocalDate.of(2003, 4, 19)).genero('M').club("Karate Melilla").federacionAutonomica("Ciudad Autónoma de Melilla").build();
        Competidor senM11 = Competidor.builder().nombre("Fernando").apellidos("Castillo Reyes").dni("11122233T").fechaNacimiento(LocalDate.of(1995, 8, 7)).genero('M').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor senM12 = Competidor.builder().nombre("Raúl").apellidos("Molina Pardo").dni("22233344U").fechaNacimiento(LocalDate.of(1999, 1, 25)).genero('M').club("Karate Pamplona").federacionAutonomica("Comunidad Foral de Navarra").build();
        Competidor senM13 = Competidor.builder().nombre("Óscar").apellidos("Blanco Fuentes").dni("33344455V").fechaNacimiento(LocalDate.of(2002, 7, 13)).genero('M').club("Karate Toledo").federacionAutonomica("Castilla-La Mancha").build();
        Competidor senM14 = Competidor.builder().nombre("Marcos").apellidos("Herrera Gutiérrez").dni("44455566W").fechaNacimiento(LocalDate.of(1997, 3, 28)).genero('M').club("Karate Barcelona").federacionAutonomica("Cataluña").build();
        Competidor senM15 = Competidor.builder().nombre("Antonio").apellidos("Soto Medina").dni("55566677X").fechaNacimiento(LocalDate.of(2000, 11, 9)).genero('M').club("Karate Santiago").federacionAutonomica("Galicia").build();
        Competidor senM16 = Competidor.builder().nombre("Diego").apellidos("Cruz Navarro").dni("66677788Y").fechaNacimiento(LocalDate.of(1994, 6, 15)).genero('M').club("Karate Oviedo").federacionAutonomica("Principado de Asturias").build();
        Competidor senM17 = Competidor.builder().nombre("Rubén").apellidos("Vidal Campos").dni("77788899Z").fechaNacimiento(LocalDate.of(2001, 9, 3)).genero('M').club("Karate Palma").federacionAutonomica("Islas Baleares").build();
        Competidor senM18 = Competidor.builder().nombre("Adrián").apellidos("Aguilar Delgado").dni("88899900A").fechaNacimiento(LocalDate.of(1998, 4, 22)).genero('M').club("Karate Las Palmas").federacionAutonomica("Canarias").build();
        Competidor senM19 = Competidor.builder().nombre("Álvaro").apellidos("Marín Expósito").dni("99900011B").fechaNacimiento(LocalDate.of(2003, 12, 1)).genero('M').club("Karate Ceuta").federacionAutonomica("Ciudad Autónoma de Ceuta").build();
        Competidor senM20 = Competidor.builder().nombre("Gonzalo").apellidos("Pascual Llorente").dni("00011122C").fechaNacimiento(LocalDate.of(1996, 7, 18)).genero('M').club("Dojo Shoto Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor senM21 = Competidor.builder().nombre("Nicolás").apellidos("Serrano Bravo").dni("11100200D").fechaNacimiento(LocalDate.of(2000, 2, 14)).genero('M').club("Karate Sevilla").federacionAutonomica("Andalucía").build();
        Competidor senM22 = Competidor.builder().nombre("Hugo").apellidos("Ramos Ibáñez").dni("22200300E").fechaNacimiento(LocalDate.of(1999, 8, 30)).genero('M').club("Karate Zaragoza").federacionAutonomica("Aragón").build();
        Competidor senM23 = Competidor.builder().nombre("Víctor").apellidos("Pons Leal").dni("33300400F").fechaNacimiento(LocalDate.of(2002, 5, 11)).genero('M').club("Karate Alicante").federacionAutonomica("Comunitat Valenciana").build();
        Competidor senM24 = Competidor.builder().nombre("Eduardo").apellidos("Fuentes Cortés").dni("44400500G").fechaNacimiento(LocalDate.of(1995, 10, 27)).genero('M').club("Karate Vigo").federacionAutonomica("Galicia").build();

        // --- SENIORS FEMENINAS (nacidas 1990-2005) ---
        Competidor senF1  = Competidor.builder().nombre("María").apellidos("García Fernández").dni("22334455D").fechaNacimiento(LocalDate.of(1999, 7, 8)).genero('F').club("Karate Oviedo").federacionAutonomica("Principado de Asturias").build();
        Competidor senF2  = Competidor.builder().nombre("Laura").apellidos("González Díaz").dni("44556677F").fechaNacimiento(LocalDate.of(2000, 9, 12)).genero('F').club("Karate Las Palmas").federacionAutonomica("Canarias").build();
        Competidor senF3  = Competidor.builder().nombre("Ana").apellidos("Sánchez Torres").dni("66778899H").fechaNacimiento(LocalDate.of(2003, 6, 18)).genero('F').club("Karate Toledo").federacionAutonomica("Castilla-La Mancha").build();
        Competidor senF4  = Competidor.builder().nombre("Elena").apellidos("Jiménez Moreno").dni("88990011J").fechaNacimiento(LocalDate.of(2001, 8, 22)).genero('F').club("Karate Barcelona").federacionAutonomica("Cataluña").build();
        Competidor senF5  = Competidor.builder().nombre("Sofía").apellidos("Álvarez Prieto").dni("00112233L").fechaNacimiento(LocalDate.of(2002, 11, 9)).genero('F').club("Karate Santiago").federacionAutonomica("Galicia").build();
        Competidor senF6  = Competidor.builder().nombre("Lucía").apellidos("Serrano Molina").dni("20304050N").fechaNacimiento(LocalDate.of(2000, 7, 27)).genero('F').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor senF7  = Competidor.builder().nombre("Claudia").apellidos("Ramírez Ortega").dni("40506070P").fechaNacimiento(LocalDate.of(1997, 3, 21)).genero('F').club("Karate Pamplona").federacionAutonomica("Comunidad Foral de Navarra").build();
        Competidor senF8  = Competidor.builder().nombre("Nuria").apellidos("Campos Benítez").dni("60708090R").fechaNacimiento(LocalDate.of(1999, 1, 7)).genero('F').club("Karate Ceuta").federacionAutonomica("Ciudad Autónoma de Ceuta").build();
        Competidor senF9  = Competidor.builder().nombre("Patricia").apellidos("Ortiz Guerrero").dni("11200300A").fechaNacimiento(LocalDate.of(1998, 4, 15)).genero('F').club("Karate Bilbao").federacionAutonomica("País Vasco").build();
        Competidor senF10 = Competidor.builder().nombre("Marta").apellidos("León Carrasco").dni("22300400B").fechaNacimiento(LocalDate.of(2001, 12, 3)).genero('F').club("Karate Murcia").federacionAutonomica("Región de Murcia").build();
        Competidor senF11 = Competidor.builder().nombre("Cristina").apellidos("Vargas Escribano").dni("33400500C").fechaNacimiento(LocalDate.of(2000, 3, 19)).genero('F').club("Karate Logroño").federacionAutonomica("La Rioja").build();
        Competidor senF12 = Competidor.builder().nombre("Isabel").apellidos("Mora Cabrera").dni("44500600D").fechaNacimiento(LocalDate.of(1996, 9, 8)).genero('F').club("Karate Santander").federacionAutonomica("Cantabria").build();
        Competidor senF13 = Competidor.builder().nombre("Rebeca").apellidos("Pardo Montoya").dni("55600700E").fechaNacimiento(LocalDate.of(2002, 6, 25)).genero('F').club("Karate Extremadura").federacionAutonomica("Extremadura").build();
        Competidor senF14 = Competidor.builder().nombre("Verónica").apellidos("Cano Iglesias").dni("66700800F").fechaNacimiento(LocalDate.of(1999, 11, 14)).genero('F').club("Karate Valladolid").federacionAutonomica("Castilla y León").build();
        Competidor senF15 = Competidor.builder().nombre("Beatriz").apellidos("Prieto Alonso").dni("77800900G").fechaNacimiento(LocalDate.of(2003, 2, 7)).genero('F').club("Karate Aragón").federacionAutonomica("Aragón").build();
        Competidor senF16 = Competidor.builder().nombre("Sandra").apellidos("Gil Méndez").dni("88900000H").fechaNacimiento(LocalDate.of(1997, 8, 31)).genero('F').club("Karate Palma").federacionAutonomica("Islas Baleares").build();
        Competidor senF17 = Competidor.builder().nombre("Irene").apellidos("Santos Quintero").dni("99000111I").fechaNacimiento(LocalDate.of(2000, 5, 16)).genero('F').club("Karate Almería").federacionAutonomica("Andalucía").build();
        Competidor senF18 = Competidor.builder().nombre("Pilar").apellidos("Flores Navas").dni("10100200J").fechaNacimiento(LocalDate.of(2001, 1, 29)).genero('F').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();

        // --- CADETES MASCULINOS (nacidos 2010-2012, edad 14-15 en 2026) ---
        Competidor cadM1 = Competidor.builder().nombre("Mateo").apellidos("Reyes Gallardo").dni("11111101A").fechaNacimiento(LocalDate.of(2011, 3, 10)).genero('M').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor cadM2 = Competidor.builder().nombre("Samuel").apellidos("Vega Torres").dni("11111102B").fechaNacimiento(LocalDate.of(2010, 7, 22)).genero('M').club("Karate Sevilla").federacionAutonomica("Andalucía").build();
        Competidor cadM3 = Competidor.builder().nombre("Daniel").apellidos("Castro Morales").dni("11111103C").fechaNacimiento(LocalDate.of(2011, 11, 5)).genero('M').club("Karate Zaragoza").federacionAutonomica("Aragón").build();
        Competidor cadM4 = Competidor.builder().nombre("Lucas").apellidos("Jiménez Roca").dni("11111104D").fechaNacimiento(LocalDate.of(2010, 4, 18)).genero('M').club("Karate Bilbao").federacionAutonomica("País Vasco").build();
        Competidor cadM5 = Competidor.builder().nombre("Martín").apellidos("Ortega Sanz").dni("11111105E").fechaNacimiento(LocalDate.of(2012, 1, 30)).genero('M').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor cadM6 = Competidor.builder().nombre("Tomás").apellidos("Fuentes Moya").dni("11111106F").fechaNacimiento(LocalDate.of(2011, 8, 14)).genero('M').club("Karate Barcelona").federacionAutonomica("Cataluña").build();
        Competidor cadM7 = Competidor.builder().nombre("Ignacio").apellidos("Blanco Rueda").dni("11111107G").fechaNacimiento(LocalDate.of(2010, 12, 3)).genero('M').club("Karate Murcia").federacionAutonomica("Región de Murcia").build();
        Competidor cadM8 = Competidor.builder().nombre("Rodrigo").apellidos("Alonso Vera").dni("11111108H").fechaNacimiento(LocalDate.of(2012, 5, 27)).genero('M').club("Karate Valladolid").federacionAutonomica("Castilla y León").build();

        // --- CADETES FEMENINAS (nacidas 2010-2012) ---
        Competidor cadF1 = Competidor.builder().nombre("Carla").apellidos("Navarro Soler").dni("22222201A").fechaNacimiento(LocalDate.of(2011, 2, 15)).genero('F').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor cadF2 = Competidor.builder().nombre("Andrea").apellidos("Molina Ibáñez").dni("22222202B").fechaNacimiento(LocalDate.of(2010, 6, 9)).genero('F').club("Karate Granada").federacionAutonomica("Andalucía").build();
        Competidor cadF3 = Competidor.builder().nombre("Paula").apellidos("Herrero Cid").dni("22222203C").fechaNacimiento(LocalDate.of(2012, 10, 21)).genero('F').club("Karate Pamplona").federacionAutonomica("Comunidad Foral de Navarra").build();
        Competidor cadF4 = Competidor.builder().nombre("Alba").apellidos("Pinto Serrano").dni("22222204D").fechaNacimiento(LocalDate.of(2011, 4, 6)).genero('F').club("Karate Santander").federacionAutonomica("Cantabria").build();
        Competidor cadF5 = Competidor.builder().nombre("Nora").apellidos("Medina Rubio").dni("22222205E").fechaNacimiento(LocalDate.of(2010, 9, 28)).genero('F').club("Karate Logroño").federacionAutonomica("La Rioja").build();
        Competidor cadF6 = Competidor.builder().nombre("Julia").apellidos("Varela Peña").dni("22222206F").fechaNacimiento(LocalDate.of(2012, 3, 17)).genero('F').club("Karate Galicia").federacionAutonomica("Galicia").build();

        // --- JUNIORS MASCULINOS (nacidos 2008-2010, edad 16-17 en 2026) ---
        Competidor junM1 = Competidor.builder().nombre("Álex").apellidos("Romero Cuesta").dni("33333301A").fechaNacimiento(LocalDate.of(2009, 5, 12)).genero('M').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor junM2 = Competidor.builder().nombre("Iker").apellidos("Zubieta Larrea").dni("33333302B").fechaNacimiento(LocalDate.of(2008, 11, 25)).genero('M').club("Karate San Sebastián").federacionAutonomica("País Vasco").build();
        Competidor junM3 = Competidor.builder().nombre("Unai").apellidos("Arrizabalaga Olaiz").dni("33333303C").fechaNacimiento(LocalDate.of(2009, 3, 8)).genero('M').club("Karate Bilbao").federacionAutonomica("País Vasco").build();
        Competidor junM4 = Competidor.builder().nombre("Joel").apellidos("Giménez Puig").dni("33333304D").fechaNacimiento(LocalDate.of(2010, 1, 14)).genero('M').club("Karate Barcelona").federacionAutonomica("Cataluña").build();
        Competidor junM5 = Competidor.builder().nombre("Pol").apellidos("Soler Massana").dni("33333305E").fechaNacimiento(LocalDate.of(2009, 7, 30)).genero('M').club("Karate Lleida").federacionAutonomica("Cataluña").build();
        Competidor junM6 = Competidor.builder().nombre("Enrique").apellidos("Pascual Delgado").dni("33333306F").fechaNacimiento(LocalDate.of(2008, 9, 19)).genero('M').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor junM7 = Competidor.builder().nombre("Borja").apellidos("Cabrera Núñez").dni("33333307G").fechaNacimiento(LocalDate.of(2010, 4, 3)).genero('M').club("Karate Tenerife").federacionAutonomica("Canarias").build();
        Competidor junM8 = Competidor.builder().nombre("Jonás").apellidos("Ferrer Llorens").dni("33333308H").fechaNacimiento(LocalDate.of(2009, 12, 11)).genero('M').club("Karate Alicante").federacionAutonomica("Comunitat Valenciana").build();

        // --- JUNIORS FEMENINAS (nacidas 2008-2010) ---
        Competidor junF1 = Competidor.builder().nombre("Laia").apellidos("Bosch Martí").dni("44444401A").fechaNacimiento(LocalDate.of(2009, 2, 20)).genero('F').club("Karate Barcelona").federacionAutonomica("Cataluña").build();
        Competidor junF2 = Competidor.builder().nombre("Aroa").apellidos("Esteban Muñoz").dni("44444402B").fechaNacimiento(LocalDate.of(2008, 8, 7)).genero('F').club("Karate Zaragoza").federacionAutonomica("Aragón").build();
        Competidor junF3 = Competidor.builder().nombre("Itziar").apellidos("Mendoza Arrieta").dni("44444403C").fechaNacimiento(LocalDate.of(2010, 5, 15)).genero('F').club("Karate Vitoria").federacionAutonomica("País Vasco").build();
        Competidor junF4 = Competidor.builder().nombre("Amaia").apellidos("Garrido Urresti").dni("44444404D").fechaNacimiento(LocalDate.of(2009, 10, 29)).genero('F').club("Karate Pamplona").federacionAutonomica("Comunidad Foral de Navarra").build();
        Competidor junF5 = Competidor.builder().nombre("Mireia").apellidos("Coll Segura").dni("44444405E").fechaNacimiento(LocalDate.of(2008, 4, 3)).genero('F').club("Karate Tarragona").federacionAutonomica("Cataluña").build();
        Competidor junF6 = Competidor.builder().nombre("Naiara").apellidos("Bengoechea Landa").dni("44444406F").fechaNacimiento(LocalDate.of(2010, 1, 22)).genero('F').club("Karate Bilbao").federacionAutonomica("País Vasco").build();

        // --- SUB21 MASCULINOS (nacidos 2005-2008, edad 18-20 en 2026) ---
        Competidor sub21M1 = Competidor.builder().nombre("Mario").apellidos("Pena Álvarez").dni("55555501A").fechaNacimiento(LocalDate.of(2006, 4, 8)).genero('M').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor sub21M2 = Competidor.builder().nombre("Andrés").apellidos("Lara Espinosa").dni("55555502B").fechaNacimiento(LocalDate.of(2007, 9, 17)).genero('M').club("Karate Málaga").federacionAutonomica("Andalucía").build();
        Competidor sub21M3 = Competidor.builder().nombre("Guillermo").apellidos("Tejada Fuertes").dni("55555503C").fechaNacimiento(LocalDate.of(2005, 12, 30)).genero('M').club("Karate Aragón").federacionAutonomica("Aragón").build();
        Competidor sub21M4 = Competidor.builder().nombre("Héctor").apellidos("Montoya Castañeda").dni("55555504D").fechaNacimiento(LocalDate.of(2006, 7, 5)).genero('M').club("Karate Murcia").federacionAutonomica("Región de Murcia").build();
        Competidor sub21M5 = Competidor.builder().nombre("Lorenzo").apellidos("Gallego Pedrosa").dni("55555505E").fechaNacimiento(LocalDate.of(2007, 2, 19)).genero('M').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor sub21M6 = Competidor.builder().nombre("Nicolás").apellidos("Ibáñez Mena").dni("55555506F").fechaNacimiento(LocalDate.of(2005, 10, 11)).genero('M').club("Karate Barcelona").federacionAutonomica("Cataluña").build();
        Competidor sub21M7 = Competidor.builder().nombre("Jaime").apellidos("Paredes Ojeda").dni("55555507G").fechaNacimiento(LocalDate.of(2006, 1, 24)).genero('M').club("Karate Galicia").federacionAutonomica("Galicia").build();
        Competidor sub21M8 = Competidor.builder().nombre("Pelayo").apellidos("Menéndez Quirós").dni("55555508H").fechaNacimiento(LocalDate.of(2007, 6, 13)).genero('M').club("Karate Oviedo").federacionAutonomica("Principado de Asturias").build();

        // --- SUB21 FEMENINAS (nacidas 2005-2008) ---
        Competidor sub21F1 = Competidor.builder().nombre("Valeria").apellidos("Bravo Hidalgo").dni("66666601A").fechaNacimiento(LocalDate.of(2006, 3, 14)).genero('F').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor sub21F2 = Competidor.builder().nombre("Daniela").apellidos("Crespo Zamora").dni("66666602B").fechaNacimiento(LocalDate.of(2007, 8, 28)).genero('F').club("Karate Sevilla").federacionAutonomica("Andalucía").build();
        Competidor sub21F3 = Competidor.builder().nombre("Natalia").apellidos("Ríos Baena").dni("66666603C").fechaNacimiento(LocalDate.of(2005, 11, 3)).genero('F').club("Karate Cataluña").federacionAutonomica("Cataluña").build();
        Competidor sub21F4 = Competidor.builder().nombre("Leire").apellidos("Urrutia Ibarra").dni("66666604D").fechaNacimiento(LocalDate.of(2006, 5, 17)).genero('F').club("Karate San Sebastián").federacionAutonomica("País Vasco").build();
        Competidor sub21F5 = Competidor.builder().nombre("Rocío").apellidos("Delgado Peña").dni("66666605E").fechaNacimiento(LocalDate.of(2007, 1, 6)).genero('F').club("Karate Málaga").federacionAutonomica("Andalucía").build();
        Competidor sub21F6 = Competidor.builder().nombre("Ainhoa").apellidos("Txabarri Elorza").dni("66666606F").fechaNacimiento(LocalDate.of(2005, 7, 21)).genero('F').club("Karate Bilbao").federacionAutonomica("País Vasco").build();

        // --- INFANTILES MASCULINOS (nacidos 2014-2016, edad 10-11 en 2026) ---
        Competidor infM1 = Competidor.builder().nombre("Leo").apellidos("Muñoz Palma").dni("77777701A").fechaNacimiento(LocalDate.of(2015, 3, 5)).genero('M').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor infM2 = Competidor.builder().nombre("Marcos").apellidos("Rojo Calvo").dni("77777702B").fechaNacimiento(LocalDate.of(2014, 9, 18)).genero('M').club("Karate Sevilla").federacionAutonomica("Andalucía").build();
        Competidor infM3 = Competidor.builder().nombre("Adrián").apellidos("Lozano Rubio").dni("77777703C").fechaNacimiento(LocalDate.of(2015, 6, 12)).genero('M').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor infM4 = Competidor.builder().nombre("Ángel").apellidos("Pereira Barros").dni("77777704D").fechaNacimiento(LocalDate.of(2014, 12, 27)).genero('M').club("Karate Galicia").federacionAutonomica("Galicia").build();
        Competidor infM5 = Competidor.builder().nombre("Bruno").apellidos("Salas Acosta").dni("77777705E").fechaNacimiento(LocalDate.of(2015, 8, 4)).genero('M').club("Karate Aragón").federacionAutonomica("Aragón").build();
        Competidor infM6 = Competidor.builder().nombre("Kevin").apellidos("Ruiz Chávez").dni("77777706F").fechaNacimiento(LocalDate.of(2014, 5, 20)).genero('M').club("Karate Canarias").federacionAutonomica("Canarias").build();

        // --- INFANTILES FEMENINAS (nacidas 2014-2016) ---
        Competidor infF1 = Competidor.builder().nombre("Emma").apellidos("Blanco García").dni("88888801A").fechaNacimiento(LocalDate.of(2015, 1, 15)).genero('F').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor infF2 = Competidor.builder().nombre("Olivia").apellidos("Morales Vega").dni("88888802B").fechaNacimiento(LocalDate.of(2014, 7, 9)).genero('F').club("Karate Andalucía").federacionAutonomica("Andalucía").build();
        Competidor infF3 = Competidor.builder().nombre("Valentina").apellidos("Ríos Cano").dni("88888803C").fechaNacimiento(LocalDate.of(2015, 11, 23)).genero('F').club("Karate Cataluña").federacionAutonomica("Cataluña").build();
        Competidor infF4 = Competidor.builder().nombre("Sara").apellidos("Torrent Mas").dni("88888804D").fechaNacimiento(LocalDate.of(2014, 4, 6)).genero('F').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor infF5 = Competidor.builder().nombre("Chloe").apellidos("Martín Sousa").dni("88888805E").fechaNacimiento(LocalDate.of(2015, 9, 30)).genero('F').club("Karate Galicia").federacionAutonomica("Galicia").build();

        // --- ALEVINES MASCULINOS (nacidos 2016-2018, edad 8-9 en 2026) ---
        Competidor alevM1 = Competidor.builder().nombre("Ian").apellidos("Durán Pedraza").dni("99999901A").fechaNacimiento(LocalDate.of(2017, 4, 2)).genero('M').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor alevM2 = Competidor.builder().nombre("Izan").apellidos("Moreira Soto").dni("99999902B").fechaNacimiento(LocalDate.of(2016, 10, 16)).genero('M').club("Karate Andalucía").federacionAutonomica("Andalucía").build();
        Competidor alevM3 = Competidor.builder().nombre("Luca").apellidos("Ferreira Gomes").dni("99999903C").fechaNacimiento(LocalDate.of(2017, 7, 28)).genero('M').club("Karate Cataluña").federacionAutonomica("Cataluña").build();
        Competidor alevM4 = Competidor.builder().nombre("Dario").apellidos("Casas Moreno").dni("99999904D").fechaNacimiento(LocalDate.of(2016, 2, 11)).genero('M').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor alevM5 = Competidor.builder().nombre("Thiago").apellidos("Alves Costa").dni("99999905E").fechaNacimiento(LocalDate.of(2017, 12, 5)).genero('M').club("Karate Aragón").federacionAutonomica("Aragón").build();

        // --- ALEVINES FEMENINAS (nacidas 2016-2018) ---
        Competidor alevF1 = Competidor.builder().nombre("Martina").apellidos("Iglesias Pino").dni("10101001A").fechaNacimiento(LocalDate.of(2017, 3, 19)).genero('F').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor alevF2 = Competidor.builder().nombre("Vera").apellidos("Giménez Ruiz").dni("10101002B").fechaNacimiento(LocalDate.of(2016, 8, 7)).genero('F').club("Karate Sevilla").federacionAutonomica("Andalucía").build();
        Competidor alevF3 = Competidor.builder().nombre("Giulia").apellidos("Romano Pons").dni("10101003C").fechaNacimiento(LocalDate.of(2017, 1, 24)).genero('F').club("Karate Cataluña").federacionAutonomica("Cataluña").build();
        Competidor alevF4 = Competidor.builder().nombre("Asia").apellidos("Delgado Leal").dni("10101004D").fechaNacimiento(LocalDate.of(2016, 6, 13)).genero('F').club("Karate Canarias").federacionAutonomica("Canarias").build();

        // --- JUVENILES MASCULINOS (nacidos 2012-2014, edad 12-13 en 2026) ---
        Competidor juvM1 = Competidor.builder().nombre("Kai").apellidos("Santos Flores").dni("20202001A").fechaNacimiento(LocalDate.of(2013, 5, 6)).genero('M').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor juvM2 = Competidor.builder().nombre("Erik").apellidos("Palomar Cid").dni("20202002B").fechaNacimiento(LocalDate.of(2012, 11, 20)).genero('M').club("Karate Cataluña").federacionAutonomica("Cataluña").build();
        Competidor juvM3 = Competidor.builder().nombre("Nil").apellidos("Ferret Compte").dni("20202003C").fechaNacimiento(LocalDate.of(2013, 8, 14)).genero('M').club("Karate Girona").federacionAutonomica("Cataluña").build();
        Competidor juvM4 = Competidor.builder().nombre("Marc").apellidos("Puig Serra").dni("20202004D").fechaNacimiento(LocalDate.of(2012, 4, 3)).genero('M').club("Karate Baleares").federacionAutonomica("Islas Baleares").build();
        Competidor juvM5 = Competidor.builder().nombre("Noel").apellidos("Vázquez Castro").dni("20202005E").fechaNacimiento(LocalDate.of(2013, 1, 27)).genero('M').club("Karate Galicia").federacionAutonomica("Galicia").build();
        Competidor juvM6 = Competidor.builder().nombre("Nico").apellidos("Herrero Piña").dni("20202006F").fechaNacimiento(LocalDate.of(2012, 9, 8)).genero('M').club("Karate Castilla").federacionAutonomica("Castilla y León").build();

        // --- JUVENILES FEMENINAS (nacidas 2012-2014) ---
        Competidor juvF1 = Competidor.builder().nombre("Inés").apellidos("Mora Bernal").dni("30303001A").fechaNacimiento(LocalDate.of(2013, 4, 10)).genero('F').club("Karate Madrid").federacionAutonomica("Comunidad de Madrid").build();
        Competidor juvF2 = Competidor.builder().nombre("Ayla").apellidos("Fernández Ruiz").dni("30303002B").fechaNacimiento(LocalDate.of(2012, 10, 24)).genero('F').club("Karate Andalucía").federacionAutonomica("Andalucía").build();
        Competidor juvF3 = Competidor.builder().nombre("Noa").apellidos("Rivas Llopis").dni("30303003C").fechaNacimiento(LocalDate.of(2013, 7, 18)).genero('F').club("Karate Valencia").federacionAutonomica("Comunitat Valenciana").build();
        Competidor juvF4 = Competidor.builder().nombre("Zoe").apellidos("Andrade Lima").dni("30303004D").fechaNacimiento(LocalDate.of(2012, 3, 5)).genero('F').club("Karate Canarias").federacionAutonomica("Canarias").build();
        Competidor juvF5 = Competidor.builder().nombre("Hana").apellidos("Muñoz Takahashi").dni("30303005E").fechaNacimiento(LocalDate.of(2013, 12, 1)).genero('F').club("Karate Aragón").federacionAutonomica("Aragón").build();

        // Guardar todos los competidores
        competidorRepository.saveAll(List.of(
                senM1, senM2, senM3, senM4, senM5, senM6, senM7, senM8, senM9, senM10,
                senM11, senM12, senM13, senM14, senM15, senM16, senM17, senM18, senM19, senM20,
                senM21, senM22, senM23, senM24,
                senF1, senF2, senF3, senF4, senF5, senF6, senF7, senF8, senF9, senF10,
                senF11, senF12, senF13, senF14, senF15, senF16, senF17, senF18,
                cadM1, cadM2, cadM3, cadM4, cadM5, cadM6, cadM7, cadM8,
                cadF1, cadF2, cadF3, cadF4, cadF5, cadF6,
                junM1, junM2, junM3, junM4, junM5, junM6, junM7, junM8,
                junF1, junF2, junF3, junF4, junF5, junF6,
                sub21M1, sub21M2, sub21M3, sub21M4, sub21M5, sub21M6, sub21M7, sub21M8,
                sub21F1, sub21F2, sub21F3, sub21F4, sub21F5, sub21F6,
                infM1, infM2, infM3, infM4, infM5, infM6,
                infF1, infF2, infF3, infF4, infF5,
                alevM1, alevM2, alevM3, alevM4, alevM5,
                alevF1, alevF2, alevF3, alevF4,
                juvM1, juvM2, juvM3, juvM4, juvM5, juvM6,
                juvF1, juvF2, juvF3, juvF4, juvF5
        ));


        // INSCRIPCIONES DE EJEMPLO
        List<Inscripcion> inscripciones = new ArrayList<>();

        // Helper para crear inscripción
        // inscripcion(campeonato, categoria, competidor)

        // --- CAMP2: Campeonato España Senior (pasado, feb 2026) ---
        // Senior Masculino Kata
        inscripciones.add(ins(camp2, catSenKatM, senM1)); inscripciones.add(ins(camp2, catSenKatM, senM2));
        inscripciones.add(ins(camp2, catSenKatM, senM3)); inscripciones.add(ins(camp2, catSenKatM, senM4));
        inscripciones.add(ins(camp2, catSenKatM, senM5)); inscripciones.add(ins(camp2, catSenKatM, senM6));
        inscripciones.add(ins(camp2, catSenKatM, senM7));
        // Senior Femenino Kata
        inscripciones.add(ins(camp2, catSenKatF, senF1)); inscripciones.add(ins(camp2, catSenKatF, senF2));
        inscripciones.add(ins(camp2, catSenKatF, senF3)); inscripciones.add(ins(camp2, catSenKatF, senF4));
        inscripciones.add(ins(camp2, catSenKatF, senF5)); inscripciones.add(ins(camp2, catSenKatF, senF6));
        // Senior Masculino <67kg
        inscripciones.add(ins(camp2, catSenKumM2, senM8)); inscripciones.add(ins(camp2, catSenKumM2, senM9));
        inscripciones.add(ins(camp2, catSenKumM2, senM10)); inscripciones.add(ins(camp2, catSenKumM2, senM11));
        inscripciones.add(ins(camp2, catSenKumM2, senM12)); inscripciones.add(ins(camp2, catSenKumM2, senM13));
        // Senior Masculino <75kg
        inscripciones.add(ins(camp2, catSenKumM3, senM14)); inscripciones.add(ins(camp2, catSenKumM3, senM15));
        inscripciones.add(ins(camp2, catSenKumM3, senM16)); inscripciones.add(ins(camp2, catSenKumM3, senM17));
        inscripciones.add(ins(camp2, catSenKumM3, senM18)); inscripciones.add(ins(camp2, catSenKumM3, senM19));
        // Senior Masculino <84kg
        inscripciones.add(ins(camp2, catSenKumM4, senM20)); inscripciones.add(ins(camp2, catSenKumM4, senM21));
        inscripciones.add(ins(camp2, catSenKumM4, senM22)); inscripciones.add(ins(camp2, catSenKumM4, senM23));
        inscripciones.add(ins(camp2, catSenKumM4, senM24));
        // Senior Femenino <55kg
        inscripciones.add(ins(camp2, catSenKumF2, senF7)); inscripciones.add(ins(camp2, catSenKumF2, senF8));
        inscripciones.add(ins(camp2, catSenKumF2, senF9)); inscripciones.add(ins(camp2, catSenKumF2, senF10));
        inscripciones.add(ins(camp2, catSenKumF2, senF11)); inscripciones.add(ins(camp2, catSenKumF2, senF12));
        // Senior Femenino <61kg
        inscripciones.add(ins(camp2, catSenKumF3, senF13)); inscripciones.add(ins(camp2, catSenKumF3, senF14));
        inscripciones.add(ins(camp2, catSenKumF3, senF15)); inscripciones.add(ins(camp2, catSenKumF3, senF16));
        inscripciones.add(ins(camp2, catSenKumF3, senF17)); inscripciones.add(ins(camp2, catSenKumF3, senF18));

        // --- CAMP4: Campeonato Andalucía Senior (feb 2026, no solapa con camp2 que termina el 4) ---
        // Senior Masculino Kata
        inscripciones.add(ins(camp4, catSenKatM, senM1)); inscripciones.add(ins(camp4, catSenKatM, senM3));
        inscripciones.add(ins(camp4, catSenKatM, senM5)); inscripciones.add(ins(camp4, catSenKatM, senM7));
        inscripciones.add(ins(camp4, catSenKatM, senM9)); inscripciones.add(ins(camp4, catSenKatM, senM11));
        inscripciones.add(ins(camp4, catSenKatM, senM13));
        // Senior Femenino Kata
        inscripciones.add(ins(camp4, catSenKatF, senF1)); inscripciones.add(ins(camp4, catSenKatF, senF3));
        inscripciones.add(ins(camp4, catSenKatF, senF5)); inscripciones.add(ins(camp4, catSenKatF, senF7));
        inscripciones.add(ins(camp4, catSenKatF, senF9)); inscripciones.add(ins(camp4, catSenKatF, senF11));
        // Senior Masculino <75kg
        inscripciones.add(ins(camp4, catSenKumM3, senM2)); inscripciones.add(ins(camp4, catSenKumM3, senM4));
        inscripciones.add(ins(camp4, catSenKumM3, senM6)); inscripciones.add(ins(camp4, catSenKumM3, senM8));
        inscripciones.add(ins(camp4, catSenKumM3, senM10)); inscripciones.add(ins(camp4, catSenKumM3, senM12));
        // Senior Femenino <61kg
        inscripciones.add(ins(camp4, catSenKumF3, senF2)); inscripciones.add(ins(camp4, catSenKumF3, senF4));
        inscripciones.add(ins(camp4, catSenKumF3, senF6)); inscripciones.add(ins(camp4, catSenKumF3, senF8));
        inscripciones.add(ins(camp4, catSenKumF3, senF10)); inscripciones.add(ins(camp4, catSenKumF3, senF12));

        // --- CAMP15: Liga Nacional Masculina Absoluta/Cadete 1ª Ronda (17 ene 2026) ---
        // Cadete Masculino Kata
        inscripciones.add(ins(camp15, catCadKataM, cadM1)); inscripciones.add(ins(camp15, catCadKataM, cadM2));
        inscripciones.add(ins(camp15, catCadKataM, cadM3)); inscripciones.add(ins(camp15, catCadKataM, cadM4));
        inscripciones.add(ins(camp15, catCadKataM, cadM5)); inscripciones.add(ins(camp15, catCadKataM, cadM6));
        // Cadete Masculino <57kg
        inscripciones.add(ins(camp15, catCadKumM2, cadM7)); inscripciones.add(ins(camp15, catCadKumM2, cadM8));
        inscripciones.add(ins(camp15, catCadKumM2, cadM1)); inscripciones.add(ins(camp15, catCadKumM2, cadM3));
        inscripciones.add(ins(camp15, catCadKumM2, cadM5));
        // Senior Masculino <75kg
        inscripciones.add(ins(camp15, catSenKumM3, senM1)); inscripciones.add(ins(camp15, catSenKumM3, senM3));
        inscripciones.add(ins(camp15, catSenKumM3, senM5)); inscripciones.add(ins(camp15, catSenKumM3, senM7));
        inscripciones.add(ins(camp15, catSenKumM3, senM9)); inscripciones.add(ins(camp15, catSenKumM3, senM11));

        // --- CAMP16: Liga Iberdrola Absoluta/Cadete 1ª Ronda (18 ene 2026) ---
        // Cadete Femenino Kata
        inscripciones.add(ins(camp16, catCadKataF, cadF1)); inscripciones.add(ins(camp16, catCadKataF, cadF2));
        inscripciones.add(ins(camp16, catCadKataF, cadF3)); inscripciones.add(ins(camp16, catCadKataF, cadF4));
        inscripciones.add(ins(camp16, catCadKataF, cadF5)); inscripciones.add(ins(camp16, catCadKataF, cadF6));
        // Cadete Femenino <54kg
        inscripciones.add(ins(camp16, catCadKumF2, cadF1)); inscripciones.add(ins(camp16, catCadKumF2, cadF2));
        inscripciones.add(ins(camp16, catCadKumF2, cadF4)); inscripciones.add(ins(camp16, catCadKumF2, cadF5));
        // Senior Femenino <55kg
        inscripciones.add(ins(camp16, catSenKumF2, senF1)); inscripciones.add(ins(camp16, catSenKumF2, senF3));
        inscripciones.add(ins(camp16, catSenKumF2, senF5)); inscripciones.add(ins(camp16, catSenKumF2, senF7));
        inscripciones.add(ins(camp16, catSenKumF2, senF9)); inscripciones.add(ins(camp16, catSenKumF2, senF11));

        // --- CAMP17: Liga Nacional Masculina Alevín/Infantil/Juvenil 1ª Ronda (14 feb 2026) ---
        // Alevín Masculino Kata
        inscripciones.add(ins(camp17, catAlevKataM, alevM1)); inscripciones.add(ins(camp17, catAlevKataM, alevM2));
        inscripciones.add(ins(camp17, catAlevKataM, alevM3)); inscripciones.add(ins(camp17, catAlevKataM, alevM4));
        inscripciones.add(ins(camp17, catAlevKataM, alevM5));
        // Infantil Masculino Kata
        inscripciones.add(ins(camp17, catInfKataM, infM1)); inscripciones.add(ins(camp17, catInfKataM, infM2));
        inscripciones.add(ins(camp17, catInfKataM, infM3)); inscripciones.add(ins(camp17, catInfKataM, infM4));
        inscripciones.add(ins(camp17, catInfKataM, infM5)); inscripciones.add(ins(camp17, catInfKataM, infM6));
        // Juvenil Masculino Kata
        inscripciones.add(ins(camp17, catJuvKataM, juvM1)); inscripciones.add(ins(camp17, catJuvKataM, juvM2));
        inscripciones.add(ins(camp17, catJuvKataM, juvM3)); inscripciones.add(ins(camp17, catJuvKataM, juvM4));
        inscripciones.add(ins(camp17, catJuvKataM, juvM5)); inscripciones.add(ins(camp17, catJuvKataM, juvM6));
        // Juvenil Masculino <42kg
        inscripciones.add(ins(camp17, catJuvKumM2, juvM1)); inscripciones.add(ins(camp17, catJuvKumM2, juvM3));
        inscripciones.add(ins(camp17, catJuvKumM2, juvM5)); inscripciones.add(ins(camp17, catJuvKumM2, juvM2));
        inscripciones.add(ins(camp17, catJuvKumM2, juvM4));

        // --- CAMP18: Liga Iberdrola Alevín/Infantil/Juvenil 1ª Ronda (15 feb 2026) ---
        // Alevín Femenino Kata
        inscripciones.add(ins(camp18, catAlevKataF, alevF1)); inscripciones.add(ins(camp18, catAlevKataF, alevF2));
        inscripciones.add(ins(camp18, catAlevKataF, alevF3)); inscripciones.add(ins(camp18, catAlevKataF, alevF4));
        // Infantil Femenino Kata
        inscripciones.add(ins(camp18, catInfKataF, infF1)); inscripciones.add(ins(camp18, catInfKataF, infF2));
        inscripciones.add(ins(camp18, catInfKataF, infF3)); inscripciones.add(ins(camp18, catInfKataF, infF4));
        inscripciones.add(ins(camp18, catInfKataF, infF5));
        // Juvenil Femenino Kata
        inscripciones.add(ins(camp18, catJuvKataF, juvF1)); inscripciones.add(ins(camp18, catJuvKataF, juvF2));
        inscripciones.add(ins(camp18, catJuvKataF, juvF3)); inscripciones.add(ins(camp18, catJuvKataF, juvF4));
        inscripciones.add(ins(camp18, catJuvKataF, juvF5));
        // Juvenil Femenino <42kg
        inscripciones.add(ins(camp18, catJuvKumF2, juvF1)); inscripciones.add(ins(camp18, catJuvKumF2, juvF2));
        inscripciones.add(ins(camp18, catJuvKumF2, juvF3)); inscripciones.add(ins(camp18, catJuvKumF2, juvF4));

        // --- CAMP3: Campeonato Andalucía Alevín/Infantil/Juvenil (1 mar 2026) ---
        // Alevín Masculino Kata
        inscripciones.add(ins(camp3, catAlevKataM, alevM1)); inscripciones.add(ins(camp3, catAlevKataM, alevM2));
        inscripciones.add(ins(camp3, catAlevKataM, alevM3)); inscripciones.add(ins(camp3, catAlevKataM, alevM4));
        inscripciones.add(ins(camp3, catAlevKataM, alevM5));
        // Alevín Femenino Kata
        inscripciones.add(ins(camp3, catAlevKataF, alevF1)); inscripciones.add(ins(camp3, catAlevKataF, alevF2));
        inscripciones.add(ins(camp3, catAlevKataF, alevF3)); inscripciones.add(ins(camp3, catAlevKataF, alevF4));
        // Infantil Masculino Kata
        inscripciones.add(ins(camp3, catInfKataM, infM1)); inscripciones.add(ins(camp3, catInfKataM, infM2));
        inscripciones.add(ins(camp3, catInfKataM, infM3)); inscripciones.add(ins(camp3, catInfKataM, infM4));
        inscripciones.add(ins(camp3, catInfKataM, infM5)); inscripciones.add(ins(camp3, catInfKataM, infM6));
        // Infantil Femenino Kata
        inscripciones.add(ins(camp3, catInfKataF, infF1)); inscripciones.add(ins(camp3, catInfKataF, infF2));
        inscripciones.add(ins(camp3, catInfKataF, infF3)); inscripciones.add(ins(camp3, catInfKataF, infF4));
        inscripciones.add(ins(camp3, catInfKataF, infF5));
        // Juvenil Masculino Kata
        inscripciones.add(ins(camp3, catJuvKataM, juvM1)); inscripciones.add(ins(camp3, catJuvKataM, juvM2));
        inscripciones.add(ins(camp3, catJuvKataM, juvM3)); inscripciones.add(ins(camp3, catJuvKataM, juvM4));
        inscripciones.add(ins(camp3, catJuvKataM, juvM5)); inscripciones.add(ins(camp3, catJuvKataM, juvM6));
        // Juvenil Femenino Kata
        inscripciones.add(ins(camp3, catJuvKataF, juvF1)); inscripciones.add(ins(camp3, catJuvKataF, juvF2));
        inscripciones.add(ins(camp3, catJuvKataF, juvF3)); inscripciones.add(ins(camp3, catJuvKataF, juvF4));
        inscripciones.add(ins(camp3, catJuvKataF, juvF5));

        // --- CAMP6: Liga Nacional Cadete/Junior/Sub21 J4 (7-9 mar 2026) ---
        // Cadete Masculino Kata
        inscripciones.add(ins(camp6, catCadKataM, cadM1)); inscripciones.add(ins(camp6, catCadKataM, cadM2));
        inscripciones.add(ins(camp6, catCadKataM, cadM3)); inscripciones.add(ins(camp6, catCadKataM, cadM4));
        inscripciones.add(ins(camp6, catCadKataM, cadM5)); inscripciones.add(ins(camp6, catCadKataM, cadM6));
        // Cadete Femenino Kata
        inscripciones.add(ins(camp6, catCadKataF, cadF1)); inscripciones.add(ins(camp6, catCadKataF, cadF2));
        inscripciones.add(ins(camp6, catCadKataF, cadF3)); inscripciones.add(ins(camp6, catCadKataF, cadF4));
        inscripciones.add(ins(camp6, catCadKataF, cadF5)); inscripciones.add(ins(camp6, catCadKataF, cadF6));
        // Junior Masculino Kata
        inscripciones.add(ins(camp6, catJunKatM, junM1)); inscripciones.add(ins(camp6, catJunKatM, junM2));
        inscripciones.add(ins(camp6, catJunKatM, junM3)); inscripciones.add(ins(camp6, catJunKatM, junM4));
        inscripciones.add(ins(camp6, catJunKatM, junM5)); inscripciones.add(ins(camp6, catJunKatM, junM6));
        // Junior Femenino Kata
        inscripciones.add(ins(camp6, catJunKatF, junF1)); inscripciones.add(ins(camp6, catJunKatF, junF2));
        inscripciones.add(ins(camp6, catJunKatF, junF3)); inscripciones.add(ins(camp6, catJunKatF, junF4));
        inscripciones.add(ins(camp6, catJunKatF, junF5)); inscripciones.add(ins(camp6, catJunKatF, junF6));
        // Junior Masculino <61kg
        inscripciones.add(ins(camp6, catJunKumM2, junM7)); inscripciones.add(ins(camp6, catJunKumM2, junM8));
        inscripciones.add(ins(camp6, catJunKumM2, junM1)); inscripciones.add(ins(camp6, catJunKumM2, junM3));
        inscripciones.add(ins(camp6, catJunKumM2, junM5));
        // Sub21 Masculino Kata
        inscripciones.add(ins(camp6, catSub21KatM, sub21M1)); inscripciones.add(ins(camp6, catSub21KatM, sub21M2));
        inscripciones.add(ins(camp6, catSub21KatM, sub21M3)); inscripciones.add(ins(camp6, catSub21KatM, sub21M4));
        inscripciones.add(ins(camp6, catSub21KatM, sub21M5)); inscripciones.add(ins(camp6, catSub21KatM, sub21M6));
        // Sub21 Femenino Kata
        inscripciones.add(ins(camp6, catSub21KatF, sub21F1)); inscripciones.add(ins(camp6, catSub21KatF, sub21F2));
        inscripciones.add(ins(camp6, catSub21KatF, sub21F3)); inscripciones.add(ins(camp6, catSub21KatF, sub21F4));
        inscripciones.add(ins(camp6, catSub21KatF, sub21F5)); inscripciones.add(ins(camp6, catSub21KatF, sub21F6));

        // --- CAMP11: Campeonato España Absoluto (27-29 mar 2026) ---
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

        // --- CAMP21: Liga Nacional Junior/Sub21 1ª Ronda (21-22 mar 2026) ---
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

        // --- CAMP22: Liga Iberdrola Junior/Sub21 1ª Ronda (4-5 abr 2026) ---
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

        // --- CAMP7: Campeonato España Master (10-12 abr 2026) ---
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

        // --- CAMP8: Campeonato España Universitario Granada (17-18 abr 2026) ---
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

        // --- CAMP9: Campeonato España Infantil (8-10 may 2026) ---
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

        // --- CAMP5: Campeonato España Universitario (27-30 may 2026) ---
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

        // --- CAMP10: Campeonato España Clubes (19-21 jun 2026) - todas las categorías ---
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

        /*// --- CAMP23: Liga Nacional Masculina Absoluta/Cadete 2ª Ronda (24 oct 2026) ---
        inscripciones.add(ins(camp23, catCadKataM, cadM2)); inscripciones.add(ins(camp23, catCadKataM, cadM4));
        inscripciones.add(ins(camp23, catCadKataM, cadM6)); inscripciones.add(ins(camp23, catCadKataM, cadM8));
        inscripciones.add(ins(camp23, catCadKataM, cadM1)); inscripciones.add(ins(camp23, catCadKataM, cadM3));
        inscripciones.add(ins(camp23, catSenKatM, senM2)); inscripciones.add(ins(camp23, catSenKatM, senM6));
        inscripciones.add(ins(camp23, catSenKatM, senM10)); inscripciones.add(ins(camp23, catSenKatM, senM14));
        inscripciones.add(ins(camp23, catSenKatM, senM18)); inscripciones.add(ins(camp23, catSenKatM, senM22));
        inscripciones.add(ins(camp23, catSenKumM2, senM3)); inscripciones.add(ins(camp23, catSenKumM2, senM7));
        inscripciones.add(ins(camp23, catSenKumM2, senM11)); inscripciones.add(ins(camp23, catSenKumM2, senM15));
        inscripciones.add(ins(camp23, catSenKumM2, senM19)); inscripciones.add(ins(camp23, catSenKumM2, senM23));

        // --- CAMP24: Liga Iberdrola Absoluta/Cadete 2ª Ronda (25 oct 2026) ---
        inscripciones.add(ins(camp24, catCadKataF, cadF2)); inscripciones.add(ins(camp24, catCadKataF, cadF4));
        inscripciones.add(ins(camp24, catCadKataF, cadF6)); inscripciones.add(ins(camp24, catCadKataF, cadF1));
        inscripciones.add(ins(camp24, catCadKataF, cadF3)); inscripciones.add(ins(camp24, catCadKataF, cadF5));
        inscripciones.add(ins(camp24, catSenKatF, senF2)); inscripciones.add(ins(camp24, catSenKatF, senF6));
        inscripciones.add(ins(camp24, catSenKatF, senF10)); inscripciones.add(ins(camp24, catSenKatF, senF14));
        inscripciones.add(ins(camp24, catSenKatF, senF18));
        inscripciones.add(ins(camp24, catSenKumF3, senF4)); inscripciones.add(ins(camp24, catSenKumF3, senF8));
        inscripciones.add(ins(camp24, catSenKumF3, senF12)); inscripciones.add(ins(camp24, catSenKumF3, senF16));
*/
        // --- CAMP1: Campeonato España Cadete/Junior/Sub21 (10-12 nov 2026) ---
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

        // --- CAMP14: Final Cadete/Junior/Sub21 (27-29 nov 2026) ---
        inscripciones.add(ins(camp14, catCadKataM, cadM2)); inscripciones.add(ins(camp14, catCadKataM, cadM4));
        inscripciones.add(ins(camp14, catCadKataM, cadM6)); inscripciones.add(ins(camp14, catCadKataM, cadM8));
        inscripciones.add(ins(camp14, catCadKataM, cadM1)); inscripciones.add(ins(camp14, catCadKataM, cadM3));
        inscripciones.add(ins(camp14, catCadKataF, cadF2)); inscripciones.add(ins(camp14, catCadKataF, cadF4));
        inscripciones.add(ins(camp14, catCadKataF, cadF6)); inscripciones.add(ins(camp14, catCadKataF, cadF1));
        inscripciones.add(ins(camp14, catCadKataF, cadF3));
        inscripciones.add(ins(camp14, catJunKatM, junM2)); inscripciones.add(ins(camp14, catJunKatM, junM4));
        inscripciones.add(ins(camp14, catJunKatM, junM6)); inscripciones.add(ins(camp14, catJunKatM, junM8));
        inscripciones.add(ins(camp14, catJunKatM, junM1)); inscripciones.add(ins(camp14, catJunKatM, junM3));
        inscripciones.add(ins(camp14, catJunKatF, junF2)); inscripciones.add(ins(camp14, catJunKatF, junF4));
        inscripciones.add(ins(camp14, catJunKatF, junF6)); inscripciones.add(ins(camp14, catJunKatF, junF1));
        inscripciones.add(ins(camp14, catJunKatF, junF3));
        inscripciones.add(ins(camp14, catSub21KatM, sub21M2)); inscripciones.add(ins(camp14, catSub21KatM, sub21M4));
        inscripciones.add(ins(camp14, catSub21KatM, sub21M6)); inscripciones.add(ins(camp14, catSub21KatM, sub21M8));
        inscripciones.add(ins(camp14, catSub21KatF, sub21F2)); inscripciones.add(ins(camp14, catSub21KatF, sub21F4));
        inscripciones.add(ins(camp14, catSub21KatF, sub21F6)); inscripciones.add(ins(camp14, catSub21KatF, sub21F1));
        inscripciones.add(ins(camp14, catSub21KatF, sub21F3));

        // --- CAMP13: Liga Master 1ª Ronda (14-15 mar 2026) ---
        inscripciones.add(ins(camp13, catSenKatM, senM4)); inscripciones.add(ins(camp13, catSenKatM, senM8));
        inscripciones.add(ins(camp13, catSenKatM, senM12)); inscripciones.add(ins(camp13, catSenKatM, senM16));
        inscripciones.add(ins(camp13, catSenKatM, senM20)); inscripciones.add(ins(camp13, catSenKatM, senM24));
        inscripciones.add(ins(camp13, catSenKatF, senF4)); inscripciones.add(ins(camp13, catSenKatF, senF8));
        inscripciones.add(ins(camp13, catSenKatF, senF12)); inscripciones.add(ins(camp13, catSenKatF, senF16));
        inscripciones.add(ins(camp13, catSenKumM5, senM1)); inscripciones.add(ins(camp13, catSenKumM5, senM5));
        inscripciones.add(ins(camp13, catSenKumM5, senM9)); inscripciones.add(ins(camp13, catSenKumM5, senM13));
        inscripciones.add(ins(camp13, catSenKumM5, senM17)); inscripciones.add(ins(camp13, catSenKumM5, senM21));

        inscripcionRepository.saveAll(inscripciones);


        // 4. CREAR ÁRBITROS
        Arbitro arb1 = Arbitro.builder().nombre("Luis").apellidos("Gómez Juez").licencia("LIC-2026-001").fecha_nacimiento(new Date()).categoria_Arbitral("Nacional").activo(true).build();
        arbitroRepository.save(arb1);

        // 5. CREAR COMBATES
        /*CombateId combateId1 = new CombateId(comp1.getId_competidor(), comp2.getId_competidor(), 1);
        Combate combate1 = Combate.builder().id(combateId1).ronda("Final").competidorRojo(comp1).competidorAzul(comp2).puntuacionRojo(3).puntuacionAzul(1).senshu("rojo").estado("finalizado").horaProgramada(LocalTime.of(10, 30)).horaInicioReal(LocalDateTime.now()).duracionSegundos(180).build();
        combateRepository.save(combate1);*/

        System.out.println("Carga de datos de Karate finalizada con éxito.");
    }

    //Este metodo crea las relaciones entre Campeonato y Categoria
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
                    .fechaInicio(campeonato.getFechaInicio())
                    .fechaFin(campeonato.getFechaFin())
                    .build());
        }
        return relaciones;
    }

    //Este metodo ayuda a crear las inscripciones de los competidores
    private Inscripcion ins(Campeonato campeonato, Categoria categoria, Competidor competidor) {
        return Inscripcion.builder()
                .id_inscripcion(new Inscripcion_Id(campeonato.getId_campeonato(), categoria.getId_categoria(), competidor.getId_competidor()))
                .campeonato(campeonato)
                .categoria(categoria)
                .competidor(competidor)
                .build();
    }
}