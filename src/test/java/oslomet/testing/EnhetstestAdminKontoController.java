package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Sikkerhet.Sikkerhet;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertNull;

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

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.hentAlleKonti()).thenReturn(kontoList);

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        assertEquals(kontoList, resultat);
    }


    @Test
    public void hentAlleKonti_IkkeLoggetInn(){
        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Konto> resultat = adminKontoController.hentAlleKonti();

        //assert
        assertNull(resultat);
    }

    @Test
    public void registrerKonto_LoggetInn(){

        //arrange
        Konto kontoEn = new Konto("01010110523","105010123456",1000,"Lønnskonto","Nok", null );

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.registrerKonto(kontoEn)).thenReturn("OK");

        String resultat = adminKontoController.registrerKonto(kontoEn);

        //Assert
        assertEquals("OK", resultat);

    }

    @Test
    public void registrerKonto_IkkeLoggetInn(){
        //Arrange
        Konto kontoEn = new Konto("01010110523","105010123456",1000,"Lønnskonto","Nok", null );

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.registrerKonto(kontoEn);

        //Assert
        assertEquals("Ikke innlogget", resultat);

    }

    @Test
    public void endreKonto_LoggetInn(){
        //Arrange
        Konto kontoEn = new Konto("01010110523","105010123456",1000,"Lønnskonto","Nok", null );

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.endreKonto(kontoEn)).thenReturn("OK");

        //act
        String resultat = adminKontoController.endreKonto(kontoEn);

        //Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void endreKonto_IkkeLoggetInn(){
        //Arrange
        Konto kontoEn = new Konto("01010110523","105010123456",1000,"Lønnskonto","Nok", null );

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKontoController.endreKonto(kontoEn);

        //Assert
        assertEquals("Ikke innlogget", resultat);
    }

    @Test
    public void slettKonto_LoggetInn(){
        //Arrange
        Konto kontoEn = new Konto("01010110523","105010123456",1000,"Lønnskonto","Nok", null );

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.slettKonto(kontoEn.getKontonummer())).thenReturn("OK");

        //Act
        String resultat = adminKontoController.slettKonto(kontoEn.getKontonummer());

        //Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void slettKonto_IkkeLoggetInn(){
        //Arrange
        Konto kontoEn = new Konto("01010110523","105010123456",1000,"Lønnskonto","Nok", null );

        when(sjekk.loggetInn()).thenReturn(null);

        //Act
        String resultat = adminKontoController.slettKonto(kontoEn.getKontonummer());

        //Assert
        assertEquals("Ikke innlogget", resultat);
    }


}

