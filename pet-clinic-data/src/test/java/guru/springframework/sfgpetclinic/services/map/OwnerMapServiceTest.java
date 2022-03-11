package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {
    OwnerMapService ownerMapService;

    final String lastName = "Kushnir";
    final Long ownerId = 1L;

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());

        ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
    }

    @Test
    void findAll() {
        Set<Owner> owners = ownerMapService.findAll();

        assertEquals(1, owners.size());

    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);

        assertEquals(owner.getId(), ownerId);
    }

    @Test
    void savePresentId() {
        Long id = 2L;

        Owner owner2 = Owner.builder().id(id).build();

        Owner saveOwner = ownerMapService.save(owner2);

        assertEquals(id, saveOwner.getId());
    }

    @Test
    void saveAbsentId() {
        Owner savedOwner = ownerMapService.save(Owner.builder().id(3L).build());

        assertNotNull(savedOwner);

        assertNotNull(savedOwner.getId());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);

        assertEquals(null, ownerMapService.findById(ownerId));
    }

    @Test
    void findByLastName() {
        Owner kushnir = ownerMapService.findByLastName(lastName);

        assertNotNull(kushnir);

        assertEquals(ownerId, kushnir.getId());
    }

    @Test
    void findByLastNameReturnsNull() {
        Owner georgiev = ownerMapService.findByLastName("Georgiev");

        assertNull(georgiev);
    }
}