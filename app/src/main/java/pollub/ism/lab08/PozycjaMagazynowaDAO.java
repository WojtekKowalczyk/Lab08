package pollub.ism.lab08;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PozycjaMagazynowaDAO {

    @Insert  //Automatyczna kwerenda wystarczy
    public void insert(PozycjaMagazynowa pozycja);

    @Update //Automatyczna kwerenda wystarczy
    void update(PozycjaMagazynowa pozycja);

    @Query("SELECT QUANTITY FROM Warzywniak WHERE NAME= :wybraneWarzywoNazwa") //Nasza kwerenda
    int findQuantityByName(String wybraneWarzywoNazwa);

    @Query("SELECT HISTORIA FROM Warzywniak WHERE NAME= :wybraneWarzywoNazwa") //Nasza kwerenda
    String findQuantityByName2(String wybraneWarzywoNazwa);

    @Query("UPDATE Warzywniak SET QUANTITY = :wybraneWarzywoNowaIlosc WHERE NAME= :wybraneWarzywoNazwa")
    void updateQuantityByName(String wybraneWarzywoNazwa, int wybraneWarzywoNowaIlosc);

    @Query("UPDATE Warzywniak SET HISTORIA = :wybraneWarzywoNowaHistoria WHERE NAME= :wybraneWarzywoNazwa")
    void updateQuantityByName2(String wybraneWarzywoNazwa, String wybraneWarzywoNowaHistoria);

    @Query("SELECT COUNT(*) FROM Warzywniak") //Ile jest rekordów w tabeli
    int size();
}
