package com.lastcrm.gateway.web.rest;

import com.lastcrm.gateway.LastcrmApp;
import com.lastcrm.gateway.domain.Cliente;
import com.lastcrm.gateway.domain.Fuente;
import com.lastcrm.gateway.repository.ClienteRepository;
import com.lastcrm.gateway.service.ClienteService;
import com.lastcrm.gateway.service.dto.ClienteDTO;
import com.lastcrm.gateway.service.mapper.ClienteMapper;
import com.lastcrm.gateway.web.rest.errors.ExceptionTranslator;
import com.lastcrm.gateway.service.dto.ClienteCriteria;
import com.lastcrm.gateway.service.ClienteQueryService;

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

import com.lastcrm.gateway.domain.enumeration.Genero;
/**
 * Integration tests for the {@link ClienteResource} REST controller.
 */
@SpringBootTest(classes = LastcrmApp.class)
public class ClienteResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_1 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_1 = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_2 = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_2 = "BBBBBBBBBB";

    private static final String DEFAULT_NIF = "AAAAAAAAAA";
    private static final String UPDATED_NIF = "BBBBBBBBBB";

    private static final Genero DEFAULT_GENERO = Genero.HOMBRE;
    private static final Genero UPDATED_GENERO = Genero.MUJER;

    private static final String DEFAULT_TLF_MOVIL = "AAAAAAAAAA";
    private static final String UPDATED_TLF_MOVIL = "BBBBBBBBBB";

    private static final String DEFAULT_TLF_MOVIL_2 = "AAAAAAAAAA";
    private static final String UPDATED_TLF_MOVIL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_TLF_FIJO = "AAAAAAAAAA";
    private static final String UPDATED_TLF_FIJO = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL_1 = "`m@V.N";
    private static final String UPDATED_EMAIL_1 = "yr@r.!=";

    private static final String DEFAULT_EMAIL_2 = "(U@m.6W";
    private static final String UPDATED_EMAIL_2 = "'>@w.c/";

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteQueryService clienteQueryService;

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

    private MockMvc restClienteMockMvc;

    private Cliente cliente;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClienteResource clienteResource = new ClienteResource(clienteService, clienteQueryService);
        this.restClienteMockMvc = MockMvcBuilders.standaloneSetup(clienteResource)
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
    public static Cliente createEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .nombre(DEFAULT_NOMBRE)
            .apellido1(DEFAULT_APELLIDO_1)
            .apellido2(DEFAULT_APELLIDO_2)
            .nif(DEFAULT_NIF)
            .genero(DEFAULT_GENERO)
            .tlfMovil(DEFAULT_TLF_MOVIL)
            .tlfMovil2(DEFAULT_TLF_MOVIL_2)
            .tlfFijo(DEFAULT_TLF_FIJO)
            .fax(DEFAULT_FAX)
            .email1(DEFAULT_EMAIL_1)
            .email2(DEFAULT_EMAIL_2);
        // Add required entity
        Fuente fuente;
        if (TestUtil.findAll(em, Fuente.class).isEmpty()) {
            fuente = FuenteResourceIT.createEntity(em);
            em.persist(fuente);
            em.flush();
        } else {
            fuente = TestUtil.findAll(em, Fuente.class).get(0);
        }
        cliente.setFuente(fuente);
        return cliente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cliente createUpdatedEntity(EntityManager em) {
        Cliente cliente = new Cliente()
            .nombre(UPDATED_NOMBRE)
            .apellido1(UPDATED_APELLIDO_1)
            .apellido2(UPDATED_APELLIDO_2)
            .nif(UPDATED_NIF)
            .genero(UPDATED_GENERO)
            .tlfMovil(UPDATED_TLF_MOVIL)
            .tlfMovil2(UPDATED_TLF_MOVIL_2)
            .tlfFijo(UPDATED_TLF_FIJO)
            .fax(UPDATED_FAX)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2);
        // Add required entity
        Fuente fuente;
        if (TestUtil.findAll(em, Fuente.class).isEmpty()) {
            fuente = FuenteResourceIT.createUpdatedEntity(em);
            em.persist(fuente);
            em.flush();
        } else {
            fuente = TestUtil.findAll(em, Fuente.class).get(0);
        }
        cliente.setFuente(fuente);
        return cliente;
    }

    @BeforeEach
    public void initTest() {
        cliente = createEntity(em);
    }

    @Test
    @Transactional
    public void createCliente() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isCreated());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate + 1);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testCliente.getApellido1()).isEqualTo(DEFAULT_APELLIDO_1);
        assertThat(testCliente.getApellido2()).isEqualTo(DEFAULT_APELLIDO_2);
        assertThat(testCliente.getNif()).isEqualTo(DEFAULT_NIF);
        assertThat(testCliente.getGenero()).isEqualTo(DEFAULT_GENERO);
        assertThat(testCliente.getTlfMovil()).isEqualTo(DEFAULT_TLF_MOVIL);
        assertThat(testCliente.getTlfMovil2()).isEqualTo(DEFAULT_TLF_MOVIL_2);
        assertThat(testCliente.getTlfFijo()).isEqualTo(DEFAULT_TLF_FIJO);
        assertThat(testCliente.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testCliente.getEmail1()).isEqualTo(DEFAULT_EMAIL_1);
        assertThat(testCliente.getEmail2()).isEqualTo(DEFAULT_EMAIL_2);
    }

    @Test
    @Transactional
    public void createClienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clienteRepository.findAll().size();

        // Create the Cliente with an existing ID
        cliente.setId(1L);
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setNombre(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellido1IsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setApellido1(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGeneroIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setGenero(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTlfMovilIsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setTlfMovil(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmail1IsRequired() throws Exception {
        int databaseSizeBeforeTest = clienteRepository.findAll().size();
        // set the field null
        cliente.setEmail1(null);

        // Create the Cliente, which fails.
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        restClienteMockMvc.perform(post("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllClientes() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellido1").value(hasItem(DEFAULT_APELLIDO_1.toString())))
            .andExpect(jsonPath("$.[*].apellido2").value(hasItem(DEFAULT_APELLIDO_2.toString())))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF.toString())))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].tlfMovil").value(hasItem(DEFAULT_TLF_MOVIL.toString())))
            .andExpect(jsonPath("$.[*].tlfMovil2").value(hasItem(DEFAULT_TLF_MOVIL_2.toString())))
            .andExpect(jsonPath("$.[*].tlfFijo").value(hasItem(DEFAULT_TLF_FIJO.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1.toString())))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2.toString())));
    }
    
    @Test
    @Transactional
    public void getCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", cliente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cliente.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellido1").value(DEFAULT_APELLIDO_1.toString()))
            .andExpect(jsonPath("$.apellido2").value(DEFAULT_APELLIDO_2.toString()))
            .andExpect(jsonPath("$.nif").value(DEFAULT_NIF.toString()))
            .andExpect(jsonPath("$.genero").value(DEFAULT_GENERO.toString()))
            .andExpect(jsonPath("$.tlfMovil").value(DEFAULT_TLF_MOVIL.toString()))
            .andExpect(jsonPath("$.tlfMovil2").value(DEFAULT_TLF_MOVIL_2.toString()))
            .andExpect(jsonPath("$.tlfFijo").value(DEFAULT_TLF_FIJO.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.email1").value(DEFAULT_EMAIL_1.toString()))
            .andExpect(jsonPath("$.email2").value(DEFAULT_EMAIL_2.toString()));
    }

    @Test
    @Transactional
    public void getAllClientesByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nombre equals to DEFAULT_NOMBRE
        defaultClienteShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the clienteList where nombre equals to UPDATED_NOMBRE
        defaultClienteShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllClientesByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultClienteShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the clienteList where nombre equals to UPDATED_NOMBRE
        defaultClienteShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllClientesByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nombre is not null
        defaultClienteShouldBeFound("nombre.specified=true");

        // Get all the clienteList where nombre is null
        defaultClienteShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByApellido1IsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where apellido1 equals to DEFAULT_APELLIDO_1
        defaultClienteShouldBeFound("apellido1.equals=" + DEFAULT_APELLIDO_1);

        // Get all the clienteList where apellido1 equals to UPDATED_APELLIDO_1
        defaultClienteShouldNotBeFound("apellido1.equals=" + UPDATED_APELLIDO_1);
    }

    @Test
    @Transactional
    public void getAllClientesByApellido1IsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where apellido1 in DEFAULT_APELLIDO_1 or UPDATED_APELLIDO_1
        defaultClienteShouldBeFound("apellido1.in=" + DEFAULT_APELLIDO_1 + "," + UPDATED_APELLIDO_1);

        // Get all the clienteList where apellido1 equals to UPDATED_APELLIDO_1
        defaultClienteShouldNotBeFound("apellido1.in=" + UPDATED_APELLIDO_1);
    }

    @Test
    @Transactional
    public void getAllClientesByApellido1IsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where apellido1 is not null
        defaultClienteShouldBeFound("apellido1.specified=true");

        // Get all the clienteList where apellido1 is null
        defaultClienteShouldNotBeFound("apellido1.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByApellido2IsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where apellido2 equals to DEFAULT_APELLIDO_2
        defaultClienteShouldBeFound("apellido2.equals=" + DEFAULT_APELLIDO_2);

        // Get all the clienteList where apellido2 equals to UPDATED_APELLIDO_2
        defaultClienteShouldNotBeFound("apellido2.equals=" + UPDATED_APELLIDO_2);
    }

    @Test
    @Transactional
    public void getAllClientesByApellido2IsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where apellido2 in DEFAULT_APELLIDO_2 or UPDATED_APELLIDO_2
        defaultClienteShouldBeFound("apellido2.in=" + DEFAULT_APELLIDO_2 + "," + UPDATED_APELLIDO_2);

        // Get all the clienteList where apellido2 equals to UPDATED_APELLIDO_2
        defaultClienteShouldNotBeFound("apellido2.in=" + UPDATED_APELLIDO_2);
    }

    @Test
    @Transactional
    public void getAllClientesByApellido2IsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where apellido2 is not null
        defaultClienteShouldBeFound("apellido2.specified=true");

        // Get all the clienteList where apellido2 is null
        defaultClienteShouldNotBeFound("apellido2.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByNifIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nif equals to DEFAULT_NIF
        defaultClienteShouldBeFound("nif.equals=" + DEFAULT_NIF);

        // Get all the clienteList where nif equals to UPDATED_NIF
        defaultClienteShouldNotBeFound("nif.equals=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    public void getAllClientesByNifIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nif in DEFAULT_NIF or UPDATED_NIF
        defaultClienteShouldBeFound("nif.in=" + DEFAULT_NIF + "," + UPDATED_NIF);

        // Get all the clienteList where nif equals to UPDATED_NIF
        defaultClienteShouldNotBeFound("nif.in=" + UPDATED_NIF);
    }

    @Test
    @Transactional
    public void getAllClientesByNifIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where nif is not null
        defaultClienteShouldBeFound("nif.specified=true");

        // Get all the clienteList where nif is null
        defaultClienteShouldNotBeFound("nif.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByGeneroIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where genero equals to DEFAULT_GENERO
        defaultClienteShouldBeFound("genero.equals=" + DEFAULT_GENERO);

        // Get all the clienteList where genero equals to UPDATED_GENERO
        defaultClienteShouldNotBeFound("genero.equals=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void getAllClientesByGeneroIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where genero in DEFAULT_GENERO or UPDATED_GENERO
        defaultClienteShouldBeFound("genero.in=" + DEFAULT_GENERO + "," + UPDATED_GENERO);

        // Get all the clienteList where genero equals to UPDATED_GENERO
        defaultClienteShouldNotBeFound("genero.in=" + UPDATED_GENERO);
    }

    @Test
    @Transactional
    public void getAllClientesByGeneroIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where genero is not null
        defaultClienteShouldBeFound("genero.specified=true");

        // Get all the clienteList where genero is null
        defaultClienteShouldNotBeFound("genero.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByTlfMovilIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where tlfMovil equals to DEFAULT_TLF_MOVIL
        defaultClienteShouldBeFound("tlfMovil.equals=" + DEFAULT_TLF_MOVIL);

        // Get all the clienteList where tlfMovil equals to UPDATED_TLF_MOVIL
        defaultClienteShouldNotBeFound("tlfMovil.equals=" + UPDATED_TLF_MOVIL);
    }

    @Test
    @Transactional
    public void getAllClientesByTlfMovilIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where tlfMovil in DEFAULT_TLF_MOVIL or UPDATED_TLF_MOVIL
        defaultClienteShouldBeFound("tlfMovil.in=" + DEFAULT_TLF_MOVIL + "," + UPDATED_TLF_MOVIL);

        // Get all the clienteList where tlfMovil equals to UPDATED_TLF_MOVIL
        defaultClienteShouldNotBeFound("tlfMovil.in=" + UPDATED_TLF_MOVIL);
    }

    @Test
    @Transactional
    public void getAllClientesByTlfMovilIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where tlfMovil is not null
        defaultClienteShouldBeFound("tlfMovil.specified=true");

        // Get all the clienteList where tlfMovil is null
        defaultClienteShouldNotBeFound("tlfMovil.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByTlfMovil2IsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where tlfMovil2 equals to DEFAULT_TLF_MOVIL_2
        defaultClienteShouldBeFound("tlfMovil2.equals=" + DEFAULT_TLF_MOVIL_2);

        // Get all the clienteList where tlfMovil2 equals to UPDATED_TLF_MOVIL_2
        defaultClienteShouldNotBeFound("tlfMovil2.equals=" + UPDATED_TLF_MOVIL_2);
    }

    @Test
    @Transactional
    public void getAllClientesByTlfMovil2IsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where tlfMovil2 in DEFAULT_TLF_MOVIL_2 or UPDATED_TLF_MOVIL_2
        defaultClienteShouldBeFound("tlfMovil2.in=" + DEFAULT_TLF_MOVIL_2 + "," + UPDATED_TLF_MOVIL_2);

        // Get all the clienteList where tlfMovil2 equals to UPDATED_TLF_MOVIL_2
        defaultClienteShouldNotBeFound("tlfMovil2.in=" + UPDATED_TLF_MOVIL_2);
    }

    @Test
    @Transactional
    public void getAllClientesByTlfMovil2IsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where tlfMovil2 is not null
        defaultClienteShouldBeFound("tlfMovil2.specified=true");

        // Get all the clienteList where tlfMovil2 is null
        defaultClienteShouldNotBeFound("tlfMovil2.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByTlfFijoIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where tlfFijo equals to DEFAULT_TLF_FIJO
        defaultClienteShouldBeFound("tlfFijo.equals=" + DEFAULT_TLF_FIJO);

        // Get all the clienteList where tlfFijo equals to UPDATED_TLF_FIJO
        defaultClienteShouldNotBeFound("tlfFijo.equals=" + UPDATED_TLF_FIJO);
    }

    @Test
    @Transactional
    public void getAllClientesByTlfFijoIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where tlfFijo in DEFAULT_TLF_FIJO or UPDATED_TLF_FIJO
        defaultClienteShouldBeFound("tlfFijo.in=" + DEFAULT_TLF_FIJO + "," + UPDATED_TLF_FIJO);

        // Get all the clienteList where tlfFijo equals to UPDATED_TLF_FIJO
        defaultClienteShouldNotBeFound("tlfFijo.in=" + UPDATED_TLF_FIJO);
    }

    @Test
    @Transactional
    public void getAllClientesByTlfFijoIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where tlfFijo is not null
        defaultClienteShouldBeFound("tlfFijo.specified=true");

        // Get all the clienteList where tlfFijo is null
        defaultClienteShouldNotBeFound("tlfFijo.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fax equals to DEFAULT_FAX
        defaultClienteShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the clienteList where fax equals to UPDATED_FAX
        defaultClienteShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClientesByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultClienteShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the clienteList where fax equals to UPDATED_FAX
        defaultClienteShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllClientesByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where fax is not null
        defaultClienteShouldBeFound("fax.specified=true");

        // Get all the clienteList where fax is null
        defaultClienteShouldNotBeFound("fax.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByEmail1IsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where email1 equals to DEFAULT_EMAIL_1
        defaultClienteShouldBeFound("email1.equals=" + DEFAULT_EMAIL_1);

        // Get all the clienteList where email1 equals to UPDATED_EMAIL_1
        defaultClienteShouldNotBeFound("email1.equals=" + UPDATED_EMAIL_1);
    }

    @Test
    @Transactional
    public void getAllClientesByEmail1IsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where email1 in DEFAULT_EMAIL_1 or UPDATED_EMAIL_1
        defaultClienteShouldBeFound("email1.in=" + DEFAULT_EMAIL_1 + "," + UPDATED_EMAIL_1);

        // Get all the clienteList where email1 equals to UPDATED_EMAIL_1
        defaultClienteShouldNotBeFound("email1.in=" + UPDATED_EMAIL_1);
    }

    @Test
    @Transactional
    public void getAllClientesByEmail1IsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where email1 is not null
        defaultClienteShouldBeFound("email1.specified=true");

        // Get all the clienteList where email1 is null
        defaultClienteShouldNotBeFound("email1.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByEmail2IsEqualToSomething() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where email2 equals to DEFAULT_EMAIL_2
        defaultClienteShouldBeFound("email2.equals=" + DEFAULT_EMAIL_2);

        // Get all the clienteList where email2 equals to UPDATED_EMAIL_2
        defaultClienteShouldNotBeFound("email2.equals=" + UPDATED_EMAIL_2);
    }

    @Test
    @Transactional
    public void getAllClientesByEmail2IsInShouldWork() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where email2 in DEFAULT_EMAIL_2 or UPDATED_EMAIL_2
        defaultClienteShouldBeFound("email2.in=" + DEFAULT_EMAIL_2 + "," + UPDATED_EMAIL_2);

        // Get all the clienteList where email2 equals to UPDATED_EMAIL_2
        defaultClienteShouldNotBeFound("email2.in=" + UPDATED_EMAIL_2);
    }

    @Test
    @Transactional
    public void getAllClientesByEmail2IsNullOrNotNull() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        // Get all the clienteList where email2 is not null
        defaultClienteShouldBeFound("email2.specified=true");

        // Get all the clienteList where email2 is null
        defaultClienteShouldNotBeFound("email2.specified=false");
    }

    @Test
    @Transactional
    public void getAllClientesByFuenteIsEqualToSomething() throws Exception {
        // Get already existing entity
        Fuente fuente = cliente.getFuente();
        clienteRepository.saveAndFlush(cliente);
        Long fuenteId = fuente.getId();

        // Get all the clienteList where fuente equals to fuenteId
        defaultClienteShouldBeFound("fuenteId.equals=" + fuenteId);

        // Get all the clienteList where fuente equals to fuenteId + 1
        defaultClienteShouldNotBeFound("fuenteId.equals=" + (fuenteId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClienteShouldBeFound(String filter) throws Exception {
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cliente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].apellido1").value(hasItem(DEFAULT_APELLIDO_1)))
            .andExpect(jsonPath("$.[*].apellido2").value(hasItem(DEFAULT_APELLIDO_2)))
            .andExpect(jsonPath("$.[*].nif").value(hasItem(DEFAULT_NIF)))
            .andExpect(jsonPath("$.[*].genero").value(hasItem(DEFAULT_GENERO.toString())))
            .andExpect(jsonPath("$.[*].tlfMovil").value(hasItem(DEFAULT_TLF_MOVIL)))
            .andExpect(jsonPath("$.[*].tlfMovil2").value(hasItem(DEFAULT_TLF_MOVIL_2)))
            .andExpect(jsonPath("$.[*].tlfFijo").value(hasItem(DEFAULT_TLF_FIJO)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].email1").value(hasItem(DEFAULT_EMAIL_1)))
            .andExpect(jsonPath("$.[*].email2").value(hasItem(DEFAULT_EMAIL_2)));

        // Check, that the count call also returns 1
        restClienteMockMvc.perform(get("/api/clientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClienteShouldNotBeFound(String filter) throws Exception {
        restClienteMockMvc.perform(get("/api/clientes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClienteMockMvc.perform(get("/api/clientes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCliente() throws Exception {
        // Get the cliente
        restClienteMockMvc.perform(get("/api/clientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Update the cliente
        Cliente updatedCliente = clienteRepository.findById(cliente.getId()).get();
        // Disconnect from session so that the updates on updatedCliente are not directly saved in db
        em.detach(updatedCliente);
        updatedCliente
            .nombre(UPDATED_NOMBRE)
            .apellido1(UPDATED_APELLIDO_1)
            .apellido2(UPDATED_APELLIDO_2)
            .nif(UPDATED_NIF)
            .genero(UPDATED_GENERO)
            .tlfMovil(UPDATED_TLF_MOVIL)
            .tlfMovil2(UPDATED_TLF_MOVIL_2)
            .tlfFijo(UPDATED_TLF_FIJO)
            .fax(UPDATED_FAX)
            .email1(UPDATED_EMAIL_1)
            .email2(UPDATED_EMAIL_2);
        ClienteDTO clienteDTO = clienteMapper.toDto(updatedCliente);

        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isOk());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
        Cliente testCliente = clienteList.get(clienteList.size() - 1);
        assertThat(testCliente.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testCliente.getApellido1()).isEqualTo(UPDATED_APELLIDO_1);
        assertThat(testCliente.getApellido2()).isEqualTo(UPDATED_APELLIDO_2);
        assertThat(testCliente.getNif()).isEqualTo(UPDATED_NIF);
        assertThat(testCliente.getGenero()).isEqualTo(UPDATED_GENERO);
        assertThat(testCliente.getTlfMovil()).isEqualTo(UPDATED_TLF_MOVIL);
        assertThat(testCliente.getTlfMovil2()).isEqualTo(UPDATED_TLF_MOVIL_2);
        assertThat(testCliente.getTlfFijo()).isEqualTo(UPDATED_TLF_FIJO);
        assertThat(testCliente.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testCliente.getEmail1()).isEqualTo(UPDATED_EMAIL_1);
        assertThat(testCliente.getEmail2()).isEqualTo(UPDATED_EMAIL_2);
    }

    @Test
    @Transactional
    public void updateNonExistingCliente() throws Exception {
        int databaseSizeBeforeUpdate = clienteRepository.findAll().size();

        // Create the Cliente
        ClienteDTO clienteDTO = clienteMapper.toDto(cliente);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClienteMockMvc.perform(put("/api/clientes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clienteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cliente in the database
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCliente() throws Exception {
        // Initialize the database
        clienteRepository.saveAndFlush(cliente);

        int databaseSizeBeforeDelete = clienteRepository.findAll().size();

        // Delete the cliente
        restClienteMockMvc.perform(delete("/api/clientes/{id}", cliente.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cliente> clienteList = clienteRepository.findAll();
        assertThat(clienteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cliente.class);
        Cliente cliente1 = new Cliente();
        cliente1.setId(1L);
        Cliente cliente2 = new Cliente();
        cliente2.setId(cliente1.getId());
        assertThat(cliente1).isEqualTo(cliente2);
        cliente2.setId(2L);
        assertThat(cliente1).isNotEqualTo(cliente2);
        cliente1.setId(null);
        assertThat(cliente1).isNotEqualTo(cliente2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClienteDTO.class);
        ClienteDTO clienteDTO1 = new ClienteDTO();
        clienteDTO1.setId(1L);
        ClienteDTO clienteDTO2 = new ClienteDTO();
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
        clienteDTO2.setId(clienteDTO1.getId());
        assertThat(clienteDTO1).isEqualTo(clienteDTO2);
        clienteDTO2.setId(2L);
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
        clienteDTO1.setId(null);
        assertThat(clienteDTO1).isNotEqualTo(clienteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(clienteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(clienteMapper.fromId(null)).isNull();
    }
}
