package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import static java.time.LocalDate.now;


@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;

    @Autowired
    public DataLoader(
            OwnerService ownerService,
            VetService vetService,
            PetTypeService petTypeService,
            SpecialtyService specialtyService,
            VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();

        if (count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType saveDogPetType = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
        PetType saveCatPetType = petTypeService.save(cat);

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality saveRadiology = specialtyService.save(radiology);

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");
        Speciality saveSurgery = specialtyService.save(surgery);

        Speciality dentistry = new Speciality();
        dentistry.setDescription("Dentistry");
        Speciality saveDentistry = specialtyService.save(dentistry);


        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("34 Baker str.");
        owner1.setCity("London");
        owner1.setTelephone("945676851");

        Pet mikesDog = new Pet();
        mikesDog.setPetType(saveDogPetType);
        mikesDog.setOwner(owner1);
        mikesDog.setBirthDate(now());
        mikesDog.setName("mikesDog");
        owner1.getPets().add(mikesDog);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("34 Baker str.");
        owner2.setCity("London");
        owner2.setTelephone("345612345");

        Pet fionasCat = new Pet();
        fionasCat.setPetType(saveCatPetType);
        fionasCat.setOwner(owner2);
        fionasCat.setBirthDate(now().plusDays(1));
        fionasCat.setName("FionasCat");
        owner2.getPets().add(fionasCat);

        ownerService.save(owner2);

        System.out.println("Owners loaded");

        Visit fionasCatVisit = new Visit();
        fionasCatVisit.setPet(fionasCat);
        fionasCatVisit.setDescription("Fiona with cat");
        fionasCatVisit.setDate(LocalDate.now());

        visitService.save(fionasCatVisit);

        Visit mikesDogVisit = new Visit();
        mikesDogVisit.setPet(mikesDog);
        mikesDogVisit.setDescription("Mike with dog");
        mikesDogVisit.setDate(LocalDate.now().minusDays(1));

        visitService.save(mikesDogVisit);

        System.out.println("Visits loaded");

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(saveRadiology);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");
        vet2.getSpecialities().add(saveDentistry);

        vetService.save(vet2);

        System.out.println("Vets loaded");
    }
}
