package com.lastcrm.gateway.web.rest;

import com.lastcrm.gateway.LastcrmApp;
import com.lastcrm.gateway.domain.*;
import com.lastcrm.gateway.domain.enumeration.Prioridad;
import com.lastcrm.gateway.domain.enumeration.Solicitud;
import com.lastcrm.gateway.repository.FichaClienteRepository;
import com.lastcrm.gateway.service.FichaClienteQueryService;
import com.lastcrm.gateway.service.FichaClienteService;
import com.lastcrm.gateway.service.dto.FichaClienteDTO;
import com.lastcrm.gateway.service.mapper.FichaClienteMapper;
import com.lastcrm.gateway.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.lastcrm.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link FichaClienteResource} REST controller.
 */
@SpringBootTest(classes = LastcrmApp.class)
public class FichaClienteResourceIT {

    private static final Solicitud DEFAULT_SOLICITUD = Solicitud.COMPRA;
    private static final Solicitud UPDATED_SOLICITUD = Solicitud.VENTA;

    private static final Prioridad DEFAULT_PRIORIDAD = Prioridad.ALTA;
    private static final Prioridad UPDATED_PRIORIDAD = Prioridad.MEDIA;

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    @Autowired
    private FichaClienteRepository fichaClienteRepository;

    @Mock
    private FichaClienteRepository fichaClienteRepositoryMock;

    @Autowired
    private FichaClienteMapper fichaClienteMapper;

    @Mock
    private FichaClienteService fichaClienteServiceMock;

    @Autowired
    private FichaClienteService fichaClienteService;

    @Autowired
    private FichaClienteQueryService fichaClienteQueryService;

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

    private MockMvc restFichaClienteMockMvc;

    private FichaCliente fichaCliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FichaClienteResource fichaClienteResource = new FichaClienteResource(fichaClienteService, fichaClienteQueryService);
        this.restFichaClienteMockMvc = MockMvcBuilders.standaloneSetup(fichaClienteResource)
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
    public static FichaCliente createEntity(EntityManager em) {
        FichaCliente fichaCliente = new FichaCliente()
            .solicitud(DEFAULT_SOLICITUD)
            .prioridad(DEFAULT_PRIORIDAD)
            .comentario(DEFAULT_COMENTARIO);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        fichaCliente.setCliente(cliente);
        // Add required entity
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            area = AreaResourceIT.createEntity(em);
            em.persist(area);
            em.flush();
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        fichaCliente.getAreas().add(area);
        // Add required entity
        Cita cita;
        if (TestUtil.findAll(em, Cita.class).isEmpty()) {
            cita = CitaResourceIT.createEntity(em);
            em.persist(cita);
            em.flush();
        } else {
            cita = TestUtil.findAll(em, Cita.class).get(0);
        }
        fichaCliente.getCitas().add(cita);
        // Add required entity
        TipoProducto tipoProducto;
        if (TestUtil.findAll(em, TipoProducto.class).isEmpty()) {
            tipoProducto = TipoProductoResourceIT.createEntity(em);
            em.persist(tipoProducto);
            em.flush();
        } else {
            tipoProducto = TestUtil.findAll(em, TipoProducto.class).get(0);
        }
        fichaCliente.getTipoProductos().add(tipoProducto);
        return fichaCliente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FichaCliente createUpdatedEntity(EntityManager em) {
        FichaCliente fichaCliente = new FichaCliente()
            .solicitud(UPDATED_SOLICITUD)
            .prioridad(UPDATED_PRIORIDAD)
            .comentario(UPDATED_COMENTARIO);
        // Add required entity
        Cliente cliente;
        if (TestUtil.findAll(em, Cliente.class).isEmpty()) {
            cliente = ClienteResourceIT.createUpdatedEntity(em);
            em.persist(cliente);
            em.flush();
        } else {
            cliente = TestUtil.findAll(em, Cliente.class).get(0);
        }
        fichaCliente.setCliente(cliente);
        // Add required entity
        Area area;
        if (TestUtil.findAll(em, Area.class).isEmpty()) {
            area = AreaResourceIT.createUpdatedEntity(em);
            em.persist(area);
            em.flush();
        } else {
            area = TestUtil.findAll(em, Area.class).get(0);
        }
        fichaCliente.getAreas().add(area);
        // Add required entity
        Cita cita;
        if (TestUtil.findAll(em, Cita.class).isEmpty()) {
            cita = CitaResourceIT.createUpdatedEntity(em);
            em.persist(cita);
            em.flush();
        } else {
            cita = TestUtil.findAll(em, Cita.class).get(0);
        }
        fichaCliente.getCitas().add(cita);
        // Add required entity
        TipoProducto tipoProducto;
        if (TestUtil.findAll(em, TipoProducto.class).isEmpty()) {
            tipoProducto = TipoProductoResourceIT.createUpdatedEntity(em);
            em.persist(tipoProducto);
            em.flush();
        } else {
            tipoProducto = TestUtil.findAll(em, TipoProducto.class).get(0);
        }
        fichaCliente.getTipoProductos().add(tipoProducto);
        return fichaCliente;
    }

    @BeforeEach
    public void initTest() {
        fichaCliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createFichaCliente() throws Exception {
        int databaseSizeBeforeCreate = fichaClienteRepository.findAll().size();

        // Create the FichaCliente
        FichaClienteDTO fichaClienteDTO = fichaClienteMapper.toDto(fichaCliente);
        restFichaClienteMockMvc.perform(post("/api/ficha-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaClienteDTO)))
            .andExpect(status().isCreated());

        // Validate the FichaCliente in the database
        List<FichaCliente> fichaClienteList = fichaClienteRepository.findAll();
        assertThat(fichaClienteList).hasSize(databaseSizeBeforeCreate + 1);
        FichaCliente testFichaCliente = fichaClienteList.get(fichaClienteList.size() - 1);
        assertThat(testFichaCliente.getSolicitud()).isEqualTo(DEFAULT_SOLICITUD);
        assertThat(testFichaCliente.getPrioridad()).isEqualTo(DEFAULT_PRIORIDAD);
        assertThat(testFichaCliente.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    public void createFichaClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fichaClienteRepository.findAll().size();

        // Create the FichaCliente with an existing ID
        fichaCliente.setId(1L);
        FichaClienteDTO fichaClienteDTO = fichaClienteMapper.toDto(fichaCliente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFichaClienteMockMvc.perform(post("/api/ficha-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaClienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FichaCliente in the database
        List<FichaCliente> fichaClienteList = fichaClienteRepository.findAll();
        assertThat(fichaClienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSolicitudIsRequired() throws Exception {
        int databaseSizeBeforeTest = fichaClienteRepository.findAll().size();
        // set the field null
        fichaCliente.setSolicitud(null);

        // Create the FichaCliente, which fails.
        FichaClienteDTO fichaClienteDTO = fichaClienteMapper.toDto(fichaCliente);

        restFichaClienteMockMvc.perform(post("/api/ficha-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaClienteDTO)))
            .andExpect(status().isBadRequest());

        List<FichaCliente> fichaClienteList = fichaClienteRepository.findAll();
        assertThat(fichaClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrioridadIsRequired() throws Exception {
        int databaseSizeBeforeTest = fichaClienteRepository.findAll().size();
        // set the field null
        fichaCliente.setPrioridad(null);

        // Create the FichaCliente, which fails.
        FichaClienteDTO fichaClienteDTO = fichaClienteMapper.toDto(fichaCliente);

        restFichaClienteMockMvc.perform(post("/api/ficha-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaClienteDTO)))
            .andExpect(status().isBadRequest());

        List<FichaCliente> fichaClienteList = fichaClienteRepository.findAll();
        assertThat(fichaClienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFichaClientes() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);

        // Get all the fichaClienteList
        restFichaClienteMockMvc.perform(get("/api/ficha-clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichaCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].solicitud").value(hasItem(DEFAULT_SOLICITUD.toString())))
            .andExpect(jsonPath("$.[*].prioridad").value(hasItem(DEFAULT_PRIORIDAD.toString())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllFichaClientesWithEagerRelationshipsIsEnabled() throws Exception {
        FichaClienteResource fichaClienteResource = new FichaClienteResource(fichaClienteServiceMock, fichaClienteQueryService);
        when(fichaClienteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restFichaClienteMockMvc = MockMvcBuilders.standaloneSetup(fichaClienteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFichaClienteMockMvc.perform(get("/api/ficha-clientes?eagerload=true"))
        .andExpect(status().isOk());

        verify(fichaClienteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllFichaClientesWithEagerRelationshipsIsNotEnabled() throws Exception {
        FichaClienteResource fichaClienteResource = new FichaClienteResource(fichaClienteServiceMock, fichaClienteQueryService);
            when(fichaClienteServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restFichaClienteMockMvc = MockMvcBuilders.standaloneSetup(fichaClienteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restFichaClienteMockMvc.perform(get("/api/ficha-clientes?eagerload=true"))
        .andExpect(status().isOk());

            verify(fichaClienteServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getFichaCliente() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);

        // Get the fichaCliente
        restFichaClienteMockMvc.perform(get("/api/ficha-clientes/{id}", fichaCliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fichaCliente.getId().intValue()))
            .andExpect(jsonPath("$.solicitud").value(DEFAULT_SOLICITUD.toString()))
            .andExpect(jsonPath("$.prioridad").value(DEFAULT_PRIORIDAD.toString()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()));
    }

    @Test
    @Transactional
    public void getAllFichaClientesBySolicitudIsEqualToSomething() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);

        // Get all the fichaClienteList where solicitud equals to DEFAULT_SOLICITUD
        defaultFichaClienteShouldBeFound("solicitud.equals=" + DEFAULT_SOLICITUD);

        // Get all the fichaClienteList where solicitud equals to UPDATED_SOLICITUD
        defaultFichaClienteShouldNotBeFound("solicitud.equals=" + UPDATED_SOLICITUD);
    }

    @Test
    @Transactional
    public void getAllFichaClientesBySolicitudIsInShouldWork() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);

        // Get all the fichaClienteList where solicitud in DEFAULT_SOLICITUD or UPDATED_SOLICITUD
        defaultFichaClienteShouldBeFound("solicitud.in=" + DEFAULT_SOLICITUD + "," + UPDATED_SOLICITUD);

        // Get all the fichaClienteList where solicitud equals to UPDATED_SOLICITUD
        defaultFichaClienteShouldNotBeFound("solicitud.in=" + UPDATED_SOLICITUD);
    }

    @Test
    @Transactional
    public void getAllFichaClientesBySolicitudIsNullOrNotNull() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);

        // Get all the fichaClienteList where solicitud is not null
        defaultFichaClienteShouldBeFound("solicitud.specified=true");

        // Get all the fichaClienteList where solicitud is null
        defaultFichaClienteShouldNotBeFound("solicitud.specified=false");
    }

    @Test
    @Transactional
    public void getAllFichaClientesByPrioridadIsEqualToSomething() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);

        // Get all the fichaClienteList where prioridad equals to DEFAULT_PRIORIDAD
        defaultFichaClienteShouldBeFound("prioridad.equals=" + DEFAULT_PRIORIDAD);

        // Get all the fichaClienteList where prioridad equals to UPDATED_PRIORIDAD
        defaultFichaClienteShouldNotBeFound("prioridad.equals=" + UPDATED_PRIORIDAD);
    }

    @Test
    @Transactional
    public void getAllFichaClientesByPrioridadIsInShouldWork() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);

        // Get all the fichaClienteList where prioridad in DEFAULT_PRIORIDAD or UPDATED_PRIORIDAD
        defaultFichaClienteShouldBeFound("prioridad.in=" + DEFAULT_PRIORIDAD + "," + UPDATED_PRIORIDAD);

        // Get all the fichaClienteList where prioridad equals to UPDATED_PRIORIDAD
        defaultFichaClienteShouldNotBeFound("prioridad.in=" + UPDATED_PRIORIDAD);
    }

    @Test
    @Transactional
    public void getAllFichaClientesByPrioridadIsNullOrNotNull() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);

        // Get all the fichaClienteList where prioridad is not null
        defaultFichaClienteShouldBeFound("prioridad.specified=true");

        // Get all the fichaClienteList where prioridad is null
        defaultFichaClienteShouldNotBeFound("prioridad.specified=false");
    }

    @Test
    @Transactional
    public void getAllFichaClientesByComentarioIsEqualToSomething() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);

        // Get all the fichaClienteList where comentario equals to DEFAULT_COMENTARIO
        defaultFichaClienteShouldBeFound("comentario.equals=" + DEFAULT_COMENTARIO);

        // Get all the fichaClienteList where comentario equals to UPDATED_COMENTARIO
        defaultFichaClienteShouldNotBeFound("comentario.equals=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllFichaClientesByComentarioIsInShouldWork() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);

        // Get all the fichaClienteList where comentario in DEFAULT_COMENTARIO or UPDATED_COMENTARIO
        defaultFichaClienteShouldBeFound("comentario.in=" + DEFAULT_COMENTARIO + "," + UPDATED_COMENTARIO);

        // Get all the fichaClienteList where comentario equals to UPDATED_COMENTARIO
        defaultFichaClienteShouldNotBeFound("comentario.in=" + UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void getAllFichaClientesByComentarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);

        // Get all the fichaClienteList where comentario is not null
        defaultFichaClienteShouldBeFound("comentario.specified=true");

        // Get all the fichaClienteList where comentario is null
        defaultFichaClienteShouldNotBeFound("comentario.specified=false");
    }

    @Test
    @Transactional
    public void getAllFichaClientesByClienteIsEqualToSomething() throws Exception {
        // Get already existing entity
        Cliente cliente = fichaCliente.getCliente();
        fichaClienteRepository.saveAndFlush(fichaCliente);
        Long clienteId = cliente.getId();

        // Get all the fichaClienteList where cliente equals to clienteId
        defaultFichaClienteShouldBeFound("clienteId.equals=" + clienteId);

        // Get all the fichaClienteList where cliente equals to clienteId + 1
        defaultFichaClienteShouldNotBeFound("clienteId.equals=" + (clienteId + 1));
    }


    @Test
    @Transactional
    public void getAllFichaClientesByProductoIsEqualToSomething() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);
        Producto producto = ProductoResourceIT.createEntity(em);
        em.persist(producto);
        em.flush();
        fichaCliente.setProducto(producto);
        fichaClienteRepository.saveAndFlush(fichaCliente);
        Long productoId = producto.getId();

        // Get all the fichaClienteList where producto equals to productoId
        defaultFichaClienteShouldBeFound("productoId.equals=" + productoId);

        // Get all the fichaClienteList where producto equals to productoId + 1
        defaultFichaClienteShouldNotBeFound("productoId.equals=" + (productoId + 1));
    }


    @Test
    @Transactional
    public void getAllFichaClientesByAreaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Set<Area> areas = fichaCliente.getAreas();
        fichaClienteRepository.saveAndFlush(fichaCliente);
        Long areaId = areas.iterator().next().getId();

        // Get all the fichaClienteList where area equals to areaId
        defaultFichaClienteShouldBeFound("areaId.equals=" + areaId);

        // Get all the fichaClienteList where area equals to areaId + 1
        defaultFichaClienteShouldNotBeFound("areaId.equals=" + (areaId + 1));
    }


    @Test
    @Transactional
    public void getAllFichaClientesByCitaIsEqualToSomething() throws Exception {
        // Get already existing entity
        Set<Cita> citas = fichaCliente.getCitas();
        fichaClienteRepository.saveAndFlush(fichaCliente);
        Long citaId = citas.iterator().next().getId();

        // Get all the fichaClienteList where cita equals to citaId
        defaultFichaClienteShouldBeFound("citaId.equals=" + citaId);

        // Get all the fichaClienteList where cita equals to citaId + 1
        defaultFichaClienteShouldNotBeFound("citaId.equals=" + (citaId + 1));
    }


    @Test
    @Transactional
    public void getAllFichaClientesByTipoProductoIsEqualToSomething() throws Exception {
        // Get already existing entity
        Set<TipoProducto> tipoProductos = fichaCliente.getTipoProductos();
        fichaClienteRepository.saveAndFlush(fichaCliente);
        Long tipoProductoId = tipoProductos.iterator().next().getId();

        // Get all the fichaClienteList where tipoProducto equals to tipoProductoId
        defaultFichaClienteShouldBeFound("tipoProductoId.equals=" + tipoProductoId);

        // Get all the fichaClienteList where tipoProducto equals to tipoProductoId + 1
        defaultFichaClienteShouldNotBeFound("tipoProductoId.equals=" + (tipoProductoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFichaClienteShouldBeFound(String filter) throws Exception {
        restFichaClienteMockMvc.perform(get("/api/ficha-clientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fichaCliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].solicitud").value(hasItem(DEFAULT_SOLICITUD.toString())))
            .andExpect(jsonPath("$.[*].prioridad").value(hasItem(DEFAULT_PRIORIDAD.toString())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO)));

        // Check, that the count call also returns 1
        restFichaClienteMockMvc.perform(get("/api/ficha-clientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFichaClienteShouldNotBeFound(String filter) throws Exception {
        restFichaClienteMockMvc.perform(get("/api/ficha-clientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFichaClienteMockMvc.perform(get("/api/ficha-clientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFichaCliente() throws Exception {
        // Get the fichaCliente
        restFichaClienteMockMvc.perform(get("/api/ficha-clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFichaCliente() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);

        int databaseSizeBeforeUpdate = fichaClienteRepository.findAll().size();

        // Update the fichaCliente
        FichaCliente updatedFichaCliente = fichaClienteRepository.findById(fichaCliente.getId()).get();
        // Disconnect from session so that the updates on updatedFichaCliente are not directly saved in db
        em.detach(updatedFichaCliente);
        updatedFichaCliente
            .solicitud(UPDATED_SOLICITUD)
            .prioridad(UPDATED_PRIORIDAD)
            .comentario(UPDATED_COMENTARIO);
        FichaClienteDTO fichaClienteDTO = fichaClienteMapper.toDto(updatedFichaCliente);

        restFichaClienteMockMvc.perform(put("/api/ficha-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaClienteDTO)))
            .andExpect(status().isOk());

        // Validate the FichaCliente in the database
        List<FichaCliente> fichaClienteList = fichaClienteRepository.findAll();
        assertThat(fichaClienteList).hasSize(databaseSizeBeforeUpdate);
        FichaCliente testFichaCliente = fichaClienteList.get(fichaClienteList.size() - 1);
        assertThat(testFichaCliente.getSolicitud()).isEqualTo(UPDATED_SOLICITUD);
        assertThat(testFichaCliente.getPrioridad()).isEqualTo(UPDATED_PRIORIDAD);
        assertThat(testFichaCliente.getComentario()).isEqualTo(UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingFichaCliente() throws Exception {
        int databaseSizeBeforeUpdate = fichaClienteRepository.findAll().size();

        // Create the FichaCliente
        FichaClienteDTO fichaClienteDTO = fichaClienteMapper.toDto(fichaCliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFichaClienteMockMvc.perform(put("/api/ficha-clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fichaClienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FichaCliente in the database
        List<FichaCliente> fichaClienteList = fichaClienteRepository.findAll();
        assertThat(fichaClienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFichaCliente() throws Exception {
        // Initialize the database
        fichaClienteRepository.saveAndFlush(fichaCliente);

        int databaseSizeBeforeDelete = fichaClienteRepository.findAll().size();

        // Delete the fichaCliente
        restFichaClienteMockMvc.perform(delete("/api/ficha-clientes/{id}", fichaCliente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FichaCliente> fichaClienteList = fichaClienteRepository.findAll();
        assertThat(fichaClienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichaCliente.class);
        FichaCliente fichaCliente1 = new FichaCliente();
        fichaCliente1.setId(1L);
        FichaCliente fichaCliente2 = new FichaCliente();
        fichaCliente2.setId(fichaCliente1.getId());
        assertThat(fichaCliente1).isEqualTo(fichaCliente2);
        fichaCliente2.setId(2L);
        assertThat(fichaCliente1).isNotEqualTo(fichaCliente2);
        fichaCliente1.setId(null);
        assertThat(fichaCliente1).isNotEqualTo(fichaCliente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichaClienteDTO.class);
        FichaClienteDTO fichaClienteDTO1 = new FichaClienteDTO();
        fichaClienteDTO1.setId(1L);
        FichaClienteDTO fichaClienteDTO2 = new FichaClienteDTO();
        assertThat(fichaClienteDTO1).isNotEqualTo(fichaClienteDTO2);
        fichaClienteDTO2.setId(fichaClienteDTO1.getId());
        assertThat(fichaClienteDTO1).isEqualTo(fichaClienteDTO2);
        fichaClienteDTO2.setId(2L);
        assertThat(fichaClienteDTO1).isNotEqualTo(fichaClienteDTO2);
        fichaClienteDTO1.setId(null);
        assertThat(fichaClienteDTO1).isNotEqualTo(fichaClienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fichaClienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fichaClienteMapper.fromId(null)).isNull();
    }
}
