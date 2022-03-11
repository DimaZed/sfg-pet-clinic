package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    public static final String LAST_NAME = "Kushnir";

    Owner returnOwner;

    long aLong = 1l;

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService ownerSDJpaService;


    @BeforeEach
    void setUp() {
        returnOwner = Owner.builder().id(1l).lastName(LAST_NAME).build();
    }

    @Test
    void findAll() {
        Set<Owner> ownersSet = new HashSet<>();

        Owner firstOwner = Owner.builder().id(1l).lastName(LAST_NAME).build();
        Owner secondOwner = Owner.builder().id(2l).lastName(LAST_NAME).build();

        ownersSet.add(firstOwner);
        ownersSet.add(secondOwner);

        when(ownerRepository.findAll()).thenReturn(ownersSet);

        Set<Owner> ownersFromDB = ownerSDJpaService.findAll();

        assertNotNull(ownersFromDB);
        assertEquals(2, ownersFromDB.size());
    }

    @Test
    void findByIdReturnTrue() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(returnOwner));

        Owner owner = ownerSDJpaService.findById(aLong);

        assertNotNull(owner);
    }

    @Test
    void findByIdReturnFalse() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        Owner owner = ownerSDJpaService.findById(aLong);

        assertNull(owner);
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(aLong).lastName(LAST_NAME).build();

        when(ownerRepository.save(any())).thenReturn(returnOwner);

        Owner savedOwner = ownerRepository.save(ownerToSave);

        assertNotNull(savedOwner);
    }

    @Test
    void delete() {
        ownerSDJpaService.delete(returnOwner);

        verify(ownerRepository).delete(any());
    }

    @Test
    void deleteById() {
        ownerSDJpaService.deleteById(aLong);

        verify(ownerRepository).deleteById(anyLong());
    }

    @Test
    void findByLastName() {
        returnOwner = Owner.builder().id(1l).lastName(LAST_NAME).build();

        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        Owner findOwner = ownerSDJpaService.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME, findOwner.getLastName());

        verify(ownerRepository).findByLastName(any());
    }
}