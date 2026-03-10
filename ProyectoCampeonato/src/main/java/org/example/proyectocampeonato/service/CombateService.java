package org.example.proyectocampeonato.service;

import org.example.proyectocampeonato.dto.CombateDTO;
import org.example.proyectocampeonato.mapperDTO.DtoMapper;
import org.example.proyectocampeonato.excepcion.CampeonatoNotFoundException;
import org.example.proyectocampeonato.modelo.*;
import org.example.proyectocampeonato.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CombateService {

    private final CombateRepository combateRepository;
    private final CompetidorRepository competidorRepository;
    private final Campeonato_CategoriaRepository campeonatoCategoriaRepository;
    private final DtoMapper mapper;

    public CombateService(CombateRepository combateRepository,
                          CompetidorRepository competidorRepository,
                          Campeonato_CategoriaRepository campeonatoCategoriaRepository,
                          DtoMapper mapper) {
        this.combateRepository = combateRepository;
        this.competidorRepository = competidorRepository;
        this.campeonatoCategoriaRepository = campeonatoCategoriaRepository;
        this.mapper = mapper;
    }

    public List<CombateDTO> getAll() {
        return combateRepository.findAll().stream()
                .map(mapper::toCombateDTO)
                .collect(Collectors.toList());
    }

    public List<CombateDTO> getByCampeonatoCategoria(Long idCampeonato, Long idCategoria) {
        return combateRepository.findByIdIdCampeonatoAndIdIdCategoria(idCampeonato, idCategoria).stream()
                .map(mapper::toCombateDTO)
                .collect(Collectors.toList());
    }

    public List<CombateDTO> getByCompetidor(Long idCompetidor) {
        return combateRepository.findByCompetidor(idCompetidor).stream()
                .map(mapper::toCombateDTO)
                .collect(Collectors.toList());
    }

    public CombateDTO one(Combate_Id id) {
        return mapper.toCombateDTO(
                combateRepository.findById(id)
                        .orElseThrow(() -> new CampeonatoNotFoundException(id.getIdCampeonato()))
        );
    }

    @Transactional
    public CombateDTO save(CombateDTO dto) {
        Combate entidad = buildCombateFromDTO(dto);
        return mapper.toCombateDTO(combateRepository.save(entidad));
    }

    @Transactional
    public CombateDTO replace(Combate_Id id, CombateDTO dto) {
        return combateRepository.findById(id)
                .map(existing -> {
                    Combate entidad = buildCombateFromDTO(dto);
                    entidad.setId(id);
                    return mapper.toCombateDTO(combateRepository.save(entidad));
                })
                .orElseThrow(() -> new CampeonatoNotFoundException(id.getIdCampeonato()));
    }

    @Transactional
    public void delete(Combate_Id id) {
        if (!combateRepository.existsById(id)) {
            throw new CampeonatoNotFoundException(id.getIdCampeonato());
        }
        combateRepository.deleteById(id);
    }

    // ── helper ───────────────────────────────────────────────────────────────

    private Combate buildCombateFromDTO(CombateDTO dto) {
        Competidor rojo = competidorRepository.findById(dto.getIdCompetidorRojo())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Competidor rojo con id " + dto.getIdCompetidorRojo() + " no encontrado"));

        Campeonato_Categoria_Id ccId = new Campeonato_Categoria_Id(dto.getIdCampeonato(), dto.getIdCategoria());
        Campeonato_Categoria campeonatoCategoria = campeonatoCategoriaRepository.findById(ccId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No existe esa combinación de campeonato y categoría"));

        // Nueva PK: (campeonato, categoria, tatami, numeroCombate)
        Combate_Id id = new Combate_Id(
                dto.getIdCampeonato(),
                dto.getIdCategoria(),
                dto.getNumeroTatami(),
                dto.getNumeroCombate()
        );

        Combate.CombateBuilder builder = Combate.builder()
                .id(id)
                .ronda(dto.getRonda())
                .estado(dto.getEstado())
                .senshu(dto.getSenshu())
                .puntuacionRojo(dto.getPuntuacionRojo() != null ? dto.getPuntuacionRojo() : 0)
                .puntuacionAzul(dto.getPuntuacionAzul() != null ? dto.getPuntuacionAzul() : 0)
                .horaProgramada(dto.getHoraProgramada())
                .horaInicioReal(dto.getHoraInicioReal())
                .duracionSegundos(dto.getDuracionSegundos())
                .observaciones(dto.getObservaciones())
                .competidorRojo(rojo)
                .campeonatoCategoria(campeonatoCategoria);

        if (dto.getIdCompetidorAzul() != null) {
            Competidor azul = competidorRepository.findById(dto.getIdCompetidorAzul())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Competidor azul con id " + dto.getIdCompetidorAzul() + " no encontrado"));
            builder.competidorAzul(azul);
        }

        return builder.build();
    }
}