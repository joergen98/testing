package oslomet.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import oslomet.testing.API.AdminKontoController;
import oslomet.testing.API.AdminKundeController;
import oslomet.testing.DAL.AdminRepository;
import oslomet.testing.Sikkerhet.Sikkerhet;
import oslomet.testing.Models.*;
import oslomet.testing.DAL.AdminRepository.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
}




