package tn.esprit.studentmanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.repositories.StudentRepository;
import tn.esprit.studentmanagement.services.StudentService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialise Mockito
    }

    @Test
    void testGetAllStudents() {
        // Préparer les données factices
        Student s1 = new Student();
        s1.setFirstName("Ali");
        s1.setLastName("Ben");

        Student s2 = new Student();
        s2.setFirstName("Sara");
        s2.setLastName("Ben");

        when(studentRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

        // Appel de la méthode
        List<Student> students = studentService.getAllStudents();

        // Vérification
        assertEquals(2, students.size());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById_Found() {
        Student s = new Student();
        s.setIdStudent(1L);
        s.setFirstName("Ali");
        s.setLastName("Ben");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(s));

        Student result = studentService.getStudentById(1L);

        assertNotNull(result);
        assertEquals("Ali", result.getFirstName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        Student result = studentService.getStudentById(1L);

        assertNull(result);
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveStudent() {
        Student s = new Student();
        s.setFirstName("Ali");
        s.setLastName("Ben");

        when(studentRepository.save(s)).thenReturn(s);

        Student saved = studentService.saveStudent(s);

        assertNotNull(saved);
        assertEquals("Ali", saved.getFirstName());
        verify(studentRepository, times(1)).save(s);
    }

    @Test
    void testDeleteStudent() {
        doNothing().when(studentRepository).deleteById(1L);

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }
}
