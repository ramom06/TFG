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


    @Override
    public void run(String... args) throws Exception {
        if (campeonatoRepository.count() == 0) {
            cargarDatos();
        }
    }

    private void cargarDatos() {
        System.out.println("Iniciando carga de datos del Campeonato de Karate...");


        // 2. CREAR CATEGORÍAS (Relacionadas con el Campeonato)

        // BENJAMIN
        Categoria catBenjM = Categoria.builder()
                .nombre("Benjamín Masculino")
                .modalidad("kata")
                .genero("M")
                .edadMinima(0)
                .edadMaxima(7)
                .build();

        Categoria catBenjF = Categoria.builder()
                .nombre("Benjamín Femenino")
                .modalidad("kata")
                .genero("F")
                .edadMinima(0)
                .edadMaxima(7)
                .build();

        List<Categoria> benjamin =  new ArrayList<>();
        benjamin.add(catBenjM);
        benjamin.add(catBenjF);

        //ALEVIN
        Categoria catAlevKataM = Categoria.builder()
                .nombre("Alevín Masculino Kata")
                .modalidad("kata")
                .genero("M")
                .edadMinima(8)
                .edadMaxima(9)
                .build();

        Categoria catAlevKataF = Categoria.builder()
                .nombre("Alevín Femenino Kata")
                .modalidad("kata")
                .genero("F")
                .edadMinima(8)
                .edadMaxima(9)
                .build();

        Categoria catAlevKumM1 = Categoria.builder()
                .nombre("Alevín Masculino <28kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(8)
                .edadMaxima(9)
                .pesoMaximo(28.0)
                .build();


        Categoria catAlevKumM2 = Categoria.builder()
                .nombre("Alevín Masculino <34kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(8)
                .edadMaxima(9)
                .pesoMaximo(34.0)
                .build();

        Categoria catAlevKumM3 = Categoria
                .builder()
                .nombre("Alevín Masculino >34kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(8)
                .edadMaxima(9)
                .pesoMinimo(34.0)
                .build();

        Categoria catAlevKumF1 = Categoria.builder()
                .nombre("Alevín Femenino <26kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(8)
                .edadMaxima(9)
                .pesoMaximo(26.0)
                .build();

        Categoria catAlevKumF2 = Categoria.builder()
                .nombre("Alevín Femenino <32kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(8)
                .edadMaxima(9)
                .pesoMaximo(32.0)
                .build();

        Categoria catAlevKumF3 = Categoria.builder()
                .nombre("Alevín Femenino >32kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(8)
                .edadMaxima(9)
                .pesoMinimo(32.0)
                .build();

        List<Categoria> alevines =   new ArrayList<>();
        alevines.add(catAlevKataM);
        alevines.add(catAlevKataF);
        alevines.add(catAlevKumM1);
        alevines.add(catAlevKumM2);
        alevines.add(catAlevKumM3);
        alevines.add(catAlevKumF1);
        alevines.add(catAlevKumF2);
        alevines.add(catAlevKumF3);


        //INFANTIL
        Categoria catInfKataM = Categoria.builder()
                .nombre("Infantil Masculino Kata")
                .modalidad("kata")
                .genero("M")
                .edadMinima(10)
                .edadMaxima(11)
                .build();

        Categoria catInfKataF = Categoria.builder()
                .nombre("Infantil Femenino Kata")
                .modalidad("kata")
                .genero("F")
                .edadMinima(10)
                .edadMaxima(11)
                .build();

        Categoria catInfKumM1 = Categoria.builder()
                .nombre("Infantil Masculino <30kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(10)
                .edadMaxima(11)
                .pesoMaximo(30.0)
                .build();

        Categoria catInfKumM2 = Categoria.builder()
                .nombre("Infantil Masculino <35kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(10)
                .edadMaxima(11)
                .pesoMaximo(35.0)
                .build();

        Categoria catInfKumM3 = Categoria.builder()
                .nombre("Infantil Masculino <40kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(10)
                .edadMaxima(11)
                .pesoMaximo(40.0)
                .build();

        Categoria catInfKumM4 = Categoria.builder()
                .nombre("Infantil Masculino <45kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(10)
                .edadMaxima(11)
                .pesoMaximo(45.0)
                .build();

        Categoria catInfKumM5 = Categoria.builder()
                .nombre("Infantil Masculino >45kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(10)
                .edadMaxima(11)
                .pesoMinimo(45.0)
                .build();

        Categoria catInfKumF1 = Categoria.builder()
                .nombre("Infantil Femenino <30kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(10)
                .edadMaxima(11)
                .pesoMaximo(30.0)
                .build();

        Categoria catInfKumF2 = Categoria.builder()
                .nombre("Infantil Femenino <36kg")
                .modalidad("kumite").genero("F")
                .edadMinima(10)
                .edadMaxima(11)
                .pesoMaximo(36.0)
                .build();

        Categoria catInfKumF3 = Categoria.builder()
                .nombre("Infantil Femenino <42kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(10)
                .edadMaxima(11)
                .pesoMaximo(42.0)
                .build();

        Categoria catInfKumF4 = Categoria.builder()
                .nombre("Infantil Femenino >42kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(10)
                .edadMaxima(11)
                .pesoMinimo(42.0)
                .build();


        //JUVENIL
        Categoria catJuvKataM = Categoria.builder()
                .nombre("Juvenil Masculino Kata")
                .modalidad("kata")
                .genero("M")
                .edadMinima(12)
                .edadMaxima(13)
                .build();

        Categoria catJuvKataF = Categoria.builder()
                .nombre("Juvenil Femenino Kata")
                .modalidad("kata")
                .genero("F")
                .edadMinima(12)
                .edadMaxima(13)
                .build();

        Categoria catJuvKumM1 = Categoria.builder()
                .nombre("Juvenil Masculino <36kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(12)
                .edadMaxima(13)
                .pesoMaximo(36.0)
                .build();

        Categoria catJuvKumM2 = Categoria.builder()
                .nombre("Juvenil Masculino <42kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(12)
                .edadMaxima(13)
                .pesoMaximo(42.0)
                .build();

        Categoria catJuvKumM3 = Categoria.builder()
                .nombre("Juvenil Masculino <48kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(12)
                .edadMaxima(13)
                .pesoMaximo(48.0)
                .build();

        Categoria catJuvKumM4 = Categoria.builder()
                .nombre("Juvenil Masculino <54kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(12)
                .edadMaxima(13)
                .pesoMaximo(54.0)
                .build();

        Categoria catJuvKumM5 = Categoria.builder()
                .nombre("Juvenil Masculino <60kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(12)
                .edadMaxima(13)
                .pesoMaximo(60.0)
                .build();

        Categoria catJuvKumM6 = Categoria.builder()
                .nombre("Juvenil Masculino >60kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(12)
                .edadMaxima(13)
                .pesoMinimo(60.0)
                .build();

        Categoria catJuvKumF1 = Categoria.builder()
                .nombre("Juvenil Femenino <37kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(12)
                .edadMaxima(13)
                .pesoMaximo(37.0)
                .build();

        Categoria catJuvKumF2 = Categoria.builder()
                .nombre("Juvenil Femenino <42kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(12)
                .edadMaxima(13)
                .pesoMaximo(42.0)
                .build();

        Categoria catJuvKumF3 = Categoria.builder()
                .nombre("Juvenil Femenino <47kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(12)
                .edadMaxima(13)
                .pesoMaximo(47.0)
                .build();

        Categoria catJuvKumF4 = Categoria.builder()
                .nombre("Juvenil Femenino <52kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(12)
                .edadMaxima(13)
                .pesoMaximo(52.0)
                .build();

        Categoria catJuvKumF5 = Categoria.builder()
                .nombre("Juvenil Femenino >52kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(12)
                .edadMaxima(13)
                .pesoMinimo(52.0)
                .build();


        //CADETE
        Categoria catCadKataM = Categoria.builder()
                .nombre("Cadete Masculino Kata")
                .modalidad("kata")
                .genero("M")
                .edadMinima(14)
                .edadMaxima(15)
                .build();

        Categoria catCadKataF = Categoria.builder()
                .nombre("Cadete Femenino Kata")
                .modalidad("kata")
                .genero("F")
                .edadMinima(14)
                .edadMaxima(15)
                .build();

        Categoria catCadKumM1 = Categoria.builder()
                .nombre("Cadete Masculino <52kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(14)
                .edadMaxima(15)
                .pesoMaximo(52.0)
                .build();

        Categoria catCadKumM2 = Categoria.builder()
                .nombre("Cadete Masculino <57kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(14)
                .edadMaxima(15)
                .pesoMaximo(57.0)
                .build();

        Categoria catCadKumM3 = Categoria.builder()
                .nombre("Cadete Masculino <63kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(14)
                .edadMaxima(15)
                .pesoMaximo(63.0)
                .build();

        Categoria catCadKumM4 = Categoria.builder()
                .nombre("Cadete Masculino <70kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(14)
                .edadMaxima(15)
                .pesoMaximo(70.0)
                .build();

        Categoria catCadKumM5 = Categoria.builder()
                .nombre("Cadete Masculino >70kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(14)
                .edadMaxima(15)
                .pesoMinimo(70.0)
                .build();

        Categoria catCadKumF1 = Categoria.builder()
                .nombre("Cadete Femenino <47kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(14)
                .edadMaxima(15)
                .pesoMaximo(47.0)
                .build();

        Categoria catCadKumF2 = Categoria.builder()
                .nombre("Cadete Femenino <54kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(14)
                .edadMaxima(15)
                .pesoMaximo(54.0)
                .build();

        Categoria catCadKumF3 = Categoria.builder()
                .nombre("Cadete Femenino <61kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(14)
                .edadMaxima(15)
                .pesoMaximo(61.0)
                .build();

        Categoria catCadKumF4 = Categoria.builder()
                .nombre("Cadete Femenino >61kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(14)
                .edadMaxima(15)
                .pesoMinimo(61.0)
                .build();


        //JUNIOR
        Categoria catJunKatM = Categoria.builder()
                .nombre("Junior Masculino Kata")
                .modalidad("kata")
                .genero("M")
                .edadMinima(16)
                .edadMaxima(17)
                .build();

        Categoria catJunKatF = Categoria.builder()
                .nombre("Junior Femenino Kata")
                .modalidad("kata")
                .genero("F")
                .edadMinima(16)
                .edadMaxima(17)
                .build();

        Categoria catJunKumM1 = Categoria.builder()
                .nombre("Junior Masculino <55kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(16)
                .edadMaxima(17)
                .pesoMaximo(55.0)
                .build();

        Categoria catJunKumM2 = Categoria.builder()
                .nombre("Junior Masculino <61kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(16)
                .edadMaxima(17)
                .pesoMaximo(61.0)
                .build();

        Categoria catJunKumM3 = Categoria.builder()
                .nombre("Junior Masculino <68kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(16)
                .edadMaxima(17)
                .pesoMaximo(68.0)
                .build();

        Categoria catJunKumM4 = Categoria.builder()
                .nombre("Junior Masculino <76kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(16)
                .edadMaxima(17)
                .pesoMaximo(76.0)
                .build();

        Categoria catJunKumM5 = Categoria.builder()
                .nombre("Junior Masculino >76kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(16)
                .edadMaxima(17)
                .pesoMinimo(76.0)
                .build();

        Categoria catJunKumF1 = Categoria.builder()
                .nombre("Junior Femenino <48kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(16)
                .edadMaxima(17)
                .pesoMaximo(48.0)
                .build();

        Categoria catJunKumF2 = Categoria.builder()
                .nombre("Junior Femenino <53kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(16)
                .edadMaxima(17)
                .pesoMaximo(53.0)
                .build();

        Categoria catJunKumF3 = Categoria.builder()
                .nombre("Junior Femenino <59kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(16)
                .edadMaxima(17)
                .pesoMaximo(59.0)
                .build();

        Categoria catJunKumF4 = Categoria.builder()
                .nombre("Junior Femenino <66kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(16)
                .edadMaxima(17)
                .pesoMaximo(66.0)
                .build();

        Categoria catJunKumF5 = Categoria.builder()
                .nombre("Junior Femenino >66kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(16)
                .edadMaxima(17)
                .pesoMinimo(66.0)
                .build();


        //SUB-21
        Categoria catSub21KatM = Categoria.builder()
                .nombre("Sub21 Masculino Kata")
                .modalidad("kata")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(20)
                .build();

        Categoria catSub21KatF = Categoria.builder()
                .nombre("Sub21 Femenino Kata")
                .modalidad("kata")
                .genero("F")
                .edadMinima(18)
                .edadMaxima(20)
                .build();

        Categoria catSub21KumM1 = Categoria.builder()
                .nombre("Sub21 Masculino <60kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(20)
                .pesoMaximo(60.0)
                .build();

        Categoria catSub21KumM2 = Categoria.builder()
                .nombre("Sub21 Masculino <67kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(20)
                .pesoMaximo(67.0)
                .build();

        Categoria catSub21KumM3 = Categoria.builder()
                .nombre("Sub21 Masculino <75kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(20)
                .pesoMaximo(75.0)
                .build();

        Categoria catSub21KumM4 = Categoria.builder()
                .nombre("Sub21 Masculino <84kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(20)
                .pesoMaximo(84.0)
                .build();

        Categoria catSub21KumM5 = Categoria.builder()
                .nombre("Sub21 Masculino >84kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(20)
                .pesoMinimo(84.0)
                .build();

        Categoria catSub21KumF1 = Categoria.builder()
                .nombre("Sub21 Femenino <50kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(18)
                .edadMaxima(20)
                .pesoMaximo(50.0)
                .build();

        Categoria catSub21KumF2 = Categoria.builder()
                .nombre("Sub21 Femenino <55kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(18)
                .edadMaxima(20)
                .pesoMaximo(55.0)
                .build();

        Categoria catSub21KumF3 = Categoria.builder()
                .nombre("Sub21 Femenino <61kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(18)
                .edadMaxima(20)
                .pesoMaximo(61.0)
                .build();

        Categoria catSub21KumF4 = Categoria.builder()
                .nombre("Sub21 Femenino <68kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(18)
                .edadMaxima(20)
                .pesoMaximo(68.0)
                .build();

        Categoria catSub21KumF5 = Categoria.builder()
                .nombre("Sub21 Femenino >68kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(18)
                .edadMaxima(20)
                .pesoMinimo(68.0)
                .build();


        //SENIOR
        Categoria catSenKatM = Categoria.builder()
                .nombre("Senior Masculino Kata")
                .modalidad("kata")
                .genero("M")
                .edadMinima(16)
                .edadMaxima(99)
                .build();

        Categoria catSenKatF = Categoria.builder()
                .nombre("Senior Femenino Kata")
                .modalidad("kata")
                .genero("F")
                .edadMinima(16)
                .edadMaxima(99)
                .build();

        Categoria catSenKumM1 = Categoria.builder()
                .nombre("Senior Masculino <60kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(99)
                .pesoMaximo(60.0)
                .build();

        Categoria catSenKumM2 = Categoria.builder()
                .nombre("Senior Masculino <67kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(99)
                .pesoMaximo(67.0)
                .build();

        Categoria catSenKumM3 = Categoria.builder()
                .nombre("Senior Masculino <75kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(99)
                .pesoMaximo(75.0)
                .build();

        Categoria catSenKumM4 = Categoria.builder()
                .nombre("Senior Masculino <84kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(99)
                .pesoMaximo(84.0)
                .build();

        Categoria catSenKumM5 = Categoria.builder()
                .nombre("Senior Masculino >84kg")
                .modalidad("kumite")
                .genero("M")
                .edadMinima(18)
                .edadMaxima(99)
                .pesoMinimo(84.0)
                .build();

        Categoria catSenKumF1 = Categoria.builder()
                .nombre("Senior Femenino <50kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(18)
                .edadMaxima(99)
                .pesoMaximo(50.0)
                .build();

        Categoria catSenKumF2 = Categoria.builder()
                .nombre("Senior Femenino <55kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(18)
                .edadMaxima(99)
                .pesoMaximo(55.0)
                .build();

        Categoria catSenKumF3 = Categoria.builder()
                .nombre("Senior Femenino <61kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(18)
                .edadMaxima(99)
                .pesoMaximo(61.0)
                .build();

        Categoria catSenKumF4 = Categoria.builder()
                .nombre("Senior Femenino <68kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(18)
                .edadMaxima(99)
                .pesoMaximo(68.0)
                .build();

        Categoria catSenKumF5 = Categoria.builder()
                .nombre("Senior Femenino >68kg")
                .modalidad("kumite")
                .genero("F")
                .edadMinima(18)
                .edadMaxima(99)
                .pesoMinimo(68.0)
                .build();



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
