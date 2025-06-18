package ControllerTests;

import com.prompto.controller.emailTemplate.EmailTemplateController;
import com.prompto.dto.emailTemplate.EmailTemplateDTO;
import com.prompto.exception.ResourceNotFoundException;
import com.prompto.service.emailTemplateService.EmailTemplateService;
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
    private EmailTemplateService service;

    @InjectMocks
    private EmailTemplateController controller;

    private EmailTemplateDTO sampleDTO;

    @BeforeEach
    void setUp() {
        sampleDTO = new EmailTemplateDTO();
        sampleDTO.setId(1L);
        sampleDTO.setName("Test template");
        sampleDTO.setSubject("Test subject");
        sampleDTO.setBodyHtml("<p>Body</p>");
        sampleDTO.setCreatedAt(LocalDateTime.now());
        sampleDTO.setUpdatedAt(LocalDateTime.now());
    }

    @Test
    public void testGetAll() {
        when(service.getAllEmailTemplates()).thenReturn(Collections.singletonList(sampleDTO));
        List<EmailTemplateDTO> result = controller.getAll();
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
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test template", response.getBody().getName());
    }

    @Test
    public void testUpdate_found() {
        when(service.update(1L, sampleDTO)).thenReturn(sampleDTO);
        ResponseEntity<EmailTemplateDTO> response = controller.update(1L, sampleDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test subject", response.getBody().getSubject());
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
}