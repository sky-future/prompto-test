package servicesTests;

import com.prompto.dto.emailTemplate.EmailTemplateDTO;
import com.prompto.exception.ResourceNotFoundException;
import com.prompto.model.emailTemplate.EmailTemplate;
import com.prompto.repository.emailTemplate.EmailTemplateRepository;
import com.prompto.service.impl.EmailTemplateServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailTemplateServiceTest {

    @Mock
    private EmailTemplateRepository repository;

    @InjectMocks
    private EmailTemplateServiceImpl service;

    @Test
    void testGetEmailTemplateById_success() {
        LocalDateTime now = LocalDateTime.now();
        EmailTemplate entity = new EmailTemplate();
        entity.setName("Test");
        entity.setSubject("Subject");
        entity.setBodyHtml("<p>Hi</p>");
        entity.setCreatedAt(now);
        entity.setUpdatedAt(LocalDateTime.now());

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        EmailTemplateDTO dto = service.getEmailTemplateById(1L);

        assertNotNull(dto);
        assertEquals("Test", dto.getName());
        assertEquals("Subject", dto.getSubject());
        assertEquals("<p>Hi</p>", dto.getBodyHtml());
        assertEquals(now, dto.getCreatedAt());

    }

    @Test
    void testGetEmailTemplateById_notFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getEmailTemplateById(1L));
    }

    @Test
    void testCreateEmailTemplate() {
        EmailTemplateDTO dto = new EmailTemplateDTO();
        LocalDateTime now = LocalDateTime.now();
        dto.setName("New");
        dto.setSubject("New Subject");
        dto.setBodyHtml("<p>New</p>");

        EmailTemplate saved = new EmailTemplate();
        saved.setId(1L);
        saved.setName("New");
        saved.setSubject("New Subject");
        saved.setBodyHtml("<p>New</p>");
        saved.setCreatedAt(now);
        saved.setUpdatedAt(LocalDateTime.now());

        when(repository.save(any(EmailTemplate.class))).thenReturn(saved);

        EmailTemplateDTO result = service.createEmailTemplate(dto);

        assertNotNull(result.getId());
        assertEquals("New", result.getName());
        assertEquals("New Subject", result.getSubject());
        assertEquals("<p>New</p>", result.getBodyHtml());
        assertEquals(now, result.getCreatedAt());

    }

    @Test
    void testDelete_notFound() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
    }

    @Test
    void testUpdate_success() {
        // Setup existing template
        EmailTemplate existing = new EmailTemplate();
        existing.setId(1L);
        existing.setName("Original");
        existing.setSubject("Original Subject");
        existing.setBodyHtml("<p>Original</p>");
        existing.setCreatedAt(LocalDateTime.now());
        existing.setUpdatedAt(LocalDateTime.now());

        // Setup update DTO
        EmailTemplateDTO updateDto = new EmailTemplateDTO();
        updateDto.setName("Updated");
        updateDto.setSubject("Updated Subject");
        updateDto.setBodyHtml("<p>Updated</p>");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(any(EmailTemplate.class))).thenAnswer(i -> i.getArguments()[0]);

        EmailTemplateDTO result = service.update(1L, updateDto);

        assertNotNull(result);
        assertEquals("Updated", result.getName());
        assertEquals("Updated Subject", result.getSubject());
        assertEquals("<p>Updated</p>", result.getBodyHtml());
        verify(repository).save(any(EmailTemplate.class));
    }

    @Test
    void testGetAllEmailTemplates_success() {
        EmailTemplate template1 = new EmailTemplate();
        template1.setName("Template 1");
        template1.setSubject("Subject 1");

        EmailTemplate template2 = new EmailTemplate();
        template2.setName("Template 2");
        template2.setSubject("Subject 2");

        when(repository.findAll()).thenReturn(List.of(template1, template2));

        List<EmailTemplateDTO> results = service.getAllEmailTemplates();

        assertEquals(2, results.size());
        assertEquals("Template 1", results.get(0).getName());
        assertEquals("Template 2", results.get(1).getName());
    }

    @Test
    void testDelete_success() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        assertDoesNotThrow(() -> service.delete(1L));
        verify(repository).deleteById(1L);
    }

    @Test
    void testCreateEmailTemplate_withNullFields() {
        EmailTemplateDTO dto = new EmailTemplateDTO();
        // All fields are null
        assertThrows(IllegalArgumentException.class,
                () -> service.createEmailTemplate(dto));
    }

    @Test
    void testUpdate_notFound() {
        EmailTemplateDTO updateDto = new EmailTemplateDTO();
        updateDto.setName("Updated");

        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.update(1L, updateDto));
    }

    @Test
    void testGetAllEmailTemplates_empty() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<EmailTemplateDTO> results = service.getAllEmailTemplates();

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }


}