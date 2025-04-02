package etap2;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Plansza {
    private int[][] plansza;
    private final int[][] originalPlansza;

    public Plansza(String plikPlanszy) throws IOException {
        odczyt(plikPlanszy);
        originalPlansza = copyPlansza(plansza);
    }

    public void odczyt(String plikPlanszy) throws IOException {
        List<String> linie = Files.readAllLines(Paths.get(plikPlanszy));
        linie.removeIf(String::isEmpty);  // Usuwa puste linie
        int wiersze = linie.size();
        int kolumny = linie.get(0).split(" ").length;
        plansza = new int[wiersze][kolumny];
        for (int i = 0; i < wiersze; i++) {
            String[] wartosci = linie.get(i).split(" ");
            for (int j = 0; j < kolumny; j++) {
                plansza[i][j] = Integer.parseInt(wartosci[j]);
            }
        }
    }

    private int[][] copyPlansza(int[][] source) {
        int[][] copy = new int[source.length][];
        for (int i = 0; i < source.length; i++) {
            copy[i] = source[i].clone();
        }
        return copy;
    }

    public int[][] getPlansza() {
        return plansza;
    }

    public void setPlansza(int[][] plansza) {
        this.plansza = plansza;
    }
}
