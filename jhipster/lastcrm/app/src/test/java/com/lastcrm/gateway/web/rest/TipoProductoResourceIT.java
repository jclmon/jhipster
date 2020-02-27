package com.lastcrm.gateway.web.rest;

import com.lastcrm.gateway.LastcrmApp;
import com.lastcrm.gateway.domain.TipoProducto;
import com.lastcrm.gateway.repository.TipoProductoRepository;
import com.lastcrm.gateway.service.TipoProductoService;
import com.lastcrm.gateway.service.dto.TipoProductoDTO;
import com.lastcrm.gateway.service.mapper.TipoProductoMapper;
import com.lastcrm.gateway.web.rest.errors.ExceptionTranslator;
import com.lastcrm.gateway.service.dto.TipoProductoCriteria;
import com.lastcrm.gateway.service.TipoProductoQueryService;

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
 * Integration tests for the {@link TipoProductoResource} REST controller.
 */
@SpringBootTest(classes = LastcrmApp.class)
public class TipoProductoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TipoProductoRepository tipoProductoRepository;

    @Autowired
    private TipoProductoMapper tipoProductoMapper;

    @Autowired
    private TipoProductoService tipoProductoService;

    @Autowired
    private TipoProductoQueryService tipoProductoQueryService;

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

    private MockMvc restTipoProductoMockMvc;

    private TipoProducto tipoProducto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoProductoResource tipoProductoResource = new TipoProductoResource(tipoProductoService, tipoProductoQueryService);
        this.restTipoProductoMockMvc = MockMvcBuilders.standaloneSetup(tipoProductoResource)
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
    public static TipoProducto createEntity(EntityManager em) {
        TipoProducto tipoProducto = new TipoProducto()
            .nombre(DEFAULT_NOMBRE);
        return tipoProducto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoProducto createUpdatedEntity(EntityManager em) {
        TipoProducto tipoProducto = new TipoProducto()
            .nombre(UPDATED_NOMBRE);
        return tipoProducto;
    }

    @BeforeEach
    public void initTest() {
        tipoProducto = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoProducto() throws Exception {
        int databaseSizeBeforeCreate = tipoProductoRepository.findAll().size();

        // Create the TipoProducto
        TipoProductoDTO tipoProductoDTO = tipoProductoMapper.toDto(tipoProducto);
        restTipoProductoMockMvc.perform(post("/api/tipo-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProductoDTO)))
            .andExpect(status().isCreated());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoProducto testTipoProducto = tipoProductoList.get(tipoProductoList.size() - 1);
        assertThat(testTipoProducto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTipoProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoProductoRepository.findAll().size();

        // Create the TipoProducto with an existing ID
        tipoProducto.setId(1L);
        TipoProductoDTO tipoProductoDTO = tipoProductoMapper.toDto(tipoProducto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoProductoMockMvc.perform(post("/api/tipo-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProductoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoProductoRepository.findAll().size();
        // set the field null
        tipoProducto.setNombre(null);

        // Create the TipoProducto, which fails.
        TipoProductoDTO tipoProductoDTO = tipoProductoMapper.toDto(tipoProducto);

        restTipoProductoMockMvc.perform(post("/api/tipo-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProductoDTO)))
            .andExpect(status().isBadRequest());

        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoProductos() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        // Get all the tipoProductoList
        restTipoProductoMockMvc.perform(get("/api/tipo-productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoProducto() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        // Get the tipoProducto
        restTipoProductoMockMvc.perform(get("/api/tipo-productos/{id}", tipoProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoProducto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getAllTipoProductosByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        // Get all the tipoProductoList where nombre equals to DEFAULT_NOMBRE
        defaultTipoProductoShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the tipoProductoList where nombre equals to UPDATED_NOMBRE
        defaultTipoProductoShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTipoProductosByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        // Get all the tipoProductoList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultTipoProductoShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the tipoProductoList where nombre equals to UPDATED_NOMBRE
        defaultTipoProductoShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTipoProductosByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        // Get all the tipoProductoList where nombre is not null
        defaultTipoProductoShouldBeFound("nombre.specified=true");

        // Get all the tipoProductoList where nombre is null
        defaultTipoProductoShouldNotBeFound("nombre.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoProductoShouldBeFound(String filter) throws Exception {
        restTipoProductoMockMvc.perform(get("/api/tipo-productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)));

        // Check, that the count call also returns 1
        restTipoProductoMockMvc.perform(get("/api/tipo-productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoProductoShouldNotBeFound(String filter) throws Exception {
        restTipoProductoMockMvc.perform(get("/api/tipo-productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoProductoMockMvc.perform(get("/api/tipo-productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTipoProducto() throws Exception {
        // Get the tipoProducto
        restTipoProductoMockMvc.perform(get("/api/tipo-productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoProducto() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();

        // Update the tipoProducto
        TipoProducto updatedTipoProducto = tipoProductoRepository.findById(tipoProducto.getId()).get();
        // Disconnect from session so that the updates on updatedTipoProducto are not directly saved in db
        em.detach(updatedTipoProducto);
        updatedTipoProducto
            .nombre(UPDATED_NOMBRE);
        TipoProductoDTO tipoProductoDTO = tipoProductoMapper.toDto(updatedTipoProducto);

        restTipoProductoMockMvc.perform(put("/api/tipo-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProductoDTO)))
            .andExpect(status().isOk());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
        TipoProducto testTipoProducto = tipoProductoList.get(tipoProductoList.size() - 1);
        assertThat(testTipoProducto.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoProducto() throws Exception {
        int databaseSizeBeforeUpdate = tipoProductoRepository.findAll().size();

        // Create the TipoProducto
        TipoProductoDTO tipoProductoDTO = tipoProductoMapper.toDto(tipoProducto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoProductoMockMvc.perform(put("/api/tipo-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoProductoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TipoProducto in the database
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoProducto() throws Exception {
        // Initialize the database
        tipoProductoRepository.saveAndFlush(tipoProducto);

        int databaseSizeBeforeDelete = tipoProductoRepository.findAll().size();

        // Delete the tipoProducto
        restTipoProductoMockMvc.perform(delete("/api/tipo-productos/{id}", tipoProducto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoProducto> tipoProductoList = tipoProductoRepository.findAll();
        assertThat(tipoProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoProducto.class);
        TipoProducto tipoProducto1 = new TipoProducto();
        tipoProducto1.setId(1L);
        TipoProducto tipoProducto2 = new TipoProducto();
        tipoProducto2.setId(tipoProducto1.getId());
        assertThat(tipoProducto1).isEqualTo(tipoProducto2);
        tipoProducto2.setId(2L);
        assertThat(tipoProducto1).isNotEqualTo(tipoProducto2);
        tipoProducto1.setId(null);
        assertThat(tipoProducto1).isNotEqualTo(tipoProducto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoProductoDTO.class);
        TipoProductoDTO tipoProductoDTO1 = new TipoProductoDTO();
        tipoProductoDTO1.setId(1L);
        TipoProductoDTO tipoProductoDTO2 = new TipoProductoDTO();
        assertThat(tipoProductoDTO1).isNotEqualTo(tipoProductoDTO2);
        tipoProductoDTO2.setId(tipoProductoDTO1.getId());
        assertThat(tipoProductoDTO1).isEqualTo(tipoProductoDTO2);
        tipoProductoDTO2.setId(2L);
        assertThat(tipoProductoDTO1).isNotEqualTo(tipoProductoDTO2);
        tipoProductoDTO1.setId(null);
        assertThat(tipoProductoDTO1).isNotEqualTo(tipoProductoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(tipoProductoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(tipoProductoMapper.fromId(null)).isNull();
    }
}
