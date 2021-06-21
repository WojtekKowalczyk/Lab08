package pollub.ism.lab08;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Logi")
public class PozycjaMagazynowa {

    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String NAME;
    public String HISTORIA;
    public int QUANTITY;
}