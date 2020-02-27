package com.lastcrm.gateway.web.rest;

import com.lastcrm.gateway.LastcrmApp;
import com.lastcrm.gateway.domain.Producto;
import com.lastcrm.gateway.domain.Municipio;
import com.lastcrm.gateway.domain.TipoProducto;
import com.lastcrm.gateway.repository.ProductoRepository;
import com.lastcrm.gateway.service.ProductoService;
import com.lastcrm.gateway.service.dto.ProductoDTO;
import com.lastcrm.gateway.service.mapper.ProductoMapper;
import com.lastcrm.gateway.web.rest.errors.ExceptionTranslator;
import com.lastcrm.gateway.service.dto.ProductoCriteria;
import com.lastcrm.gateway.service.ProductoQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.lastcrm.gateway.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.lastcrm.gateway.domain.enumeration.Solicitud;
/**
 * Integration tests for the {@link ProductoResource} REST controller.
 */
@SpringBootTest(classes = LastcrmApp.class)
public class ProductoResourceIT {

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final Solicitud DEFAULT_DESTINO = Solicitud.COMPRA;
    private static final Solicitud UPDATED_DESTINO = Solicitud.VENTA;

    private static final Long DEFAULT_PRECIO = 1L;
    private static final Long UPDATED_PRECIO = 2L;
    private static final Long SMALLER_PRECIO = 1L - 1L;

    private static final byte[] DEFAULT_IMAGE_1 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_1 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_1_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_1_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_2 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_2 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_2_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_2_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_3 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_3 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_3_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_3_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_4 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_4 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_4_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_4_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_IMAGE_5 = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE_5 = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_5_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_5_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_PRECIO_ANTERIOR = 1L;
    private static final Long UPDATED_PRECIO_ANTERIOR = 2L;
    private static final Long SMALLER_PRECIO_ANTERIOR = 1L - 1L;

    private static final Integer DEFAULT_DORMITORIOS = 1;
    private static final Integer UPDATED_DORMITORIOS = 2;
    private static final Integer SMALLER_DORMITORIOS = 1 - 1;

    private static final Integer DEFAULT_ASEOS = 1;
    private static final Integer UPDATED_ASEOS = 2;
    private static final Integer SMALLER_ASEOS = 1 - 1;

    private static final Long DEFAULT_METROS = 1L;
    private static final Long UPDATED_METROS = 2L;
    private static final Long SMALLER_METROS = 1L - 1L;

    private static final Integer DEFAULT_GARAGE = 1;
    private static final Integer UPDATED_GARAGE = 2;
    private static final Integer SMALLER_GARAGE = 1 - 1;

    private static final Integer DEFAULT_ANIOCONSTRUCCION = 1;
    private static final Integer UPDATED_ANIOCONSTRUCCION = 2;
    private static final Integer SMALLER_ANIOCONSTRUCCION = 1 - 1;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoQueryService productoQueryService;

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

    private MockMvc restProductoMockMvc;

    private Producto producto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoResource productoResource = new ProductoResource(productoService, productoQueryService);
        this.restProductoMockMvc = MockMvcBuilders.standaloneSetup(productoResource)
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
    public static Producto createEntity(EntityManager em) {
        Producto producto = new Producto()
            .direccion(DEFAULT_DIRECCION)
            .comentario(DEFAULT_COMENTARIO)
            .destino(DEFAULT_DESTINO)
            .precio(DEFAULT_PRECIO)
            .image1(DEFAULT_IMAGE_1)
            .image1ContentType(DEFAULT_IMAGE_1_CONTENT_TYPE)
            .image2(DEFAULT_IMAGE_2)
            .image2ContentType(DEFAULT_IMAGE_2_CONTENT_TYPE)
            .image3(DEFAULT_IMAGE_3)
            .image3ContentType(DEFAULT_IMAGE_3_CONTENT_TYPE)
            .image4(DEFAULT_IMAGE_4)
            .image4ContentType(DEFAULT_IMAGE_4_CONTENT_TYPE)
            .image5(DEFAULT_IMAGE_5)
            .image5ContentType(DEFAULT_IMAGE_5_CONTENT_TYPE)
            .precioAnterior(DEFAULT_PRECIO_ANTERIOR)
            .dormitorios(DEFAULT_DORMITORIOS)
            .aseos(DEFAULT_ASEOS)
            .metros(DEFAULT_METROS)
            .garage(DEFAULT_GARAGE)
            .anioconstruccion(DEFAULT_ANIOCONSTRUCCION);
        // Add required entity
        Municipio municipio;
        if (TestUtil.findAll(em, Municipio.class).isEmpty()) {
            municipio = MunicipioResourceIT.createEntity(em);
            em.persist(municipio);
            em.flush();
        } else {
            municipio = TestUtil.findAll(em, Municipio.class).get(0);
        }
        producto.setMunicipio(municipio);
        // Add required entity
        TipoProducto tipoProducto;
        if (TestUtil.findAll(em, TipoProducto.class).isEmpty()) {
            tipoProducto = TipoProductoResourceIT.createEntity(em);
            em.persist(tipoProducto);
            em.flush();
        } else {
            tipoProducto = TestUtil.findAll(em, TipoProducto.class).get(0);
        }
        producto.setTipoProducto(tipoProducto);
        return producto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createUpdatedEntity(EntityManager em) {
        Producto producto = new Producto()
            .direccion(UPDATED_DIRECCION)
            .comentario(UPDATED_COMENTARIO)
            .destino(UPDATED_DESTINO)
            .precio(UPDATED_PRECIO)
            .image1(UPDATED_IMAGE_1)
            .image1ContentType(UPDATED_IMAGE_1_CONTENT_TYPE)
            .image2(UPDATED_IMAGE_2)
            .image2ContentType(UPDATED_IMAGE_2_CONTENT_TYPE)
            .image3(UPDATED_IMAGE_3)
            .image3ContentType(UPDATED_IMAGE_3_CONTENT_TYPE)
            .image4(UPDATED_IMAGE_4)
            .image4ContentType(UPDATED_IMAGE_4_CONTENT_TYPE)
            .image5(UPDATED_IMAGE_5)
            .image5ContentType(UPDATED_IMAGE_5_CONTENT_TYPE)
            .precioAnterior(UPDATED_PRECIO_ANTERIOR)
            .dormitorios(UPDATED_DORMITORIOS)
            .aseos(UPDATED_ASEOS)
            .metros(UPDATED_METROS)
            .garage(UPDATED_GARAGE)
            .anioconstruccion(UPDATED_ANIOCONSTRUCCION);
        // Add required entity
        Municipio municipio;
        if (TestUtil.findAll(em, Municipio.class).isEmpty()) {
            municipio = MunicipioResourceIT.createUpdatedEntity(em);
            em.persist(municipio);
            em.flush();
        } else {
            municipio = TestUtil.findAll(em, Municipio.class).get(0);
        }
        producto.setMunicipio(municipio);
        // Add required entity
        TipoProducto tipoProducto;
        if (TestUtil.findAll(em, TipoProducto.class).isEmpty()) {
            tipoProducto = TipoProductoResourceIT.createUpdatedEntity(em);
            em.persist(tipoProducto);
            em.flush();
        } else {
            tipoProducto = TestUtil.findAll(em, TipoProducto.class).get(0);
        }
        producto.setTipoProducto(tipoProducto);
        return producto;
    }

    @BeforeEach
    public void initTest() {
        producto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProducto() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isCreated());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate + 1);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testProducto.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testProducto.getDestino()).isEqualTo(DEFAULT_DESTINO);
        assertThat(testProducto.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testProducto.getImage1()).isEqualTo(DEFAULT_IMAGE_1);
        assertThat(testProducto.getImage1ContentType()).isEqualTo(DEFAULT_IMAGE_1_CONTENT_TYPE);
        assertThat(testProducto.getImage2()).isEqualTo(DEFAULT_IMAGE_2);
        assertThat(testProducto.getImage2ContentType()).isEqualTo(DEFAULT_IMAGE_2_CONTENT_TYPE);
        assertThat(testProducto.getImage3()).isEqualTo(DEFAULT_IMAGE_3);
        assertThat(testProducto.getImage3ContentType()).isEqualTo(DEFAULT_IMAGE_3_CONTENT_TYPE);
        assertThat(testProducto.getImage4()).isEqualTo(DEFAULT_IMAGE_4);
        assertThat(testProducto.getImage4ContentType()).isEqualTo(DEFAULT_IMAGE_4_CONTENT_TYPE);
        assertThat(testProducto.getImage5()).isEqualTo(DEFAULT_IMAGE_5);
        assertThat(testProducto.getImage5ContentType()).isEqualTo(DEFAULT_IMAGE_5_CONTENT_TYPE);
        assertThat(testProducto.getPrecioAnterior()).isEqualTo(DEFAULT_PRECIO_ANTERIOR);
        assertThat(testProducto.getDormitorios()).isEqualTo(DEFAULT_DORMITORIOS);
        assertThat(testProducto.getAseos()).isEqualTo(DEFAULT_ASEOS);
        assertThat(testProducto.getMetros()).isEqualTo(DEFAULT_METROS);
        assertThat(testProducto.getGarage()).isEqualTo(DEFAULT_GARAGE);
        assertThat(testProducto.getAnioconstruccion()).isEqualTo(DEFAULT_ANIOCONSTRUCCION);
    }

    @Test
    @Transactional
    public void createProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto with an existing ID
        producto.setId(1L);
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setDireccion(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDestinoIsRequired() throws Exception {
        int databaseSizeBeforeTest = productoRepository.findAll().size();
        // set the field null
        producto.setDestino(null);

        // Create the Producto, which fails.
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProductos() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].destino").value(hasItem(DEFAULT_DESTINO.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())))
            .andExpect(jsonPath("$.[*].image1ContentType").value(hasItem(DEFAULT_IMAGE_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_1))))
            .andExpect(jsonPath("$.[*].image2ContentType").value(hasItem(DEFAULT_IMAGE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_2))))
            .andExpect(jsonPath("$.[*].image3ContentType").value(hasItem(DEFAULT_IMAGE_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_3))))
            .andExpect(jsonPath("$.[*].image4ContentType").value(hasItem(DEFAULT_IMAGE_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image4").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_4))))
            .andExpect(jsonPath("$.[*].image5ContentType").value(hasItem(DEFAULT_IMAGE_5_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image5").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_5))))
            .andExpect(jsonPath("$.[*].precioAnterior").value(hasItem(DEFAULT_PRECIO_ANTERIOR.intValue())))
            .andExpect(jsonPath("$.[*].dormitorios").value(hasItem(DEFAULT_DORMITORIOS)))
            .andExpect(jsonPath("$.[*].aseos").value(hasItem(DEFAULT_ASEOS)))
            .andExpect(jsonPath("$.[*].metros").value(hasItem(DEFAULT_METROS.intValue())))
            .andExpect(jsonPath("$.[*].garage").value(hasItem(DEFAULT_GARAGE)))
            .andExpect(jsonPath("$.[*].anioconstruccion").value(hasItem(DEFAULT_ANIOCONSTRUCCION)));
    }
    
    @Test
    @Transactional
    public void getProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(producto.getId().intValue()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.destino").value(DEFAULT_DESTINO.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.intValue()))
            .andExpect(jsonPath("$.image1ContentType").value(DEFAULT_IMAGE_1_CONTENT_TYPE))
            .andExpect(jsonPath("$.image1").value(Base64Utils.encodeToString(DEFAULT_IMAGE_1)))
            .andExpect(jsonPath("$.image2ContentType").value(DEFAULT_IMAGE_2_CONTENT_TYPE))
            .andExpect(jsonPath("$.image2").value(Base64Utils.encodeToString(DEFAULT_IMAGE_2)))
            .andExpect(jsonPath("$.image3ContentType").value(DEFAULT_IMAGE_3_CONTENT_TYPE))
            .andExpect(jsonPath("$.image3").value(Base64Utils.encodeToString(DEFAULT_IMAGE_3)))
            .andExpect(jsonPath("$.image4ContentType").value(DEFAULT_IMAGE_4_CONTENT_TYPE))
            .andExpect(jsonPath("$.image4").value(Base64Utils.encodeToString(DEFAULT_IMAGE_4)))
            .andExpect(jsonPath("$.image5ContentType").value(DEFAULT_IMAGE_5_CONTENT_TYPE))
            .andExpect(jsonPath("$.image5").value(Base64Utils.encodeToString(DEFAULT_IMAGE_5)))
            .andExpect(jsonPath("$.precioAnterior").value(DEFAULT_PRECIO_ANTERIOR.intValue()))
            .andExpect(jsonPath("$.dormitorios").value(DEFAULT_DORMITORIOS))
            .andExpect(jsonPath("$.aseos").value(DEFAULT_ASEOS))
            .andExpect(jsonPath("$.metros").value(DEFAULT_METROS.intValue()))
            .andExpect(jsonPath("$.garage").value(DEFAULT_GARAGE))
            .andExpect(jsonPath("$.anioconstruccion").value(DEFAULT_ANIOCONSTRUCCION));
    }

    @Test
    @Transactional
    public void getAllProductosByDireccionIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where direccion equals to DEFAULT_DIRECCION
        defaultProductoShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

        // Get all the productoList where direccion equals to UPDATED_DIRECCION
        defaultProductoShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllProductosByDireccionIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where direccion in DEFAULT_DIRECCION or UPDATED_DIRECCION
        defaultProductoShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

        // Get all the productoList where direccion equals to UPDATED_DIRECCION
        defaultProductoShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllProductosByDireccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where direccion is not null
        defaultProductoShouldBeFound("direccion.specified=true");

        // Get all the productoList where direccion is null
        defaultProductoShouldNotBeFound("direccion.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByDestinoIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where destino equals to DEFAULT_DESTINO
        defaultProductoShouldBeFound("destino.equals=" + DEFAULT_DESTINO);

        // Get all the productoList where destino equals to UPDATED_DESTINO
        defaultProductoShouldNotBeFound("destino.equals=" + UPDATED_DESTINO);
    }

    @Test
    @Transactional
    public void getAllProductosByDestinoIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where destino in DEFAULT_DESTINO or UPDATED_DESTINO
        defaultProductoShouldBeFound("destino.in=" + DEFAULT_DESTINO + "," + UPDATED_DESTINO);

        // Get all the productoList where destino equals to UPDATED_DESTINO
        defaultProductoShouldNotBeFound("destino.in=" + UPDATED_DESTINO);
    }

    @Test
    @Transactional
    public void getAllProductosByDestinoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where destino is not null
        defaultProductoShouldBeFound("destino.specified=true");

        // Get all the productoList where destino is null
        defaultProductoShouldNotBeFound("destino.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio equals to DEFAULT_PRECIO
        defaultProductoShouldBeFound("precio.equals=" + DEFAULT_PRECIO);

        // Get all the productoList where precio equals to UPDATED_PRECIO
        defaultProductoShouldNotBeFound("precio.equals=" + UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio in DEFAULT_PRECIO or UPDATED_PRECIO
        defaultProductoShouldBeFound("precio.in=" + DEFAULT_PRECIO + "," + UPDATED_PRECIO);

        // Get all the productoList where precio equals to UPDATED_PRECIO
        defaultProductoShouldNotBeFound("precio.in=" + UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio is not null
        defaultProductoShouldBeFound("precio.specified=true");

        // Get all the productoList where precio is null
        defaultProductoShouldNotBeFound("precio.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio is greater than or equal to DEFAULT_PRECIO
        defaultProductoShouldBeFound("precio.greaterThanOrEqual=" + DEFAULT_PRECIO);

        // Get all the productoList where precio is greater than or equal to UPDATED_PRECIO
        defaultProductoShouldNotBeFound("precio.greaterThanOrEqual=" + UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio is less than or equal to DEFAULT_PRECIO
        defaultProductoShouldBeFound("precio.lessThanOrEqual=" + DEFAULT_PRECIO);

        // Get all the productoList where precio is less than or equal to SMALLER_PRECIO
        defaultProductoShouldNotBeFound("precio.lessThanOrEqual=" + SMALLER_PRECIO);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsLessThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio is less than DEFAULT_PRECIO
        defaultProductoShouldNotBeFound("precio.lessThan=" + DEFAULT_PRECIO);

        // Get all the productoList where precio is less than UPDATED_PRECIO
        defaultProductoShouldBeFound("precio.lessThan=" + UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precio is greater than DEFAULT_PRECIO
        defaultProductoShouldNotBeFound("precio.greaterThan=" + DEFAULT_PRECIO);

        // Get all the productoList where precio is greater than SMALLER_PRECIO
        defaultProductoShouldBeFound("precio.greaterThan=" + SMALLER_PRECIO);
    }


    @Test
    @Transactional
    public void getAllProductosByPrecioAnteriorIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precioAnterior equals to DEFAULT_PRECIO_ANTERIOR
        defaultProductoShouldBeFound("precioAnterior.equals=" + DEFAULT_PRECIO_ANTERIOR);

        // Get all the productoList where precioAnterior equals to UPDATED_PRECIO_ANTERIOR
        defaultProductoShouldNotBeFound("precioAnterior.equals=" + UPDATED_PRECIO_ANTERIOR);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioAnteriorIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precioAnterior in DEFAULT_PRECIO_ANTERIOR or UPDATED_PRECIO_ANTERIOR
        defaultProductoShouldBeFound("precioAnterior.in=" + DEFAULT_PRECIO_ANTERIOR + "," + UPDATED_PRECIO_ANTERIOR);

        // Get all the productoList where precioAnterior equals to UPDATED_PRECIO_ANTERIOR
        defaultProductoShouldNotBeFound("precioAnterior.in=" + UPDATED_PRECIO_ANTERIOR);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioAnteriorIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precioAnterior is not null
        defaultProductoShouldBeFound("precioAnterior.specified=true");

        // Get all the productoList where precioAnterior is null
        defaultProductoShouldNotBeFound("precioAnterior.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioAnteriorIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precioAnterior is greater than or equal to DEFAULT_PRECIO_ANTERIOR
        defaultProductoShouldBeFound("precioAnterior.greaterThanOrEqual=" + DEFAULT_PRECIO_ANTERIOR);

        // Get all the productoList where precioAnterior is greater than or equal to UPDATED_PRECIO_ANTERIOR
        defaultProductoShouldNotBeFound("precioAnterior.greaterThanOrEqual=" + UPDATED_PRECIO_ANTERIOR);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioAnteriorIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precioAnterior is less than or equal to DEFAULT_PRECIO_ANTERIOR
        defaultProductoShouldBeFound("precioAnterior.lessThanOrEqual=" + DEFAULT_PRECIO_ANTERIOR);

        // Get all the productoList where precioAnterior is less than or equal to SMALLER_PRECIO_ANTERIOR
        defaultProductoShouldNotBeFound("precioAnterior.lessThanOrEqual=" + SMALLER_PRECIO_ANTERIOR);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioAnteriorIsLessThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precioAnterior is less than DEFAULT_PRECIO_ANTERIOR
        defaultProductoShouldNotBeFound("precioAnterior.lessThan=" + DEFAULT_PRECIO_ANTERIOR);

        // Get all the productoList where precioAnterior is less than UPDATED_PRECIO_ANTERIOR
        defaultProductoShouldBeFound("precioAnterior.lessThan=" + UPDATED_PRECIO_ANTERIOR);
    }

    @Test
    @Transactional
    public void getAllProductosByPrecioAnteriorIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where precioAnterior is greater than DEFAULT_PRECIO_ANTERIOR
        defaultProductoShouldNotBeFound("precioAnterior.greaterThan=" + DEFAULT_PRECIO_ANTERIOR);

        // Get all the productoList where precioAnterior is greater than SMALLER_PRECIO_ANTERIOR
        defaultProductoShouldBeFound("precioAnterior.greaterThan=" + SMALLER_PRECIO_ANTERIOR);
    }


    @Test
    @Transactional
    public void getAllProductosByDormitoriosIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where dormitorios equals to DEFAULT_DORMITORIOS
        defaultProductoShouldBeFound("dormitorios.equals=" + DEFAULT_DORMITORIOS);

        // Get all the productoList where dormitorios equals to UPDATED_DORMITORIOS
        defaultProductoShouldNotBeFound("dormitorios.equals=" + UPDATED_DORMITORIOS);
    }

    @Test
    @Transactional
    public void getAllProductosByDormitoriosIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where dormitorios in DEFAULT_DORMITORIOS or UPDATED_DORMITORIOS
        defaultProductoShouldBeFound("dormitorios.in=" + DEFAULT_DORMITORIOS + "," + UPDATED_DORMITORIOS);

        // Get all the productoList where dormitorios equals to UPDATED_DORMITORIOS
        defaultProductoShouldNotBeFound("dormitorios.in=" + UPDATED_DORMITORIOS);
    }

    @Test
    @Transactional
    public void getAllProductosByDormitoriosIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where dormitorios is not null
        defaultProductoShouldBeFound("dormitorios.specified=true");

        // Get all the productoList where dormitorios is null
        defaultProductoShouldNotBeFound("dormitorios.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByDormitoriosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where dormitorios is greater than or equal to DEFAULT_DORMITORIOS
        defaultProductoShouldBeFound("dormitorios.greaterThanOrEqual=" + DEFAULT_DORMITORIOS);

        // Get all the productoList where dormitorios is greater than or equal to UPDATED_DORMITORIOS
        defaultProductoShouldNotBeFound("dormitorios.greaterThanOrEqual=" + UPDATED_DORMITORIOS);
    }

    @Test
    @Transactional
    public void getAllProductosByDormitoriosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where dormitorios is less than or equal to DEFAULT_DORMITORIOS
        defaultProductoShouldBeFound("dormitorios.lessThanOrEqual=" + DEFAULT_DORMITORIOS);

        // Get all the productoList where dormitorios is less than or equal to SMALLER_DORMITORIOS
        defaultProductoShouldNotBeFound("dormitorios.lessThanOrEqual=" + SMALLER_DORMITORIOS);
    }

    @Test
    @Transactional
    public void getAllProductosByDormitoriosIsLessThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where dormitorios is less than DEFAULT_DORMITORIOS
        defaultProductoShouldNotBeFound("dormitorios.lessThan=" + DEFAULT_DORMITORIOS);

        // Get all the productoList where dormitorios is less than UPDATED_DORMITORIOS
        defaultProductoShouldBeFound("dormitorios.lessThan=" + UPDATED_DORMITORIOS);
    }

    @Test
    @Transactional
    public void getAllProductosByDormitoriosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where dormitorios is greater than DEFAULT_DORMITORIOS
        defaultProductoShouldNotBeFound("dormitorios.greaterThan=" + DEFAULT_DORMITORIOS);

        // Get all the productoList where dormitorios is greater than SMALLER_DORMITORIOS
        defaultProductoShouldBeFound("dormitorios.greaterThan=" + SMALLER_DORMITORIOS);
    }


    @Test
    @Transactional
    public void getAllProductosByAseosIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where aseos equals to DEFAULT_ASEOS
        defaultProductoShouldBeFound("aseos.equals=" + DEFAULT_ASEOS);

        // Get all the productoList where aseos equals to UPDATED_ASEOS
        defaultProductoShouldNotBeFound("aseos.equals=" + UPDATED_ASEOS);
    }

    @Test
    @Transactional
    public void getAllProductosByAseosIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where aseos in DEFAULT_ASEOS or UPDATED_ASEOS
        defaultProductoShouldBeFound("aseos.in=" + DEFAULT_ASEOS + "," + UPDATED_ASEOS);

        // Get all the productoList where aseos equals to UPDATED_ASEOS
        defaultProductoShouldNotBeFound("aseos.in=" + UPDATED_ASEOS);
    }

    @Test
    @Transactional
    public void getAllProductosByAseosIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where aseos is not null
        defaultProductoShouldBeFound("aseos.specified=true");

        // Get all the productoList where aseos is null
        defaultProductoShouldNotBeFound("aseos.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByAseosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where aseos is greater than or equal to DEFAULT_ASEOS
        defaultProductoShouldBeFound("aseos.greaterThanOrEqual=" + DEFAULT_ASEOS);

        // Get all the productoList where aseos is greater than or equal to UPDATED_ASEOS
        defaultProductoShouldNotBeFound("aseos.greaterThanOrEqual=" + UPDATED_ASEOS);
    }

    @Test
    @Transactional
    public void getAllProductosByAseosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where aseos is less than or equal to DEFAULT_ASEOS
        defaultProductoShouldBeFound("aseos.lessThanOrEqual=" + DEFAULT_ASEOS);

        // Get all the productoList where aseos is less than or equal to SMALLER_ASEOS
        defaultProductoShouldNotBeFound("aseos.lessThanOrEqual=" + SMALLER_ASEOS);
    }

    @Test
    @Transactional
    public void getAllProductosByAseosIsLessThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where aseos is less than DEFAULT_ASEOS
        defaultProductoShouldNotBeFound("aseos.lessThan=" + DEFAULT_ASEOS);

        // Get all the productoList where aseos is less than UPDATED_ASEOS
        defaultProductoShouldBeFound("aseos.lessThan=" + UPDATED_ASEOS);
    }

    @Test
    @Transactional
    public void getAllProductosByAseosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where aseos is greater than DEFAULT_ASEOS
        defaultProductoShouldNotBeFound("aseos.greaterThan=" + DEFAULT_ASEOS);

        // Get all the productoList where aseos is greater than SMALLER_ASEOS
        defaultProductoShouldBeFound("aseos.greaterThan=" + SMALLER_ASEOS);
    }


    @Test
    @Transactional
    public void getAllProductosByMetrosIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where metros equals to DEFAULT_METROS
        defaultProductoShouldBeFound("metros.equals=" + DEFAULT_METROS);

        // Get all the productoList where metros equals to UPDATED_METROS
        defaultProductoShouldNotBeFound("metros.equals=" + UPDATED_METROS);
    }

    @Test
    @Transactional
    public void getAllProductosByMetrosIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where metros in DEFAULT_METROS or UPDATED_METROS
        defaultProductoShouldBeFound("metros.in=" + DEFAULT_METROS + "," + UPDATED_METROS);

        // Get all the productoList where metros equals to UPDATED_METROS
        defaultProductoShouldNotBeFound("metros.in=" + UPDATED_METROS);
    }

    @Test
    @Transactional
    public void getAllProductosByMetrosIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where metros is not null
        defaultProductoShouldBeFound("metros.specified=true");

        // Get all the productoList where metros is null
        defaultProductoShouldNotBeFound("metros.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByMetrosIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where metros is greater than or equal to DEFAULT_METROS
        defaultProductoShouldBeFound("metros.greaterThanOrEqual=" + DEFAULT_METROS);

        // Get all the productoList where metros is greater than or equal to UPDATED_METROS
        defaultProductoShouldNotBeFound("metros.greaterThanOrEqual=" + UPDATED_METROS);
    }

    @Test
    @Transactional
    public void getAllProductosByMetrosIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where metros is less than or equal to DEFAULT_METROS
        defaultProductoShouldBeFound("metros.lessThanOrEqual=" + DEFAULT_METROS);

        // Get all the productoList where metros is less than or equal to SMALLER_METROS
        defaultProductoShouldNotBeFound("metros.lessThanOrEqual=" + SMALLER_METROS);
    }

    @Test
    @Transactional
    public void getAllProductosByMetrosIsLessThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where metros is less than DEFAULT_METROS
        defaultProductoShouldNotBeFound("metros.lessThan=" + DEFAULT_METROS);

        // Get all the productoList where metros is less than UPDATED_METROS
        defaultProductoShouldBeFound("metros.lessThan=" + UPDATED_METROS);
    }

    @Test
    @Transactional
    public void getAllProductosByMetrosIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where metros is greater than DEFAULT_METROS
        defaultProductoShouldNotBeFound("metros.greaterThan=" + DEFAULT_METROS);

        // Get all the productoList where metros is greater than SMALLER_METROS
        defaultProductoShouldBeFound("metros.greaterThan=" + SMALLER_METROS);
    }


    @Test
    @Transactional
    public void getAllProductosByGarageIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where garage equals to DEFAULT_GARAGE
        defaultProductoShouldBeFound("garage.equals=" + DEFAULT_GARAGE);

        // Get all the productoList where garage equals to UPDATED_GARAGE
        defaultProductoShouldNotBeFound("garage.equals=" + UPDATED_GARAGE);
    }

    @Test
    @Transactional
    public void getAllProductosByGarageIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where garage in DEFAULT_GARAGE or UPDATED_GARAGE
        defaultProductoShouldBeFound("garage.in=" + DEFAULT_GARAGE + "," + UPDATED_GARAGE);

        // Get all the productoList where garage equals to UPDATED_GARAGE
        defaultProductoShouldNotBeFound("garage.in=" + UPDATED_GARAGE);
    }

    @Test
    @Transactional
    public void getAllProductosByGarageIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where garage is not null
        defaultProductoShouldBeFound("garage.specified=true");

        // Get all the productoList where garage is null
        defaultProductoShouldNotBeFound("garage.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByGarageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where garage is greater than or equal to DEFAULT_GARAGE
        defaultProductoShouldBeFound("garage.greaterThanOrEqual=" + DEFAULT_GARAGE);

        // Get all the productoList where garage is greater than or equal to UPDATED_GARAGE
        defaultProductoShouldNotBeFound("garage.greaterThanOrEqual=" + UPDATED_GARAGE);
    }

    @Test
    @Transactional
    public void getAllProductosByGarageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where garage is less than or equal to DEFAULT_GARAGE
        defaultProductoShouldBeFound("garage.lessThanOrEqual=" + DEFAULT_GARAGE);

        // Get all the productoList where garage is less than or equal to SMALLER_GARAGE
        defaultProductoShouldNotBeFound("garage.lessThanOrEqual=" + SMALLER_GARAGE);
    }

    @Test
    @Transactional
    public void getAllProductosByGarageIsLessThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where garage is less than DEFAULT_GARAGE
        defaultProductoShouldNotBeFound("garage.lessThan=" + DEFAULT_GARAGE);

        // Get all the productoList where garage is less than UPDATED_GARAGE
        defaultProductoShouldBeFound("garage.lessThan=" + UPDATED_GARAGE);
    }

    @Test
    @Transactional
    public void getAllProductosByGarageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where garage is greater than DEFAULT_GARAGE
        defaultProductoShouldNotBeFound("garage.greaterThan=" + DEFAULT_GARAGE);

        // Get all the productoList where garage is greater than SMALLER_GARAGE
        defaultProductoShouldBeFound("garage.greaterThan=" + SMALLER_GARAGE);
    }


    @Test
    @Transactional
    public void getAllProductosByAnioconstruccionIsEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where anioconstruccion equals to DEFAULT_ANIOCONSTRUCCION
        defaultProductoShouldBeFound("anioconstruccion.equals=" + DEFAULT_ANIOCONSTRUCCION);

        // Get all the productoList where anioconstruccion equals to UPDATED_ANIOCONSTRUCCION
        defaultProductoShouldNotBeFound("anioconstruccion.equals=" + UPDATED_ANIOCONSTRUCCION);
    }

    @Test
    @Transactional
    public void getAllProductosByAnioconstruccionIsInShouldWork() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where anioconstruccion in DEFAULT_ANIOCONSTRUCCION or UPDATED_ANIOCONSTRUCCION
        defaultProductoShouldBeFound("anioconstruccion.in=" + DEFAULT_ANIOCONSTRUCCION + "," + UPDATED_ANIOCONSTRUCCION);

        // Get all the productoList where anioconstruccion equals to UPDATED_ANIOCONSTRUCCION
        defaultProductoShouldNotBeFound("anioconstruccion.in=" + UPDATED_ANIOCONSTRUCCION);
    }

    @Test
    @Transactional
    public void getAllProductosByAnioconstruccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where anioconstruccion is not null
        defaultProductoShouldBeFound("anioconstruccion.specified=true");

        // Get all the productoList where anioconstruccion is null
        defaultProductoShouldNotBeFound("anioconstruccion.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductosByAnioconstruccionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where anioconstruccion is greater than or equal to DEFAULT_ANIOCONSTRUCCION
        defaultProductoShouldBeFound("anioconstruccion.greaterThanOrEqual=" + DEFAULT_ANIOCONSTRUCCION);

        // Get all the productoList where anioconstruccion is greater than or equal to UPDATED_ANIOCONSTRUCCION
        defaultProductoShouldNotBeFound("anioconstruccion.greaterThanOrEqual=" + UPDATED_ANIOCONSTRUCCION);
    }

    @Test
    @Transactional
    public void getAllProductosByAnioconstruccionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where anioconstruccion is less than or equal to DEFAULT_ANIOCONSTRUCCION
        defaultProductoShouldBeFound("anioconstruccion.lessThanOrEqual=" + DEFAULT_ANIOCONSTRUCCION);

        // Get all the productoList where anioconstruccion is less than or equal to SMALLER_ANIOCONSTRUCCION
        defaultProductoShouldNotBeFound("anioconstruccion.lessThanOrEqual=" + SMALLER_ANIOCONSTRUCCION);
    }

    @Test
    @Transactional
    public void getAllProductosByAnioconstruccionIsLessThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where anioconstruccion is less than DEFAULT_ANIOCONSTRUCCION
        defaultProductoShouldNotBeFound("anioconstruccion.lessThan=" + DEFAULT_ANIOCONSTRUCCION);

        // Get all the productoList where anioconstruccion is less than UPDATED_ANIOCONSTRUCCION
        defaultProductoShouldBeFound("anioconstruccion.lessThan=" + UPDATED_ANIOCONSTRUCCION);
    }

    @Test
    @Transactional
    public void getAllProductosByAnioconstruccionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList where anioconstruccion is greater than DEFAULT_ANIOCONSTRUCCION
        defaultProductoShouldNotBeFound("anioconstruccion.greaterThan=" + DEFAULT_ANIOCONSTRUCCION);

        // Get all the productoList where anioconstruccion is greater than SMALLER_ANIOCONSTRUCCION
        defaultProductoShouldBeFound("anioconstruccion.greaterThan=" + SMALLER_ANIOCONSTRUCCION);
    }


    @Test
    @Transactional
    public void getAllProductosByMunicipioIsEqualToSomething() throws Exception {
        // Get already existing entity
        Municipio municipio = producto.getMunicipio();
        productoRepository.saveAndFlush(producto);
        Long municipioId = municipio.getId();

        // Get all the productoList where municipio equals to municipioId
        defaultProductoShouldBeFound("municipioId.equals=" + municipioId);

        // Get all the productoList where municipio equals to municipioId + 1
        defaultProductoShouldNotBeFound("municipioId.equals=" + (municipioId + 1));
    }


    @Test
    @Transactional
    public void getAllProductosByTipoProductoIsEqualToSomething() throws Exception {
        // Get already existing entity
        TipoProducto tipoProducto = producto.getTipoProducto();
        productoRepository.saveAndFlush(producto);
        Long tipoProductoId = tipoProducto.getId();

        // Get all the productoList where tipoProducto equals to tipoProductoId
        defaultProductoShouldBeFound("tipoProductoId.equals=" + tipoProductoId);

        // Get all the productoList where tipoProducto equals to tipoProductoId + 1
        defaultProductoShouldNotBeFound("tipoProductoId.equals=" + (tipoProductoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductoShouldBeFound(String filter) throws Exception {
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].destino").value(hasItem(DEFAULT_DESTINO.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.intValue())))
            .andExpect(jsonPath("$.[*].image1ContentType").value(hasItem(DEFAULT_IMAGE_1_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image1").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_1))))
            .andExpect(jsonPath("$.[*].image2ContentType").value(hasItem(DEFAULT_IMAGE_2_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image2").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_2))))
            .andExpect(jsonPath("$.[*].image3ContentType").value(hasItem(DEFAULT_IMAGE_3_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image3").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_3))))
            .andExpect(jsonPath("$.[*].image4ContentType").value(hasItem(DEFAULT_IMAGE_4_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image4").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_4))))
            .andExpect(jsonPath("$.[*].image5ContentType").value(hasItem(DEFAULT_IMAGE_5_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image5").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE_5))))
            .andExpect(jsonPath("$.[*].precioAnterior").value(hasItem(DEFAULT_PRECIO_ANTERIOR.intValue())))
            .andExpect(jsonPath("$.[*].dormitorios").value(hasItem(DEFAULT_DORMITORIOS)))
            .andExpect(jsonPath("$.[*].aseos").value(hasItem(DEFAULT_ASEOS)))
            .andExpect(jsonPath("$.[*].metros").value(hasItem(DEFAULT_METROS.intValue())))
            .andExpect(jsonPath("$.[*].garage").value(hasItem(DEFAULT_GARAGE)))
            .andExpect(jsonPath("$.[*].anioconstruccion").value(hasItem(DEFAULT_ANIOCONSTRUCCION)));

        // Check, that the count call also returns 1
        restProductoMockMvc.perform(get("/api/productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductoShouldNotBeFound(String filter) throws Exception {
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductoMockMvc.perform(get("/api/productos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProducto() throws Exception {
        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto
        Producto updatedProducto = productoRepository.findById(producto.getId()).get();
        // Disconnect from session so that the updates on updatedProducto are not directly saved in db
        em.detach(updatedProducto);
        updatedProducto
            .direccion(UPDATED_DIRECCION)
            .comentario(UPDATED_COMENTARIO)
            .destino(UPDATED_DESTINO)
            .precio(UPDATED_PRECIO)
            .image1(UPDATED_IMAGE_1)
            .image1ContentType(UPDATED_IMAGE_1_CONTENT_TYPE)
            .image2(UPDATED_IMAGE_2)
            .image2ContentType(UPDATED_IMAGE_2_CONTENT_TYPE)
            .image3(UPDATED_IMAGE_3)
            .image3ContentType(UPDATED_IMAGE_3_CONTENT_TYPE)
            .image4(UPDATED_IMAGE_4)
            .image4ContentType(UPDATED_IMAGE_4_CONTENT_TYPE)
            .image5(UPDATED_IMAGE_5)
            .image5ContentType(UPDATED_IMAGE_5_CONTENT_TYPE)
            .precioAnterior(UPDATED_PRECIO_ANTERIOR)
            .dormitorios(UPDATED_DORMITORIOS)
            .aseos(UPDATED_ASEOS)
            .metros(UPDATED_METROS)
            .garage(UPDATED_GARAGE)
            .anioconstruccion(UPDATED_ANIOCONSTRUCCION);
        ProductoDTO productoDTO = productoMapper.toDto(updatedProducto);

        restProductoMockMvc.perform(put("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testProducto.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testProducto.getDestino()).isEqualTo(UPDATED_DESTINO);
        assertThat(testProducto.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testProducto.getImage1()).isEqualTo(UPDATED_IMAGE_1);
        assertThat(testProducto.getImage1ContentType()).isEqualTo(UPDATED_IMAGE_1_CONTENT_TYPE);
        assertThat(testProducto.getImage2()).isEqualTo(UPDATED_IMAGE_2);
        assertThat(testProducto.getImage2ContentType()).isEqualTo(UPDATED_IMAGE_2_CONTENT_TYPE);
        assertThat(testProducto.getImage3()).isEqualTo(UPDATED_IMAGE_3);
        assertThat(testProducto.getImage3ContentType()).isEqualTo(UPDATED_IMAGE_3_CONTENT_TYPE);
        assertThat(testProducto.getImage4()).isEqualTo(UPDATED_IMAGE_4);
        assertThat(testProducto.getImage4ContentType()).isEqualTo(UPDATED_IMAGE_4_CONTENT_TYPE);
        assertThat(testProducto.getImage5()).isEqualTo(UPDATED_IMAGE_5);
        assertThat(testProducto.getImage5ContentType()).isEqualTo(UPDATED_IMAGE_5_CONTENT_TYPE);
        assertThat(testProducto.getPrecioAnterior()).isEqualTo(UPDATED_PRECIO_ANTERIOR);
        assertThat(testProducto.getDormitorios()).isEqualTo(UPDATED_DORMITORIOS);
        assertThat(testProducto.getAseos()).isEqualTo(UPDATED_ASEOS);
        assertThat(testProducto.getMetros()).isEqualTo(UPDATED_METROS);
        assertThat(testProducto.getGarage()).isEqualTo(UPDATED_GARAGE);
        assertThat(testProducto.getAnioconstruccion()).isEqualTo(UPDATED_ANIOCONSTRUCCION);
    }

    @Test
    @Transactional
    public void updateNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Create the Producto
        ProductoDTO productoDTO = productoMapper.toDto(producto);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMockMvc.perform(put("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(productoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeDelete = productoRepository.findAll().size();

        // Delete the producto
        restProductoMockMvc.perform(delete("/api/productos/{id}", producto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Producto.class);
        Producto producto1 = new Producto();
        producto1.setId(1L);
        Producto producto2 = new Producto();
        producto2.setId(producto1.getId());
        assertThat(producto1).isEqualTo(producto2);
        producto2.setId(2L);
        assertThat(producto1).isNotEqualTo(producto2);
        producto1.setId(null);
        assertThat(producto1).isNotEqualTo(producto2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoDTO.class);
        ProductoDTO productoDTO1 = new ProductoDTO();
        productoDTO1.setId(1L);
        ProductoDTO productoDTO2 = new ProductoDTO();
        assertThat(productoDTO1).isNotEqualTo(productoDTO2);
        productoDTO2.setId(productoDTO1.getId());
        assertThat(productoDTO1).isEqualTo(productoDTO2);
        productoDTO2.setId(2L);
        assertThat(productoDTO1).isNotEqualTo(productoDTO2);
        productoDTO1.setId(null);
        assertThat(productoDTO1).isNotEqualTo(productoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(productoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(productoMapper.fromId(null)).isNull();
    }
}
