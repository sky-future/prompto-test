package ControllerTests;

import com.prompto.controller.emailTemplate.EmailTemplateController;
import com.prompto.dto.emailTemplate.EmailTemplateDTO;
import com.prompto.exception.ResourceNotFoundException;
import com.prompto.service.impl.EmailTemplateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailTemplateControllerTest {

    @Mock
    private EmailTemplateServiceImpl service;

    @InjectMocks
    private EmailTemplateController controller;

    private EmailTemplateDTO sampleDTO;

    private LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        sampleDTO = new EmailTemplateDTO();
        sampleDTO.setId(1L);
        sampleDTO.setName("Test template");
        sampleDTO.setSubject("Test subject");
        sampleDTO.setBodyHtml("<p>Body</p>");
        sampleDTO.setCreatedAt(now);
        sampleDTO.setUpdatedAt(null);
    }

    @Test
    public void testGetAll() {
                when(service.getAllEmailTemplates()).thenReturn(Collections.singletonList(sampleDTO));

                ResponseEntity<List<EmailTemplateDTO>> response = controller.getAll();

                assertEquals(HttpStatus.OK, response.getStatusCode());
                List<EmailTemplateDTO> result = response.getBody();
                assertNotNull(result);
                assertEquals(1, result.size());
                assertEquals("Test template", result.getFirst().getName());
            }


    @Test
    public void testGetById_found() {
        when(service.getEmailTemplateById(1L)).thenReturn(sampleDTO);
        ResponseEntity<EmailTemplateDTO> response = controller.getById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test subject", response.getBody().getSubject());
    }

    @Test
    public void testGetById_notFound() {
        Long nonExistentId = 2L;
        when(service.getEmailTemplateById(nonExistentId))
            .thenThrow(new ResourceNotFoundException("Email template with ID " + nonExistentId + " not found"));

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> controller.getById(nonExistentId)
        );

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains(nonExistentId.toString()));
        verify(service, times(1)).getEmailTemplateById(nonExistentId);
    }

    @Test
    public void testCreate() {
        when(service.createEmailTemplate(sampleDTO)).thenReturn(sampleDTO);
        ResponseEntity<EmailTemplateDTO> response = controller.create(sampleDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test template", response.getBody().getName());
        assertEquals("Test subject", response.getBody().getSubject());
        assertEquals("<p>Body</p>", response.getBody().getBodyHtml());
        assertEquals(now, response.getBody().getCreatedAt());
        assertNull(response.getBody().getUpdatedAt());
    }

    @Test
    public void testUpdate_found() {
        // Create initial timestamp before update
        LocalDateTime beforeUpdate = LocalDateTime.now();

        // Simulate service behavior with updated DTO
        EmailTemplateDTO updatedDTO = new EmailTemplateDTO();
        updatedDTO.setSubject("Test subject");
        updatedDTO.setCreatedAt(beforeUpdate);
        updatedDTO.setUpdatedAt(LocalDateTime.now()); // Service sets this value
        when(service.update(1L, sampleDTO)).thenReturn(updatedDTO);

        // Execute update
        ResponseEntity<EmailTemplateDTO> response = controller.update(1L, sampleDTO);

        // Verifications
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test subject", response.getBody().getSubject());
        assertNotNull(response.getBody().getUpdatedAt(), "updatedAt should not be null");
        assertTrue(response.getBody().getUpdatedAt().isAfter(beforeUpdate),
                "updatedAt should be after the time before update");
        assertEquals(beforeUpdate , response.getBody().getCreatedAt(), "createdAt should remain unchanged");
    }


    @Test
    public void testUpdate_notFound() {
        Long nonExistentId = 2L;
        when(service.update(nonExistentId, sampleDTO))
            .thenThrow(new ResourceNotFoundException("Email template with ID " + nonExistentId + " not found"));

        ResourceNotFoundException exception = assertThrows(
            ResourceNotFoundException.class,
            () -> controller.update(nonExistentId, sampleDTO)
        );

        assertNotNull(exception);
        assertTrue(exception.getMessage().contains(nonExistentId.toString()));
        verify(service, times(1)).update(nonExistentId, sampleDTO);
    }


    @Test
    public void testDelete_found() {
        doNothing().when(service).delete(1L);

        ResponseEntity<Void> response = controller.delete(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDelete_notFound() {
        doThrow(new ResourceNotFoundException("Email template with ID 2 not found"))
                .when(service).delete(2L);

        assertThrows(ResourceNotFoundException.class, () -> controller.delete(2L));
    }

    @Test
    public void testCreate_withNullTemplate() {
        assertThrows(IllegalArgumentException.class, () -> controller.create(null));
    }

    @Test
    public void testCreate_withInvalidTemplate() {
        EmailTemplateDTO invalidDTO = new EmailTemplateDTO();

        when(service.createEmailTemplate(invalidDTO))
                .thenThrow(new IllegalArgumentException("Template name and content are required"));

        assertThrows(IllegalArgumentException.class, () -> controller.create(invalidDTO));
    }

    @Test
    public void testGetAll_emptyList() {
        when(service.getAllEmailTemplates()).thenReturn(Collections.emptyList());

        ResponseEntity<List<EmailTemplateDTO>> response = controller.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    public void testCreate_withExistingName() {
        when(service.createEmailTemplate(sampleDTO))
                .thenThrow(new IllegalStateException("Template with this name already exists"));

        assertThrows(IllegalStateException.class, () -> controller.create(sampleDTO));
    }

    @Test
    public void testUpdate_withoutIdChange() {
        EmailTemplateDTO updatedDTO = new EmailTemplateDTO();
        updatedDTO.setId(1L);
        updatedDTO.setName("Updated Name");
        updatedDTO.setSubject("Updated Subject");

        when(service.update(1L, updatedDTO)).thenReturn(updatedDTO);

        ResponseEntity<EmailTemplateDTO> response = controller.update(1L, updatedDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated Name", response.getBody().getName());
        assertEquals("Updated Subject", response.getBody().getSubject());
    }


}