package etap2;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Gra {
    public Gra() {
    }

    public static void main(String[] args) {
        try {
            String[] dozwolonewartosci = new String[]{"1", "2", "3", "4", "5", "6", "7", "losuj", "test"};
            Scanner skaner = new Scanner(System.in);
            System.out.print("Podaj numer na której planszy chcesz grać lub wylosuj planszę [numer planszy [1,2,3,4,5,6,7] / losuj]: ");
            String wybor;
            while (true) {
                String wejscie = skaner.nextLine();
                if (Arrays.stream(dozwolonewartosci).anyMatch(wejscie::equals)) {

                    if (Objects.equals(wejscie, "losuj")) {
                        Random liczba = new Random();
                        int los = liczba.nextInt(7) + 1;
                        wybor = String.valueOf(los);
                    } else {
                        wybor = wejscie;
                    }
                    break;
                } else {
                    System.out.println("Takiej planszy nie ma");
                }
            }

            String basePath = "prm2t24l_pro_zowczak_numberlink/src/";
            Plansza plansza = new Plansza(basePath + "pla" + wybor);
            Plansza sprawdzenie = new Plansza(basePath + "rpla" + wybor);
            Uzytkownik uzytkownik = new Uzytkownik(plansza);

            String komenda;
            do {
                wyswietlPlansze(uzytkownik);
                if (uzytkownik.czyWygrana(sprawdzenie)) {
                    czyszczeniekonsoli();
                    System.out.println("Gratulacje! Wygrałeś!");
                    break;
                }

                System.out.print("Używaj komend żeby przesuwać się po planszy i zmieniać numery \nGra powie ci kiedy wygrasz \nPodaj komendę (przesun - p [góra - g/dół - d/lewo - l/prawo - p], ustaw [wartość] - u [wartość], cofnij - c, reset, zapisz [plik], wyjdź): ");
                komenda = skaner.nextLine();
                wykonajKomende(komenda, uzytkownik, plansza);
            } while (!komenda.equals("wyjdź"));
        } catch (IOException var9) {
            System.err.println("Błąd wczytywania planszy: " + var9.getMessage());
        }

    }

    private static void wyswietlPlansze(Uzytkownik uzytkownik) {
        int[][] plansza = uzytkownik.getPlansza();
        int[] kursor = uzytkownik.getKursor();

        for (int i = 0; i < plansza.length; ++i) {
            for (int j = 0; j < plansza[i].length; ++j) {
                if (i == kursor[0] && j == kursor[1]) {
                    System.out.print("[" + plansza[i][j] + "]");
                } else {
                    System.out.print(" " + plansza[i][j] + " ");
                }
            }
            System.out.println();
        }

    }

    private static void wykonajKomende(String komenda, Uzytkownik uzytkownik, Plansza plansza) {
        String[] czesci = komenda.split(" ");
        czyszczeniekonsoli();

        try {
            switch (czesci[0]) {
                case "p"://poruszanie się
                    if (czesci.length > 1) {
                        uzytkownik.poruszanie(czesci[1]);
                    }
                    break;
                case "u"://zmiana cyfry
                    if (czesci.length > 1) {
                        uzytkownik.laczenie(Integer.parseInt(czesci[1]));
                    }
                    break;
                case "c"://cofanie
                    uzytkownik.cofnij();
                    break;
                case "reset"://powrót do pierwotnej planszy
                    uzytkownik.reset();
                    break;
                case "zapisz":
                    if (czesci.length > 1) {
                        uzytkownik.zapis(czesci[1]);
                    }
                    break;
                case "wyjdź":
                    System.out.println("Do widzenia!");
                    break;
                default:
                    System.out.println("Nieprawidłowa komenda.");
            }
        } catch (IOException var7) {
            System.err.println("Błąd: " + var7.getMessage());
        } catch (NumberFormatException var8) {
            System.err.println("Nieprawidłowy format liczby.");
        }

    }

    private static void czyszczeniekonsoli() {// usuwanie poprzednich plansz, wyświetla się tylko aktualna
        for (int clear = 0; clear < 1000; ++clear) {
            System.out.println("\b");
        }

    }
}
