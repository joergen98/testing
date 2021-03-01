package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.BankController;
import oslomet.testing.DAL.BankRepository;
import oslomet.testing.Models.Konto;
import oslomet.testing.Models.Kunde;
import oslomet.testing.Models.Transaksjon;
import oslomet.testing.Sikkerhet.Sikkerhet;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestBankController {

    @InjectMocks
    // denne skal testes
    private BankController bankController;

    @Mock
    // denne skal Mock'es
    private BankRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void hentTransaksjoner_LoggetInn(){
        //Arrange
        Konto enKonto = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);

        when(sjekk.loggetInn()).thenReturn(enKonto.getKontonummer());

        when(repository.hentTransaksjoner(anyString(),anyString(),anyString())).thenReturn(enKonto);

        //Act
        Konto resultat = bankController.hentTransaksjoner("987654321", "01-03-2021", "01-03-2021");

        //Assert
        assertEquals(enKonto, resultat);

    }

    @Test
    public void hentTransaksjoner_IkkeLoggetInn(){
        //Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Konto resultat = bankController.hentTransaksjoner("987654321", "01-03-2021","01-03-2021");

        //Assert
        assertNull(resultat);
    }

    @Test
    public void hentSaldi_LoggetInn(){
        //Arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn(konto1.getKontonummer());

        when(repository.hentSaldi(anyString())).thenReturn(konti);

        //Act
        List<Konto> resultat = bankController.hentSaldi();

        //Assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentSaldi_IkkeLoggetInn(){
        //Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //Act
        List<Konto> resultat = bankController.hentSaldi();

        //Assert
        assertNull(resultat);
    }

    @Test
    public void registrerBetaling_LoggetInn(){
        //Arrange
        Transaksjon transaksjon = new Transaksjon(100, "123456789", 500, "01-03-2021", "Ny betaling", "Dest", "123456788");

        when(sjekk.loggetInn()).thenReturn(transaksjon.getFraTilKontonummer());

        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("OK");

        //Act
        String resultat = bankController.registrerBetaling(transaksjon);

        //Assert
        assertEquals("OK", resultat);
    }

    @Test
    public void registrerBetaling_LoggetInnFeil(){
        //Arrange
        Transaksjon transaksjon = new Transaksjon(100, "123456789", 500, "01-03-2021", "Ny betaling", "Dest", "123456788");

        when(sjekk.loggetInn()).thenReturn("111222");

        when(repository.registrerBetaling(any(Transaksjon.class))).thenReturn("Feil");

        //Act
        String resultat = bankController.registrerBetaling(transaksjon);

        //Assert
        assertEquals("Feil", resultat);
    }

    @Test
    public void registrerBetaling_IkkeLoggetInn(){
        //Arrange
        Transaksjon transaksjon = new Transaksjon(100, "123456789", 500, "01-03-2021", "Ny betaling", "Dest", "123456788");

        when(sjekk.loggetInn()).thenReturn(null);

        //Act
        String resultat = bankController.registrerBetaling(transaksjon);

        //Assert
        assertNull(resultat);
    }

    @Test
    public void hentBetalinger_LoggetInn(){
        //Arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(100, "123456789", 500, "01-03-2021", "Ny betaling", "Dest", "123456788");
        Transaksjon transaksjon2 = new Transaksjon(100, "121212122", 500, "01-03-2021", "Ny betaling", "Dest", "123321123");
        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn(transaksjon1.getFraTilKontonummer());

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjonList);

        //Act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        //Assert
        assertEquals(transaksjonList,resultat);
    }

    @Test
    public void hentBetalinger_IkkeLoggetInn(){
        //Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //Act
        List<Transaksjon> resultat = bankController.hentBetalinger();

        //Assert
        assertNull(resultat);
    }

    @Test
    public void utForBetaling_LoggetInn(){
        //Arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(100, "123456789", 500, "01-03-2021", "Ny betaling", "Dest", "123456788");
        Transaksjon transaksjon2 = new Transaksjon(101, "121212122", 500, "01-03-2021", "Ny betaling", "Dest", "123321123");
        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn(transaksjon1.getFraTilKontonummer());

        when(repository.utforBetaling(any(int.class))).thenReturn("OK");

        when(repository.hentBetalinger(anyString())).thenReturn(transaksjonList);

        //Act
        List<Transaksjon> resultat = bankController.utforBetaling(102);

        //Assert
        assertEquals(transaksjonList, resultat);
    }

    @Test
    public void utForBetaling_LoggetInnFeil(){
        //Arrange
        List<Transaksjon> transaksjonList = new ArrayList<>();
        Transaksjon transaksjon1 = new Transaksjon(100, "123456789", 500, "01-03-2021", "Ny betaling", "Dest", "123456788");
        Transaksjon transaksjon2 = new Transaksjon(101, "121212122", 500, "01-03-2021", "Ny betaling", "Dest", "123321123");
        transaksjonList.add(transaksjon1);
        transaksjonList.add(transaksjon2);

        when(sjekk.loggetInn()).thenReturn("123");

        when(repository.utforBetaling(any(int.class))).thenReturn("Feil");

        //Act
        List<Transaksjon> resultat = bankController.utforBetaling(102);

        //Assert
        assertNull(resultat);
    }

    @Test
    public void utForBetaling__IkkeLoggetInn(){
        //Arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Transaksjon> resultat = bankController.utforBetaling(102);

        //Assert
        assertNull(resultat);
    }

    @Test
    public void endre_LoggetInn(){
        //Arrange
        Kunde kunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("987654321");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("OK");

        //act
        String resultat = bankController.endre(kunde);

        //Assert
        assertEquals("OK",resultat);
    }

    @Test
    public void endre_IkkeLoggetInn(){
        //Arrange
        Kunde kunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        //Act
        String resultat = bankController.endre(kunde);

        //Assert
        assertNull(resultat);
    }

    @Test
    public void endre_LoggetInnFeil(){
        //Arrange
        Kunde kunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("987654321");

        when(repository.endreKundeInfo(any(Kunde.class))).thenReturn("Feil");

        //act
        String resultat = bankController.endre(kunde);

        //Assert
        assertEquals("Feil",resultat);
    }

    @Test
    public void hentKundeInfo_loggetInn() {

        // arrange
        Kunde enKunde = new Kunde("01010110523",
                "Lene", "Jensen", "Askerveien 22", "3270",
                "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKundeInfo(anyString())).thenReturn(enKunde);

        // act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertEquals(enKunde, resultat);
    }

    @Test
    public void hentKundeInfo_IkkeloggetInn() {

        // arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        Kunde resultat = bankController.hentKundeInfo();

        // assert
        assertNull(resultat);
    }

    @Test
    public void hentKonti_LoggetInn()  {
        // arrange
        List<Konto> konti = new ArrayList<>();
        Konto konto1 = new Konto("105010123456", "01010110523",
                720, "Lønnskonto", "NOK", null);
        Konto konto2 = new Konto("105010123456", "12345678901",
                1000, "Lønnskonto", "NOK", null);
        konti.add(konto1);
        konti.add(konto2);

        when(sjekk.loggetInn()).thenReturn("01010110523");

        when(repository.hentKonti(anyString())).thenReturn(konti);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertEquals(konti, resultat);
    }

    @Test
    public void hentKonti_IkkeLoggetInn()  {
        // arrange

        when(sjekk.loggetInn()).thenReturn(null);

        // act
        List<Konto> resultat = bankController.hentKonti();

        // assert
        assertNull(resultat);
    }
}

