package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import oslomet.testing.Models.Kunde;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class EnhetstestAdminKundeController {

    @InjectMocks
    //denne klassen skal testes
    private AdminKundeController adminKundeController;

    @Mock
    // denne skal Mock'es
    private AdminRepository repository;

    @Mock
    // denne skal Mock'es
    private Sikkerhet sjekk;

    @Test
    public void lagrekunde_loggetInn(){
        Kunde enKunde = new Kunde( "21010110523", "Kene", "Lensen", "Akerveien 22", "3280", "Oslo", "34556654", "okei");
        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.registrerKunde(enKunde)).thenReturn("OK");
        String resultat = adminKundeController.lagreKunde(enKunde);

        assertEquals("OK",resultat);
    }

    @Test
    public void lagrekunde_ikkeLoggetInn(){
        Kunde enKunde = new Kunde( "21010110523", "Kene", "Lensen", "Akerveien 22", "3280", "Oslo", "34556654", "okei");
        when(sjekk.loggetInn()).thenReturn("Ikke logget inn");

        when(repository.registrerKunde(enKunde)).thenReturn("OK");
        String resultat = adminKundeController.lagreKunde(enKunde);

        assertEquals("OK",resultat);
    }

    @Test
    public void slettKunde_loggetInn(){
        Kunde enKunde = new Kunde( "21010110523", "Kene", "Lensen", "Akerveien 22", "3280", "Oslo", "34556654", "okei");
        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.slettKunde(enKunde.getPersonnummer())).thenReturn("OK");
        String resultat = adminKundeController.slett(enKunde.getPersonnummer());

        assertEquals("OK", resultat);

    }

    @Test
    public void slettKunde_ikkeLoggetInn(){
        Kunde enKunde = new Kunde( "21010110523", "Kene", "Lensen", "Akerveien 22", "3280", "Oslo", "34556654", "okei");
        when(sjekk.loggetInn()).thenReturn("Ikke logget inn");

        when(repository.slettKunde(enKunde.getPersonnummer())).thenReturn("OK");
        String resultat = adminKundeController.slett(enKunde.getPersonnummer());

        assertEquals("OK", resultat);

    }


    @Test
    public void HentAlleKunder_LoggetInn(){

        //arrange
        List<Kunde> kundelist = new ArrayList<>();
        Kunde KundeEn = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");
        Kunde KundeTo = new Kunde("12345678901", "Per", "Hansen", "Osloveien 82", "1234", "Oslo", "12345678","HeiHei");

        kundelist.add(KundeEn);
        kundelist.add(KundeTo);

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.hentAlleKunder()).thenReturn(kundelist);

        //act
        List<Kunde> resultat = adminKundeController.hentAlle();

        //assert
        assertEquals(kundelist, resultat);
    }

    @Test
    public void HentAlleKunder_IkkeLoggetInn() {

        //arrange
        when(sjekk.loggetInn()).thenReturn(null);

        //act
        List<Kunde> resultat = adminKundeController.hentAlle();

        //assert
        assertNull(resultat);
    }

    @Test
    public void EndreKundeInfo_LoggetInn(){

        //arrange
        Kunde KundeEn = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn("Admin");

        when(repository.endreKundeInfo(KundeEn)).thenReturn("Ok");

        //act
        String resultat = adminKundeController.endre(KundeEn);

        //assert
        assertEquals("Ok", resultat);
    }

    @Test
    public void EndreKundeInfo_IkkeLoggetInn(){

        //arrange
        Kunde KundeEn = new Kunde("01010110523", "Lene", "Jensen", "Askerveien 22", "3270", "Asker", "22224444", "HeiHei");

        when(sjekk.loggetInn()).thenReturn(null);

        //act
        String resultat = adminKundeController.endre(KundeEn);

        //assert
        assertEquals("Ikke logget inn", resultat);
    }


}




