package com.lastcrm.gateway.web.rest;

import com.lastcrm.gateway.LastcrmApp;
import com.lastcrm.gateway.domain.Municipio;
import com.lastcrm.gateway.domain.Provincia;
import com.lastcrm.gateway.repository.MunicipioRepository;
import com.lastcrm.gateway.service.MunicipioService;
import com.lastcrm.gateway.service.dto.MunicipioDTO;
import com.lastcrm.gateway.service.mapper.MunicipioMapper;
import com.lastcrm.gateway.web.rest.errors.ExceptionTranslator;
import com.lastcrm.gateway.service.dto.MunicipioCriteria;
import com.lastcrm.gateway.service.MunicipioQueryService;

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
 * Integration tests for the {@link MunicipioResource} REST controller.
 */
@SpringBootTest(classes = LastcrmApp.class)
public class MunicipioResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_COD_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_COD_POSTAL = "BBBBBBBBBB";

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private MunicipioMapper municipioMapper;

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private MunicipioQueryService municipioQueryService;

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

    private MockMvc restMunicipioMockMvc;

    private Municipio municipio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MunicipioResource municipioResource = new MunicipioResource(municipioService, municipioQueryService);
        this.restMunicipioMockMvc = MockMvcBuilders.standaloneSetup(municipioResource)
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
    public static Municipio createEntity(EntityManager em) {
        Municipio municipio = new Municipio()
            .nombre(DEFAULT_NOMBRE)
            .codPostal(DEFAULT_COD_POSTAL);
        // Add required entity
        Provincia provincia;
        if (TestUtil.findAll(em, Provincia.class).isEmpty()) {
            provincia = ProvinciaResourceIT.createEntity(em);
            em.persist(provincia);
            em.flush();
        } else {
            provincia = TestUtil.findAll(em, Provincia.class).get(0);
        }
        municipio.setProvincia(provincia);
        return municipio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Municipio createUpdatedEntity(EntityManager em) {
        Municipio municipio = new Municipio()
            .nombre(UPDATED_NOMBRE)
            .codPostal(UPDATED_COD_POSTAL);
        // Add required entity
        Provincia provincia;
        if (TestUtil.findAll(em, Provincia.class).isEmpty()) {
            provincia = ProvinciaResourceIT.createUpdatedEntity(em);
            em.persist(provincia);
            em.flush();
        } else {
            provincia = TestUtil.findAll(em, Provincia.class).get(0);
        }
        municipio.setProvincia(provincia);
        return municipio;
    }

    @BeforeEach
    public void initTest() {
        municipio = createEntity(em);
    }

    @Test
    @Transactional
    public void createMunicipio() throws Exception {
        int databaseSizeBeforeCreate = municipioRepository.findAll().size();

        // Create the Municipio
        MunicipioDTO municipioDTO = municipioMapper.toDto(municipio);
        restMunicipioMockMvc.perform(post("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isCreated());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeCreate + 1);
        Municipio testMunicipio = municipioList.get(municipioList.size() - 1);
        assertThat(testMunicipio.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testMunicipio.getCodPostal()).isEqualTo(DEFAULT_COD_POSTAL);
    }

    @Test
    @Transactional
    public void createMunicipioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = municipioRepository.findAll().size();

        // Create the Municipio with an existing ID
        municipio.setId(1L);
        MunicipioDTO municipioDTO = municipioMapper.toDto(municipio);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMunicipioMockMvc.perform(post("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = municipioRepository.findAll().size();
        // set the field null
        municipio.setNombre(null);

        // Create the Municipio, which fails.
        MunicipioDTO municipioDTO = municipioMapper.toDto(municipio);

        restMunicipioMockMvc.perform(post("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isBadRequest());

        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodPostalIsRequired() throws Exception {
        int databaseSizeBeforeTest = municipioRepository.findAll().size();
        // set the field null
        municipio.setCodPostal(null);

        // Create the Municipio, which fails.
        MunicipioDTO municipioDTO = municipioMapper.toDto(municipio);

        restMunicipioMockMvc.perform(post("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isBadRequest());

        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMunicipios() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList
        restMunicipioMockMvc.perform(get("/api/municipios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(municipio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].codPostal").value(hasItem(DEFAULT_COD_POSTAL.toString())));
    }
    
    @Test
    @Transactional
    public void getMunicipio() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get the municipio
        restMunicipioMockMvc.perform(get("/api/municipios/{id}", municipio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(municipio.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.codPostal").value(DEFAULT_COD_POSTAL.toString()));
    }

    @Test
    @Transactional
    public void getAllMunicipiosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where nombre equals to DEFAULT_NOMBRE
        defaultMunicipioShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the municipioList where nombre equals to UPDATED_NOMBRE
        defaultMunicipioShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultMunicipioShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the municipioList where nombre equals to UPDATED_NOMBRE
        defaultMunicipioShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where nombre is not null
        defaultMunicipioShouldBeFound("nombre.specified=true");

        // Get all the municipioList where nombre is null
        defaultMunicipioShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllMunicipiosByCodPostalIsEqualToSomething() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where codPostal equals to DEFAULT_COD_POSTAL
        defaultMunicipioShouldBeFound("codPostal.equals=" + DEFAULT_COD_POSTAL);

        // Get all the municipioList where codPostal equals to UPDATED_COD_POSTAL
        defaultMunicipioShouldNotBeFound("codPostal.equals=" + UPDATED_COD_POSTAL);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByCodPostalIsInShouldWork() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where codPostal in DEFAULT_COD_POSTAL or UPDATED_COD_POSTAL
        defaultMunicipioShouldBeFound("codPostal.in=" + DEFAULT_COD_POSTAL + "," + UPDATED_COD_POSTAL);

        // Get all the municipioList where codPostal equals to UPDATED_COD_POSTAL
        defaultMunicipioShouldNotBeFound("codPostal.in=" + UPDATED_COD_POSTAL);
    }

    @Test
    @Transactional
    public void getAllMunicipiosByCodPostalIsNullOrNotNull() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        // Get all the municipioList where codPostal is not null
        defaultMunicipioShouldBeFound("codPostal.specified=true");

        // Get all the municipioList where codPostal is null
        defaultMunicipioShouldNotBeFound("codPostal.specified=false");
    }

    @Test
    @Transactional
    public void getAllMunicipiosByProvinciaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Provincia provincia = municipio.getProvincia();
        municipioRepository.saveAndFlush(municipio);
        Long provinciaId = provincia.getId();

        // Get all the municipioList where provincia equals to provinciaId
        defaultMunicipioShouldBeFound("provinciaId.equals=" + provinciaId);

        // Get all the municipioList where provincia equals to provinciaId + 1
        defaultMunicipioShouldNotBeFound("provinciaId.equals=" + (provinciaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMunicipioShouldBeFound(String filter) throws Exception {
        restMunicipioMockMvc.perform(get("/api/municipios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(municipio.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].codPostal").value(hasItem(DEFAULT_COD_POSTAL)));

        // Check, that the count call also returns 1
        restMunicipioMockMvc.perform(get("/api/municipios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMunicipioShouldNotBeFound(String filter) throws Exception {
        restMunicipioMockMvc.perform(get("/api/municipios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMunicipioMockMvc.perform(get("/api/municipios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMunicipio() throws Exception {
        // Get the municipio
        restMunicipioMockMvc.perform(get("/api/municipios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMunicipio() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        int databaseSizeBeforeUpdate = municipioRepository.findAll().size();

        // Update the municipio
        Municipio updatedMunicipio = municipioRepository.findById(municipio.getId()).get();
        // Disconnect from session so that the updates on updatedMunicipio are not directly saved in db
        em.detach(updatedMunicipio);
        updatedMunicipio
            .nombre(UPDATED_NOMBRE)
            .codPostal(UPDATED_COD_POSTAL);
        MunicipioDTO municipioDTO = municipioMapper.toDto(updatedMunicipio);

        restMunicipioMockMvc.perform(put("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isOk());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeUpdate);
        Municipio testMunicipio = municipioList.get(municipioList.size() - 1);
        assertThat(testMunicipio.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testMunicipio.getCodPostal()).isEqualTo(UPDATED_COD_POSTAL);
    }

    @Test
    @Transactional
    public void updateNonExistingMunicipio() throws Exception {
        int databaseSizeBeforeUpdate = municipioRepository.findAll().size();

        // Create the Municipio
        MunicipioDTO municipioDTO = municipioMapper.toDto(municipio);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMunicipioMockMvc.perform(put("/api/municipios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(municipioDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Municipio in the database
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMunicipio() throws Exception {
        // Initialize the database
        municipioRepository.saveAndFlush(municipio);

        int databaseSizeBeforeDelete = municipioRepository.findAll().size();

        // Delete the municipio
        restMunicipioMockMvc.perform(delete("/api/municipios/{id}", municipio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Municipio> municipioList = municipioRepository.findAll();
        assertThat(municipioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Municipio.class);
        Municipio municipio1 = new Municipio();
        municipio1.setId(1L);
        Municipio municipio2 = new Municipio();
        municipio2.setId(municipio1.getId());
        assertThat(municipio1).isEqualTo(municipio2);
        municipio2.setId(2L);
        assertThat(municipio1).isNotEqualTo(municipio2);
        municipio1.setId(null);
        assertThat(municipio1).isNotEqualTo(municipio2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MunicipioDTO.class);
        MunicipioDTO municipioDTO1 = new MunicipioDTO();
        municipioDTO1.setId(1L);
        MunicipioDTO municipioDTO2 = new MunicipioDTO();
        assertThat(municipioDTO1).isNotEqualTo(municipioDTO2);
        municipioDTO2.setId(municipioDTO1.getId());
        assertThat(municipioDTO1).isEqualTo(municipioDTO2);
        municipioDTO2.setId(2L);
        assertThat(municipioDTO1).isNotEqualTo(municipioDTO2);
        municipioDTO1.setId(null);
        assertThat(municipioDTO1).isNotEqualTo(municipioDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(municipioMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(municipioMapper.fromId(null)).isNull();
    }
}
