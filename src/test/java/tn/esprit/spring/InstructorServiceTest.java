package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.Instructor;
import tn.esprit.spring.repositories.ICourseRepository;
import tn.esprit.spring.repositories.IInstructorRepository;
import tn.esprit.spring.services.InstructorServicesImpl;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class InstructorServiceTest {

    // Mock dependencies for IInstructorRepository and ICourseRepository to simulate their behavior
    @Mock
    private IInstructorRepository instructorRepository;

    @Mock
    private ICourseRepository courseRepository;

    // Inject mocks into the instance of InstructorService to be tested
    @InjectMocks
    private InstructorServicesImpl instructorServices;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddInstructor() {
        // Create a test Instructor object
        Instructor instructor = new Instructor(null, "John", "Doe", LocalDate.now(), null);

        // Define the behavior for the mock repository: save(instructor) will return instructor
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        // Call the service method to add an instructor
        Instructor result = instructorServices.addInstructor(instructor);

        // Verify that the added instructor is not null and has the expected attributes
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        
        // Verify that the save method was called once with the instructor
        verify(instructorRepository, times(1)).save(instructor);
    }

    @Test
    void testRetrieveInstructor() {
        // Define the ID of the instructor to retrieve
        Long numInstructor = 1L;
        // Create a test Instructor object with the specified ID
        Instructor instructor = new Instructor(numInstructor, "Jane", "Smith", LocalDate.now(), null);

        // Define the behavior for findById: it will return an Optional containing the instructor
        when(instructorRepository.findById(numInstructor)).thenReturn(Optional.of(instructor));

        // Call the service method to retrieve an instructor by ID
        Instructor result = instructorServices.retrieveInstructor(numInstructor);

        // Verify that the retrieved instructor is not null and has the expected attributes
        assertNotNull(result);
        assertEquals(numInstructor, result.getNumInstructor());
        assertEquals("Jane", result.getFirstName());
        
        // Verify that findById was called once with the correct ID
        verify(instructorRepository, times(1)).findById(numInstructor);
    }

    @Test
    void testAddInstructorAndAssignToCourse() {
        // Define the ID of the course to which the instructor will be assigned
        Long numCourse = 1L;
        // Create a test Course object with the specified ID
        Course course = new Course();
        course.setNumCourse(numCourse);

        // Create a test Instructor object with no courses assigned
        Instructor instructor = new Instructor(null, "Alice", "Johnson", LocalDate.now(), null);

        // Simulate retrieving the course by its ID and saving the instructor
        when(courseRepository.findById(numCourse)).thenReturn(Optional.of(course));
        when(instructorRepository.save(instructor)).thenReturn(instructor);

        // Call the service method to add an instructor and assign them to the course
        Instructor result = instructorServices.addInstructorAndAssignToCourse(instructor, numCourse);

        // Verify that the resulting instructor is not null and has the assigned course
        assertNotNull(result);
        assertEquals(1, result.getCourses().size());
        assertEquals(course, result.getCourses().iterator().next());

        // Verify that findById and save were each called once
        verify(courseRepository, times(1)).findById(numCourse);
        verify(instructorRepository, times(1)).save(instructor);
    }
}
