package com.lastcrm.gateway.web.rest;

import com.lastcrm.gateway.LastcrmApp;
import com.lastcrm.gateway.domain.Cita;
import com.lastcrm.gateway.domain.Agente;
import com.lastcrm.gateway.repository.CitaRepository;
import com.lastcrm.gateway.service.CitaService;
import com.lastcrm.gateway.service.dto.CitaDTO;
import com.lastcrm.gateway.service.mapper.CitaMapper;
import com.lastcrm.gateway.web.rest.errors.ExceptionTranslator;
import com.lastcrm.gateway.service.dto.CitaCriteria;
import com.lastcrm.gateway.service.CitaQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.lastcrm.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lastcrm.gateway.domain.enumeration.Estado;
/**
 * Integration tests for the {@link CitaResource} REST controller.
 */
@SpringBootTest(classes = LastcrmApp.class)
public class CitaResourceIT {

    private static final Instant DEFAULT_FECHA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA = Instant.now().truncatedTo(ChronoUnit.MILLIS);
    private static final Instant SMALLER_FECHA = Instant.ofEpochMilli(-1L);

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Estado DEFAULT_ESTADO = Estado.PENDIENTE;
    private static final Estado UPDATED_ESTADO = Estado.REALIZADA;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private CitaMapper citaMapper;

    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaQueryService citaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCitaMockMvc;

    private Cita cita;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CitaResource citaResource = new CitaResource(citaService, citaQueryService);
        this.restCitaMockMvc = MockMvcBuilders.standaloneSetup(citaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cita createEntity(EntityManager em) {
        Cita cita = new Cita()
            .fecha(DEFAULT_FECHA)
            .comentario(DEFAULT_COMENTARIO)
            .estado(DEFAULT_ESTADO);
        // Add required entity
        Agente agente;
        if (TestUtil.findAll(em, Agente.class).isEmpty()) {
            agente = AgenteResourceIT.createEntity(em);
            em.persist(agente);
            em.flush();
        } else {
            agente = TestUtil.findAll(em, Agente.class).get(0);
        }
        cita.setAgente(agente);
        return cita;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cita createUpdatedEntity(EntityManager em) {
        Cita cita = new Cita()
            .fecha(UPDATED_FECHA)
            .comentario(UPDATED_COMENTARIO)
            .estado(UPDATED_ESTADO);
        // Add required entity
        Agente agente;
        if (TestUtil.findAll(em, Agente.class).isEmpty()) {
            agente = AgenteResourceIT.createUpdatedEntity(em);
            em.persist(agente);
            em.flush();
        } else {
            agente = TestUtil.findAll(em, Agente.class).get(0);
        }
        cita.setAgente(agente);
        return cita;
    }

    @BeforeEach
    public void initTest() {
        cita = createEntity(em);
    }

    @Test
    @Transactional
    public void createCita() throws Exception {
        int databaseSizeBeforeCreate = citaRepository.findAll().size();

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);
        restCitaMockMvc.perform(post("/api/citas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citaDTO)))
            .andExpect(status().isCreated());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeCreate + 1);
        Cita testCita = citaList.get(citaList.size() - 1);
        assertThat(testCita.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testCita.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testCita.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    public void createCitaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = citaRepository.findAll().size();

        // Create the Cita with an existing ID
        cita.setId(1L);
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitaMockMvc.perform(post("/api/citas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkFechaIsRequired() throws Exception {
        int databaseSizeBeforeTest = citaRepository.findAll().size();
        // set the field null
        cita.setFecha(null);

        // Create the Cita, which fails.
        CitaDTO citaDTO = citaMapper.toDto(cita);

        restCitaMockMvc.perform(post("/api/citas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citaDTO)))
            .andExpect(status().isBadRequest());

        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = citaRepository.findAll().size();
        // set the field null
        cita.setEstado(null);

        // Create the Cita, which fails.
        CitaDTO citaDTO = citaMapper.toDto(cita);

        restCitaMockMvc.perform(post("/api/citas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citaDTO)))
            .andExpect(status().isBadRequest());

        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCitas() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get all the citaList
        restCitaMockMvc.perform(get("/api/citas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cita.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));
    }
    
    @Test
    @Transactional
    public void getCita() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get the cita
        restCitaMockMvc.perform(get("/api/citas/{id}", cita.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cita.getId().intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()));
    }

    @Test
    @Transactional
    public void getAllCitasByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get all the citaList where fecha equals to DEFAULT_FECHA
        defaultCitaShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the citaList where fecha equals to UPDATED_FECHA
        defaultCitaShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCitasByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get all the citaList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultCitaShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the citaList where fecha equals to UPDATED_FECHA
        defaultCitaShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllCitasByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get all the citaList where fecha is not null
        defaultCitaShouldBeFound("fecha.specified=true");

        // Get all the citaList where fecha is null
        defaultCitaShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllCitasByComentarioIsEqualToSomething() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get all the citaList where comentario equals to DEFAULT_COMENTARIO
        defaultCitaShouldBeFound("comentario.equals=" + DEFAULT_COMENTARIO);

        // Get all the citaList where comentario equals to UPDATED_COMENTARIO
        defaultCitaShouldNotBeFound("comentario.equals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllCitasByComentarioIsInShouldWork() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get all the citaList where comentario in DEFAULT_COMENTARIO or UPDATED_COMENTARIO
        defaultCitaShouldBeFound("comentario.in=" + DEFAULT_COMENTARIO + "," + UPDATED_COMENTARIO);

        // Get all the citaList where comentario equals to UPDATED_COMENTARIO
        defaultCitaShouldNotBeFound("comentario.in=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllCitasByComentarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get all the citaList where comentario is not null
        defaultCitaShouldBeFound("comentario.specified=true");

        // Get all the citaList where comentario is null
        defaultCitaShouldNotBeFound("comentario.specified=false");
    }

    @Test
    @Transactional
    public void getAllCitasByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get all the citaList where estado equals to DEFAULT_ESTADO
        defaultCitaShouldBeFound("estado.equals=" + DEFAULT_ESTADO);

        // Get all the citaList where estado equals to UPDATED_ESTADO
        defaultCitaShouldNotBeFound("estado.equals=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllCitasByEstadoIsInShouldWork() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get all the citaList where estado in DEFAULT_ESTADO or UPDATED_ESTADO
        defaultCitaShouldBeFound("estado.in=" + DEFAULT_ESTADO + "," + UPDATED_ESTADO);

        // Get all the citaList where estado equals to UPDATED_ESTADO
        defaultCitaShouldNotBeFound("estado.in=" + UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void getAllCitasByEstadoIsNullOrNotNull() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        // Get all the citaList where estado is not null
        defaultCitaShouldBeFound("estado.specified=true");

        // Get all the citaList where estado is null
        defaultCitaShouldNotBeFound("estado.specified=false");
    }

    @Test
    @Transactional
    public void getAllCitasByAgenteIsEqualToSomething() throws Exception {
        // Get already existing entity
        Agente agente = cita.getAgente();
        citaRepository.saveAndFlush(cita);
        Long agenteId = agente.getId();

        // Get all the citaList where agente equals to agenteId
        defaultCitaShouldBeFound("agenteId.equals=" + agenteId);

        // Get all the citaList where agente equals to agenteId + 1
        defaultCitaShouldNotBeFound("agenteId.equals=" + (agenteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCitaShouldBeFound(String filter) throws Exception {
        restCitaMockMvc.perform(get("/api/citas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cita.getId().intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())));

        // Check, that the count call also returns 1
        restCitaMockMvc.perform(get("/api/citas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCitaShouldNotBeFound(String filter) throws Exception {
        restCitaMockMvc.perform(get("/api/citas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCitaMockMvc.perform(get("/api/citas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCita() throws Exception {
        // Get the cita
        restCitaMockMvc.perform(get("/api/citas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCita() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        int databaseSizeBeforeUpdate = citaRepository.findAll().size();

        // Update the cita
        Cita updatedCita = citaRepository.findById(cita.getId()).get();
        // Disconnect from session so that the updates on updatedCita are not directly saved in db
        em.detach(updatedCita);
        updatedCita
            .fecha(UPDATED_FECHA)
            .comentario(UPDATED_COMENTARIO)
            .estado(UPDATED_ESTADO);
        CitaDTO citaDTO = citaMapper.toDto(updatedCita);

        restCitaMockMvc.perform(put("/api/citas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citaDTO)))
            .andExpect(status().isOk());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
        Cita testCita = citaList.get(citaList.size() - 1);
        assertThat(testCita.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testCita.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testCita.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    public void updateNonExistingCita() throws Exception {
        int databaseSizeBeforeUpdate = citaRepository.findAll().size();

        // Create the Cita
        CitaDTO citaDTO = citaMapper.toDto(cita);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitaMockMvc.perform(put("/api/citas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(citaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cita in the database
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCita() throws Exception {
        // Initialize the database
        citaRepository.saveAndFlush(cita);

        int databaseSizeBeforeDelete = citaRepository.findAll().size();

        // Delete the cita
        restCitaMockMvc.perform(delete("/api/citas/{id}", cita.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cita> citaList = citaRepository.findAll();
        assertThat(citaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cita.class);
        Cita cita1 = new Cita();
        cita1.setId(1L);
        Cita cita2 = new Cita();
        cita2.setId(cita1.getId());
        assertThat(cita1).isEqualTo(cita2);
        cita2.setId(2L);
        assertThat(cita1).isNotEqualTo(cita2);
        cita1.setId(null);
        assertThat(cita1).isNotEqualTo(cita2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CitaDTO.class);
        CitaDTO citaDTO1 = new CitaDTO();
        citaDTO1.setId(1L);
        CitaDTO citaDTO2 = new CitaDTO();
        assertThat(citaDTO1).isNotEqualTo(citaDTO2);
        citaDTO2.setId(citaDTO1.getId());
        assertThat(citaDTO1).isEqualTo(citaDTO2);
        citaDTO2.setId(2L);
        assertThat(citaDTO1).isNotEqualTo(citaDTO2);
        citaDTO1.setId(null);
        assertThat(citaDTO1).isNotEqualTo(citaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(citaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(citaMapper.fromId(null)).isNull();
    }
}
