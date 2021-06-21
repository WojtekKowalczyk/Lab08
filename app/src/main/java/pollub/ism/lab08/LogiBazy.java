package pollub.ism.lab08;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Logi")
public class LogiBazy{

    @PrimaryKey(autoGenerate = true)
    public int _id;
    public String HISTORIA;
}