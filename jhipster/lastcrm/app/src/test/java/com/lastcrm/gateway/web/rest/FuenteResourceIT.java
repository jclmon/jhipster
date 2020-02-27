package com.lastcrm.gateway.web.rest;

import com.lastcrm.gateway.LastcrmApp;
import com.lastcrm.gateway.domain.Fuente;
import com.lastcrm.gateway.repository.FuenteRepository;
import com.lastcrm.gateway.service.FuenteService;
import com.lastcrm.gateway.service.dto.FuenteDTO;
import com.lastcrm.gateway.service.mapper.FuenteMapper;
import com.lastcrm.gateway.web.rest.errors.ExceptionTranslator;
import com.lastcrm.gateway.service.dto.FuenteCriteria;
import com.lastcrm.gateway.service.FuenteQueryService;

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
import java.util.List;

import static com.lastcrm.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FuenteResource} REST controller.
 */
@SpringBootTest(classes = LastcrmApp.class)
public class FuenteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private FuenteRepository fuenteRepository;

    @Autowired
    private FuenteMapper fuenteMapper;

    @Autowired
    private FuenteService fuenteService;

    @Autowired
    private FuenteQueryService fuenteQueryService;

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

    private MockMvc restFuenteMockMvc;

    private Fuente fuente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FuenteResource fuenteResource = new FuenteResource(fuenteService, fuenteQueryService);
        this.restFuenteMockMvc = MockMvcBuilders.standaloneSetup(fuenteResource)
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
    public static Fuente createEntity(EntityManager em) {
        Fuente fuente = new Fuente()
            .nombre(DEFAULT_NOMBRE);
        return fuente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fuente createUpdatedEntity(EntityManager em) {
        Fuente fuente = new Fuente()
            .nombre(UPDATED_NOMBRE);
        return fuente;
    }

    @BeforeEach
    public void initTest() {
        fuente = createEntity(em);
    }

    @Test
    @Transactional
    public void createFuente() throws Exception {
        int databaseSizeBeforeCreate = fuenteRepository.findAll().size();

        // Create the Fuente
        FuenteDTO fuenteDTO = fuenteMapper.toDto(fuente);
        restFuenteMockMvc.perform(post("/api/fuentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuenteDTO)))
            .andExpect(status().isCreated());

        // Validate the Fuente in the database
        List<Fuente> fuenteList = fuenteRepository.findAll();
        assertThat(fuenteList).hasSize(databaseSizeBeforeCreate + 1);
        Fuente testFuente = fuenteList.get(fuenteList.size() - 1);
        assertThat(testFuente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createFuenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fuenteRepository.findAll().size();

        // Create the Fuente with an existing ID
        fuente.setId(1L);
        FuenteDTO fuenteDTO = fuenteMapper.toDto(fuente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuenteMockMvc.perform(post("/api/fuentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fuente in the database
        List<Fuente> fuenteList = fuenteRepository.findAll();
        assertThat(fuenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = fuenteRepository.findAll().size();
        // set the field null
        fuente.setNombre(null);

        // Create the Fuente, which fails.
        FuenteDTO fuenteDTO = fuenteMapper.toDto(fuente);

        restFuenteMockMvc.perform(post("/api/fuentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuenteDTO)))
            .andExpect(status().isBadRequest());

        List<Fuente> fuenteList = fuenteRepository.findAll();
        assertThat(fuenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFuentes() throws Exception {
        // Initialize the database
        fuenteRepository.saveAndFlush(fuente);

        // Get all the fuenteList
        restFuenteMockMvc.perform(get("/api/fuentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getFuente() throws Exception {
        // Initialize the database
        fuenteRepository.saveAndFlush(fuente);

        // Get the fuente
        restFuenteMockMvc.perform(get("/api/fuentes/{id}", fuente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fuente.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getAllFuentesByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        fuenteRepository.saveAndFlush(fuente);

        // Get all the fuenteList where nombre equals to DEFAULT_NOMBRE
        defaultFuenteShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the fuenteList where nombre equals to UPDATED_NOMBRE
        defaultFuenteShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllFuentesByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        fuenteRepository.saveAndFlush(fuente);

        // Get all the fuenteList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultFuenteShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the fuenteList where nombre equals to UPDATED_NOMBRE
        defaultFuenteShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllFuentesByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        fuenteRepository.saveAndFlush(fuente);

        // Get all the fuenteList where nombre is not null
        defaultFuenteShouldBeFound("nombre.specified=true");

        // Get all the fuenteList where nombre is null
        defaultFuenteShouldNotBeFound("nombre.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFuenteShouldBeFound(String filter) throws Exception {
        restFuenteMockMvc.perform(get("/api/fuentes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));

        // Check, that the count call also returns 1
        restFuenteMockMvc.perform(get("/api/fuentes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFuenteShouldNotBeFound(String filter) throws Exception {
        restFuenteMockMvc.perform(get("/api/fuentes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFuenteMockMvc.perform(get("/api/fuentes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFuente() throws Exception {
        // Get the fuente
        restFuenteMockMvc.perform(get("/api/fuentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFuente() throws Exception {
        // Initialize the database
        fuenteRepository.saveAndFlush(fuente);

        int databaseSizeBeforeUpdate = fuenteRepository.findAll().size();

        // Update the fuente
        Fuente updatedFuente = fuenteRepository.findById(fuente.getId()).get();
        // Disconnect from session so that the updates on updatedFuente are not directly saved in db
        em.detach(updatedFuente);
        updatedFuente
            .nombre(UPDATED_NOMBRE);
        FuenteDTO fuenteDTO = fuenteMapper.toDto(updatedFuente);

        restFuenteMockMvc.perform(put("/api/fuentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuenteDTO)))
            .andExpect(status().isOk());

        // Validate the Fuente in the database
        List<Fuente> fuenteList = fuenteRepository.findAll();
        assertThat(fuenteList).hasSize(databaseSizeBeforeUpdate);
        Fuente testFuente = fuenteList.get(fuenteList.size() - 1);
        assertThat(testFuente.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingFuente() throws Exception {
        int databaseSizeBeforeUpdate = fuenteRepository.findAll().size();

        // Create the Fuente
        FuenteDTO fuenteDTO = fuenteMapper.toDto(fuente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuenteMockMvc.perform(put("/api/fuentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fuenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Fuente in the database
        List<Fuente> fuenteList = fuenteRepository.findAll();
        assertThat(fuenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFuente() throws Exception {
        // Initialize the database
        fuenteRepository.saveAndFlush(fuente);

        int databaseSizeBeforeDelete = fuenteRepository.findAll().size();

        // Delete the fuente
        restFuenteMockMvc.perform(delete("/api/fuentes/{id}", fuente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fuente> fuenteList = fuenteRepository.findAll();
        assertThat(fuenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fuente.class);
        Fuente fuente1 = new Fuente();
        fuente1.setId(1L);
        Fuente fuente2 = new Fuente();
        fuente2.setId(fuente1.getId());
        assertThat(fuente1).isEqualTo(fuente2);
        fuente2.setId(2L);
        assertThat(fuente1).isNotEqualTo(fuente2);
        fuente1.setId(null);
        assertThat(fuente1).isNotEqualTo(fuente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuenteDTO.class);
        FuenteDTO fuenteDTO1 = new FuenteDTO();
        fuenteDTO1.setId(1L);
        FuenteDTO fuenteDTO2 = new FuenteDTO();
        assertThat(fuenteDTO1).isNotEqualTo(fuenteDTO2);
        fuenteDTO2.setId(fuenteDTO1.getId());
        assertThat(fuenteDTO1).isEqualTo(fuenteDTO2);
        fuenteDTO2.setId(2L);
        assertThat(fuenteDTO1).isNotEqualTo(fuenteDTO2);
        fuenteDTO1.setId(null);
        assertThat(fuenteDTO1).isNotEqualTo(fuenteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fuenteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fuenteMapper.fromId(null)).isNull();
    }
}
