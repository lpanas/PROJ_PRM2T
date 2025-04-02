package etap2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Uzytkownik {
    private int[][] wspolrzedne;
    private final Plansza plansza;
    private int[] kursor = {0, 0};
    private final List<int[]> historia = new ArrayList<>();
    private final int[][] oryginalneWartosci;

    public Uzytkownik(Plansza plansza) {
        this.plansza = plansza;
        this.wspolrzedne = copyPlansza(plansza.getPlansza());
        this.oryginalneWartosci = copyPlansza(plansza.getPlansza());
    }

    public void poruszanie(String kierunek) {
        switch (kierunek) {
            case "g"://góra
                if (kursor[0] > 0) kursor[0]--;
                break;
            case "d"://dół
                if (kursor[0] < wspolrzedne.length - 1) kursor[0]++;
                break;
            case "l"://lewo
                if (kursor[1] > 0) kursor[1]--;
                break;
            case "p"://prawo
                if (kursor[1] < wspolrzedne[0].length - 1) kursor[1]++;
                break;
        }
    }

    public void laczenie(int wartosc) {
        if (oryginalneWartosci[kursor[0]][kursor[1]] == 0) {
            if (wartosc < 10 && wartosc > -1) {
                wspolrzedne[kursor[0]][kursor[1]] = wartosc;
                historia.add(kursor.clone());
            } else {
                System.out.println("Zła wartość");
            }
        } else {
            System.out.println("Tu coś jest");
        }
    }

    public void reset() {
        wspolrzedne = copyPlansza(plansza.getPlansza());
        kursor = new int[]{0, 0};
        historia.clear();
    }

    public void cofnij() {
        if (!historia.isEmpty()) {
            int[] ostatniRuch = historia.remove(historia.size() - 1);
            wspolrzedne[ostatniRuch[0]][ostatniRuch[1]] = 0;
        }
    }

    public int[][] getPlansza() {
        return wspolrzedne;
    }

    public int[] getKursor() {
        return kursor;
    }

    public boolean czyWygrana(Plansza planszaWygrana) {
        int[][] planszaDoSprawdzenia = planszaWygrana.getPlansza();
        if (wspolrzedne.length != planszaDoSprawdzenia.length || wspolrzedne[0].length != planszaDoSprawdzenia[0].length) {
            return false;
        }
        for (int i = 0; i < wspolrzedne.length; i++) {
            for (int j = 0; j < wspolrzedne[i].length; j++) {
                if (wspolrzedne[i][j] != planszaDoSprawdzenia[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void zapis(String plikPlanszy) throws IOException {
        List<String> linie = new ArrayList<>();
        for (int[] wiersz : wspolrzedne) {
            StringBuilder linia = new StringBuilder();
            for (int wartosc : wiersz) {
                linia.append(wartosc).append(" ");
            }
            linie.add(linia.toString().trim());
        }
        Files.write(Paths.get(plikPlanszy), linie);
    }

    private int[][] copyPlansza(int[][] source) {
        int[][] copy = new int[source.length][];
        for (int i = 0; i < source.length; i++) {
            copy[i] = source[i].clone();
        }
        return copy;
    }
}
