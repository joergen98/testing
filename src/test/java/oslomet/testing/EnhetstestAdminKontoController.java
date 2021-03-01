package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKontoController {

    @InjectMocks
    //denne klassen skal testes
    private AdminKontoController adminKontoController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentAlleKonti_LoggetInn(){

        //arrange
        List<Konto> kontoList = new ArrayList<>();
        Konto kontoEn = new Konto("01010110523","105010123456",1000,"Lønnskonto","Nok", null );
        Konto kontoTo = new Konto("01010110524","105010123457",1000,"Lønnskonto","Nok", null );

        kontoList.add(kontoEn);
        kontoList.add(kontoTo);

        when(sjekk.loggetInn()).thenReturn("105010123456");

        when(repository.hentAlleKonti()).thenReturn(kontoList);

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        assertEquals(kontoList, resultat);
    }
}

