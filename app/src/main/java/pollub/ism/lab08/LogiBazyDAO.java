package pollub.ism.lab08;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LogiBazyDAO {

    @Insert  //Automatyczna kwerenda wystarczy
    public void insert(LogiBazy logiBazy);


}