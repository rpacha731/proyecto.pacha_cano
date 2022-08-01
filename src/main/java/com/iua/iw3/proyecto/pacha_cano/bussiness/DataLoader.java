package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.model.*;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.Rol;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.RolRepository;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.UserRepository;
import com.iua.iw3.proyecto.pacha_cano.persistence.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RolRepository rolRepository;
    private CamionRepository camionRepository;
    private ChoferRepository choferRepository;
    private ClienteRepository clienteRepository;
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) {

        if (rolRepository.findByNombre("ROLE_ADMIN").isEmpty()) {
            Rol admin = Rol.builder()
                    .nombre("ROLE_ADMIN")
                    .descripcion("Rol de administrador")
                    .build();
            try {
                rolRepository.save(admin);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
            }
        }

        if (rolRepository.findByNombre("ROLE_USER").isEmpty()) {
            Rol user = Rol.builder()
                    .nombre("ROLE_USER")
                    .descripcion("Rol de usuario")
                    .build();
            try {
                rolRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
            }
        }

        if (userRepository.findFirstByEmail("graylogpc2020@gmail.com").isEmpty()) {
            Set<Rol> roles = new HashSet<>(rolRepository.findAll());
            User admin = User.builder()
                    .nombre("Pacha-Cano")
                    .apellido("IW3")
                    .enabled(true)
                    .email("graylogpc2020@gmail.com")
                    .password(passwordEncoder.encode("pc-2022"))
                    .roles(roles)
                    .build();
            try {
                userRepository.save(admin);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
            }
        }

        if (userRepository.findFirstByEmail("lpacha1603@gmail.com").isEmpty()) {
            Set<Rol> rol = new HashSet<>();
            rol.add(rolRepository.findByNombre("ROLE_USER").get());
            User user = User.builder()
                    .nombre("Leonel ")
                    .apellido("User")
                    .enabled(true)
                    .email("lpacha1603@gmail.com")
                    .password(passwordEncoder.encode("pc-2022"))
                    .roles(rol).build();
            try {
                userRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
            }
        }

        if (this.camionRepository.findAll().isEmpty()) {
            Camion camion = Camion.builder()
                    .patente("AB123CD")
                    .descripcion("Camión de carga")
                    .cisternado(25000L)
                    .codigoExterno("CAM_1234")
                    .build();
            try {
                this.camionRepository.save(camion);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
            }
        }

        if (this.choferRepository.findAll().isEmpty()) {
            Chofer chofer = Chofer.builder()
                    .nombre("Nicolas ")
                    .apellido("Gómez")
                    .dni(48948915L)
                    .codigoExterno("NIC_GOM_915")
                    .build();
            try {
                this.choferRepository.save(chofer);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
            }
        }

        if (this.clienteRepository.findAll().isEmpty()) {
            Cliente cliente = Cliente.builder()
                    .razonSocial("Arcor")
                    .contacto("arcor@gmail.com")
                    .codigoExterno("CLN123")
                    .build();
            try {
                this.clienteRepository.save(cliente);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
            }
        }

        if (this.productoRepository.findAll().isEmpty()) {
            Producto producto = Producto.builder()
                    .nombre("Gas Liquido")
                    .descripcion("Gas GNC Liquido Volátil")
                    .codigoExterno("GNC_LV")
                    .build();
            try {
                this.productoRepository.save(producto);
            } catch (DataIntegrityViolationException e) {
                log.error(e.getMessage());
            }
        }

    }
}
