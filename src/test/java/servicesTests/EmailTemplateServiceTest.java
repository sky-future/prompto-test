package servicesTests;

import com.prompto.dto.emailTemplate.EmailTemplateDTO;
import com.prompto.exception.ResourceNotFoundException;
import com.prompto.model.emailTemplate.EmailTemplate;
import com.prompto.repository.emailTemplate.EmailTemplateRepository;
import com.prompto.service.emailTemplateService.EmailTemplateService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailTemplateServiceTest {

    @Mock
    private EmailTemplateRepository repository;

    @InjectMocks
    private EmailTemplateService service;

    @Test
    void testGetEmailTemplateById_success() {
        EmailTemplate entity = new EmailTemplate();
        entity.setId(1L);
        entity.setName("Test");
        entity.setSubject("Subject");
        entity.setBodyHtml("<p>Hi</p>");
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        when(repository.findById(1L)).thenReturn(Optional.of(entity));

        EmailTemplateDTO dto = service.getEmailTemplateById(1L);

        assertNotNull(dto);
        assertEquals("Test", dto.getName());
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
        dto.setName("New");
        dto.setSubject("New Subject");
        dto.setBodyHtml("<p>New</p>");

        EmailTemplate saved = new EmailTemplate();
        saved.setId(1L);
        saved.setName("New");
        saved.setSubject("New Subject");
        saved.setBodyHtml("<p>New</p>");
        saved.setCreatedAt(LocalDateTime.now());
        saved.setUpdatedAt(LocalDateTime.now());

        when(repository.save(any(EmailTemplate.class))).thenReturn(saved);

        EmailTemplateDTO result = service.createEmailTemplate(dto);

        assertNotNull(result.getId());
        assertEquals("New", result.getName());
    }

    @Test
    void testDelete_notFound() {
        when(repository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(1L));
    }
}