package com.lastcrm.gateway.web.rest;

import com.lastcrm.gateway.LastcrmApp;
import com.lastcrm.gateway.domain.Agente;
import com.lastcrm.gateway.repository.AgenteRepository;
import com.lastcrm.gateway.service.AgenteService;
import com.lastcrm.gateway.service.dto.AgenteDTO;
import com.lastcrm.gateway.service.mapper.AgenteMapper;
import com.lastcrm.gateway.web.rest.errors.ExceptionTranslator;
import com.lastcrm.gateway.service.dto.AgenteCriteria;
import com.lastcrm.gateway.service.AgenteQueryService;

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
 * Integration tests for the {@link AgenteResource} REST controller.
 */
@SpringBootTest(classes = LastcrmApp.class)
public class AgenteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_1 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_1 = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_2 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    @Autowired
    private AgenteRepository agenteRepository;

    @Autowired
    private AgenteMapper agenteMapper;

    @Autowired
    private AgenteService agenteService;

    @Autowired
    private AgenteQueryService agenteQueryService;

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

    private MockMvc restAgenteMockMvc;

    private Agente agente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AgenteResource agenteResource = new AgenteResource(agenteService, agenteQueryService);
        this.restAgenteMockMvc = MockMvcBuilders.standaloneSetup(agenteResource)
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
    public static Agente createEntity(EntityManager em) {
        Agente agente = new Agente()
            .nombre(DEFAULT_NOMBRE)
            .apellido1(DEFAULT_APELLIDO_1)
            .apellido2(DEFAULT_APELLIDO_2)
            .telefono(DEFAULT_TELEFONO);
        return agente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Agente createUpdatedEntity(EntityManager em) {
        Agente agente = new Agente()
            .nombre(UPDATED_NOMBRE)
            .apellido1(UPDATED_APELLIDO_1)
            .apellido2(UPDATED_APELLIDO_2)
            .telefono(UPDATED_TELEFONO);
        return agente;
    }

    @BeforeEach
    public void initTest() {
        agente = createEntity(em);
    }

    @Test
    @Transactional
    public void createAgente() throws Exception {
        int databaseSizeBeforeCreate = agenteRepository.findAll().size();

        // Create the Agente
        AgenteDTO agenteDTO = agenteMapper.toDto(agente);
        restAgenteMockMvc.perform(post("/api/agentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenteDTO)))
            .andExpect(status().isCreated());

        // Validate the Agente in the database
        List<Agente> agenteList = agenteRepository.findAll();
        assertThat(agenteList).hasSize(databaseSizeBeforeCreate + 1);
        Agente testAgente = agenteList.get(agenteList.size() - 1);
        assertThat(testAgente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAgente.getApellido1()).isEqualTo(DEFAULT_APELLIDO_1);
        assertThat(testAgente.getApellido2()).isEqualTo(DEFAULT_APELLIDO_2);
        assertThat(testAgente.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    public void createAgenteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = agenteRepository.findAll().size();

        // Create the Agente with an existing ID
        agente.setId(1L);
        AgenteDTO agenteDTO = agenteMapper.toDto(agente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAgenteMockMvc.perform(post("/api/agentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agente in the database
        List<Agente> agenteList = agenteRepository.findAll();
        assertThat(agenteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = agenteRepository.findAll().size();
        // set the field null
        agente.setNombre(null);

        // Create the Agente, which fails.
        AgenteDTO agenteDTO = agenteMapper.toDto(agente);

        restAgenteMockMvc.perform(post("/api/agentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenteDTO)))
            .andExpect(status().isBadRequest());

        List<Agente> agenteList = agenteRepository.findAll();
        assertThat(agenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellido1IsRequired() throws Exception {
        int databaseSizeBeforeTest = agenteRepository.findAll().size();
        // set the field null
        agente.setApellido1(null);

        // Create the Agente, which fails.
        AgenteDTO agenteDTO = agenteMapper.toDto(agente);

        restAgenteMockMvc.perform(post("/api/agentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenteDTO)))
            .andExpect(status().isBadRequest());

        List<Agente> agenteList = agenteRepository.findAll();
        assertThat(agenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelefonoIsRequired() throws Exception {
        int databaseSizeBeforeTest = agenteRepository.findAll().size();
        // set the field null
        agente.setTelefono(null);

        // Create the Agente, which fails.
        AgenteDTO agenteDTO = agenteMapper.toDto(agente);

        restAgenteMockMvc.perform(post("/api/agentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenteDTO)))
            .andExpect(status().isBadRequest());

        List<Agente> agenteList = agenteRepository.findAll();
        assertThat(agenteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAgentes() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList
        restAgenteMockMvc.perform(get("/api/agentes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido1").value(hasItem(DEFAULT_APELLIDO_1.toString())))
            .andExpect(jsonPath("$.[*].apellido2").value(hasItem(DEFAULT_APELLIDO_2.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())));
    }
    
    @Test
    @Transactional
    public void getAgente() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get the agente
        restAgenteMockMvc.perform(get("/api/agentes/{id}", agente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(agente.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido1").value(DEFAULT_APELLIDO_1.toString()))
            .andExpect(jsonPath("$.apellido2").value(DEFAULT_APELLIDO_2.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()));
    }

    @Test
    @Transactional
    public void getAllAgentesByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList where nombre equals to DEFAULT_NOMBRE
        defaultAgenteShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the agenteList where nombre equals to UPDATED_NOMBRE
        defaultAgenteShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllAgentesByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultAgenteShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the agenteList where nombre equals to UPDATED_NOMBRE
        defaultAgenteShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllAgentesByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList where nombre is not null
        defaultAgenteShouldBeFound("nombre.specified=true");

        // Get all the agenteList where nombre is null
        defaultAgenteShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgentesByApellido1IsEqualToSomething() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList where apellido1 equals to DEFAULT_APELLIDO_1
        defaultAgenteShouldBeFound("apellido1.equals=" + DEFAULT_APELLIDO_1);

        // Get all the agenteList where apellido1 equals to UPDATED_APELLIDO_1
        defaultAgenteShouldNotBeFound("apellido1.equals=" + UPDATED_APELLIDO_1);
    }

    @Test
    @Transactional
    public void getAllAgentesByApellido1IsInShouldWork() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList where apellido1 in DEFAULT_APELLIDO_1 or UPDATED_APELLIDO_1
        defaultAgenteShouldBeFound("apellido1.in=" + DEFAULT_APELLIDO_1 + "," + UPDATED_APELLIDO_1);

        // Get all the agenteList where apellido1 equals to UPDATED_APELLIDO_1
        defaultAgenteShouldNotBeFound("apellido1.in=" + UPDATED_APELLIDO_1);
    }

    @Test
    @Transactional
    public void getAllAgentesByApellido1IsNullOrNotNull() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList where apellido1 is not null
        defaultAgenteShouldBeFound("apellido1.specified=true");

        // Get all the agenteList where apellido1 is null
        defaultAgenteShouldNotBeFound("apellido1.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgentesByApellido2IsEqualToSomething() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList where apellido2 equals to DEFAULT_APELLIDO_2
        defaultAgenteShouldBeFound("apellido2.equals=" + DEFAULT_APELLIDO_2);

        // Get all the agenteList where apellido2 equals to UPDATED_APELLIDO_2
        defaultAgenteShouldNotBeFound("apellido2.equals=" + UPDATED_APELLIDO_2);
    }

    @Test
    @Transactional
    public void getAllAgentesByApellido2IsInShouldWork() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList where apellido2 in DEFAULT_APELLIDO_2 or UPDATED_APELLIDO_2
        defaultAgenteShouldBeFound("apellido2.in=" + DEFAULT_APELLIDO_2 + "," + UPDATED_APELLIDO_2);

        // Get all the agenteList where apellido2 equals to UPDATED_APELLIDO_2
        defaultAgenteShouldNotBeFound("apellido2.in=" + UPDATED_APELLIDO_2);
    }

    @Test
    @Transactional
    public void getAllAgentesByApellido2IsNullOrNotNull() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList where apellido2 is not null
        defaultAgenteShouldBeFound("apellido2.specified=true");

        // Get all the agenteList where apellido2 is null
        defaultAgenteShouldNotBeFound("apellido2.specified=false");
    }

    @Test
    @Transactional
    public void getAllAgentesByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList where telefono equals to DEFAULT_TELEFONO
        defaultAgenteShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the agenteList where telefono equals to UPDATED_TELEFONO
        defaultAgenteShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllAgentesByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultAgenteShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the agenteList where telefono equals to UPDATED_TELEFONO
        defaultAgenteShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllAgentesByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        // Get all the agenteList where telefono is not null
        defaultAgenteShouldBeFound("telefono.specified=true");

        // Get all the agenteList where telefono is null
        defaultAgenteShouldNotBeFound("telefono.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAgenteShouldBeFound(String filter) throws Exception {
        restAgenteMockMvc.perform(get("/api/agentes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(agente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido1").value(hasItem(DEFAULT_APELLIDO_1)))
            .andExpect(jsonPath("$.[*].apellido2").value(hasItem(DEFAULT_APELLIDO_2)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)));

        // Check, that the count call also returns 1
        restAgenteMockMvc.perform(get("/api/agentes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAgenteShouldNotBeFound(String filter) throws Exception {
        restAgenteMockMvc.perform(get("/api/agentes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAgenteMockMvc.perform(get("/api/agentes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAgente() throws Exception {
        // Get the agente
        restAgenteMockMvc.perform(get("/api/agentes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAgente() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        int databaseSizeBeforeUpdate = agenteRepository.findAll().size();

        // Update the agente
        Agente updatedAgente = agenteRepository.findById(agente.getId()).get();
        // Disconnect from session so that the updates on updatedAgente are not directly saved in db
        em.detach(updatedAgente);
        updatedAgente
            .nombre(UPDATED_NOMBRE)
            .apellido1(UPDATED_APELLIDO_1)
            .apellido2(UPDATED_APELLIDO_2)
            .telefono(UPDATED_TELEFONO);
        AgenteDTO agenteDTO = agenteMapper.toDto(updatedAgente);

        restAgenteMockMvc.perform(put("/api/agentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenteDTO)))
            .andExpect(status().isOk());

        // Validate the Agente in the database
        List<Agente> agenteList = agenteRepository.findAll();
        assertThat(agenteList).hasSize(databaseSizeBeforeUpdate);
        Agente testAgente = agenteList.get(agenteList.size() - 1);
        assertThat(testAgente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAgente.getApellido1()).isEqualTo(UPDATED_APELLIDO_1);
        assertThat(testAgente.getApellido2()).isEqualTo(UPDATED_APELLIDO_2);
        assertThat(testAgente.getTelefono()).isEqualTo(UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void updateNonExistingAgente() throws Exception {
        int databaseSizeBeforeUpdate = agenteRepository.findAll().size();

        // Create the Agente
        AgenteDTO agenteDTO = agenteMapper.toDto(agente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAgenteMockMvc.perform(put("/api/agentes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(agenteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Agente in the database
        List<Agente> agenteList = agenteRepository.findAll();
        assertThat(agenteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAgente() throws Exception {
        // Initialize the database
        agenteRepository.saveAndFlush(agente);

        int databaseSizeBeforeDelete = agenteRepository.findAll().size();

        // Delete the agente
        restAgenteMockMvc.perform(delete("/api/agentes/{id}", agente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Agente> agenteList = agenteRepository.findAll();
        assertThat(agenteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agente.class);
        Agente agente1 = new Agente();
        agente1.setId(1L);
        Agente agente2 = new Agente();
        agente2.setId(agente1.getId());
        assertThat(agente1).isEqualTo(agente2);
        agente2.setId(2L);
        assertThat(agente1).isNotEqualTo(agente2);
        agente1.setId(null);
        assertThat(agente1).isNotEqualTo(agente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AgenteDTO.class);
        AgenteDTO agenteDTO1 = new AgenteDTO();
        agenteDTO1.setId(1L);
        AgenteDTO agenteDTO2 = new AgenteDTO();
        assertThat(agenteDTO1).isNotEqualTo(agenteDTO2);
        agenteDTO2.setId(agenteDTO1.getId());
        assertThat(agenteDTO1).isEqualTo(agenteDTO2);
        agenteDTO2.setId(2L);
        assertThat(agenteDTO1).isNotEqualTo(agenteDTO2);
        agenteDTO1.setId(null);
        assertThat(agenteDTO1).isNotEqualTo(agenteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(agenteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(agenteMapper.fromId(null)).isNull();
    }
}
